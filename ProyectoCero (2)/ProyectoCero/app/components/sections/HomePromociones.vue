<script setup lang="ts">

type Promocion = {
  id: number
  titulo: string
  imagenUrl: string
  fechaInicio: string
  fechaFin: string
  estado?: string
  categoria?: 'PROMOCION' | 'ANUNCIO'
}

const config = useRuntimeConfig()

const actual = ref(0)
const promociones = ref<Promocion[]>([])
const cargando = ref(true)

const promocionSeleccionada = ref<Promocion | null>(null)
const errorImagen = ref(false)

let timer: ReturnType<typeof setInterval> | undefined
let carruselTimer: ReturnType<typeof setInterval> | undefined


// =====================================================
// RESOLVER URL DE IMAGEN
// =====================================================

function resolverImagen(url: string) {

  if (!url) {
    return ''
  }

  // URL completa
  if (
    url.startsWith('http://') ||
    url.startsWith('https://') ||
    url.startsWith('data:')
  ) {
    return url
  }

  // Ruta relativa
  return `${config.public.apiBase}${url.startsWith('/') ? '' : '/'}${url}`
}


// =====================================================
// CARGAR PROMOCIONES DESDE EL BACKEND
// =====================================================

async function cargarPromociones() {

  try {

    const respuesta = await $fetch<Promocion[]>(
      '/promociones/activas',
      {
        baseURL: config.public.apiBase,

        // Evita utilizar una respuesta antigua
        cache: 'no-store'
      }
    )

    promociones.value = Array.isArray(respuesta)
      ? respuesta.filter(p => p.imagenUrl)
      : []

    // Mantener el índice válido
    if (
      promociones.value.length === 0 ||
      actual.value >= promociones.value.length
    ) {
      actual.value = 0
    }

  } catch (error) {

    console.error(
      'No se pudieron cargar las promociones:',
      error
    )

  } finally {

    cargando.value = false

  }
}


// =====================================================
// ELEMENTOS DEL CARRUSEL
// =====================================================

const items = computed(() => {
  return promociones.value
})


// =====================================================
// CONTROLES
// =====================================================

const tieneControles = computed(() => {
  return items.value.length > 1
})


function ir(index: number) {

  if (!items.value.length) {
    return
  }

  actual.value =
    (index + items.value.length) %
    items.value.length
}


// =====================================================
// ABRIR PROMOCIÓN EN PANTALLA COMPLETA
// =====================================================

function abrirPromocion(promo: Promocion) {

  console.log(
    'Promoción seleccionada:',
    promo
  )

  console.log(
    'Imagen:',
    resolverImagen(promo.imagenUrl)
  )

  errorImagen.value = false

  promocionSeleccionada.value = promo
}


// =====================================================
// CERRAR PANTALLA COMPLETA
// =====================================================

function cerrarPromocion() {

  promocionSeleccionada.value = null

  errorImagen.value = false
}


// =====================================================
// ERROR AL CARGAR IMAGEN
// =====================================================

function imagenNoCargada() {

  console.error(
    'No se pudo cargar la imagen:',
    promocionSeleccionada.value?.imagenUrl
  )

  errorImagen.value = true
}


// =====================================================
// CARGA INICIAL + ACTUALIZACIÓN AUTOMÁTICA
// =====================================================

onMounted(async () => {

  // Primera carga
  await cargarPromociones()

  // Actualizar desde el backend cada 10 segundos
  timer = setInterval(() => {

    cargarPromociones()

  }, 10000)

})


// =====================================================
// LIMPIAR TEMPORIZADORES
// =====================================================

onBeforeUnmount(() => {

  if (timer) {
    clearInterval(timer)
  }

  if (carruselTimer) {
    clearInterval(carruselTimer)
  }

})


// =====================================================
// CARRUSEL AUTOMÁTICO
// =====================================================

watch(
  () => items.value.length,

  () => {

    if (carruselTimer) {
      clearInterval(carruselTimer)
    }

    if (items.value.length > 1) {

      carruselTimer = setInterval(() => {

        ir(actual.value + 1)

      }, 5000)

    }

  },

  {
    immediate: true
  }
)

</script>


