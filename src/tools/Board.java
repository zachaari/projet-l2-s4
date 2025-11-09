
package tools;

import java.util.*;
import tools.exception.*;

/**
 * Classe Board qui permet de représenter le plateau de jeu.
 */
public class Board{
	
    // Attributs : 
	private Tile[][] board;
    private int cols;
    private int rows;
	private HashMap<String, ArrayList<Tile>> islands;

    private final int sea;
    private final int earth;

    /**
     * Constructeur de la classe Board
     * @param rows nombre de ligne
     * @param cols nombre de colonne
     */
    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        //this.sea = random.nextInt((this.rows*this.cols)-(this.rows*this.cols*2/3)) + (this.cols*this.rows*2/3);
        this.sea = (this.rows*this.cols*2)/3;
        this.earth = (this.rows*this.cols) - sea;    
        this.board = new Tile[rows][cols];
        this.islands = new HashMap<String, ArrayList<Tile>>();
    }


    /**
     * Permet de remplir le plateau aléatoirement en fonction de la taille.
     */
    public void fillBoard(){
        int nbTile = 0;

        while(nbTile < earth){
            boolean validPos = false;
            Position pos = new Position(this.rows, this.cols, true); //cree une postion aléatoire
            while(!validPos){
                if(this.getTile(pos) == null){ // verification de la validité de la case
                    validPos = true;
                }
                else{
                    pos = new Position(this.rows, this.cols, true);
                }
            }
            Type type = Type.random(); // renvoie un type aléatoire
            Tile tile = new Tile(type, pos);
            this.addTile(tile);
            int neighboor = this.addNeighboor(tile); //ajoute une case voisine à une position et un type aleatoire
            nbTile = nbTile +(1+neighboor); //ajoute 2 tuile

        }

        // remplir le plateau avec les case de type mer
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                Position newSeaPos = new Position(i,j, false);
                if (this.getTile(newSeaPos) == null){ //verication de la validité de la case
                    Tile seaTile = new Tile(Type.SEA, newSeaPos);
                    this.addTile(seaTile);//ajoute la case mer
                }
            }
        }
    }

    /**
     * Méthode permettant de regrouper les tuiles terrestres adjacentes pour former des îles.
     */
    public void fillIslands() {
        this.islands.clear(); 
        boolean[][] visited = new boolean[this.rows][this.cols];  
        int islandCount = 1;
   
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (board[i][j] != null && board[i][j].getType() != Type.SEA && !visited[i][j]) {
                    ArrayList<Tile> island = new ArrayList<>();
                    this.adjacentesTiles(i, j, visited, island);  
                    this.islands.put("ile"+islandCount++, island);
                }
            }
        }
    }

    /**
     * Renvoie la liste des tuiles formant une ile aves celle passée en paramètre.
     * @param pos position de la tuile dont on recherche l'ile
     * @return La liste des tuiles formant l'ile
     * @throws WrongPositionException en cas d'erreur
     */
    public List<Position> getIslandPos(Position pos) throws WrongPositionException{
        String res = null;
        Tile posTile = this.getTile(pos);
        if(posTile.getType() == Type.SEA){
            throw new WrongPositionException("Position non valide.");
        }
        HashMap<String, List<Position>> ile = this.getIslands();
        for (String key : this.islands.keySet()) {
            for (Tile tile : this.islands.get(key)) {
                if(tile == posTile){
                    res = key;
                }
            }
        }
        return ile.get(res);
    }

    /**
     * 
     * @param x
     * @param y
     * @param visited
     * @param island
     */
    private void adjacentesTiles(int x, int y, boolean[][] visited, ArrayList<Tile> island) {
        boolean estFini = false;  
        if (x < 0 || x >= this.rows || y < 0 || y >= this.cols || visited[x][y] || board[x][y] == null || board[x][y].getType() == Type.SEA) {
            estFini = true;  
        }
        if (!estFini) {
            visited[x][y] = true;  
            island.add(board[x][y]); 
    
            adjacentesTiles(x + 1, y, visited, island); 
            adjacentesTiles(x - 1, y, visited, island);  
            adjacentesTiles(x, y + 1, visited, island);  
            adjacentesTiles(x, y - 1, visited, island); 
        }
    }

    /**
    * Retourne la hashmap des associations numéro d'île : liste de positions des tuiles (donc une île)
    * @return association pour avoir un numéro d'île : liste de positions
    */
    public HashMap<String, List<Position>> getIslands() {
        HashMap<String, List<Position>> iles = new HashMap<>();

        for (String key : this.islands.keySet()) {
            List<Position> islandPositions = new ArrayList<>();
            for (Tile tile : this.islands.get(key)) {
                islandPositions.add(tile.getPosition());
            }
            iles.put(key, islandPositions);
        }
        return iles;
    }
  
    /**
     * Retourne le nombre d'ile sur le plateau
     *@return le nombre d'ile present sur le plateau 
     */
    public int getNumberOfIsland(){
        return this.islands.size();
    } 
    /**
     * Retourne la tuile d'une position donnée
     * @param pos la position donnée
     * @return la tuile a la position pos
     */
    public Tile getTile(Position pos){
        int x = pos.getX();
        int y = pos.getY();
        return this.board[x][y];
    }

    /**
    * Ajoute une tuile voisine a la tuile passée en parametre
    * @param tile la tuile 
    * @return le nombre de tentative pour la methode fillboard.
    */
    public int addNeighboor(Tile tile){
        int res=0;
        int maxTent = 0;
        Position pos = tile.getPosition();
        boolean estPosee = false;
        //if(!this.haveNeighboor(pos)){}
            
            if(this.canAddNeighboor(pos)){
                while((!estPosee) && maxTent<6){
                    Position posNeighboor = pos.getRandomNeighboorPosition(this.rows, this.cols);
                    
                    if(this.getTile(posNeighboor) == null){
                        Tile newTile = new Tile(Type.random(), posNeighboor);
                        this.addTile(newTile);
                        estPosee = true;
                        res = 1;
                    }
                    maxTent++;
                }
            }
        
        return res;
    }
    
    /**
     * Ajoute une tuile au plateau 
     * @param tile la tuile à ajouter
     */
    public void addTile(Tile tile){
        Position pos = tile.getPosition();
        int x = pos.getX();
        int y = pos.getY();
        this.board[x][y] = tile;
    }

    /**
     * Récupère une liste des positions voisines autour de pos dans un rayon n, excluant les tuiles de type SEA.
     * 
     * @param pos La position de départ.
     * @param n Le rayon de la zone à explorer.
     * @return Liste des positions des tuiles valides (non-SEA). Liste vide si aucune tuile valide.
     */
    public List<Position> getNeighboorIsland(Position pos, int n){
        List<Position> neighboor = new ArrayList<>();
        for (int i = -n; i <= n; i++) {
            for (int j = -n; j <= n; j++) {
                if (pos.getX()>= 0 && pos.getX() < this.rows && pos.getY() >= 0 && pos.getY() < this.cols) {
                    Tile neighborTile = this.getTile(new Position(pos.getX(), pos.getY(), true));
                    if (neighborTile.getType() != Type.SEA) {
                        neighboor.add(neighborTile.getPosition());
                    }
                }
            }
        } 

        return neighboor;   
    }


    /**
     * Affiche le plateau en console avec des bordures en bleu et des coordonnées améliorées.
     */
    public void display() {
        // Codes couleur ANSI
        final String BLUE = "\u001B[34m";      // Bleu normal pour les lignes
        final String BRIGHT_BLUE = "\u001B[94m"; // Bleu vif pour les bordures spéciales
        final String YELLOW = "\u001B[33m";    // Jaune pour les numéros de colonnes
        final String CYAN = "\u001B[36m";      // Cyan pour les numéros de lignes
        final String BOLD = "\u001B[1m";       // Texte en gras
        final String RESET = "\u001B[0m";      // Réinitialiser les styles
        
        // En-tête des colonnes
        System.out.print("         ");
        for (int i = 0; i < this.cols; i++) {
            int spaces_cols = 7 - String.valueOf(i).length();
            System.out.print(YELLOW + BOLD + i + RESET + " ".repeat(spaces_cols));
        }
        System.out.println();
        
        // Ligne supérieure du tableau
        System.out.print("      " + BRIGHT_BLUE + "╔");
        for (int i = 0; i < this.cols; i++) {
            System.out.print("══════" + (i < this.cols - 1 ? "╦" : "╗"));
        }
        System.out.println(RESET);
        
        // Corps du tableau
        for (int row = 0; row < this.rows; row++) {
            int spaces_rows = 5 - String.valueOf(row).length();
            System.out.print(" " + CYAN + BOLD + row + RESET + " ".repeat(spaces_rows) + BLUE + "║" + RESET);
            
            // Contenu des cellules - INCHANGÉ
            for (int col = 0; col < this.cols; col++) {
                Position pos = new Position(row, col, false);
                
                String tileType = ".";
                String resource = ".";
                char player = '.';
                String building = ". ";
                
                if (this.getTile(pos).getType() != Type.SEA) {
                    tileType = this.getTypeString(this.getTile(pos));
                    if (this.getTile(pos).getPlayer() != null) {
                        player = this.getTile(pos).getPlayer().getName().charAt(0);
                    }
                    
                    if (this.getTile(pos).getBuilding() != null) {
                        building = this.getBuildingString(this.getTile(pos));
                    }
                    
                    System.out.print(tileType + resource + player + building + BLUE + "║" + RESET);
                } else {
                    System.out.print("🌊🌊 " + " " + BLUE + "║" + RESET);
                }
            }
            
            System.out.println();
            
            // Ligne de séparation entre les rangées
            if (row < this.rows - 1) {
                System.out.print("      " + BRIGHT_BLUE + "╠");
                for (int i = 0; i < this.cols; i++) {
                    System.out.print("══════" + (i < this.cols - 1 ? "╬" : "╣"));
                }
                System.out.println(RESET);
            }
        }
        
        // Ligne inférieure du tableau
        System.out.print("      " + BRIGHT_BLUE + "╚");
        for (int i = 0; i < this.cols; i++) {
            System.out.print("══════" + (i < this.cols - 1 ? "╩" : "╝"));
        }
        System.out.println(RESET);
    }

    /**
    * methode permettant de savoir si la tuile est voisine à une tuile de type mer
    *@param tile la tuile testé
    * @return renvoie true si la tuile est voisine à une tuile de type me, false sinon
    */
    public Boolean isNearSea(Tile tile){
        boolean res = false; 
        int y = tile.getPosition().getY();
        int x = tile.getPosition().getX();
        
        for (int i=-1; i<=1; i++){
            Position pos = new Position(x+i, y, false);
            if(0<=x+i && x+i <= this.rows && this.getTile(pos).getType() == Type.SEA){
                res = true;
            }
        }

        for (int i=-1; i<=1; i++){
            Position pos = new Position(x, y+i, false);
            if(0<=y+i && y+i<= this.cols && this.getTile(pos).getType() == Type.SEA){
                res = true;
            }
        }
        return res;
    }

    /**
     * renvoie une chaine correspondant au type
     * @param tile la tuile correspondant au type
     * @return une chaine correspondnt au type
     *
     */
    public String getTypeString(Tile tile){
        HashMap<Type, String> mapType = new HashMap<Type, String>();
        String res;
        String tab[] = {"🌲", "⛰️ ", "🍀", "🌻"};
        for(int i=0; i<tab.length; i++){
            mapType.put(Type.values()[i], tab[i]);
        }
        res = mapType.get(tile.getType());
        return res;

    }

    /**
     * renvoie une chaine correspondant au batiment
     * @param tile la tuile sur laquel le batiment est posé
     * @return la chaine correspondant au batiment
     */
    public String getBuildingString(Tile tile){
        String res;
        if(tile.getBuilding() instanceof Camp){
            res = "🏕️ ";
        }
        else if(tile.getBuilding() instanceof Army){
            res = "🎖️ ";
        }
        else if(tile.getBuilding() instanceof Exploitation){
            res = "⛏️ ";
        }
        else if(tile.getBuilding() instanceof Farm){
            res = "🚜";
        }
        else{
            res = "🛥️ ";
        }
        return res;
    }
    
    /**
     * renvoie true si il y a des case non null dans le voisinage d'une tuile en fonction de sa position, false sinon
     * @param pos position de la tuile
     * @return true si on peut ajouter un voisin, false sinon
     */
    public boolean canAddNeighboor(Position pos){
        boolean res = false;
        List<Position> listPos= pos.getAllNeighboorPos(this.rows, this.cols);
        for(Position position : listPos){
            if(this.getTile(position) == null){
                return true;
            }
        }  
        return res;
    }
    public boolean haveNeighboor(Position pos){
        boolean res = false;
        List<Position> listPos= pos.getAllNeighboorPos(this.rows, this.cols);
        for(Position position : listPos){
            if(this.getTile(position) != null){
                return true; 
            }
        }  
        return res;
    }
    /**
     * Renvoie la position d'une tuile adjacente à une case de type mer
     * @return une position 
     * @throws NoTileNearSeaException si la position n'est pas adjacente a une position mer
     */
    public Position getPositionNearSea() throws NoTileNearSeaException{
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                Position pos = new Position(i, j);
                if(this.isNearSea(this.getTile(pos))){
                    return pos;
                }
            }
        }
        throw new NoTileNearSeaException("aucune tuile n'est adjacente à la mer");
    }

    /**
     * Renvoie true si la postion est valide, false sinon
     * @param pos position de la tuile
     * @return un booleen suivant si la position est valide ou non.
     */
    public boolean validPosition(Position pos){
        int x = pos.getX();
        int y = pos.getY();
        return 0<=x && x<this.rows && 0<=y && y<this.cols && this.getTile(pos).getType()!=Type.SEA;
    }

    /**
     * getteur pour recuperer l'attribut rows
     * @return l'attribut rows
     */
    public int getRows(){
        return this.rows;
    }

    /**
     * getteur pour recuperer l'attribut cols
     * @return l'attribut cols
     */
    public int getCols(){
        return this.cols;
    }

    /**
    * Compte le nombre d'îles sur lesquelles un joueur possède au moins une tuile.
    * Utilisé pour le calcul des points bonus :
    * - 2 îles = 1 point bonus
    * - 3+ îles = 2 points bonus
    * @param player joueur dont on veut connaître le nombre d'îles occupées
    * @return nombre d'îles sur lesquelles le joueur a au moins une tuile
    */
    public int getNbPlayerIslands(Player player) {
        int nbIslands = 0;
        HashMap<String, List<Position>> islands = this.getIslands();
    
        for (String islandName : islands.keySet()) {
            List<Position> positions = islands.get(islandName);


            for (Position pos : positions) {
                if (this.getTile(pos).getPlayer() == player) {
                    nbIslands++;
                    break;
                }
            }
        }
        return nbIslands;
    }
}
