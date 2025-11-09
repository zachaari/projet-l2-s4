package tools.exception;
/**
 * Classe d'excepetion pour gérer les erreurs de tuile.
 */
public class WrongTileException extends Exception{

    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public WrongTileException(String msg){
        super(msg);
    }
    
}
