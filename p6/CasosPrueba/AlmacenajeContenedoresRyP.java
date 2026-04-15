package p6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlmacenajeContenedoresRyP {

    private int C; // Capacidad máxima del contenedor
    private int[] items; // Tamaños de los objetos
    private int bestBins = Integer.MAX_VALUE; // Mínimo de contenedores encontrados
    private List<List<Integer>> bestAssignment = new ArrayList<>();

    // Lee el fichero de entrada
    public void load(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        C = Integer.parseInt(br.readLine().trim());
        String[] parts = br.readLine().trim().split("\\s+");
        items = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            items[i] = Integer.parseInt(parts[i]);
        }
        br.close();
    }

    // Inicia el algoritmo 
    public void solve() {
        
        List<List<Integer>> currentBins = new ArrayList<>();
        List<Integer> currentBinSums = new ArrayList<>();
        backtrack(0, currentBins, currentBinSums,sumatorio());
    }

    private int sumatorio() {
        int suma = 0;
        for(int i = 0; i < items.length ; i++) {
            suma += items[i];
        }
        return suma;
    }

    private int calcularK() {
        int k = 0;
        int pareja = 0;
        for(int i = 0; i < items.length-2 ; i=+2 ) {
            pareja = 0;
            pareja = items[i] + items[i+1];
            if(pareja % items.length > 0) {
                k ++;
            }
            k += pareja + items.length;
        }
        if(items.length % 2 == 0) {
            int parejaUlt = items[items.length-2] + items[items.length-1];
            if(parejaUlt % items.length > 0) {
                k ++;
            }
        }else{
            int parejaUlt = items[items.length-1];
            if(parejaUlt % items.length > 0) {
                k ++;
            }
        }
        k += pareja + items.length;
        return k;
    }

    // Algoritmo Backtracking con poda
    private void backtrack(int index, List<List<Integer>> currentBins, List<Integer> currentBinSums, int sumaRestante) {
        //LowerBound
        //Calcular el numero minimoTEORICOS de contenedores ADICIONALES necesarios
        int lowerBound = (sumaRestante +  items.length - 1) / items.length;
        
        if(currentBins.size() + lowerBound >= bestBins) {
            return;
        }
        // Poda: Si ya hemos igualado o superado el número de contenedores de la mejor solución, descartamos esta rama
        if (currentBins.size()>= bestBins) {
            return;
        }

        // Caso base: Todos los objetos han sido asignados a contenedores
        if (index == items.length) {
            bestBins = currentBins.size();
            bestAssignment = new ArrayList<>();
            for (List<Integer> bin : currentBins) {
                bestAssignment.add(new ArrayList<>(bin));
            }
            return;
        }

        int item = items[index];

        // Opción 1: Intentar meter el objeto en un contenedor ya existente
        for (int i = 0; i < currentBins.size(); i++) {
            if (currentBinSums.get(i) + item <= C) { // miramos capacidad
                currentBins.get(i).add(item);
                currentBinSums.set(i, currentBinSums.get(i) + item);

                backtrack(index + 1, currentBins, currentBinSums, sumaRestante - items[index]); 

                // borramos
                currentBins.get(i).remove(currentBins.get(i).size() - 1);
                currentBinSums.set(i, currentBinSums.get(i) - item);
            }
        }

        // Opción 2: Meter el objeto en un contenedor nuevo
        List<Integer> newBin = new ArrayList<>();
        newBin.add(item);
        currentBins.add(newBin);
        currentBinSums.add(item);

        backtrack(index + 1, currentBins, currentBinSums, sumaRestante - items[index]); 

        // Borramos
        currentBins.remove(currentBins.size() - 1);
        currentBinSums.remove(currentBinSums.size() - 1);
    }

    public void printSolution() {
        System.out.println("Lista de contenedores y objetos contenidos:");
        for (int i = 0; i < bestAssignment.size(); i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (int item : bestAssignment.get(i)) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
        System.out.println("El número de contenedores necesario es " + bestBins + ".");
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