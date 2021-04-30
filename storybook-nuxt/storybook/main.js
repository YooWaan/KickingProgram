const { nuxifyStorybook } = require('../.nuxt-storybook/storybook/main.js')

module.exports = nuxifyStorybook({
  webpackFinal (config) {
    // extend config here
    console.log('...................')
    console.log(config)
    return config
  }
})
