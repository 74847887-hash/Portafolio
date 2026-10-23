<script setup lang="ts">
const config = useRuntimeConfig()

const datos = ref({
    telefono: '+51 999 123 456',
    whatsapp: '',
    correo: 'ventas@andesllantas.com',
    horario: 'Lun - Sab: 8:00 am - 6:00 pm'
})

onMounted(async () => {
    try {
        datos.value = await $fetch('/informacion-contacto', {
            baseURL: config.public.apiBase
        })
    } catch {
        // Se conservan los datos visibles si el backend no está disponible
    }
})

const contacto = computed(() => [
    {
        icon: 'fa-solid fa-phone',
        texto: datos.value.telefono
    },
    {
        icon: 'fa-solid fa-envelope',
        texto: datos.value.correo
    },
    {
        icon: 'fa-solid fa-clock',
        texto: datos.value.horario
    },
    {
        icon: 'fa-brands fa-whatsapp',
        texto: datos.value.whatsapp || datos.value.telefono
    },
])

const atencionCliente = [
    {
        label: 'Preguntas frecuentes',
        href: '/faq'
    },
    {
        label: 'Cambios y devoluciones',
        href: '/cambios'
    },
    {
        label: 'Seguimiento de pedidos',
        href: '/seguimiento'
    },
]

const redes = [
    {
        nombre: 'Facebook',
        href: 'https://facebook.com',
        icon: 'fa-brands fa-facebook-f'
    },
    {
        nombre: 'Instagram',
        href: 'https://instagram.com',
        icon: 'fa-brands fa-instagram'
    },
    {
        nombre: 'TikTok',
        href: 'https://tiktok.com',
        icon: 'fa-brands fa-tiktok'
    },
    {
        nombre: 'LinkedIn',
        href: 'https://linkedin.com',
        icon: 'fa-brands fa-linkedin-in'
    },
]

const metodosPago = [
    {
        nombre: 'Yape',
        icon: 'fa-solid fa-wallet'
    },
    {
        nombre: 'Plin',
        icon: 'fa-solid fa-money-bill-transfer'
    },
    {
        nombre: 'Visa',
        icon: 'fa-brands fa-cc-visa'
    },
]
</script>

<template>
    <footer class="bg-zinc-900 text-white pt-12 pb-6">
        <div class="max-w-7xl mx-auto px-6">

            <div class="grid grid-cols-1 gap-10 mb-10 sm:grid-cols-2 lg:grid-cols-4">

                <!-- Columna 1: Logo + descripción + redes -->
                <div>

                    <NuxtLink to="/" class="mb-4 flex w-fit min-w-0 items-center gap-2.5">
                        <i class="fa-solid fa-mountain text-[28px] text-brand-yellow" />
                        <div class="text-[18px] font-black leading-[1.1] tracking-[0.5px] text-white">
                            ANDES
                            <span class="block text-[12px] font-bold tracking-[1px]">
                                LLANTAS
                            </span>
                        </div>
                    </NuxtLink>

                    <p class="text-gray-400 text-sm leading-relaxed mb-6">
                        Tu mejor opción en llantas originales. Calidad, seguridad
                        y confianza en cada kilómetro.
                    </p>

                    <!-- Redes sociales -->
                    <div class="flex items-center gap-3">
                        <a
                            v-for="red in redes"
                            :key="red.nombre"
                            :href="red.href"
                            target="_blank"
                            rel="noopener noreferrer"
                            class="w-8 h-8 rounded bg-zinc-700 flex items-center justify-center hover:bg-yellow-400 hover:text-black transition-all group"
                            :aria-label="red.nombre"
                        >
                            <i
                                :class="[
                                    red.icon,
                                    'text-sm text-white group-hover:text-black'
                                ]"
                            />
                        </a>
                    </div>

                </div>

                <!-- Columna 2: Contáctanos -->
                <div>

                    <h4
                        class="text-sm font-bold tracking-widest text-white mb-4 uppercase"
                    >
                        Contáctanos
                    </h4>

                    <ul class="space-y-3">

                        <li
                            v-for="item in contacto"
                            :key="item.texto"
                            class="flex items-start gap-3 text-gray-400 text-sm"
                        >
                            <i
                                :class="[
                                    item.icon,
                                    'mt-0.5 text-brand-yellow w-4 text-center'
                                ]"
                            />

                            <span>
                                {{ item.texto }}
                            </span>
                        </li>

                    </ul>

                </div>

                <!-- Columna 3: Atención al cliente -->
                <div>

                    <h4
                        class="text-sm font-bold tracking-widest text-white mb-4 uppercase"
                    >
                        Atención al cliente
                    </h4>

                    <ul class="space-y-3">

                        <li
                            v-for="item in atencionCliente"
                            :key="item.href"
                        >
                            <NuxtLink
                                :to="item.href"
                                class="text-gray-400 text-sm hover:text-yellow-400 transition-colors"
                            >
                                {{ item.label }}
                            </NuxtLink>
                        </li>

                    </ul>

                </div>

                <!-- Columna 4: Pago seguro -->
                <div>

                    <h4
                        class="text-sm font-bold tracking-widest text-white mb-4 uppercase"
                    >
                        Pago seguro
                    </h4>

                    <div class="flex flex-wrap gap-2">
                        <div
                            v-for="metodo in metodosPago"
                            :key="metodo.nombre"
                            class="flex items-center gap-2 rounded border border-zinc-700 bg-zinc-800 px-3 py-2 text-xs font-bold text-white"
                        >
                            <i
                                :class="[
                                    metodo.icon,
                                    'text-brand-yellow'
                                ]"
                            />

                            <span>
                                {{ metodo.nombre }}
                            </span>
                        </div>
                    </div>

                </div>

            </div>

            <!-- Línea divisoria -->
            <div class="border-t border-zinc-700 pt-6 text-center">

                <p class="text-gray-500 text-xs">
                    © 2026 Andes Llantas. Todos los derechos reservados.
                </p>

            </div>

        </div>
    </footer>
</template>
