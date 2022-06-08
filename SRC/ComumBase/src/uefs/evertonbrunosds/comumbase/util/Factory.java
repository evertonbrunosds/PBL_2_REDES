package uefs.evertonbrunosds.comumbase.util;

/**
 * Classe responsável por construir instâncias de objetos.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface Factory {

    /**
     * Método responsável por gerar instância de thread sem uso de semáforos.
     *
     * @param worker Refere-se ao trabalhador que desempenhará dado trabalho.
     * @return Retorna instância de thread sem uso de semáforos.
     */
    public static Thread thread(final Worker worker) {
        return new java.lang.Thread() {
            @Override
            public void run() {
                worker.work();
            }
        };
    }

}
