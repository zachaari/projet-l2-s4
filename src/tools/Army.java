package tools;

import java.util.*;

/**
 * Classe représentant une armé , hérite de Building
 */
public class Army extends Building{

    /**
     * dimension du batiment
     */
    protected int dimension;
    
    /**
     * dimension maximum du batiment
     */
    protected static final int MAXG = 5;

    /**
     * Constructeur pour initialiser une armée avec une dimension donnée de 1 à 5
     * @param dimension dimension de l'armée
     */
    public Army(int dimension){
        super();
        this.dimension = dimension;
    } 


    /**
     * Setter pour modifier la dimension d'une armée
     * @param dimension de l'armée
     */ 
    public void setDim(int dimension){
        if(this.dimension<MAXG){
            this.dimension = dimension;
        }
    }

    /**
     * getter pour accèder à la dimension
     * @return la dimension
     */
    public int getDimension(){
        return this.dimension;
    }

    /**
    * Methode renvoyant le nombre de ressource renvoyé par une tuile 
    * @return le nombre de ressource
    **/
    public int getRessources(){
        return super.ressources;
    } 

    /**
     * Methode permettant d'obtenir le coût en ressources de la création d'une armée sous forme d'une Map associant chaque ressoource à son coût (son nombre)
     * @return Une map qui contient les ressources et leurs coût pour chaque armée
     */
    public HashMap<String,Integer> getCostRessources(){
        HashMap<String,Integer> CostRessources = new HashMap<>();
        CostRessources.put("wood",1);
        CostRessources.put("Wheat",1);
        CostRessources.put("sheep",1);
        return CostRessources;
    } 

    /**
     * Renvoie une chaine de caractere avec le cout d'une armée 
     */
    public String toString(){
        return "Armée: 1 🪵, 1 🐑, 1 🌾";
    }
} 