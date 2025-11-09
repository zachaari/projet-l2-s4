package liv;
import java.io.IOException;

import tools.*;
import java.util.*;

public class Livrable2Main{

    public static void displayLegend() {
        System.out.println("Légende du plateau :");
        System.out.println("---------------------");
        System.out.println("🌲  : Forêt");
        System.out.println("⛰️   : Montagne");
        System.out.println("🍀  : Paturages");
        System.out.println("🌻  : Champ");
        System.out.println("🌊  : Mer");
        System.out.println("------ Batiments ------");
        System.out.println("🏕️  : Camp");
        System.out.println("🎖️  : Armée");
        System.out.println("⛏️  : Exploitation");
        System.out.println("🚜 : Ferme");
        System.out.println("🛥️  : Port");
        System.out.println("---------------------");
    }
    public static void main(String[] args) throws IOException{
    System.out.println("------- Plateau de jeu -------");
        System.out.println("Entrez un nombre de lignes: ");
        int rows = Input.readInt();
        System.out.println("Entrez un nombre de colones: ");
        int cols = Input.readInt();
        if(cols<4 || rows < 4)
            throw new IOException();
        
        Board b = new Board(rows, cols);
        displayLegend();
        
        b.fillBoard();
        b.fillIslands();
        while (b.getNumberOfIsland() < 2){
            b = new Board(rows, cols);
            b.fillBoard();
            b.fillIslands();
        } 
        b.display();

        Player player1 = new Player("Timo");
        Player player2 = new Player("Léon");
        Army Army1 = new Army(3);
        Farm Farm1 = new Farm();
        Exploitation Exploitation1 = new Exploitation();
        Port Port1 = new Port();
        Camp Camp1 = new Camp(1);

        List<Position> pos1 = b.getIslands().get("ile1"); // Récupère toutes les positions de l'île "ile1" et l'affecte à pos1.
        for (Position pos : pos1) {
            Tile tile1 = b.getTile(pos);
            tile1.setPlayer(player1); // Récupere la tuile de pos1 et assigne player1 à cette tuile.
        } 
        
        List<Position> pos2 = b.getIslands().get("ile2");  // Récupère toutes les positions de l'île "ile2" et l'affecte à pos2.
        for (Position pos : pos2) {
            Tile tile2 = b.getTile(pos);
            tile2.setPlayer(player2);
        } 



       boolean placedArmy1 = false;
       boolean placedCamp1 = false;
       boolean placedExploitation1 = false;
       boolean placedFarm1 = false;
       boolean placedPort1 = false;

        for (String island : b.getIslands().keySet()) {
            boolean playerPresent = false;
            for (Position pos : b.getIslands().get(island)) {
                if (!playerPresent){ 
                    if (b.getTile(pos).getPlayer() != null) {
                        playerPresent = true;
                    }
                } 
            }
            if (playerPresent){ 
                for (Position pos : b.getIslands().get(island)) {
                    if (!placedArmy1) {
                        if (b.getTile(pos).getType() != Type.SEA && b.getTile(pos).getBuilding() == null) {
                            b.getTile(pos).setBuilding(Army1);
                            placedArmy1 = true;
                    
                        }
                    }
                


                    if (!placedFarm1) {
                        if (b.getTile(pos).getType() != Type.SEA && b.getTile(pos).getBuilding() == null) {
                            b.getTile(pos).setBuilding(Farm1);
                            placedFarm1 = true;
                        }
                    }
                


                    if (!placedExploitation1) {
                        if (b.getTile(pos).getType() != Type.SEA && b.getTile(pos).getBuilding() == null) {
                            b.getTile(pos).setBuilding(Exploitation1);
                            placedExploitation1 = true;
                        }
                    }
                


                    if (!placedCamp1) {
                        if (b.getTile(pos).getType() != Type.SEA && b.getTile(pos).getBuilding() == null) {
                            b.getTile(pos).setBuilding(Camp1);
                            placedCamp1 = true;
                        
                        }
                    }
                


                    if (!placedPort1) {
                        if (b.getTile(pos).getType() != Type.SEA && b.getTile(pos).getBuilding() == null && b.isNearSea(b.getTile(pos)) == true) {
                            b.getTile(pos).setBuilding(Port1);
                            placedPort1 = true;
                        }
                    }
                }
            } 
        } 

        for (int tours = 0; tours < 2; tours++){
            tours = tours + 1;

            for (Position pos : b.getIslands().get("ile1")){
                if (b.getTile(pos).getBuilding() != null){
                    player1.addRessource(b.getTile(pos).getType(), b.getTile(pos).getBuilding().getRessources());
                } 
            } 

            for (Position pos : b.getIslands().get("ile2")){
                if (b.getTile(pos).getBuilding() != null){
                    player2.addRessource(b.getTile(pos).getType(), b.getTile(pos).getBuilding().getRessources());
                } 
            } 


            
            System.out.println("\n-------Tour " + tours + "-------");
            b.display();
            System.out.println(player1.getName() + " - Ressources: " + player1.getRessource());
            System.out.println(player2.getName() + " - Ressources: " + player2.getRessource());
            System.out.println("\n");
            System.out.println("Coût des batiments \n");
            System.out.println(Army1.toString());
            System.out.println(Exploitation1.toString());
            System.out.println(Camp1.toString());
            System.out.println(Port1.toString());
            System.out.println(Farm1.toString());


        } 

        System.out.println("\n------- Iles du jeu -------");
        System.out.println("Nombre d'île: " + b.getNumberOfIsland());

    }
}

