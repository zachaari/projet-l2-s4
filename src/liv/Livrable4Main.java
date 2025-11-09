package liv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import game.*;
import tools.*;

/**
 * Classe principale pour les jeux Demeter et Ares.
 * Permet de jouer à deux versions différentes du jeu de plateau:
 */
public class Livrable4Main {
    public static Display display = new Display();
    /**
     * Méthode principale pour jouer au jeu Demeter.
     * Gère l'initialisation, le déroulement et la fin d'une partie.
     * 
     * @throws IOException En cas d'erreur lors de la lecture des entrées
     */
    public static void mainDemeter() throws IOException {
        display.clearScreen();
        display.displayDemeterIntroAnimation();
        display.displayLoadingAnimation("Initialisation du jeu Demeter", 2000);
        display.displayGameHeader("DEMETER");
        ArrayList<Player> players = display.initializePlayers();
        System.out.println(display.CYAN + "------- CONFIGURATION DU PLATEAU -------" + display.RESET);

        int rows = display.getInputInt("Entrez un nombre de lignes : ", players.size()*players.size());
        int cols = display.getInputInt("Entrez un nombre de colonnes : ", players.size()*players.size());

        while (players.size() > ((cols * rows) / players.size())){
            System.out.println("Veuillez choisir un plateau plus grand pour pouvoir joué.");
            rows = display.getInputInt("Entrez un nombre de lignes : ", players.size()*players.size());
            cols = display.getInputInt("Entrez un nombre de colonnes : ", players.size()*players.size());
        } 

        Board b = new Board(rows, cols);
        display.displayLegend("demeter");
        System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
        new Scanner(System.in).nextLine();
        
        System.out.println(display.CYAN + "Génération du plateau en cours..." + display.RESET);
        display.displayLoadingAnimation("Création du monde", 3000); // Animation de 3 secondes
        b.fillBoard();
        b.fillIslands();
        while (b.getNumberOfIsland() < players.size()) {
            System.out.println(display.YELLOW + "Le plateau doit contenir au moins "+players.size()+ " îles. Génération d'un nouveau plateau..." + display.RESET);
            display.displayLoadingAnimation("Reconfiguration du monde", 2000); // Animation de 2 secondes
            b = new Board(rows, cols);
            b.fillBoard();
            b.fillIslands();
        }
        
        display.clearScreen();
        System.out.println(display.GREEN + "Plateau généré avec succès!" + display.RESET);
        b.display();
        display.showIslands(b);

        DemeterGame game = new DemeterGame(players, b, 1);

        // Distribution initiale des ressources
        display.displayLoadingAnimation("Distribution des ressources aux joueurs", 2000);
        for (Player player : players) {
            player.addRessource(Type.FOREST, 10);
            player.addRessource(Type.MOUNTAIN, 10);
            player.addRessource(Type.PASTURE, 10);
            player.addRessource(Type.FIELD, 10);
        }
        System.out.println(display.YELLOW + "Chaque joueur peut poser 2 fermes gratuitement au début du jeu." + display.RESET);

        // Premier tour - ordre croissant
        System.out.println(display.CYAN + "\n--- PREMIER TOUR DE PLACEMENT ---" + display.RESET);
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println(player.getName() + ", placez votre première ferme.");
            Position pos;
            boolean validPosition = false;
            
            while (!validPosition) {
                pos = display.getPositionInputWithArrows(player.getName()+": Sélectionnez la position pour placer votre ferme.", b);
                boolean isDone = game.addFirstBuilding(player, pos, new Farm());
                if (!isDone) {
                    System.out.println(display.RED + "Erreur: La tuile doit être vide et ne pas être de type mer. Veuillez choisir une autre position." + display.RESET);
                    System.out.println("Appuyez sur ENTRÉE pour continuer...");
                    new Scanner(System.in).nextLine();
                } else {
                    validPosition = true;
                    display.displayLoadingAnimation("Déploiement de la ferme", 1000);
                }
            }
        }

        // Deuxième tour - ordre décroissant
        System.out.println(display.CYAN + "\n--- DEUXIÈME TOUR DE PLACEMENT (ORDRE INVERSÉ) ---" + display.RESET);
        for (int i = players.size() - 1; i >= 0; i--) {
            Player player = players.get(i);
            System.out.println(player.getName() + ", placez votre seconde ferme.");
            Position pos;
            boolean validPosition = false;
            
            while (!validPosition) {
                pos = display.getPositionInputWithArrows(player.getName()+": Sélectionnez la position pour placer votre ferme.", b);
                boolean isDone = game.addFirstBuilding(player, pos, new Farm());
                if (!isDone) {
                    System.out.println(display.RED + "Erreur: La tuile doit être vide et ne pas être de type mer. Veuillez choisir une autre position." + display.RESET);
                    System.out.println("Appuyez sur ENTRÉE pour continuer...");
                    new Scanner(System.in).nextLine();
                } else {
                    validPosition = true;
                    display.displayLoadingAnimation("Déploiement de la ferme", 1000);
                }
            }
        }
        display.clearScreen();
        display.displayGameRulesDemeter();
        System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour commencer la partie..." + display.RESET);
        new Scanner(System.in).nextLine();

