package uefs.evertonbrunosds.comumbase.controller;

import uefs.evertonbrunosds.comumbase.model.MQTTManager;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import static uefs.evertonbrunosds.comumbase.util.Constants.Address.*;
import uefs.evertonbrunosds.comumbase.util.Receiver;

/**
 * Classe responsável por comportar-se como controlador de gerenciador de
 * conexão MQTT.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class MQTTManagerController {

    /**
     * Refere-se ao gerenciador de conexão MQTT.
     */
    private final MQTTManager mQTTManager;
    private final String suffixTopic;

    /**
     * Construtor responsável por instanciar do controlador de conexão
     * MQTT.
     * 
     * @param suffixTopic Refere-se ao sufixo do tópico.
     */
    public MQTTManagerController(final String suffixTopic) {
        this.suffixTopic = suffixTopic == null ? "" : suffixTopic;
        mQTTManager = new MQTTManager(DEFAULT_URI);
        try {
            mQTTManager.open();
        } catch (final MqttException ex) {
        }
    }

    /**
     * Método responsável pelo envio de mensagens via JSON.
     *
     * @param content Refere-se ao conteúdo da mensagem.
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo de envio de mensagem.
     */
    public synchronized void sendMessager(final Receiver<JSONObject> content) throws MqttException {
        final JSONObject jSONObject = new JSONObject();
        content.receive(jSONObject);
        mQTTManager.publish(DEFAULT_TOPIC.concat(suffixTopic), jSONObject.toString().getBytes());
    }

    /**
     * Método responsável por adicionar um ouvinte de mensagem via JSON.
     *
     * @param content Refere-se ao conteúdo da mensagem.
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo ouvir mensagem.
     */
    public synchronized void addMessagerListener(final Receiver<JSONObject> content) throws MqttException {
        final IMqttMessageListener listener = (final String __, final MqttMessage message) -> {
            final JSONObject jSONObject = new JSONObject(new String(message.getPayload()));
            content.receive(jSONObject);
        };
        mQTTManager.subscribe(listener, DEFAULT_TOPIC.concat(suffixTopic));
    }

    /**
     * Método responsável por remover um tópico de leitura de mensagem.
     * 
     * @param topic Refere-se ao tópico removido.
     * @throws MqttException
     */
    public synchronized void removeMessagerListener(final String topic) throws MqttException {
        mQTTManager.unsubscribe(DEFAULT_TOPIC.concat(suffixTopic));
    }

    /**
     * Método responsável por indicar se o controlador de gerenciamento MQTT está
     * conectado.
     * 
     * @return Retorna indicativo de que o controlador de gerenciamento MQTT está
     *         conectado.
     */
    public synchronized boolean isConnected() {
        return mQTTManager.isConnected();
    }

    /**
     * Método responsável por abrir conexão com o servidor MQTT.
     * 
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo abrir conexão.
     */
    public void open() throws MqttException {
        mQTTManager.open();
    }

    /**
     * Método responsável por fechar conexão com o servidor MQTT.
     * 
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo fechar conexão.
     */
    public void close() throws MqttException {
        mQTTManager.close();
    }

}
