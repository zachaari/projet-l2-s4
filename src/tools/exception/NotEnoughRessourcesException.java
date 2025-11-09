package tools.exception;
/**
 * Classe d'exception pour gérer les erreurs par rapport aux ressources des joueurs.
 */
public class NotEnoughRessourcesException extends Exception{

    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public NotEnoughRessourcesException(String msg){
        super(msg);
    }
}
