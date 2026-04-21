

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlmacenajeContenedoresRyP {

    private int capacidadC; 
    private int[] conjuntoS; 
    private int mejorK = Integer.MAX_VALUE; 
    private List<List<Integer>> mejorDistribucion = new ArrayList<>();

    public void load(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        capacidadC = Integer.parseInt(br.readLine().trim());
        String[] parts = br.readLine().trim().split("\\s+");
        conjuntoS = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            conjuntoS[i] = Integer.parseInt(parts[i]);
        }
        br.close();
    }

    public void solve() {
        List<List<Integer>> contenedores = new ArrayList<>();
        List<Integer> currentBinSums = new ArrayList<>();
        backtrack(0, contenedores, currentBinSums, sumatorio());
    }

    private int sumatorio() {
        int suma = 0;
        for(int i = 0; i < conjuntoS.length ; i++) {
            suma += conjuntoS[i];
        }
        return suma;
    }

    private int calcularK() {
        int k = 0;
        int pareja = 0;
        for(int i = 0; i < conjuntoS.length-2 ; i=+2 ) {
            pareja = 0;
            pareja = conjuntoS[i] + conjuntoS[i+1];
            if(pareja % conjuntoS.length > 0) {
                k ++;
            }
            k += pareja + conjuntoS.length;
        }
        if(conjuntoS.length % 2 == 0) {
            int parejaUlt = conjuntoS[conjuntoS.length-2] + conjuntoS[conjuntoS.length-1];
            if(parejaUlt % conjuntoS.length > 0) {
                k ++;
            }
        }else{
            int parejaUlt = conjuntoS[conjuntoS.length-1];
            if(parejaUlt % conjuntoS.length > 0) {
                k ++;
            }
        }
        k += pareja + conjuntoS.length;
        return k;
    }

    private void backtrack(int indexObject, List<List<Integer>> contenedores, List<Integer> currentBinSums, int sumaRestante) {
        
        int lowerBound = (sumaRestante +  conjuntoS.length - 1) / conjuntoS.length;
        
        if(contenedores.size() + lowerBound >= mejorK) {
            return;
        }
        
        if (contenedores.size()>= mejorK) {
            return;
        }

        if (indexObject == conjuntoS.length) {
            mejorK = contenedores.size();
            mejorDistribucion = new ArrayList<>();
            for (List<Integer> bin : contenedores) {
                mejorDistribucion.add(new ArrayList<>(bin));
            }
            return;
        }

        int obj = conjuntoS[indexObject];

        for (int i = 0; i < contenedores.size(); i++) {
            if (currentBinSums.get(i) + obj <= capacidadC) { 
                contenedores.get(i).add(obj);
                currentBinSums.set(i, currentBinSums.get(i) + obj);

                backtrack(indexObject + 1, contenedores, currentBinSums, sumaRestante - conjuntoS[indexObject]); 

                contenedores.get(i).remove(contenedores.get(i).size() - 1);
                currentBinSums.set(i, currentBinSums.get(i) - obj);
            }
        }

        List<Integer> nuevoContenedor = new ArrayList<>();
        nuevoContenedor.add(obj);
        contenedores.add(nuevoContenedor);
        currentBinSums.add(obj);

        backtrack(indexObject + 1, contenedores, currentBinSums, sumaRestante - conjuntoS[indexObject]); 

        contenedores.remove(contenedores.size() - 1);
        currentBinSums.remove(currentBinSums.size() - 1);
    }

    public void printSolution() {
        System.out.println("Lista de contenedores y objetos contenidos:");
        for (int i = 0; i < mejorDistribucion.size(); i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (int obj : mejorDistribucion.get(i)) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }
        System.out.println("El número de contenedores necesario es " + mejorK + ".");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java p6.AlmacenajeContenedores fichero.txt");
            return;
        }
        
        AlmacenajeContenedoresRyP solver = new AlmacenajeContenedoresRyP();
        try {
            solver.load(args[0]);
            solver.solve();
            solver.printSolution();
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
    }
}