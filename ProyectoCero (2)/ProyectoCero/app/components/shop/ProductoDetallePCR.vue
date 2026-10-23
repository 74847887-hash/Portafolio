<!-- app/components/shop/ProductoDetallePCR.vue -->
<script setup lang="ts">
import type { ProductoDetalleResponse, ProductoResumen } from '~/types'
import { WHATSAPP_URL } from '~/utils/constants'

const props = defineProps<{
    producto: ProductoDetalleResponse
}>()

const p = computed(() => props.producto)
const config = useRuntimeConfig()
const activeImage = ref(0)

/* ================================
   FICHA TÉCNICA PDF
================================ */
const mostrarFichaTecnica = ref(false)

const fichaTecnicaUrl = computed(() => {
    const producto: any = p.value

    return producto.fichaTecnicaUrl
        || producto.ficha_tecnica_url
        || ''
})

function abrirFichaTecnica() {
    if (!fichaTecnicaUrl.value) return
    mostrarFichaTecnica.value = true
}

function cerrarFichaTecnica() {
    mostrarFichaTecnica.value = false
}

/* Productos relacionados */
const { data: relacionados } = await useAsyncData(
    `relacionados-${p.value.slug}`,
    () => $fetch<ProductoResumen[]>(`/productos/${p.value.slug}/relacionados`, {
        baseURL: config.public.apiBase,
    }),
    {
        default: () => [] as ProductoResumen[]
    }
)

const breadcrumb = computed(() => [
    { label: 'Inicio', to: '/' },
    { label: 'Catálogo', to: '/catalogo' },
    { label: p.value.marca, to: `/catalogo?marca=${p.value.marca}` },
    { label: p.value.nombre },
])

const whatsappMessage = computed(() =>
    encodeURIComponent(
        `Hola, estoy interesado en: ${p.value.nombre} (${p.value.medida}). ¿Tienen disponibilidad?`
    )
)

const whatsappLink = computed(() =>
    `${WHATSAPP_URL}?text=${whatsappMessage.value}`
)

const precioFormateado = computed(() =>
    `S/ ${Number(p.value.precio).toLocaleString('es-PE', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    })}`
)

const precioRelacionado = (precio: number) =>
    `S/ ${Number(precio).toLocaleString('es-PE', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    })}`

useSeoMeta({
    title: `${p.value.nombre} — Andes Llantas`,
    description:
        p.value.descripcion ??
        `${p.value.nombre} ${p.value.medida}. Disponible en Andes Llantas.`,
})
</script>

