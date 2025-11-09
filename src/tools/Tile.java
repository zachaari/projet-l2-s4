package tools;


/**
* Classe Tile representant les tuiles du jeu
*/
public class Tile{

    //Attribut
    private Type type;
    private Building building;
    private Position position;
    private Player player;

    /** 
    * Constructeur de la classe Tile permettant de crée une tuile
    * @param type type de la tuile
    * @param position position de la tuile 
    */
    public Tile(Type type,Position position){
        this.type = type;
        this.position = position;
        this.building = null;
        this.player = null;
        
    }

    /**
    * getteur permettant d'obtenir le type d'une tuile
    * @return renvoie le type de la tuile
    */
    public Type getType(){
        return this.type;
    }


    /**
     * setter permettant d'attribuer un joueur à une tuiles
     * @param player le joueur que l'on veux attribuer à la tuile 
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * setter permettant d'attribuer un building à une tuiles
     * @param building le batiment que l'on veux attribuer à la tuile 
     */
    public void setBuilding(Building building){
        this.building = building;
    }

    /**
     * getteur permettant d'obtenir les ressources d'une tuile
     * @return renvoie les ressources de la tuile
     */
    public String getRessource(){
        return this.type.getRessource();
    }

    /**
     * getteur permettant d'obtenir le batiment present la tuile
     * @return renvoie le batiment de la tuile
     */     
    public Building getBuilding(){
        return this.building;
    }

    /**
     * getteur permettant d'obtenir le joueur present sur une tuile
     * @return renvoie le joueur present sur la tuile
     **/
    public Player getPlayer(){
        return this.player;
    }

    /**
     * getteur permettant d'obtenir la position d'une tuile
     * @return renvoie la position de la tuile
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * methode permettant de detruire un batiment
     */
    public void removeBuilding(){
        this.building = null;
    }

    /**
     * methode equals
     * @return True si la tuile est identique à celle passée en parametre, false sinon 
     */
    public boolean equals(Object o){
        if(!(o instanceof Tile)){
            return false;
        }
        Tile other = (Tile) o;
        return this.position.equals(other.getPosition()) && this.type == other.getType();
    }
    

    /**
     * Supprime un joueur d'une ile.
     */
    public void removePlayer(){
        this.player = null;
    }

}