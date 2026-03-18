import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Ferry{

    private int LFerry; //longitud del carril del ferry
    private List<Integer> vehicles; // lista de vehiculos
    private boolean[][] dp; // matriz con posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos

    private void loadData(String file) {
        this.vehicles = new ArrayList<Integer>();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            this.LFerry = Integer.valueOf(reader.readLine());
            for (String s: reader.readLine().split(" ")) {
                this.vehicles.add(Integer.valueOf(s));
            }
        } catch (FileNotFoundException e) {
            
        }
    }

    public Ferry(int longitud, List<Integer> vehicles) {
        this.LFerry = longitud;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][LFerry + 1];

        this.sumatorio = new int[vehicles.size() + 1];

        rellenarSumatorio();
    }

    private void rellenarSumatorio() {
        this.sumatorio[0] = 0;
        for(int i = 1; i < vehicles.size() + 1 ; i++) {
            this.sumatorio[i] = sumatorio[i - 1] + vehicles.get(i-1);
        }
    }

    public void run() {
        algoritmo();
    }

    public void printDatos() {
        System.out.printf("Longitud de los carriles: %d",LFerry);
        System.out.printf("Longitud de los vehiculos: \n\n");

        for(int i = 0; i < vehicles.size() ; i++) {
            System.out.printf("\tVehiculo $d: %D unidades\n", i+1, vehicles.get(i));
        }
    }

    private void algoritmo() {
        //inicializar
        dp[0][0] = true;

        for(int i = 1; i < vehicles.size() + 1 ; i++) {
            for(int j = LFerry; j >= 0; j--) {
                if(!dp[i-1][j]) {
                    continue;
                }

                //meter coche en babaor
                if(j + vehicles.get(i-1) <= LFerry) {
                    dp[i][j + vehicles.get(i-1)] = true;
                }
                //meter coche en estribor
                if(sumatorio[i] - j <= LFerry) {
                    dp[i][j] = true;
                }            
            
            }
        }
    }

}