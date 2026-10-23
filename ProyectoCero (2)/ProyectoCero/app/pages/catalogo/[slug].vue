<!-- app/pages/catalogo/[slug].vue -->
<script setup lang="ts">
import type { ProductoDetalleResponse } from '~/types'
import { esOtrTbr } from '~/types'

const route = useRoute()
const slug = route.params.slug as string
const config = useRuntimeConfig()

const { data: producto, error } = await useAsyncData(
  `producto-${slug}`,
  () => $fetch<ProductoDetalleResponse>(`/productos/${slug}`, {
    baseURL: config.public.apiBase,
  })  
)

if (error.value || !producto.value) {
  throw createError({ statusCode: 404, statusMessage: 'Producto no encontrado' })
}

const esOtr = computed(() => esOtrTbr(producto.value!.categoriaSlug as any))
</script>

<template>
  <div>
    <ShopProductoDetalleOTR v-if="esOtr" :producto="producto!" />
    <ShopProductoDetallePCR v-else :producto="producto!" />
  </div>
</template>