package tools;

/**
 * Classe TestingBoard pour générer un plateau destiné aux tests
 */
public class TestingBoard extends Board {
    
    static final int rows = 6;
    static final int cols = 7;

    /**
     * Constructeur classe TestingBoard
     */
    public TestingBoard(){
        super(rows, cols);
    }


    /**
     * Permet de remplir le plateau pour avoir un plateau générer manuellement.
     */
    public void fillBoard(){
        
        //Ajout des tuiles manuellement
        this.addTile(new Tile(Type.PASTURE, new Position(0, 4)));
        this.addTile(new Tile(Type.PASTURE, new Position(1, 4)));
        this.addTile(new Tile(Type.PASTURE, new Position(1, 5)));
        this.addTile(new Tile(Type.PASTURE, new Position(3, 1)));
        this.addTile(new Tile(Type.PASTURE, new Position(3, 3)));
        this.addTile(new Tile(Type.PASTURE, new Position(4, 2)));
        this.addTile(new Tile(Type.PASTURE, new Position(4, 3)));
        this.addTile(new Tile(Type.FIELD, new Position(0, 5)));
        this.addTile(new Tile(Type.FIELD, new Position(3, 4)));
        this.addTile(new Tile(Type.FIELD, new Position(4, 4)));
        this.addTile(new Tile(Type.FOREST, new Position(1, 1)));
        this.addTile(new Tile(Type.FOREST, new Position(4, 1)));
        this.addTile(new Tile(Type.MOUNTAIN, new Position(1, 2)));
        this.addTile(new Tile(Type.MOUNTAIN, new Position(3, 2)));

        //Ajout des cases mers
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                Position newSeaPos = new Position(i,j, false);
                if (this.getTile(newSeaPos) == null){ //verication de la validité de la case
                    Tile seaTile = new Tile(Type.SEA, newSeaPos);
                    this.addTile(seaTile);//ajoute la case mer
                }
            }
        }

    }

}
