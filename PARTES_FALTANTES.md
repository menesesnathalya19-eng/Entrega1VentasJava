# Estado del proyecto - Entrega 2

## Qué se hizo para esta entrega

Para la Entrega 2, el trabajo del grupo consistió en migrar el proyecto de Java a TypeScript con Node.js, cambio autorizado verbalmente por el profesor en clase.

El proyecto original en Java de la Entrega 1 se puede consultar aquí: https://github.com/menesesnathalya19-eng/Entrega1VentasJava

En esta migración se conservó toda la funcionalidad ya construida en la Entrega 1:

- Generación de `productos.txt`: 15 productos con ID, nombre y precio.
- Generación de `vendedores.txt`: 8 vendedores con tipo de documento, número, nombres y apellidos.
- Generación de `ventas_<documento>_<nombre>.txt`: un archivo de ventas por cada vendedor (entre 3 y 8 transacciones cada uno).
- El programa no solicita ningún dato al usuario y muestra mensaje de finalización exitosa o de error.

## Qué falta para el proyecto completo

- El programa que procesa los archivos generados y crea los dos reportes finales:
  1. Reporte de vendedores ordenado de mayor a menor según el dinero recaudado.
  2. Reporte de productos vendidos ordenado de mayor a menor según la cantidad vendida.
- Mensaje de éxito/error para ese programa de procesamiento.
- El archivo `conclusion.txt` con la reflexión final del proyecto.
