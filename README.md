# Sales File Generator - First Deliverable

A Java project that generates sample text files containing products, salespeople, and sales records. These files will serve as input for the main sales-processing application, which is planned for a later deliverable.

## Team Members

- INGRID VIVIANA ARENAS MALDONADO
- JOHAN PATAQUIVA VARGAS
- NATHALYA BRIGITTE MENESES RAMIREZ
- CRISTHIAN FERNANDO MELO MONTILLA
- ORTIZ BERNAL DAYAN ANGELICA

## Features

- Generates 15 products with IDs, names, and unit prices.
- Generates 8 salespeople with document types, document numbers, first names, and last names.
- Generates one sales file per salesperson, with 3–8 sales records and 1–20 units per record.
- References generated product IDs in sales records.
- Runs without interactive input or external dependencies.

Data is randomly generated, so results vary between runs. Generation quantities can be adjusted in the `main` method of `GenerateInfoFiles.java`.

## Requirements

- JDK 8 or later, with `java` and `javac` available on your PATH.
- Optional: Eclipse IDE. Eclipse project configuration files are included.

## Compile and Run

Run these commands from the project root:

```sh
javac -encoding UTF-8 -d bin src/com/poli/ventas/GenerateInfoFiles.java
java -cp bin com.poli.ventas.GenerateInfoFiles
```

In Eclipse, import the repository as an existing project and run `GenerateInfoFiles` as a Java application, using the project root as the working directory.

## Generated Files

Files are written to the current working directory (the project root when following the instructions above). Fields are separated by semicolons.

### `productos.txt`

One product per line:

```text
ProductID;ProductName;UnitPrice
```

### `vendedores.txt`

One salesperson per line:

```text
DocumentType;DocumentNumber;FirstNames;LastNames
```

### `ventas_<id>_<name>.txt`

The filename includes the salesperson's document number and name without spaces. The first line identifies the salesperson; subsequent lines contain a product ID and quantity sold:

```text
DocumentType;DocumentNumber
ProductID;Quantity;
```

The formats above describe the fields; the generated files do not include column headers.

**Note:** Running the generator overwrites `productos.txt`, `vendedores.txt`, and any sales files with matching filenames. Sales files from previous runs with different filenames remain in the directory and may no longer match the current product or salesperson data.

## Project Structure

```text
src/com/poli/ventas/GenerateInfoFiles.java  # Entry point and file-generation logic
productos.txt                             # Generated products
vendedores.txt                            # Generated salespeople
ventas_*.txt                              # Generated sales records
```
