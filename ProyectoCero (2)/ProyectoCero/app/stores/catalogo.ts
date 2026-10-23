// app/stores/catalogo.ts
import { defineStore } from 'pinia'
import type { Product, VehicleType, Brand, ValueProp } from '~/types'

type CatalogoState = {
  products: Product[]
  vehicleTypes: VehicleType[]
  brands: Brand[]
  valueProps: ValueProp[]
  sidebarOpen: boolean
  selectedVehicleTypes: string[]
  brandSearch: string
  selectedAncho: string
  selectedPerfil: string
  selectedAro: string
  sortBy: string
  resultsPerPage: number
  currentPage: number
}

export const useCatalogoStore = defineStore('catalogo', {
  state: (): CatalogoState => ({
    products: [
      {
        id: 1,
        slug: 'llanta-linglong-sport-275-45-r21',
        brand: 'Linglong',
        brandLogo: '/images/brands/linglong.png',
        brandLogoWidth: '120px',
        image: '/images/products/primera.jpg',
        title: 'LLANTA LING LONG SPORT 275/45 R21',
        medida: '275/45 R21',
        vehiculoTipo: 'Sport',
        description: 'Llanta de alto rendimiento para autos deportivos y de lujo.',
        specs: [
          { icon: 'fa-solid fa-circle-dot', text: '275/45 R21' },
          { icon: 'fa-solid fa-car', text: 'Sport' },
          { icon: 'fa-solid fa-gauge-high', text: 'Índice de carga: 110' },
          { icon: 'hero:shield-check', text: 'Garantía: 5 años' },
        ],
        price: 'S/568.00',
      },

      {
        id: 2,
        slug: 'llanta-rockbuster-h992a-solida-6-50-10',
        brand: 'Rockbuster',
        brandLogo: '/images/brands/rockbuster.jpg',
        brandLogoWidth: '100px',
        image: '/images/products/segunda.jpg',
        title: 'LLANTA ROCKBUSTER H992A SÓLIDA 6.50 10',
        medida: '6.50 10',
        vehiculoTipo: 'Sólida Industrial',
        description: 'Llanta sólida industrial para operaciones exigentes.',
        specs: [
          { icon: 'fa-solid fa-circle-dot', text: '6.50 10' },
          { icon: 'fa-solid fa-gears', text: 'Sólida Industrial' },
          { icon: 'fa-solid fa-weight-hanging', text: 'Capacidad: 1500 kg' },
          { icon: 'hero:shield-check', text: 'Garantía: 3 años' },
        ],
        price: 'S/648.00',
      },

      {
        id: 3,
        slug: 'llanta-bridgestone-dueler-ht-684-ii',
        brand: 'Bridgestone',
        brandLogo: '/images/brands/Bridgestone.jpg',
        brandLogoWidth: '105px',
        image: '/images/products/tercera.jpg',
        title: 'LLANTA BRIDGESTONE DUELER H/T 684 II',
        medida: '265/60 R18',
        vehiculoTipo: 'SUV / Camioneta',
        description: 'Llanta para SUV y camioneta con desempeño estable en carretera.',
        specs: [
          { icon: 'fa-solid fa-circle-dot', text: '265/60 R18' },
          { icon: 'fa-solid fa-truck-pickup', text: 'SUV / Camioneta' },
          { icon: 'fa-solid fa-gauge-high', text: 'Índice de carga: 110' },
          { icon: 'hero:shield-check', text: 'Garantía: 5 años' },
        ],
        price: 'S/732.00',
      },

      {
        id: 4,
        slug: 'llanta-goodyear-armor-max-msa',
        brand: 'Goodyear',
        brandLogo: '/images/brands/goodyear.png',
        brandLogoWidth: '105px',
        image: '/images/products/cuarta.jpg',
        title: 'LLANTA GOODYEAR ARMOR MAX MSA',
        medida: '11R22.5',
        vehiculoTipo: 'Camión / Ruta',
        description: 'Llanta para camión orientada a resistencia y rendimiento.',
        specs: [
          { icon: 'fa-solid fa-circle-dot', text: '11R22.5' },
          { icon: 'fa-solid fa-truck', text: 'Camión / Ruta' },
          { icon: 'fa-solid fa-gauge-high', text: 'Índice de carga: 146/143' },
          { icon: 'hero:shield-check', text: 'Garantía: 5 años' },
        ],
        price: 'S/1,285.00',
      },

      {
        id: 5,
        slug: 'llanta-pirelli-p-zero-pz4',
        brand: 'Pirelli',
        brandLogo: '/images/brands/pirelli.png',
        brandLogoWidth: '65px',
        image: '/images/products/quinta.jpg',
        title: 'LLANTA PIRELLI P ZERO (PZ4)',
        medida: '245/40 R18',
        vehiculoTipo: 'Sport Premium',
        description: 'Llanta premium sport para autos de alto desempeño.',
        specs: [
          { icon: 'fa-solid fa-circle-dot', text: '245/40 R18' },
          { icon: 'fa-solid fa-car', text: 'Sport Premium' },
          { icon: 'hero:shield-check', text: 'Índice de carga: 97' },
        ],
        price: 'S/612.00',
      },

      {
        id: 6,
        slug: 'llanta-bfg-all-terrain-ta-ko2',
        brand: 'BFGoodrich',
        brandLogo: '/images/brands/bfgoodrich.png',
        brandLogoWidth: '110px',
        image: '/images/products/sexta.jpg',
        title: 'LLANTA BFG ALL-TERRAIN T/A KO2',
        medida: '265/70 R16',
        vehiculoTipo: 'Todo Terreno',
        description: 'Llanta todo terreno para uso mixto dentro y fuera de carretera.',
        specs: [
          { icon: 'fa-solid fa-circle-dot', text: '265/70 R16' },
          { icon: 'fa-solid fa-mountain', text: 'Todo Terreno' },
          { icon: 'hero:shield-check', text: 'Índice de carga: 112' },
        ],
        price: 'S/698.00',
      },

      {
        id: 7,
        slug: 'llanta-michelin-agilis-3',
        brand: 'Michelin',
        brandLogo: '/images/brands/michelin.jpg',
        brandLogoWidth: '110px',
        image: '/images/products/septima.jpg',
        title: 'LLANTA MICHELIN AGILIS 3',
        medida: '215/65 R16',
        vehiculoTipo: 'Van / Carga',
        description: 'Llanta para van y carga ligera, orientada a durabilidad.',
        specs: [
          { icon: 'fa-solid fa-circle-dot', text: '215/65 R16' },
          { icon: 'fa-solid fa-van-shuttle', text: 'Van / Carga' },
          { icon: 'hero:shield-check', text: 'Índice de carga: 109' },
        ],
        price: 'S/615.00',
      },

      {
        id: 8,
        slug: 'llanta-kumho-agri-power',
        brand: 'Kumho',
        brandLogo: '/images/brands/kumho.jpg',
        brandLogoWidth: '90px',
        image: '/images/products/octava.jpg',
        title: 'LLANTA KUMHO AGRI POWER',
        medida: '18.4-30',
        vehiculoTipo: 'Agrícola',
        description: 'Llanta agrícola para trabajo en campo y tracción constante.',
        specs: [
          { icon: 'fa-solid fa-circle-dot', text: '18.4-30' },
          { icon: 'fa-solid fa-tractor', text: 'Agrícola' },
          { icon: 'hero:shield-check', text: 'Índice de carga: 153' },
        ],
        price: 'S/1,050.00',
      },
    ],

    vehicleTypes: [
      {
        icon: 'fa-solid fa-car',
        name: 'Vehículos livianos',
        count: 2,
      },
      {
        icon: 'fa-solid fa-truck-pickup',
        name: 'Camionetas',
        count: 3,
      },
      {
        icon: 'fa-solid fa-truck',
        name: 'Camiones',
        count: 1,
      },
      {
        icon: 'fa-solid fa-industry',
        name: 'Industrial',
        count: 1,
      },
      {
        icon: 'fa-solid fa-tractor',
        name: 'Agrícola',
        count: 1,
      },
    ],

    brands: [
      { name: 'Michelin', count: 1, checked: false },
      { name: 'Bridgestone', count: 1, checked: false },
      { name: 'Goodyear', count: 1, checked: false },
      { name: 'Pirelli', count: 1, checked: false },
      { name: 'Linglong', count: 1, checked: false },
      { name: 'Rockbuster', count: 1, checked: false },
      { name: 'BFGoodrich', count: 1, checked: false },
      { name: 'Kumho', count: 1, checked: false },
    ],

    valueProps: [
      {
        icon: 'hero:check-badge',
        title: 'PRODUCTOS ORIGINALES',
        subtitle: 'Marcas líderes',
      },
      {
        icon: 'fa-solid fa-truck',
        title: 'ENVÍOS A TODO EL PERÚ',
        subtitle: 'Agencias confiables',
      },
      {
        icon: 'fa-solid fa-headset',
        title: 'ASESORÍA PERSONALIZADA',
        subtitle: 'Te ayudamos a elegir',
      },
      {
        icon: 'hero:shield-check',
        title: 'COMPRA 100% SEGURA',
        subtitle: 'Protegemos tu compra',
      },
    ],

    sidebarOpen: false,
    selectedVehicleTypes: [],
    brandSearch: '',
    selectedAncho: '',
    selectedPerfil: '',
    selectedAro: '',
    sortBy: 'precio-menor',
    resultsPerPage: 12,
    currentPage: 1,
  }),

  getters: {
    filteredBrands: (state): Brand[] => {
      const search = state.brandSearch.trim().toLowerCase()

      if (!search) {
        return state.brands
      }

      return state.brands.filter((brand) =>
        brand.name.toLowerCase().includes(search),
      )
    },

    displayedProducts: (state): Product[] => {
      const products = state.products.filter((product) =>
        matchesFilters(product, state),
      )

      return [...products].sort((a, b) => {
        if (state.sortBy === 'precio-menor') {
          return parsePrice(a.price) - parsePrice(b.price)
        }

        if (state.sortBy === 'precio-mayor') {
          return parsePrice(b.price) - parsePrice(a.price)
        }

        return a.id - b.id
      })
    },

    resultsRange: (state): string => {
      const total = state.products.filter((product) =>
        matchesFilters(product, state),
      ).length

      if (total === 0) {
        return 'No se encontraron productos'
      }

      const start =
        (state.currentPage - 1) * state.resultsPerPage + 1

      const end = Math.min(
        state.currentPage * state.resultsPerPage,
        total,
      )

      return `Mostrando ${start} - ${end} de ${total} productos`
    },
  },

  actions: {
    toggleSidebar() {
      this.sidebarOpen = !this.sidebarOpen
    },

    closeSidebar() {
      this.sidebarOpen = false
    },

    toggleVehicleType(category: string) {
      if (this.selectedVehicleTypes.includes(category)) {
        this.selectedVehicleTypes =
          this.selectedVehicleTypes.filter(
            (item) => item !== category,
          )
      } else {
        this.selectedVehicleTypes.push(category)
      }

      this.currentPage = 1
    },

    applyMeasureFilters() {
      this.currentPage = 1
      this.closeSidebar()
    },

    clearFilters() {
      this.selectedVehicleTypes = []
      this.brandSearch = ''
      this.selectedAncho = ''
      this.selectedPerfil = ''
      this.selectedAro = ''
      this.sortBy = 'precio-menor'

      this.brands.forEach((brand) => {
        brand.checked = false
      })

      this.currentPage = 1
    },

    setMeasureFilters(
      ancho?: string,
      perfil?: string,
      aro?: string,
    ) {
      this.selectedAncho = ancho ?? ''
      this.selectedPerfil = perfil ?? ''
      this.selectedAro = aro ?? ''
      this.currentPage = 1
    },
  },
})

