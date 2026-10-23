<script setup lang="ts">
import { ref } from 'vue'

const activeTab = ref<'medida' | 'vehiculo'>('medida')

const anchoOptions = ['155', '165', '175', '185', '195', '205', '215', '225', '235', '245', '255', '265', '275']
const perfilOptions = ['35', '40', '45', '50', '55', '60', '65', '70', '75', '80']
const aroOptions = ['13', '14', '15', '16', '17', '18', '19', '20', '21', '22']
const selectedAncho = ref('')
const selectedPerfil = ref('')
const selectedAro = ref('')


const router = useRouter()

function buscar() {
  if (!selectedAncho.value || !selectedPerfil.value || !selectedAro.value) return
  router.push({
    path: '/catalogo',
    query: {
      ancho: selectedAncho.value,
      perfil: selectedPerfil.value,
      aro: selectedAro.value,
    }
  })
}
</script>

<template>
  <section class="rounded-lg border border-gray-100 bg-white shadow-xl">
    <div class="mx-auto max-w-7xl px-4 py-5 sm:px-6 sm:py-6">

      <!-- Header de la sección -->
      <div class="mb-5 flex flex-col gap-4 md:mb-6 md:flex-row md:items-center md:justify-between">
        <h2 class="text-lg font-bold text-gray-900">
          Encuentra la llanta ideal para tu vehículo
        </h2>

        <!-- Tabs -->
        <div class="flex w-full border-b border-gray-200 md:w-auto">
          <button class="px-4 py-2 text-sm font-semibold transition-colors" :class="activeTab === 'medida'
            ? 'text-yellow-500 border-b-2 border-yellow-500'
            : 'text-gray-500 hover:text-gray-700'" @click="activeTab = 'medida'">
            POR MEDIDA
          </button>
        </div>
      </div>

      <!-- Tab: Por medida -->
      <div v-if="activeTab === 'medida'">
        <div class="grid grid-cols-1 gap-3 sm:grid-cols-3 lg:grid-cols-[1fr_1fr_1fr_auto] lg:items-end lg:gap-4">

          <!-- Ancho -->
          <div class="min-w-0">
            <label class="block text-xs text-gray-500 mb-1">Ancho</label>
            <select v-model="selectedAncho"
              class="w-full border border-gray-300 rounded px-3 py-2.5 text-sm text-gray-800 focus:outline-none focus:ring-2 focus:ring-yellow-400">
              <option value="" disabled>Selecciona</option>
              <option v-for="op in anchoOptions" :key="op" :value="op">{{ op }}</option>
            </select>
          </div>

          <!-- Perfil -->
          <div class="min-w-0">
            <label class="block text-xs text-gray-500 mb-1">Perfil</label>
            <select v-model="selectedPerfil"
              class="w-full border border-gray-300 rounded px-3 py-2.5 text-sm text-gray-800 focus:outline-none focus:ring-2 focus:ring-yellow-400">
              <option value="" disabled>Selecciona</option>
              <option v-for="op in perfilOptions" :key="op" :value="op">{{ op }}</option>
            </select>
          </div>

          <!-- Aro -->
          <div class="min-w-0">
            <label class="block text-xs text-gray-500 mb-1">Aro</label>
            <select v-model="selectedAro"
              class="w-full border border-gray-300 rounded px-3 py-2.5 text-sm text-gray-800 focus:outline-none focus:ring-2 focus:ring-yellow-400">
              <option value="" disabled>Selecciona</option>
              <option v-for="op in aroOptions" :key="op" :value="op">{{ op }}</option>
            </select>
          </div>

          <!-- Botón buscar -->
          <button
            class="flex w-full items-center justify-center gap-2 rounded bg-yellow-400 px-6 py-2.5 font-bold text-black transition-all hover:brightness-110 disabled:cursor-not-allowed disabled:opacity-50 sm:col-span-3 lg:col-span-1 lg:w-auto"
            :disabled="!selectedAncho || !selectedPerfil || !selectedAro" @click="buscar">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24">
              <circle cx="11" cy="11" r="8" />
              <path d="m21 21-4.35-4.35" />
            </svg>
            BUSCAR
          </button>

        </div>

        <!-- Guía de medidas -->
        <p class="mt-3 text-xs text-gray-500">
          ¿No sabes tu medida?
          <a href="#" class="text-yellow-600 font-medium hover:underline">Guía de medidas</a>
        </p>
      </div>
    </div>
  </section>
</template>
