<script setup lang="ts">
const config = useRuntimeConfig()
const whatsappUrl = ref('https://wa.me/51999999999')
onMounted(async () => {
  try {
    const contacto = await $fetch<{ whatsapp: string }>('/informacion-contacto', { baseURL: config.public.apiBase })
    if (contacto.whatsapp) whatsappUrl.value = `https://wa.me/${contacto.whatsapp.replace(/\D/g, '')}`
  } catch { /* se usa el enlace de respaldo */ }
})
</script>

<template>
  <a
    :href="whatsappUrl"
    target="_blank"
    rel="noopener noreferrer"
    aria-label="Cotizar por WhatsApp"
    class="whatsapp-float group fixed bottom-5 right-5 z-[60] flex h-14 w-14 items-center justify-center rounded-full bg-[#25d366] text-white shadow-[0_10px_28px_rgba(37,211,102,0.38)] transition-transform duration-200 hover:scale-105 focus:outline-none focus:ring-4 focus:ring-[#25d366]/30 sm:bottom-6 sm:right-6 sm:h-16 sm:w-16"
  >
    <span class="whatsapp-pulse absolute inset-0 rounded-full bg-[#25d366]" />
    <i class="fa-brands fa-whatsapp relative z-10 text-[30px] sm:text-[34px]" />
    <span
      class="pointer-events-none absolute right-full top-1/2 mr-3 hidden -translate-y-1/2 whitespace-nowrap rounded-md bg-black px-3 py-2 text-xs font-bold uppercase tracking-[0.2px] text-white opacity-0 shadow-lg transition-opacity duration-200 group-hover:opacity-100 md:block"
    >
      Cotizar ahora
    </span>
  </a>
</template>

<style scoped>
.whatsapp-float {
  animation: whatsapp-bob 2.8s ease-in-out infinite;
}

.whatsapp-pulse {
  animation: whatsapp-pulse 2s ease-out infinite;
}

@keyframes whatsapp-bob {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-6px);
  }
}

@keyframes whatsapp-pulse {
  0% {
    opacity: 0.45;
    transform: scale(1);
  }
  75%,
  100% {
    opacity: 0;
    transform: scale(1.45);
  }
}
</style>
