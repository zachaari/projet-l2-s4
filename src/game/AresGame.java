package game;

import java.util.*;

import tools.*;
import tools.exception.*;

/**
 * Classe permettant de représenter les regles du jeu et les 
 * fonctionalité que le jeu offre.
 */

public class AresGame extends Game{

    /**
     * Constructeur du jeu Ares
     * @param players Liste de joueur participant au jeu
     * @param board Plateau de jeu 
     */
    public AresGame(ArrayList<Player> players , Board board){
        super(players, board);
        this.dice = new Dice();
    }


    /**
     * methode permet de créer une armée sur une position donnée et une certaine dimension
     * @param player le joueur voulant créer une armée
     * @param pos la position sur laquel nous voulons créer notre armée 
     * @param dimension la dimension de notre armée
     */
    public void createArmy(Player player,Position pos , int dimension){
        try{
            if(dimension > 5){
                throw new ArmyDimensionException("Armée saturée");
            }
            if(!this.board.validPosition(pos)){
                throw new WrongPositionException("Position invalide");
            }
            if(this.board.getTile(pos).getType() == Type.SEA){
                throw new WrongPositionException("Position non valide");
            }
            if(this.board.getTile(pos).getPlayer() != null){
                throw new AlreadyBuildingPlayerException("Un autre joueur possède cette tuile");
            }
            if((!this.playerOnIsland(player, pos) && !player.getHasPort()) && this.board.getNbPlayerIslands(player)!=0){
                throw new WrongTileException("Vous ne possèdez aucune tuile sur cette ile");
            }
            Map<String, Integer> ressources = player.getRessource();
            if (ressources.get("wood") >= 1 && ressources.get("wheat") >= 1 && ressources.get("sheep") >= 1){  
                this.board.getTile(pos).setBuilding(new Army(dimension));
                this.board.getTile(pos).setPlayer(player);
                player.addRessource(Type.FOREST, -1);
                player.addRessource(Type.PASTURE, -1);
                player.addRessource(Type.FIELD, -1);
                player.addRessourceBuildings(this.board.getTile(pos).getType(), 1);
                this.next = true;
            }
            else{
                throw new NotEnoughRessourcesException("Vous n'avez pas assez de ressouces pour créer une armée");
            }
        }
        catch(NotEnoughRessourcesException | WrongPositionException | ArmyDimensionException | AlreadyBuildingPlayerException | WrongTileException e){
            System.out.println(e.getMessage());
            this.createArmyUsage();
        }
    }     
    
 

    /**
     * Méthode permettant de remplacer une Armée par un Camp
     * @param player Le joueur
     * @param dimension La dimension choisi pour le Camp
     * @param pos position de la tuile
     */
    public void replaceArmy(Player player, Position pos, int dimension){
        try{
            if((player.getRessource().get("wood") >= 2 && player.getRessource().get("ore") >= 3)
                || (this.board.getTile(pos).getBuilding().getDimension() > 5)){
                if(playerOnIsland(player, pos)){
                    this.board.getTile(pos).setBuilding(new Camp(dimension));
                    player.addRessource(Type.FOREST, -2);
                    player.addRessource(Type.MOUNTAIN, -3);
                    this.next = true;
                }
                else{
                    throw new WrongTileException("Vous ne possèdez pas la tuile");
                }
            }
            else{
                throw new NotEnoughRessourcesException("Ressources insuffisante");
            }
        }
        catch(WrongTileException | WrongPositionException | NotEnoughRessourcesException e){
            System.out.println(e.getMessage());
            this.replaceArmyUsage();
        }
    }

