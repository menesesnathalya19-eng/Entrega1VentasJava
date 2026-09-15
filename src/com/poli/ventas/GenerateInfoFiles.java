package com.poli.ventas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Genera los archivos planos de prueba (productos, vendedores y ventas)
 * que servirán como entrada para el programa principal del proyecto,
 * el cual se implementará en una entrega posterior.
 *
 * Al ejecutarse esta clase, se generan de forma pseudoaleatoria pero
 * coherente:
 * <ul>
 *   <li>Un archivo con la información de los productos disponibles.</li>
 *   <li>Un archivo con la información de los vendedores.</li>
 *   <li>Un archivo de ventas por cada vendedor generado.</li>
 * </ul>
 *
 * Todos los archivos se generan en la carpeta raíz del proyecto y el
 * programa no solicita ningún dato por teclado al usuario.
 *
 * @author INGRID VIVIANA ARENAS MALDONADO
 * @author JOHAN PATAQUIVA VARGAS
 * @author NATHALYA BRIGITTE MENESES RAMIREZ
 * @author CRISTHIAN FERNANDO MELO MONTILLA
 * @author ORTIZ BERNAL DAYAN ANGELICA
 */
public class GenerateInfoFiles {

    /** Generador de números pseudoaleatorios reutilizado en toda la clase. */
    private static final Random RANDOM = new Random();

    /** Nombres usados para generar vendedores con datos creíbles. */
    private static final String[] NOMBRES = {
        "Carlos", "Maria", "Juan", "Laura", "Andres", "Camila", "Diego",
        "Valentina", "Santiago", "Isabella", "Felipe", "Daniela", "Sebastian",
        "Mariana", "Julian", "Paula", "Nicolas", "Sofia", "Alejandro", "Natalia"
    };

    /** Apellidos usados para generar vendedores con datos creíbles. */
    private static final String[] APELLIDOS = {
        "Gomez", "Rodriguez", "Martinez", "Lopez", "Garcia", "Hernandez",
        "Perez", "Sanchez", "Ramirez", "Torres", "Flores", "Rivera",
        "Gutierrez", "Diaz", "Vargas", "Castro", "Ortiz", "Morales",
        "Jimenez", "Ruiz"
    };

    /** Nombres base usados para generar productos. */
    private static final String[] PRODUCTOS_BASE = {
        "Cuaderno", "Lapicero", "Borrador", "Regla", "Mochila", "Calculadora",
        "Marcador", "Tijeras", "Cartuchera", "Agenda", "Resaltador",
        "Tajalapiz", "Corrector", "Colores", "Carpeta"
    };

    /** Tipos de documento válidos para los vendedores. */
    private static final String[] TIPOS_DOCUMENTO = { "CC", "CE", "TI" };

    /**
     * Guarda en memoria los productos generados en la última llamada a
     * {@link #createProductsFile(int)}, para poder referenciar IDs de
     * producto válidos al generar los archivos de ventas.
     */
    private static final List<Producto> productosGenerados = new ArrayList<>();

    /**
     * Guarda en memoria los vendedores generados en la última llamada a
     * {@link #createSalesManInfoFile(int)}, para poder generar sus
     * respectivos archivos de ventas con el tipo de documento correcto.
     */
    private static final List<Vendedor> vendedoresGenerados = new ArrayList<>();

    /** Representa un producto generado internamente. */
    private static class Producto {
        long id;
        String nombre;
        double precio;

