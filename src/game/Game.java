package game;
import java.util.*;

import tools.*;
import tools.exception.*;


/**
 * Classe abstraite game 
 */
public abstract class Game{
    
    // Attributs : 
    /**Dé du jeu */
    protected Dice dice;

    /**Plateau du jeu */
    protected Board board;

    /**Liste des joueurs */
    protected ArrayList<Player> players;

    /**Attribut next pour passer les tours */
    protected boolean next;

    /**
     * Constructeur du jeu
     * @param players Liste de joueur participant au jeu
     * @param board Plateau de jeu 
     */
    public Game(ArrayList<Player> players , Board board){
        this.players = new ArrayList<Player>();
        for(Player p : players){
            this.players.add(p);
        }
        this.board = board;
        this.next = false;
    }

    /**
     * Action -> ne rien faire
     * @param player Joueur
     */
    public void doNothing(Player player){
        System.out.println("Aucune action");
        this.next = true;
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
                    if (this.playerOnIsland(player,pos)) {
                        Port port = new Port();
                        this.board.getTile(pos).setBuilding(port);
                        this.board.getTile(pos).setPlayer(player);
                        player.addRessource(Type.FOREST, -1);
                        player.addRessource(Type.FIELD, -2);
                        this.next = true;
                        player.addRessourceBuildings(this.board.getTile(pos).getType(), 1);
                        player.setPort(true);
   
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
        catch(WrongTileException | NotEnoughRessourcesException | TileIsNotNearSeaException | WrongPositionException e){
            System.out.println(e.getMessage());
            this.createPortUsage();
        }
    }


    /**
     * Permet à un joueur d'échanger 3 ressources identiques de son choix contre une autre.
     * @param player joueur 
     * @param getRessource ressource reçu
     * @param giveRessource ressource donné
     */
    public void changeResource(Player player, String getRessource, String giveRessource){
        try{
            int nbRessources = player.getRessource().get(giveRessource);
            if(nbRessources>=3){
                player.addRessource(Type.getType(getRessource), 1);
                player.addRessource(Type.getType(giveRessource), -3);
            }
            throw new NotEnoughRessourcesException("Nombre de ressource insuffisante");
        }
        catch(WrongRessourceException | NotEnoughRessourcesException e){
            System.out.println(e.getMessage());
            this.changeResourceUsage();
        }
    }


    
    /**
     * Renvoie true si le joeur possède au moins une case sur une ile.
     * @param player joueur   
     * @param pos position de la tuile
     * @throws WrongPositionException en cas de mauvaise position
     * @return true si le joueur possède une case une ile, false sinon.
     */
    public boolean playerOnIsland(Player player, Position pos) throws WrongPositionException{
        if(!this.board.validPosition(pos)){
            throw new WrongPositionException("Position invalide");
        }
        boolean res = false;
        List<Position> ile = this.board.getIslandPos(pos);
        for(Position position : ile ){
            if(this.board.getTile(position).getPlayer() == player){
                res = true;
            }
        }
        return res;
    }

    /**
     * Renvoie true si le joueur possede un port sur une iles
     *@param player le joueur pour qui nous voulons verifier si il possede un port
     *@param pos la position de l'iles
     *@throws WrongPositionException en cas de mauvaise position
     *@return true si le joueur possede un port sinon false
     */
    public boolean playerHasPort(Player player, Position pos) throws WrongPositionException{
        if(!this.board.validPosition(pos)){
            throw new WrongPositionException("Position invalide");
        }
        boolean res = false;
        List<Position> ile = this.board.getIslandPos(pos);
        for (Position position : ile){
            if (this.board.getTile(position).getPlayer() == player && this.board.getTile(position).getBuilding() instanceof Port){
                res = true;
            } 
        } 
        return res;
    }

    /**
     * Renvoie le nombre de batiment d'un joueur sur une ile
     *@param player le joueur pour qui nous voulons verifier sont nombre de batiment sur l'ile
     *@param pos la position de l'iles
     *@throws WrongPositionException en cas de mauvaise position
     *@return le nombre de batiment present sur l'ile
     */
    public int playerBuildingOnIsland(Player player,Position pos) throws WrongPositionException{
        if(!this.board.validPosition(pos)){
            throw new WrongPositionException("Position invalide");
        }
        int count = 0;
        List<Position> ile = this.board.getIslandPos(pos);
        for (Position position : ile){
            if (this.board.getTile(position).getPlayer() == player && this.board.getTile(position).getBuilding() != null){
                    count += 1;
            }
        } 
        return count;
    } 


    /**
     * Ajoute les batiments au debut du jeu.
     * @param player joueur placant le batiment
     * @param pos position de la tuile
     * @param building batiment à placer
     * @return true si la tuile est posée, false sinon.
     */
    public boolean addFirstBuilding(Player player, Position pos, Building building){
            if(!this.board.validPosition(pos) || this.board.getTile(pos).getBuilding()!=null){
                return false;
            }
            this.board.getTile(pos).setBuilding(building);
            this.board.getTile(pos).setPlayer(player);
            player.addRessourceBuildings(this.board.getTile(pos).getType(), 1);
            return true;  
    }

    /**
     * Renvoie le plateau de jeu.
     * @return le plateau passé en paramètre
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Permet de modifier l'attribut next
     * @param status etat souhaité
     */
    public void setNext(Boolean status){
        this.next = status;
    }

    /**
     * Permet d'accèder à l'attribut next
     * @return l'attribut next
     */
    public boolean getNext(){
        return this.next;
    }
    

    /**
     * permet d'acheter une arme secrète
     * @param player joueur achetant une arme secrete
     */
    public abstract void buySecretWeapon(Player player);


    /**
     * Usage: createPort(Player player, Position pos)
     */
    public void createPortUsage(){
        System.out.println( "Usage: createPort(Player player, Position pos)\n" +
               "Description: Permet de créer un port sur une position donnée.\n" +
               "Conditions: Le joueur doit avoir au moins 1 bois et 2 moutons, et la tuile doit être adjacente à la mer.");
    }    


    /**
     * Usage: changeResource(Player player)
     */
    public void changeResourceUsage(){
        System.out.println("Usage: changeResource(Player player)\n" +
               "Description: Permet de changer les ressources d'un joueur.\n"+
               "Consditions: avoir au moins 3 ressouces identiques à échanger.");
    }


    /**
     * collecte les type des batiments pour les ajouter aux ressoures
     */
    public void collectRessources() {
        try{
            for(Player player : this.players){
                for(String type : player.getBuildings().keySet()){
                    player.addRessource(Type.getType(type), player.getBuildings().get(type));
                }
            }

        }
        catch(WrongRessourceException e){
            System.out.println(e.getMessage());
        }
    }
}