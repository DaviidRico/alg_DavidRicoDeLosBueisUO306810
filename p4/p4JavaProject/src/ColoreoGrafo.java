import java.util.*;

public class ColoreoGrafo {
    private static final List<String> colors = Arrays.asList(
        "red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime");

    public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo) {
        Map<String, String> resultado = new HashMap<>();

        for (String nodo : grafo.keySet()) {
            Set<String> coloresProhibidos = new HashSet<>();
            
            // Al estar normalizado, los vecinos ya son Strings
            for (String vecino : grafo.get(nodo)) {
                if (resultado.containsKey(vecino)) {
                    coloresProhibidos.add(resultado.get(vecino));
                }
            }

            // Asignar el primer color disponible
            for (String color : colors) {
                if (!coloresProhibidos.contains(color)) {
                    resultado.put(nodo, color);
                    break;
                }
            }
        }
        return resultado;
    }
}