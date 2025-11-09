package tools;
import java.util.*;

/**
 * Classe permettant de représenter un Port
 */
public class Port extends Building {
    private boolean isBuilt;

    /**
     * Constructeur classe Port
     */
    public Port() {
        this.dimension = 0;
        this.isBuilt = true;
    }


    /**
     * Renvoie true pour indiquer qu'un port est construit.
     * @return l'attribut isBuilt
     */
    public boolean isBuilt() {
        return this.isBuilt;
    }
    

    /**
     * Permet de definir la dimension du port.
     */
    public void setDim(int dimension){
        this.dimension+= dimension;
    }

    /**
     * Methode permettant d'obtenir le coût en ressources de la création d'un port sous forme d'une Map associant chaque ressoource à son coût (son nombre)
     * @return Une map qui contient les ressources et leurs coût pour chaque port
     */
    public HashMap<String,Integer> getCostRessources(){
        HashMap<String,Integer> CostRessources = new HashMap<>();
        CostRessources.put("wood",1);
        CostRessources.put("sheep",2);
        return CostRessources;
    } 

    /**
     * Renvoie une chaine correspondant au cout d'un port
     */
    public String toString(){
        return "Port: 1 🪵, 2 🐑";
    }


    /**
     * Renvoie les ressources.
     * @return la ressource du port
     */
    public int getRessources(){
        return super.ressources;
    }

    /**
     * Renvoie la dimension du port.
     * @return la dimension du port
     */
    public int getDimension() {
        return this.dimension;
    }

}
