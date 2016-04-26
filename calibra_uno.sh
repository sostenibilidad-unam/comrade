#!/bin/bash

/export/home/rgarcia/jre1.8.0_91/bin/java -Xss100m -Dfile.encoding=UTF-8 -classpath :jmatharray.jar:jmathplot.jar: Calibradores.EvolucionanCuencaAgri $1
