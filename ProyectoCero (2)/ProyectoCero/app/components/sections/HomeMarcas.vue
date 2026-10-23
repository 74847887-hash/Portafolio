<script setup lang="ts">
const marcas = [
    { nombre: 'Michelin', logo: '/images/brands/michelin.jpg' },
    { nombre: 'Bridgestone', logo: '/images/brands/Bridgestone.jpg' },
    { nombre: 'Pirelli', logo: '/images/brands/pirelli.png' },
    { nombre: 'Goodyear', logo: '/images/brands/goodyear.png' },
    { nombre: 'BFGoodrich', logo: '/images/brands/bfgoodrich.png' },
    { nombre: 'Kumho', logo: '/images/brands/kumho.jpg' },
    { nombre: 'Linglong', logo: '/images/brands/linglong.png' },
    { nombre: 'Rockbuster', logo: '/images/brands/rockbuster.jpg' },
]

const marcasLoop = [...marcas, ...marcas]
</script>

<template>
    <section id="marcas" class="bg-gray-100 py-10 overflow-hidden">
        <div class="max-w-7xl mx-auto px-6 mb-8">
            <h2 class="text-center text-lg font-bold text-gray-900">
                Trabajamos con las mejores marcas
            </h2>
        </div>

        <!-- Carrusel infinito -->
        <div class="relative">
            <!-- Fade izquierda -->
            <div
                class="absolute left-0 top-0 h-full w-24 bg-gradient-to-r from-gray-100 to-transparent z-10 pointer-events-none" />
            <!-- Fade derecha -->
            <div
                class="absolute right-0 top-0 h-full w-24 bg-gradient-to-l from-gray-100 to-transparent z-10 pointer-events-none" />

            <!-- Track -->
            <div class="flex gap-6 animate-marquee w-max">
                <div v-for="(marca, index) in marcasLoop" :key="index"
                    class="flex-shrink-0 w-36 h-14 bg-white rounded-lg border border-gray-200 flex items-center justify-center shadow-sm px-4">
                    <img :src="marca.logo" :alt="marca.nombre" class="max-h-8 max-w-full object-contain" />
                </div>
            </div>
        </div>

        <!-- Botón -->
        <div class="flex justify-center mt-8">
            <NuxtLink to="/marcas"
                class="border border-yellow-500 text-yellow-600 text-sm font-bold px-8 py-2.5 rounded hover:bg-yellow-400 hover:text-black transition-all">
                VER TODAS LAS MARCAS
            </NuxtLink>
        </div>
    </section>
</template>