        int turn = 1;
        boolean gameRunning = true;
        final int VICTORY_POINTS = 12; // Points nécessaires pour gagner
        Player winner = null;
        
        while (gameRunning) {
            for (Player player : players) {
                display.clearScreen();
                display.displayTurnHeader(player, turn);
                b.display();
                player.displayRessources();
                display.displayActionsDemeter();
                
                while(!game.getNext()){

                    System.out.print(display.CYAN + "Votre action > " + display.RESET);
                    String action = Input.readString();
                    
                    switch (action.toLowerCase()) {
                        case "createfarm":
                            Position pos = display.getPositionInputWithArrows("Sélectionnez la position pour créer une ferme", b);
                            display.displayLoadingAnimation("Construction de la ferme", 1500);
                            game.createFarm(player, pos);
                            break;

                        case "replacefarm":
                            pos = display.getPositionInputWithArrows("Sélectionnez la position pour transformer la ferme en exploitation", b);
                            display.displayLoadingAnimation("Transformation en exploitation", 1500);
                            game.replaceFarm(player, pos);
                            break;

                        case "createharbour":
                            pos = display.getPositionInputWithArrows("Sélectionnez la position pour créer un port", b);
                            display.displayLoadingAnimation("Construction du port", 1500);
                            game.createPort(player, pos);
                            break;

                        case "exchangeport":
                            pos = display.getPositionInputWithArrows("Sélectionnez la position du port pour l'échange", b);
                            System.out.println(display.YELLOW + "Ressource souhaitée (wood, ore, wheat, sheep) : " + display.RESET);
                            String getRessource = Input.readString();
                            System.out.println(display.YELLOW + "Ressource à donner (wood, ore, wheat, sheep) : " + display.RESET);
                            String giveRessource = Input.readString();
                            display.displayLoadingAnimation("Échange en cours", 1500);
                            game.changeResourceWithPort(player, pos, getRessource, giveRessource);
                            break;

                        case "buysecretweapon":
                            display.displayLoadingAnimation("Acquisition d'une arme secrète", 1500);
                            game.buySecretWeapon(player);
                            break;

                        case "playthief":
                            System.out.println(display.YELLOW + "Ressource à voler (wood, ore, wheat, sheep) : " + display.RESET);
                            String resource = Input.readString();
                            display.displayLoadingAnimation("Vol en cours", 2000);
                            game.playThief(player, resource);
                            break;
                        
                        case "showislands":
                            display.showIslands(b);
                            System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
                            new Scanner(System.in).nextLine();
                            continue; // Ne pas passer au joueur suivant

                            case "help":
                            display.displayGameRulesDemeter();
                            System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
                            new Scanner(System.in).nextLine();
                            continue; // Ne pas passer au joueur suivant
                        case "pass":
                            System.out.println(display.YELLOW + "Vous avez choisi de ne rien faire pour ce tour." + display.RESET);
                            System.out.println(display.GREEN + "Appuyez sur ENTRÉE pour continuer..." + display.RESET);
                            new Scanner(System.in).nextLine();
                            game.setNext(true); // Définir next à true pour que le tour soit considéré comme joué
                            break;    
                        case "end":
                        gameRunning = false;
                            game.setNext(true); // Définir next à true pour éviter la boucle
                            break;
                        case "inspecttile":
                            pos = display.getPositionInputWithArrows("Sélectionnez la position de la tuile à examiner", b);
                            display.displayTileDetails(b, pos);
                            System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
                            new Scanner(System.in).nextLine();
                            continue; // Ne pas passer au joueur suivant

                        default:
                            System.out.println(display.RED + "Action non reconnue. Veuillez réessayer." + display.RESET);
                            System.out.println(display.GREEN + "Appuyez sur ENTRÉE pour continuer..." + display.RESET);
                            new Scanner(System.in).nextLine();
                            continue; // Ne pas passer au joueur suivant
                    }
                
                    if (!game.getNext()) {
                        System.out.println(display.YELLOW + "\nAucune action effectuée. Appuyez sur ENTRÉE pour réessayer..." + display.RESET);
                        new Scanner(System.in).nextLine();
                    }
                }
                game.setNext(false);
                System.out.println(display.GREEN + "\nAction effectuée! Appuyez sur ENTRÉE pour continuer..." + display.RESET);
                new Scanner(System.in).nextLine();
                
                // Vérification de la condition de victoire (12 points)
                int playerPoints = game.calculatePoints(player);
                if (playerPoints >= VICTORY_POINTS) {
                    winner = player;
                    gameRunning = false;
                    break;
                }
            }
            game.collectRessources();

            
            // Afficher les scores à la fin du tour
            if (gameRunning) {
                display.clearScreen();
                b.display();
                display.displayScores(game, players);
                System.out.println(display.GREEN + "\nFin du tour " + turn + ". Appuyez sur ENTRÉE pour continuer..." + display.RESET);
                new Scanner(System.in).nextLine();
                turn++;
                
                // Afficher un décompte entre les tours
                display.displayTurnTransition();
            }
        }
        
