<!-- app/error.vue -->
<script setup lang="ts">
const props = defineProps<{
  error: {
    statusCode: number
    message: string
  }
}>()

const handleError = () => clearError({ redirect: '/' })
</script>

<template>
  <div class="min-h-screen bg-black flex flex-col items-center justify-center px-6 text-center">

    <!-- Logo -->
    <NuxtLink to="/" class="flex items-center gap-2.5 mb-12">
      <i class="fa-solid fa-mountain text-[28px] text-brand-yellow" />
      <div class="text-[18px] font-black leading-[1.1] tracking-[0.5px] text-white">
        ANDES
        <span class="block text-[12px] font-bold tracking-[1px]">LLANTAS</span>
      </div>
    </NuxtLink>

    <!-- Código de error -->
    <h1 class="text-[120px] font-black text-brand-yellow leading-none mb-4">
      {{ error.statusCode }}
    </h1>

    <!-- Mensaje -->
    <p class="text-white text-xl font-bold mb-2">
      {{ error.statusCode === 404 ? 'Página no encontrada' : 'Algo salió mal' }}
    </p>
    <p class="text-gray-400 text-sm mb-10 max-w-md">
      {{ error.statusCode === 404
        ? 'La página que buscas no existe o fue movida.'
        : 'Ocurrió un error inesperado. Intenta de nuevo.' }}
    </p>

    <!-- Botones -->
    <div class="flex items-center gap-4">
      <button
        class="flex items-center gap-2 bg-brand-yellow text-black font-bold px-6 py-3 rounded hover:brightness-110 transition-all"
        @click="handleError"
      >
        <i class="fa-solid fa-house text-sm" />
        VOLVER AL INICIO
      </button>
      <NuxtLink
        to="/catalogo"
        class="flex items-center gap-2 border-2 border-white text-white font-bold px-6 py-3 rounded hover:bg-white hover:text-black transition-all"
      >
        <i class="fa-solid fa-tire text-sm" />
        VER CATÁLOGO
      </NuxtLink>
    </div>

  </div>
</template>
