#!/bin/bash

java -Xss100m -Dfile.encoding=UTF-8 -classpath :jmatharray.jar:jmathplot.jar: Calibradores.EvolucionanCuencaAgri $1 $2
