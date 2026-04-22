import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LaberintoTodas {

    // Variables globales para guardar el estado de la mejor solución
    static int[][] mejorLaberinto = new int[7][7];
    static int minPasos = Integer.MAX_VALUE;
    static int totalSoluciones = 0;

    static private int posInicial = 0;
    static private int posFinal = 48;
    static private ArrayList<int[][]> laberintosG = new ArrayList<>();

    public static void main(String[] args) {
        String nombreFichero = args[0];
        
        int[][] laberinto = cargarLaberinto(nombreFichero);

        int filaInicio = posInicial / 7;
        int colInicio = posInicial % 7;
        int filaFin = posFinal / 7;
        int colFin = posFinal % 7;

        System.out.println("EL LABERINTO ES INICIALMENTE DEL SIGUIENTE MODO:");
        imprimirMatriz(laberinto);

        resolverLaberinto(laberinto, filaInicio, colInicio, filaFin, colFin, 1);

        for(int i = 0; i < totalSoluciones; i++){
                imprimirMatriz(laberintosG.get(i));
        }
    }

    public static int[][] cargarLaberinto(String rutaFichero) {
        int[][] laberintoLeido = new int[7][7]; // Creamos la matriz vacía de 7x7
        
        try {
            File archivo = new File(rutaFichero);
            Scanner lector = new Scanner(archivo);
            
            // Recorremos las 7 filas y 7 columnas
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    // Verificamos que hay un número para leer
                    if (lector.hasNextInt()) {
                        laberintoLeido[i][j] = lector.nextInt();
                    }
                }
            }
            lector.close(); // Siempre es buena práctica cerrar el archivo al terminar
            
        } catch (FileNotFoundException e) {
            System.out.println("¡Error! No se ha podido encontrar el fichero: " + rutaFichero);
            System.out.println("Asegúrate de que está en la carpeta correcta.");
        }
        
        return laberintoLeido;
    }

    private static boolean esValido(int[][] laberinto, int fila, int col) {
        if (fila < 0 || fila >= 7 || col < 0 || col >= 7) {
            return false;
        }
        if (laberinto[fila][col] == 1 || laberinto[fila][col] == 2) {
            return false;
        }
        return true;
    }

    public static void resolverLaberinto(int[][] laberinto, int filaActual, int colActual, int filaFin, int colFin, int pasos) {
        
        //no tengo mas movimientos
        if (!esValido(laberinto, filaActual, colActual)) {
            return;
        }

        //Marcamos que hemos pasado
        laberinto[filaActual][colActual] = 2;

        //Si entra llegamos al final 
        if (filaActual == filaFin && colActual == colFin) {
            totalSoluciones++;
            laberintosG.add(laberinto);
            
            // Si entra es la mejor
            if (pasos < minPasos) {
                minPasos = pasos;
                guardarMejorLaberinto(laberinto);
            }
        } else {
            // miramos todas las posibilidades
            resolverLaberinto(laberinto, filaActual + 1, colActual, filaFin, colFin, pasos + 1);
            resolverLaberinto(laberinto, filaActual - 1, colActual, filaFin, colFin, pasos + 1); 
            resolverLaberinto(laberinto, filaActual, colActual + 1, filaFin, colFin, pasos + 1); 
            resolverLaberinto(laberinto, filaActual, colActual - 1, filaFin, colFin, pasos + 1); 
        }

        //deshacer
        laberinto[filaActual][colActual] = 0;
    }

    private static void guardarMejorLaberinto(int[][] laberintoActual) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                mejorLaberinto[i][j] = laberintoActual[i][j];
            }
        }
    }

    private static void imprimirMatriz(int[][] matriz) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (matriz[i][j] == 0) {
                    System.out.print("0 ");
                } else if (matriz[i][j] == 1) {
                    System.out.print("1 ");
                } else {
                    System.out.print("2 ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void imprimirMejorMatriz() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (mejorLaberinto[i][j] == 0) {
                    System.out.print(". ");
                } else if (mejorLaberinto[i][j] == 1) {
                    System.out.print("H ");
                } else if (mejorLaberinto[i][j] == 2) {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}