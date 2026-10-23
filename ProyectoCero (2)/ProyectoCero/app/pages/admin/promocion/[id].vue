<script setup lang="ts">

definePageMeta({
  layout: false
})

const route = useRoute()
const config = useRuntimeConfig()

const token = ref('')
const guardando = ref(false)
const subiendoImagen = ref(false)

const error = ref('')
const mensaje = ref('')

const nuevo = computed(() => {
  return route.params.id === 'nueva'
})


// =====================================================
// FORMULARIO
// =====================================================

const hoy = new Date()
  .toISOString()
  .slice(0, 10)

const form = reactive({
  titulo: '',
  imagenUrl: '',
  fechaInicio: hoy,
  fechaFin: hoy,
  estado: 'ACTIVA',
  categoria: 'PROMOCION'
})


// =====================================================
// API
// =====================================================

function api(
  path: string,
  options: any = {}
) {

  return $fetch(path, {
    baseURL: config.public.apiBase,

    headers: {
      'X-Admin-Token': token.value
    },

    ...options
  })

}


// =====================================================
// INICIO
// =====================================================

onMounted(async () => {

  token.value =
    localStorage.getItem('admin-token') || ''

  if (!token.value) {

    await navigateTo('/admin')

    return
  }

  // Si estamos editando
  if (!nuevo.value) {

    await cargarPromocion()

  }

})


// =====================================================
// CARGAR PROMOCIÓN PARA EDITAR
// =====================================================

async function cargarPromocion() {

  error.value = ''

  try {

    const promociones =
      await api(
        '/admin/promociones'
      ) as any[]

    const id =
      Number(route.params.id)

    const promocion =
      promociones.find(
        p => Number(p.id) === id
      )

    if (!promocion) {

      error.value =
        'No se encontró la promoción.'

      return
    }


    form.titulo =
      promocion.titulo || ''


    form.imagenUrl =
      promocion.imagenUrl ||
      promocion.imagen_url ||
      ''


    form.fechaInicio =
      promocion.fechaInicio ||
      promocion.fecha_inicio ||
      ''


    form.fechaFin =
      promocion.fechaFin ||
      promocion.fecha_fin ||
      ''


    form.estado =
      promocion.estado ||
      'ACTIVA'
    
    form.categoria =
      promocion.categoria ||
      'PROMOCION'  

  } catch (e: any) {

    console.error(
      'Error cargando promoción:',
      e
    )

    error.value =
      e?.data?.message ||
      e?.data?.error ||
      'No se pudo cargar la promoción.'

  }

}


// =====================================================
// SUBIR IMAGEN
// =====================================================

async function subirImagen(
  event: Event
) {

  error.value = ''
  mensaje.value = ''

  const input =
    event.target as HTMLInputElement

  const archivo =
    input.files?.[0]

  if (!archivo) {
    return
  }


  // ===================================================
  // VALIDAR TIPO
  // ===================================================

  if (!archivo.type.startsWith('image/')) {

    error.value =
      'Solo puedes subir imágenes.'

    input.value = ''

    return
  }


  // ===================================================
  // VALIDAR TAMAÑO
  // ===================================================

  if (
    archivo.size >
    8 * 1024 * 1024
  ) {

    error.value =
      'La imagen no puede superar los 8 MB.'

    input.value = ''

    return
  }


  try {

    subiendoImagen.value = true


    const datos =
      new FormData()

    datos.append(
      'archivo',
      archivo
    )


    const respuesta =
      await $fetch(
        '/admin/archivos',
        {
          baseURL:
            config.public.apiBase,

          method: 'POST',

          headers: {
            'X-Admin-Token':
              token.value
          },

          body: datos
        }
      ) as any


    if (!respuesta?.url) {

      throw new Error(
        'El servidor no devolvió la URL de la imagen.'
      )

    }


    form.imagenUrl =
      respuesta.url


    mensaje.value =
      'Imagen subida correctamente.'

  } catch (e: any) {

    console.error(
      'Error subiendo imagen:',
      e
    )

    error.value =
      e?.data?.message ||
      e?.data?.error ||
      e?.message ||
      'No se pudo subir la imagen.'

  } finally {

    subiendoImagen.value = false

  }

}


