<!-- app/pages/catalogo/index.vue -->
<script setup lang="ts">
import type { ProductoResumen } from '~/types'
import { esOtrTbr } from '~/types'
import { useCatalogo } from '~/composables/useCatalogo'

const { fetchProductos } = useCatalogo()
const route = useRoute()

// --- Estado ---
const productos = ref<ProductoResumen[]>([])
const cargando = ref(true)
const sortBy = ref('precio-menor')
const sidebarOpen = ref(false)
const marcasFiltro = ref<string[]>([])
const categoriasFiltro = ref<string[]>([])

// --- Carga inicial ---
onMounted(async () => {
  const params: {
    marca?: string
    categoria?: string
    medida?: string
  } = {}

  if (typeof route.query.marca === 'string') {
    params.marca = route.query.marca
  }

  if (typeof route.query.categoria === 'string') {
    params.categoria = route.query.categoria
  }

  // Aplicar filtros de medida si vienen por query params del buscador.
  if (route.query.ancho || route.query.perfil || route.query.aro) {
    const partes = [
      route.query.ancho,
      route.query.perfil ? `/${route.query.perfil}` : '',
      route.query.aro ? ` R${route.query.aro}` : '',
    ].join('')
    if (partes.trim()) {
      params.medida = partes.trim()
    }
  }

  productos.value = await fetchProductos(params)
  cargando.value = false
})

// --- Marcas únicas para el filtro ---
const marcasDisponibles = computed(() => {
  const set = new Set(productos.value.map(p => p.marca))
  return [...set].sort()
})

// --- Categorías únicas para el filtro ---
const categoriasDisponibles = computed(() => {
  const set = new Set(productos.value.map(p => p.categoria))
  return [...set].sort()
})

// --- Productos filtrados y ordenados ---
const productosFiltrados = computed(() => {
  let lista = [...productos.value]

  if (marcasFiltro.value.length) {
    lista = lista.filter(p => marcasFiltro.value.includes(p.marca))
  }

  if (categoriasFiltro.value.length) {
    lista = lista.filter(p => categoriasFiltro.value.includes(p.categoria))
  }

  lista.sort((a, b) => {
    if (sortBy.value === 'precio-menor') return a.precio - b.precio
    if (sortBy.value === 'precio-mayor') return b.precio - a.precio
    return a.nombre.localeCompare(b.nombre)
  })

  return lista
})

const resultadosTexto = computed(() => {
  const total = productosFiltrados.value.length
  if (total === 0) return 'No se encontraron productos'
  return `${total} producto${total !== 1 ? 's' : ''} encontrado${total !== 1 ? 's' : ''}`
})

function toggleMarca(marca: string) {
  const idx = marcasFiltro.value.indexOf(marca)
  if (idx === -1) marcasFiltro.value.push(marca)
  else marcasFiltro.value.splice(idx, 1)
}

function toggleCategoria(cat: string) {
  const idx = categoriasFiltro.value.indexOf(cat)
  if (idx === -1) categoriasFiltro.value.push(cat)
  else categoriasFiltro.value.splice(idx, 1)
}

function limpiarFiltros() {
  marcasFiltro.value = []
  categoriasFiltro.value = []
  sortBy.value = 'precio-menor'
}

useSeoMeta({
  title: 'Catálogo de Productos — Andes Llantas',
  description: 'Encuentra la llanta ideal para tu vehículo o maquinaria. Las mejores marcas al mejor precio.',
})
</script>

