package model;

import org.json.JSONObject;
import static uefs.evertonbrunosds.comumbase.util.Constants.RecycleBin.CLEAR;
import static uefs.evertonbrunosds.comumbase.util.Constants.RecycleBin.USAGE;
import static uefs.evertonbrunosds.comumbase.util.Constants.RecycleBin.LOCATION;
import static uefs.evertonbrunosds.comumbase.util.Constants.Generic.TRUE;
import static uefs.evertonbrunosds.comumbase.util.Constants.Generic.ID;

/**
 * Classe responsável por comportar-se como lixeira.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class RecycleBin {

    /**
     * Refere-se aos dados da lixeira em JSON.
     */
    private final JSONObject data;
    /**
     * Refere-se a quantidade de lixo contido na lixeira.
     */
    private int trashAmount;
    /**
     * Refere-se a capacidade máxima de lixo da lixeira.
     */
    private static final int LIMIT = 100;

    /**
     * Construtor responsável por instanciar uma lixeira.
     *
     * @param latitude     Refere-se a sua latitude.
     * @param longitude    Refere-se a sua longitude.
     * @param serialNumber Refere-se ao seu número de série.
     */
    public RecycleBin(final String latitude, final String longitude, final String serialNumber) {
        data = new JSONObject();
        data.put(ID, serialNumber);
        data.put(LOCATION, latitude.concat(";").concat(longitude));
        data.put(USAGE, 0);
        trashAmount = 0;
    }

    /**
     * Método responsável por atualizar o estado de uso da lixeira.
     *
     * @param data Refere-se ao novo estado de uso da lixeira que está prestes
     *             da altera-la.
     */
    public void update(final JSONObject data) {
        if (data.getString(CLEAR).equals(TRUE)) {
            this.data.put(USAGE, 0);
            trashAmount = 0;
            System.out.println("\n Localização: ["
                    .concat(data.getString(LOCATION))
                    .concat("]. A lixeira \"")
                    .concat(data.getString(ID))
                    .concat("\" foi esvaziada."));
        }
    }

    /**
     * Método responsável por retornar os dados contidos na lixeira.
     *
     * @return Retorna os dados contidos na lixeira.
     */
    public JSONObject getData() {
        return new JSONObject(data.toMap());
    }

    /**
     * Método responsável por adicionar lixo a lixeira.
     *
     * @param trashAmount Refere-se a quantidade de lixo que será adicionada a
     *                    lixeira.
     * @return Retorna indicativo de que os dados da lixeira foram alterados.
     */
    public boolean addTrash(final int trashAmount) {
        final int beforeUsage = data.getInt(USAGE);
        this.trashAmount += trashAmount;
        if (this.trashAmount <= LIMIT) {
            data.put(USAGE, Integer.toString(this.trashAmount));
            System.out.println("\nLocalização: ["
                    .concat(data.getString(LOCATION))
                    .concat("]. Lixo adicionado a lixeira \"")
                    .concat(data.getString(ID) + "\". ")
                    .concat("Quantidade de lixo atual: ")
                    .concat(data.getString(USAGE))
                    .concat("/" + LIMIT + "."));
        } else {
            data.put(USAGE, Integer.toString(LIMIT));
            if (beforeUsage != data.getInt(USAGE)) {
                System.out.println("\nLocalização: ["
                        .concat(data.getString(LOCATION))
                        .concat("]. Lixo adicionado ao limite da lixeira \"")
                        .concat(data.getString(ID) + "\". ")
                        .concat("Quantidade de lixo atual: ")
                        .concat(data.getString(USAGE))
                        .concat("/" + LIMIT + "."));
            }
        }
        return beforeUsage != data.getInt(USAGE);
    }

}
