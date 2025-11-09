package tools.exception;

/**
 * Classe d'exception qui gère si un joueur possède ou non une arme secrète.
 */
public class AlreadyHaveSecretWeaponException extends Exception{

    /**
     * constructeur de la classe
     * @param msg message envoyé
     */
    public AlreadyHaveSecretWeaponException(String msg){
        super(msg);
    }
}
