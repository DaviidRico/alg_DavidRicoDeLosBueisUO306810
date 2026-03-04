package p3p;

import java.util.Random;
import java.util.Locale;

public class PuntosTrivialTiempos {

    public static void main(String[] args) {
        int[] tamaños = {
            1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288};
            
        System.out.println("n\ttiempo");

        for (int n : tamaños) {
            double[] x = new double[n];
            double[] y = new double[n];
            generarPuntosAleatorios(x, y);

            long t1 = System.currentTimeMillis();
            PuntosTrivial.resolverTrivial(x, y);
            long t2 = System.currentTimeMillis();
            
            long tiempoTotal = t2 - t1;

            System.out.println(n + "\t" + tiempoTotal);
            
        }
    }

    private static void generarPuntosAleatorios(double[] x, double[] y) {
        Random rand = new Random();
        for (int i = 0; i < x.length; i++) {
            x[i] = rand.nextDouble() * 100.0;
            y[i] = rand.nextDouble() * 100.0;
        }
    }
}