        // Annonce du vainqueur si quelqu'un a gagné
        if (winner != null) {
            display.clearScreen();
            b.display();
            display.displayScores(game, players);
            display.announceWinner(winner, game.calculatePoints(winner));
            System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour terminer la partie..." + display.RESET);
            new Scanner(System.in).nextLine();
        } else {
            display.displayGameResultsDemeter(players, game);
        }
    }

    /**
     * Méthode principale pour jouer au jeu Ares.
     * Gère l'initialisation, le déroulement et la fin d'une partie.
     * @throws IOException En cas d'erreur lors de la lecture des entrées
     */
    public static void mainAres() throws IOException {
        display.clearScreen();
        display.displayAresIntroAnimation();
        display.displayLoadingAnimation("Initialisation du jeu Ares", 2000);
        display.displayGameHeader("ARES");
        ArrayList<Player> players = display.initializePlayers();
        System.out.println(display.CYAN + "------- CONFIGURATION DU PLATEAU -------" + display.RESET);

        int rows = display.getInputInt("Entrez un nombre de lignes : ", players.size()*players.size());
        int cols = display.getInputInt("Entrez un nombre de colonnes : ", players.size()*players.size());

        while (players.size() > ((cols * rows) / players.size())){
            System.out.println("Veuillez choisir un plateau plus grand pour pouvoir joué.");
            rows = display.getInputInt("Entrez un nombre de lignes : ", players.size()*players.size());
            cols = display.getInputInt("Entrez un nombre de colonnes : ", players.size()*players.size());
        } 

        Board b = new Board(rows, cols);
        display.displayLegend("ares");
        System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
        new Scanner(System.in).nextLine();

        System.out.println(display.CYAN + "Génération du plateau en cours..." + display.RESET);
        display.displayLoadingAnimation("Chargement", 3000); // Animation de 3 secondes
        b.fillBoard();
        b.fillIslands();
        while (b.getNumberOfIsland() < players.size()) {
            System.out.println(display.YELLOW + "Le plateau doit contenir au moins "+ players.size()+ " îles. Génération d'un nouveau plateau..." + display.RESET);
            display.displayLoadingAnimation("Rechargement", 3000); // Animation de 3 secondes
            b = new Board(rows, cols);
            b.fillBoard();
            b.fillIslands();
        }

        display.clearScreen();
        System.out.println(display.GREEN + "Plateau généré avec succès!" + display.RESET);
        b.display();
        display.showIslands(b);
        
        // Utiliser la roulette pour sélectionner un objectif

        AresGame game = new AresGame(players, b);
        
        // Attribuer l'objectif à tous les joueurs
        for (Player player : players) {
            display.displayLoadingAnimation("Préparation de la mission...", 1500);
            Objectif missionObjective = Objectif.createWithRoulette(b, display,player.getName());
            System.out.println(display.GREEN + "Votre mission: " + display.YELLOW + missionObjective.getDescription() + display.RESET);
            System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
            new Scanner(System.in).nextLine();
            player.setObjectif(missionObjective);
        }

        // Distribution initiale des ressources
        display.displayLoadingAnimation("Distribution des ressources aux joueurs", 2000);
        for (Player player : players) {
            player.addRessource(Type.FOREST, 10);
            player.addRessource(Type.MOUNTAIN, 10);
            player.addRessource(Type.PASTURE, 10);
            player.addRessource(Type.FIELD, 10);
        }
        
        System.out.println(display.YELLOW + "Chaque joueur peut poser 2 armées gratuitement au début du jeu." + display.RESET);

        // Premier tour - ordre croissant
        System.out.println(display.CYAN + "\n--- PREMIER TOUR DE PLACEMENT ---" + display.RESET);
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println(player.getName() + ", placez votre première armée avec 1 guerrier.");
            Position pos;
            boolean validPosition = false;
            
            while (!validPosition) {
                pos = display.getPositionInputWithArrows(player.getName()+": Sélectionnez la position pour placer votre armée", b);
                boolean isDone = game.addFirstBuilding(player, pos, new Army(1));
                if (!isDone) {
                    System.out.println(display.RED + "Erreur: La tuile doit être vide et ne pas être de type mer. Veuillez choisir une autre position." + display.RESET);
                    System.out.println("Appuyez sur ENTRÉE pour continuer...");
                    new Scanner(System.in).nextLine();
                } else {
                    validPosition = true;
                    display.displayLoadingAnimation("Déploiement de l'armée avec 1 guerrier", 1000);
                }
            }
        }

        // Deuxième tour - ordre décroissant
        System.out.println(display.CYAN + "\n--- DEUXIÈME TOUR DE PLACEMENT (ORDRE INVERSÉ) ---" + display.RESET);
        for (int i = players.size() - 1; i >= 0; i--) {
            Player player = players.get(i);
            System.out.println(player.getName() + ", placez votre seconde armée avec 1 guerrier.");
            Position pos;
            boolean validPosition = false;
            
            while (!validPosition) {
                pos = display.getPositionInputWithArrows(player.getName()+": Sélectionnez la position pour placer votre armée", b);
                boolean isDone = game.addFirstBuilding(player, pos, new Army(1));
                if (!isDone) {
                    System.out.println(display.RED + "Erreur: La tuile doit être vide et ne pas être de type mer. Veuillez choisir une autre position." + display.RESET);
                    System.out.println("Appuyez sur ENTRÉE pour continuer...");
                    new Scanner(System.in).nextLine();
                } else {
                    validPosition = true;
                    display.displayLoadingAnimation("Déploiement de l'armée avec 1 guerrier", 1000);
                }
            }
        }

        display.clearScreen();
        display.displayGameRulesAres();
        System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour commencer la partie..." + display.RESET);
        new Scanner(System.in).nextLine();

        int turn = 1;
        boolean gameRunning = true;

        while (gameRunning) {
            for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
                Player player = players.get(playerIndex);
                display.clearScreen();
                display.displayTurnHeader(player, turn);
                b.display();
                
                player.displayRessources();
                display.displayActionsAres();
                

                while(!game.getNext()){

                    System.out.print(display.CYAN + "Votre action > " + display.RESET);
                    String action = Input.readString();
                    
                    switch (action.toLowerCase()) {
                        case "createarmy":
                        Position pos = display.getPositionInputWithArrows("Sélectionnez la position pour créer une armée", b);
                        int dimension = display.getInputInt("Entrez la dimension de l'armée (1-5) : ", 1);
                        display.displayLoadingAnimation("Création de l'armée", 1500);
                        game.createArmy(player, pos, dimension);
                        break;
                        
                    case "replacearmy":
                        pos = display.getPositionInputWithArrows("Sélectionnez la position pour transformer l'armée en camp", b);
                        dimension = display.getInputInt("Entrez la dimension du camp (1-5) : ", 1);
                        display.displayLoadingAnimation("Transformation en camp", 1500);
                        game.replaceArmy(player, pos, dimension);
                        break;
                        
                    case "createharbour":
                        pos = display.getPositionInputWithArrows("Sélectionnez la position pour créer un port", b);
                        display.displayLoadingAnimation("Construction du port", 1500);
                        game.createPort(player, pos);
                        break;
                        
                    case "addguerriers":
                        display.displayLoadingAnimation("Recrutement de guerriers", 1500);
                        game.addGuerriers(player);
                        break;
                        
                    case "putguerriers":
                        pos = display.getPositionInputWithArrows("Sélectionnez la position où placer les guerriers", b);
                        int nbGuerriers = display.getInputInt("Entrez le nombre de guerriers à placer : ", 1);
                        display.displayLoadingAnimation("Déploiement des guerriers", 1500);
                        game.putGuerriers(player, pos, nbGuerriers);
                        break;
                        
                    case "attack":
                        Position pos1 = display.getPositionInputWithArrows("Sélectionnez la position de votre armée attaquante", b);
                        
                        System.out.println(display.YELLOW + "Choisissez le joueur à attaquer :" + display.RESET);
                        for (int i = 0; i < players.size(); i++) {
                            if (i != playerIndex) {
                                System.out.println((i+1) + ". " + players.get(i).getName());
                            }
                        }
                        
                        int targetIndex;
                        do {
                            targetIndex = display.getInputInt("Entrez le numéro du joueur : ", 1) - 1;
                        } while (targetIndex == playerIndex || targetIndex >= players.size());
                        
                        Player targetPlayer = players.get(targetIndex);
                        
                        Position pos2 = display.getPositionInputWithArrows("Sélectionnez la position de l'armée à attaquer", b);
                        
                        System.out.println(display.YELLOW + "Utiliser une arme secrète ? (true/false) : " + display.RESET);
                        boolean swP1 = Input.readBoolean();
                        
                        System.out.println(display.YELLOW + "L'adversaire utilise une arme secrète ? (true/false) : " + display.RESET);
                        boolean swP2 = Input.readBoolean();
                        
                        System.out.println(display.RED + "\n⚔️ COMBAT EN COURS ⚔️" + display.RESET);
                        display.displayCombatAnimation(); // Animation de combat
                        game.attack(player, targetPlayer, pos1, pos2, swP1, swP2);
                        break;
                        
                    case "buysecretweapon":
                        display.displayLoadingAnimation("Acquisition d'une arme secrète", 1500);
                        game.buySecretWeapon(player);
                        break;
                        
                    case "showislands":
                        display.showIslands(b);
                        System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
                        new Scanner(System.in).nextLine();
                        continue;
                        
                    case "help":
                        display.displayGameRulesAres();
                        System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
                        new Scanner(System.in).nextLine();
                        continue;
                        case "pass":
                        System.out.println(display.YELLOW + "Vous avez choisi de ne rien faire pour ce tour." + display.RESET);
                        System.out.println(display.GREEN + "Appuyez sur ENTRÉE pour continuer..." + display.RESET);
                        new Scanner(System.in).nextLine();
                        game.setNext(true); // Définir next à true pour que le tour soit considéré comme joué
                        break;    
                        case "end":
                        gameRunning = false;
                        game.setNext(true); // Définir next à true pour éviter la boucle
                        break;
                        case "inspecttile":
                        pos = display.getPositionInputWithArrows("Sélectionnez la position de la tuile à examiner", b);
                        display.displayTileDetails(b, pos);
                        System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour continuer..." + display.RESET);
                        new Scanner(System.in).nextLine();
                        continue;
                        
                    default:
                        System.out.println(display.RED + "Action non reconnue. Veuillez réessayer." + display.RESET);
                        System.out.println(display.GREEN + "Appuyez sur ENTRÉE pour continuer..." + display.RESET);
                        new Scanner(System.in).nextLine();
                        continue;
                    }
                
                    if (!game.getNext()) {
                        System.out.println(display.YELLOW + "\nAucune action effectuée. Appuyez sur ENTRÉE pour réessayer..." + display.RESET);
                        new Scanner(System.in).nextLine();
                    }
                }
                game.setNext(false);
                System.out.println(display.GREEN + "\nAction effectuée! Appuyez sur ENTRÉE pour continuer..." + display.RESET);
                new Scanner(System.in).nextLine();
                
                // Vérifier si le joueur actuel a atteint son objectif
                if (game.hasReachedObjective(player)) {
                    display.clearScreen();
                    b.display();
                    System.out.println(display.GREEN + "🏆 VICTOIRE! 🏆" + display.RESET);
                    System.out.println(display.YELLOW + player.getName() + " a atteint l'objectif: " + player.getObjectif().getDescription() + display.RESET);
                    
                    // Afficher le statut de l'objectif pour confirmer
                    game.displayObjectiveStatus(player);
                    
                    System.out.println(display.GREEN + "\nAppuyez sur ENTRÉE pour terminer la partie..." + display.RESET);
                    new Scanner(System.in).nextLine();
                    
                    // Terminer la partie
                    gameRunning = false;
                    break;  // Sortir de la boucle des joueurs
                }
                
                // Afficher le statut actuel de l'objectif
                game.displayObjectiveStatus(player);
            }
            
            // Si un joueur a gagné, on ne continue pas les tours suivants
            if (!gameRunning) {
                break;
            }
            
            game.collectRessources();
            
            // Afficher un résumé de la progression des objectifs à la fin du tour
            display.clearScreen();
            b.display();
            display.displayObjectivesProgress(game, players);
            System.out.println(display.GREEN + "\nFin du tour " + turn + ". Appuyez sur ENTRÉE pour continuer..." + display.RESET);
            new Scanner(System.in).nextLine();
            
            turn++;
            if (gameRunning) {
                display.displayTurnTransition();
            }
        }
        
        // Si la partie s'est terminée par une victoire, afficher les résultats finaux
        display.displayGameResultsAres(players);
    }


}