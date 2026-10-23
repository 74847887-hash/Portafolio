<script setup lang="ts">
import { WHATSAPP_URL } from '~/utils/constants'
const whatsappUrl = WHATSAPP_URL
</script>

<template>
    <section class="bg-gray-100 py-6 border-t border-gray-200 overflow-hidden">
        <div class="max-w-7xl mx-auto px-6">
            <div class="flex flex-col items-center justify-between gap-5 md:flex-row md:gap-6">

                <!-- Imagen llanta -->
                <div class="hidden md:block flex-shrink-0 relative w-32 h-20">
                    <img src="/images/llanta-CTA.jpg" alt="Llanta"
                        class="absolute -left-2 -bottom-2 h-24 w-auto object-contain" />
                </div>
                <!-- Texto -->
                <div class="flex-1 text-center md:text-left">
                    <h3 class="text-lg font-bold text-gray-900">
                        ¿Necesitas ayuda para elegir tus llantas?
                    </h3>
                    <p class="text-sm text-gray-500 mt-1">
                        Nuestros expertos están listos para asesorarte.
                    </p>
                </div>

                <!-- Botón WhatsApp -->
                <a :href="whatsappUrl" target="_blank" rel="noopener noreferrer"
                    class="flex w-full items-center justify-center gap-2 rounded bg-yellow-400 px-6 py-3 font-bold text-black transition-all hover:brightness-110 sm:w-auto">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                        <path
                            d="M17.472 14.382c-.297-.149-1.758-.867-2.03-.967-.273-.099-.471-.148-.67.15-.197.297-.767.966-.94 1.164-.173.199-.347.223-.644.075-.297-.15-1.255-.463-2.39-1.475-.883-.788-1.48-1.761-1.653-2.059-.173-.297-.018-.458.13-.606.134-.133.298-.347.446-.52.149-.174.198-.298.298-.497.099-.198.05-.371-.025-.52-.075-.149-.669-1.612-.916-2.207-.242-.579-.487-.5-.669-.51-.173-.008-.371-.01-.57-.01-.198 0-.52.074-.792.372-.272.297-1.04 1.016-1.04 2.479 0 1.462 1.065 2.875 1.213 3.074.149.198 2.096 3.2 5.077 4.487.709.306 1.262.489 1.694.625.712.227 1.36.195 1.871.118.571-.085 1.758-.719 2.006-1.413.248-.694.248-1.289.173-1.413-.074-.124-.272-.198-.57-.347z" />
                        <path
                            d="M12 0C5.373 0 0 5.373 0 12c0 2.123.554 4.117 1.528 5.845L.057 23.272a.75.75 0 0 0 .92.92l5.427-1.471A11.943 11.943 0 0 0 12 24c6.627 0 12-5.373 12-12S18.627 0 12 0zm0 22c-1.907 0-3.695-.528-5.222-1.443l-.374-.223-3.873 1.05 1.05-3.872-.223-.374A9.953 9.953 0 0 1 2 12C2 6.477 6.477 2 12 2s10 4.477 10 10-4.477 10-10 10z" />
                    </svg>
                    HABLAR POR WHATSAPP
                </a>

            </div>
        </div>
    </section>
</template>
