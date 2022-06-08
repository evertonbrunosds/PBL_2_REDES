package control;

import model.RecycleBin;
import org.eclipse.paho.client.mqttv3.MqttException;
import uefs.evertonbrunosds.comumbase.controller.MQTTManagerController;
import util.Keyboard;

/**
 * Classe responsável por comportar-se como controlador de conexões MQTT.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class Control extends RecycleBin {

    /**
     * Refere-se a instância singular da classe.
     */
    private static Control instance;
    /**
     * Refere-se ao gerenciador de conexão MQTT usado para recebimento de
     * mensagens.
     */
    private final MQTTManagerController managerReceiver;
    /**
     * Refere-se ao gerenciador de conexão MQTT usado para envio de mensagens.
     */
    private final MQTTManagerController managerSender;
    /**
     * Refere-se ao quadrante da cidade em que o setor da lixeira cobre cobre.
     */
    private final String quadrant;

    /**
     * Método responsável por retornar instância singular do controlador de
     * conexões MQTT.
     *
     * @return Retorna instância singular do controlador de conexões MQTT.
     */
    public static synchronized Control getInstance() {
        if (instance == null) {
            final int latitude = Keyboard.sortNumber(255);
            final int longitude = Keyboard.sortNumber(255);
            final String serialNumber = Long.toString(Keyboard.sortNumber());
            instance = new Control(latitude, longitude, serialNumber);
        }
        return instance;
    }

    /**
     * Método responsável por executar as funções do intrínsecas do controlador.
     *
     * @throws MqttException Exceção lançada para o caso de haver problemas ao
     *                       estabelecer, bem como manter a conexão MQTT.
     */
    public void run() throws MqttException {
        managerReceiver.addMessagerListener(this::update);
    }

    /**
     * Construtor responsável por instanciar do controlador de conexões MQTT.
     *
     * @param latitude     Refere-se a latitude de onde se encontra a lixeira na
     *                     cidade.
     * @param longitude    Refere-se a longitude de onde se encontra a lixeira na
     *                     cidade.
     * @param serialNumber Refere-se ao número de série usado para identificar a
     *                     lixeira.
     */
    private Control(final int latitude, final int longitude, final String serialNumber) {
        super(Integer.toString(latitude), Integer.toString(longitude), serialNumber);
        quadrant = getQuadrant(latitude, latitude);
        managerSender = new MQTTManagerController("SECTOR"
                .concat("/")
                .concat(quadrant));
        managerReceiver = new MQTTManagerController("SECTOR"
                .concat("/")
                .concat(quadrant)
                .concat("/")
                .concat("RECYCLE_BIN")
                .concat("/")
                .concat(serialNumber));
    }

    /**
     * Método responsável por determinar por meio da latitude e longitude o
     * quadrante do qual a lixeira faz parte na cidade.
     *
     * @param y Refere-se ao eixo X do plano.
     * @param x Refere-se ao eixo Y do plano.
     * @return Retorna o quadrante al qual a lixeira faz parte na cidade.
     */
    private String getQuadrant(final int y, final int x) {
        return (y >= 0)
                ? (x >= 0) ? "1" : "2"
                : (x >= 0) ? "3" : "4";
    }

    /**
     * Método responsável por adicionar lixo a lixeira, bem como comunicar o
     * setor responsável sobre esta ocorrência.
     *
     * @param trashAmount Refere-se a quantidade de lixo adicionada.
     * @return Retorna indicativo de que os dados da lixeira foram atualizados.
     */
    @Override
    public boolean addTrash(final int trashAmount) {
        final boolean updatedUsage = super.addTrash(trashAmount);
        try {
            if (updatedUsage) {
                System.out.println("Enviando dados ao setor de quadrante: " + quadrant);
                managerSender.sendMessager(content -> {
                    getData().toMap().entrySet().forEach((entry) -> {
                        content.put(entry.getKey(), entry.getValue());
                    });
                });
            }
        } catch (final MqttException ex) {
            System.out.println("Falha ao enviar dados ao servidor!");
        }
        return updatedUsage;
    }

}
