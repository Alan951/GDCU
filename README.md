# GDCU

### Gestor de contraseñas y útilidades

Este gestor de contraseñas busca facilitar y organizar las contraseñas de archivos comprimidos (zip y rar) que comúnmente se descargan de internet y que después de un tiempo su contraseña es olvidada.
Además, el gestor cuenta con otras utilidades, como por ejemplo el buscador de archivos, este brinda distintos filtros que podrán ser modificados según tus necesidades, además verificara los archivos comprimidos que tengan contraseña entre otras propiedades.
Este proyecto está hecho en Netbeans utilizando maven.

#####Estas son las dependencias utilizadas: 

* Commons-io-1.3.2
* fst-2.47
* jdatapicker-1.3.4
* swingx-all-1.6.5-1
* zip4j-1.3.2
* Jackson-core-2.5.3
* javaasist-3.19.0-GA
* objenesis-2.4

Otra herramienta necesaria para las utilidades que tengan que ver con la verificación de los archivos rar es el programa UnRAR el cual ya biene incluido en el proyecto.

La aplicación es bastante intuitiva y fácil de usar, si bien aún está en desarrollo, ya puede ser usada para tener una idea de cómo será finalmente.

#####Archivos que genera:

Algunos archivos son generados por la aplicación, por ejemplo, la carpeta Unrar la cual contiene un ejecutable UnRAR.exe, este programa es usado por GDCU para poder hacer verificaciones si archivos rar tienen contraseña y para validar si su contraseña es correcta, esto es requerido por algunas funciones de GDCU. Otro archivo que es generado por GDCU es GestorArchivoLista.dat, aquí se serializa la lista de los archivos que guardes en el gestor de contraseñas. En futuras modificaciones, GDCU te permitirá exportar dicha lista en formato JSON y en texto plano, además de permitirte escoger la ruta del archivo.

#####Como importar el proyecto a netbeans
Para descargar el proyecto a netbeans es relativamente sencillo, primero debes de ir a Team -> Git -> Clone… y en Repository URL: introduces https://github.com/Alan951/GDCU.git y listo, la clase principal está en mx.jalan.GDCU.Tests.GDCU
