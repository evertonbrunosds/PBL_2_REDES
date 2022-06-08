package control;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import uefs.evertonbrunosds.comumbase.controller.MQTTManagerController;
import static uefs.evertonbrunosds.comumbase.util.Constants.RecycleBin.USAGE;
import static uefs.evertonbrunosds.comumbase.util.Constants.RecycleBin.CLEAR;
import static uefs.evertonbrunosds.comumbase.util.Constants.Generic.ID;
import static uefs.evertonbrunosds.comumbase.util.Constants.Generic.TRUE;

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
    private final MQTTManagerController managerReceiver;

    /**
     * Construtor responsável por instanciar do controlador de conexões MQTT.
     */
    private Control() {
        managerReceiver = new MQTTManagerController("GARBAGE_TRUCK");
    }

    /**
     * Método responsável por retornar instância singular do controlador de
     * conexões MQTT.
     *
     * @return Retorna instância singular do controlador de conexões MQTT.
     */
    public synchronized static Control getInstance() {
        if (instance == null) {
            instance = new Control();
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
            final JSONObject recycleBinCritical = new JSONObject(
                    content.toMap().entrySet().iterator().next().getValue().toString());
            final String sectorKey = content.toMap().entrySet().iterator().next().getKey();
            final MQTTManagerController managerSenderRecycleBin = new MQTTManagerController("SECTOR"
                    .concat("/")
                    .concat(sectorKey)
                    .concat("/")
                    .concat("RECYCLE_BIN")
                    .concat("/")
                    .concat(recycleBinCritical.getString(ID)));
            final MQTTManagerController managerSenderSector = new MQTTManagerController("SECTOR"
                    .concat("/")
                    .concat(sectorKey));
            try {
                managerSenderRecycleBin.sendMessager(contentSender -> contentSender.put(CLEAR, TRUE));
                managerSenderSector.sendMessager(contentSender -> {
                    recycleBinCritical.put(USAGE, "0");
                    recycleBinCritical.toMap().entrySet().forEach(entry -> {
                        contentSender.put(entry.getKey(), entry.getValue().toString());
                    });
                });
                managerSenderRecycleBin.close();
                managerSenderSector.close();
            } catch (MqttException ex) {
                System.out.println("Falha no caminhão!");
            }
        });
    }

}
