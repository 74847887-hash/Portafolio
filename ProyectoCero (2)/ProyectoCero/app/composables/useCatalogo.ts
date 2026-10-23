// app/composables/useCatalogo.ts
import type { ProductoResumen } from '~/types'

export function useCatalogo() {
  const config = useRuntimeConfig()

  async function fetchProductos(params?: {
    marca?: string
    categoria?: string
    medida?: string
  }): Promise<ProductoResumen[]> {
    try {
      const query: Record<string, string> = {}
      if (params?.marca) query.marca = params.marca
      if (params?.categoria) query.categoria = params.categoria
      if (params?.medida) query.medida = params.medida

      return await $fetch<ProductoResumen[]>('/productos', {
        baseURL: config.public.apiBase,
        query,
      })
    } catch (e) {
      console.error('Error al cargar productos:', e)
      return []
    }
  }

  async function fetchProductoPorSlug(slug: string): Promise<ProductoResumen | null> {
    try {
      return await $fetch<ProductoResumen>(`/productos/${slug}`, {
        baseURL: config.public.apiBase,
      })
    } catch (e) {
      console.error('Error al cargar producto:', e)
      return null
    }
  }

  return {
    fetchProductos,
    fetchProductoPorSlug,
  }
}