package tools.exception;
/**
 * Classe d'exception pour gérer les erreurs par rapport aux armée de dimension > 5.
 */
public class ArmyDimensionException extends Exception{

    /**
     * constructeur de la classe
     * @param msg message envoyé
     */
    public ArmyDimensionException(String msg){
        super(msg);
    }
}
