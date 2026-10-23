<!-- app/components/TheHeader.vue -->
<script setup lang="ts">
import { useScroll } from '@vueuse/core'
import { WHATSAPP_URL } from '~/utils/constants'

const navLinks = [
  { label: 'INICIO',          to: '/' },
  { label: 'CATÁLOGO',        to: '/catalogo' },
  { label: 'MARCAS',          to: '/#marcas',     isAnchor: true },
  { label: 'PROMOCIONES',     to: '/#promociones', isAnchor: true },
  { label: '¿CÓMO COMPRAR?', to: '/#como-comprar', isAnchor: true },
  { label: 'CONTACTO',        to: '/contacto' },
]

const whatsappUrl = WHATSAPP_URL

const { y } = useScroll(typeof window !== 'undefined' ? window : null)
const mobileMenuOpen = ref(false)
const route = useRoute()

watch(() => route.path, () => {
  mobileMenuOpen.value = false
})
</script>

<template>
  <header class="sticky top-0 z-50 flex h-[70px] items-center justify-between gap-4 border-b border-brand-dark bg-black px-4 sm:px-5 lg:px-8 xl:px-10">

    <!-- Logo -->
    <NuxtLink to="/" class="flex min-w-0 shrink-0 items-center gap-2.5">
      <i class="fa-solid fa-mountain text-[28px] text-brand-yellow" />
      <div class="text-[18px] font-black leading-[1.1] tracking-[0.5px] text-white">
        ANDES
        <span class="block text-[12px] font-bold tracking-[1px]">LLANTAS</span>
      </div>
    </NuxtLink>

    <!-- Nav desktop -->
    <ul class="hidden min-w-0 flex-1 items-center justify-center gap-4 lg:flex xl:gap-7">
      <li v-for="link in navLinks" :key="link.to">
        <NuxtLink
          :to="link.to"
          class="border-b-2 border-transparent pb-1 text-[13px] font-bold uppercase text-white transition-colors hover:text-brand-yellow xl:text-[14px]"
          :class="{ '!border-brand-yellow !text-brand-yellow': route.path === link.to }"
        >
          {{ link.label }}
        </NuxtLink>
      </li>
    </ul>

    <!-- CTA desktop -->
    <a
      :href="whatsappUrl"
      target="_blank"
      rel="noopener noreferrer"
      class="hidden shrink-0 items-center gap-2 rounded-md bg-brand-yellow px-4 py-2.5 text-[13px] font-extrabold uppercase text-black transition-colors duration-150 hover:bg-[#e6b000] lg:flex xl:px-5 xl:text-[14px]"
    >
      <i class="fa-brands fa-whatsapp text-[16px]" />
      COTIZAR AHORA
    </a>

    <!-- Hamburguesa mobile -->
    <button
      class="flex h-11 w-11 shrink-0 items-center justify-center rounded-md bg-brand-yellow text-black lg:hidden"
      @click="mobileMenuOpen = !mobileMenuOpen"
    >
      <i
        class="text-lg"
        :class="mobileMenuOpen ? 'fa-solid fa-xmark' : 'fa-solid fa-bars'"
      />
    </button>
  </header>

  <!-- Overlay mobile -->
  <Transition name="overlay">
    <div
      v-if="mobileMenuOpen"
      class="fixed inset-0 z-40 bg-black/60 lg:hidden"
      @click="mobileMenuOpen = false"
    />
  </Transition>

  <!-- Panel mobile -->
  <Transition name="slide-right">
    <div
      v-if="mobileMenuOpen"
      class="fixed right-0 top-[70px] z-50 flex h-[calc(100vh-70px)] w-[min(18rem,85vw)] flex-col overflow-y-auto bg-black p-6 lg:hidden"
    >
      <ul class="flex flex-col gap-5">
        <li v-for="link in navLinks" :key="link.to">
          <NuxtLink
            :to="link.to"
            class="block border-b-2 border-transparent pb-1 text-[16px] font-bold uppercase tracking-[0.5px] text-white transition-colors hover:text-brand-yellow"
            :class="{ '!border-brand-yellow !text-brand-yellow': route.path === link.to }"
          >
            {{ link.label }}
          </NuxtLink>
        </li>
      </ul>

      <a
        :href="whatsappUrl"
        target="_blank"
        rel="noopener noreferrer"
        class="mt-8 flex items-center justify-center gap-2 rounded-md bg-brand-yellow py-3 text-[16px] font-extrabold uppercase tracking-[0.3px] text-black"
      >
        <i class="fa-brands fa-whatsapp text-[16px]" />
        COTIZAR AHORA
      </a>
    </div>
  </Transition>
</template>

<style scoped>
.overlay-enter-active,
.overlay-leave-active {
  transition: opacity 0.3s ease;
}
.overlay-enter-from,
.overlay-leave-to {
  opacity: 0;
}
.slide-right-enter-active,
.slide-right-leave-active {
  transition: transform 0.3s ease;
}
.slide-right-enter-from,
.slide-right-leave-to {
  transform: translateX(100%);
}
</style>
