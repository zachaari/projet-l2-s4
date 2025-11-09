package tools.exception;

/**
 * Classe d'exception qui gère les erreurs de type ressourcces
 */
public class WrongRessourceException extends Exception{

    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public WrongRessourceException(String msg){
        super(msg);
    }
}
