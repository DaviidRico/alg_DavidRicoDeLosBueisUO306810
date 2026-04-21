

import java.io.File;

public class AlmacenajeContenedoresRyPTiempos {

    public static void main(String[] arg) {
        if (arg.length < 1) {
            System.out.println("Uso: java p6.AlmacenajeContenedoresRyPTiempos <nVeces>");
            return;
        }

        long t1, t2;
        int nVeces = Integer.parseInt(arg[0]);

        String[] ficheros = {
            "test00.txt", "test01.txt", "test02.txt", "test03.txt", "test04.txt", 
            "test05.txt", "test06.txt", "test07.txt", "test08.txt", "test09.txt"
        };

        for (String fichero : ficheros) {
            File f = new File(fichero);
            if (!f.exists()) {
                continue; 
            }

            t1 = System.currentTimeMillis();

            for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++) { 
                AlmacenajeContenedoresRyP solver = new AlmacenajeContenedoresRyP();
                try {
                    solver.load(fichero);
                    solver.solve();
                } catch (Exception e) {
                    System.err.println("Error procesando " + fichero + ": " + e.getMessage());
                }
            } 

            t2 = System.currentTimeMillis();

            System.out.println(" fichero=" + fichero + " **TIEMPO=" + (t2 - t1) + "**nVeces=" + nVeces);
        }  
    } 
}