<template>
  <div>
    <ShopTheHeroBanner />

    <div class="mx-auto grid w-full max-w-screen-2xl grid-cols-1 gap-5 px-4 py-5 sm:px-5 lg:grid-cols-[280px_minmax(0,1fr)] lg:gap-6 lg:px-8">

      <!-- Sidebar desktop -->
      <aside class="hidden lg:block">
        <div class="sticky top-[86px] rounded-xl border border-gray-100 bg-white p-5 shadow-sm">
          <div class="mb-4 flex items-center justify-between">
            <h2 class="text-[13px] font-black uppercase tracking-wider text-gray-800">Filtros</h2>
            <button
              v-if="marcasFiltro.length || categoriasFiltro.length"
              class="text-[11px] font-semibold text-brand-yellow hover:underline"
              @click="limpiarFiltros"
            >
              Limpiar todo
            </button>
          </div>

          <!-- Filtro por categoría -->
          <div class="mb-5">
            <p class="mb-2 text-[11px] font-bold uppercase tracking-wider text-gray-500">Categoría</p>
            <div class="flex flex-col gap-1.5">
              <label
                v-for="cat in categoriasDisponibles"
                :key="cat"
                class="flex cursor-pointer items-center gap-2 text-[13px] text-gray-700"
              >
                <input
                  type="checkbox"
                  :checked="categoriasFiltro.includes(cat)"
                  class="accent-brand-yellow"
                  @change="toggleCategoria(cat)"
                />
                {{ cat }}
              </label>
            </div>
          </div>

          <!-- Filtro por marca -->
          <div>
            <p class="mb-2 text-[11px] font-bold uppercase tracking-wider text-gray-500">Marca</p>
            <div class="flex flex-col gap-1.5">
              <label
                v-for="marca in marcasDisponibles"
                :key="marca"
                class="flex cursor-pointer items-center gap-2 text-[13px] text-gray-700"
              >
                <input
                  type="checkbox"
                  :checked="marcasFiltro.includes(marca)"
                  class="accent-brand-yellow"
                  @change="toggleMarca(marca)"
                />
                {{ marca }}
              </label>
            </div>
          </div>
        </div>
      </aside>

      <!-- Contenido principal -->
      <main id="productos" class="min-w-0 scroll-mt-24">
        <!-- Barra superior -->
        <div class="mb-5 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
          <p class="text-[14px] text-gray-500">{{ resultadosTexto }}</p>
          <select
            v-model="sortBy"
            class="w-full appearance-auto rounded-md border border-gray-200 bg-white py-2 pl-3 pr-8 text-[13px] text-gray-800 outline-none sm:w-auto"
          >
            <option value="precio-menor">Precio: menor a mayor</option>
            <option value="precio-mayor">Precio: mayor a menor</option>
            <option value="nombre">Nombre A-Z</option>
          </select>
        </div>

        <!-- Skeleton de carga -->
        <div v-if="cargando" class="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-3">
          <div
            v-for="i in 6"
            :key="i"
            class="h-[260px] animate-pulse rounded-xl bg-gray-100"
          />
        </div>

        <!-- Grid de productos -->
        <div
          v-else-if="productosFiltrados.length"
          class="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4"
        >
          <template v-for="producto in productosFiltrados" :key="producto.id">
            <ShopProductCardOTR
              v-if="esOtrTbr(producto.categoriaSlug)"
              :producto="producto"
            />
            <ShopProductCardPCR
              v-else
              :producto="producto"
            />
          </template>
        </div>

        <!-- Sin resultados -->
        <div
          v-else
          class="rounded-xl border border-dashed border-gray-200 bg-white p-10 text-center text-gray-500"
        >
          <i class="fa-solid fa-tire text-3xl mb-3 text-gray-300" />
          <p class="text-[14px]">No hay productos que coincidan con los filtros seleccionados.</p>
          <button class="mt-3 text-[13px] font-semibold text-brand-yellow hover:underline" @click="limpiarFiltros">
            Limpiar filtros
          </button>
        </div>
      </main>
    </div>

    <!-- Botón filtros mobile -->
    <button
      class="fixed bottom-5 right-5 z-20 flex h-12 w-12 items-center justify-center rounded-full bg-brand-yellow text-black shadow-lg lg:hidden"
      @click="sidebarOpen = !sidebarOpen"
    >
      <i class="fa-solid fa-filter text-lg" />
    </button>
  </div>
</template>