// =====================================================
// GUARDAR PROMOCIÓN
// =====================================================

async function guardar() {

  error.value = ''
  mensaje.value = ''


  // ===================================================
  // VALIDACIONES
  // ===================================================

  if (!form.titulo.trim()) {

    error.value =
      'Ingresa el título de la promoción.'

    return
  }


  if (!form.imagenUrl.trim()) {

    error.value =
      'Debes seleccionar una imagen.'

    return
  }


  if (!form.fechaInicio) {

    error.value =
      'Selecciona la fecha de inicio.'

    return
  }


  if (!form.fechaFin) {

    error.value =
      'Selecciona la fecha de finalización.'

    return
  }


  if (
    form.fechaFin <
    form.fechaInicio
  ) {

    error.value =
      'La fecha final no puede ser anterior a la fecha inicial.'

    return
  }


  try {

    guardando.value = true


    const body = {
  titulo: form.titulo.trim(),
  imagenUrl: form.imagenUrl.trim(),
  fechaInicio: form.fechaInicio,
  fechaFin: form.fechaFin,
  estado: form.estado,
  categoria: form.categoria
}


    // =================================================
    // CREAR
    // =================================================

    if (nuevo.value) {

      await api(
        '/admin/promociones',
        {
          method: 'POST',
          body
        }
      )

      mensaje.value =
        'Promoción creada correctamente.'

    }


    // =================================================
    // EDITAR
    // =================================================

    else {

      await api(
        `/admin/promociones/${route.params.id}`,
        {
          method: 'PUT',
          body
        }
      )

      mensaje.value =
        'Promoción actualizada correctamente.'

    }


    // =================================================
    // VOLVER AL PANEL
    // =================================================

    setTimeout(() => {

      navigateTo('/admin')

    }, 700)

  } catch (e: any) {

    console.error(
      'Error guardando promoción:',
      e
    )

    error.value =
      e?.data?.message ||
      e?.data?.error ||
      e?.message ||
      'No se pudo guardar la promoción.'

  } finally {

    guardando.value = false

  }

}


// =====================================================
// VOLVER
// =====================================================

function volver() {

  navigateTo('/admin')

}

</script>


