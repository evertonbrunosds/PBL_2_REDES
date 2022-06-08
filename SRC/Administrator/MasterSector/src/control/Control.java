package control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import model.Container;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import uefs.evertonbrunosds.comumbase.controller.MQTTManagerController;
import static uefs.evertonbrunosds.comumbase.util.Constants.RecycleBin.USAGE;
import uefs.evertonbrunosds.comumbase.util.Factory;

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
     * Refere-se ao gerenciador de conexão MQTT usado para envio de mensagens.
     */
    private final MQTTManagerController managerSender;
    /**
     * Refere-se aos dados das lixeiras disponíveis em todos os setores.
     */
    private final JSONObject data;

    /**
     * Construtor responsável por instanciar do controlador de conexões MQTT.
     */
    private Control() {
        managerReceiver = new MQTTManagerController(null);
        managerSender = new MQTTManagerController("GARBAGE_TRUCK");
        data = new JSONObject();
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
            content.toMap().entrySet().forEach(entry -> {
                data.put(entry.getKey(), entry.getValue().toString());
                System.out.println("RECEBENDO DADOS DO SETOR :" + entry.getKey());
            });
        });
        Factory.thread(() -> {
            while (true) {
                try {
                    sleep(2500);
                    saveFromFile("datasectors.json");
                    final Container<Integer> maxValue = new Container<>();
                    maxValue.setElement(0);
                    final Container<JSONObject> criticalRecycleBin = new Container<>();
                    final Container<String> sectorKey = new Container<>();
                    data.toMap().entrySet().forEach(entry -> {
                        final String currentKeySector = entry.getKey();
                        final JSONObject currentSector = new JSONObject(entry.getValue().toString());
                        currentSector.toMap().entrySet().forEach(subEntry -> {
                            JSONObject currentRecycleBin = new JSONObject(subEntry.getValue().toString());
                            if (maxValue.getElement() < currentRecycleBin.getInt(USAGE)) {
                                maxValue.setElement(currentRecycleBin.getInt(USAGE));
                                sectorKey.setElement(currentKeySector);
                                criticalRecycleBin.setElement(currentRecycleBin);
                            }
                        });
                    });
                    if (criticalRecycleBin.getElement() != null) {
                        managerSender.sendMessager(content -> {
                            content.put(sectorKey.getElement(), criticalRecycleBin.getElement().toString());
                        });
                    }
                } catch (final IOException | InterruptedException | MqttException ex) {
                }
            }
        }).start();
    }

    /**
     * Método responsável por salvar em arquivo os dados das lixeiras contidas em
     * todos os setores.
     * 
     * @param fileName Refere-se ao nome do fito arquivo.
     * @throws FileNotFoundException Exceção lançada para o caso de haverem
     *                               problemas na escrita do arquivo.
     */
    private void saveFromFile(final String fileName) throws IOException {
        try (final PrintWriter fileStream = new PrintWriter(new FileOutputStream(fileName, false))) {
            fileStream.println(data.toString());
        }
    }

}
