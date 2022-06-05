#!/bin/bash
home_casadocodigo=.
home_dir=$home_estudos/modulos

echo "Iniciando Build Back"
mvn clean install -f $home_dir/estudos-commons/pom.xml;
mvn clean install -f $home_dir/estudos-api/pom.xml;
mvn clean install -f $home_dir/estudos-operacao/pom.xml;