    /**
     * Méthode permettant de positionner des guerriers sur un camp ou une armée
     * @param player Joueur 
     * @param pos La position de la tuile qui contient l'armée ou le camp
     * @param nbGuerriers Le nombre de guerriers que l'on veut positionner
     */
    public void putGuerriers(Player player , Position pos ,int nbGuerriers){
        try{
            if(this.board.getTile(pos).getPlayer() != player){
                throw new WrongTileException("Vous ne possèdez pas cette tuile.");
            }
            if(!(this.board.getTile(pos).getBuilding() instanceof Army) && !(this.board.getTile(pos).getBuilding() instanceof Camp)){
                throw new WrongBuildingException("La tuile ne possède pas de batiments Camp ou Army");
            } 
            if(player.getWarriorsStock() < nbGuerriers){
                throw new NotEnoughRessourcesException("Stock de guerrier insuffisant");
            }
            int initWar = this.board.getTile(pos).getBuilding().getDimension();
            this.board.getTile(pos).getBuilding().setDim(nbGuerriers+initWar);
            this.next = true;

        }
        catch(WrongTileException | WrongBuildingException | NotEnoughRessourcesException e){
            System.out.println(e.getMessage());
            this.putGuerriersUsage();
        }
    }

    /**
     * Permet d'ajouter 5 guerriers à son stock
     * @param player le joueur
     */
    public void addGuerriers(Player player){
        try{
            if(player.getRessource().get("wheat")<2 || player.getRessource().get("sheep")<2 || player.getRessource().get("wood")<1){
                throw new NotEnoughRessourcesException("Nombre de ressource insuffisantes:  \"wheat\">2, \"sheep\">2, \"wood\">1.");
            }
            player.addRessource(Type.FIELD, -2);
            player.addRessource(Type.PASTURE, -2);
            player.addRessource(Type.FOREST, -1);
            player.addWarriors(5);   
            this.next = true;
            
            System.out.println("+5 guerriers pour " + player.getName());
        }
        catch(NotEnoughRessourcesException e){
            System.out.println(e.getMessage());
            this.addGuerriersUsage();
        }

    }
    
    /**
     * Gère le combat entre deux joueurs sur des positions spécifiques du plateau.
     * 
     * @param player1 Premier joueur participant au combat
     * @param player2 Deuxième joueur participant au combat
     * @param positionP1 Position des unités du premier joueur sur le plateau
     * @param positionP2 Position des unités du deuxième joueur sur le plateau
     * @param swP1 Booléen indiquant si le joueur 1 utilise une arme secrète
     * @param swP2 Booléen indiquant si le joueur 2 utilise une arme secrète
     * 
     * Le combat se déroule comme suit:
     * - Vérifie la validité des positions et la possession d'armes secrètes
     * - Lance les dés pour chaque armée en fonction de leur taille et de l'utilisation d'armes secrètes
     * - Applique les pertes en fonction du résultat:
     *   - Le perdant perd une unité
     *   - En cas d'égalité, les deux joueurs perdent une unité
     *   - Si une armée atteint 0 unité, elle est retirée du plateau
     */
    public void attack(Player player1, Player player2, Position positionP1, Position positionP2, boolean swP1, boolean swP2){
        try{
            if(!this.isValidTile(positionP1, positionP2) && !player1.getHasPort()){
                throw new WrongTileException("Les positions ne sont pas valides");
            }
            if(swP1 && !player1.haveSecretWeapon()){
                throw new NotEnoughRessourcesException("player1 vous ne possedez pas d'arme secrète");
            }
            if(swP2 && !player2.haveSecretWeapon()){
                throw new NotEnoughRessourcesException("player2 vous ne possedez pas d'arme secrète");
            }
            int nbGuerriersP1 = this.board.getTile(positionP1).getBuilding().getDimension();
            int nbGuerriersP2 = this.board.getTile(positionP2).getBuilding().getDimension(); 
            int player1NbDice = this.dice.nbLancer(nbGuerriersP1, swP1);
            int player2NbDice = this.dice.nbLancer(nbGuerriersP2, swP2);
            int nbPointP1 = this.dice.lancer(player1NbDice);
            int nbPointP2 = this.dice.lancer(player2NbDice);

            if(nbPointP1 < nbPointP2){
                if(nbGuerriersP1==1){
                    this.board.getTile(positionP1).removeBuilding();
                    this.board.getTile(positionP1).removePlayer();
                    player1.addRessourceBuildings(this.board.getTile(positionP1).getType(), -1);


                }
                else{
                    this.board.getTile(positionP1).getBuilding().setDim(nbGuerriersP1-1); 
                }
            }
            if(nbPointP1 > nbPointP2){
                if(nbGuerriersP2==1){
                    this.board.getTile(positionP2).removeBuilding();
                    this.board.getTile(positionP2).removePlayer();
                    player2.addRessourceBuildings(this.board.getTile(positionP2).getType(), -1);

                }
                else{
                    this.board.getTile(positionP2).getBuilding().setDim(nbGuerriersP2-1); 
                }
            }
            if(nbPointP1 == nbPointP2){
                if(nbGuerriersP1==1){
                    this.board.getTile(positionP1).removeBuilding();
                    this.board.getTile(positionP1).removePlayer();
                    player1.addRessourceBuildings(this.board.getTile(positionP1).getType(), -1);


                }
                else{
                    this.board.getTile(positionP1).getBuilding().setDim(nbGuerriersP1-1); 
                }
                if(nbGuerriersP2==1){
                    this.board.getTile(positionP2).removeBuilding();
                    this.board.getTile(positionP2).removePlayer();
                    player2.addRessourceBuildings(this.board.getTile(positionP2).getType(), -1);


                }
                else{
                    this.board.getTile(positionP2).getBuilding().setDim(nbGuerriersP2-1); 
                }
            }
            this.next = true;
            
        }
        catch(WrongTileException | WrongPositionException | NotEnoughRessourcesException e){
            System.out.println(e.getMessage());
            this.attackUsage();
        }
            
    }

