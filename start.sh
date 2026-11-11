#!/usr/bin/env bash
cd "$(dirname "$0")"

if ! command -v java >/dev/null 2>&1; then
    echo "No se ha encontrado Java. Instala Java 17 o superior desde https://adoptium.net"
    exit 1
fi

export ABRIR_NAVEGADOR="${ABRIR_NAVEGADOR:-true}"
java -jar tabla-tiempos.jar
