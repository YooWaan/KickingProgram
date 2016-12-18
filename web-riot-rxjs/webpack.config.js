module.exports = {
  entry : {
    app: ["./js/main.js"],
    flux: ["./js/app-flux.js"],
    clean: ["./js/app-clean.js"],
    rp: ["./js/app-rp.js"]
  },
  output: {
    filename: "./[name].js"
  }
}
