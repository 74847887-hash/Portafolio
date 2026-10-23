<!-- app/components/shop/ProductCardOTR.vue -->
<script setup lang="ts">
import type { ProductoResumen } from '~/types'
import { WHATSAPP_URL } from '~/utils/constants'

const props = defineProps<{
    producto: ProductoResumen
}>()

const specsVisible = ref(false)

const whatsappMessage = encodeURIComponent(
    `Hola, estoy interesado en: ${props.producto.nombre} (${props.producto.medida}). ¿Tienen disponibilidad?`
)
const whatsappLink = `${WHATSAPP_URL}?text=${whatsappMessage}`

const precioFormateado = computed(() => {
    return `S/ ${Number(props.producto.precio).toLocaleString('es-PE', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    })}`
})

const specs = computed(() => [
    { label: 'Profundidad de cocada', value: props.producto.profCocada },
    { label: 'Ancho de banda', value: props.producto.anchoBanda },
    { label: 'Eje', value: props.producto.eje },
    { label: 'Operación', value: props.producto.operacion },
].filter(s => s.value))
</script>

<template>
    <div class="flex flex-col overflow-hidden rounded-xl border border-gray-100 bg-white shadow-sm transition-shadow duration-300 hover:shadow-md cursor-pointer"
        @mouseenter="specsVisible = true" @mouseleave="specsVisible = false" @click="specsVisible = !specsVisible">
        <!-- Imagen + logo + info -->
        <div class="flex gap-3 p-4">
            <!-- Imagen -->
            <div class="flex h-[120px] w-[100px] shrink-0 items-center justify-center">
                <img :src="producto.imagenPrincipalUrl ?? '/images/products/primera.jpg'" :alt="producto.nombre"
                    class="max-h-[110px] w-full object-contain" loading="lazy" />
            </div>

            <!-- Info -->
            <div class="flex min-w-0 flex-1 flex-col gap-1.5 pt-1">
                <!-- Logo marca -->
                <img :src="producto.marcaLogoUrl ?? ''" :alt="producto.marca" class="h-5 object-contain self-start" />

                <!-- Nombre -->
                <h3 class="text-[14px] font-black text-gray-900 leading-tight line-clamp-2">
                    {{ producto.nombre }}
                </h3>

                <!-- Medida -->
                <p class="text-[12px] text-gray-500 font-medium">{{ producto.medida }}</p>

                <!-- Precio -->
                <div class="mt-auto">
                    <p class="text-[10px] uppercase tracking-wider text-gray-400">Desde</p>
                    <p class="text-[20px] font-black text-gray-900 leading-tight">{{ precioFormateado }}</p>
                    <p class="text-[10px] text-gray-400">Incl. IGV</p>
                </div>
            </div>
        </div>

        <!-- Botón WhatsApp -->
        <div class="px-4 pb-3">
            <a :href="whatsappLink" target="_blank" rel="noopener noreferrer"
                class="flex w-full items-center justify-center gap-2 rounded-lg bg-brand-yellow py-2.5 text-[13px] font-bold text-black hover:brightness-110 transition-all"
                @click.stop>
                <i class="fa-brands fa-whatsapp text-[15px]" />
                Cotizar por WhatsApp
            </a>
        </div>

        <!-- Specs técnicas — desplegables -->
        <Transition name="specs">
            <div v-if="specsVisible && specs.length" class="border-t border-gray-100 px-4 pb-3 pt-3">
                <div class="mb-2 flex items-center justify-between">
                    <p class="flex items-center gap-1.5 text-[11px] font-bold uppercase tracking-wider text-gray-500">
                        <i class="fa-solid fa-gears text-brand-yellow" />
                        Especificaciones Técnicas
                    </p>
                    <i class="fa-solid fa-chevron-up text-[10px] text-gray-400" />
                </div>
                <div class="flex flex-col gap-2">
                    <div v-for="spec in specs" :key="spec.label"
                        class="grid grid-cols-[1fr_auto] items-center border-b border-gray-50 pb-1.5 last:border-0 last:pb-0">
                        <span class="text-[11px] text-gray-500">{{ spec.label }}</span>
                        <span class="text-[12px] font-semibold text-gray-800">{{ spec.value }}</span>
                    </div>
                </div>
            </div>
        </Transition>

        <!-- Ver detalle -->
        <div class="mt-auto border-t border-gray-100">
            <NuxtLink :to="`/catalogo/${producto.slug}`"
                class="flex w-full items-center justify-center gap-2 py-2.5 text-[12px] font-bold text-gray-600 hover:bg-gray-50 transition-all">
                <i class="fa-solid fa-eye text-[11px]" />
                Ver detalle
                <i class="fa-solid fa-chevron-right text-[10px]" />
            </NuxtLink>
        </div>
    </div>
</template>

<style scoped>
.specs-enter-active,
.specs-leave-active {
    transition: all 0.25s ease;
}

.specs-enter-from,
.specs-leave-to {
    opacity: 0;
    max-height: 0;
}

.specs-enter-to,
.specs-leave-from {
    opacity: 1;
    max-height: 200px;
}
</style>