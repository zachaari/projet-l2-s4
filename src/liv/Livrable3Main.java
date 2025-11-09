package liv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.*;
import tools.*;
public class Livrable3Main {

    public static void displayLegend(String legend) {
        System.out.println("Légende du plateau :");
        System.out.println("---------------------");
        System.out.println("🌲  : Forêt");
        System.out.println("⛰️   : Montagne");
        System.out.println("🍀  : Paturages");
        System.out.println("🌻  : Champ");
        System.out.println("🌊  : Mer");
        System.out.println("------ Batiments ------");
        if(legend.equals("ares")){
            System.out.println("🏕️  : Camp");
            System.out.println("🎖️  : Armée");
        }else{
            System.out.println("⛏️  : Exploitation");
            System.out.println("🚜 : Ferme");
        }
        System.out.println("🛥️  : Port");
        System.out.println("---------------------");
    }

    public static void mainDemeter() throws IOException {
            System.out.println("------- Bienvenue dans Demeter -------");
            System.out.println("------- Plateau de jeu -------");
            System.out.println("Entrez un nombre de lignes: ");
            int rows = Input.readInt();
            System.out.println("Entrez un nombre de colones: ");
            int cols = Input.readInt();
            if (cols < 4 || rows < 4)
                throw new IOException();
    
            Board b = new Board(rows, cols);
            displayLegend("demeter");
    
            b.fillBoard();
            b.fillIslands();
            while (b.getNumberOfIsland() < 2) {
                b = new Board(rows, cols);
                b.fillBoard();
                b.fillIslands();
            }
            b.display();
    
            ArrayList<Player> players = new ArrayList<>();
            Player player1 = new Player("Timo");
            Player player2 = new Player("Léon");
            players.add(player1);
            players.add(player2);
            
            DemeterGame game = new DemeterGame(players, b, 1);
            // Add resources to player1
            player1.addRessource(Type.FOREST, 10);
            player1.addRessource(Type.MOUNTAIN, 10);
            player1.addRessource(Type.PASTURE, 10);
            player1.addRessource(Type.FIELD, 10);
    
            // Add resources to player2
            player2.addRessource(Type.FOREST, 10);
            player2.addRessource(Type.MOUNTAIN, 10);
            player2.addRessource(Type.PASTURE, 10);
            player2.addRessource(Type.FIELD, 10);
    
            List<Position> pos1 = b.getIslands().get("ile1");
            for (Position pos : pos1) {
                Tile tile = b.getTile(pos);
                tile.setPlayer(player1);
            }
    
            List<Position> pos2 = b.getIslands().get("ile2");
            for (Position pos : pos2) {
                Tile tile = b.getTile(pos);
                tile.setPlayer(player2);
            }
    
            System.out.println("******* Action Timo *******");
            // Actions pour player1
            Position pos1Action = pos1.get(0);
            game.createFarm(player1, pos1Action);
            System.out.println("Le joueur " + player1.getName() + " a créé une ferme à la position " + pos1Action);
    
            game.replaceFarm(player1, pos1Action);
            System.out.println("Le joueur " + player1.getName() + " a remplacé une ferme par une exploitation à la position " + pos1Action);
    
            game.buySecretWeapon(player1);
            System.out.println("Le joueur " + player1.getName() + " a un voleur: " + player1.haveSecretWeapon());
    
            game.changeResourceWithPort(player1, pos1Action, "wood", "wheat");
            System.out.println("Le joueur " + player1.getName() + " a échangé des ressources avec un port");
    
            game.playThief(player1, "wood");
            System.out.println("Le joueur " + player1.getName() + " a joué le voleur et volé des ressources");
            
            b.display();
    
            System.out.println("******* Action Léon *******");
            // Actions pour player2
            Position pos2Action = pos2.get(0);
            game.createFarm(player2, pos2Action);
            System.out.println("Le joueur " + player2.getName() + " a créé une ferme à la position " + pos2Action);
    
            game.replaceFarm(player2, pos2Action);
            System.out.println("Le joueur " + player2.getName() + " a remplacé une ferme par une exploitation à la position " + pos2Action);
    
            game.buySecretWeapon(player2);
            System.out.println("Le joueur " + player2.getName() + " a un voleur: " + player2.haveSecretWeapon());
    
            game.changeResourceWithPort(player2, pos2Action, "wood", "wheat");
            System.out.println("Le joueur " + player2.getName() + " a échangé des ressources avec un port");
    
            game.playThief(player2, "wood");
            System.out.println("Le joueur " + player2.getName() + " a joué le voleur et volé des ressources");
    
            b.display();
            player1.displayRessources();
            player2.displayRessources();
    
            System.out.println("\n------- Iles du jeu -------");
            System.out.println("Nombre d'île: " + b.getNumberOfIsland());
        }
    public static void mainAres() throws IOException {
        System.out.println("------- Bienvenue dans Ares -------");
        System.out.println("------- Plateau de jeu -------");
        System.out.println("Entrez un nombre de lignes: ");
        int rows = Input.readInt();
        System.out.println("Entrez un nombre de colones: ");
        int cols = Input.readInt();
        if (cols < 4 || rows < 4)
            throw new IOException();
    
        Board b = new Board(rows, cols);
        displayLegend("ares");
    
        b.fillBoard();
        b.fillIslands();
        while (b.getNumberOfIsland() < 2) {
            b = new Board(rows, cols);
            b.fillBoard();
            b.fillIslands();
        }
        b.display();
    
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("Timo");
        Player player2 = new Player("Léon");
        players.add(player1);
        players.add(player2);
        
        AresGame game = new AresGame(players, b);
        // Add resources to player1
        player1.addRessource(Type.FOREST, 10);
        player1.addRessource(Type.MOUNTAIN, 10);
        player1.addRessource(Type.PASTURE, 10);
        player1.addRessource(Type.FIELD, 10);
    
        // Add resources to player2
        player2.addRessource(Type.FOREST, 10);
        player2.addRessource(Type.MOUNTAIN, 10);
        player2.addRessource(Type.PASTURE, 10);
        player2.addRessource(Type.FIELD, 10);
    
        List<Position> pos1 = b.getIslands().get("ile1");
        for (Position pos : pos1) {
            Tile tile = b.getTile(pos);
            tile.setPlayer(player1);
        }
    
        List<Position> pos2 = b.getIslands().get("ile2");
        for (Position pos : pos2) {
            Tile tile = b.getTile(pos);
            tile.setPlayer(player2);
        }
    
        System.out.println("******* Action Timo *******");
        // Actions pour player1
        Position pos1Action = pos1.get(0);
        game.createArmy(player1, pos1Action, 3);
        System.out.println("Le joueur " + player1.getName() + " a créé une armée à la position " + pos1Action);
    
        game.replaceArmy(player1, pos1Action, 5);
        System.out.println("Le joueur " + player1.getName() + " a remplacé une armée par un camp à la position " + pos1Action);
    
        game.buySecretWeapon(player1);
        System.out.println("Le joueur " + player1.getName() + " a une arme secrète: " + player1.haveSecretWeapon());
    
        game.addGuerriers(player1);
        System.out.println("Le joueur " + player1.getName() + " a ajouté des guerriers à son stock");
    
        game.putGuerriers(player1, pos1Action, 5);
        System.out.println("Le joueur " + player1.getName() + " a positionné des guerriers sur une armée à la position " + pos1Action);
    
        b.display();
    
        System.out.println("******* Action Léon *******");
        // Actions pour player2
        Position pos2Action = pos2.get(0);
        game.createArmy(player2, pos2Action, 3);
        System.out.println("Le joueur " + player2.getName() + " a créé une armée à la position " + pos2Action);
    
        game.replaceArmy(player2, pos2Action, 5);
        System.out.println("Le joueur " + player2.getName() + " a remplacé une armée par un camp à la position " + pos2Action);
    
        game.buySecretWeapon(player2);
        System.out.println("Le joueur " + player2.getName() + " a une arme secrète: " + player2.haveSecretWeapon());
    
        game.addGuerriers(player2);
        System.out.println("Le joueur " + player2.getName() + " a ajouté des guerriers à son stock");
    
        game.putGuerriers(player2, pos2Action, 5);
        System.out.println("Le joueur " + player2.getName() + " a positionné des guerriers sur une armée à la position " + pos2Action);
    
        b.display();
        player1.displayRessources();
        player2.displayRessources();
    
        System.out.println("\n------- Iles du jeu -------");
        System.out.println("Nombre d'île: " + b.getNumberOfIsland());
    }
    
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java Livrable3Main <game>");
            System.out.println("Available games: ares, demeter");
            return;
        }

        String game = args[0];
        switch (game) {
            case "ares":
                mainAres();
                break;
            case "demeter":
                mainDemeter();
                break;
            default:
                System.out.println("Jeu inconnu: " + game);
                System.out.println("Available games: ares, demeter");
                break;
        }
    }
}
