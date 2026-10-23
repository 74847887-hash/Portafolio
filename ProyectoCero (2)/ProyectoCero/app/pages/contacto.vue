<script setup lang="ts">
type ContactForm = {
  nombre: string
  correo: string
  telefono: string
  asunto: string
  mensaje: string
  privacidad: boolean
}

const form = reactive<ContactForm>({
  nombre: '',
  correo: '',
  telefono: '',
  asunto: '',
  mensaje: '',
  privacidad: false,
})

const touched = reactive<Record<keyof ContactForm, boolean>>({
  nombre: false,
  correo: false,
  telefono: false,
  asunto: false,
  mensaje: false,
  privacidad: false,
})

const submitted = ref(false)
const successMessage = ref('')

const config = useRuntimeConfig()

const datosContacto = ref({
  telefono: '',
  whatsapp: '',
  correo: '',
  horario: '',
})

onMounted(async () => {
  try {
    datosContacto.value = await $fetch('/informacion-contacto', {
      baseURL: config.public.apiBase,
    }) as any
  } catch {
    datosContacto.value = {
      telefono: '+51 959 123 456',
      whatsapp: '+51 959 123 456',
      correo: 'ventas@andesllantas.com',
      horario: 'Lunes a Sábado 8:00 am - 6:00 pm',
    }
  }
})

const errors = computed(() => {
  const result: Partial<Record<keyof ContactForm, string>> = {}

  const cleanPhone = form.telefono.replace(/\D/g, '')

  if (form.nombre.trim().length < 3) {
    result.nombre = 'Ingresa al menos 3 caracteres.'
  }

  if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(form.nombre.trim())) {
    result.nombre = 'Usa solo letras y espacios.'
  }

  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.correo.trim())) {
    result.correo = 'Ingresa un correo válido.'
  }

  if (cleanPhone.length !== 9) {
    result.telefono = 'Ingresa un celular peruano de 9 dígitos.'
  }

  if (!form.asunto) {
    result.asunto = 'Selecciona un asunto.'
  }

  if (form.mensaje.trim().length < 20) {
    result.mensaje = 'Escribe al menos 20 caracteres.'
  }

  if (!form.privacidad) {
    result.privacidad = 'Debes aceptar la política de privacidad.'
  }

  return result
})

const isValid = computed(() => Object.keys(errors.value).length === 0)

function showError(field: keyof ContactForm) {
  return (touched[field] || submitted.value)
    ? errors.value[field]
    : ''
}

function markTouched(field: keyof ContactForm) {
  touched[field] = true
  successMessage.value = ''
}

async function submitForm() {
  submitted.value = true

  Object.keys(touched).forEach((key) => {
    touched[key as keyof ContactForm] = true
  })

  if (!isValid.value) {
    successMessage.value = ''
    return
  }

  try {
    await $fetch('/contacto', {
      baseURL: config.public.apiBase,
      method: 'POST',
      body: {
        nombre: form.nombre,
        email: form.correo,
        telefono: form.telefono,
        asunto: form.asunto,
        mensaje: form.mensaje,
      },
    })

    successMessage.value =
      `Tu mensaje fue enviado correctamente a ${contactEmail.value}.`
  } catch {
    successMessage.value =
      'No pudimos enviar el mensaje en este momento. Intenta nuevamente o escríbenos por WhatsApp.'
    return
  }

  form.nombre = ''
  form.correo = ''
  form.telefono = ''
  form.asunto = ''
  form.mensaje = ''
  form.privacidad = false

  submitted.value = false

  Object.keys(touched).forEach((key) => {
    touched[key as keyof ContactForm] = false
  })
}

const features = computed(() => [
  {
    icon: 'fa-solid fa-headset',
    label: 'Asesoría<br/>personalizada',
  },
  {
    icon: 'fa-solid fa-comments',
    label: 'Respuesta<br/>rápida',
  },
  {
    icon: 'fa-solid fa-shield-halved',
    label: 'Atención<br/>confiable',
  },
  {
    icon: 'fa-regular fa-clock',
    label: datosContacto.value.horario || 'Horario de atención',
  },
])

const contactInfo = computed(() => [
  {
    icon: 'fa-solid fa-phone',
    title: 'Teléfono / WhatsApp',
    main: datosContacto.value.telefono || datosContacto.value.whatsapp,
    sub: 'Atención rápida por WhatsApp',
  },
  {
    icon: 'fa-regular fa-envelope',
    title: 'Correo electrónico',
    main: datosContacto.value.correo,
    sub: 'Te responderemos a la brevedad',
  },
  {
    icon: 'fa-regular fa-clock',
    title: 'Horario de atención',
    main: datosContacto.value.horario,
    sub: datosContacto.value.whatsapp
      ? `WhatsApp: ${datosContacto.value.whatsapp}`
      : '',
  },
])

