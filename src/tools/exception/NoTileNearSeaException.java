package tools.exception;

/**
 * Classe d'exception qui gère si une tuile est adjacente à une tuile de type mer.
 */
public class NoTileNearSeaException extends Exception {

    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public NoTileNearSeaException(String msg){
        super(msg);
    }
}
