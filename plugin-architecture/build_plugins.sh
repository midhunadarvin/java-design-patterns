#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PLUGINS_HOME="${SCRIPT_DIR}/plugins"

for entry in "$PLUGINS_HOME"/*/
do
  cd "$entry" || exit
  javac src/Main.java
  jar cmvf META-INF/MANIFEST.MF "$(basename "$entry")".jar ./src
  cd "$SCRIPT_DIR" || exit
done
#CLASSPATH=""
#for filepath in "${LIB_DIR}"/*; do
#  CLASSPATH="${CLASSPATH}:${filepath}"
#done
#
#java -cp "${CLASSPATH}" org.spyne.javapluginquickstart.core.App