import vue from 'rollup-plugin-vue'
import buble from 'rollup-plugin-buble'
import nodeResolve from 'rollup-plugin-node-resolve'
import commonjs from 'rollup-plugin-commonjs'

const plugins = [
  vue({compileTemplate:true}),
  buble({ exclude: 'node_modules/**' }),
  //nodeResolve({ browser: true, jsnext: true }),
  commonjs({ exclude: 'node_modules/**'})
]

/*
if (process.env.NODE_ENV === 'production') {
  plugins.push(uglify())
}

if (process.env.NODE_ENV === 'development') {
  plugins.push(livereload())
  plugins.push(serve({
    open: true
  }))
}
*/

export default {
  entry: 'src/main.js',
  dest: 'build.js',
  sourceMap: false,
  plugins
}
