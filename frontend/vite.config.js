import { defineConfig } from 'vite'

export default defineConfig({
    server: {
        port: 5178,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                // ❌ rewrite: p => p.replace(/^\/api/, ''),   <-- usuń lub zakomentuj
                secure: false,
            },
        },
    },
})
