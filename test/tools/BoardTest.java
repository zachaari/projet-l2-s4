package tools;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import tools.exception.WrongPositionException;

import java.util.*;

public class BoardTest {

    private Board board;
    private Tile tile1;
    private Tile tile2;
    private Tile tile3;
    private Tile tile4;


    @BeforeEach
    public void before(){
        this.board = new Board(6,7);
        this.tile1 = new Tile(Type.PASTURE, new Position(3,4,false));
        this.board.addTile(tile1);
        this.tile2 = new Tile(Type.FIELD, new Position(3,5, false));
        this.board.addTile(tile2);
        this.tile3 = new Tile(Type.MOUNTAIN, new Position(1,0,false));
        this.board.addTile(tile3);
        this.tile4 = new Tile(Type.FOREST, new Position(1,1, false));
        this.board.addTile(tile4);
        
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                Position newSeaPos = new Position(i,j, false);
                if (this.board.getTile(newSeaPos) == null){ //verication de la validité de la case
                    Tile seaTile = new Tile(Type.SEA, newSeaPos);
                    this.board.addTile(seaTile);//ajoute la case mer
                }
            }
        }
        this.board.fillIslands();
    }    

    @Test 
    public void isNearSeaTest(){
        assertTrue(this.board.isNearSea(this.tile1));
        assertTrue(this.board.isNearSea(this.tile3));
    }

    @Test
    public void getTileTest(){
        Position pos = new Position(3,4, false);
        assertEquals(this.board.getTile(pos), this.tile1);
    }

    @Test
    public void addTileTest(){
        Position pos = new Position(6,7,true);
        Tile newTile = new Tile(Type.FOREST, pos);
        this.board.addTile(newTile);
        assertTrue(this.board.getTile(pos)==newTile);
    }

    @Test 
    public void getNumberOfIslandTest(){
        this.board.fillIslands();
        this.board.getIslands();
        assertTrue(this.board.getNumberOfIsland()==2);
    }

    @Test 
    public void getTypeString(){
        assertEquals(this.board.getTypeString(tile1), "🍀");
        assertEquals(this.board.getTypeString(tile4), "🌲");

    }


    @Test
    public void getIslandsPosTest() throws WrongPositionException{
        this.board.fillIslands();
        Position pos = new Position(3,4,false);
        List<Position> islandPositions = this.board.getIslandPos(pos);
        assertTrue(islandPositions.contains(pos));
    }  

    @Test
    public void addNeighboorTest(){
        Tile tile = new Tile(Type.FIELD,new Position(2,2,false));
        this.board.addTile(tile);
        assertTrue(this.board.addNeighboor(tile) == 0 || this.board.addNeighboor(tile) == 1);
    }  



    @Test
    public void canAddNeighboorTest(){
        Position pos = new Position(2,3,false);
        Tile tile = new Tile(Type.PASTURE,pos);
        this.board.addTile(tile);
        assertFalse(this.board.canAddNeighboor(pos));
    }  


    @Test
    public void getBuildingStringTest(){
        Tile tile1 = new Tile(Type.FIELD, new Position(4,4,false));
        tile1.setBuilding(new Camp(3));
        assertEquals(this.board.getBuildingString(tile1), "🏕️ ");

        Tile tile2 = new Tile(Type.FIELD, new Position(4,5,false));
        tile2.setBuilding(new Farm());
        assertEquals(this.board.getBuildingString(tile2), "🚜");
    }  


    @Test
    public void getColsTest(){
        assertEquals(7, this.board.getCols());
    }     


    @Test
    public void getRowsTest(){
        assertEquals(6, this.board.getRows());
    }  


    @Test
    public void getIslandTest(){
        HashMap<String, List<Position>> islands = this.board.getIslands();
        assertNotNull(islands, "La HashMap des îles ne doit pas être nulle.");
        assertFalse(islands.isEmpty(), "La HashMap des îles ne doit pas être vide.");
        for (List<Position> positions : islands.values()) {
            assertFalse(positions.isEmpty(), "Une île doit avoir des positions.");
        }  
    }

    @Test
    public void validPositionTest(){
    Position pos = new Position(3,4); 
    assertTrue(this.board.validPosition(pos), "La position devrait être valide.");
    }

}  


