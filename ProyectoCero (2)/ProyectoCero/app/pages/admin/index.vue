<script setup lang="ts">

definePageMeta({
  layout: false
})

const config = useRuntimeConfig()

const token = ref('')
const usuario = ref('')
const password = ref('')

const autenticado = computed(() => Boolean(token.value))

const tab = ref<'productos' | 'promociones' | 'contacto'>('productos')

const productos = ref<any[]>([])
const promociones = ref<any[]>([])

const contacto = ref({
  telefono: '',
  whatsapp: '',
  correo: '',
  horario: ''
})

const q = ref('')
const aviso = ref('')


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
) =>
  $fetch(path, {
    baseURL: config.public.apiBase,
    headers: headers.value,
    ...options
  })


// =====================================================
// INICIO
// =====================================================

onMounted(async () => {

  token.value =
    localStorage.getItem('admin-token') || ''

  if (token.value) {
    await cargar()
  }

})


// =====================================================
// LOGIN
// =====================================================

async function entrar() {

  aviso.value = ''

  try {

    const sesion: any =
      await $fetch('/admin/auth/login', {
        baseURL: config.public.apiBase,
        method: 'POST',
        body: {
          usuario: usuario.value.trim(),
          password: password.value
        }
      })

    if (!sesion?.token) {
      throw new Error(
        'El backend no devolvió un token válido.'
      )
    }

    token.value = sesion.token

    localStorage.setItem(
      'admin-token',
      token.value
    )

    password.value = ''

    await cargar()

  } catch (error: any) {

    localStorage.removeItem(
      'admin-token'
    )

    token.value = ''

    aviso.value =
      error?.data?.message ||
      error?.data?.error ||
      'Usuario o contraseña incorrectos.'

  }

}


// =====================================================
// CARGAR INFORMACIÓN
// =====================================================

async function cargar() {

  try {

    aviso.value = ''

    productos.value =
      await api(
        '/admin/productos' +
        (
          q.value
            ? `?q=${encodeURIComponent(q.value)}`
            : ''
        )
      ) as any[]

    promociones.value =
      await api(
        '/admin/promociones'
      ) as any[]

    contacto.value =
      await api(
        '/admin/contacto'
      ) as any

  } catch (error: any) {

    localStorage.removeItem(
      'admin-token'
    )

    token.value = ''

    aviso.value =
      error?.data?.message ||
      'La sesión expiró o no se pudo conectar con el backend.'

  }

}


// =====================================================
// PRODUCTOS
// =====================================================

async function cambiarEstadoProducto(
  producto: any
) {

  try {

    await api(
      `/admin/productos/${producto.id}/estado`,
      {
        method: 'PATCH',
        body: {
          estado:
            producto.estado === 'ACTIVO'
              ? 'OCULTO'
              : 'ACTIVO'
        }
      }
    )

    await cargar()

  } catch (error: any) {

    aviso.value =
      error?.data?.message ||
      'No se pudo cambiar el estado del producto.'

  }

}


async function eliminarProducto(
  id: number
) {

  if (
    !confirm(
      '¿Eliminar este producto definitivamente?'
    )
  ) {
    return
  }

  try {

    await api(
      `/admin/productos/${id}`,
      {
        method: 'DELETE'
      }
    )

    await cargar()

  } catch (error: any) {

    aviso.value =
      error?.data?.message ||
      'No se pudo eliminar el producto.'

  }

}


// =====================================================
// PROMOCIONES
// =====================================================

async function eliminarPromo(
  id: number
) {

  if (
    !confirm(
      '¿Eliminar esta promoción?'
    )
  ) {
    return
  }

  try {

    await api(
      `/admin/promociones/${id}`,
      {
        method: 'DELETE'
      }
    )

    await cargar()

  } catch (error: any) {

    aviso.value =
      error?.data?.message ||
      'No se pudo eliminar la promoción.'

  }

}


// =====================================================
// CONTACTO
// =====================================================

