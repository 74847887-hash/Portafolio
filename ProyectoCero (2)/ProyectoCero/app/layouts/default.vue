<template>
  <div class="min-h-screen bg-white flex flex-col">
    <TheHeader />
    <main class="flex-1">
      <slot />
    </main>
    <TheFooter />
    <FloatingWhatsApp />
  </div>
</template>
