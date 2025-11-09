package game;

import java.util.*;

import tools.*;
import tools.exception.*;

/**
 * Classe permettant de représenter les regles du jeu et les 
 * fonctionalité que le jeu offre.
 */

public class DemeterGame extends Game {

    //Attribut

    private int nbThieves;

    /**
     * Constructeur du jeu Demeter
     * @param players liste des joueurs
     * @param board plateau de jeu
     * @param nbThieves nombre de voleur
     */
    public DemeterGame(ArrayList<Player> players , Board board, int nbThieves){
        super(players, board);
        this.nbThieves = nbThieves;
    }


    /**
     * Action -> créer une ferme
     * @param player Le joueur
     * @param pos Position de la ferme
     */
    public void createFarm(Player player,Position pos ){  
        try{
            Map<String, Integer> ressources = player.getRessource();

            if(!this.board.validPosition(pos)){
                throw new WrongPositionException("Position invalide");
            }
            if(!this.playerOnIsland(player, pos) && !player.getHasPort()){
                throw new WrongTileException("Vous ne possèdez aucune tuile sur cette ile");
            }
            if(this.board.getTile(pos).getPlayer()!=null){
                throw new AlreadyBuildingPlayerException("Un autre joueur possede la tuile");
            }
            if (this.board.getTile(pos).getType() == Type.SEA){
                throw new WrongTileException("Vous ne pouvez pas construire de ferme  sur la mer");
            } 
            if (ressources.get("wood") >= 1 && ressources.get("ore") >= 1){  
                this.board.getTile(pos).setBuilding(new Farm());
                this.board.getTile(pos).setPlayer(player);
                player.addRessource(Type.FOREST, -1);
                player.addRessource(Type.MOUNTAIN, -1);
                player.addRessourceBuildings(this.board.getTile(pos).getType(), -1);
                this.next = true;
            }
            else{
                throw new NotEnoughRessourcesException("Vous n'avez pas assez de ressouces pour créer une ferme");
            }
        }
        catch(NotEnoughRessourcesException | WrongPositionException |AlreadyBuildingPlayerException | WrongTileException   e){
            System.out.println(e.getMessage());
        }
    }     
    


    /**
     * Action -> remplacer une ferme par une exploitation
     * @param player Le joueur
     * @param pos La position de la ferme à remplacer
     */
    public void replaceFarm(Player player,Position pos){
        try{
            if((player.getRessource().get("wood") >= 2 && player.getRessource().get("wheat") >= 1 && player.getRessource().get("sheep") >= 1)){
                if (this.board.getTile(pos).getBuilding() instanceof Farm){ 
                    if(playerOnTile(player, pos)){
                        this.board.getTile(pos).setBuilding(new Exploitation());
                        player.addRessource(Type.FOREST, -2);
                        player.addRessource(Type.PASTURE, -1);
                        player.addRessource(Type.FIELD, -1);
                        this.next = true;
                    }
                    else{
                        throw new WrongTileException("Vous ne possèdez pas la tuile");
                    }
                } 
                else {
                    throw new WrongBuildingException("Seules les fermes peuvent être transformées en exploitations.");
                } 
            }
            else{
                throw new NotEnoughRessourcesException("Ressources insuffisantes");
            }
        }
        catch(WrongTileException | WrongPositionException | NotEnoughRessourcesException | WrongBuildingException e){
            System.out.println(e.getMessage());
        }
    } 

