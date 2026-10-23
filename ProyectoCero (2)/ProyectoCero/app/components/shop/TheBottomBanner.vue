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
    class="relative flex flex-col items-center justify-center gap-10 bg-cover bg-center px-5 py-10 text-white md:flex-row md:gap-[60px] md:px-10"
    :style="{
      backgroundImage:
        'linear-gradient(rgba(0,0,0,0.82), rgba(0,0,0,0.88)), url(https://images.unsplash.com/photo-1578844251758-2f71da64c96f?q=80&w=1600&auto=format&fit=crop)',
    }"
  >
    <div class="md:ml-[-100px]">
      <h1 class="mb-2 text-[26px] font-bold md:text-[38px] md:mr-[100px]">
        Catálogo de Productos
      </h1>
      <p class="text-[13px] leading-relaxed text-brand-gray-400 md:text-[14px] md:mr-[100px]">
        Encuentra la llanta ideal para tu vehículo o maquinaria.<br class="hidden md:block" />
        Contamos con las mejores marcas y al mejor precio.
      </p>
    </div>

    <div class="flex flex-col gap-5 md:flex-row md:justify-end md:gap-[70px]">
      <div
        v-for="prop in store.valueProps"
        :key="prop.title"
        class="flex items-center gap-3"
      >
        <div class="flex h-[42px] w-[42px] items-center justify-center text-brand-yellow">
          <HeroIcon v-if="isHeroIcon(prop.icon)" :name="heroIconName(prop.icon)" class="h-8 w-8" />
          <i v-else :class="prop.icon" class="text-2xl" />
        </div>
        <div>
          <h4 class="text-[13px] font-extrabold uppercase tracking-[0.5px] text-white">
            {{ prop.title }}
          </h4>
          <p class="mt-0.5 text-[13px] text-white">{{ prop.subtitle }}</p>
        </div>
      </div>
    </div>
  </section>
</template>
