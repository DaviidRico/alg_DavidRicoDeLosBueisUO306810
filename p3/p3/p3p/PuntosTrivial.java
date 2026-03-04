package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class PuntosTrivial {

    public static class Resultado {
        public double x1, y1, x2, y2, distancia;
        public Resultado(double x1, double y1, double x2, double y2, double dist) {
            this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2; this.distancia = dist;
        }
    }

    public static Resultado resolverTrivial(double[] x, double[] y) {
        int n = x.length;
        double distMin = Double.MAX_VALUE;
        int p1 = 0, p2 = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double d = Math.sqrt(Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
                if (d < distMin) {
                    distMin = d;
                    p1 = i; p2 = j;
                }
            }
        }
        return new Resultado(x[p1], y[p1], x[p2], y[p2], distMin);
    }

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            int n = Integer.parseInt(br.readLine().trim());
            double[] x = new double[n];
            double[] y = new double[n];

            for (int i = 0; i < n; i++) {
                String[] partes = br.readLine().split(",");
                x[i] = Double.parseDouble(partes[0].trim());
                y[i] = Double.parseDouble(partes[1].trim());
            }

            long t1 = System.currentTimeMillis();
            Resultado res = resolverTrivial(x, y);
            long t2 = System.currentTimeMillis();

            System.out.printf(Locale.US, "PUNTOS MÁS CERCANOS: [%f, %f] [%f, %f]%n", res.x1, res.y1, res.x2, res.y2);
            System.out.printf(Locale.US, "SU DISTANCIA MÍNIMA = %f%n", res.distancia);
            System.out.println("Tiempo de ejecución: " + (t2 - t1) + " ms");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}