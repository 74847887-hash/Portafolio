<script setup lang="ts">

definePageMeta({
  layout: false
})

const route = useRoute()
const config = useRuntimeConfig()

// =====================================================
// ESTADO
// =====================================================
2
const token = ref('')

const esNuevo = computed(() => {
  return route.params.id === 'nuevo'
})

const guardando = ref(false)
const error = ref('')
const mensaje = ref('')

// =====================================================
// CATEGORÍAS
// =====================================================

const categorias = [
  'Autos',
  'Camionetas',
  'Camiones',
  'Industrial',
  'Agricola'
]

// =====================================================
// FORMULARIO
// =====================================================

const form = reactive<any>({

  id: null,

  codigoIngreso: '',

  nombre: '',

  // Marca sigue siendo texto
  marca: '',

  // Categoría usa select
  categoria: '',

  precio: null,

  medida: '',

  descripcion: '',

  tipo: 'PCR',

  disponibilidad: 'DISPONIBLE',

  estado: 'ACTIVO',

  fichaTecnicaUrl: '',

  // PCR
  indiceCarga: '',
  indiceVelocidad: '',
  treadwear: '',
  traccion: '',
  temperatura: '',

  // OTR / TBR
  profCocada: '',
  anchoBanda: '',
  eje: '',
  operacion: '',
  pliegues: null,
  construccion: '',
  disenio: '',

  videoUrl: '',

  imagenes: []

})

// =====================================================
// HEADERS
// =====================================================

const headers = computed(() => ({
  'X-Admin-Token': token.value
}))

// =====================================================
// API
// =====================================================

const api = (
  path: string,
  options: any = {}
) => {

  return $fetch(path, {

    baseURL: config.public.apiBase,

    headers: headers.value,

    ...options

  })

}

// =====================================================
// CARGAR PRODUCTO
// =====================================================

onMounted(async () => {

  token.value =
    localStorage.getItem('admin-token') || ''

  if (!token.value) {

    await navigateTo('/admin')

    return
  }

  // ===================================================
  // EDITAR
  // ===================================================

  if (!esNuevo.value) {

    try {

      const p: any =
        await api(
          `/admin/productos/${route.params.id}`
        )

      Object.assign(form, {

        ...p,

        codigoIngreso:
          p.codigo_ingreso ||
          p.codigoIngreso ||
          '',

        fichaTecnicaUrl:
          p.ficha_tecnica_url ||
          p.fichaTecnicaUrl ||
          '',

        marca:
          p.marca ||
          '',

        categoria:
          p.categoria ||
          '',

        imagenes:
          (p.imagenes || [])
            .map((imagen: any) => {

              if (
                typeof imagen === 'string'
              ) {

                return imagen

              }

              return imagen.url || ''

            })
            .filter(Boolean)

      })

    } catch (e: any) {

      console.error(
        'Error al cargar producto:',
        e
      )

      error.value =
        e?.data?.message ||
        e?.message ||
        'No se pudo cargar el producto.'

    }

  }

})

// =====================================================
// CAMBIO DE TIPO
// =====================================================

watch(
  () => form.tipo,

  (tipo) => {

    if (tipo === 'PCR') {

      Object.assign(form, {

        profCocada: '',
        anchoBanda: '',
        eje: '',
        operacion: '',
        pliegues: null,
        construccion: '',
        disenio: ''

      })

    } else {

      Object.assign(form, {

        indiceCarga: '',
        indiceVelocidad: '',
        treadwear: '',
        traccion: '',
        temperatura: ''

      })

    }

  }
)

// =====================================================
// GUARDAR PRODUCTO
// =====================================================

