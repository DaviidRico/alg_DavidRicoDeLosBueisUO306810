import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DevoradorTiempos {
    public static void main(String[] args) {
        
        int repeticiones = Integer.parseInt(args[0]);
        int[] tamaños = {4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536};
        JSONParser parser = new JSONParser();

        System.out.println("n\t\t|\tt Coloreado (ms)");
        System.out.println("------------------------------------------");

        for (int n : tamaños) {
            String nombreArchivo = "sols/g" + n + ".json";
            
            try (FileReader reader = new FileReader(nombreArchivo)) {
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                
                Map<?, ?> rawGrafo = (Map<?, ?>) jsonObject.get("grafo");
                Map<String, List<String>> grafo = Devorador.normalizarGrafo(rawGrafo);

                long tTotal = 0;
                long t1, t2;
                
                // Bucle de repeticiones 
                for (int i = 0; i < repeticiones; i++) {
                    t1 = System.currentTimeMillis();
                    ColoreoGrafo.realizarVoraz(grafo);
                    t2 = System.currentTimeMillis();
                    tTotal += (t2 - t1);
                }

                System.out.println(n + "\t\t|\t" + tTotal + " ms\t\t|\t" + repeticiones);

            } catch (IOException | ParseException e) {
                System.err.println("Error al procesar el archivo g" + n + ".json: " + e.getMessage());
            }
        }
    }
}