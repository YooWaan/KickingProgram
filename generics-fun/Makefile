

show:
	@echo "____Java Env____"
	javac --version
	java -version
	@echo "____node Env____"
	npm list
	node --version
	@echo "____python Env____"
	poetry show
	poetry env info


clean:
	@rm -f *.class
	@rm -f __pycache__
	@rm -f typ.js


java:
	javac g.java
	java g


py:
	python typ.py

ts:
	npx ts-node typ.ts

java-chk:
	javac g.java

py-chk:
	poetry run mypy typ.py

ts-chk:
	npx tsc typ.ts