package tools;
import java.util.*;

import tools.exception.WrongRessourceException;

/**
 * Classe enum pour représenter le type des tuile 
 */

public enum Type{

    /**Type: Foret ressource: bois */
    FOREST("wood"),

    /**Type: Montagne ressource:  */
    MOUNTAIN("ore"), 

    /**Type: Pâturage ressource: blé */
    PASTURE("wheat"), 

    /**Type: Champ ressource: mouton */
    FIELD("sheep"), 

    /**Type: Mer ressource: aucune */
    SEA(null);
    
    private String ressource;
    private static Random ALEA = new Random();
        
    /**
     * Constructeur de la classe Type
     * @param ressource type de ressource
     */
    private Type(String ressource){
        this.ressource = ressource;
    }
    
    /**
     * Renvoie le type associé à une ressource
     * @param ressource ressource du type
     * @return Le type associé à une ressource
     * @throws WrongRessourceException en cas de ressource inconnue
     */
    public static Type getType(String ressource) throws WrongRessourceException{
        Type res;
        switch (ressource) {
            case "wood":
                res = Type.values()[0];
                break;
            case "ore":
                res = Type.values()[1];
                break;
            case "wheat":
                res = Type.values()[2];
                break;
            case "sheep":
                res = Type.values()[3];
                break;
            default:
                throw new WrongRessourceException("Erreur de ressources");
        }
        return res;
    }
    
    /**
     * revoie le type de ressource
     * @return renvoie la ressource associée
     */
    public String getRessource(){
        return this.ressource;
    }
    
    /**
     * fonction random qui renvoie un type aléatoirement
     * @return un type 
     */
    public static Type random(){
        return Type.values() [ ALEA.nextInt(Type.values().length-1) ];
    }
        
}