async function guardarContacto() {

  try {

    await api(
      '/admin/contacto',
      {
        method: 'PUT',
        body: contacto.value
      }
    )

    aviso.value =
      'Información de contacto guardada correctamente.'

  } catch (error: any) {

    aviso.value =
      error?.data?.message ||
      'No se pudo guardar la información de contacto.'

  }

}


// =====================================================
// CERRAR SESIÓN
// =====================================================

function salir() {

  localStorage.removeItem(
    'admin-token'
  )

  token.value = ''

  usuario.value = ''

  password.value = ''

  productos.value = []

  promociones.value = []

  contacto.value = {
    telefono: '',
    whatsapp: '',
    correo: '',
    horario: ''
  }

  tab.value = 'productos'

  aviso.value = ''

}

</script>


<template>

  <div
    class="min-h-screen bg-zinc-100 text-zinc-900"
  >

    <!-- =================================================
         HEADER
    ================================================== -->

    <header
      class="sticky top-0 z-10 flex items-center justify-between bg-zinc-950 px-5 py-4 text-white md:px-10"
    >

      <NuxtLink
        to="/"
        class="font-black tracking-tight"
      >
        ANDES

        <span class="text-brand-yellow">
          ADMIN
        </span>

      </NuxtLink>


      <button
        v-if="autenticado"
        type="button"
        class="text-sm font-semibold text-zinc-300 hover:text-white"
        @click="salir"
      >
        Cerrar sesión
      </button>

    </header>


    <!-- =================================================
         LOGIN
    ================================================== -->

    <main
      v-if="!autenticado"
      class="mx-auto max-w-md p-5 pt-16"
    >

      <form
        class="rounded-lg bg-white p-6 shadow-sm"
        @submit.prevent="entrar"
      >

        <p
          class="text-xs font-bold uppercase tracking-[.2em] text-yellow-600"
        >
          ACCESO ADMINISTRATIVO
        </p>


        <h1
          class="mt-2 text-2xl font-black"
        >
          Inicia sesión
        </h1>


        <p
          class="mt-2 text-sm text-zinc-500"
        >
          Ingresa tus credenciales para acceder al panel administrativo.
        </p>


        <!-- USUARIO -->

        <label
          class="mt-5 block text-sm font-bold"
        >

          Usuario

          <input
            v-model="usuario"
            type="text"
            autocomplete="username"
            required
            class="mt-2 w-full rounded-lg border p-3 font-normal outline-none focus:border-yellow-500"
            placeholder="Ingresa tu usuario"
          >

        </label>


        <!-- CONTRASEÑA -->

        <label
          class="mt-4 block text-sm font-bold"
        >

          Contraseña

          <input
            v-model="password"
            type="password"
            autocomplete="current-password"
            required
            class="mt-2 w-full rounded-lg border p-3 font-normal outline-none focus:border-yellow-500"
            placeholder="Ingresa tu contraseña"
          >

        </label>


        <!-- BOTÓN -->

        <button
          type="submit"
          class="mt-6 w-full rounded-lg bg-brand-yellow px-5 py-3 text-sm font-extrabold transition hover:opacity-90"
        >
          Entrar
        </button>


        <!-- ERROR -->

        <p
          v-if="aviso"
          class="mt-3 text-sm text-red-700"
        >
          {{ aviso }}
        </p>

      </form>

    </main>


    <!-- =================================================
         PANEL ADMINISTRATIVO
    ================================================== -->

    <div
      v-else
      class="mx-auto max-w-7xl p-5 md:p-10"
    >

      <!-- TÍTULO -->

      <div class="mb-8">

        <p
          class="text-xs font-bold tracking-[.2em] text-yellow-600"
        >
          GESTIÓN DE CATÁLOGO
        </p>

        <h1
          class="mt-1 text-3xl font-black"
        >
          Panel administrativo
        </h1>

      </div>


      <!-- =================================================
           NAVEGACIÓN
      ================================================== -->

      <nav
        class="mb-6 flex gap-2 border-b border-zinc-200"
      >

        <button
          type="button"
          class="border-b-2 px-4 py-3 text-sm font-bold"
          :class="
            tab === 'productos'
              ? 'border-brand-yellow text-zinc-950'
              : 'border-transparent text-zinc-500'
          "
          @click="tab = 'productos'"
        >
          Productos
        </button>


        <button
          type="button"
          class="border-b-2 px-4 py-3 text-sm font-bold"
          :class="
            tab === 'promociones'
              ? 'border-brand-yellow text-zinc-950'
              : 'border-transparent text-zinc-500'
          "
          @click="tab = 'promociones'"
        >
          Promociones
        </button>


        <button
          type="button"
          class="border-b-2 px-4 py-3 text-sm font-bold"
          :class="
            tab === 'contacto'
              ? 'border-brand-yellow text-zinc-950'
              : 'border-transparent text-zinc-500'
          "
          @click="tab = 'contacto'"
        >
          Contacto
        </button>

      </nav>


      <!-- =================================================
           PRODUCTOS
      ================================================== -->

      <section
        v-if="tab === 'productos'"
        class="space-y-5"
      >

        <div
          class="flex flex-wrap gap-3"
        >

          <input
            v-model="q"
            placeholder="Buscar por ID, código o nombre"
            class="rounded-lg border px-4 py-2.5 text-sm"
            @keyup.enter="cargar"
          >


          <button
            type="button"
            class="rounded-lg bg-zinc-900 px-4 py-2.5 text-sm font-bold text-white"
            @click="cargar"
          >
            Buscar
          </button>


          <NuxtLink
            to="/admin/producto/nuevo"
            class="ml-auto rounded-lg bg-brand-yellow px-4 py-2.5 text-sm font-extrabold"
          >
            + Crear producto
          </NuxtLink>

        </div>


        <div
          class="overflow-hidden rounded-lg bg-white shadow-sm"
        >

          <div class="overflow-x-auto">

            <table
              class="w-full min-w-[900px] text-left text-sm"
            >

              <thead
                class="bg-zinc-50 text-xs uppercase text-zinc-500"
              >

                <tr>

                  <th class="p-4">
                    ID
                  </th>

                  <th>
                    Producto
                  </th>
                  <th>
                    Tipo
                  </th>

                  <th>
                    Disponibilidad
                  </th>

                  <th>
                    Estado
                  </th>

                  <th class="p-4">
                    Acciones
                  </th>

                </tr>

              </thead>


              <tbody>

                <tr
                  v-for="p in productos"
                  :key="p.id"
                  class="border-t"
                >

                  <td class="p-4 font-bold">
                    {{
                      p.codigo_ingreso ||
                      p.codigoIngreso ||
                      ('#' + p.id)
                    }}
                  </td>


                  <td>

                    <b>
                      {{ p.nombre }}
                    </b>

                    <br>

                    <span
                      class="text-xs text-zinc-500"
                    >
                      {{ p.marca }}
                      ·
                      {{ p.medida }}
                    </span>

                  </td>


                  <td>
                    {{ p.tipo }}
                  </td>


                  <td>
                    {{ p.disponibilidad }}
                  </td>


                  <td>

                    <span
                      class="rounded-full px-2 py-1 text-xs font-bold"
                      :class="
                        p.estado === 'ACTIVO'
                          ? 'bg-green-100 text-green-700'
                          : 'bg-zinc-200 text-zinc-600'
                      "
                    >
                      {{ p.estado }}
                    </span>

                  </td>


                  <td
                    class="whitespace-nowrap p-4"
                  >

                    <NuxtLink
                      :to="`/admin/producto/${p.id}`"
                      class="mr-3 font-bold text-yellow-700"
                    >
                      Editar
                    </NuxtLink>


                    <button
                      type="button"
                      class="mr-3 font-bold"
                      @click="cambiarEstadoProducto(p)"
                    >
                      {{
                        p.estado === 'ACTIVO'
                          ? 'Ocultar'
                          : 'Mostrar'
                      }}
                    </button>


                    <button
                      type="button"
                      class="font-bold text-red-600"
                      @click="eliminarProducto(p.id)"
                    >
                      Eliminar
                    </button>

                  </td>

                </tr>


                <tr
                  v-if="productos.length === 0"
                >

                  <td
                    colspan="6"
                    class="p-10 text-center text-sm text-zinc-500"
                  >
                    No hay productos para mostrar.
                  </td>

                </tr>

              </tbody>

            </table>

          </div>

        </div>

      </section>


      <!-- =================================================
           PROMOCIONES
      ================================================== -->

      <section
        v-if="tab === 'promociones'"
        class="space-y-5"
      >

        <!-- CREAR PROMOCIÓN -->
        <NuxtLink
          to="/admin/promocion/nueva"
          class="inline-block rounded-lg bg-brand-yellow px-4 py-2.5 text-sm font-extrabold"
        >
          + Subir promoción
        </NuxtLink>

        <!-- LISTADO -->
        <div
          v-if="promociones.length"
          class="grid gap-4 md:grid-cols-2"
        >

          <article
            v-for="p in promociones"
            :key="p.id"
            class="overflow-hidden rounded-lg bg-white shadow-sm"
          >

            <img
              :src="p.imagen_url || p.imagenUrl || ''"
              :alt="p.titulo || 'Promoción'"
              class="h-40 w-full object-cover"
            >

            <div class="p-4">

              <b>
                {{ p.titulo }}
              </b>

              <p class="mt-1 text-xs text-zinc-500">
                {{ p.fecha_inicio || p.fechaInicio || '' }}
                -
                {{ p.fecha_fin || p.fechaFin || '' }}
                ·
                {{ p.estado }}
              </p>

              <!-- EDITAR -->
              <NuxtLink
                :to="`/admin/promocion/${p.id}`"
                class="mr-4 mt-4 inline-block text-sm font-bold text-yellow-700"
              >
                Editar
              </NuxtLink>

              <!-- ELIMINAR -->
              <button
                type="button"
                class="text-sm font-bold text-red-600"
                @click="eliminarPromo(p.id)"
              >
                Eliminar
              </button>

            </div>

          </article>

        </div>

        <!-- SIN PROMOCIONES -->
        <div
          v-else
          class="rounded-lg bg-white p-10 text-center text-sm text-zinc-500 shadow-sm"
        >
          No hay promociones registradas.
        </div>

      </section>


      <!-- =================================================
           CONTACTO
      ================================================== -->

      <section
        v-if="tab === 'contacto'"
        class="max-w-xl rounded-lg bg-white p-6 shadow-sm"
      >

        <h2
          class="text-xl font-black"
        >
          Información de contacto
        </h2>


        <p
          class="mb-5 mt-1 text-sm text-zinc-500"
        >
          Estos datos alimentan Contacto y Footer.
        </p>


        <!-- TELÉFONO -->

        <label
          class="block text-sm font-bold"
        >

          Teléfono

          <input
            v-model="contacto.telefono"
            type="text"
            class="mt-2 w-full rounded-lg border p-3 font-normal"
          >

        </label>


        <!-- WHATSAPP -->

        <label
          class="mt-4 block text-sm font-bold"
        >

          WhatsApp

          <input
            v-model="contacto.whatsapp"
            type="text"
            class="mt-2 w-full rounded-lg border p-3 font-normal"
          >

        </label>


        <!-- CORREO -->

        <label
          class="mt-4 block text-sm font-bold"
        >

          Correo

          <input
            v-model="contacto.correo"
            type="email"
            class="mt-2 w-full rounded-lg border p-3 font-normal"
          >

        </label>


        <!-- HORARIO -->

        <label
          class="mt-4 block text-sm font-bold"
        >

          Horario

          <input
            v-model="contacto.horario"
            type="text"
            class="mt-2 w-full rounded-lg border p-3 font-normal"
          >

        </label>


        <!-- GUARDAR -->

        <button
          type="button"
          class="mt-6 rounded-lg bg-brand-yellow px-5 py-3 text-sm font-extrabold"
          @click="guardarContacto"
        >
          Guardar cambios
        </button>


        <p
          v-if="aviso"
          class="mt-3 text-sm"
          :class="
            aviso.includes('correctamente')
              ? 'text-green-700'
              : 'text-red-700'
          "
        >
          {{ aviso }}
        </p>

      </section>

    </div>

  </div>

</template>