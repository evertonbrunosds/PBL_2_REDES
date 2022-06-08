package view;

import control.Control;
import org.eclipse.paho.client.mqttv3.MqttException;

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
     * @throws MqttException Exceção lançada para o caso de haverem problemas de
     *                       conexão MQTT.
     */
    public static void main(final String[] args) throws MqttException {
        Control.getInstance().run();
    }

}
