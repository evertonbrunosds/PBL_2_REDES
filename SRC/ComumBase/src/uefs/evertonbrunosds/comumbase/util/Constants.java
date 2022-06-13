package uefs.evertonbrunosds.comumbase.util;

/**
 * Classe responsável
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public final class Constants {

    /**
     * Classe responsável por comportar-se como constantes genéricas.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     */
    public static final class Generic {

        /**
         * Refere-se a chave de valor que indica status.
         */
        public static final String STATUS = "STATUS";

        /**
         * Refere-se a chave de valor que indica método.
         */
        public static final String METHOD = "METHOD";

        /**
         * Refere-se a chave de valor que indica ID.
         */
        public static final String ID = "ID";

        /**
         * Refere-se a chave de valor que indica todos os IDs.
         */
        public static final String ALL_IDS = "ALL_IDS";

        /**
         * Refere-se a chave de valor que indica o dispositivo.
         */
        public static final String DEVICE = "DEVICE";

        /**
         * Refere-se a valor verdadeiro.
         */
        public static final String TRUE = "TRUE";

        /**
         * Refere-se a valor falso.
         */
        public static final String FALSE = "FALSE";
    }

    /**
     * Classe responsável por comportar-se como constantes lixeira.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     */
    public static final class RecycleBin {

        /**
         * Refere-se a chave de valor que indica uso.
         */
        public static final String USAGE = "USAGE";

        /**
         * Refere-se a chave de valor que indica bloqueio.
         */
        public static final String IS_BLOCKED = "IS_BLOCKED";

        /**
         * Refere-se a chave de valor que indica localização.
         */
        public static final String LOCATION = "LOCATION";

        /**
         * Refere-se a chave de valor que indica a limpeza de uma lixeira.
         */
        public static final String CLEAR = "CLEAR";
        /**
         * Refere-se a chave de valor que indica a priorização de uma lixeira.
         */
        public static final String IS_PRIORITY = "IS_PRIORITY";
    }

    /**
     * Classe responsável por comportar-se como constante de endereçamento.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     */
    public static final class Address {

        /**
         * Refere-se ao endereço de URI ao broker.
         */
        public static final String DEFAULT_URI = "tcp://broker.mqttdashboard.com:1883";
        /**
         * Refere-se ao tópico padrão.
         */
        public static final String DEFAULT_TOPIC = "br/ecomp/uefs/evertonbrunosds/";
    }

    /**
     * Classe responsável por comportar-se como constantes de StatusCodeHTTP.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     */
    public static final class Status {

        /**
         * O servidor pode encontrar o recurso solicitado.
         */
        public static final String FOUND = "302";

        /**
         * O servidor não pode encontrar o recurso solicitado.
         */
        public static final String NOT_FOUND = "404";

        /**
         * O servidor não pode ou não irá processar a requisição devido a alguma
         * coisa que foi entendida como um erro do cliente.
         */
        public static final String BAD_REQUEST = "400";

        /**
         * O servidor encontrou uma situação com a qual não sabe lidar.
         */
        public static final String INTERNAL_SERVER_ERROR = "500";

    }

    /**
     * Classe responsável por comportar-se como constantes de métodos REST.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     */
    public static final class Method {

        /**
         * Responsável por buscar dados.
         */
        public static final String GET = "GET";
        /**
         * Responsável por atualizar dados.
         */
        public static final String PUT = "PUT";
        /**
         * Responsável por criar dados.
         */
        public static final String POST = "POST";
        /**
         * Responsável por apagar dados.
         */
        public static final String DELETE = "DELETE";

    }

}
