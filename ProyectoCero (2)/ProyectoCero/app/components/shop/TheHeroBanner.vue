<script setup lang="ts">
import { useCatalogoStore } from '~/stores/catalogo'

const store = useCatalogoStore()

function isHeroIcon(icon: string): boolean {
  return icon.startsWith('hero:')
}

function heroIconName(icon: string): string {
  return icon.replace('hero:', '')
}
</script>

<template>
  <section
    class="relative bg-cover bg-center px-4 py-10 text-white sm:px-6 lg:px-10"
    :style="{
      backgroundImage:
        'linear-gradient(rgba(0,0,0,0.82), rgba(0,0,0,0.88)), url(https://images.unsplash.com/photo-1578844251758-2f71da64c96f?q=80&w=1600&auto=format&fit=crop)',
    }"
  >
    <div class="mx-auto grid w-full max-w-7xl grid-cols-1 gap-8 lg:grid-cols-[minmax(0,0.9fr)_minmax(0,1.1fr)] lg:items-center">
      <div class="min-w-0">
        <h1 class="mb-2 text-[26px] font-bold leading-tight sm:text-[34px] lg:text-[38px]">
          Catálogo de Productos
        </h1>
        <p class="max-w-[34rem] text-[13px] leading-relaxed text-brand-gray-400 sm:text-[14px]">
          Encuentra la llanta ideal para tu vehículo o maquinaria.<br class="hidden md:block" />
          Contamos con las mejores marcas y al mejor precio.
        </p>
      </div>

      <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-4">
        <div
          v-for="prop in store.valueProps"
          :key="prop.title"
          class="flex min-w-0 items-center gap-3"
        >
          <div class="flex h-[42px] w-[42px] shrink-0 items-center justify-center text-brand-yellow">
            <HeroIcon v-if="isHeroIcon(prop.icon)" :name="heroIconName(prop.icon)" class="h-8 w-8" />
            <i v-else :class="prop.icon" class="text-2xl" />
          </div>
          <div class="min-w-0">
            <h4 class="text-[12px] font-extrabold uppercase text-white sm:text-[13px]">
              {{ prop.title }}
            </h4>
            <p class="mt-0.5 text-[13px] text-white">{{ prop.subtitle }}</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
