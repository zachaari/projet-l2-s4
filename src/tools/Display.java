package tools;
import java.io.IOException;
import java.util.*;

import game.*;
import liv.Livrable4Main;


/**
 * Classe display qui gère l'affichage des jeux.
 */
public class Display {
    /** Efface */
    public final String RESET = "\u001B[0m";
    /**Rouge */
    public final String RED = "\u001B[31m";
    /**Vert */
    public final String GREEN = "\u001B[32m";
    /**Jaune */
    public final String YELLOW = "\u001B[33m";
    /**Bleu */
    public final String BLUE = "\u001B[34m";
    /**Violoet */
    public final String PURPLE = "\u001B[35m";
    /**Cyan */
    public final String CYAN = "\u001B[36m";

    /**
     * Affiche une animation épique pour l'introduction d'Ares.
     */
    public void displayAresIntroAnimation() {
        clearScreen();
        try {
            // Logo ARES avec ASCII Art
            String[] aresLogo = {
                "    ___    ____  ___________ ",
                "   /   |  / __ \\/ ____/ ___/",
                "  / /| | / /_/ / __/  \\__ \\ ",
                " / ___ |/ _, _/ /___ ___/ / ",
                "/_/  |_/_/ |_/_____//____/  ",
                "                            "
            };
            
            // Animation d'apparition du logo
            for (String line : aresLogo) {
                System.out.println(RED + line + RESET);
                Thread.sleep(200);
            }
            Thread.sleep(500);
            
            clearScreen();
            
            // Animation de texte d'introduction
            String[] introText = {
                "BIENVENUE DANS LE MONDE DE LA GUERRE",
                "CONSTRUISEZ VOS ARMÉES",
                "FORMEZ VOTRE STRATÉGIE",
                "CONQUÉREZ LES ÎLES",
                "DEVENEZ LE MAÎTRE D'ARÈS",
                "QUE LA GUERRE COMMENCE!"
            };
            
            for (String text : introText) {
                System.out.println("\n\n\n\n");
                System.out.println(RED + "          " + text + RESET);
                Thread.sleep(800);
                clearScreen();
            }
            
            // Animation d'épées croisées
            String[] swordAnimation = {
                "      \\\\  //      ",
                "       \\\\//       ",
                "     ---><---     ",
                "       //\\\\       ",
                "      //  \\\\      "
            };
            
            for (int i = 0; i < 3; i++) {
                clearScreen();
                System.out.println("\n\n\n");
                for (String line : swordAnimation) {
                    System.out.println(RED + "          " + line + RESET);
                }
                Thread.sleep(300);
                
                clearScreen();
                System.out.println("\n\n\n");
                for (String line : swordAnimation) {
                    System.out.println(YELLOW + "          " + line + RESET);
                }
                Thread.sleep(300);
            }
            
            clearScreen();
            System.out.println("\n\n\n\n");
            System.out.println(RED + "            ARES" + RESET);
            Thread.sleep(1000);
            System.out.println(YELLOW + "         QUE LE MEILLEUR GAGNE" + RESET);
            Thread.sleep(2000);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        clearScreen();
    }

    /**
     * Affiche une animation épique pour l'introduction de Demeter.
     */
    public void displayDemeterIntroAnimation() {
        clearScreen();
        try {
            // Logo DEMETER avec ASCII Art
            String[] demeterLogo = {
                "  _____   ______ __  __ ______ _______ ______ _____  ",
                " |  __ \\ |  ____|  \\/  |  ____|__   __|  ____|  __ \\ ",
                " | |  | || |__  | \\  / | |__     | |  | |__  | |__) |",
                " | |  | ||  __| | |\\/| |  __|    | |  |  __| |  _  / ",
                " | |__| || |____| |  | | |____   | |  | |____| | \\ \\ ",
                " |_____/ |______|_|  |_|______|  |_|  |______|_|  \\_\\"
            };
            
            // Animation d'apparition du logo
            for (String line : demeterLogo) {
                System.out.println(GREEN + line + RESET);
                Thread.sleep(200);
            }
            Thread.sleep(500);
            
            clearScreen();
            
            // Animation de texte d'introduction
            String[] introText = {
                "BIENVENUE DANS LE MONDE DE LA PROSPÉRITÉ",
                "CULTIVEZ VOS TERRES",
                "DÉVELOPPEZ VOS FERMES",
                "EXPLOITEZ LES RESSOURCES",
                "DEVENEZ LE MAÎTRE DE DEMETER",
                "QUE LA RÉCOLTE COMMENCE!"
            };
            
            for (String text : introText) {
                System.out.println("\n\n\n\n");
                System.out.println(GREEN + "          " + text + RESET);
                Thread.sleep(800);
                clearScreen();
            }
            
            // Animation de culture qui pousse
            String[] growthAnimation = {
                "     ",
                "  .  ",
                " .'. ",
                " |'| ",
                "/\\|'\\/\\",
            };
            
            for (int stage = 0; stage < growthAnimation.length; stage++) {
                clearScreen();
                System.out.println("\n\n\n");
                
                // Afficher plusieurs plantes à différents stades
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 3; j++) {
                        int plantStage = Math.min(stage, growthAnimation.length - 1);
                        System.out.print(GREEN + growthAnimation[plantStage] + "   " + RESET);
                    }
                    System.out.println();
                }
                
                Thread.sleep(400);
            }
            
            clearScreen();
            System.out.println("\n\n\n\n");
            System.out.println(GREEN + "            DEMETER" + RESET);
            Thread.sleep(1000);
            System.out.println(YELLOW + "      L'ESSENCE DE LA FERTILITÉ" + RESET);
            Thread.sleep(2000);
        
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        clearScreen();
    }

