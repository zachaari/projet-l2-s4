package tools;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class TileTest {
    
    private Tile sea;
    private Tile pasture;
    private Tile mountain;
    private Tile forest;
    private Tile field;

    @BeforeEach
    public void before(){
        this.sea = new Tile(Type.SEA, new Position(4,6, false));
        this.pasture = new Tile(Type.PASTURE, new Position(5,7,false));
        this.mountain = new Tile(Type.MOUNTAIN, new Position(2,2,false));
        this.forest = new Tile(Type.FOREST, new Position(6,5,false));
        this.field = new Tile(Type.FIELD, new Position(3,4,false));
    }

    @Test 
    public void getTypeTest(){
        assertEquals(this.sea.getType(), Type.SEA);
        assertEquals(this.pasture.getType(), Type.PASTURE);
    }

    @Test 
    public void getRessourcesTest(){
        assertEquals(this.sea.getRessource(), null);
        assertEquals(this.forest.getRessource(), "wood");
        assertEquals(this.pasture.getRessource(), Type.PASTURE.getRessource());
    }

    @Test 
    public void getPositionTest(){
        Position pos = new Position(4,6,false);
        assertEquals(this.sea.getPosition(), pos);
        assertTrue(this.sea.getPosition().equals(pos));
    }

    @Test 
    public void equalsTest(){
        Tile newTile = new Tile(Type.SEA, new Position(4,6,false));
        assertFalse(this.field.equals(newTile));
        assertTrue(this.sea.equals(newTile));
    }


   @Test
   public void setPlayerTest(){
        Player newPlayer = new Player("Timo");
        this.pasture.setPlayer(newPlayer);
        assertEquals(newPlayer,this.pasture.getPlayer());
   }  


    @Test
    public void removeBuildingTest(){
        Building army = new Army(5);
        this.mountain.setBuilding(army);
        assertEquals(this.mountain.getBuilding(),army);
        this.mountain.removeBuilding();
        assertEquals(this.mountain.getBuilding(), null);
    }  

    @Test 
    public void setBuildingTest(){
        Building army = new Army(1);
        this.sea.setBuilding(army);
        assertTrue(this.sea.getBuilding() != null);
        assertTrue(this.sea.getBuilding() instanceof Army);
        assertEquals(this.sea.getBuilding(), army);
    }

    @Test 
    public void removePlayerTest(){
        this.sea.setPlayer(new Player("timo"));
        assertTrue(this.sea.getPlayer() != null);
        this.sea.removePlayer();
        assertTrue(this.sea.getPlayer() == null);
    }


    @Test 
    public void getBuilding(){
        Building army = new Army(1);
        this.sea.setBuilding(army);
        assertEquals(this.sea.getBuilding(), army);
    }

    @Test 
    public void getPlayer(){
        Player player = new Player("Timo");
        this.sea.setPlayer(player);
        assertTrue(this.sea.getPlayer() == player);
    }

 


}