        Producto(long id, String nombre, double precio) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
        }
    }

    /** Representa un vendedor generado internamente. */
    private static class Vendedor {
        String tipoDocumento;
        long numeroDocumento;
        String nombres;
        String apellidos;

        Vendedor(String tipoDocumento, long numeroDocumento, String nombres, String apellidos) {
            this.tipoDocumento = tipoDocumento;
            this.numeroDocumento = numeroDocumento;
            this.nombres = nombres;
            this.apellidos = apellidos;
        }
    }

    /**
     * Punto de entrada del programa. Genera el archivo de productos, el
     * archivo de vendedores y un archivo de ventas por cada vendedor.
     * No solicita ningún dato al usuario: las cantidades están definidas
     * dentro de este método.
     *
     * @param args no se usan.
     */
    public static void main(String[] args) {
        try {
            int cantidadProductos = 15;
            int cantidadVendedores = 8;
            int ventasMinPorVendedor = 3;
            int ventasMaxPorVendedor = 8;

            createProductsFile(cantidadProductos);
            createSalesManInfoFile(cantidadVendedores);

            for (Vendedor vendedor : vendedoresGenerados) {
                int cantidadVentas = ventasMinPorVendedor
                        + RANDOM.nextInt(ventasMaxPorVendedor - ventasMinPorVendedor + 1);
                createSalesMenFile(cantidadVentas, vendedor.nombres + " " + vendedor.apellidos,
                        vendedor.numeroDocumento);
            }

            System.out.println("Generacion de archivos completada exitosamente.");
            System.out.println("Productos generados: " + cantidadProductos);
            System.out.println("Vendedores generados: " + cantidadVendedores);

        } catch (IOException e) {
            System.err.println("Ocurrio un error generando los archivos: " + e.getMessage());
        }
    }

    /**
     * Genera un archivo de ventas pseudoaleatorio para un vendedor
     * específico. El archivo se llama "ventas_&lt;id&gt;_&lt;nombre&gt;.txt"
     * y sigue el formato: la primera línea contiene el tipo y número de
     * documento del vendedor, y cada línea siguiente contiene el ID de un
     * producto junto con la cantidad vendida.
     *
     * @param randomSalesCount cantidad de líneas de venta a generar.
     * @param name nombre completo del vendedor (se usa para nombrar el
     *        archivo de forma más legible).
     * @param id número de documento del vendedor.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
        String tipoDocumento = buscarTipoDocumento(id);
        String nombreArchivo = "ventas_" + id + "_" + name.replaceAll("[^a-zA-Z0-9]", "") + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(tipoDocumento + ";" + id);
            writer.newLine();

            for (int i = 0; i < randomSalesCount; i++) {
                long idProducto = obtenerIdProductoValido();
                int cantidadVendida = 1 + RANDOM.nextInt(20);
                writer.write(idProducto + ";" + cantidadVendida + ";");
                writer.newLine();
            }
        }
    }

    /**
     * Genera un archivo con información pseudoaleatoria de productos.
     * El archivo se llama "productos.txt" y cada línea tiene el formato:
     * IDProducto;NombreProducto;PrecioPorUnidad.
     *
     * @param productsCount cantidad de productos a generar.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public static void createProductsFile(int productsCount) throws IOException {
        productosGenerados.clear();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (long i = 1; i <= productsCount; i++) {
                String nombreBase = PRODUCTOS_BASE[RANDOM.nextInt(PRODUCTOS_BASE.length)];
                String nombreProducto = nombreBase + " tipo " + i;
                double precio = 1000 + RANDOM.nextInt(50000);

                productosGenerados.add(new Producto(i, nombreProducto, precio));
                writer.write(i + ";" + nombreProducto + ";" + precio);
                writer.newLine();
            }
        }
    }

    /**
     * Genera un archivo con información pseudoaleatoria pero coherente de
     * vendedores. El archivo se llama "vendedores.txt" y cada línea tiene
     * el formato: TipoDocumento;NumeroDocumento;Nombres;Apellidos.
     *
     * @param salesmanCount cantidad de vendedores a generar.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        vendedoresGenerados.clear();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("vendedores.txt"))) {
            for (int i = 0; i < salesmanCount; i++) {
                String tipoDocumento = TIPOS_DOCUMENTO[RANDOM.nextInt(TIPOS_DOCUMENTO.length)];
                long numeroDocumento = 1000000000L + RANDOM.nextInt(900000000);
                String nombres = NOMBRES[RANDOM.nextInt(NOMBRES.length)];
                String apellidos = APELLIDOS[RANDOM.nextInt(APELLIDOS.length)];

                vendedoresGenerados.add(new Vendedor(tipoDocumento, numeroDocumento, nombres, apellidos));
                writer.write(tipoDocumento + ";" + numeroDocumento + ";" + nombres + ";" + apellidos);
                writer.newLine();
            }
        }
    }

    /**
     * Busca el tipo de documento de un vendedor ya generado a partir de su
     * número de documento. Si no se encuentra (por ejemplo, si se llama a
     * createSalesMenFile de forma independiente), se usa "CC" por defecto.
     *
     * @param id número de documento del vendedor.
     * @return el tipo de documento correspondiente.
     */
    private static String buscarTipoDocumento(long id) {
        for (Vendedor vendedor : vendedoresGenerados) {
            if (vendedor.numeroDocumento == id) {
                return vendedor.tipoDocumento;
            }
        }
        return "CC";
    }

    /**
     * Obtiene un ID de producto válido a partir de los productos generados
     * previamente. Si aún no se ha generado ningún producto, se retorna un
     * ID pseudoaleatorio entre 1 y 10 como respaldo.
     *
     * @return un ID de producto.
     */
    private static long obtenerIdProductoValido() {
        if (productosGenerados.isEmpty()) {
            return 1 + RANDOM.nextInt(10);
        }
        return productosGenerados.get(RANDOM.nextInt(productosGenerados.size())).id;
    }
}