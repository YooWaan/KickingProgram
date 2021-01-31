

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


init: init-java init-py init-js

init-java:
	@rm -f junit-console.jar
	@wget -O junit-console.jar https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.7.0/junit-platform-console-standalone-1.7.0.jar

init-py:
	poetry install

init-js:
	npm ci


clean:
	@rm -f *.class
	@rm -f __pycache__
	@rm -f typ.js


java:
	javac -classpath $(PWD)/junit-console.jar g.java
	javac -classpath $(PWD):$(PWD)/junit-console.jar gt.java
	java -jar junit-console.jar -cp $(PWD) --scan-classpath -n gt


py:
	python typ.py

ts:
	npx ts-node typ.ts

java-chk:
	javac g.java

py-chk:
	poetry run mypy --disallow-untyped-defs typ.py 

ts-chk:
	npx tsc typ.ts