async function guardar() {

  error.value = ''
  mensaje.value = ''

  // ===================================================
  // VALIDAR NOMBRE
  // ===================================================

  if (!form.nombre?.trim()) {

    error.value =
      'Ingresa el nombre del producto.'

    return
  }

  // ===================================================
  // VALIDAR MARCA
  // ===================================================

  if (!form.marca?.trim()) {

    error.value =
      'Ingresa la marca del producto.'

    return
  }

  // ===================================================
  // VALIDAR CATEGORÍA
  // ===================================================

  if (!form.categoria?.trim()) {

    error.value =
      'Selecciona una categoría.'

    return
  }

  const categoriaValida =
    categorias.some(
      categoria =>
        categoria.toLowerCase() ===
        form.categoria
          .trim()
          .toLowerCase()
    )

  if (!categoriaValida) {

    error.value =
      'Selecciona una categoría válida.'

    return
  }

  // ===================================================
  // VALIDAR PRECIO
  // ===================================================

  if (
    form.precio === null ||
    form.precio === '' ||
    Number(form.precio) < 0
  ) {

    error.value =
      'Ingresa un precio válido.'

    return
  }

  // ===================================================
  // VALIDAR MEDIDA
  // ===================================================

  if (!form.medida?.trim()) {

    error.value =
      'Ingresa la medida del producto.'

    return
  }

  try {

    guardando.value = true

    // =================================================
    // NORMALIZAR DATOS
    // =================================================

    const body = {

      ...form,

      nombre:
        form.nombre.trim(),

      marca:
        form.marca.trim(),

      categoria:
        form.categoria.trim(),

      medida:
        form.medida.trim(),

      descripcion:
        form.descripcion?.trim() || '',

      precio:
        Number(form.precio),

      codigoIngreso:
        form.codigoIngreso?.trim() || '',

      fichaTecnicaUrl:
        form.fichaTecnicaUrl?.trim() || '',

      videoUrl:
        form.videoUrl?.trim() || '',

      imagenes:
        (form.imagenes || [])
          .map((url: string) =>
            url?.trim()
          )
          .filter(Boolean)

    }

    console.log(
      'Producto que se enviará:',
      body
    )

    // =================================================
    // CREAR
    // =================================================

    if (esNuevo.value) {

      await api(
        '/admin/productos',
        {
          method: 'POST',
          body
        }
      )

      mensaje.value =
        'Producto creado correctamente.'

    }

    // =================================================
    // EDITAR
    // =================================================

    else {

      await api(
        `/admin/productos/${route.params.id}`,
        {
          method: 'PUT',
          body
        }
      )

      mensaje.value =
        'Producto actualizado correctamente.'

    }

    // =================================================
    // MOSTRAR MENSAJE
    // =================================================

    await new Promise(
      resolve =>
        setTimeout(
          resolve,
          700
        )
    )

    await navigateTo('/admin')

  } catch (e: any) {

    console.error(
      'Error al guardar producto:',
      e
    )

    error.value =
      e?.data?.message ||
      e?.data?.error ||
      e?.message ||
      'No se pudo guardar el producto.'

  } finally {

    guardando.value = false

  }

}

// =====================================================
// IMÁGENES
// =====================================================

function agregarImagen() {

  if (
    form.imagenes.length >= 5
  ) {

    return
  }

  form.imagenes.push('')

}

function quitarImagen(
  i: number
) {

  form.imagenes.splice(
    i,
    1
  )

}

function principal(
  i: number
) {

  if (
    i < 0 ||
    i >= form.imagenes.length
  ) {

    return
  }

  const [url] =
    form.imagenes.splice(
      i,
      1
    )

  form.imagenes.unshift(url)

}

// =====================================================
// SUBIR ARCHIVO
// =====================================================

async function subirArchivo(
  archivo: File
) {

  const datos =
    new FormData()

  datos.append(
    'archivo',
    archivo
  )

  return await api(
    '/admin/archivos',
    {
      method: 'POST',
      body: datos
    }
  ) as any

}

// =====================================================
// SUBIR IMAGEN
// =====================================================

async function subirImagen(
  event: Event
) {

  try {

    error.value = ''

    const input =
      event.target as HTMLInputElement

    const archivo =
      input.files?.[0]

    if (!archivo) {

      return
    }

    // -----------------------------------------------
    // TIPO
    // -----------------------------------------------

    if (
      !archivo.type.startsWith(
        'image/'
      )
    ) {

      error.value =
        'El archivo seleccionado no es una imagen.'

      input.value = ''

      return
    }

    // -----------------------------------------------
    // TAMAÑO
    // -----------------------------------------------

    if (
      archivo.size >
      8 * 1024 * 1024
    ) {

      error.value =
        'La imagen no puede superar los 8 MB.'

      input.value = ''

      return
    }

    // -----------------------------------------------
    // MÁXIMO 5
    // -----------------------------------------------

    if (
      form.imagenes.length >= 5
    ) {

      error.value =
        'Solo puedes tener hasta 5 imágenes.'

      input.value = ''

      return
    }

    // -----------------------------------------------
    // SUBIR
    // -----------------------------------------------

    const respuesta =
      await subirArchivo(
        archivo
      )

    if (!respuesta?.url) {

      throw new Error(
        'El servidor no devolvió la URL de la imagen.'
      )

    }

    form.imagenes.push(
      respuesta.url
    )

  } catch (e: any) {

    console.error(
      'Error al subir imagen:',
      e
    )

    error.value =
      e?.data?.message ||
      e?.message ||
      'No se pudo subir la imagen.'

  }

}

// =====================================================
// SUBIR FICHA TÉCNICA
// =====================================================

