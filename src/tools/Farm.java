package tools;
import java.util.*;

/**
 * Classe représentant une Ferme , un type de Batiment donc hérite de Building
 */
public class Farm extends Building {
    
    /**
     * Cnostructeur d'une ferme.
     */
    public Farm(){
        this.dimension = 1;
    }

    /**
     * renvoie le nombre de ressource que le batiment renvoie
     * @return le nombre de ressource
     */
    public int getRessources(){
        return super.ressources;
    }

    /**
     * Permet de définir la dimension.
     */
    public void setDim(int dimension){
        this.dimension+= dimension;
    }
    /**
     * renvoie la dimensionn du batiment
     * @return la dimension d'une ferme
     */
    public int getDimension(){
        return this.dimension;
    }

    /**
     * Methode permettant d'obtenir le coût en ressources de la création d'une ferme sous forme d'une Map associant chaque ressoource à son coût (son nombre)
     * @return Une map qui contient les ressources et leurs coût pour chaque ferme
     */
    public HashMap<String,Integer> getCostRessources(){
        HashMap<String,Integer> CostRessources = new HashMap<>();
        CostRessources.put("wood",1);
        CostRessources.put("ore",1);
        return CostRessources;
    } 

    /**
     * Renvoie une chaine correspondant au cout d'une ferme.
     */
    public String toString(){
        return "Ferme: 1 🪵, 1 💎";
    }

}
