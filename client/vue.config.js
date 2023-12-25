const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    open: process.platform === 'darwin',
    host: '127.0.0.2',
    port: 8081, // CHANGE YOUR PORT HERE!
    https: false,
  },
})
