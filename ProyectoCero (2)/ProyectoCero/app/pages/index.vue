<script setup lang="ts">
useSeoMeta({
  title: 'Andes Llantas — Llantas para minería, agrícola e industrial',
  description: 'Distribuidor de llantas industriales en Perú. Marcas líderes para minería, agricultura e industria. Envíos a todo el país.',
  ogTitle: 'Andes Llantas — Llantas industriales en Perú',
  ogDescription: 'Distribuidor de llantas para minería, agrícola e industrial. Las mejores marcas al mejor precio.',
  ogType: 'website',
})
</script>

<template>
  <div>
    <div class="relative pb-[18rem] sm:pb-44 lg:pb-16">
      <LayoutTheHero />
      <div class="absolute bottom-0 left-1/2 z-20 w-full max-w-4xl -translate-x-1/2 translate-y-1/2 px-4 sm:px-6">
        <LayoutTheTireSearch />
      </div>
    </div>

    <div class="mt-0 lg:mt-16">
      <SectionsHomePromociones />
      <SectionsHomeTecnologia />
      <SectionsHomeMarcas />
      <SectionsHomeTipoVehiculo />
      <SectionsHomeComoComprar />
      <SectionsHomeContactoCTA />
    </div>
  </div>
</template>
