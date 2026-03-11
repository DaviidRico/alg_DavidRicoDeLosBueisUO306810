package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PuntosDyV {

    public static class Punto {
        public double x, y;
        public Punto(double x, double y) { this.x = x; this.y = y; }
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) return;
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

            PuntosTrivial.Resultado res = resolverDyV(x, y);

            System.out.printf("PUNTOS MÁS CERCANOS: [%f, %f] [%f, %f]%n", res.x1, res.y1, res.x2, res.y2);
            System.out.printf("SU DISTANCIA MÍNIMA = %f%n", res.distancia);

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // MÉTODO NO RECURSIVO:
    public static PuntosTrivial.Resultado resolverDyV(double[] x, double[] y) {
        int n = x.length;
        Punto[] puntos = new Punto[n];
        for (int i = 0; i < n; i++) puntos[i] = new Punto(x[i], y[i]);

        // Ordenamos para establecer la división física
        Arrays.sort(puntos, (p1, p2) -> Double.compare(p1.x, p2.x));

        int medio = (n / 2) - 1;

        PuntosTrivial.Resultado resMitadIzq = algoritmoDyV(puntos, 0, medio);
        PuntosTrivial.Resultado resMitadDer = algoritmoDyV(puntos, medio + 1, n - 1);

        PuntosTrivial.Resultado mejor = (resMitadIzq.distancia < resMitadDer.distancia) ? resMitadIzq : resMitadDer;

        // COMPARACIÓN CRÍTICA:
        double dUnion = dist(puntos[medio], puntos[medio + 1]);
        if (dUnion < mejor.distancia) {
            mejor = new PuntosTrivial.Resultado(puntos[medio].x, puntos[medio].y, 
                                               puntos[medio + 1].x, puntos[medio + 1].y, dUnion);
        }

        return mejor;
    }

    // MÉTODO RECURSIVO:
    private static PuntosTrivial.Resultado algoritmoDyV(Punto[] pts, int izq, int der) {
        // Caso base
        if (der - izq == 1) {
            double d = dist(pts[izq], pts[der]);
            return new PuntosTrivial.Resultado(pts[izq].x, pts[izq].y, pts[der].x, pts[der].y, d);
        }

        int medio = (izq + der) / 2;
        
        PuntosTrivial.Resultado dIzq = algoritmoDyV(pts, izq, medio);
        PuntosTrivial.Resultado dDer = algoritmoDyV(pts, medio + 1, der);

        return (dIzq.distancia < dDer.distancia) ? dIzq : dDer;
    }

    private static double dist(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
}