function matchesFilters(
  product: Product,
  state: CatalogoState,
): boolean {
  const selectedBrands = state.brands
    .filter((brand) => brand.checked)
    .map((brand) => normalize(brand.name))

  const selectedCategories =
    state.selectedVehicleTypes.map(normalize)

  const medida =
    product.medida ??
    product.specs[0]?.text ??
    ''

  const vehicleType = normalize(
    product.vehiculoTipo ??
      product.specs[1]?.text ??
      '',
  )

  const matchesBrand =
    selectedBrands.length === 0 ||
    selectedBrands.includes(normalize(product.brand))

  /*
   * CATEGORÍAS PÚBLICAS
   *
   * Vehículos livianos:
   *   Sport
   *   Sport Premium
   *
   * Camionetas:
   *   SUV / Camioneta
   *   Todo Terreno
   *   Van / Carga
   *
   * Camiones:
   *   Camión / Ruta
   *
   * Industrial:
   *   Sólida Industrial
   *
   * Agrícola:
   *   Agrícola
   */
  const matchesCategory =
    selectedCategories.length === 0 ||
    selectedCategories.some((category) => {
      if (category === 'vehiculos livianos') {
        return (
          vehicleType.includes('sport') ||
          vehicleType.includes('premium')
        )
      }

      if (category === 'camionetas') {
        return (
          vehicleType.includes('suv') ||
          vehicleType.includes('camioneta') ||
          vehicleType.includes('todo terreno') ||
          vehicleType.includes('terreno') ||
          vehicleType.includes('van') ||
          vehicleType.includes('carga')
        )
      }

      if (category === 'camiones') {
        return (
          vehicleType.includes('camion') ||
          vehicleType.includes('ruta')
        )
      }

      if (category === 'industrial') {
        return (
          vehicleType.includes('industrial') ||
          vehicleType.includes('solida')
        )
      }

      if (category === 'agricola') {
        return vehicleType.includes('agricola')
      }

      return false
    })

  const matchesAncho =
    !state.selectedAncho ||
    medida.includes(state.selectedAncho)

  const matchesPerfil =
    !state.selectedPerfil ||
    medida.includes(`/${state.selectedPerfil}`)

  const matchesAro =
    !state.selectedAro ||
    medida.toLowerCase().includes(
      `r${state.selectedAro}`,
    ) ||
    medida.endsWith(` ${state.selectedAro}`)

  return (
    matchesBrand &&
    matchesCategory &&
    matchesAncho &&
    matchesPerfil &&
    matchesAro
  )
}

function parsePrice(price: string): number {
  const normalized = price.replace(/[^\d.]/g, '')
  return Number.parseFloat(normalized) || 0
}

function normalize(value: string): string {
  return value
    .toLowerCase()
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .trim()
}