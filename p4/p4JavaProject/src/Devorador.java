
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Devorador {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader("grafo.json")) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			Map<?, ?> rawGrafo = (Map<?, ?>) jsonObject.get("grafo");
			Map<String, List<String>> grafo = normalizarGrafo(rawGrafo);
			Map<String, String> solucion = ColoreoGrafo.realizarVoraz(grafo);
			try (FileWriter file = new FileWriter("solucion.json")) {
				file.write(new JSONObject(solucion).toJSONString());
			}

			if (solucion != null) {
				System.out.println("Solución encontrada: " + solucion);
			} else {
				System.out.println("No se encontró solución.");
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Normaliza el grafo leído del archivo JSON.
	 */
	static Map<String, List<String>> normalizarGrafo(Map<?, ?> rawGrafo) {
        Map<String, List<String>> limpio = new HashMap<>();
        
        for (Object key : rawGrafo.keySet()) {
            String nodoStr = String.valueOf(key);
            List<?> vecinosRaw = (List<?>) rawGrafo.get(key);
            List<String> vecinosStr = new ArrayList<>();
            
            if (vecinosRaw != null) {
                for (Object v : vecinosRaw) {
                    vecinosStr.add(String.valueOf(v));
                }
            }
            limpio.put(nodoStr, vecinosStr);
        }
        return limpio;
    }
}