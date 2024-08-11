import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vuetify from "vite-plugin-vuetify";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
      // vuetify  사용한 컴포넌트만 import
      vuetify({autoImport: true})
  ],
  server: {
    host: "0.0.0.0",
    port: 80,
    watch: {
      usePolling: true,
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