<template>

  <!-- =====================================================
       CARRUSEL DE PROMOCIONES / ANUNCIOS
  ====================================================== -->

  <section
    v-if="items.length"
    id="promociones"
    class="bg-white py-10"
  >

    <div class="mx-auto max-w-7xl px-6">

      <div
        class="relative overflow-hidden rounded-lg bg-zinc-900"
      >

        <!-- =================================================
             CONTENEDOR DEL CARRUSEL
        ================================================== -->

        <div
          class="relative aspect-[16/7] min-h-[200px] md:min-h-[300px]"
        >

          <!-- =================================================
               ELEMENTOS
          ================================================== -->

          <article
            v-for="(promo, index) in items"
            :key="promo.id"
            class="absolute inset-0 cursor-pointer transition-opacity duration-500"
            :class="
              index === actual
                ? 'opacity-100'
                : 'pointer-events-none opacity-0'
            "
            @click="abrirPromocion(promo)"
          >

            <!-- IMAGEN -->

            <img
              :src="resolverImagen(promo.imagenUrl)"
              :alt="promo.titulo"
              class="h-full w-full object-cover"
            >

            <!-- OSCURECIMIENTO -->

            <div
              class="absolute inset-0 bg-gradient-to-r from-black/70 via-black/30 to-transparent"
            />

            <!-- INFORMACIÓN -->

            <div
              class="absolute bottom-0 left-0 max-w-xl p-6 text-white md:p-9"
            >

              <!-- CATEGORÍA -->

              <p
                class="text-xs font-black uppercase tracking-[.2em] text-brand-yellow"
              >
                {{
                  promo.categoria === 'ANUNCIO'
                    ? 'Anuncio'
                    : 'Promoción activa'
                }}
              </p>

              <!-- TÍTULO -->

              <h2
                class="mt-2 text-2xl font-black leading-tight md:text-4xl"
              >
                {{ promo.titulo }}
              </h2>

              <!-- INDICACIÓN -->

              <p
                class="mt-3 text-sm font-semibold text-white/90"
              >
                Haz clic para ver
              </p>

            </div>

          </article>

        </div>


        <!-- =================================================
             BOTÓN ANTERIOR
        ================================================== -->

        <button
          v-if="tieneControles"
          type="button"
          class="absolute left-3 top-1/2 z-20 grid h-10 w-10 -translate-y-1/2 place-items-center rounded-full bg-white/90 text-zinc-950 shadow"
          aria-label="Elemento anterior"
          @click.stop="ir(actual - 1)"
        >

          <i class="fa-solid fa-chevron-left" />

        </button>


        <!-- =================================================
             BOTÓN SIGUIENTE
        ================================================== -->

        <button
          v-if="tieneControles"
          type="button"
          class="absolute right-3 top-1/2 z-20 grid h-10 w-10 -translate-y-1/2 place-items-center rounded-full bg-white/90 text-zinc-950 shadow"
          aria-label="Elemento siguiente"
          @click.stop="ir(actual + 1)"
        >

          <i class="fa-solid fa-chevron-right" />

        </button>


        <!-- =================================================
             INDICADORES
        ================================================== -->

        <div
          v-if="tieneControles"
          class="absolute bottom-4 right-5 z-20 flex gap-2"
        >

          <button
            v-for="(_, index) in items"
            :key="index"
            type="button"
            class="h-2.5 w-2.5 rounded-full"
            :class="
              index === actual
                ? 'bg-brand-yellow'
                : 'bg-white/70'
            "
            :aria-label="`Ir al elemento ${index + 1}`"
            @click.stop="ir(index)"
          />

        </div>

      </div>

    </div>

  </section>


  <!-- =====================================================
       ELEMENTO EN PANTALLA COMPLETA
  ====================================================== -->

  <Teleport to="body">

    <div
      v-if="promocionSeleccionada"
      class="fixed inset-0 z-[99999] flex min-h-screen items-center justify-center bg-black"
    >

      <!-- =================================================
           BOTÓN VOLVER
      ================================================== -->

      <button
        type="button"
        class="absolute left-5 top-5 z-[100000] rounded-lg bg-white px-5 py-3 font-bold text-zinc-950 shadow-lg transition hover:bg-brand-yellow"
        @click="cerrarPromocion"
      >
        ← Volver al inicio
      </button>


      <!-- =================================================
           IMAGEN EN PANTALLA COMPLETA
      ================================================== -->

      <div
        class="flex h-screen w-screen items-center justify-center bg-black"
      >

        <img
          v-if="!errorImagen"
          :src="
            resolverImagen(
              promocionSeleccionada.imagenUrl
            )
          "
          :alt="promocionSeleccionada.titulo"
          class="max-h-screen max-w-full object-contain"
          @error="imagenNoCargada"
        >


        <!-- =================================================
             ERROR DE IMAGEN
        ================================================== -->

        <div
          v-else
          class="flex max-w-lg flex-col items-center px-6 text-center text-white"
        >

          <p
            class="text-xl font-black text-red-400"
          >
            No se pudo cargar la imagen.
          </p>

          <p
            class="mt-3 break-all text-sm text-white/70"
          >
            {{
              resolverImagen(
                promocionSeleccionada.imagenUrl
              )
            }}
          </p>

        </div>

      </div>


      <!-- =================================================
           INFORMACIÓN INFERIOR
      ================================================== -->

      <div
        class="pointer-events-none absolute inset-x-0 bottom-0 z-40 bg-gradient-to-t from-black/90 via-black/40 to-transparent p-6 pt-24 text-white md:p-10 md:pt-32"
      >

        <div
          class="mx-auto max-w-7xl"
        >

          <!-- CATEGORÍA -->

          <p
            class="text-xs font-black uppercase tracking-[.2em] text-brand-yellow"
          >
            {{
              promocionSeleccionada.categoria === 'ANUNCIO'
                ? 'Anuncio'
                : 'Promoción activa'
            }}
          </p>


          <!-- TÍTULO -->

          <h2
            class="mt-2 text-2xl font-black md:text-4xl"
          >
            {{ promocionSeleccionada.titulo }}
          </h2>


          <!-- FECHAS -->

          <p
            v-if="
              promocionSeleccionada.fechaInicio &&
              promocionSeleccionada.fechaFin
            "
            class="mt-2 text-sm text-white/80"
          >

            Válida desde
            {{ promocionSeleccionada.fechaInicio }}

            hasta
            {{ promocionSeleccionada.fechaFin }}

          </p>

        </div>

      </div>

    </div>

  </Teleport>

</template>