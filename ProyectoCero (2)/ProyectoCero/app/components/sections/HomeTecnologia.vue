<script setup lang="ts">
const videoId = 'xq4Ss2g87oY'
</script>

<template>
    <section class="bg-gray-100 py-12">
        <div class="max-w-7xl mx-auto px-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-10 items-center">

                <!-- Texto izquierda -->
                <div>
                    <h2 class="text-2xl font-bold text-gray-900 mb-4 leading-snug">
                        Tecnología y rendimiento <br /> en cada llanta
                    </h2>
                    <p class="text-gray-500 text-sm leading-relaxed mb-6">
                        Conoce más sobre nuestras llantas, procesos
                        de calidad y por qué somos tu mejor opción.
                    </p>
                    <a :href="`https://youtu.be/${videoId}`" target="_blank" rel="noopener noreferrer"
                        class="inline-flex items-center gap-2 bg-yellow-400 text-black text-sm font-bold px-5 py-2.5 rounded hover:brightness-110 transition-all">
                        <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 24 24">
                            <path d="M8 5v14l11-7z" />
                        </svg>
                        VER VIDEO COMPLETO
                    </a>
                </div>

                <!-- Video derecha -->
                <div class="rounded-xl overflow-hidden shadow-lg aspect-video">
                    <iframe :src="`https://www.youtube.com/embed/${videoId}`"
                        title="Tecnología y rendimiento en cada llanta" frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowfullscreen class="w-full h-full" />
                </div>

            </div>
        </div>
    </section>
</template>