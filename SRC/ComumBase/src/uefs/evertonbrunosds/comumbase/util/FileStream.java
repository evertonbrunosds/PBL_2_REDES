package uefs.evertonbrunosds.comumbase.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Classe responsável por comportar-se como arquivo em fluxo.
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class FileStream {

    /**
     * Método responsável por carregar arquivos da memória.
     * @param fileName Refere-se ao nome do arquivo.
     * @return Retorna dados contidos no arquivo.
     */
    public static String loadFromFile(final String fileName) {
        try (BufferedReader fileStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            return fileStream.lines().iterator().next();
        } catch (final Exception exception) {
            return null;
        }
    }

}
