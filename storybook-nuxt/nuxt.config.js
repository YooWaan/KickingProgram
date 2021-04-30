const path = require("path");
const rootPath = path.resolve(__dirname, ".");

export default {
  // Disable server-side rendering: https://go.nuxtjs.dev/ssr-mode
  ssr: false,

  // Target: https://go.nuxtjs.dev/config-target
  target: 'static',

  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'storybook-nuxt',
    htmlAttrs: {
      lang: 'en'
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [
  ],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    // https://go.nuxtjs.dev/typescript
    '@nuxt/typescript-build',
    '@nuxtjs/composition-api/module'
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    '@nuxtjs/axios',
  ],

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
  },

  storybook: {
    port: 6003,
    stories: [
      '~/test/**/*.stories.ts'
    ],
    addons: [
      '@storybook/addon-actions/register',
      '@storybook/addon-backgrounds/register',
      '@storybook/addon-controls/register',
      '@storybook/addon-docs/register',
      '@storybook/addon-toolbars/register',
      '@storybook/addon-viewport/register',
      '@storybook/preset-typescript'
    ],
    webpackFinal(config) {
      console.log(config)
      config.resolve.alias["@"] = rootPath;
      config.resolve.alias["~"] = rootPath;
      return config;
    }
  }
}
