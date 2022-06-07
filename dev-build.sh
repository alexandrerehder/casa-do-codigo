#!/bin/bash
home_casadocodigo=.
home_dir=$home_estudos/modulos

echo "Iniciando Build Back"
mvn clean install -f $home_casadocodigo/modulos/commons/pom.xml;
mvn clean install -f $home_casadocodigo/modulos/services/pom.xml;
mvn clean install -f $home_casadocodigo/modulos/api/pom.xml;