<template>

  <main
    class="min-h-screen bg-zinc-100 p-5 md:p-10"
  >

    <div
      class="mx-auto max-w-xl"
    >

      <!-- =================================================
           VOLVER AL PANEL
      ================================================== -->

      <button
        type="button"
        class="text-sm font-bold text-yellow-700 hover:text-yellow-800"
        @click="volver"
      >
        ← Volver al panel
      </button>


      <!-- =================================================
           FORMULARIO
      ================================================== -->

      <form
        class="mt-5 rounded-xl bg-white p-6 shadow-sm"
        @submit.prevent="guardar"
      >

        <!-- ENCABEZADO -->

        <p
          class="text-xs font-bold uppercase tracking-[.2em] text-yellow-600"
        >
          {{
            nuevo
              ? 'NUEVA PROMOCIÓN'
              : 'EDITAR PROMOCIÓN'
          }}
        </p>


        <h1
          class="mt-2 text-2xl font-black"
        >
          {{
            nuevo
              ? 'Subir promoción'
              : 'Editar promoción'
          }}
        </h1>


        <p
          class="mt-2 text-sm text-zinc-500"
        >
          {{
            nuevo
              ? 'Crea una nueva promoción para mostrarla en el carrusel público.'
              : 'Modifica los datos de esta promoción.'
          }}
        </p>


        <!-- =================================================
             TÍTULO
        ================================================== -->

        <label
          class="mt-6 block text-sm font-bold"
        >

          Título

          <input
            v-model="form.titulo"
            type="text"
            required
            maxlength="200"
            placeholder="Ej. Descuentos exclusivos"
            class="input"
          >

        </label>

        <label class="mt-5 block text-sm font-bold">
          Categoría

          <select
            v-model="form.categoria"
            class="input"
          >
            <option value="PROMOCION">
              Promoción
            </option>

            <option value="ANUNCIO">
              Anuncio
            </option>
          </select>
        </label>
        <!-- =================================================
             IMAGEN
        ================================================== -->

        <label
          class="mt-5 block text-sm font-bold"
        >

          Imagen de promoción

          <input
            type="file"
            accept="image/jpeg,image/png,image/webp,image/gif"
            class="input"
            :disabled="
              subiendoImagen ||
              guardando
            "
            @change="subirImagen"
          >

        </label>


        <p
          class="mt-2 text-xs text-zinc-500"
        >
          Formatos permitidos: JPG, JPEG, PNG, WEBP y GIF.
          Máximo 8 MB.
        </p>


        <!-- =================================================
             SUBIENDO
        ================================================== -->

        <div
          v-if="subiendoImagen"
          class="mt-3 rounded-lg bg-yellow-50 p-3 text-sm font-semibold text-yellow-700"
        >

          Subiendo imagen...

        </div>


        <!-- =================================================
             VISTA PREVIA
        ================================================== -->

        <div
          v-if="form.imagenUrl"
          class="mt-4 overflow-hidden rounded-lg border bg-zinc-50"
        >

          <div
            class="border-b px-3 py-2 text-xs font-bold uppercase text-zinc-500"
          >
            Vista previa
          </div>


          <img
            :src="form.imagenUrl"
            alt="Vista previa de la promoción"
            class="h-56 w-full object-cover"
          >

        </div>


        <!-- =================================================
             FECHAS
        ================================================== -->

        <div
          class="mt-5 grid grid-cols-1 gap-4 md:grid-cols-2"
        >

          <label
            class="block text-sm font-bold"
          >

            Fecha de inicio

            <input
              v-model="form.fechaInicio"
              type="date"
              required
              class="input"
            >

          </label>


          <label
            class="block text-sm font-bold"
          >

            Fecha de finalización

            <input
              v-model="form.fechaFin"
              type="date"
              required
              class="input"
            >

          </label>

        </div>


        <!-- =================================================
             VISIBILIDAD
        ================================================== -->

        <label
          class="mt-5 block text-sm font-bold"
        >

          Visibilidad

          <select
            v-model="form.estado"
            class="input"
          >

            <option value="ACTIVA">
              Mostrar
            </option>

            <option value="INACTIVA">
              Ocultar
            </option>

          </select>

        </label>


        <!-- =================================================
             ERROR
        ================================================== -->

        <div
          v-if="error"
          class="mt-5 rounded-lg bg-red-50 p-3 text-sm font-semibold text-red-700"
        >

          {{ error }}

        </div>


        <!-- =================================================
             MENSAJE
        ================================================== -->

        <div
          v-if="mensaje"
          class="mt-5 rounded-lg bg-green-50 p-3 text-sm font-semibold text-green-700"
        >

          {{ mensaje }}

        </div>


        <!-- =================================================
             BOTONES
        ================================================== -->

        <div
          class="mt-6 flex flex-col gap-3 sm:flex-row"
        >

          <button
            type="button"
            class="rounded-lg border border-zinc-300 bg-white px-5 py-3 text-sm font-bold text-zinc-700 hover:bg-zinc-50"
            :disabled="
              guardando ||
              subiendoImagen
            "
            @click="volver"
          >
            Cancelar
          </button>


          <button
            type="submit"
            class="flex-1 rounded-lg bg-brand-yellow px-5 py-3 text-sm font-extrabold disabled:cursor-not-allowed disabled:opacity-50"
            :disabled="
              guardando ||
              subiendoImagen ||
              !form.imagenUrl
            "
          >

            {{
              guardando
                ? 'Guardando...'
                : subiendoImagen
                  ? 'Subiendo imagen...'
                  : nuevo
                    ? 'Crear promoción'
                    : 'Guardar cambios'
            }}

          </button>

        </div>

      </form>

    </div>

  </main>

</template>


<style scoped>

.input {
  display: block;
  width: 100%;
  margin-top: 0.4rem;
  border: 1px solid #d4d4d8;
  border-radius: 0.5rem;
  padding: 0.7rem;
  background: white;
}

.input:focus {
  outline: none;
  border-color: #eab308;
  box-shadow:
    0 0 0 2px
    rgba(234, 179, 8, 0.15);
}

.input:disabled {
  cursor: not-allowed;
  background: #f4f4f5;
}

</style>