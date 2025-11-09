package tools.exception;
/**
 *Classe pour gerer les erreur des batiment
 */
public class AlreadyBuildingPlayerException extends Exception{
    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public AlreadyBuildingPlayerException(String msg){
        super(msg);
    }
}
