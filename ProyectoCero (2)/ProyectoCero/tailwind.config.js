export default {
  content: [
    './app/components/**/*.{vue,js,ts}',
    './app/layouts/**/*.vue',
    './app/pages/**/*.vue',
    './app/plugins/**/*.{js,ts}',
    './app/app.vue',
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
      },
      colors: {
        brand: {
          yellow: '#F5C400',
          black: '#111111',
          white: '#FFFFFF',
          dark: '#1a1a1a',
          gray: {
            50: '#f4f5f7',
            100: '#f0f0f0',
            200: '#e4e4e7',
            300: '#dddddd',
            400: '#cccccc',
            500: '#888888',
            600: '#777777',
            700: '#666666',
            800: '#555555',
            900: '#444444',
            950: '#333333',
          },
        },
      },
      animation: {
        marquee: 'marquee 30s linear infinite',
      },
      keyframes: {
        marquee: {
          '0%': { transform: 'translateX(0%)' },
          '100%': { transform: 'translateX(-50%)' },
        },
      },
    },
  },
  plugins: [],
}