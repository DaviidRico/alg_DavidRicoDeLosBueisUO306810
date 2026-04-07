import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Ferry{

    private int LFerry; //longitud del carril del ferry
    private List<Integer> vehicles; // lista de vehiculos
    private boolean[][] dp; // matriz con posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos


    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.err.println("Por favor, indica la ruta del archivo.");
            System.err.println("Uso: java Ferry <ruta_del_archivo>");
            return; 
        }

        String rutaArchivo = args[0]; 

        Ferry miFerry = new Ferry(0, new ArrayList<>()); 
        
        miFerry.run(rutaArchivo);

        miFerry.printDatos();
    }


    private void loadData(String file) {
    this.vehicles = new ArrayList<>();
    
    // Usamos 'try-with-resources' para asegurar que el BufferedReader se cierre automáticamente
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        
        String firstLine = reader.readLine();
        if (firstLine != null) {
            this.LFerry = Integer.parseInt(firstLine.trim());
        }

        String secondLine = reader.readLine();
        if (secondLine != null) {
            // El regex "\\s+" maneja casos donde haya múltiples espacios seguidos por error
            String[] vehiculosStr = secondLine.trim().split("\\s+"); 
            for (String s : vehiculosStr) {
                this.vehicles.add(Integer.parseInt(s));
            }
        }

        // ¡IMPORTANTE! Re-inicializamos las estructuras dinámicas con los nuevos datos leídos
        this.dp = new boolean[vehicles.size() + 1][LFerry + 1];
        this.sumatorio = new int[vehicles.size() + 1];
        rellenarSumatorio();

    } catch (IOException e) {
        System.err.println("Error de lectura o archivo no encontrado: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.err.println("Error: El archivo contiene caracteres no numéricos donde se esperaban números.");
    }
}   

    public Ferry() {
        this.vehicles = new ArrayList<>();
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

    public void run(String filePath) {
        // Le pasamos la ruta del archivo que recibimos por parámetro
        loadData(filePath); 
        algoritmo();
    }

    public void printDatos() {
        System.out.printf("Longitud de los carriles: %d\n", LFerry);
        System.out.println("Longitud de los vehiculos:");

        for(int i = 0; i < vehicles.size() ; i++) {
            System.out.printf("\tVehiculo %d: %d unidades\n", i + 1, vehicles.get(i));
        }

        System.out.println("\n--- Matriz DP ---");
        
        // Cabeceras
        System.out.print("V\\L |");
        for (int j = 0; j <= LFerry; j++) {
            System.out.printf("%3d", j);
        }
        System.out.println();
        
        // Separador
        for (int j = 0; j <= LFerry + 1; j++) {
            System.out.print("---");
        }
        System.out.println();

        // Matriz
        for(int i = 0; i <= vehicles.size(); i++) {
            // Indice fila
            System.out.printf("%3d |", i); 
            
            for(int j = 0; j <= LFerry; j++) {
                // True = T : False = .
                String valor = dp[i][j] ? " T " : " . ";
                System.out.print(valor);
            }
            System.out.println();
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