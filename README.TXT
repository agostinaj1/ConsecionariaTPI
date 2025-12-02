------------------------------------------------------------------------
PROYECTO FINAL: SISTEMA DE GESTIÓN DE CONCESIONARIA
------------------------------------------------------------------------

TÍTULO DEL PROYECTO: Sistema de Gestión de Concesionaria (Versión Final)
PROPÓSITO: Trabajo final de programación para la gestión de vehículos, taller y lavadero.
VERSIÓN: 1.0
AUTOR/ES: BARRIOS CRISTIAN, JARA AGOSTINA, SVOBODA SOFIA. 

------------------------------------------------------------------------
INSTRUCCIONES PARA EJECUTAR EL PROYECTO (BLUEJ)
------------------------------------------------------------------------

1. ABRIR EL PROYECTO:
   - Simplemente abre el archivo "package.bluej" que está en esta carpeta.
   - Deberías ver todos los paquetes y la clase "Main" suelta.

2. COMPILAR:
   - Dale al botón "Compilar" (Compile) que está a la izquierda en BlueJ.
   - No debería tirar ningún error. Si ves todo rayado, dale a compilar de nuevo.

3. EJECUTAR:
   - Busca el cuadro naranja que dice "Main" (está abajo o a la izquierda de los paquetes).
   - Haz clic derecho sobre él.
   - Selecciona la opción: void main(String[] args).
   - Dale a "Aceptar" en la ventanita que sale.

------------------------------------------------------------------------
NOTAS SOBRE LOS DATOS (PERSISTENCIA)
------------------------------------------------------------------------

El programa usa archivos de texto para guardar los cambios y que no se pierdan al cerrar:

* vehiculos.csv -> Aquí está la base de datos de todos los autos y motos.
* cola_taller.txt -> Guarda los vehículos que quedaron esperando en el taller.
* cola_lavadero.txt -> Guarda los que quedaron en el lavadero.

IMPORTANTE:
Estos archivos deben estar siempre en la misma carpeta que el proyecto. Si el programa arranca y no ve datos, es posible que estos archivos estén vacíos o se hayan movido.

Si quieres reiniciar todo "de fábrica", puedes borrar el contenido de los .txt (pero no los borres a ellos, solo déjalos en blanco) y dejar el .csv como estaba al principio.

------------------------------------------------------------------------