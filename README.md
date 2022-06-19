# PFC-Unity-Server

## Descripción
Servidor creado en Spring Boot. El cliente es un juego de Unity.

## Diagrama UML
<p align="center">
  <img src="https://i.imgur.com/jnIVLe9_d.webp?maxwidth=760&fidelity=grand"/>
</p>

## Arquitectura
<p align="center">
  <img src="https://i.imgur.com/4r1PwDf_d.webp?maxwidth=1000"/>
</p>


## ⚠ Requisitos
- Docker y Docker Compose (o ejecutar en H2).

## ⚙️ Mejoras que se pueden realizar
- Implementar correctamente los tests.

## ✏ Instrucciones
Inicializar la base de datos:
1. Abrir la consola y acceder a la carpeta **docker**.
2. **Escribir el siguiente comando para inicializar la base de datos con Docker Compose:** sudo docker-compose up -d
3. Ejecutar el programa con el parámetro de la ubicación donde se guardarán los datos a exportar.

## 🐛 En caso de fallo (reinicio base de datos)
1. **Para parar el adminer** sudo docker stop adminer
2. **Para parar MariaDB** sudo docker stop mariadb
3. **Para eliminar todas las imágenes, volúmenes, contenedores y redes no utilizadas:** sudo docker system prune -a --volumes
