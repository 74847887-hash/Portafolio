<script setup lang="ts">
import { useMediaQuery } from '@vueuse/core'
import { useCatalogoStore } from '~/stores/catalogo'

const store = useCatalogoStore()
const isDesktop = useMediaQuery('(min-width: 1024px)')

const anchoOptions = [
  '155',
  '165',
  '175',
  '185',
  '195',
  '205',
  '215',
  '225',
  '235',
  '245',
  '255',
  '265',
  '275',
]

const perfilOptions = [
  '35',
  '40',
  '45',
  '50',
  '55',
  '60',
  '65',
  '70',
  '75',
  '80',
]

const aroOptions = [
  '13',
  '14',
  '15',
  '16',
  '17',
  '18',
  '19',
  '20',
  '21',
  '22',
]
</script>

<template>
  <Transition name="overlay">
    <div
      v-if="store.sidebarOpen && !isDesktop"
      class="fixed inset-0 z-30 bg-black/50 lg:hidden"
      @click="store.closeSidebar()"
    />
  </Transition>

  <aside
    class="fixed right-0 top-0 z-40 h-full w-[min(330px,88vw)] overflow-y-auto bg-white p-5 shadow-lg transition-transform duration-300 lg:static lg:h-fit lg:w-full lg:rounded-lg lg:shadow-[0_2px_8px_rgba(0,0,0,0.03)]"
    :class="
      store.sidebarOpen
        ? 'translate-x-0'
        : 'translate-x-full lg:translate-x-0'
    "
  >
    <!-- CABECERA -->
    <div
      class="mb-5 flex items-center justify-between border-b border-brand-gray-100 pb-4"
    >
      <span
        class="min-w-0 text-[15px] font-extrabold uppercase"
      >
        Filtrar productos
      </span>

      <div class="flex shrink-0 items-center gap-2">
        <button
          type="button"
          class="text-[13px] font-semibold text-blue-600"
          @click="store.clearFilters()"
        >
          <i class="fa-solid fa-rotate-right mr-1" />
          Limpiar filtros
        </button>

        <button
          type="button"
          class="ml-2 flex h-7 w-7 items-center justify-center rounded-md text-brand-gray-700 hover:bg-brand-gray-100 lg:hidden"
          @click="store.closeSidebar()"
        >
          <i class="fa-solid fa-xmark" />
        </button>
      </div>
    </div>

    <!-- CATEGORÍA -->
    <div class="mb-5">
      <div
        class="mb-3 text-[14px] font-bold uppercase"
      >
        Categoría
      </div>

      <ul class="flex flex-col gap-2.5">
        <li
          v-for="category in store.vehicleTypes"
          :key="category.name"
          class="flex cursor-pointer items-center justify-between rounded-md px-2 py-1.5 text-[14px] text-brand-gray-900 transition-colors hover:bg-brand-gray-100"
          :class="{
            'bg-yellow-50 font-bold text-black':
              store.selectedVehicleTypes.includes(category.name),
          }"
          @click="
            store.toggleVehicleType(category.name)
          "
        >
          <div class="flex items-center gap-2.5">
            <i
              :class="category.icon"
              class="w-4 text-brand-gray-700"
            />
            <span>{{ category.name }}</span>
          </div>

          <span class="text-[13px] text-brand-gray-500">
            {{ category.count }}
          </span>
        </li>
      </ul>
    </div>

    <!-- MEDIDA -->
    <div class="mb-5">
      <div
        class="mb-3 text-[14px] font-bold uppercase"
      >
        Medida
      </div>

      <select
        v-model="store.selectedAncho"
        class="mb-2 w-full appearance-auto rounded-md border border-brand-gray-300 bg-white py-2.5 pl-2.5 pr-8 text-[14px] text-brand-gray-700 outline-none"
      >
        <option value="">Ancho</option>

        <option
          v-for="option in anchoOptions"
          :key="option"
          :value="option"
        >
          {{ option }}
        </option>
      </select>

      <select
        v-model="store.selectedPerfil"
        class="mb-2 w-full appearance-auto rounded-md border border-brand-gray-300 bg-white py-2.5 pl-2.5 pr-8 text-[14px] text-brand-gray-700 outline-none"
      >
        <option value="">Perfil</option>

        <option
          v-for="option in perfilOptions"
          :key="option"
          :value="option"
        >
          {{ option }}
        </option>
      </select>

      <select
        v-model="store.selectedAro"
        class="mb-2 w-full appearance-auto rounded-md border border-brand-gray-300 bg-white py-2.5 pl-2.5 pr-8 text-[14px] text-brand-gray-700 outline-none"
      >
        <option value="">Aro</option>

        <option
          v-for="option in aroOptions"
          :key="option"
          :value="option"
        >
          {{ option }}
        </option>
      </select>

      <button
        type="button"
        class="mt-1 w-full cursor-pointer rounded-md bg-brand-yellow py-2.5 text-[14px] font-extrabold uppercase transition-colors duration-150 hover:bg-[#e6b000]"
        @click="store.applyMeasureFilters()"
      >
        Filtrar
      </button>
    </div>

    <!-- MARCA -->
    <div class="mb-5">
      <div
        class="mb-3 text-[14px] font-bold uppercase"
      >
        Marca
      </div>

      <input
        v-model="store.brandSearch"
        type="text"
        placeholder="Buscar marca..."
        class="mb-3 w-full rounded-md border border-brand-gray-300 p-2 text-[14px] outline-none"
      />

      <div class="flex flex-col gap-2">
        <div
          v-for="brand in store.filteredBrands"
          :key="brand.name"
          class="flex items-center justify-between text-[14px] text-brand-gray-900"
        >
          <label
            class="flex cursor-pointer items-center gap-2"
          >
            <input
              v-model="brand.checked"
              type="checkbox"
              class="accent-brand-yellow"
              @change="store.currentPage = 1"
            />

            <span>{{ brand.name }}</span>
          </label>

          <span class="text-[13px] text-brand-gray-500">
            ({{ brand.count }})
          </span>
        </div>
      </div>
    </div>
  </aside>
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
</style>