const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');



module.exports = {
  entry: {
    vuex: './vuex-html/src/main.js'
  },
  output: {
    path: path.resolve(__dirname, './dist'),
    filename: '[name].bundle.js',
	chunkFilename: '[name].bundle.js'
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
		exclude: file => (
			/node_modules/.test(file) &&
			!/\.vue\.js/.test(file)
		)
      },
    ]
  },
  plugins: [
	new VueLoaderPlugin(),
    new HtmlWebpackPlugin({
      title: 'Simple Html Page',
      template: './simple-html/index.html',
	  filename: 'simple.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      title: 'Vuex Html Page',
      template: './vuex-html/index.html',
	  filename: 'vuex.html'
    })
  ],
  externals: [
    'Vue'
  ]
};
