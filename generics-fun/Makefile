OPT=

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
	@rm -rf __pycache__
	@rm -f typ.js


java: javac-test
	java -jar junit-console.jar -cp $(PWD) --scan-classpath -n gt  $(OPT)

javac-test: java-chk
	javac -classpath $(PWD):$(PWD)/junit-console.jar gt.java

py:
	poetry run pytest -s -v test_typ.py$(OPT)

ts:
	npm run test $(OPT)

java-chk:
	javac -classpath $(PWD)/junit-console.jar g.java

py-chk:
	poetry run mypy --strict typ.py test_typ.py

#@poetry run mypy --disallow-untyped-defs typ.py test_typ.py

ts-chk:
	npx tsc typ.ts