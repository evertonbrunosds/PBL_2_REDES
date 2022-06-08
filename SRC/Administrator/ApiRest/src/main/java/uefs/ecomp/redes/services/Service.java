package uefs.ecomp.redes.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * Classe responsável por fornecer serviços de acesso a arquivos JSON.
 * 
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class Service {

    /**
     * Método responsável por retornar os dados das lixeiras disponíveis em todos os
     * setores.
     * 
     * @return Retorna os dados das lixeiras disponíveis em todos os setores.
     */
    public static List<JSONObject> getAllDataSectors() {
        final List<JSONObject> list = new LinkedList<>();
        final String stringDataSectors = loadFromFile("datasectors.json").iterator().next();
        if (stringDataSectors == null) {
            return new LinkedList<>();
        }
        final Map<String, Map<String, JSONObject>> jsonDataSectors = new HashMap<>();
        (new JSONObject(stringDataSectors)).toMap().entrySet().forEach(sector -> {
            final Map<String, JSONObject> jsonDataRecycleBins = new HashMap<>();
            (new JSONObject(sector.getValue().toString())).toMap().entrySet().forEach(recycleBin -> {
                list.add(new JSONObject(recycleBin.getValue().toString()));
            });
            jsonDataSectors.put(sector.getKey(), jsonDataRecycleBins);
        });
        return list;
    }

    /**
     * Método responsável por retornar os dados da lixeira cujo ID é fornecido.
     * 
     * @param id Refere-se ao id da dita lixeira.
     * @return Retorna os dados da dita lixeira.
     */
    public static String getRecycleBinById(final String id) {
        final Map<String, String> map = new HashMap<>();
        final String stringDataSectors = loadFromFile("datasectors.json").iterator().next();
        if (stringDataSectors == null) {
            return "";
        }
        final Map<String, Map<String, JSONObject>> jsonDataSectors = new HashMap<>();
        (new JSONObject(stringDataSectors)).toMap().entrySet().forEach(sector -> {
            final Map<String, JSONObject> jsonDataRecycleBins = new HashMap<>();
            (new JSONObject(sector.getValue().toString())).toMap().entrySet().forEach(recycleBin -> {
                map.put(recycleBin.getKey(), new JSONObject(recycleBin.getValue().toString()).toString());
            });
            jsonDataSectors.put(sector.getKey(), jsonDataRecycleBins);
        });
        return map.containsKey(id) ? map.get(id) : "Lixeira Não Encontrada!";
    }

    /**
     * Método responsável por carregar da memória arquivos JSON.
     * 
     * @param fileName Refere-se ao nome do arquivo.
     * @return Retorna estrutura iterável de linhas contidas no arquivo.
     */
    private static Iterable<String> loadFromFile(final String fileName) {
        final LinkedList<String> linked = new LinkedList<>();
        try (BufferedReader fileStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            fileStream.lines().forEach(linked::add);
        } catch (final Exception exception) {
            return new Iterable<String>() {
                @Override
                public Iterator<String> iterator() {
                    return new Iterator<String>() {
                        @Override
                        public boolean hasNext() {
                            return false;
                        }

                        @Override
                        public String next() {
                            return null;
                        }
                    };
                }
            };
        }
        return linked;
    }

}
