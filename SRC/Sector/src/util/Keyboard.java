package util;

import java.util.Random;

/**
 * Classe responsável por comportar-se como teclado
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public final class Keyboard {

    /**
     * Método responsável por sortear números positivos.
     *
     * @param range Refere-se a faixa de valores possíveis se serem sorteados.
     * @return Retorna número sorteado no range indicado.
     */
    public static int sortPositiveNumber(final int range) {
        return (new Random()).nextInt(range);
    }

    /**
     * Método responsável por sortear números positivos e negativos.
     *
     * @param range Refere-se a faixa de valores possíveis se serem sorteados.
     * @return Retorna número sorteado no range indicado.
     */
    public static int sortNumber(final int range) {
        final int number = (new Random()).nextInt(range);
        final boolean isPositive = (new Random()).nextBoolean();
        return isPositive ? number : number * -1;
    }

}
