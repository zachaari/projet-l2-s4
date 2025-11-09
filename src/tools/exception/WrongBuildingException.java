package tools.exception;

/**
 *Classe d'exception pour gérer les erreurs de batiments des tuiles. 
 */
public class WrongBuildingException extends Exception {

    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public WrongBuildingException(String msg){
        super(msg);
    }
}
