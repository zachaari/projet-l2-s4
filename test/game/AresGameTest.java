package game;



import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import tools.*;
import tools.exception.*;
import java.util.*;



public class AresGameTest{
    
    private AresGame game;
    private Player player1, player2;
    private Board board;
    private Position pos1, pos2;
    private Tile tile1, tile2, tile3, tile4;


    @BeforeEach
    public void before(){
        this.pos1 = new Position(0, 4);
        this.pos2 = new Position(0, 5);
        this.player1 = new Player("Timo");
        this.player2 = new Player("Leon");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(this.player1);
        players.add(this.player2);
        this.board = new TestingBoard();
        this.board.fillBoard();
        this.board.fillIslands();
        this.game = new AresGame(players, this.board);
        this.game = new AresGame(players, this.board);
        this.player1.addRessource(Type.FOREST, 10);
        this.player1.addRessource(Type.MOUNTAIN, 10);
        this.player1.addRessource(Type.PASTURE, 10);
        this.player1.addRessource(Type.FIELD, 10);
        this.game.getBoard().getTile(new Position(1, 4)).setBuilding(new Army(3));
        this.game.getBoard().getTile(new Position(1, 4)).setPlayer(this.player1);
        this.game.getBoard().getTile(new Position(0, 5)).setBuilding(new Army(3));
        this.game.getBoard().getTile(new Position(0, 5)).setPlayer(this.player1);
        this.game.getBoard().getTile(new Position(3, 4)).setBuilding(new Army(4));
        this.game.getBoard().getTile(new Position(3, 4)).setPlayer(this.player2);
        this.game.getBoard().getTile(new Position(1, 5)).setBuilding(new Port());
        this.game.getBoard().getTile(new Position(1, 5)).setPlayer(this.player1);
        this.player1.setPort(true);

    }
    
    @Test 
    public void createArmyTest(){
        this.game.createArmy(player1, pos1, 3);
        assertTrue(this.game.getBoard().getTile(pos1).getBuilding() instanceof Army);
        assertEquals(this.player1.getRessource().get("wood"), 9);
        assertEquals(this.player1.getRessource().get("sheep"), 9);
        assertEquals(this.player1.getRessource().get("wheat"), 9);  
    }
    
    @Test 
    public void createPortTest(){
        this.game.createPort(player1, pos1);
        assertTrue(this.game.getBoard().getTile(pos1).getBuilding() instanceof Port);
        assertEquals(this.player1.getRessource().get("wood"), 9);
        assertEquals(this.player1.getRessource().get("sheep"), 8);
    }
    
    @Test 
    public void replaceArmyTest(){
        this.game.replaceArmy(player1, pos2, 8);
        assertEquals(this.player1.getRessource().get("wood"), 8);
        assertEquals(this.player1.getRessource().get("ore"), 7);
        assertTrue(this.game.getBoard().getTile(pos2).getBuilding() instanceof Camp);
    }
    
    @Test 
    public void putGuerriersTest(){
        this.game.putGuerriers(player1, pos2, 2);
        assertTrue(this.game.getBoard().getTile(pos2).getBuilding().getDimension() == 5);
    }
    
    
    @Test
    public void addGuerrierTest(){
        assertEquals(30, this.player1.getWarriorsStock());
        this.game.addGuerriers(this.player1);
        assertEquals(35, this.player1.getWarriorsStock());
        assertEquals(8, this.player1.getRessource().get("wheat"));
        assertEquals(8, this.player1.getRessource().get("sheep"));
        assertEquals(9, this.player1.getRessource().get("wood"));
    }  



    @Test
   public void buySecretWeaponTest(){
        this.game.buySecretWeapon(this.player1);
        assertEquals(9, this.player1.getRessource().get("ore"));
        assertEquals(9, this.player1.getRessource().get("wood"));
        assertTrue(this.player1.haveSecretWeapon());
   }   

   @Test
    public void buySecretWeaponTestNotEnoughResourcesTest(){
        this.player1.addRessource(Type.FOREST, -10);
        this.player1.addRessource(Type.MOUNTAIN, -10);
        this.game.buySecretWeapon(this.player1);
        assertFalse(this.player1.haveSecretWeapon());
   } 



   @Test
   public void isValidTileTest() throws WrongPositionException {
        this.game.getBoard().getTile(new Position(0, 4)).setBuilding(new Camp(3));
        assertTrue(this.game.isValidTile(pos1,pos2));
        assertFalse(this.game.isValidTile(new Position(1,5), new Position(0,5)));
        assertFalse(this.game.isValidTile(pos2, new Position(3,1)));
   }  

    @Test
    public void attackTest(){
        this.game.getBoard().display();
        this.game.attack(player1, player2, new Position(1, 4), new Position(3, 4), false, false);
        int dimPlayer1 = this.game.getBoard().getTile(new Position(1, 4)).getBuilding().getDimension();
        int dimPlayer2 = this.game.getBoard().getTile(new Position(3, 4)).getBuilding().getDimension();
        assertTrue(dimPlayer1==2 || dimPlayer2 ==3);
    }


    @Test
    public void hasConqueredIslandTest(){
        assertFalse(this.game.hasConqueredIsland(this.player1));
        this.board.getTile(new Position(0, 4)).setPlayer(this.player1);
        assertTrue(this.game.hasConqueredIsland(this.player1));
         
    }  


    @Test
    public void countTotalWarriorsTest(){
        this.game.addGuerriers(this.player1);
        this.game.createArmy(this.player1, new Position(0, 0), 10);
        assertEquals(41, this.game.countTotalWarriors(this.player1));
    }  

    @Test
    public void countPlayerTiles(){
        this.game.board.display();
        assertEquals(3, this.game.countPlayerTiles(this.player1));
        this.board.getTile(new Position(0, 4)).setPlayer(this.player1);
        assertEquals(4, this.game.countPlayerTiles(this.player1));
    }   


} 