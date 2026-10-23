export default defineNuxtConfig({
  compatibilityDate: '2026-07-20',
  ssr: true,

  modules: [
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    '@vueuse/nuxt',
  ],

  app: {
    seoMeta: {
      ogImage: '/images/og-cover.jpg',   // imagen que aparece al compartir en redes
      twitterCard: 'summary_large_image',
      twitterSite: '@andesllantas',   // si tienen Twitter/X
    },
    head: {
      htmlAttrs: { lang: 'es' },
      title: 'Andes Llantas',
      link: [
        { rel: 'preconnect', href: 'https://fonts.googleapis.com' },
        { rel: 'preconnect', href: 'https://fonts.gstatic.com', crossorigin: '' },
        {
          rel: 'stylesheet',
          href: 'https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800;900&display=swap',
        },
        {
          rel: 'stylesheet',
          href: 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css',
        },
      ],
    },
  },

  runtimeConfig: {
    apiSecret: '',
    public: {
      apiBase: process.env.NUXT_PUBLIC_API_BASE || 'http://localhost:8080',
    },
  },

  typescript: { strict: true },
  devtools: { enabled: true },
})