<template>
    <div class="min-h-screen bg-gray-50">

        <!-- Breadcrumb -->
        <div
            class="mx-auto flex max-w-7xl flex-wrap items-center gap-1 px-4 py-3 text-xs text-gray-500 sm:px-6"
        >
            <template v-for="(item, index) in breadcrumb" :key="index">
                <NuxtLink
                    v-if="item.to"
                    :to="item.to"
                    class="transition-colors hover:text-brand-yellow"
                >
                    {{ item.label }}
                </NuxtLink>

                <span
                    v-else
                    class="max-w-[200px] truncate text-gray-400"
                >
                    {{ item.label }}
                </span>

                <span
                    v-if="index < breadcrumb.length - 1"
                    class="text-gray-300"
                >
                    ›
                </span>
            </template>
        </div>

        <div class="mx-auto max-w-7xl px-4 pb-12 sm:px-6">

            <!-- Grid superior -->
            <div class="grid grid-cols-1 gap-8 lg:grid-cols-2">

                <!-- Galería -->
                <div class="flex flex-col gap-3">

                    <!-- Imagen principal -->
                    <div
                        class="relative flex min-h-[320px] items-center justify-center overflow-hidden rounded-xl border border-gray-100 bg-white"
                    >

                        <button
                            v-if="p.imagenes.length > 1"
                            class="absolute left-3 top-1/2 z-10 flex h-8 w-8 -translate-y-1/2 items-center justify-center rounded-full border border-gray-200 bg-white shadow-sm transition-all hover:bg-gray-50"
                            @click="
                                activeImage =
                                    activeImage > 0
                                        ? activeImage - 1
                                        : p.imagenes.length - 1
                            "
                        >
                            <i class="fa-solid fa-chevron-left text-[11px] text-gray-600" />
                        </button>

                        <img
                            v-if="p.imagenes.length"
                            :src="p.imagenes[activeImage]?.url"
                            :alt="p.nombre"
                            class="max-h-[360px] w-full object-contain p-8"
                        />

                        <div
                            v-else
                            class="flex items-center justify-center text-gray-200"
                        >
                            <i class="fa-solid fa-image text-6xl" />
                        </div>

                        <button
                            v-if="p.imagenes.length > 1"
                            class="absolute right-3 top-1/2 z-10 flex h-8 w-8 -translate-y-1/2 items-center justify-center rounded-full border border-gray-200 bg-white shadow-sm transition-all hover:bg-gray-50"
                            @click="
                                activeImage =
                                    activeImage < p.imagenes.length - 1
                                        ? activeImage + 1
                                        : 0
                            "
                        >
                            <i class="fa-solid fa-chevron-right text-[11px] text-gray-600" />
                        </button>

                        <span
                            class="absolute left-3 top-3 text-[11px] font-semibold text-gray-400"
                        >
                            {{ activeImage + 1 }} / {{ p.imagenes.length }}
                        </span>
                    </div>

                    <!-- Miniaturas -->
                    <div class="flex gap-2 overflow-x-auto">
                        <button
                            v-for="(img, i) in p.imagenes"
                            :key="i"
                            class="h-16 w-16 shrink-0 overflow-hidden rounded-lg border-2 transition-all"
                            :class="
                                activeImage === i
                                    ? 'border-brand-yellow'
                                    : 'border-gray-200 hover:border-gray-400'
                            "
                            @click="activeImage = i"
                        >
                            <img
                                :src="img.url"
                                :alt="`Vista ${i + 1}`"
                                class="h-full w-full object-contain p-1"
                            />
                        </button>
                    </div>
                </div>

                <!-- Información derecha -->
                <div class="flex flex-col gap-5">

                    <!-- Logo + nombre + medida -->
                    <div>
                        <img
                            v-if="p.marcaLogoUrl"
                            :src="p.marcaLogoUrl"
                            :alt="p.marca"
                            class="mb-3 h-8 self-start object-contain"
                        />

                        <h1
                            class="text-2xl font-black leading-tight text-gray-900 sm:text-3xl"
                        >
                            {{ p.nombre }}
                        </h1>

                        <p class="mt-1 text-[15px] font-medium text-gray-400">
                            {{ p.medida }}
                        </p>
                    </div>

                    <!-- Precio + disponibilidad -->
                    <div>
                        <p
                            class="text-[36px] font-black leading-none text-gray-900"
                        >
                            {{ precioFormateado }}
                        </p>

                        <p class="mt-1 text-[12px] text-gray-400">
                            Precio por unidad
                        </p>

                        <div class="mt-2 flex items-center gap-1.5">
                            <span class="h-2 w-2 rounded-full bg-green-500" />

                            <span class="text-[13px] font-semibold text-green-600">
                                Disponible
                            </span>

                            <span class="text-[12px] text-gray-400">
                                — Listo para envío
                            </span>
                        </div>
                    </div>

                    <!-- WhatsApp -->
                    <a
                        :href="whatsappLink"
                        target="_blank"
                        rel="noopener noreferrer"
                        class="flex items-center justify-center gap-2 rounded-lg bg-brand-yellow py-3.5 text-[15px] font-black text-black transition-all hover:brightness-110"
                    >
                        <i class="fa-brands fa-whatsapp text-[18px]" />
                        Cotizar por WhatsApp
                    </a>

                    <!-- FICHA TÉCNICA -->
                    <button
                        v-if="fichaTecnicaUrl"
                        type="button"
                        class="flex items-center justify-center gap-2 rounded-lg py-3.5 text-[15px] font-black text-white transition-all hover:brightness-95"
                        style="background-color: #1bd1c1;"
                        @click="abrirFichaTecnica"
                    >
                        <i class="fa-solid fa-file-pdf text-[18px]" />
                        Ver ficha técnica PDF
                    </button>

                    <!-- Treadwear / Tracción / Temperatura -->
                    <div
                        v-if="p.treadwear || p.traccion || p.temperatura"
                        class="grid grid-cols-3 gap-3 rounded-xl border border-gray-100 bg-white p-4"
                    >

                        <!-- Treadwear -->
                        <div
                            v-if="p.treadwear"
                            class="flex flex-col items-center gap-2"
                        >
                            <svg
                                viewBox="0 0 48 48"
                                class="h-12 w-12"
                                fill="none"
                            >
                                <circle
                                    cx="24"
                                    cy="24"
                                    r="20"
                                    stroke="#16a34a"
                                    stroke-width="2.5"
                                />
                                <circle
                                    cx="24"
                                    cy="24"
                                    r="13"
                                    stroke="#16a34a"
                                    stroke-width="2"
                                />
                                <rect
                                    x="22"
                                    y="4"
                                    width="4"
                                    height="6"
                                    rx="1"
                                    fill="#16a34a"
                                />
                                <rect
                                    x="22"
                                    y="38"
                                    width="4"
                                    height="6"
                                    rx="1"
                                    fill="#16a34a"
                                />
                                <rect
                                    x="4"
                                    y="22"
                                    width="6"
                                    height="4"
                                    rx="1"
                                    fill="#16a34a"
                                />
                                <rect
                                    x="38"
                                    y="22"
                                    width="6"
                                    height="4"
                                    rx="1"
                                    fill="#16a34a"
                                />
                            </svg>

                            <p
                                class="text-[10px] font-bold uppercase tracking-wider text-green-600"
                            >
                                Treadwear
                            </p>

                            <p class="text-[20px] font-black text-gray-900">
                                {{ p.treadwear }}
                            </p>
                        </div>

                        <!-- Tracción -->
                        <div
                            v-if="p.traccion"
                            class="flex flex-col items-center gap-2"
                        >
                            <svg
                                viewBox="0 0 48 48"
                                class="h-12 w-12"
                                fill="none"
                            >
                                <circle
                                    cx="24"
                                    cy="24"
                                    r="20"
                                    stroke="#2563eb"
                                    stroke-width="2.5"
                                />
                                <path
                                    d="M14 30 Q24 10 34 30"
                                    stroke="#2563eb"
                                    stroke-width="2.5"
                                    fill="none"
                                    stroke-linecap="round"
                                />
                                <circle
                                    cx="24"
                                    cy="30"
                                    r="3"
                                    fill="#2563eb"
                                />
                                <path
                                    d="M18 34 Q24 28 30 34"
                                    stroke="#2563eb"
                                    stroke-width="2"
                                    fill="none"
                                    stroke-linecap="round"
                                />
                            </svg>

                            <p
                                class="text-[10px] font-bold uppercase tracking-wider text-blue-600"
                            >
                                Tracción
                            </p>

                            <p class="text-[20px] font-black text-gray-900">
                                {{ p.traccion }}
                            </p>
                        </div>

                        <!-- Temperatura -->
                        <div
                            v-if="p.temperatura"
                            class="flex flex-col items-center gap-2"
                        >
                            <svg
                                viewBox="0 0 48 48"
                                class="h-12 w-12"
                                fill="none"
                            >
                                <circle
                                    cx="24"
                                    cy="24"
                                    r="20"
                                    stroke="#dc2626"
                                    stroke-width="2.5"
                                />
                                <rect
                                    x="21"
                                    y="10"
                                    width="6"
                                    height="20"
                                    rx="3"
                                    stroke="#dc2626"
                                    stroke-width="2"
                                />
                                <circle
                                    cx="24"
                                    cy="33"
                                    r="5"
                                    stroke="#dc2626"
                                    stroke-width="2"
                                    fill="#dc2626"
                                    fill-opacity="0.2"
                                />
                                <line
                                    x1="28"
                                    y1="16"
                                    x2="32"
                                    y2="16"
                                    stroke="#dc2626"
                                    stroke-width="1.5"
                                    stroke-linecap="round"
                                />
                                <line
                                    x1="28"
                                    y1="20"
                                    x2="31"
                                    y2="20"
                                    stroke="#dc2626"
                                    stroke-width="1.5"
                                    stroke-linecap="round"
                                />
                                <line
                                    x1="28"
                                    y1="24"
                                    x2="32"
                                    y2="24"
                                    stroke="#dc2626"
                                    stroke-width="1.5"
                                    stroke-linecap="round"
                                />
                            </svg>

                            <p
                                class="text-[10px] font-bold uppercase tracking-wider text-red-600"
                            >
                                Temperatura
                            </p>

                            <p class="text-[20px] font-black text-gray-900">
                                {{ p.temperatura }}
                            </p>
                        </div>
                    </div>

                    <!-- 6 specs -->
                    <div class="grid grid-cols-3 gap-3 sm:grid-cols-6">
                        <div
                            v-for="spec in [
                                { icon: 'fa-solid fa-arrows-left-right', label: 'Ancho', unit: 'mm', value: p.ancho },
                                { icon: 'fa-solid fa-percent', label: 'Perfil', unit: '%', value: p.perfil },
                                { icon: 'fa-solid fa-circle-dot', label: 'Aro', unit: 'pulgadas', value: p.aro ? `R${p.aro}` : null },
                                { icon: 'fa-solid fa-gauge-high', label: 'Índice de velocidad', unit: null, value: p.indiceVelocidad },
                                { icon: 'fa-solid fa-weight-hanging', label: 'Índice de carga', unit: null, value: p.indiceCarga },
                            ].filter(s => s.value)"
                            :key="spec.label"
                            class="flex flex-col items-center gap-1.5 rounded-lg border border-gray-100 bg-white p-3 text-center"
                        >
                            <i
                                :class="[spec.icon, 'text-gray-400 text-lg']"
                            />

                            <p class="text-[16px] font-black text-gray-900">
                                {{ spec.value }}
                            </p>

                            <p
                                class="text-[9px] uppercase tracking-wider text-gray-400"
                            >
                                {{ spec.label }}
                            </p>

                            <p
                                v-if="spec.unit"
                                class="text-[9px] text-gray-300"
                            >
                                {{ spec.unit }}
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Tabla especificaciones -->
            <div
                class="mt-8 overflow-hidden rounded-xl border border-gray-100 bg-white shadow-sm"
            >
                <div class="border-b border-gray-100 px-6 py-4">
                    <p
                        class="text-[13px] font-black uppercase tracking-wider text-gray-800"
                    >
                        Especificaciones Técnicas
                    </p>
                </div>

                <div
                    class="grid grid-cols-1 divide-y divide-gray-50 sm:grid-cols-2 sm:divide-y-0"
                >
                    <div
                        v-for="spec in [
                            { label: 'Marca', value: p.marca },
                            { label: 'Medida', value: p.medida },
                            { label: 'Ancho', value: p.ancho ? `${p.ancho} mm` : null },
                            { label: 'Perfil', value: p.perfil ? `${p.perfil}` : null },
                            { label: 'Aro', value: p.aro ? `R${p.aro}` : null },
                            { label: 'Aplicación', value: p.categoria },
                            { label: 'Treadwear', value: p.treadwear },
                            { label: 'Tracción', value: p.traccion },
                            { label: 'Temperatura', value: p.temperatura },
                            { label: 'IV', value: p.indiceVelocidad },
                            { label: 'IC', value: p.indiceCarga },
                            { label: 'Construcción', value: p.construccion },
                        ].filter(s => s.value)"
                        :key="spec.label"
                        class="flex items-center justify-between border-b border-gray-50 px-6 py-3 odd:bg-gray-50"
                    >
                        <span class="text-[12px] text-gray-500">
                            {{ spec.label }}
                        </span>

                        <span class="text-[12px] font-bold text-gray-800">
                            {{ spec.value }}
                        </span>
                    </div>
                </div>
            </div>

            <!-- Razones para elegir -->
            <div
                v-if="p.descripcion"
                class="mt-8 grid grid-cols-1 items-center gap-6 lg:grid-cols-2"
            >
                <div>
                    <h2 class="mb-3 text-[18px] font-black text-gray-900">
                        Razones para elegir {{ p.nombre }}
                    </h2>

                    <p class="text-sm leading-relaxed text-gray-600">
                        {{ p.descripcion }}
                    </p>
                </div>

                <div
                    class="flex min-h-[200px] items-center justify-center overflow-hidden rounded-xl bg-gray-100"
                >
                    <img
                        v-if="p.imagenes.length"
                        :src="p.imagenes[0]?.url"
                        :alt="p.nombre"
                        class="max-h-[240px] w-full object-contain p-6"
                    />
                </div>
            </div>

            <!-- Marcas compatibles -->
            <div class="mt-8 rounded-xl bg-black p-6">
                <h2
                    class="mb-4 text-[13px] font-black uppercase tracking-wider text-white"
                >
                    Marcas de vehículos principales compatibles
                </h2>

                <div class="flex flex-wrap gap-3">
                    <div
                        v-for="marca in ['Toyota', 'Hyundai', 'Kia', 'Chevrolet', 'Honda', 'Nissan']"
                        :key="marca"
                        class="flex items-center gap-2 rounded-lg border border-gray-700 px-4 py-2"
                    >
                        <i class="fa-solid fa-car text-sm text-gray-400" />

                        <span class="text-[12px] font-semibold text-white">
                            {{ marca }}
                        </span>
                    </div>
                </div>
            </div>

            <!-- Productos relacionados -->
            <div v-if="relacionados?.length" class="mt-8">
                <div class="mb-4 flex items-center justify-between">
                    <h2 class="text-[16px] font-black text-gray-900">
                        Productos relacionados
                    </h2>

                    <NuxtLink
                        :to="`/catalogo?categoria=${p.categoriaSlug}`"
                        class="text-[12px] font-semibold text-brand-yellow hover:underline"
                    >
                        Ver más productos →
                    </NuxtLink>
                </div>

                <div class="grid grid-cols-2 gap-4 sm:grid-cols-4">
                    <NuxtLink
                        v-for="rel in relacionados"
                        :key="rel.id"
                        :to="`/catalogo/${rel.slug}`"
                        class="flex flex-col gap-2 rounded-xl border border-gray-100 bg-white p-4 transition-all hover:shadow-md"
                    >
                        <img
                            :src="
                                rel.imagenPrincipalUrl ??
                                '/images/products/primera.jpg'
                            "
                            :alt="rel.nombre"
                            class="h-24 w-full object-contain"
                        />

                        <p
                            class="line-clamp-2 text-[12px] font-bold leading-tight text-gray-900"
                        >
                            {{ rel.nombre }}
                        </p>

                        <p class="text-[11px] text-gray-400">
                            {{ rel.medida }}
                        </p>

                        <p class="mt-auto text-[15px] font-black text-gray-900">
                            {{ precioRelacionado(rel.precio) }}
                        </p>

                        <img
                            v-if="rel.marcaLogoUrl"
                            :src="rel.marcaLogoUrl"
                            :alt="rel.marca"
                            class="h-4 self-start object-contain"
                        />
                    </NuxtLink>
                </div>
            </div>

            <!-- Footer garantías -->
            <div class="mt-8 grid grid-cols-2 gap-4 sm:grid-cols-4">
                <div
                    v-for="item in [
                        { icon: 'fa-solid fa-truck', title: 'Envíos a todo el Perú', sub: 'Llegamos a cualquier ciudad' },
                        { icon: 'fa-solid fa-certificate', title: 'Productos originales', sub: 'Solo trabajamos con marcas líderes' },
                        { icon: 'fa-solid fa-headset', title: 'Asesoría personalizada', sub: 'Te ayudamos a elegir la llanta ideal' },
                        { icon: 'fa-solid fa-shield-halved', title: 'Pago seguro', sub: 'Transferencias bancarias seguras' },
                    ]"
                    :key="item.title"
                    class="flex flex-col items-center gap-2 rounded-xl border border-gray-100 bg-white p-4 text-center"
                >
                    <i
                        :class="[item.icon, 'text-brand-yellow text-2xl']"
                    />

                    <p class="text-[12px] font-bold text-gray-800">
                        {{ item.title }}
                    </p>

                    <p class="text-[11px] leading-tight text-gray-400">
                        {{ item.sub }}
                    </p>
                </div>
            </div>
        </div>

        <!-- ============================================
             VISOR PDF A PANTALLA COMPLETA
        ============================================= -->
        <Teleport to="body">
            <div
                v-if="mostrarFichaTecnica && fichaTecnicaUrl"
                class="fixed inset-0 z-[9999] flex flex-col bg-black"
            >

                <!-- Barra superior -->
                <div
                    class="flex shrink-0 items-center justify-between gap-3 bg-gray-950 px-4 py-3"
                >
                    <button
                        type="button"
                        class="flex items-center gap-2 rounded-lg bg-white px-4 py-2 text-sm font-bold text-gray-900 transition-all hover:bg-gray-100"
                        @click="cerrarFichaTecnica"
                    >
                        <i class="fa-solid fa-arrow-left" />
                        Volver al producto
                    </button>

                    <div class="flex items-center gap-2">
                        <a
                            :href="fichaTecnicaUrl"
                            target="_blank"
                            rel="noopener noreferrer"
                            class="flex items-center gap-2 rounded-lg px-4 py-2 text-sm font-bold text-white transition-all hover:brightness-95"
                            style="background-color: #1bd1c1;"
                        >
                            <i class="fa-solid fa-download" />
                            Descargar PDF
                        </a>

                        <button
                            type="button"
                            class="flex h-10 w-10 items-center justify-center rounded-lg bg-white text-gray-900 transition-all hover:bg-gray-100"
                            title="Cerrar"
                            @click="cerrarFichaTecnica"
                        >
                            <i class="fa-solid fa-xmark text-lg" />
                        </button>
                    </div>
                </div>

                <!-- PDF -->
                <div class="min-h-0 flex-1 bg-gray-800">
                    <iframe
                        :src="fichaTecnicaUrl"
                        class="h-full w-full border-0"
                        title="Ficha técnica del producto"
                    />
                </div>
            </div>
        </Teleport>
    </div>
</template>