async function subirFicha(
  event: Event
) {

  try {

    error.value = ''

    const input =
      event.target as HTMLInputElement

    const archivo =
      input.files?.[0]

    if (!archivo) {

      return
    }

    // -----------------------------------------------
    // SOLO PDF
    // -----------------------------------------------

    if (
      archivo.type !==
      'application/pdf'
    ) {

      error.value =
        'La ficha técnica debe ser un archivo PDF.'

      input.value = ''

      return
    }

    // -----------------------------------------------
    // SUBIR
    // -----------------------------------------------

    const respuesta =
      await subirArchivo(
        archivo
      )

    if (!respuesta?.url) {

      throw new Error(
        'El servidor no devolvió la URL del PDF.'
      )

    }

    form.fichaTecnicaUrl =
      respuesta.url

  } catch (e: any) {

    console.error(
      'Error al subir ficha técnica:',
      e
    )

    error.value =
      e?.data?.message ||
      e?.message ||
      'No se pudo subir la ficha técnica.'

  }

}

</script>


<template>

  <main
    class="min-h-screen bg-zinc-100 p-5 md:p-10"
  >

    <div
      class="mx-auto max-w-4xl"
    >

      <!-- VOLVER -->

      <NuxtLink
        to="/admin"
        class="text-sm font-bold text-yellow-700"
      >
        ← Volver al panel
      </NuxtLink>


      <!-- TÍTULO -->

      <h1
        class="mt-4 text-3xl font-black"
      >

        {{
          esNuevo
            ? 'Crear producto'
            : 'Editar producto'
        }}

      </h1>


      <!-- FORMULARIO -->

      <form
        class="mt-6 space-y-6 rounded-lg bg-white p-6 shadow-sm"
        @submit.prevent="guardar"
      >

        <!-- ID -->

        <div
          v-if="!esNuevo"
          class="rounded-lg bg-zinc-50 p-4"
        >

          <label>

            ID del producto

            <input
              :value="
                form.codigoIngreso ||
                ('#' + form.id)
              "
              readonly
              class="input bg-zinc-100"
            >

          </label>

        </div>


        <!-- DATOS PRINCIPALES -->

        <div
          class="grid gap-4 md:grid-cols-2"
        >

          <!-- NOMBRE -->

          <label>

            Nombre

            <input
              v-model="form.nombre"
              required
              class="input"
            >

          </label>


          <!-- TIPO -->

          <label>

            Tipo

            <select
              v-model="form.tipo"
              class="input"
            >

              <option value="PCR">
                PCR
              </option>

              <option value="OTR/TBR">
                OTR/TBR
              </option>

            </select>

          </label>


          <!-- MARCA -->

          <label>

            Marca

            <input
              v-model="form.marca"
              required
              placeholder="Ej. Michelin"
              class="input"
            >

            <span
              class="mt-1 block text-xs font-normal text-zinc-500"
            >
              Puedes escribir una marca nueva.
            </span>

          </label>


          <!-- CATEGORÍA -->

          <label>

            Categoría

            <select
              v-model="form.categoria"
              required
              class="input"
            >

              <option
                value=""
                disabled
              >
                Selecciona una categoría
              </option>

              <option
                v-for="categoria in categorias"
                :key="categoria"
                :value="categoria"
              >
                {{ categoria }}
              </option>

            </select>

          </label>


          <!-- PRECIO -->

          <label>

            Precio

            <input
              v-model="form.precio"
              required
              type="number"
              min="0"
              step=".01"
              class="input"
            >

          </label>


          <!-- MEDIDA -->

          <label>

            Medida

            <input
              v-model="form.medida"
              required
              class="input"
            >

          </label>


          <!-- DISPONIBILIDAD -->

          <label>

            Disponibilidad

            <select
              v-model="form.disponibilidad"
              class="input"
            >

              <option value="DISPONIBLE">
                Disponible
              </option>

              <option value="SIN_STOCK">
                Sin stock
              </option>

            </select>

          </label>


          <!-- ESTADO -->

          <label>

            Estado

            <select
              v-model="form.estado"
              class="input"
            >

              <option value="ACTIVO">
                Activo
              </option>

              <option value="OCULTO">
                Oculto
              </option>

            </select>

          </label>

        </div>


        <!-- DESCRIPCIÓN -->

        <label class="block">

          Descripción

          <textarea
            v-model="form.descripcion"
            class="input min-h-24"
          />

        </label>


        <!-- PCR -->

        <div
          v-if="form.tipo === 'PCR'"
          class="rounded-lg bg-zinc-50 p-4"
        >

          <h2 class="font-black">
            Especificaciones PCR
          </h2>

          <div
            class="mt-3 grid gap-4 md:grid-cols-3"
          >

            <label>

              Índice de carga

              <input
                v-model="form.indiceCarga"
                class="input"
              >

            </label>


            <label>

              Índice de velocidad

              <input
                v-model="form.indiceVelocidad"
                class="input"
              >

            </label>


            <label>

              Treadwear

              <input
                v-model="form.treadwear"
                class="input"
              >

            </label>


            <label>

              Tracción

              <input
                v-model="form.traccion"
                class="input"
              >

            </label>


            <label>

              Temperatura

              <input
                v-model="form.temperatura"
                class="input"
              >

            </label>

          </div>

        </div>


        <!-- OTR / TBR -->

        <div
          v-else
          class="rounded-lg bg-zinc-50 p-4"
        >

          <h2 class="font-black">
            Especificaciones OTR / TBR
          </h2>

          <div
            class="mt-3 grid gap-4 md:grid-cols-3"
          >

            <label>

              Profundidad de cocada

              <input
                v-model="form.profCocada"
                class="input"
              >

            </label>


            <label>

              Ancho de banda

              <input
                v-model="form.anchoBanda"
                class="input"
              >

            </label>


            <label>

              Eje

              <input
                v-model="form.eje"
                class="input"
              >

            </label>


            <label>

              Operación

              <input
                v-model="form.operacion"
                class="input"
              >

            </label>


            <label>

              Pliegues

              <input
                v-model="form.pliegues"
                type="number"
                class="input"
              >

            </label>


            <label>

              Construcción

              <input
                v-model="form.construccion"
                class="input"
              >

            </label>


            <label>

              Diseño

              <input
                v-model="form.disenio"
                class="input"
              >

            </label>

          </div>

        </div>


        <!-- IMÁGENES -->

        <div>

          <h2 class="font-black">
            Imágenes del producto
          </h2>

          <input
            v-if="form.imagenes.length < 5"
            class="mt-3 block text-sm"
            type="file"
            accept="image/*"
            @change="subirImagen"
          >


          <div
            v-for="(url, i) in form.imagenes"
            :key="i"
            class="mt-3 flex flex-wrap items-center gap-2"
          >

            <img
              v-if="url"
              :src="url"
              class="h-16 w-16 rounded object-cover"
            >


            <input
              v-model="form.imagenes[i]"
              required
              placeholder="URL o ruta de imagen"
              class="input min-w-[220px] flex-1"
            >


            <button
              type="button"
              class="text-sm font-bold text-yellow-700"
              @click="principal(i)"
            >
              Principal
            </button>


            <button
              type="button"
              class="text-sm font-bold text-red-600"
              @click="quitarImagen(i)"
            >
              Quitar
            </button>

          </div>


          <button
            v-if="form.imagenes.length < 5"
            type="button"
            class="mt-3 text-sm font-bold text-yellow-700"
            @click="agregarImagen"
          >
            + Agregar URL
          </button>

        </div>


        <!-- FICHA TÉCNICA -->

        <div>

          <h2 class="font-black">
            Ficha técnica PDF
          </h2>


          <input
            class="mt-3 block text-sm"
            type="file"
            accept="application/pdf"
            @change="subirFicha"
          >


          <div
            v-if="form.fichaTecnicaUrl"
            class="mt-3 flex flex-wrap items-center gap-3 rounded-lg bg-zinc-50 p-3 text-sm"
          >

            <a
              :href="form.fichaTecnicaUrl"
              target="_blank"
              class="font-bold text-yellow-700"
            >
              Ver PDF actual
            </a>


            <button
              type="button"
              class="font-bold text-red-600"
              @click="
                form.fichaTecnicaUrl = ''
              "
            >
              Eliminar PDF
            </button>

          </div>

        </div>


        <!-- ERROR -->

        <div
          v-if="error"
          class="rounded-lg bg-red-50 p-4 text-sm font-semibold text-red-700"
        >
          {{ error }}
        </div>


        <!-- MENSAJE -->

        <div
          v-if="mensaje"
          class="rounded-lg bg-green-50 p-4 text-sm font-semibold text-green-700"
        >
          {{ mensaje }}
        </div>


        <!-- GUARDAR -->

        <button
          type="submit"
          :disabled="guardando"
          class="rounded-lg bg-brand-yellow px-5 py-3 text-sm font-extrabold disabled:cursor-not-allowed disabled:opacity-50"
        >

          {{
            guardando
              ? 'Guardando...'
              : 'Guardar producto'
          }}

        </button>

      </form>

    </div>

  </main>

</template>


<style scoped>

.input {

  display: block;

  width: 100%;

  margin-top: .35rem;

  border: 1px solid #d4d4d8;

  border-radius: .5rem;

  padding: .7rem;

  font-size: .9rem;

  background: white;

}

.input:focus {

  outline: none;

  border-color: #eab308;

  box-shadow:
    0 0 0 2px
    rgba(234, 179, 8, 0.15);

}

label {

  font-size: .85rem;

  font-weight: 700;

}

</style>