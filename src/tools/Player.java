package tools;
import java.util.*;

/**
 * Classe permettant de représenter un joueur
 */
public class Player {

    private String name;
    private HashMap<String, Integer> ressources;
    private HashMap<String, Integer> buildings;
    private int StockWarriors;
    private Boolean hasPort;
    private boolean secretWeapon;
    private Objectif objectif;

    /**
     * Constructeur d' la classe joueur
     * @param name nom du joueur
     */
    public Player(String name){
        this.name = name;
        this.ressources = new HashMap<String , Integer>();
        this.buildings = new HashMap<String , Integer>();
        this.secretWeapon = false;
        this.hasPort = false;
        this.StockWarriors = 30;
        this.objectif = null;

        // Initialisation des ressources à la création d'un joueur
        for(Type type : Type.values()){
            this.ressources.put(type.name(), 0);
            this.buildings.put(type.name(), 0);
        }
    }

    /**
     * Ajoute/enleve l'arme secrete d'un joueur.
     */
    public void setSecretWeapon(){
        this.secretWeapon = !this.secretWeapon;
    }

    /**
     * Getteur du nom
     * @return le nom de joueur
     */
    public String getName(){
        return this.name;
    }


    /**
     * Ajoute nb au batiment de type type
     * @param type le type du batiment
     * @param nb le nombre a ajouter
     */
    public void addRessourceBuildings(Type type, int nb){
        this.buildings.replace(type.name(),this.buildings.get(type.name()) +nb);
    }


    /**
     * Retourne une Map associant chaque ressoource à sa valeur (son nombre) pour chaque joueur
     * @return Une map qui contient les ressources et valeurs de chaque joueur
     */
    public Map<String, Integer> getBuildings() {
        Map<String, Integer> Building = new HashMap<>();

        for (Map.Entry<String, Integer> entry : this.buildings.entrySet()) {
            if (!entry.getKey().equals("SEA")){  // verifie que la clé de la map que nous parcourons n'est pas égal à SEA
                String Name = Type.valueOf(entry.getKey()).getRessource(); // Récupere les ressources associé à chaque clé sauf SEA
                Building.put(Name, entry.getValue());
            }
        }
        return Building;
    }


    /**
     * Retourne une Map associant chaque ressoource à sa valeur (son nombre) pour chaque joueur
     * @return Une map qui contient les ressources et valeurs de chaque joueur
     */
    public Map<String, Integer> getRessource() {
        Map<String, Integer> Ressources = new HashMap<>();

        for (Map.Entry<String, Integer> entry : this.ressources.entrySet()) {
            if (!entry.getKey().equals("SEA")){  // verifie que la clé de la map que nous parcourons n'est pas égal à SEA
                String Name = Type.valueOf(entry.getKey()).getRessource(); // Récupere les ressources associé à chaque clé sauf SEA
                Ressources.put(Name, entry.getValue());
            }
        }
        return Ressources;
    }

    /**
     * Verifie si un joueur possède une arme secrête 
     * @return vrai si c'est le cas , faux sinon
     */
    public boolean haveSecretWeapon(){
        return this.secretWeapon;
    }

    /**
     * Retourne de nombre de guerriers que le joueur possède
     * @return int -> le nombre de guerriers
     */
    public int getWarriorsStock(){
        return this.StockWarriors;
    }

    /**
     * Retourne un objet de type Objectif
     * @return L'objectif du joueur
     */
    public Objectif getObjectif(){
        return this.objectif;
    }

    /**
     * Définit l'objectif du joueur
     * @param objectif L'objectif à atteindre
     */
    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    /**
     * Méthode permettant d'ajouter des ressources à un joueur
     * @param type le type de la ressource à ajouter
     * @param nbRessource le nombre de ressource à ajouter
     */
    public void addRessource(Type type , int nbRessource){
        this.ressources.replace(type.name(),this.ressources.get(type.name()) +nbRessource);
    }

    /**
     * Méthode permettant d'enlever des ressources à un joueur
     * @param type le type de la ressource à soustraire
     * @param nbRessource le nombre de ressource à soustraire
     */
    public void removeRessource(Type type , int nbRessource){
        this.ressources.replace(type.name(),this.ressources.get(type.name()) - nbRessource);
    }

    /**
     * Permet d'ajouter une arme secrète a un joueur
     */
    public void addSecretWeapon(){
        this.secretWeapon = true;
    }

    /**
     * Méthode permettant d'ajouter un nombre de guerriers
     * @param nbWarriors le nombre de guerriers à ajouter
     */
    public void addWarriors(int nbWarriors){
        this.StockWarriors+= nbWarriors;
    }

    /**
     * permet de comparer deux joueurs pour savoir s'ils sont egaux
     * @return true si les deux joueurs sont egaux false sinon
     */
    public boolean equals(Object o){
        if(!(o instanceof Player)){
            return false;
        }
        Player other = (Player) o;
        return this.name == other.getName() &&
               this.StockWarriors == other.getWarriorsStock();
    }

    /**
     * Affiche les ressources du joueur avec des émojis
     * bois 🪵, minerai 💎, blé 🌾, mouton 🐑
     */
    public void displayRessources() {
        Map<String, Integer> ressources = this.getRessource();
        StringBuilder sb = new StringBuilder();
        sb.append("Ressources de ").append(this.name).append(" :\n");
        for (Map.Entry<String, Integer> entry : ressources.entrySet()) {
            String emoji = "";
            switch (entry.getKey()) {
                case "wood":
                    emoji = "🪵";
                    break;
                case "ore":
                    emoji = "💎";
                    break;
                case "wheat":
                    emoji = "🌾";
                    break;
                case "sheep":
                    emoji = "🐑";
                    break;
            }
            sb.append(emoji).append(" : ").append(entry.getValue()).append("\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * return l'attribut hasPort
     * @return hasPort
     */
    public boolean getHasPort(){
        return this.hasPort;
    }

    /**
     * permet de modifier la valeur de l'attribut hasPort
     * @param value nouvelle valeur de l'attribut hasPort
     */
    public void setPort(boolean value){
        this.hasPort = value;
    }
}