    /**
     * permet d'acheter un voleur
     * @param player joueur 
     */
    public void buySecretWeapon(Player player){
        try{
            if(this.nbThieves >0){
                if(!player.haveSecretWeapon()){
                    if(player.getRessource().get("ore")>=1 && player.getRessource().get("wood")>=1 && player.getRessource().get("wheat")>=1){
                        player.setSecretWeapon();
                        player.addRessource(Type.MOUNTAIN, -1);
                        player.addRessource(Type.FOREST, -1);
                        player.addRessource(Type.PASTURE, -1);
                        this.nbThieves --;
                        this.next = true;
                        System.out.println("Le joueur " + player.getName() + " achete un voleur");
                    }
                    else{
                        throw new NotEnoughRessourcesException("Ressources insuffisantes");
                    }
                }
                else{
                    throw new AlreadyHaveSecretWeaponException("Vous possèdez déja une arme secrète");
                }
            }
            else{
                throw new NoMoreThievesException("Aucuns voleurs disponible");
            }
        }
        catch(NoMoreThievesException | AlreadyHaveSecretWeaponException | NotEnoughRessourcesException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permet d'échanger des ressources via un port.
     * @param player joueur enchangeant ses ressources
     * @param pos position du port
     * @param getRessource ressource souhaitant avoir
     * @param giveRessource ressources données
     */
    public void changeResourceWithPort(Player player, Position pos, String getRessource, String giveRessource){
        try{
            int nbRessources = player.getRessource().get(giveRessource);
            if(nbRessources>=2){
                if(this.playerHasPort(player,pos)){ 
                    player.addRessource(Type.getType(getRessource), 1);
                    player.addRessource(Type.getType(giveRessource), -2);
                    this.next = true;
                }
                else{
                    throw new WrongBuildingException("Vous devez posséder un port sur une autre île avant de construire ici.");
                }  
            }
            else{  
                throw new NotEnoughRessourcesException("Nombre de ressource insuffisante");
            } 
        }
        catch(WrongRessourceException | WrongPositionException | NotEnoughRessourcesException| WrongBuildingException e){
            System.out.println(e.getMessage());
        }

    }
    


    /**
     * Calcule le nombre de points pour un joueur selon les règles de Demeter:
     * - 1 point pour chaque ferme
     * - 2 points pour chaque exploitation
     * - 1 point bonus si présent sur 2 îles
     * - 2 points bonus si présent sur plus de 2 îles
     *
     * @param player Le joueur pour qui calculer les points
     * @return Le nombre de points du joueur
     */
    public int calculatePoints(Player player) {
        int points = 0;
        int farms = 0;
        int exploitations = 0;
        
        // Compter les fermes et exploitations
        for (int i = 0; i < this.board.getRows(); i++) {
            for (int j = 0; j < this.board.getCols(); j++) {
                Position pos = new Position(i, j);
                Tile tile = this.board.getTile(pos);
                
                if (tile.getPlayer() == player) {
                    if (tile.getBuilding() instanceof Exploitation) {
                        exploitations++;
                    } else if (tile.getBuilding() instanceof Farm && !(tile.getBuilding() instanceof Exploitation)) {
                        farms++;
                    }
                }
            }
        }
        
        // Ajouter les points pour les bâtiments
        points += farms * 1;
        points += exploitations * 2;
        
        // Ajouter les bonus pour les îles occupées
        int nbIslands = this.board.getNbPlayerIslands(player);
        if (nbIslands == 2) {
            points += 1;
        } else if (nbIslands > 2) {
            points += 2;
        }
        
        return points;
    }


    /**
     * Permet de jouer le voleur en volant tout le stock des ressources des autres joueurs
     * @param player joueur
     * @param ressource ressource 
     */
    public void playThief(Player player, String ressource){
     try{
        if(player.haveSecretWeapon()){
            int nbRessources = 0;
            for (Player players : this.players) {
                if(players != player){
                    nbRessources += players.getRessource().get(ressource);
                    players.addRessource(Type.getType(ressource), -players.getRessource().get(ressource));
                }
            }
            player.addRessource(Type.getType(ressource), nbRessources);
            player.setSecretWeapon();
            this.nbThieves ++;
            this.next = true;
        }
        else{
            throw new NotEnoughRessourcesException("Vous n'avez pas de voleur.");
        }
     }   
     catch(WrongRessourceException | NotEnoughRessourcesException e){
        System.out.println(e.getMessage());
     }
    }

    
    /**
     * Obtient le nombre de voleurs dans le jeu.
     * @return le nombre actuel de voleurs
     */
    public int getNbThieves() {
        return this.nbThieves;
    }


    /**
     * Renvoie true si le joueur possède la tuile.
     * @param player joueur   
     * @param pos position de la tuile
     * @throws WrongPositionException en cas de mauvaise position
     * @return true si le joueur possède cette tuile, false sinon.
     */
    public boolean playerOnTile(Player player, Position pos) throws WrongPositionException {
        if(!this.board.validPosition(pos)){
            throw new WrongPositionException("Position invalide");
        }
        boolean res = false;
        if (this.board.getTile(pos).getPlayer() == player){
            res = true;
        } 
        return res;
    }

}