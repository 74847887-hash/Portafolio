<script setup lang="ts">
import { WHATSAPP_NUMBER } from '~/utils/constants'

const pasos = [
    {
        numero: 1,
        titulo: 'Busca',
        descripcion: 'Encuentra la llanta ideal para tu vehículo.',
        icon: 'fa-solid fa-magnifying-glass',
    },
    {
        numero: 2,
        titulo: 'Cotiza',
        descripcion: 'Recibe asesoría y confirma tu pedido.',
        icon: 'fa-solid fa-comments',
    },
    {
        numero: 3,
        titulo: 'Paga seguro',
        descripcion: 'Realiza tu pago con total seguridad.',
        icon: 'fa-solid fa-credit-card',
    },
    {
        numero: 4,
        titulo: 'Recibe',
        descripcion: 'Enviamos tu pedido a la dirección que elijas.',
        icon: 'fa-solid fa-truck',
    },
]
</script>

<template>
    <section id="como-comprar" class="bg-zinc-900 py-10 relative overflow-hidden">

        <!-- Textura decorativa derecha -->
        <div
            class="absolute right-0 top-0 h-full w-48 opacity-10 bg-gradient-to-l from-yellow-400 to-transparent pointer-events-none" />

        <div class="max-w-7xl mx-auto px-6">

            <!-- Título -->
            <h2 class="text-center text-xl font-bold text-white mb-10">
                Así de <span class="text-yellow-400">fácil</span> es comprar tus llantas
            </h2>

            <!-- Pasos -->
            <div class="grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-4">
                <div v-for="(paso, index) in pasos" :key="paso.numero"
                    class="relative flex items-start justify-center">
                    <!-- Paso -->
                    <div class="flex max-w-[180px] flex-col items-center text-center">
                        <!-- Número + ícono -->
                        <div class="relative mb-3">
                            <div
                                class="w-12 h-12 rounded-full bg-yellow-400 flex items-center justify-center text-black font-black text-sm absolute -top-2 -left-3 z-10">
                                {{ paso.numero }}
                            </div>
                            <div class="w-14 h-14 flex items-center justify-center ml-4">
                                <i :class="[paso.icon, 'text-3xl text-white']" />
                            </div>
                        </div>
                        <h3 class="text-white font-bold text-sm mb-1">{{ paso.titulo }}</h3>
                        <p class="text-gray-400 text-xs leading-relaxed">{{ paso.descripcion }}</p>
                    </div>

                    <!-- Línea conectora -->
                    <div v-if="index < pasos.length - 1"
                        class="absolute left-[calc(50%+5rem)] right-[calc(-50%+5rem)] top-7 hidden border-t-2 border-dashed border-gray-600 lg:block" />
                </div>
            </div>

        </div>
    </section>
</template>
