import { writeFileSync } from 'node:fs';
import { join } from 'node:path';

interface Product {
  id: number;
  name: string;
  price: number;
}

interface Salesperson {
  documentType: string;
  documentNumber: number;
  firstNames: string;
  lastNames: string;
}

const NAMES = [
  'Carlos', 'Maria', 'Juan', 'Laura', 'Andres', 'Camila', 'Diego',
  'Valentina', 'Santiago', 'Isabella', 'Felipe', 'Daniela', 'Sebastian',
  'Mariana', 'Julian', 'Paula', 'Nicolas', 'Sofia', 'Alejandro', 'Natalia',
];
const SURNAMES = [
  'Gomez', 'Rodriguez', 'Martinez', 'Lopez', 'Garcia', 'Hernandez',
  'Perez', 'Sanchez', 'Ramirez', 'Torres', 'Flores', 'Rivera',
  'Gutierrez', 'Diaz', 'Vargas', 'Castro', 'Ortiz', 'Morales', 'Jimenez', 'Ruiz',
];
const PRODUCT_NAMES = [
  'Cuaderno', 'Lapicero', 'Borrador', 'Regla', 'Mochila', 'Calculadora',
  'Marcador', 'Tijeras', 'Cartuchera', 'Agenda', 'Resaltador', 'Tajalapiz',
  'Corrector', 'Colores', 'Carpeta',
];
const DOCUMENT_TYPES = ['CC', 'CE', 'TI'];
const randomItem = <T>(items: readonly T[]): T => items[Math.floor(Math.random() * items.length)];
const randomInt = (min: number, max: number): number => min + Math.floor(Math.random() * (max - min + 1));

function writeProductsFile(count: number, outputDir: string): Product[] {
  const products = Array.from({ length: count }, (_, index): Product => ({
    id: index + 1,
    name: `${randomItem(PRODUCT_NAMES)} tipo ${index + 1}`,
    price: randomInt(1000, 50999),
  }));
  writeFileSync(join(outputDir, 'productos.txt'), products.map(p => `${p.id};${p.name};${p.price}`).join('\n') + (count ? '\n' : ''), 'utf8');
  return products;
}

function writeSalespeopleFile(count: number, outputDir: string): Salesperson[] {
  const sellers = Array.from({ length: count }, (): Salesperson => ({
    documentType: randomItem(DOCUMENT_TYPES),
    documentNumber: 1_000_000_000 + randomInt(0, 899_999_999),
    firstNames: randomItem(NAMES),
    lastNames: randomItem(SURNAMES),
  }));
  writeFileSync(join(outputDir, 'vendedores.txt'), sellers.map(s =>
    `${s.documentType};${s.documentNumber};${s.firstNames};${s.lastNames}`,
  ).join('\n') + (count ? '\n' : ''), 'utf8');
  return sellers;
}

function writeSalesFile(seller: Salesperson, products: Product[], outputDir: string): void {
  const fullName = `${seller.firstNames} ${seller.lastNames}`;
  const fileName = `ventas_${seller.documentNumber}_${fullName.replace(/[^a-zA-Z0-9]/g, '')}.txt`;
  const lines = [`${seller.documentType};${seller.documentNumber}`];
  for (let i = 0, count = randomInt(3, 8); i < count; i += 1) {
    const product = randomItem(products);
    lines.push(`${product.id};${randomInt(1, 20)};`);
  }
  writeFileSync(join(outputDir, fileName), `${lines.join('\n')}\n`, 'utf8');
}

export function generateInfoFiles(outputDir = process.cwd()): void {
  const productCount = 15;
  const salespersonCount = 8;
  const products = writeProductsFile(productCount, outputDir);
  const sellers = writeSalespeopleFile(salespersonCount, outputDir);
  sellers.forEach(seller => writeSalesFile(seller, products, outputDir));
  console.log('Generación de archivos completada exitosamente.');
  console.log(`Productos generados: ${productCount}`);
  console.log(`Vendedores generados: ${salespersonCount}`);
}

if (require.main === module) {
  try {
    generateInfoFiles();
  } catch (error) {
    console.error('Ocurrió un error generando los archivos:', error);
    process.exitCode = 1;
  }
}
