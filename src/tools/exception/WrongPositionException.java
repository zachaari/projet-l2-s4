package tools.exception;

/**
 * Classe d'exception pour gérer les erreurs de posirtion
 */
public class WrongPositionException extends Exception {

    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public WrongPositionException(String msg){
        super(msg);
    }
}