    /**
     * Initialise les joueurs en demandant leur nombre et leurs noms.
     * 
     * @return Une liste des joueurs créés
     * @throws IOException En cas d'erreur lors de la lecture des entrées
     */
    public ArrayList<Player> initializePlayers() throws IOException {
        ArrayList<Player> players = new ArrayList<>();
        System.out.println(YELLOW + "Entrez le nombre de joueurs (minimum 2) : " + RESET);
        int numPlayers = getInputInt("", 2);
        for (int i = 1; i <= numPlayers; i++) {
            System.out.println(YELLOW + "Entrez le nom du joueur " + i + " : " + RESET);
            String name = Input.readString();
            players.add(new Player(name));
        }
        return players;
    }

    /**
     * Demande à l'utilisateur d'entrer un nombre entier supérieur ou égal à une valeur minimale.
     * 
     * @param prompt Message à afficher avant la saisie
     * @param minValue Valeur minimale acceptée
     * @return La valeur entière saisie par l'utilisateur
     * @throws IOException En cas d'erreur lors de la lecture des entrées
     */
    public int getInputInt(String prompt, int minValue) throws IOException {
        int value;
        do {
            if (!prompt.isEmpty()) {
                System.out.print(CYAN + prompt + RESET);
            }
            value = Input.readInt();
            if (value < minValue) {
                System.out.println(RED + "Veuillez entrer une valeur supérieure ou égale à " + minValue + "." + RESET);
            }
        } while (value < minValue);
        return value;
    }

    /**
     * Demande à l'utilisateur d'entrer les coordonnées d'une position sur le plateau.
     * 
     * @param prompt Message à afficher avant la saisie
     * @return Un objet Position contenant les coordonnées saisies
     * @throws IOException En cas d'erreur lors de la lecture des entrées
     */
    public Position getPositionInput(String prompt) throws IOException {
        System.out.println(YELLOW + prompt + RESET);
        System.out.print(CYAN + "Entrez la ligne : " + RESET);
        int x = Input.readInt();
        System.out.print(CYAN + "Entrez la colonne : " + RESET);
        int y = Input.readInt();
        return new Position(x, y);
    }

    /**
     * Permet à l'utilisateur de choisir une position sur le plateau en naviguant avec les touches du clavier.
     * 
     * @param prompt Message à afficher pour la sélection
     * @param board Le plateau de jeu
     * @return La position sélectionnée
     * @throws IOException En cas d'erreur lors de la lecture des entrées
     */
    public Position getPositionInputWithArrows(String prompt, Board board) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int currentX = 0;
        int currentY = 0;
        
        int rows = board.getRows();
        int cols = board.getCols();
        
        System.out.println(YELLOW + prompt + RESET);
        System.out.println(CYAN + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║  Utilisez WASD ou 8456 pour naviguer:                        ║" + RESET);
        System.out.println(CYAN + "║  Z/8=haut, Q/4=gauche, S/2=bas, D/6=droite                   ║" + RESET);
        System.out.println(CYAN + "║  Appuyez sur ENTRÉE pour confirmer la position               ║" + RESET);
        System.out.println(CYAN + "╚══════════════════════════════════════════════════════════════╝" + RESET);
        
        boolean confirmed = false;
        
        while (!confirmed) {
            clearScreen();
            System.out.println(YELLOW + prompt + RESET);
            System.out.println(CYAN + "Position actuelle: [" + currentX + ", " + currentY + "]" + RESET);
            
            // Afficher le plateau avec la position sélectionnée mise en évidence
            displayBoardWithSelection(board, currentX, currentY);
            
            System.out.println(PURPLE + "╔══════════════════════════════════════════════════════════════╗" + RESET);
            System.out.println(PURPLE + "║  Contrôles: Z/8=haut, Q/4=gauche, S/2=bas, D/6=droite        ║" + RESET);
            System.out.println(PURPLE + "║  ENTRÉE=confirmer                                            ║" + RESET);
            System.out.println(PURPLE + "╚══════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print(CYAN + "> " + RESET);
            
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.isEmpty()) {
                // L'utilisateur a appuyé sur ENTRÉE pour confirmer
                confirmed = true;
            } else if (input.equals("z") || input.equals("8")) {
                currentX = Math.max(0, currentX - 1); // Haut
            } else if (input.equals("q") || input.equals("4")) {
                currentY = Math.max(0, currentY - 1); // Gauche
            } else if (input.equals("s") || input.equals("2")) {
                currentX = Math.min(rows - 1, currentX + 1); // Bas
            } else if (input.equals("d") || input.equals("6")) {
                currentY = Math.min(cols - 1, currentY + 1); // Droite
            }
        }
        
        return new Position(currentX, currentY);
    }

