<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { WHATSAPP_URL } from '~/utils/constants'

const slides = [
    { type: 'video', src: '/videos/hero-video.mp4' }
]

const current = ref(0)
let interval: ReturnType<typeof setInterval>

function goTo(index: number) {
    current.value = index
}

function next() {
    current.value = (current.value + 1) % slides.length
}

onMounted(() => {
    interval = setInterval(next, 5000)
})

onUnmounted(() => {
    clearInterval(interval)
})

const whatsappUrl = WHATSAPP_URL
</script>

<template>
    <section class="relative min-h-[520px] w-full overflow-hidden bg-black sm:min-h-[560px] lg:h-[580px]">

        <!-- Slides -->
        <div v-for="(slide, index) in slides" :key="index" class="absolute inset-0 transition-opacity duration-700"
            :class="index === current ? 'opacity-100' : 'opacity-0'">
            <!-- Video slide -->
            <template v-if="slide.type === 'video'">
                <video
                    v-if="slide.src"
                    :src="slide.src"
                    class="h-full w-full object-cover"
                    autoplay
                    muted
                    loop
                    playsinline
                    preload="metadata"
                />
                <div v-else class="w-full h-full bg-zinc-900 flex items-center justify-center">
                    <span class="text-zinc-600 text-sm">[ Video placeholder ]</span>
                </div>
            </template>

            <!-- Image slide -->
            <template v-else>
                <div class="w-full h-full bg-zinc-800 flex items-center justify-center">
                    <span class="text-zinc-600 text-sm">[ Imagen placeholder {{ index }} ]</span>
                </div>
            </template>

            <!-- Overlay oscuro para legibilidad del texto -->
            <div class="absolute inset-0 bg-black/50" />
        </div>

        <!-- Contenido encima del carrusel -->
        <div class="relative z-10 mx-auto flex min-h-[520px] max-w-7xl items-center px-4 py-16 sm:min-h-[560px] sm:px-6 lg:h-full lg:py-0">
            <div class="max-w-xl">

                <!-- Título -->
                <h1 class="mb-4 text-[2.35rem] font-bold leading-[1.05] text-white sm:text-5xl">
                    Las mejores llantas <br />
                    para <span class="text-yellow-400">cada camino</span>
                </h1>

                <!-- Subtítulo -->
                <p class="mb-8 max-w-[34rem] text-sm leading-relaxed text-white/80 sm:text-base">
                    Trabajamos con las mejores marcas para garantizarte
                    seguridad, rendimiento y durabilidad en cada kilómetro.
                    Asesoría personalizada y envíos a todo el Perú.
                </p>

                <!-- Botones CTA -->
                <div class="flex flex-col gap-3 sm:flex-row sm:flex-wrap sm:items-center sm:gap-4">
                    <NuxtLink to="/catalogo#productos"
                        class="flex w-full items-center justify-center gap-2 rounded bg-yellow-400 px-6 py-3 font-bold text-black transition-all hover:brightness-110 sm:w-auto">
                        Ver catálogo →
                    </NuxtLink>

                    <a :href="whatsappUrl" target="_blank" rel="noopener noreferrer"
                        class="flex w-full items-center justify-center gap-2 rounded border-2 border-white px-6 py-3 font-bold text-white transition-all hover:bg-white hover:text-black sm:w-auto">
                        <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                            <path
                                d="M17.472 14.382c-.297-.149-1.758-.867-2.03-.967-.273-.099-.471-.148-.67.15-.197.297-.767.966-.94 1.164-.173.199-.347.223-.644.075-.297-.15-1.255-.463-2.39-1.475-.883-.788-1.48-1.761-1.653-2.059-.173-.297-.018-.458.13-.606.134-.133.298-.347.446-.52.149-.174.198-.298.298-.497.099-.198.05-.371-.025-.52-.075-.149-.669-1.612-.916-2.207-.242-.579-.487-.5-.669-.51-.173-.008-.371-.01-.57-.01-.198 0-.52.074-.792.372-.272.297-1.04 1.016-1.04 2.479 0 1.462 1.065 2.875 1.213 3.074.149.198 2.096 3.2 5.077 4.487.709.306 1.262.489 1.694.625.712.227 1.36.195 1.871.118.571-.085 1.758-.719 2.006-1.413.248-.694.248-1.289.173-1.413-.074-.124-.272-.198-.57-.347z" />
                            <path
                                d="M12 0C5.373 0 0 5.373 0 12c0 2.123.554 4.117 1.528 5.845L.057 23.272a.75.75 0 0 0 .92.92l5.427-1.471A11.943 11.943 0 0 0 12 24c6.627 0 12-5.373 12-12S18.627 0 12 0zm0 22c-1.907 0-3.695-.528-5.222-1.443l-.374-.223-3.873 1.05 1.05-3.872-.223-.374A9.953 9.953 0 0 1 2 12C2 6.477 6.477 2 12 2s10 4.477 10 10-4.477 10-10 10z" />
                        </svg>
                        Cotizar por WhatsApp
                    </a>
                </div>
            </div>

            <!-- Botón Ver video (derecha) -->
            <div class="absolute right-8 hidden cursor-pointer items-center gap-3 lg:flex xl:right-16" @click="goTo(0)">
                <div
                    class="w-14 h-14 rounded-full border-2 border-white flex items-center justify-center group-hover:bg-white/10 transition-all">
                    <svg class="w-6 h-6 text-white ml-1" fill="currentColor" viewBox="0 0 24 24">
                        <path d="M8 5v14l11-7z" />
                    </svg>
                </div>
                <div class="text-white text-sm">
                    <p class="font-bold">VER VIDEO</p>
                    <p class="text-white/60 text-xs">Conoce más sobre<br />nuestras llantas</p>
                </div>
            </div>
        </div>

        <!-- Indicadores (puntos) -->
        <div class="absolute bottom-6 left-1/2 -translate-x-1/2 z-10 flex items-center gap-2">
            <button v-for="(_, index) in slides" :key="index" class="w-2.5 h-2.5 rounded-full transition-all"
                :class="index === current ? 'bg-yellow-400 w-6' : 'bg-white/50'" @click="goTo(index)" />
        </div>

    </section>
</template>
