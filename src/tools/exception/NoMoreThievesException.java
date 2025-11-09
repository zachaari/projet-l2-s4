package tools.exception;

/**
 * Classe d'exception pour gerer le nombre de voleurs de demeter.
 */
public class NoMoreThievesException extends Exception {

    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public NoMoreThievesException(String msg){
        super(msg);
    }
}
