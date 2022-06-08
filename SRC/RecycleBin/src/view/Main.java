package view;

import control.Control;
import static java.lang.Thread.sleep;
import org.eclipse.paho.client.mqttv3.MqttException;
import util.Keyboard;

/**
 * Classe responsável por iniciar a aplicação.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class Main {

    /**
     * Método responsável por iniciar a aplicação;
     *
     * @param args Refere-se aos argumentos da inicialização.
     * @throws MqttException        Exceção lançada para o caso de haverem problemas
     *                              de conexão MQTT.
     * @throws InterruptedException
     */
    public static void main(String[] args) throws MqttException, InterruptedException {
        Control.getInstance().run();
        while (true) {
            sleep(Keyboard.sortPositiveNumber(2500) + 5000);
            Control.getInstance().addTrash(Keyboard.sortPositiveNumber(15));
        }
    }

}
