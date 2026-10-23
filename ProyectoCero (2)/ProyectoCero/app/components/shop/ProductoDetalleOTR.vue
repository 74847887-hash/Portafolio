<!-- app/components/shop/ProductoDetalleOTR.vue -->
<script setup lang="ts">
import type { ProductoDetalleResponse, ProductoResumen } from '~/types'
import { WHATSAPP_URL } from '~/utils/constants'

const props = defineProps<{
    producto: ProductoDetalleResponse
}>()

const p = computed(() => props.producto)
const activeImage = ref(0)
const config = useRuntimeConfig()

/* ==========================================
   FICHA TÉCNICA PDF
========================================== */

const mostrarFichaTecnica = ref(false)

const fichaTecnicaUrl = computed(() => {
    const producto: any = p.value

    return (
        producto.fichaTecnicaUrl ||
        producto.ficha_tecnica_url ||
        ''
    )
})

function abrirFichaTecnica() {
    if (!fichaTecnicaUrl.value) return

    mostrarFichaTecnica.value = true
}

function cerrarFichaTecnica() {
    mostrarFichaTecnica.value = false
}

/* ==========================================
   BREADCRUMB
========================================== */

const breadcrumb = computed(() => [
    {
        label: 'Inicio',
        to: '/'
    },
    {
        label: 'Catálogo',
        to: '/catalogo'
    },
    {
        label: p.value.marca,
        to: `/catalogo?marca=${p.value.marca}`
    },
    {
        label: p.value.nombre
    },
])

/* ==========================================
   WHATSAPP
========================================== */

const whatsappMessage = computed(() =>
    encodeURIComponent(
        `Hola, estoy interesado en: ${p.value.nombre} (${p.value.medida}). ¿Tienen disponibilidad?`
    )
)

const whatsappLink = computed(() =>
    `${WHATSAPP_URL}?text=${whatsappMessage.value}`
)

/* ==========================================
   PRECIO
========================================== */

const precioFormateado = computed(() =>
    `S/ ${Number(p.value.precio).toLocaleString('es-PE', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    })}`
)

/* ==========================================
   APLICACIÓN
========================================== */

const aplicacionTexto = computed(() => {
    return p.value.categoria || null
})

/* ==========================================
   PRODUCTOS RELACIONADOS
========================================== */

const { data: productosRelacionados } = await useAsyncData(
    `relacionados-${p.value.slug}`,
    () => $fetch<ProductoResumen[]>(`/productos/${p.value.slug}/relacionados`, {
        baseURL: config.public.apiBase,
    }),
    {
        default: () => [] as ProductoResumen[]
    }
)

/* ==========================================
   MARCAS COMPATIBLES
========================================== */

const marcasCompatibles = [
    {
        nombre: 'Toyota',
        icon: 'fa-solid fa-car'
    },
    {
        nombre: 'Hyundai',
        icon: 'fa-solid fa-car'
    },
    {
        nombre: 'Kia',
        icon: 'fa-solid fa-car'
    },
    {
        nombre: 'Chevrolet',
        icon: 'fa-solid fa-car'
    },
    {
        nombre: 'Honda',
        icon: 'fa-solid fa-car'
    },
    {
        nombre: 'Nissan',
        icon: 'fa-solid fa-car'
    },
]

/* ==========================================
   BENEFICIOS
========================================== */

const beneficios = [
    {
        icon: 'fa-solid fa-truck',
        titulo: 'Envíos a todo el Perú',
        descripcion: 'Llegamos a cualquier ciudad'
    },
    {
        icon: 'fa-solid fa-certificate',
        titulo: 'Productos originales',
        descripcion: 'Solo trabajamos con marcas líderes'
    },
    {
        icon: 'fa-solid fa-headset',
        titulo: 'Asesoría personalizada',
        descripcion: 'Te ayudamos a elegir la llanta ideal'
    },
    {
        icon: 'fa-solid fa-shield-halved',
        titulo: 'Pago seguro',
        descripcion: 'Transferencias bancarias seguras'
    },
]

/* ==========================================
   SEO
========================================== */

useSeoMeta({
    title: `${p.value.nombre} — Andes Llantas`,
    description:
        p.value.descripcion ??
        `${p.value.nombre} ${p.value.medida}. Disponible en Andes Llantas.`,
})
</script>

