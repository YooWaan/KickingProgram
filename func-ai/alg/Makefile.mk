fu
CDIR=clang
GODIR=golang
RSTDIR=rust


cls-clang:
	@rm -rf CMakeCache.txt CMakeFiles/ cmake_install.cmake build.ninja rules.ninja

clang:
	@cd $(CDIR)
	ninja
	@cd $(PWD)


go:
	@cd $(GODIR)
	@cd $(PWD)

rust:
	@cd $(RSTDIR)
	cargo build
	@cd $(PWD)


doc:
	pandoc -s -t html5 -o foo.html --mathml docs/ex1.md docs/ex2.md
