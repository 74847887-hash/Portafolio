<script setup lang="ts">
import type { Product } from '~/types'
import { WHATSAPP_URL } from '~/utils/constants'

const props = defineProps<{
  product: Product
}>()

const whatsappMessage = encodeURIComponent(
  `Hola, estoy interesado en: ${props.product.title} (${props.product.price}). ¿Tienen disponibilidad?`
)
const whatsappLink = `${WHATSAPP_URL}?text=${whatsappMessage}`
</script>

<template>
  <div
    class="flex h-full flex-col overflow-hidden rounded-lg border border-gray-100 bg-white shadow-sm transition-shadow duration-300 hover:shadow-md">

    <!-- Parte superior -->
    <div class="relative flex flex-col gap-3 p-4 min-[430px]:flex-row min-[430px]:gap-4">

      <!-- Badge PREMIUM -->
      <div
        class="absolute top-3 left-3 flex items-center gap-1 bg-brand-yellow text-black text-[10px] font-black px-2 py-0.5 rounded-full">
        <i class="fa-solid fa-star text-[9px]" />
        PREMIUM
      </div>

      <div class="flex h-32 w-full flex-shrink-0 items-center justify-center pt-4 min-[430px]:w-[112px]">
        <img :src="product.image" :alt="product.title" class="max-h-28 w-full object-contain" />
      </div>

      <!-- Info derecha -->
      <div class="flex min-w-0 flex-1 flex-col gap-1.5 pt-1 min-[430px]:pt-5">
        <!-- Logo marca -->
        <img :src="product.brandLogo" :alt="product.brand" class="max-h-6 object-contain self-start"
          :style="{ width: product.brandLogoWidth }" />

        <!-- Nombre y medida -->
        <div>
          <h3 class="text-[15px] font-black text-gray-900 leading-tight">
            {{ product.shortName ?? product.brand }}
          </h3>
          <p class="text-[13px] text-gray-500 font-medium">{{ product.medida }}</p>
        </div>

        <!-- Descripción -->
        <p class="text-[12px] text-gray-500 leading-relaxed line-clamp-2">
          {{ product.description ?? 'Llanta de alto rendimiento para mayor seguridad y durabilidad.' }}
        </p>
      </div>
    </div>

    <!-- Info rápida -->
    <div class="grid grid-cols-3 gap-1 px-4 py-3 border-t border-gray-100">
      <div class="flex flex-col items-center gap-1 text-center">
        <i class="fa-solid fa-shield-halved text-gray-400 text-sm" />
        <span class="text-[10px] text-gray-400 leading-tight">Garantía</span>
        <span class="text-[11px] font-bold text-gray-700">5 años</span>
      </div>
      <div class="flex flex-col items-center gap-1 text-center">
        <i class="fa-solid fa-circle-check text-green-500 text-sm" />
        <span class="text-[10px] text-gray-400 leading-tight">En stock</span>
        <span class="text-[11px] font-bold text-gray-700">+20 und.</span>
      </div>
      <div class="flex flex-col items-center gap-1 text-center">
        <i class="fa-solid fa-medal text-brand-yellow text-sm" />
        <span class="text-[10px] text-gray-400 leading-tight">Calidad</span>
        <span class="text-[11px] font-bold text-brand-yellow">Premium</span>
      </div>
    </div>

    <!-- Precio y botones -->
    <div class="mt-auto flex flex-col gap-3 border-t border-gray-100 px-4 py-3 min-[430px]:flex-row min-[430px]:items-center min-[430px]:justify-between">
      <div class="shrink-0">
        <p class="text-[10px] text-gray-400 uppercase tracking-wider">Desde</p>
        <p class="text-[18px] font-black text-gray-900">{{ product.price }}</p>
        <p class="text-[10px] text-gray-400">Incl. IGV</p>
      </div>
      <div class="flex min-w-0 flex-1 flex-col gap-2">
        <NuxtLink :to="`/catalogo/${product.slug}`"
          class="flex items-center justify-center gap-2 border border-gray-300 text-gray-700 text-[12px] font-bold py-2 rounded-lg hover:bg-gray-50 transition-all">
          <i class="fa-solid fa-eye text-[11px]" />
          Ver detalle
        </NuxtLink>
        <a :href="whatsappLink" target="_blank" rel="noopener noreferrer"
          class="flex items-center justify-center gap-2 bg-brand-yellow text-black text-[12px] font-bold py-2 rounded-lg hover:brightness-110 transition-all">
          <i class="fa-brands fa-whatsapp text-[13px]" />
          Cotizar por WhatsApp
        </a>
      </div>
    </div>

  </div>
</template>
