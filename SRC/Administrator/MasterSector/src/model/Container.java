package model;

/**
 * Classe responsável por comportar-se como container, sendo muito útil para
 * tornar variáveis visíveis não só em expressões lambdas, mas também em classes
 * internas anônimas.
 * 
 * @author Everton Bruno Silva dos Santos.
 * @param <T> Refere-se ao tipo de conteúdo contido no container.
 */
public class Container<T> {
    /**
     * Refere-se ao elemento contido no container.
     */
    private T element;

    /**
     * Construtor responsável por instanciar um container.
     */
    public Container() {
        element = null;
    }

    /**
     * Método responsável por retornar o elemento contido no container.
     * 
     * @return Retorna o elemento contido no container.
     */
    public T getElement() {
        return element;
    }

    /**
     * Método responsável por alterar o elemento contido no container.
     * 
     * @param element Refere-se ao novo elemento a ser contido no container.
     */
    public void setElement(final T element) {
        this.element = element;
    }

}
