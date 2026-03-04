package p3p;

import java.util.Random;

public class PuntosDyVTiempos {

    public static void main(String arg[]) {
        long t1, t2;

        for (int n = 1024; n <= 1000000000; n *= 2) {

            double[] x = new double[n];
            double[] y = new double[n];
            generarPuntosAleatorios(x, y);

            t1 = System.currentTimeMillis();

            PuntosDyV.resolverDyV(x, y);
            
            t2 = System.currentTimeMillis();

            long tiempoTotal = t2 - t1;

            System.out.println(" n=" + n + "**TIEMPO=" + tiempoTotal + " ma");

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