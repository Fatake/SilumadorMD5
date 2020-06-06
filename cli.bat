#!/bin/bash
javac -d class Cliente.java
echo "Ejecutando Cliente"
java -cp class Cliente localhost 9999
pause
