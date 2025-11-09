package ares;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

import game.AresGame;
import tools.*;

public class AresMain{
    public static void displayLegend(String action) {
        if(action.equals("action")){
            System.out.println("------ Actions possibles ------\n");
            System.out.println("createArmy : Créer une armée 🎖️");
            System.out.println("replaceArmy : Remplacer une armée 🏕️");
            System.out.println("buySecretWeapon : Acheter une arme secrète");
            System.out.println("addGuerriers : Ajouter des guerriers");
            System.out.println("putGuerriers : Placer des guerriers dans une armée");
            System.out.println("attack : Attaquer une armée ennemie");
            System.out.println("createPort : Créer un port 🛥️");
            System.out.println("changeResource : Échanger des ressources");
            System.out.println("nothing : ne rien faire");
        }
        else{
            System.out.println("Légende du plateau :");
            System.out.println("---------------------");
            System.out.println("🌲  : Forêt");
            System.out.println("⛰️  : Montagne");
            System.out.println("🍀  : Paturages");
            System.out.println("🌻  : Champ");
            System.out.println("🌊  : Mer");
            System.out.println("------ Batiments ------");
            System.out.println("🏕️  : Camp");
            System.out.println("🎖️  : Armée");
            System.out.println("🛥️  : Port");
            System.out.println("---------------------");
        }
    }
    public static void main(String[] args) throws IOException{
        System.out.println("------- Bienvenue dans Ares -------");
        System.out.println("------- Plateau de jeu -------");
        System.out.println("Entrez un nombre de lignes: ");
        int rows = Input.readInt();
        System.out.println("Entrez un nombre de colones: ");
        int cols = Input.readInt();
        if (cols < 4 || rows < 4)
            throw new IOException();
    
        Board b = new Board(rows, cols);
        displayLegend("null");
    
        b.fillBoard();
        b.fillIslands();
        while (b.getNumberOfIsland() < 2) {
            b = new Board(rows, cols);
            b.fillBoard();
            b.fillIslands();
        }
        b.display();
        System.out.println(b.getIslands());
        System.out.println("\n");

        
        System.out.println("------- Choix des joueurs -------");
        ArrayList<Player> players = new ArrayList<>();

        System.out.println("Joueur 1 : ");
        Player player1 = new Player(Input.readString());
        System.out.println("Joueur 2 : ");
        Player player2 = new Player(Input.readString());
        players.add(player1);
        players.add(player2);

        
        AresGame game = new AresGame(players, b);
        // Add resources to player1
        player1.addRessource(Type.FOREST, 10);
        player1.addRessource(Type.MOUNTAIN, 10);
        player1.addRessource(Type.PASTURE, 10);
        player1.addRessource(Type.FIELD, 10);
        player1.displayRessources();
    
        // Add resources to player2
        player2.addRessource(Type.FOREST, 10);
        player2.addRessource(Type.MOUNTAIN, 10);
        player2.addRessource(Type.PASTURE, 10);
        player2.addRessource(Type.FIELD, 10);
        player2.displayRessources();
        System.out.println("\n");
        boolean objectif = player1.getWarriorsStock() == 50 || player2.getWarriorsStock() == 50;
        Player currentPlayer = player1;
        Player otherPlayer = player2;
        
        System.out.println("Pour le début de la partie, vous posez 2 armées gratuitement:");
        //Ajout des Armées debut de partie
        for(int i=0; i<4; i++){
            System.out.println(currentPlayer.getName() +" construit gratuitement 1 armée\n");
            System.out.println("Donnez la position");
            Position pos = new Position(Input.readInt() , Input.readInt());
            game.getBoard().getTile(pos).setBuilding(new Army(1));
            game.getBoard().getTile(pos).setPlayer(currentPlayer);
            currentPlayer = currentPlayer == player1 ? player2:player1;

        }

        b.display();


        while(!objectif){
            
            while(!game.getNext()){
                
                System.out.println(currentPlayer.getName() +" joue\n");
                displayLegend("action");

                //System.out.println("\nObjectif de " + currentPlayer.getName() + ": " + 
                //currentPlayer.getObjectif().getDescription());
                //game.displayObjectiveStatus(currentPlayer);

                String action = Input.readString();
                switch(action){
                    case "nothing":
                    game.doNothing(currentPlayer);
                    break;
                    case "createPort":
                    System.out.println("Donnez la position");
                    Position pos = new Position(Input.readInt(), Input.readInt());
                    game.createPort(currentPlayer, pos);
                    break;
                    case "createArmy":
                    System.out.println("Donnez la position");
                    pos = new Position(Input.readInt() , Input.readInt());
                    System.out.println("Donnez la dimension");
                    int dim = Input.readInt();
                    game.createArmy(currentPlayer, pos, dim);
                    break;
                    case "replaceArmy":
                    System.out.println("Donnez la position");
                    pos = new Position(Input.readInt() , Input.readInt());
                    System.out.println("Donnez la dimension");
                    dim = Input.readInt();
                    game.replaceArmy(currentPlayer, pos, dim);
                    break;
                    case "buySecretWeapon":
                    game.buySecretWeapon(currentPlayer);
                    break;
                    case "addGuerriers":
                    game.addGuerriers(currentPlayer);
                    break;
                    case "putGuerriers":
                    System.out.println("Donnez la position");
                    pos = new Position(Input.readInt() , Input.readInt());
                    System.out.println("Donnez le nombre de guerriers");
                    int nbGuerriers = Input.readInt();
                    game.putGuerriers(currentPlayer, pos, nbGuerriers);
                    break;
                    case "attack":
                    System.out.println("Donnez la position de l'armée attaquante");
                    Position pos1 = new Position(Input.readInt() , Input.readInt());
                    System.out.println("Donnez la position de l'armée attaquée");
                    Position pos2 = new Position(Input.readInt() , Input.readInt());
                    System.out.println("Utiliser une arme secrète ? (true/false)");
                    boolean swP1 = Input.readBoolean();
                    System.out.println("L'ennemi utilise une arme secrète ? (true/false)");
                    boolean swP2 = Input.readBoolean();
                    game.attack(currentPlayer, otherPlayer, pos1, pos2, swP1, swP2);
                    System.out.println("batiment joueur 1: "+ game.getBoard().getTile(pos1).getBuilding().getDimension());
                    System.out.println("batiment joueur 2: "+ game.getBoard().getTile(pos2).getBuilding().getDimension());
                    break;
                    case "changeResource":
                    System.out.println("Donnez la position");
                    pos = new Position(Input.readInt(), Input.readInt());
                    System.out.println("Ressource à obtenir : ");
                    String getRessource = Input.readString();
                    System.out.println("Ressource à donner : ");
                    String giveRessource = Input.readString();
                    game.changeResource(currentPlayer, getRessource, giveRessource);
                    break;
                    default:
                    System.out.println("Action non reconnue");
                    break;
                }
                if(!game.getNext()){
                    System.out.println("Entrez une action valide.");
                }
            }
            currentPlayer.displayRessources();
            currentPlayer = currentPlayer == player1 ? player2:player1;
            otherPlayer = otherPlayer == player1 ? player2:player1;
            game.setNext(false);
            b.display();
            
        }
        
        System.out.println("La victoire est attribuée à " + (player1.getWarriorsStock() > player2.getWarriorsStock() ? player1.getName() : player2.getName()));
    }
}