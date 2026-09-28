# Generador de archivos de ventas

Proyecto en TypeScript con Node.js que genera archivos de texto de prueba para una futura aplicación de procesamiento de ventas. No procesa ventas ni solicita datos por teclado.

## Requisitos

- Node.js 18 o posterior
- npm

## Instalar y ejecutar

```sh
npm install
npm run generate
```

`npm run build` compila TypeScript a `dist/`; `npm start` ejecuta el JavaScript compilado. La generación escribe los archivos en el directorio de trabajo actual.

## Archivos generados

- `productos.txt`: 15 productos, con formato `ID;Nombre;Precio`.
- `vendedores.txt`: 8 vendedores, con formato `TipoDocumento;NumeroDocumento;Nombres;Apellidos`.
- `ventas_<documento>_<nombre>.txt`: un archivo por vendedor. La primera línea identifica al vendedor (`TipoDocumento;NumeroDocumento`); las siguientes contienen producto y cantidad (`IDProducto;Cantidad;`). Cada archivo tiene de 3 a 8 ventas, y cada cantidad es de 1 a 20.

Los datos son pseudoaleatorios y los campos están separados por punto y coma; no se incluyen encabezados. Al ejecutar el generador se sobrescriben `productos.txt`, `vendedores.txt` y los archivos de ventas cuyo nombre coincida. Los archivos de ventas antiguos con nombres diferentes no se borran automáticamente.

## Estructura

```text
src/GenerateInfoFiles.ts  # Generador y punto de entrada
productos.txt             # Datos generados de productos
vendedores.txt            # Datos generados de vendedores
ventas_*.txt              # Datos generados de ventas
```
