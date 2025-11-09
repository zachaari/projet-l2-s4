package tools;

import java.util.HashMap;

/**
* Classe Building représentant les batiments du jeu
*/
public abstract class Building{
    
    /**dimension du batiment */
    protected int dimension;
    
    /**ressource du batiment */
    protected int ressources;

    /**
    *Constructeur permettant de crée un batiment
    */
    public Building(){
        this.dimension = 0;
        this.ressources = 1;
    }
    /**
     * Méthode permettant de modifier la dimension d'un batiment
     * @param dimension valeur de la dimension
     */
    abstract public void setDim(int dimension);

    /**
    * Methode renvoyant le nombre de ressource renvoyé par une tuile ressources
    @return le nombre de ressource
    **/
    abstract public int getRessources();

    /**
     * Methode qui représente un batiment avec sont cout
     * @return une chaine représentant le batiment
     */
    abstract public String toString();
    
    /**
     *Rnvoie la dimension d'un batiment 
     * @return la dimension du batiment
     */
    abstract public int getDimension();

    /**
     * Methode abstrait permettant d'obtenir le coût en ressources de la création d'un batiment sous forme d'une Map associant chaque ressoource à son coût (son nombre)
     * @return Une map qui contient les ressources et leurs coût pour chaque batiment
     */
    abstract public HashMap<String,Integer> getCostRessources();
}
