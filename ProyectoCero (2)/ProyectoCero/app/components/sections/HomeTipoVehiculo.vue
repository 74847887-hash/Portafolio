<script setup lang="ts">
const categorias = [
  {
    id: 1,
    nombre: 'Vehículos livianos',
    icon: 'fa-solid fa-car',
    subcategorias: [
      { nombre: 'Automóvil', imagen: '/images/vehiculos/automovil.png' },
      { nombre: 'SUV', imagen: '/images/vehiculos/suv.png' },
      { nombre: '4x4', imagen: '/images/vehiculos/4x4.png' },
      { nombre: 'Van', imagen: '/images/vehiculos/van.jpg' },
    ],
  },
  {
    id: 2,
    nombre: 'Transporte',
    icon: 'fa-solid fa-truck',
    subcategorias: [
      { nombre: 'Camión Ligero', imagen: '/images/vehiculos/camion-ligero2.png' },
      { nombre: 'Bus', imagen: '/images/vehiculos/bus.png' },
      { nombre: 'Tracto Camión', imagen: '/images/vehiculos/tracto-camion.png' },
    ],
  },
  {
    id: 3,
    nombre: 'Maquinaria pesada',
    icon: 'fa-solid fa-triangle-exclamation',
    subcategorias: [
      { nombre: 'Volquete', imagen: '/images/vehiculos/volquete.png' },
      { nombre: 'Cargador Frontal', imagen: '/images/vehiculos/cargador-frontal.png' },
      { nombre: 'Retroexcavadora', imagen: '/images/vehiculos/retroexcavadora.png' },
    ],
  },
  {
    id: 4,
    nombre: 'Agrícola',
    icon: 'fa-solid fa-tractor',
    subcategorias: [
      { nombre: 'Tractor', imagen: '/images/vehiculos/tractor.jpg' },
    ],
  },
]

function itemClass(total: number) {
  if (total === 1) return 'vehicle-item single'
  if (total === 3) return 'vehicle-item half'
  return 'vehicle-item quarter'
}
</script>

<template>
  <section class="vehicle-section">
    <div class="vehicle-container">
      <div class="section-heading">
        <span class="heading-mark" />
        <h2>Explora por tipo de vehículo</h2>
      </div>

      <div class="vehicle-board">
        <article
          v-for="categoria in categorias"
          :key="categoria.id"
          class="category-block"
        >
          <header class="category-head">
            <span class="icon-badge">
              <i :class="categoria.icon" />
            </span>
            <h3>{{ categoria.nombre }}</h3>
          </header>

          <div class="vehicle-list">
            <button
              v-for="sub in categoria.subcategorias"
              :key="sub.nombre"
              :class="itemClass(categoria.subcategorias.length)"
              type="button"
            >
              <span class="image-wrap">
                <img
                  :src="sub.imagen"
                  :alt="sub.nombre"
                  @error="($event.target as HTMLImageElement).style.display = 'none'"
                />
              </span>
              <span class="vehicle-name">{{ sub.nombre }}</span>
            </button>
          </div>
        </article>
      </div>
    </div>
  </section>
</template>

<style scoped>
.vehicle-section {
  background: #f5f6f8;
  padding: 2.25rem 0;
}

.vehicle-container {
  width: 100%;
  max-width: 1360px;
  margin: 0 auto;
  padding: 0 1rem;
}

.section-heading {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  margin-bottom: 1.25rem;
}

.section-heading h2 {
  margin: 0;
  color: #111827;
  font-size: 1.25rem;
  font-weight: 900;
  line-height: 1.2;
}

.heading-mark {
  width: 38px;
  height: 5px;
  border-radius: 999px;
  background: #ffc107;
}

.vehicle-board {
  display: grid;
  overflow: hidden;
  border: 1px solid #e5e7eb;
  border-top: 4px solid #ffc107;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 14px 30px rgba(17, 24, 39, 0.08);
}

.category-block {
  min-width: 0;
  border-bottom: 1px solid #e5e7eb;
}

.category-block:last-child {
  border-bottom: 0;
}

.category-head {
  display: flex;
  align-items: center;
  gap: 0.65rem;
  padding: 1rem;
  background: linear-gradient(180deg, #ffffff 0%, #fff9e8 100%);
}

.icon-badge {
  display: inline-flex;
  width: 34px;
  height: 34px;
  flex: 0 0 34px;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background: #111827;
  color: #ffc107;
  font-size: 1rem;
}

.category-head h3 {
  margin: 0;
  color: #111827;
  font-size: 0.86rem;
  font-weight: 900;
  line-height: 1.15;
  text-transform: uppercase;
}

.vehicle-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.65rem;
  padding: 1rem;
}

.vehicle-item {
  display: flex;
  min-height: 82px;
  cursor: pointer;
  align-items: center;
  justify-content: center;
  gap: 0.65rem;
  border: 1px solid #eef0f3;
  border-radius: 8px;
  background: #fff;
  padding: 0.65rem;
  transition: transform 0.16s ease, border-color 0.16s ease, box-shadow 0.16s ease;
}

.vehicle-item:hover {
  border-color: #ffc107;
  box-shadow: 0 10px 18px rgba(17, 24, 39, 0.1);
  transform: translateY(-2px);
}

.vehicle-item.single {
  width: 100%;
}

.vehicle-item.half {
  width: calc(50% - 0.325rem);
}

.vehicle-item.quarter {
  width: calc(50% - 0.325rem);
}

.image-wrap {
  display: flex;
  width: 56px;
  height: 56px;
  flex: 0 0 56px;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background: #f9fafb;
}

.image-wrap img {
  width: 50px;
  height: 50px;
  object-fit: contain;
  filter: drop-shadow(0 5px 5px rgba(17, 24, 39, 0.18));
  mix-blend-mode: multiply;
}

.vehicle-name {
  min-width: 0;
  color: #374151;
  font-size: 0.78rem;
  font-weight: 800;
  line-height: 1.15;
  text-align: left;
}

@media (min-width: 768px) {
  .vehicle-board {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .category-block {
    border-right: 1px solid #e5e7eb;
  }

  .category-block:nth-child(2n) {
    border-right: 0;
  }

  .category-block:nth-last-child(-n + 2) {
    border-bottom: 0;
  }
}

@media (min-width: 1200px) {
  .vehicle-board {
    grid-template-columns: repeat(4, minmax(0, 1fr));
  }

  .category-block {
    border-right: 1px solid #e5e7eb;
    border-bottom: 0;
  }

  .category-block:nth-child(2n) {
    border-right: 1px solid #e5e7eb;
  }

  .category-block:last-child {
    border-right: 0;
  }

  .vehicle-item.quarter {
    width: calc(50% - 0.325rem);
  }
}

@media (max-width: 575.98px) {
  .section-heading {
    align-items: flex-start;
    justify-content: flex-start;
  }

  .section-heading h2 {
    font-size: 1.05rem;
  }

  .vehicle-list {
    padding: 0.85rem;
  }

  .vehicle-item.half,
  .vehicle-item.quarter {
    width: 100%;
  }
}
</style>