const contactEmail = computed(() => datosContacto.value.correo || '')

useSeoMeta({
  title: 'Contacto — Andes Llantas',
  description:
    'Contáctanos para cotizaciones, asesoría y seguimiento de pedidos.',
})
</script>

<template>
  <div>

    <!-- HERO -->
    <section
      class="relative overflow-hidden bg-cover bg-center bg-no-repeat px-4 py-10 text-white sm:px-5 lg:min-h-[444px]"
      style="
        background-image:
          linear-gradient(
            90deg,
            rgba(0, 0, 0, 0.88) 0%,
            rgba(0, 0, 0, 0.72) 42%,
            rgba(0, 0, 0, 0.42) 100%
          ),
          url('/hero-bg.jpg');
      "
    >
      <div
        class="relative mx-auto grid min-h-[360px] max-w-[1200px] grid-cols-1 items-center gap-5 lg:min-h-[380px] lg:grid-cols-[minmax(0,1.35fr)_minmax(0,0.65fr)] lg:gap-5 lg:py-7"
      >

        <div class="pb-2 pt-8 md:pb-2 md:pt-[26px]">

          <h1
            class="mb-3.5 text-[2.35rem] font-extrabold leading-[1] sm:text-[3rem] lg:text-[4.08rem]"
          >
            Estamos aquí<br />

            <span class="text-brand-accent">
              para ayudarte
            </span>
          </h1>

          <p
            class="mx-0 mb-0 max-w-[560px] text-[16px] leading-[1.65] text-white/93"
          >
            ¿Tienes dudas, necesitas una cotización o asesoría personalizada?
            Nuestro equipo está listo para atenderte.
          </p>

          <div
            class="mt-8 grid grid-cols-2 gap-4 lg:mt-[32px] lg:grid-cols-4"
          >
            <div
              v-for="feature in features"
              :key="feature.label"
              class="min-w-0 text-center text-white"
            >
              <div
                class="mx-auto mb-2 grid h-12 w-12 place-items-center rounded-[14px] border-2 border-brand-accent bg-brand-accent/[0.03] text-[1.3rem] text-brand-accent"
              >
                <i :class="feature.icon"></i>
              </div>

              <div
                class="text-[14px] font-medium leading-[1.35]"
                v-html="feature.label"
              ></div>
            </div>
          </div>

        </div>

        <div
          class="relative hidden min-h-[340px] items-center justify-end pr-1 lg:flex lg:min-h-[390px]"
        >
          <div
            class="relative z-[2] ml-auto flex h-full w-full max-w-[430px] items-center justify-end"
          >
          </div>
        </div>

      </div>
    </section>


    <!-- FORMULARIO -->
    <main class="pb-0 pt-12">

      <section
        class="mx-auto max-w-[1200px] px-4 sm:px-5 md:px-10 lg:px-14"
      >

        <div
          class="grid grid-cols-1 gap-10 lg:grid-cols-[1.65fr_1fr] lg:gap-14"
        >

          <!-- FORMULARIO -->
          <div>

            <h2
              class="mb-2.5 text-[25px] font-extrabold leading-[1.08] lg:text-[32px]"
            >
              Envíanos tu mensaje
            </h2>

            <p
              class="mb-[34px] text-[17px] text-[#404040]"
            >
              Completa el formulario y nos pondremos en contacto contigo
              lo antes posible.
            </p>


            <!-- MENSAJE DE ÉXITO -->
            <div
              v-if="successMessage"
              class="mb-6 flex items-start gap-3 rounded-lg border border-green-200 bg-green-50 px-4 py-3 text-[15px] font-semibold text-green-800"
            >
              <i
                class="fa-solid fa-circle-check mt-0.5 text-green-600"
              ></i>

              <span>{{ successMessage }}</span>
            </div>


            <form
              class="grid grid-cols-1 gap-[26px] lg:grid-cols-2 lg:gap-x-5 lg:gap-y-[26px]"
              novalidate
              @submit.prevent="submitForm"
            >

              <!-- NOMBRE -->
              <div class="flex flex-col gap-[9px]">

                <label
                  for="nombre"
                  class="text-[15px] font-bold text-[#232323]"
                >
                  Nombre completo
                  <span class="text-[#e6523d]">*</span>
                </label>

                <input
                  id="nombre"
                  v-model.trim="form.nombre"
                  type="text"
                  required
                  minlength="3"
                  maxlength="80"
                  pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+"
                  placeholder="Ingresa tu nombre"
                  class="w-full rounded-[5px] border border-[#e7e7e7] bg-white px-3.5 py-3.5 font-inter text-[17px] text-[#222] outline-none shadow-[0_1px_0_rgba(0,0,0,0.02)_inset] focus:border-[#d7d7d7] focus:shadow-[0_0_0_3px_rgba(244,180,0,0.10)]"
                  @blur="markTouched('nombre')"
                />

                <p
                  v-if="showError('nombre')"
                  class="text-sm font-medium text-red-600"
                >
                  {{ showError('nombre') }}
                </p>

              </div>


              <!-- CORREO -->
              <div class="flex flex-col gap-[9px]">

                <label
                  for="correo"
                  class="text-[15px] font-bold text-[#232323]"
                >
                  Correo electrónico
                  <span class="text-[#e6523d]">*</span>
                </label>

                <input
                  id="correo"
                  v-model.trim="form.correo"
                  type="email"
                  required
                  maxlength="120"
                  placeholder="Ingresa tu correo"
                  class="w-full rounded-[5px] border border-[#e7e7e7] bg-white px-3.5 py-3.5 font-inter text-[17px] text-[#222] outline-none shadow-[0_1px_0_rgba(0,0,0,0.02)_inset] focus:border-[#d7d7d7] focus:shadow-[0_0_0_3px_rgba(244,180,0,0.10)]"
                  @blur="markTouched('correo')"
                />

                <p
                  v-if="showError('correo')"
                  class="text-sm font-medium text-red-600"
                >
                  {{ showError('correo') }}
                </p>

              </div>


              <!-- TELEFONO -->
              <div class="flex flex-col gap-[9px]">

                <label
                  for="telefono"
                  class="text-[15px] font-bold text-[#232323]"
                >
                  Teléfono / WhatsApp
                  <span class="text-[#e6523d]">*</span>
                </label>

                <input
                  id="telefono"
                  v-model.trim="form.telefono"
                  type="tel"
                  required
                  inputmode="numeric"
                  minlength="9"
                  maxlength="9"
                  pattern="9[0-9]{8}"
                  placeholder="Ej. 987654321"
                  class="w-full rounded-[5px] border border-[#e7e7e7] bg-white px-3.5 py-3.5 font-inter text-[17px] text-[#222] outline-none shadow-[0_1px_0_rgba(0,0,0,0.02)_inset] focus:border-[#d7d7d7] focus:shadow-[0_0_0_3px_rgba(244,180,0,0.10)]"
                  @blur="markTouched('telefono')"
                />

                <p
                  v-if="showError('telefono')"
                  class="text-sm font-medium text-red-600"
                >
                  {{ showError('telefono') }}
                </p>

              </div>


              <!-- ASUNTO -->
              <div class="flex flex-col gap-[9px]">

                <label
                  for="asunto"
                  class="text-[15px] font-bold text-[#232323]"
                >
                  Asunto
                  <span class="text-[#e6523d]">*</span>
                </label>

                <select
                  id="asunto"
                  v-model="form.asunto"
                  required
                  class="w-full appearance-auto rounded-[5px] border border-[#e7e7e7] bg-white px-3.5 py-3.5 font-inter text-[17px] text-[#222] outline-none shadow-[0_1px_0_rgba(0,0,0,0.02)_inset] focus:border-[#d7d7d7] focus:shadow-[0_0_0_3px_rgba(244,180,0,0.10)]"
                  @blur="markTouched('asunto')"
                >

                  <option value="" disabled>
                    Selecciona un asunto
                  </option>

                  <option value="Cotización">
                    Cotización
                  </option>

                  <option value="Asesoría">
                    Asesoría
                  </option>

                  <option value="Seguimiento">
                    Seguimiento
                  </option>

                  <option value="Otro">
                    Otro
                  </option>

                </select>

                <p
                  v-if="showError('asunto')"
                  class="text-sm font-medium text-red-600"
                >
                  {{ showError('asunto') }}
                </p>

              </div>


              <!-- MENSAJE -->
              <div class="flex flex-col gap-[9px] lg:col-span-2">

                <label
                  for="mensaje"
                  class="text-[15px] font-bold text-[#232323]"
                >
                  Mensaje
                  <span class="text-[#e6523d]">*</span>
                </label>

                <textarea
                  id="mensaje"
                  v-model.trim="form.mensaje"
                  required
                  minlength="20"
                  maxlength="1200"
                  placeholder="Cuéntanos cómo podemos ayudarte..."
                  class="min-h-[134px] w-full resize-y rounded-[5px] border border-[#e7e7e7] bg-white px-3.5 py-3.5 font-inter text-[17px] text-[#222] outline-none shadow-[0_1px_0_rgba(0,0,0,0.02)_inset] focus:border-[#d7d7d7] focus:shadow-[0_0_0_3px_rgba(244,180,0,0.10)]"
                  @blur="markTouched('mensaje')"
                ></textarea>

                <div class="flex items-center justify-between gap-3">

                  <p
                    v-if="showError('mensaje')"
                    class="text-sm font-medium text-red-600"
                  >
                    {{ showError('mensaje') }}
                  </p>

                  <p class="ml-auto text-xs text-gray-400">
                    {{ form.mensaje.length }}/1200
                  </p>

                </div>

              </div>


              <!-- PRIVACIDAD -->
              <div class="lg:col-span-2">

                <label
                  class="my-1 flex cursor-pointer items-start gap-2.5 text-[15px] text-[#494949] sm:items-center sm:text-[16px]"
                >

                  <input
                    v-model="form.privacidad"
                    type="checkbox"
                    required
                    class="mt-1 h-4 w-4 sm:mt-0"
                    @change="markTouched('privacidad')"
                  />

                  <span>
                    Acepto la
                    <a
                      href="#"
                      class="font-semibold text-[#f0a200] no-underline"
                    >
                      política de privacidad
                    </a>
                  </span>

                </label>

                <p
                  v-if="showError('privacidad')"
                  class="mt-1 text-sm font-medium text-red-600"
                >
                  {{ showError('privacidad') }}
                </p>

              </div>


              <!-- BOTON -->
              <button
                type="submit"
                :disabled="!isValid"
                class="flex w-full cursor-pointer items-center justify-center gap-2.5 rounded-md border-none bg-gradient-to-b from-[#f7bc19] to-[#f2b20c] px-[18px] py-4 text-[16px] font-black tracking-[0.3px] text-[#121212] transition-all duration-150 hover:from-[#e6b000] hover:to-[#d9a500] disabled:cursor-not-allowed disabled:opacity-50 lg:col-span-2"
              >

                <i class="fa-regular fa-paper-plane"></i>

                Enviar mensaje

              </button>

            </form>

          </div>


          <!-- INFORMACION DE CONTACTO -->
          <aside
            class="rounded-lg border border-[#ececec] bg-white p-5 shadow-[0_1px_0_rgba(0,0,0,0.02)] lg:p-[22px]"
          >

            <h3
              class="mb-[22px] text-center text-[26px] font-extrabold tracking-[-0.4px]"
            >
              Información de contacto
            </h3>

            <div
              v-for="(info, index) in contactInfo"
              :key="info.title"
              class="mb-7 grid grid-cols-[42px_minmax(0,1fr)] items-start gap-3.5 sm:grid-cols-[56px_minmax(0,1fr)]"
              :class="{ '!mb-0': index === contactInfo.length - 1 }"
            >

              <div
                class="mt-0.5 grid h-[42px] w-[42px] place-items-center rounded-full bg-brand-accent text-[17px] text-[#111] shadow-[0_6px_14px_rgba(244,180,0,0.18)]"
              >
                <i :class="info.icon"></i>
              </div>

              <div>

                <strong
                  class="mb-1.5 block text-[16px] font-extrabold"
                >
                  {{ info.title }}
                </strong>

                <div
                  class="text-[15px] font-bold leading-[1.45] text-[#171717]"
                  v-html="info.main"
                ></div>

                <div
                  class="mt-1 text-[14.5px] leading-[1.45] text-[#5a5a5a]"
                  v-html="info.sub"
                ></div>

              </div>

            </div>

          </aside>

        </div>


        <!-- WHATSAPP -->
        <div
          class="mb-5 mt-20 text-center text-[15.5px] text-[#222]"
        >

          ¿No encuentras lo que buscas?

          <i class="fa-brands fa-whatsapp mx-1.5"></i>

          Escríbenos por WhatsApp y te ayudamos.

        </div>

      </section>

    </main>

  </div>
</template>
