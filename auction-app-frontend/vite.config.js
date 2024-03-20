import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      src: "/src",
      "src/scss": "/src/scss",
      "src/components": "/src/components",
      "src/assets": "/src/assets",
      "src/pages": "/src/pages",
    },
  },
});
