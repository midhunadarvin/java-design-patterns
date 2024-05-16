build-mac:
	gcc -c -fPIC -I"${JAVA_HOME}/include" -I"${JAVA_HOME}/include/darwin" -I"." -o src_Main.o src_Main.c
	gcc -dynamiclib -o libpipenv-python-script-jni-plugin.dylib src_Main.o -lc
	rm -rf ../../../integration-toolkit-core/libs
	mkdir ../../../integration-toolkit-core/libs
	mv libpipenv-python-script-jni-plugin.dylib ../../../integration-toolkit-core/libs/libpipenv-python-script-jni-plugin.dylib

build-linux:
	gcc -c -fPIC -I"${JAVA_HOME}/include" -I"${JAVA_HOME}/include/linux" -I"." -o src_Main.o src_Main.c
	gcc -shared -o src_Main.so src_Main.o -lc
	mv src_Main.so ../../../integration-toolkit-core/libs

test-pipenv-header:
	gcc -I"." -o TestPipenvPythonScriptCommand TestPipenvPythonScriptCommand.c
	./TestPipenvPythonScriptCommand
	rm TestPipenvPythonScriptCommand

run-on-docker-ubuntu:
	docker run -it --rm -v $(PWD):/app -w /app ubuntu:20.04 /bin/bash
	apt-get update
	apt-get install -y gcc make openjdk-11-jdk python3 python3-pip curl git
	export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-arm64
	pip3 install pipenv
	# install pyenv
	curl https://pyenv.run | bash
	export PATH="$HOME/.pyenv/bin:$PATH"
	# get all the dependencies, install python 3.10.6 and set it as global
	apt-get install -y make build-essential libssl-dev zlib1g-dev libbz2-dev libreadline-dev libsqlite3-dev wget curl llvm libncurses5-dev libncursesw5-dev xz-utils tk-dev libffi-dev liblzma-dev python-openssl
	pyenv install 3.10.6
	pyenv global 3.10.6