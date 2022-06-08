package uefs.evertonbrunosds.comumbase.model;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * Classe responsável por comportar-se como gerenciador de MQTT.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class MQTTManager implements MqttCallbackExtended {

    /**
     * Refere-se ao cliente de conexão MQTT.
     */
    private MqttClient client;
    /**
     * Refere-se ao URI do servidor MQTT.
     */
    private final String uRIServer;
    /**
     * Refere-se a configuração de opções da conexão.
     */
    private final MqttConnectOptions settings;

    /**
     * Construtor responsável por instanciar do gerenciador de MQTT.
     *
     * @param uRIServer Refere-se ao URI do servidor MQTT.
     */
    public MQTTManager(final String uRIServer) {
        settings = new MqttConnectOptions();
        settings.setAutomaticReconnect(true);
        settings.setKeepAliveInterval(10);
        settings.setConnectionTimeout(3);
        settings.setCleanSession(false);
        settings.setMaxInflight(200);
        this.uRIServer = uRIServer;
    }

    /**
     * Método responsável por realizar inscrição em tópico MQTT.
     *
     * @param listener Refere-se ao ouvinte da inscrição.
     * @param topic    Refere-se ao tópico da inscrição.
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo de inscrição de tópico.
     */
    public void subscribe(final IMqttMessageListener listener, final String topic) throws MqttException {
        if (client != null && topic != null) {
            client.subscribeWithResponse(topic, listener);
        }
    }

    /**
     * Método responsável por realizar desinscrição em tópico MQTT.
     *
     * @param topic
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo de desinscrição de tópico.
     */
    public void unsubscribe(final String topic) throws MqttException {
        if (client != null && topic != null && client.isConnected()) {
            client.unsubscribe(topic);
        }
    }

    /**
     * Método responsável por abrir a conexão MQTT com o servidor.
     *
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo de abertura de conexão.
     */
    public void open() throws MqttException {
        client = new MqttClient(uRIServer, String.format(
                "cliente_java_%d",
                System.currentTimeMillis()),
                new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
        client.setCallback(this);
        client.connect(settings);
    }

    /**
     * Método responsável por fechar a conexão MQTT com o servidor.
     *
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo de encerramento de conexão.
     */
    public void close() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            client.close();
        }
    }

    /**
     * Método responsável por publicar mensagens no servidor MQTT.
     *
     * @param topic    Refere-se ao tópico de destino da mensagem no servidor.
     * @param massager Refere-se a mensagem destinada ao servidor.
     * @throws MqttException Exceção lançada para o caso de haver falha no
     *                       processo de envio de mensagem.
     */
    public synchronized void publish(final String topic, final byte[] massager) throws MqttException {
        if (client.isConnected()) {
            client.publish(topic, massager, 0, false);
        } else {
        }
    }

    /**
     * Método responsável por retornar o estado de conexão MQTT.
     *
     * @return
     */
    public boolean isConnected() {
        return client == null ? null : client.isConnected();
    }

    /**
     * Método responsável por tratar de problemas quando há perda de conexão
     * MQTT.
     *
     * @param throwable Refere-se a exceção tratável.
     */
    @Override
    public void connectionLost(final Throwable throwable) {
        try {
            this.close();
            this.open();
        } catch (MqttException ex) {
            System.out.println("Conexão perdida, razão: " + ex.getMessage());
        }
    }

    /**
     * Método responsável por indicar que uma conexão foi completada ou não.
     *
     * @param reconnect Refere-se ao estado atual do processo.
     * @param serverURI Refere-se ao endereço do broker.
     */
    @Override
    public void connectComplete(final boolean reconnect, final String serverURI) {
        System.out.println("Cliente MQTT " + (reconnect ? "reconectado" : "conectado") + " com o broker " + serverURI);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage mqttMessage) throws Exception {
    }

}
