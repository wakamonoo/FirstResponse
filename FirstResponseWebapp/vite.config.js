import { defineConfig } from "vite";
import laravel from "laravel-vite-plugin";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [
    laravel({
      input: ["resources/sass/app.scss", "resources/js/app.js"],
      refresh: true,
    }),
    vue(),
  ],
  resolve: {
    alias: {
      "@": "/resources/js",
      vue: "vue/dist/vue.esm-bundler.js",
    },
  },
  build: {
    manifest: true, // Ensure manifest is generated
    outDir: 'public/build', // Ensure this matches Laravel's default path
  },
});
