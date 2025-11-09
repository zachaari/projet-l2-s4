package tools;

import java.util.Random;

import tools.exception.NotEnoughRessourcesException;

/**
 * Classe permettant de créer un dé , celui ci nous sera utile pour les choix aléatoires
 */
public class Dice {

    private static Random random =  new Random();

    /**
     * Constructeur de Dice
     */
    public Dice(){
    }

    /**
     * Renvoie le nombre de lancer en fonction de la dimension de l'arméee/camp du joueur ou si le joueur à une arme secrete.
     * @param dimension dimension du batiment
     * @param secretWeapon valeur de l'arme secrete
     * @return le nombre de lancer
     * @throws NotEnoughRessourcesException si le joueur ne possède pas de guerriers
     */
    public int nbLancer(int dimension, boolean secretWeapon) throws NotEnoughRessourcesException{
        int res = 0;
        if(dimension<1){
            throw new NotEnoughRessourcesException("Vous n'avez pas assez de guerriers.");
        }
        if(1<=dimension && dimension<=3){
            res += 1;
        }
        if(4<=dimension && dimension<=7){
            res += 2;
        }
        if(7<dimension){
            res += 3;
        }
        if(secretWeapon){
            res += 1;
        }
        return res;
    }


    /**
     * Renvoie le resultat du lancer du dé.
     * @param nbfois nombre de lancer
     * @return resultat des lancers
     */        
    public int lancer(int nbfois){
        int total = 0;
        for (int i = 0; i < nbfois; i++) {
            total += random.nextInt(6)+1;
        }
        return total;
    }
  
}