<template>
    <div class="min-h-screen bg-gray-50">

        <!-- ==========================================
             BREADCRUMB
        =========================================== -->

        <div
            class="mx-auto flex max-w-7xl flex-wrap items-center gap-1 px-4 py-3 text-xs text-gray-500 sm:px-6"
        >
            <template
                v-for="(item, index) in breadcrumb"
                :key="index"
            >
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

        <!-- ==========================================
             CONTENIDO PRINCIPAL
        =========================================== -->

        <div class="mx-auto max-w-7xl px-4 pb-12 sm:px-6">

            <!-- ==========================================
                 GRID PRINCIPAL
            =========================================== -->

            <div
                class="grid grid-cols-1 gap-6 lg:grid-cols-[240px_1fr_300px]"
            >

                <!-- ==========================================
                     GALERÍA
                =========================================== -->

                <div class="flex flex-row gap-3 lg:flex-col">

                    <!-- Miniaturas -->
                    <div class="flex flex-col gap-2">

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

                        <!-- Video -->
                        <button
                            v-if="p.videoUrl"
                            type="button"
                            class="flex h-16 w-16 shrink-0 flex-col items-center justify-center gap-1 rounded-lg border-2 border-gray-200 transition-all hover:border-gray-400"
                        >
                            <i
                                class="fa-solid fa-play text-sm text-gray-500"
                            />

                            <span
                                class="text-[9px] font-semibold text-gray-400"
                            >
                                Ver video
                            </span>
                        </button>

                    </div>

                    <!-- Imagen principal -->
                    <div
                        class="flex min-h-[280px] flex-1 items-center justify-center overflow-hidden rounded-xl border border-gray-100 bg-white"
                    >
                        <img
                            v-if="p.imagenes.length"
                            :src="p.imagenes[activeImage]?.url"
                            :alt="p.nombre"
                            class="max-h-[320px] w-full object-contain p-6"
                        />

                        <div
                            v-else
                            class="flex items-center justify-center text-gray-200"
                        >
                            <i class="fa-solid fa-image text-6xl" />
                        </div>
                    </div>

                </div>

                <!-- ==========================================
                     INFORMACIÓN CENTRAL
                =========================================== -->

                <div class="flex flex-col gap-5">

                    <!-- Logo + nombre + descripción -->
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

                        <p
                            v-if="p.descripcion"
                            class="mt-2 text-sm leading-relaxed text-gray-500"
                        >
                            {{ p.descripcion }}
                        </p>

                    </div>

                    <!-- ==========================================
                         4 SPECS OTR
                    =========================================== -->

                    <div
                        class="grid grid-cols-2 gap-3 sm:grid-cols-4"
                    >
                        <div
                            v-for="spec in [
                                {
                                    icon: 'fa-solid fa-ruler-vertical',
                                    label: 'Profundidad de cocada',
                                    value: p.profCocada
                                },
                                {
                                    icon: 'fa-solid fa-arrows-left-right',
                                    label: 'Ancho de banda',
                                    value: p.anchoBanda
                                },
                                {
                                    icon: 'fa-solid fa-circle-dot',
                                    label: 'Eje',
                                    value: p.eje
                                },
                                {
                                    icon: 'fa-solid fa-road',
                                    label: 'Operación',
                                    value: p.operacion
                                },
                            ].filter(s => s.value)"
                            :key="spec.label"
                            class="flex flex-col items-center gap-2 rounded-lg border border-gray-100 bg-white p-3 text-center shadow-sm"
                        >
                            <i
                                :class="[
                                    spec.icon,
                                    'text-xl text-brand-yellow'
                                ]"
                            />

                            <p
                                class="text-[10px] uppercase leading-tight tracking-wider text-gray-400"
                            >
                                {{ spec.label }}
                            </p>

                            <p
                                class="text-[14px] font-black uppercase text-gray-900"
                            >
                                {{ spec.value }}
                            </p>
                        </div>
                    </div>

                    <!-- ==========================================
                         APLICACIÓN + PLIEGUES
                    =========================================== -->

                    <div
                        v-if="aplicacionTexto || p.pliegues"
                        class="flex items-center gap-4 rounded-lg border border-amber-100 bg-amber-50 p-4"
                    >
                        <i
                            class="fa-solid fa-truck shrink-0 text-3xl text-brand-yellow"
                        />

                        <div class="flex-1">
                            <p
                                class="mb-1 text-[11px] font-bold uppercase tracking-wider text-amber-700"
                            >
                                Aplicación
                            </p>

                            <p class="text-sm text-gray-700">
                                {{ aplicacionTexto }}
                            </p>
                        </div>

                        <div
                            v-if="p.pliegues"
                            class="flex shrink-0 flex-col items-center justify-center rounded-lg border-2 border-amber-300 bg-white px-4 py-2"
                        >
                            <p
                                class="text-[28px] font-black leading-none text-gray-900"
                            >
                                {{ p.pliegues }}
                            </p>

                            <p
                                class="text-[9px] font-bold uppercase tracking-wider text-gray-500"
                            >
                                Pliegues
                            </p>
                        </div>
                    </div>

                    <!-- ==========================================
                         TABLA ESPECIFICACIONES
                    =========================================== -->

                    <div
                        class="overflow-hidden rounded-xl border border-gray-100 bg-white shadow-sm"
                    >
                        <div
                            class="border-b border-gray-100 px-4 py-3"
                        >
                            <p
                                class="text-[12px] font-black uppercase tracking-wider text-gray-700"
                            >
                                <i
                                    class="fa-solid fa-gears mr-1.5 text-brand-yellow"
                                />

                                Especificaciones Técnicas
                            </p>
                        </div>

                        <div
                            class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3"
                        >
                            <div
                                v-for="spec in [
                                    { label: 'Marca', value: p.marca },
                                    { label: 'Medida', value: p.medida },
                                    { label: 'Índice de carga', value: p.indiceCarga },
                                    { label: 'Índice de velocidad', value: p.indiceVelocidad },
                                    { label: 'Construcción', value: p.construccion },
                                    { label: 'Diseño', value: p.disenio },
                                    { label: 'Eje', value: p.eje },
                                    { label: 'Operación', value: p.operacion },
                                    { label: 'Profundidad de cocada', value: p.profCocada },
                                    { label: 'Ancho de banda', value: p.anchoBanda },
                                    {
                                        label: 'Ply Rating',
                                        value: p.pliegues
                                            ? `${p.pliegues} Pliegues`
                                            : null
                                    },
                                ].filter(s => s.value)"
                                :key="spec.label"
                                class="flex items-center justify-between border-b border-gray-50 px-4 py-2.5"
                            >
                                <span class="text-[12px] text-gray-500">
                                    {{ spec.label }}
                                </span>

                                <span
                                    class="text-[12px] font-bold text-gray-800"
                                >
                                    {{ spec.value }}
                                </span>
                            </div>
                        </div>
                    </div>

                </div>

                <!-- ==========================================
                     PANEL DERECHO
                =========================================== -->

                <div>

                    <div
                        class="sticky top-[86px] flex flex-col gap-4 rounded-xl border border-gray-100 bg-white p-5 shadow-sm"
                    >

                        <!-- Precio -->
                        <div>
                            <p
                                class="text-[11px] uppercase tracking-wider text-gray-400"
                            >
                                Precio por unidad
                            </p>

                            <p
                                class="text-[32px] font-black leading-tight text-gray-900"
                            >
                                {{ precioFormateado }}
                            </p>

                            <p class="text-[11px] text-gray-400">
                                Incl. IGV
                            </p>
                        </div>

                        <!-- WhatsApp -->
                        <a
                            :href="whatsappLink"
                            target="_blank"
                            rel="noopener noreferrer"
                            class="flex items-center justify-center gap-2 rounded-lg bg-brand-yellow py-3 text-[14px] font-black text-black transition-all hover:brightness-110"
                        >
                            <i
                                class="fa-brands fa-whatsapp text-[16px]"
                            />

                            Cotizar por WhatsApp
                        </a>

                        <!-- ==========================================
                             FICHA TÉCNICA PDF
                             SOLO SI EXISTE
                        =========================================== -->

                        <button
                            v-if="fichaTecnicaUrl"
                            type="button"
                            class="flex items-center justify-center gap-2 rounded-lg py-3 text-[14px] font-black text-white transition-all hover:brightness-95"
                            style="background-color: #1bd1c1;"
                            @click="abrirFichaTecnica"
                        >
                            <i
                                class="fa-solid fa-file-pdf text-[16px]"
                            />

                            Ver ficha técnica PDF
                        </button>

                        <!-- Garantías -->
                        <div
                            class="flex flex-col gap-3 border-t border-gray-100 pt-4"
                        >
                            <div
                                v-for="item in [
                                    {
                                        icon: 'fa-solid fa-truck',
                                        text: 'Despacho a todo el Perú'
                                    },
                                    {
                                        icon: 'fa-solid fa-certificate',
                                        text: 'Garantía de fábrica'
                                    },
                                    {
                                        icon: 'fa-solid fa-shield-halved',
                                        text: 'Productos 100% originales'
                                    },
                                    {
                                        icon: 'fa-solid fa-headset',
                                        text: 'Asesoría personalizada'
                                    },
                                ]"
                                :key="item.text"
                                class="flex items-center gap-2.5 text-[12px] text-gray-600"
                            >
                                <i
                                    :class="[
                                        item.icon,
                                        'w-4 shrink-0 text-center text-brand-yellow'
                                    ]"
                                />

                                {{ item.text }}
                            </div>
                        </div>

                    </div>

                </div>

            </div>

            <!-- =====================================================
                 TERCERA PARTE - OTR
            ====================================================== -->

            <div class="mt-6 space-y-6">

                <!-- ==========================================
                     RAZONES PARA ELEGIR
                =========================================== -->

                <section
                    class="grid grid-cols-1 items-center gap-6 rounded-xl bg-gray-100 p-6 md:grid-cols-2"
                >
                    <div>
                        <h2
                            class="text-base font-black text-gray-900 sm:text-lg"
                        >
                            Razones para elegir {{ p.nombre }}
                        </h2>

                        <p
                            class="mt-2 text-sm leading-relaxed text-gray-600"
                        >
                            {{ p.descripcion || 'Llanta de alta calidad diseñada para ofrecer rendimiento, seguridad y durabilidad en operaciones exigentes.' }}
                        </p>
                    </div>

                    <div
                        class="flex h-48 items-center justify-center rounded-xl bg-gray-100"
                    >
                        <img
                            v-if="p.imagenes.length"
                            :src="p.imagenes[0]?.url"
                            :alt="p.nombre"
                            class="max-h-44 max-w-[220px] object-contain"
                        />
                    </div>
                </section>

                <!-- ==========================================
                     MARCAS DE VEHÍCULOS COMPATIBLES
                =========================================== -->

                <section
                    class="rounded-xl bg-black px-5 py-6 sm:px-6"
                >
                    <h2
                        class="mb-4 text-sm font-black uppercase tracking-wide text-white"
                    >
                        Marcas de vehículos principales compatibles
                    </h2>

                    <div class="flex flex-wrap gap-3">
                        <div
                            v-for="marca in marcasCompatibles"
                            :key="marca.nombre"
                            class="flex items-center gap-2 rounded-lg border border-gray-700 px-4 py-2 text-xs font-bold text-white transition-colors hover:border-brand-yellow"
                        >
                            <i
                                :class="[
                                    marca.icon,
                                    'text-gray-300'
                                ]"
                            />

                            {{ marca.nombre }}
                        </div>
                    </div>
                </section>

                <!-- ==========================================
                     PRODUCTOS RELACIONADOS
                =========================================== -->

                <section
                    v-if="productosRelacionados.length"
                >
                    <div
                        class="mb-4 flex items-center justify-between"
                    >
                        <h2
                            class="text-base font-black text-gray-900"
                        >
                            Productos relacionados
                        </h2>

                        <NuxtLink
                            to="/catalogo"
                            class="text-xs font-bold text-brand-yellow transition-colors hover:text-black"
                        >
                            Ver más productos →
                        </NuxtLink>
                    </div>

                    <div
                        class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4"
                    >
                        <ShopProductCardOTR
                            v-for="producto in productosRelacionados"
                            :key="producto.id"
                            :producto="producto"
                        />
                    </div>
                </section>

                <!-- ==========================================
                     BENEFICIOS
                =========================================== -->

                <section
                    class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4"
                >
                    <div
                        v-for="beneficio in beneficios"
                        :key="beneficio.titulo"
                        class="flex min-h-[105px] flex-col items-center justify-center rounded-xl border border-gray-100 bg-white p-4 text-center shadow-sm"
                    >
                        <i
                            :class="[
                                beneficio.icon,
                                'mb-3 text-xl text-brand-yellow'
                            ]"
                        />

                        <p
                            class="text-xs font-bold text-gray-900"
                        >
                            {{ beneficio.titulo }}
                        </p>

                        <p
                            class="mt-1 text-[11px] text-gray-400"
                        >
                            {{ beneficio.descripcion }}
                        </p>
                    </div>
                </section>

            </div>

        </div>

        <!-- ==========================================
             VISOR PDF PANTALLA COMPLETA
        =========================================== -->

        <Teleport to="body">

            <div
                v-if="mostrarFichaTecnica && fichaTecnicaUrl"
                class="fixed inset-0 z-[9999] flex flex-col bg-black"
            >

                <!-- Barra superior -->
                <div
                    class="flex shrink-0 items-center justify-between gap-3 bg-gray-950 px-4 py-3"
                >

                    <!-- Volver -->
                    <button
                        type="button"
                        class="flex items-center gap-2 rounded-lg bg-white px-4 py-2 text-sm font-bold text-gray-900 transition-all hover:bg-gray-100"
                        @click="cerrarFichaTecnica"
                    >
                        <i class="fa-solid fa-arrow-left" />

                        Volver al producto
                    </button>

                    <!-- Acciones -->
                    <div class="flex items-center gap-2">

                        <!-- Descargar -->
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

                        <!-- Cerrar -->
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
                <div
                    class="min-h-0 flex-1 bg-gray-800"
                >
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
