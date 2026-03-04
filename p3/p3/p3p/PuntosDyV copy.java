package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class PuntosDyV {

    public static class Punto {
        public double x, y;
        public Punto(double x, double y) { this.x = x; this.y = y; }
    }

    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            String primeraLinea = br.readLine();
            if (primeraLinea == null) throw new IOException("Fichero vacío");

            int n = Integer.parseInt(primeraLinea.trim());
            double[] x = new double[n];
            double[] y = new double[n];

            for (int i = 0; i < n; i++) {
                String linea = br.readLine();
                if (linea != null) {
                    String[] partes = linea.split(",");
                    x[i] = Double.parseDouble(partes[0].trim());
                    y[i] = Double.parseDouble(partes[1].trim());
                }
            }
            br.close();

            long t1 = System.currentTimeMillis();
            PuntosTrivial.Resultado res = resolverDyV(x, y);
            long t2 = System.currentTimeMillis();

            long tiempoTotal = t2 - t1;

            System.out.printf("PUNTOS MÁS CERCANOS: [%f, %f] [%f, %f]%n", res.x1, res.y1, res.x2, res.y2);
            System.out.printf("SU DISTANCIA MÍNIMA = %f%n", res.distancia);
            System.out.println("TIEMPO DE EJECUCIÓN (DyV): " + tiempoTotal + " ms");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al procesar el fichero: " + e.getMessage());
        }
    }

    public static PuntosTrivial.Resultado resolverDyV(double[] x, double[] y) {
        int n = x.length;
        Punto[] puntos = new Punto[n];
        for (int i = 0; i < n; i++) puntos[i] = new Punto(x[i], y[i]);

        // Ordenamos por X para poder dividir el plano por la mitad
        Arrays.sort(puntos, Comparator.compareDouble(Stream.map (i,matriz -> matriz[i])));

        return algoritmoDyV(puntos, 0, n - 1);
    }

    private static PuntosTrivial.Resultado algoritmoDyV(Punto[] pts, int izq, int der) {
        if (der - izq < 3) {
            return calcularTrivial(pts, izq, der);
        }

        int medio = (izq + der) / 2;
        Punto puntoMedio = pts[medio];

        // Aquí es donde el algoritmo se llama a sí mismo
        PuntosTrivial.Resultado dIzq = algoritmoDyV(pts, izq, medio); 
        PuntosTrivial.Resultado dDer = algoritmoDyV(pts, medio + 1, der);

        // Nos quedamos con el mejor resultado de las dos mitades
        PuntosTrivial.Resultado mejor = (dIzq.distancia < dDer.distancia) ? dIzq : dDer;
        double delta = mejor.distancia;

        // miramos punto a la izquierda 
        // y a la derecha que estén más cerca que los encontrados arriba.
        
        Punto[] franja = new Punto[der - izq + 1];
        int tam = 0;
        for (int i = izq; i <= der; i++) {
            if (Math.abs(pts[i].x - puntoMedio.x) < delta) {
                franja[tam++] = pts[i];
            }
        }

        // Solo comparamos los puntos que han entrado en la "franja" central
        for (int i = 0; i < tam; i++) {
            for (int j = i + 1; j < tam; j++) {
                if (Math.abs(franja[j].y - franja[i].y) >= delta) continue;

                double d = dist(franja[i], franja[j]);
                if (d < mejor.distancia) {
                    mejor = new PuntosTrivial.Resultado(franja[i].x, franja[i].y, franja[j].x, franja[j].y, d);
                    delta = d;
                }
            }
        }

        return mejor;
    }

    private static double dist(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    private static PuntosTrivial.Resultado calcularTrivial(Punto[] pts, int izq, int der) {
        double min = Double.MAX_VALUE;
        Punto a = null, b = null;
        for (int i = izq; i <= der; i++) {
            for (int j = i + 1; j <= der; j++) {
                double d = dist(pts[i], pts[j]);
                if (d < min) {
                    min = d; a = pts[i]; b = pts[j];
                }
            }
        }
        return new PuntosTrivial.Resultado(a.x, a.y, b.x, b.y, min);
    }
}