    /**
     * Affiche les détails d'une tuile sélectionnée.
     * 
     * @param board Le plateau de jeu
     * @param pos La position de la tuile à examiner
     */
    public void displayTileDetails(Board board, Position pos) {
        Tile tile = board.getTile(pos);
        
        System.out.println(CYAN + "╔════════════════════════════════════════════════════════╗");
        System.out.println("║              DÉTAILS DE LA TUILE                       ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        
        // Position
        System.out.println("║ Position: (" + pos.getX() + ", " + pos.getY() + ")                                    ║");
        
        // Type
        System.out.println("║ Type: " + tile.getType() + " - " + board.getTypeString(tile) + "                                    ║");
        
        // Joueur
        if (tile.getPlayer() != null) {
            System.out.println("║ Propriétaire: " + tile.getPlayer().getName() + "                                    ║");
        } else {
            System.out.println("║ Propriétaire: Aucun                                    ║");
        }
        
        // Bâtiment
        if (tile.getBuilding() != null) {
            String buildingType = "";
            if (tile.getBuilding() instanceof Army) {
                buildingType = "Armée";
                System.out.println("║ Bâtiment: " + buildingType + " - Dimension: " + tile.getBuilding().getDimension() + "                                    ║");
            } else if (tile.getBuilding() instanceof Camp) {
                buildingType = "Camp";
                System.out.println("║ Bâtiment: " + buildingType + " - Dimension: " + tile.getBuilding().getDimension() + "                                    ║");
            } else if (tile.getBuilding() instanceof Farm) {
                buildingType = "Ferme";
                System.out.println("║ Bâtiment: " + buildingType + " - Ressources: " + tile.getBuilding().getRessources() + "                                    ║");
            } else if (tile.getBuilding() instanceof Exploitation) {
                buildingType = "Exploitation";
                System.out.println("║ Bâtiment: " + buildingType + " - Ressources: " + tile.getBuilding().getRessources() + "                                    ║");
            } else if (tile.getBuilding() instanceof Port) {
                buildingType = "Port";
                System.out.println("║ Bâtiment: " + buildingType + "                                          ║");
            }
        } else {
            System.out.println("║ Bâtiment: Aucun                                         ║");
        }
        
        // Voisinage avec la mer
        boolean nearSea = board.isNearSea(tile);
        System.out.println("║ Adjacent à la mer: " + (nearSea ? "Oui" : "Non") + "                                ║");
        
        System.out.println("╚════════════════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Affiche le plateau avec la position actuellement sélectionnée mise en évidence.
     * 
     * @param board Le plateau de jeu
     * @param selectedX La coordonnée X de la position sélectionnée
     * @param selectedY La coordonnée Y de la position sélectionnée
     */
    public void displayBoardWithSelection(Board board, int selectedX, int selectedY) {
        // Codes couleur ANSI
        final String BLUE = "\u001B[34m";
        final String BRIGHT_BLUE = "\u001B[94m";
        final String YELLOW = "\u001B[33m";
        final String CYAN = "\u001B[36m";
        final String BOLD = "\u001B[1m";
        final String RESET = "\u001B[0m";
        final String SELECTION_BG = "\u001B[41m"; // Fond rouge pour la sélection
        
        int rows = board.getRows();
        int cols = board.getCols();
        
        // En-tête des colonnes
        System.out.print("         ");
        for (int i = 0; i < cols; i++) {
            int spaces_cols = 7 - String.valueOf(i).length();
            System.out.print(YELLOW + BOLD + i + RESET + " ".repeat(spaces_cols));
        }
        System.out.println();
        
        // Ligne supérieure du tableau
        System.out.print("      " + BRIGHT_BLUE + "╔");
        for (int i = 0; i < cols; i++) {
            System.out.print("══════" + (i < cols - 1 ? "╦" : "╗"));
        }
        System.out.println(RESET);
        
        // Corps du tableau
        for (int row = 0; row < rows; row++) {
            int spaces_rows = 5 - String.valueOf(row).length();
            System.out.print(" " + CYAN + BOLD + row + RESET + " ".repeat(spaces_rows) + BLUE + "║" + RESET);
            
            for (int col = 0; col < cols; col++) {
                Position pos = new Position(row, col, false);
                
                // Vérifier si c'est la position sélectionnée
                boolean isSelected = (row == selectedX && col == selectedY);
                
                String tileType = ".";
                String resource = ".";
                char player = '.';
                String building = ". ";
                
                if (board.getTile(pos).getType() != Type.SEA) {
                    tileType = board.getTypeString(board.getTile(pos));
                    if (board.getTile(pos).getPlayer() != null) {
                        player = board.getTile(pos).getPlayer().getName().charAt(0);
                    }
                    
                    if (board.getTile(pos).getBuilding() != null) {
                        building = board.getBuildingString(board.getTile(pos));
                    }
                    
                    // Ajouter le fond de la sélection si nécessaire
                    if (isSelected) {
                        System.out.print(SELECTION_BG + tileType + resource + player + building + RESET + BLUE + "║" + RESET);
                    } else {
                        System.out.print(tileType + resource + player + building + BLUE + "║" + RESET);
                    }
                } else {
                    // Afficher la mer avec un fond de sélection si nécessaire
                    if (isSelected) {
                        System.out.print(SELECTION_BG + "🌊🌊 " + " " + RESET + BLUE + "║" + RESET);
                    } else {
                        System.out.print("🌊🌊 " + " " + BLUE + "║" + RESET);
                    }
                }
            }
            
            System.out.println();
            
            // Ligne de séparation entre les rangées
            if (row < rows - 1) {
                System.out.print("      " + BRIGHT_BLUE + "╠");
                for (int i = 0; i < cols; i++) {
                    System.out.print("══════" + (i < cols - 1 ? "╬" : "╣"));
                }
                System.out.println(RESET);
            }
        }
        
        // Ligne inférieure du tableau
        System.out.print("      " + BRIGHT_BLUE + "╚");
        for (int i = 0; i < cols; i++) {
            System.out.print("══════" + (i < cols - 1 ? "╩" : "╝"));
        }
        System.out.println(RESET);
    }

    /**
     * Affiche le menu des actions disponibles pour le jeu Demeter.
     */
    public void displayActionsDemeter() {
        System.out.println(PURPLE + "╔════════════════════════════════════════════════╗");
        System.out.println("║             ACTIONS DISPONIBLES               ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║ createFarm     - Créer une ferme              ║");
        System.out.println("║ replaceFarm    - Remplacer ferme→exploitation ║");
        System.out.println("║ createHarbour     - Créer un port             ║");
        System.out.println("║ exchangePort   - Échanger avec un port        ║");
        System.out.println("║ buySecretWeapon - Acheter une arme secrète    ║");
        System.out.println("║ playThief      - Jouer le voleur              ║");
        System.out.println("║ showIslands    - Afficher les îles            ║");
        System.out.println("║ help           - Afficher l'aide              ║");
        System.out.println("║ pass           - Passer le tour               ║");
        System.out.println("║ inspectTile    - Examiner une tuile           ║");
        System.out.println("║ end            - Terminer la partie           ║");
        System.out.println("╚═══════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Affiche le menu des actions disponibles pour le jeu Ares.
     */
    public void displayActionsAres() {
        System.out.println(PURPLE + "╔════════════════════════════════════════════════╗");
        System.out.println("║             ACTIONS DISPONIBLES               ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║ createArmy     - Créer une armée              ║");
        System.out.println("║ replaceArmy    - Remplacer armée→camp         ║");
        System.out.println("║ createHarbour     - Créer un port             ║");
        System.out.println("║ addGuerriers   - Ajouter des guerriers        ║");
        System.out.println("║ putGuerriers   - Placer des guerriers         ║");
        System.out.println("║ attack         - Attaquer                     ║");
        System.out.println("║ buySecretWeapon - Acheter une arme secrète    ║");
        System.out.println("║ showIslands    - Afficher les îles            ║");
        System.out.println("║ help           - Afficher l'aide              ║");
        System.out.println("║ pass           - Passer le tour               ║");
        System.out.println("║ inspectTile    - Examiner une tuile           ║");
        System.out.println("║ end            - Terminer la partie           ║");
        System.out.println("╚═══════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Affiche la légende du plateau de jeu selon le type de jeu.
     * 
     * @param legend Le type de jeu ("ares" ou "demeter")
     */
    public void displayLegend(String legend) {
        String margin = "          "; // Marges pour centrer horizontalement
        System.out.println(margin + GREEN + "╔══════════════════════════════════════╗");
        System.out.println(margin + "║        LÉGENDE DU PLATEAU            ║");
        System.out.println(margin + "╠══════════════════════════════════════╣");
        System.out.println(margin + "║ 🌲  : Forêt                          ║");
        System.out.println(margin + "║ ⛰️   : Montagne                       ║");
        System.out.println(margin + "║ 🍀  : Paturages                      ║");
        System.out.println(margin + "║ 🌻  : Champ                          ║");
        System.out.println(margin + "║ 🌊  : Mer                            ║");
        System.out.println(margin + "║       --- BÂTIMENTS ---              ║");
        if (legend.equals("ares")) {
            System.out.println(margin + "║ 🏕️  : Camp                            ║");
            System.out.println(margin + "║ 🎖️  : Armée                           ║");
        } else {
            System.out.println(margin + "║ ⛏️  : Exploitation                    ║");
            System.out.println(margin + "║ 🚜  : Ferme                          ║");
        }
        System.out.println(margin + "║ 🛥️  : Port                            ║");
        System.out.println(margin + "╚══════════════════════════════════════╝" + RESET);
    }

    /**
     * Affiche les règles du jeu Demeter.
     */
    public void displayGameRulesDemeter() {
        System.out.println(BLUE + "╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  RÈGLES DE JEU - DEMETER                         ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ 1. Créez des fermes pour produire des ressources                 ║");
        System.out.println("║ 2. Améliorez vos fermes en exploitations pour plus de production ║");
        System.out.println("║ 3. Construisez des ports pour accéder à d'autres îles            ║");
        System.out.println("║ 4. Utilisez l'arme secrète (voleur) pour prendre les ressources  ║");
        System.out.println("║    des autres joueurs                                            ║");
        System.out.println("║ 5. Échangez vos ressources via les ports (2:1)                   ║");
        System.out.println("║                                                                  ║");
        System.out.println("║ Coûts:                                                           ║");
        System.out.println("║ - Ferme: 1 🪵 (bois), 1 💎 (minerai)                              ║");
        System.out.println("║ - Exploitation: 2 🪵, 1 🌾 (blé), 1 🐑 (mouton)                   ║");
        System.out.println("║ - Port: 1 🪵, 2 🐑                                                ║");
        System.out.println("║ - Voleur: 1 🪵, 1 💎, 1 🌾                                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Affiche les règles du jeu Ares.
     */
    public void displayGameRulesAres() {
        System.out.println(BLUE + "╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   RÈGLES DE JEU - ARES                           ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ 1. Créez des armées pour conquérir des territoires               ║");
        System.out.println("║ 2. Améliorez vos armées en camps pour plus de stabilité          ║");
        System.out.println("║ 3. Construisez des ports pour accéder à d'autres îles            ║");
        System.out.println("║ 4. Ajoutez des guerriers à votre stock et placez-les             ║");
        System.out.println("║ 5. Attaquez d'autres joueurs pour conquérir leurs territoires    ║");
        System.out.println("║                                                                  ║");
        System.out.println("║ Coûts:                                                           ║");
        System.out.println("║ - Armée: 1 🪵 (bois), 1 🌾 (blé), 1 🐑 (mouton)                   ║");
        System.out.println("║ - Camp: 2 🪵, 3 💎 (minerai)                                      ║");
        System.out.println("║ - Port: 1 🪵, 2 🐑                                                ║");
        System.out.println("║ - Arme secrète: 1 🪵, 1 💎                                        ║");
        System.out.println("║ - Ajouter guerriers (5): 1 🪵, 2 🌾, 2 🐑                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Affiche la liste des îles présentes sur le plateau.
     * 
     * @param board Le plateau de jeu
     */
    public void showIslands(Board board) {
        System.out.println(CYAN + "\n------- ÎLES DU JEU -------" + RESET);
        System.out.println(YELLOW + "Nombre d'îles: " + board.getNumberOfIsland() + RESET);
        
        for (int i = 1; i <= board.getNumberOfIsland(); i++) {
            List<Position> islandPositions = board.getIslands().get("ile" + i);
            System.out.println(CYAN + "Île " + i + ": " + islandPositions.size() + " tuiles" + RESET);
        }
    }

    /**
     * Affiche l'en-tête du tour d'un joueur.
     * 
     * @param player Le joueur dont c'est le tour
     * @param turn Le numéro du tour actuel
     */
    public void displayTurnHeader(Player player, int turn) {
        String margin = "          "; // Marges pour centrer horizontalement
        System.out.println(YELLOW + margin + "╔═════════════════════════════════════════════════════════════╗");
        System.out.println(margin + "║                          TOUR " + String.format("%-2d", turn) + "                            ║");
        System.out.println(margin + "║                 C'est au tour de " + String.format("%-15s", player.getName()) + "            ║");
        System.out.println(margin + "╚═════════════════════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Affiche l'en-tête du jeu avec son nom.
     * 
     * @param gameName Le nom du jeu à afficher
     */
    public void displayGameHeader(String gameName) {
        String decoration = "★";
        String header = " Bienvenue dans " + gameName + " ";
        int terminalWidth = 80; // Largeur approximative du terminal
        int decorLength = (terminalWidth - header.length()) / 2;
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < decorLength; i++) {
            sb.append(decoration);
        }
        
        System.out.println("\n\n"); // Lignes vides pour centrer verticalement
        System.out.println(YELLOW + sb.toString() + header + sb.toString() + RESET);
        System.out.println("\n\n"); // Lignes vides pour espacer après
    }

    /**
     * Affiche une animation de chargement avec des points.
     * 
     * @param message Le message à afficher avant les points
     * @param duration La durée totale de l'animation en millisecondes
     */
    public void displayLoadingAnimation(String message, int duration) {
        int steps = duration / 500; // Nombre d'étapes (500ms par étape)
        try {
            System.out.print(CYAN + message);
            for (int i = 0; i < steps; i++) {
                System.out.print(".");
                Thread.sleep(500); // Pause de 500ms
            }
            System.out.println(RESET); // Réinitialiser la ligne
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Affiche une animation de transition entre les tours de jeu.
     */
    public void displayTurnTransition() {
        try {
            String[] frames = {
                "◐ Changement de tour ◐",
                "◓ Changement de tour ◓",
                "◑ Changement de tour ◑",
                "◒ Changement de tour ◒"
            };
            
            for (int i = 0; i < 8; i++) {
                System.out.print(YELLOW + "\r" + frames[i % 4]);
                Thread.sleep(200);
            }
            System.out.println("\r                          " + RESET);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Affiche une animation de combat pour le jeu Ares.
     */
    public void displayCombatAnimation() {
        try {
            String[] frames = {
                "⚔️  COMBAT  ⚔️",
                " ⚔️ COMBAT ⚔️ ",
                "  ⚔️COMBAT⚔️  ",
                " ⚔️ COMBAT ⚔️ "
            };
            
            for (int i = 0; i < 10; i++) {
                System.out.print(RED + "\r" + frames[i % 4]);
                Thread.sleep(200);
            }
            System.out.println("\r              " + RESET);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Affiche un compte à rebours entre les tours.
     * 
     * @param seconds Le nombre de secondes du compte à rebours
     */
    public void countdown(int seconds) {
        try {
            System.out.print(YELLOW);
            for (int i = seconds; i > 0; i--) {
                System.out.print("\rNouveau tour dans " + i + " secondes...");
                Thread.sleep(1000);
            }
            System.out.println("\r                                        ");
            System.out.print(RESET);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Affiche les résultats finaux de la partie Ares pour chaque joueur.
     * 
     * @param players La liste des joueurs
     */
    public void displayGameResultsAres(ArrayList<Player> players) {
        clearScreen();
        System.out.println(YELLOW + "╔═════════════════════════════════════════════════════════════╗");
        System.out.println("║                        FIN DE PARTIE                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════════╝" + RESET);
        
        System.out.println(PURPLE + "\n********** RÉSULTATS FINAUX **********" + RESET);
        
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println(CYAN + "\nJoueur " + (i+1) + ": " + player.getName() + RESET);
            player.displayRessources();
            
            if (player.haveSecretWeapon()) {
                System.out.println(YELLOW + "🔮 Possède une arme secrète" + RESET);
            }
            
            System.out.println(YELLOW + "🛡️ Guerriers en stock: " + player.getWarriorsStock() + RESET);
            
            if (player.getHasPort()) {
                System.out.println(YELLOW + "🛥️ Possède au moins un port" + RESET);
            }
        }
        
        System.out.println(GREEN + "\nMerci d'avoir joué! Appuyez sur ENTRÉE pour quitter..." + RESET);
        new Scanner(System.in).nextLine();
    }
    
    /**
     * Affiche les résultats finaux de la partie Demeter pour chaque joueur
     * avec le détail des points obtenus.
     * 
     * @param players La liste des joueurs
     * @param game L'instance du jeu Demeter pour calculer les points
     */
    public void displayGameResultsDemeter(ArrayList<Player> players, DemeterGame game) {
        clearScreen();
        System.out.println(YELLOW + "╔═════════════════════════════════════════════════════════════╗");
        System.out.println("║                        FIN DE PARTIE                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════════╝" + RESET);
        
        System.out.println(PURPLE + "\n********** RÉSULTATS FINAUX **********" + RESET);
        
        // Identifier le gagnant (celui qui a le plus de points)
        Player winner = null;
        int maxPoints = -1;
        
        for (Player player : players) {
            int points = game.calculatePoints(player);
            if (points > maxPoints) {
                maxPoints = points;
                winner = player;
            }
        }
        
        // Afficher les résultats pour chaque joueur
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int points = game.calculatePoints(player);
            int nbIslands = game.getBoard().getNbPlayerIslands(player);
            
            System.out.println(CYAN + "\n" + (player == winner ? "🏆 " : "") + "Joueur " + (i+1) + ": " + player.getName() + RESET);
            
            // Comptage des bâtiments
            int fermes = 0;
            int exploitations = 0;
            Board b = game.getBoard();
            
            for (int r = 0; r < b.getRows(); r++) {
                for (int c = 0; c < b.getCols(); c++) {
                    Position pos = new Position(r, c);
                    Tile tile = b.getTile(pos);
                    
                    if (tile.getPlayer() == player) {
                        if (tile.getBuilding() instanceof Farm && !(tile.getBuilding() instanceof Exploitation)) {
                            fermes++;
                        } else if (tile.getBuilding() instanceof Exploitation) {
                            exploitations++;
                        }
                    }
                }
            }
            
            // Afficher les points
            System.out.println(YELLOW + "Points: " + points + RESET);
            System.out.println(PURPLE + "  • Fermes: " + fermes + " x 1 = " + fermes + " points" + RESET);
            System.out.println(PURPLE + "  • Exploitations: " + exploitations + " x 2 = " + (exploitations * 2) + " points" + RESET);
            
            if (nbIslands >= 3) {
                System.out.println(PURPLE + "  • Bonus pour " + nbIslands + " îles: 2 points" + RESET);
            } else if (nbIslands == 2) {
                System.out.println(PURPLE + "  • Bonus pour 2 îles: 1 point" + RESET);
            }
            
            // Ressources du joueur
            player.displayRessources();
            
            // Autres informations
            if (player.haveSecretWeapon()) {
                System.out.println(YELLOW + "🔮 Possède une arme secrète (voleur)" + RESET);
            }
            
            if (player.getHasPort()) {
                System.out.println(YELLOW + "🛥️ Possède au moins un port" + RESET);
            }
            
            // Ligne de séparation entre les joueurs
            System.out.println(PURPLE + "───────────────────────────────────────────" + RESET);
        }
        
        // Annonce du gagnant
        if (winner != null) {
            System.out.println(GREEN + "\n🏆 " + winner.getName() + " remporte la partie avec " + maxPoints + " points! 🏆" + RESET);
        }
        
        System.out.println(GREEN + "\nMerci d'avoir joué! Appuyez sur ENTRÉE pour quitter..." + RESET);
        new Scanner(System.in).nextLine();
    }
    
    /**
     * Permet de mieux gérer l'affichage du résultat
     * @param players liste des joueurs
     */
    public void displayGameResults(ArrayList<Player> players) {
        displayGameResultsAres(players);
    }

    /**
     * Affiche les scores de tous les joueurs
     * @param game Le jeu Demeter en cours
     * @param players La liste des joueurs
     */
    public void displayScores(DemeterGame game, ArrayList<Player> players) {
        System.out.println(CYAN + "╔══════════════════════════════════════════════╗");
        System.out.println("║                TABLEAU DES SCORES             ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        
        for (Player player : players) {
            int points = game.calculatePoints(player);
            int nbIslands = game.getBoard().getNbPlayerIslands(player);
            String bonusText = "";
            
            if (nbIslands == 2) {
                bonusText = " (+1 bonus pour 2 îles)";
            } else if (nbIslands > 2) {
                bonusText = " (+2 bonus pour " + nbIslands + " îles)";
            }
            
            System.out.printf("║ %-20s: %2d points%s", player.getName(), points, bonusText);
            System.out.println("            ║");
        }
        
        System.out.println("╚══════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Annonce le vainqueur de la partie
     * @param winner Le joueur qui a gagné
     * @param points Le nombre de points du vainqueur
     */
    public void announceWinner(Player winner, int points) {
        System.out.println("\n" + YELLOW + "╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     FIN DE LA PARTIE                          ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %s a atteint %d points et remporte la victoire!          ║\n", winner.getName(), points);
        System.out.println("╚══════════════════════════════════════════════════════════════╝" + RESET);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n" + GREEN + "Félicitations " + winner.getName() + "! 🎉🏆" + RESET);
    }

    /**
     * Efface l'écran du terminal.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Affiche le menu principal permettant de choisir entre les différentes options:
     * - Jouer à Demeter
     * - Jouer à Ares
     * - Afficher les règles
     * - Quitter
     * @throws IOException En cas d'erreur lors de la lecture des entrées
     */
    public void displayMainMenu() throws IOException {
        displayLoadingAnimation("Bienvenue dans le jeu", 2000); // Animation de bienvenue
        
        while (true) {
            clearScreen();
            System.out.println(PURPLE + "╔══════════════════════════════════════════════════╗");
            System.out.println("║                  MENU PRINCIPAL                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.println("║ 1. Jouer à Demeter                               ║");
            System.out.println("║ 2. Jouer à Ares                                  ║");
            System.out.println("║ 3. Afficher les règles de Demeter                ║");
            System.out.println("║ 4. Afficher les règles d'Ares                    ║");
            System.out.println("║ 5. Quitter                                       ║");
            System.out.println("╚══════════════════════════════════════════════════╝" + RESET);
            
            System.out.print(CYAN + "Votre choix > " + RESET);
            int choice = Input.readInt();
            
            switch (choice) {
                case 1:
                    displayLoadingAnimation("Lancement de Demeter", 1500);
                    Livrable4Main.mainDemeter();
                    break;
                case 2:
                    displayLoadingAnimation("Lancement d'Ares", 1500);
                    Livrable4Main.mainAres();
                    break;
                case 3:
                    clearScreen();
                    displayLoadingAnimation("Chargement des règles", 1000);
                    displayGameRulesDemeter();
                    System.out.println(GREEN + "\nAppuyez sur ENTRÉE pour revenir au menu principal..." + RESET);
                    new Scanner(System.in).nextLine();
                    break;
                case 4:
                    clearScreen();
                    displayLoadingAnimation("Chargement des règles", 1000);
                    displayGameRulesAres();
                    System.out.println(GREEN + "\nAppuyez sur ENTRÉE pour revenir au menu principal..." + RESET);
                    new Scanner(System.in).nextLine();
                    break;
                case 5:
                    clearScreen();
                    displayLoadingAnimation("Fermeture du jeu", 1500);
                    System.out.println(GREEN + "Au revoir et merci d'avoir joué!" + RESET);
                    return;
                default:
                    System.out.println(RED + "Option invalide. Appuyez sur ENTRÉE pour réessayer..." + RESET);
                    new Scanner(System.in).nextLine();
            }
        }
    }

    /**
     * Animation de la roue pour sélectionner un objectif aléatoirement.
     * L'animation ralentit progressivement et s'arrête sur un objectif choisi.
     * @param playerName nom du joueur
     * @param objectives Liste des descriptions d'objectifs possibles
     * @return L'indice de l'objectif sélectionné
     */
    public int displayObjectiveRoulette(String[] objectives,String playerName) {
        clearScreen();
        Scanner scanner = new Scanner(System.in);
        
        // Configuration de l'animation
        int selectedIndex = 0;
        int spinSpeed = 80; // Millisecondes entre les frames initialement
        int speedIncrement = 15; // Augmentation du délai à chaque étape de ralentissement
        int maxSpeed = 500; // Délai maximal = vitesse la plus lente
        int totalSpins = 30; // Nombre total de "tours" avant la sélection finale
        
        System.out.println(YELLOW + "╔═══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    "+playerName + " : TIRAGE DE L'OBJECTIF DE MISSION              ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  Appuyez sur ENTRÉE pour lancer la roue des objectifs...                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝" + RESET);
        
        scanner.nextLine(); // Attendre que l'utilisateur appuie sur Entrée
        
        try {
            // Véritable sélection aléatoire à la fin, indépendamment de l'animation
            Random random = new Random();
            selectedIndex = random.nextInt(objectives.length);
            
            for (int i = 0; i < totalSpins; i++) {
                clearScreen();
                // Pour l'animation, nous continuons à utiliser i comme avant
                int animationIndex = (i % objectives.length);
                displayRouletteFrame(objectives, animationIndex,playerName);
                
                // Calculer le délai pour cette étape
                int currentDelay;
                if (i < totalSpins / 2) {
                    // Première moitié: ralentissement progressif
                    currentDelay = spinSpeed + (i * speedIncrement);
                } else {
                    // Seconde moitié: ralentissement plus important
                    currentDelay = spinSpeed + (i * speedIncrement * 2);
                }
                
                // Limiter la vitesse maximale
                if (currentDelay > maxSpeed) {
                    currentDelay = maxSpeed;
                }
                
                Thread.sleep(currentDelay);
            }
            
            // Sélection finale avec effet visuel - utilise l'index aléatoire pré-sélectionné
            clearScreen();
            
            // Animation de sélection finale avec clignotement
            for (int i = 0; i < 5; i++) {
                clearScreen();
                displaySelectedObjective(objectives, selectedIndex, (i % 2 == 0),playerName);
                Thread.sleep(300);
            }
            
            // Affichage final de l'objectif sélectionné
            clearScreen();
            displaySelectedObjective(objectives, selectedIndex, true,playerName);
            
            System.out.println(GREEN + "\nAppuyez sur ENTRÉE pour accepter cet objectif de mission..." + RESET);
            scanner.nextLine();
            
            return selectedIndex;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new Random().nextInt(objectives.length); // Fallback en cas d'interruption
        }
    }
    
    /**
     * Affiche une frame de l'animation de la roulette.
     * 
     * @param objectives Liste des descriptions d'objectifs
     * @param currentIndex Index actuellement sélectionné
     */
    private void displayRouletteFrame(String[] objectives, int currentIndex,String playerName) {
        System.out.println(YELLOW + "╔═══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          "+playerName + " : ROUE DES OBJECTIFS                     ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣" + RESET);
        
        for (int i = 0; i < objectives.length; i++) {
            if (i == currentIndex) {
                // Objectif actuellement sélectionné
                System.out.println(RED + "║ " + CYAN + "▶ " + RED + objectives[i] + " ◀" + YELLOW + " ║");
            } else {
                // Autres objectifs
                System.out.println(YELLOW + "║   " + objectives[i] + "   ║");
            }
        }
        
        System.out.println(YELLOW + "╚═══════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println("\n" + PURPLE + "La roue tourne... 🎲" + RESET);
    }
    
    /**
     * Affiche l'objectif sélectionné de manière festive.
     * 
     * @param objectives Liste des descriptions d'objectifs
     * @param selectedIndex Index de l'objectif sélectionné
     * @param highlight Si true, met en surbrillance l'objectif
     */
    private void displaySelectedObjective(String[] objectives, int selectedIndex, boolean highlight,String playerName) {
        System.out.println(YELLOW + "╔═══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     " + playerName + " : VOTRE OBJECTIF DE MISSION EST...                       ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣" + RESET);
        
        for (int i = 0; i < objectives.length; i++) {
            if (i == selectedIndex) {
                // Objectif sélectionné avec mise en évidence conditionnelle
                if (highlight) {
                    System.out.println(GREEN + "║ " + RED + "★★★ " + GREEN + objectives[i] + RED + " ★★★" + YELLOW + " ║");
                } else {
                    System.out.println(YELLOW + "║     " + objectives[i] + "     ║");
                }
            } else {
                // Objectifs non sélectionnés (estompés)
                System.out.println(YELLOW + "║   " + RESET + objectives[i] + YELLOW + "   ║");
            }
        }
        
        System.out.println(YELLOW + "╚═══════════════════════════════════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Affiche l'avancement des objectifs pour tous les joueurs avec une barre de progression
     * @param game Le jeu Ares
     * @param players La liste des joueurs
     */
    public void displayObjectivesProgress(AresGame game, ArrayList<Player> players) {
        System.out.println(YELLOW + "╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      PROGRÈS VERS LES OBJECTIFS                           ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════╣" + RESET);
        
        for (Player player : players) {
            Objectif objectif = player.getObjectif();
            if (objectif == null) {
                continue;
            }
            
            int progress = 0;
            
            switch (objectif.getType()) {
                case "CONQUER_TILES":
                    int currentTiles = game.countPlayerTiles(player);
                    progress = Math.min(100, (currentTiles * 100) / objectif.getRequiredTiles());
                    System.out.println(CYAN + "║ " + player.getName() + " - Conquérir des tuiles: " + 
                                      currentTiles + "/" + objectif.getRequiredTiles() + " (" + progress + "%)" + RESET);
                    break;
                    
                case "INVADE_ISLAND":
                    boolean conquered = game.hasConqueredIsland(player);
                    progress = conquered ? 100 : 0;
                    System.out.println(CYAN + "║ " + player.getName() + " - Envahir une île: " + 
                                      (conquered ? "Réussi" : "En cours") + " (" + progress + "%)" + RESET);
                    break;
                    
                case "WARRIOR_COUNT":
                    int currentWarriors = game.countTotalWarriors(player);
                    progress = Math.min(100, (currentWarriors * 100) / objectif.getRequiredWarriors());
                    System.out.println(CYAN + "║ " + player.getName() + " - Atteindre " + objectif.getRequiredWarriors() + " guerriers: " + 
                                      currentWarriors + "/" + objectif.getRequiredWarriors() + " (" + progress + "%)" + RESET);
                    break;
            }
            
            // Création de la barre de progression
            int barLength = 50; // Longueur totale de la barre
            int filledLength = (int)((progress * barLength) / 100.0);
            
            StringBuilder bar = new StringBuilder("[");
            for (int i = 0; i < barLength; i++) {
                if (i < filledLength) {
                    bar.append(GREEN + "█" + RESET);
                } else {
                    bar.append(RED + "░" + RESET);
                }
            }
            bar.append("]");
            
            System.out.println(YELLOW + "║ " + bar.toString() + RESET);
            System.out.println(YELLOW + "╟──────────────────────────────────────────────────────────────────────────╢" + RESET);
        }
        
        System.out.println(YELLOW + "╚══════════════════════════════════════════════════════════════════════════╝" + RESET);
    }
}
