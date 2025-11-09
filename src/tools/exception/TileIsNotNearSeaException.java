package tools.exception;
/**
 * Classe d'exception pour gerer les tuile qui ne sont pas adjacentes aux cases mers.
 */
public class TileIsNotNearSeaException extends Exception{

    /**
     * constructeur de la classe
     * @param msg message ennvoyé
     */
    public TileIsNotNearSeaException(String msg){
        super(msg);
    }
}
