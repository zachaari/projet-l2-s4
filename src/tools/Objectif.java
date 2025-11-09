package tools;
import java.util.Random;

/**
 * Classe qui permet de représenter un objectif et de le génerer aléatoirement
 */
public class Objectif {
    private String description;
    private String type;
    private static final Random random = new Random();
    
    // Constantes pour les objectifs
    /**Permet d'avoir un nombre de tuile aléatoirement en fonction du plateau*/
    private static final double POURCENTAGE_TUILES = 0.2;

    /**Permet d'avoir un nombre de guerriers aléatoirement en fonction du plateau*/
    private static final double POURCENTAGE_GUERRIERS = 0.35;
    
    // Types d'objectifs
    /**représente l'objectif de conquerir des tuile */
    public static final String TYPE_CONQUER_TILES = "CONQUER_TILES";

    /**repésente l'objectif d'envahir une îles */
    public static final String TYPE_INVADE_ISLAND = "INVADE_ISLAND";

    /**represente l'objectif d'obtenir un nombre de guerriers*/
    public static final String TYPE_WARRIOR_COUNT = "WARRIOR_COUNT";

    private final int requiredTiles;
    private final int requiredWarriors;

    /**
     * Liste des descriptions des objectifs disponibles
     */
    public static final String[] OBJECTIVES_DESCRIPTIONS = {
        "Conquérir un territoire de taille stratégique",
        "Envahir complètement une île entière",
        "Atteindre une force militaire supérieure"
    };

    /**
     * Crée un objectif en le sélectionnant avec une animation de roulette
     * @param board Plateau de jeu
     * @param display Interface d'affichage
     * @param playerName nom du joueur
     * @return Un nouvel objectif sélectionné par la roulette
     */
    public static Objectif createWithRoulette(Board board, Display display, String playerName) {
        int[] requirements = calculateRequirements(board);
        int requiredTiles = requirements[0];
        int requiredWarriors = requirements[1];
        
        int selectedIndex = display.displayObjectiveRoulette(OBJECTIVES_DESCRIPTIONS,playerName);
        
        String type;
        String description;
        
        switch (selectedIndex) {
            case 0:
                type = TYPE_CONQUER_TILES;
                description = "Conquérir " + requiredTiles + " tuiles";
                break;
            case 1:
                type = TYPE_INVADE_ISLAND;
                description = "Envahir complètement une île";
                break;
            case 2:
                type = TYPE_WARRIOR_COUNT;
                description = "Atteindre " + requiredWarriors + " guerriers";
                break;
            default:
                type = TYPE_CONQUER_TILES;
                description = "Conquérir " + requiredTiles + " tuiles";
        }
        
        return new Objectif(type, description, requiredTiles, requiredWarriors);
    }

    /**
     * Calcule les exigences pour les objectifs en fonction de la taille du plateau
     * @param board Le plateau de jeu
     * @return Un tableau avec [requiredTiles, requiredWarriors]
     */
    private static int[] calculateRequirements(Board board) {
        int totalTuiles = board.getRows() * board.getCols();
        int requiredTiles = (int)(totalTuiles * POURCENTAGE_TUILES);
        int requiredWarriors = (int)(totalTuiles * POURCENTAGE_GUERRIERS);
        
        // Assurer un minimum de 40 guerriers pour l'objectif
        requiredWarriors = Math.max(requiredWarriors, 80);
        
        return new int[] {requiredTiles, requiredWarriors};
    }

    /**
     * Constructeur pour créer un objectif aléatoire
     * @param board Plateau de jeu
     */
    public Objectif(Board board) {
        int[] requirements = calculateRequirements(board);
        this.requiredTiles = requirements[0];
        this.requiredWarriors = requirements[1];
        
        // Choix aléatoire du type d'objectif
        int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                this.type = TYPE_CONQUER_TILES;
                this.description = "Conquérir " + requiredTiles + " tuiles";
                break;
            case 1:
                this.type = TYPE_INVADE_ISLAND;
                this.description = "Envahir complètement une île";
                break;
            case 2:
                this.type = TYPE_WARRIOR_COUNT;
                this.description = "Atteindre " + requiredWarriors + " guerriers";
                break;
        }
    }
    
    /**
     * Constructeur privé pour créer un objectif avec des paramètres spécifiques
     * @param type type de l'objectif
     * @param requiredTiles nombre de tuile requis
     * @param requiredWarriors nombre de guerriers requis
     * @param description desciption de l'objectif
     */
    public Objectif(String type, String description, int requiredTiles, int requiredWarriors) {
        this.type = type;
        this.description = description;
        this.requiredTiles = requiredTiles;
        this.requiredWarriors = requiredWarriors;
    }

    /**
     * Retourne le type d'objectif
     * @return le type d'objectif
     */
    public String getType() { 
        return type; 
    }

    /**
     * Retourne le nombre de tuiles requises
     * @return nombre de tuiles à conquérir
     */
    public int getRequiredTiles() { 
        return requiredTiles; 
    }

    /**
     * Retourne le nombre de guerriers requis
     * @return nombre de guerriers à atteindre
     */
    public int getRequiredWarriors() { 
        return requiredWarriors; 
    }

    /**
     * Retourne la description de l'objectif
     * @return description textuelle de l'objectif
     */
    public String getDescription() { 
        return description; 
    }

    /**
     * Retourne la description sous forme textuelle
     * @return description formatée de l'objectif
     */
    @Override
    public String toString() {
        return "Objectif: " + description;
    }
}