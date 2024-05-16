build:
	./build_plugins.sh
	javac ./integration-toolkit-core/src/*.java

up:
	export PLUGINS_FOLDER=$(shell pwd)/plugins && java -Djava.library.path=$(shell pwd)/integration-toolkit-core/libs  -cp integration-toolkit-core/src/ Main