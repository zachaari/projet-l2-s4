package tools;

import java.util.*;

/**
 * Class Position qui permet de créer une position par rapport à l'objet Tile
 */
public class Position {
    // Attributs:
    private int x;
    private int y;

    private Random random = new Random();
    /**
     * Constructeur d'une position
     * @param x position en x dans la grille
     * @param y position en y dans la grille
     * @param isRandom parametre pour creer une position aléatoire
     */
    public Position(int x, int y ,boolean isRandom){
        if(isRandom){
            this.x = random.nextInt(x-1);
            this.y = random.nextInt(y-1);
        }
        else{
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Constructeur de base  
     * @param x position en x dans la grille
     * @param y position en y dans la grille
     */
    public Position(int x, int y){
        this.y = y;
        this.x = x;
    }

    /**
     * getteur getX
     * @return renvoie la position en x
     */
    public int getX(){
        return this.x;
    }

    /**
     * getteur getY
     * @return renvoie la position en y 
     */
    public int getY(){
        return this.y;
    }

    /**
     * Methode equals entre deux position
     * @return true si deux position sont equals false sinon
     */
    public boolean equals(Object o){
        if (! (o instanceof Position))
            return false;
            // else
        Position other = (Position) o;
        return (this.x == other.x) && (this.y == other.y);
    }
    /**
     * Méthode qui génere une position aléatoire
     * @return la Position aléatoire 
     *
    public Position generateRandomPos(){
        return new Position();
    }**/

    /**
     * renvoie une position alétoire pour une case voisine 
     * @param rows dimension du tableau
     * @param cols dimension du tableau
     * @return une noubvelle position 
     */
    public Position getRandomNeighboorPosition(int rows, int cols){
        Position res;
        List<Position> list = this.getAllNeighboorPos(rows, cols); 
        int i=list.size()-1;
        res = list.get(random.nextInt(i));
        return res;
    }


    /**
     * renvoie une liste contenant la positions adjacente en fonction d'une position
     * @param rows nombre de ligne
     * @param cols nombre de colonne
     * @return La liste des positions
     */
    public List<Position> getAllNeighboorPos(int rows, int cols){
        List<Position> res = new ArrayList<Position>();
        int posX = this.getX();
        int posY = this.getY();
        int x[] = {-1,1};
        int y[] = {-1,1};
        for(int i : x){
            if((0<=posX+i && posX+i < rows)){
                res.add(new Position(posX+i, posY));
            }
        }
        for(int j : y){
            if((0<=posY+j && posY+j < cols)){
                res.add(new Position(posX, posY+j));
            }   
        }
        return res;
    }

    /**
     * Affiche la position en x et y
     * @return le position en x et y
     */
    public String toString(){
        return "x:"+this.x+" y:"+this.y;
    }
}