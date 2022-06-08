package control;

import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import uefs.evertonbrunosds.comumbase.controller.MQTTManagerController;
import static uefs.evertonbrunosds.comumbase.util.Constants.Generic.*;

/**
 * Classe responsável por comportar-se como controlador de conexões MQTT.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class Control {

    /**
     * Refere-se a instância singular da classe.
     */
    private static Control instance;
    /**
     * Refere-se ao gerenciador de conexão MQTT usado para recebimento de
     * mensagens.
     */
    public final MQTTManagerController managerReceiver;
    /**
     * Refere-se ao gerenciador de conexão MQTT usado para envio de mensagens.
     */
    private final MQTTManagerController managerSender;
    /**
     * Refere-se aos dados das lixeiras disponíveis no setor.
     */
    private final HashMap<String, String> data;
    /**
     * Refere-se ao quadrante da cidade em que o setor cobre.
     */
    private final String quadrant;

    /**
     * Construtor responsável por instanciar do controlador de conexões MQTT.
     * 
     * @param quadrant Refere-se ao quadrante do setor.
     */
    private Control(final String quadrant) {
        this.quadrant = quadrant;
        data = new HashMap<>();
        managerReceiver = new MQTTManagerController("SECTOR"
                .concat("/")
                .concat(quadrant));
        managerSender = new MQTTManagerController(null);
    }

    /**
     * Método responsável por retornar instância singular do controlador de
     * conexões MQTT.
     *
     * @param quadrant Refere-se ao quadrante do setor.
     * @return Retorna instância singular do controlador de conexões MQTT.
     */
    public synchronized static Control getInstance(final String quadrant) {
        if (instance == null) {
            instance = new Control(quadrant);
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
        managerReceiver.addMessagerListener(content -> {
            final String id = content.getString(ID);
            data.put(id, content.toString());
            System.out.println("RECEBENDO DADOS DA LIXEIRA CUJO ID É: \"" + id
                    .concat("\" -> ")
                    .concat(content.toString()));
            try {
                managerSender.sendMessager(messager -> {
                    System.out.println("REPASSANDO OS TODOS OS DADOS AO SERVIDOR MESTRE");
                    final JSONObject payload = new JSONObject();
                    data.entrySet().forEach(entry -> {
                        payload.put(entry.getKey(), entry.getValue());
                    });
                    messager.put(quadrant, payload.toString());
                });
            } catch (final MqttException ex) {
                System.out.println("Falha no setor: " + quadrant);
            }
        });
    }

}
