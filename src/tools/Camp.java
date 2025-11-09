package tools;
import java.util.*;

/**
 * Classe permettant de représenter un Camp (un sous type d'armé)
 */
public class Camp extends Army{
    /**
     * constructeur Camp
     * @param dimension dimension du camp
     */
    public Camp(int dimension){
        super(dimension);
    }

    @Override
    public int getRessources(){
        return super.getRessources() * 2;
    }

    /**
     * Methode permettant d'obtenir le coût en ressources de la création d'un camp sous forme d'une Map associant chaque ressoource à son coût (son nombre)
     * @return Une map qui contient les ressources et leurs coût pour chaque camp
     */
    public HashMap<String,Integer> getCostRessources(){
        HashMap<String,Integer> CostRessources = new HashMap<>();
        CostRessources.put("wood",2);
        CostRessources.put("ore",3);
        return CostRessources;
    } 

    /**
     * Renvoie une chaine correspondant au coût d'un camp
     */
    public String toString(){
        return "Camp: 2 🪵, 3 💎";
    }   
}