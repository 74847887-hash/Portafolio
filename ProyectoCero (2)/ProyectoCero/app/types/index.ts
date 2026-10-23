// app/types/index.ts

export type CategoriaSlug = 'autos' | 'camionetas' | 'camiones' | 'industrial' | 'agricola'

export interface ProductoResumen {
  id: number
  codigoIngreso: string | null
  slug: string
  nombre: string
  marca: string
  marcaLogoUrl: string
  categoria: string
  categoriaSlug: CategoriaSlug
  subcategoria: string | null
  medida: string
  precio: number
  disponibilidad: 'DISPONIBLE' | 'SIN_STOCK'
  imagenPrincipalUrl: string | null
  // PCR
  indiceCarga: string | null
  indiceVelocidad: string | null
  treadwear: string | null
  traccion: string | null
  temperatura: string | null
  // OTR / TBR
  profCocada: string | null
  anchoBanda: string | null
  eje: string | null
  operacion: string | null
}

export interface ImagenProductoResponse {
  id: number
  nombreArchivo: string
  ruta: string
  url: string
  orden: number
}

export interface ProductoDetalleResponse extends ProductoResumen {
  descripcion: string | null
  ancho: number | null
  perfil: number | null
  aro: number | null
  tipo: 'PCR' | 'OTR/TBR' | string
  fichaTecnicaUrl: string | null
  imagenes: ImagenProductoResponse[]
  pliegues: number | null
  construccion: string | null
  disenio: string | null
  videoUrl: string | null
}

// Helper — determina si una categoría es OTR/TBR
export function esOtrTbr(categoriaSlug: CategoriaSlug): boolean {
  return ['camiones', 'industrial', 'agricola'].includes(categoriaSlug)
}

// Tipos legacy — se mantienen mientras se migra el store hardcodeado
export interface ProductSpec {
  icon: string
  text: string
}

export interface Product {
  id: number
  codigoIngreso?: string | null
  brand: string
  brandLogo: string
  brandLogoWidth: string
  image: string
  title: string
  shortName?: string
  medida?: string
  vehiculoTipo?: string
  description?: string
  specs: ProductSpec[]
  price: string
  slug: string
}

export interface VehicleType {
  icon: string
  name: string
  count: number
}

export interface Brand {
  name: string
  count: number
  checked: boolean
}

export interface ValueProp {
  icon: string
  title: string
  subtitle: string
}
