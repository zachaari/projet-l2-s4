package liv;
import java.io.IOException;


public class FinalMain {
    /**
     * Point d'entrée principal du programme.
     * Permet de choisir le jeu à lancer via les arguments ou affiche le menu principal.
     * 
     * @param args Arguments de la ligne de commande (optionnellement "ares" ou "demeter")
     * @throws IOException En cas d'erreur lors de la lecture des entrées
     */
    public static void main(String[] args) throws IOException {
        Livrable4Main.display.clearScreen();
        
        if (args.length < 1) {
            // Menu principal interactif
            Livrable4Main.display.displayMainMenu();
            return;
        }

        String game = args[0];
        switch (game) {
            case "ares":
                Livrable4Main.mainAres();
                break;
            case "demeter":
                Livrable4Main.mainDemeter();
                break;
            default:
                System.out.println(Livrable4Main.display.RED + "Jeu inconnu: " + game + Livrable4Main.display.RESET);
                System.out.println(Livrable4Main.display.YELLOW + "Jeux disponibles: ares, demeter" + Livrable4Main.display.RESET);
                break;
        }
    }
    
}