    /**
    * Compte le nombre total de tuiles possédées par un joueur
    * @param player le joueur dont on veut compter les tuiles
    * @return nombre de tuiles possédées
    */
    public int countPlayerTiles(Player player) {
    int count = 0;

    for (int i = 0; i < board.getRows(); i++) {
        for (int j = 0; j < board.getCols(); j++) {
            Position pos = new Position(i, j);
            Tile tile = board.getTile(pos);
            
            if (tile.getType() != Type.SEA && tile.getPlayer() == player) {
                count++;
            }
        }
    }
    return count;
    }   
    

    /**
    * Vérifie si un joueur a atteint son objectif
    * @param player le joueur à vérifier
    * @return true si l'objectif est atteint
    */
    public boolean hasReachedObjective(Player player) {
        Objectif objectif = player.getObjectif();
    
        switch (objectif.getType()) {
            case "CONQUER_TILES":
                return countPlayerTiles(player) >= objectif.getRequiredTiles();

            case "INVADE_ISLAND":
                return hasConqueredIsland(player);
            
            case "WARRIOR_COUNT":
                return countTotalWarriors(player) >= objectif.getRequiredWarriors();
            
            default:
                return false;
        }
    }

    /**
    * Compte le nombre total de guerriers d'un joueur (stock + armées + camps)
    * @param player le joueur dont on veut compter le nombre de guerriers
    * @return nombre de guerriers possédées
    */
    public int countTotalWarriors(Player player) {
        int total = player.getWarriorsStock();
    
        // Parcourir le plateau pour compter les guerriers dans les armées/camps
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Position pos = new Position(i, j);
                Tile tile = board.getTile(pos);
                if (tile.getPlayer() == player && 
                    (tile.getBuilding() instanceof Army || tile.getBuilding() instanceof Camp)) {
                    total += tile.getBuilding().getDimension();
                }
            }
        }
        return total;
    }

    /**
     * Permet au joueur de placer un port à condition que celui ci soit adjacent à la mer.
     * @param player Le joueur
     * @param pos La position du port
     */
    public void createPort(Player player , Position pos){
        Map<String, Integer> ressources = player.getRessource();
        try{
            if(!this.board.validPosition(pos)){
                throw new WrongPositionException("Position invalide");
            }
            if(ressources.get("wood") >= 1 && ressources.get("sheep") >= 2){
                if (this.board.isNearSea(this.board.getTile(pos))){
                    if (this.playerOnIsland(player,pos) || player.getHasPort()) {
                        if (this.playerBuildingOnIsland(player,pos) >= 2){
                            this.board.getTile(pos).setBuilding(new Port());
                            this.board.getTile(pos).setPlayer(player);
                            player.addRessource(Type.FOREST, -1);
                            player.addRessource(Type.FIELD, -2);
                            player.setPort(true);
                            this.next = true;
                            player.addRessourceBuildings(this.board.getTile(pos).getType(), 1);

                        }
                        else{ throw new WrongBuildingException("Vous devez avoir au moins 2 bâtiments sur chaque île occupée.");
                        } 
                    } 
                    else{
                        throw new WrongTileException("Vous ne possèdez pas de tuile sur l'ile");
                    }
                }
                else{
                    throw new TileIsNotNearSeaException("La tuile n'est pas adjacentes à une case de type mer");
                }
            }
            else{
                throw new NotEnoughRessourcesException("Vous n'avez pas assez de ressources");
            }
        }
        catch(WrongTileException | NotEnoughRessourcesException | TileIsNotNearSeaException | WrongBuildingException | WrongPositionException e){
            System.out.println(e.getMessage());
            this.createPortUsage();
        }
    }

    /**
     * Permet au joueur d'acheter une arme secrête
     * @param player joueur
     */
    public void buySecretWeapon(Player player){
        try{
            if(player.haveSecretWeapon()){
                throw new AlreadyHaveSecretWeaponException("Vous avez deja une arme secrete");
            }
            if(player.getRessource().get("ore")<1 || player.getRessource().get("wood")<1){
                throw new NotEnoughRessourcesException("Nombre de ressources insuffisantes: \"ore\" >=1, \"wood\" >=1.");
            }
            player.setSecretWeapon();
            player.addRessource(Type.MOUNTAIN, -1);
            player.addRessource(Type.FOREST, -1);
            this.next = true;
            System.out.println("Le joueur " + player.getName() + " achete une arme secrete");
        }
        catch(AlreadyHaveSecretWeaponException | NotEnoughRessourcesException e){
            System.out.println(e.getMessage());
            this.buySecretWeaponUsage();
        }
    }




    /**
     * Renovoie true si les tuile sont sur la même ile et qu'elles ont des batiment de type camp ou army, false sinon.
     * @param pos1 position de la premiere tuile 
     * @param pos2 position de la 2e tuile
     * @throws WrongPositionException si la position n'est pas valide
     * @return un true si les conditions sont respectées, false sinon.
     */
    public boolean isValidTile(Position pos1, Position pos2) throws WrongPositionException{
        if(!this.board.validPosition(pos1) || !this.board.validPosition(pos2)){
            throw new WrongPositionException("Position invalide");
        }
        boolean res = true;
        List<Position> ile = this.board.getIslandPos(pos1);
        if(!ile.contains(pos2)){
            return false;
        }
        if(!(this.board.getTile(pos1).getBuilding() instanceof Army) && !(this.board.getTile(pos1).getBuilding() instanceof Camp)){
            return false;
        }
        if(!(this.board.getTile(pos2).getBuilding() instanceof Army) && !(this.board.getTile(pos2).getBuilding() instanceof Camp)){
            return false;
        }
        return res;
    }

    /**
    * Verifie si le joueur possede entierement au moins une ile
    * @param player joueur à vérifier
    * @return true si le joueur contrôle totalement au moins une île, false sinon
    */


    public boolean hasConqueredIsland(Player player) {
        HashMap<String, List<Position>> islands = this.board.getIslands();

        for (List<Position> islandPositions : islands.values()) {
            boolean fullControl = true;
            for (Position pos : islandPositions) {
                Tile tile = this.board.getTile(pos);

                if (tile.getType() != Type.SEA &&
                    (tile.getPlayer() == null || tile.getPlayer() != player)) {
                    fullControl = false;
                    break;
                }
            }
            if (fullControl) return true;
        }
        return false;
    }





    // Méthodes d'usage :
    /**
     * Usage: createArmy(Player player, Position pos, int dimension)
     */
    public void createArmyUsage(){
        System.out.println("Usage: createArmy(Player player, Position pos, int dimension)\n" +
               "Description: Permet de créer une armée sur une position donnée et une certaine dimension.\n" +
               "Conditions: Le joueur doit avoir au moins 1 bois, 1 blé et 1 mouton. Au moins un guerrier requis");
    }

    /**
     * Usage: replaceArmy(Player player)
     */
    public void replaceArmyUsage(){
        System.out.println("Usage: replaceArmy(Player player)\n" +
               "Description: Permet de remplacer une armée existante." +
               "Conditions: Le joueur doit avoir au moins 2 bois et 3 minerais");
    }
    /**
     * Usage: putGuerriers(Player player, Position pos, int nbGuerriers)
     */
    public void putGuerriersUsage(){
        System.out.println("Usage: putGuerriers(Player player, Position pos, int nbGuerriers)\n" +
               "Description: Permet de positionner des guerriers sur un camp ou une armée.\n" +
               "Conditions: Le joueur doit posséder la tuile et avoir suffisamment de guerriers en stock.");
    }
    /**
     * Usage: addGuerriers(Player player)
     */
    public void addGuerriersUsage(){
        System.out.println("Usage: addGuerriers(Player player)\n" +
               "Description: Permet d'ajouter 5 guerriers à son stock.\n" +
               "Conditions: Le joueur doit avoir au moins 2 blés, 2 moutons et 1 bois.");
    }
    /**
     * Usage: Attack(Player player)
     */
    public void attackUsage(){
        System.out.println("Usage: Attack(Player player)\n" +
               "Description: Permet d'attaquer un autre joueur.");

    }
    /**
     * Usage: buySecretWeapon(Player player)
     */
    public void buySecretWeaponUsage(){
        System.out.println("Usage: buySecretWeapon(Player player)\n" +
               "Description: Permet d'acheter une arme secrète.\n" +
               "Conditions: Le joueur doit avoir au moins 1 minerai et 1 bois, et ne doit pas déjà posséder une arme secrète.");
    }

    /**
     * Usage: checkObjective(Player player)
     */
    public void checkObjectiveUsage() {
        System.out.println("Usage: checkObjective(Player player)\n" +
            "Description: Vérifie si le joueur a atteint son objectif.\n" +
            "Objectifs possibles:\n" +
            "- Conquérir un certain nombre de tuiles\n" +
            "- Envahir complètement une île\n" +
            "- Atteindre un certain nombre de guerriers");
    }

    /**
     * Usage: displayObjective(Player player)
     * @param player joeule joueur
     */
    public void displayObjectiveStatus(Player player) {
        System.out.println("\n----- Status de l'objectif de " + player.getName() + " -----");
        switch (player.getObjectif().getType()) {
            case "CONQUER_TILES":
                System.out.println("Tuiles conquises: " + countPlayerTiles(player) + "/" + 
                                player.getObjectif().getRequiredTiles());
                break;
            case "INVADE_ISLAND":
                System.out.println("Îles conquises entièrement: " + 
                                (hasConqueredIsland(player) ? "Oui" : "Non"));
                break;
            case "WARRIOR_COUNT":
                System.out.println("Guerriers: " + countTotalWarriors(player) + "/" + 
                                player.getObjectif().getRequiredWarriors());
                break;
        }
        System.out.println("----------------------------------------\n");
    }

    

    



}