module.exports = {
  entry : {
    app: ["./js/main.js"],
    flux: ["./js/app-flux.js"],
    clean: ["./js/app-clean.js"]
  },
  output: {
    filename: "./[name].js"
  }
}
