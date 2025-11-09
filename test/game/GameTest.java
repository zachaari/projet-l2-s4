package game;

import java.util.*;

import javax.crypto.spec.PSource;

import tools.*;
import tools.exception.NoTileNearSeaException;
import tools.exception.NotEnoughRessourcesException;
import tools.exception.WrongPositionException;
import tools.exception.WrongRessourceException;
import tools.exception.WrongTileException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class GameTest{

    private DemeterGame game;
    private Player player1;
    private Player player2;
    private Board board;


    @BeforeEach
    public void before(){
        this.player1 = new Player("Timo");
        this.player2 = new Player("Leon");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(this.player1);
        players.add(this.player2);
        this.board = new TestingBoard();
        this.board.fillBoard();
        this.board.fillIslands();
        this.game = new DemeterGame(players, this.board,2);
        this.player1.addRessource(Type.FOREST, 10);
        this.player1.addRessource(Type.MOUNTAIN, 10);
        this.player1.addRessource(Type.PASTURE, 10);
        this.player1.addRessource(Type.FIELD, 10);
        this.game.getBoard().getTile(new Position(0, 4)).setBuilding(new Army(3));
        this.game.getBoard().getTile(new Position(0, 4)).setPlayer(this.player1);
        this.game.getBoard().getTile(new Position(0, 5)).setBuilding(new Army(3));
        this.game.getBoard().getTile(new Position(0, 5)).setPlayer(this.player1);
        this.game.getBoard().getTile(new Position(1, 5)).setBuilding(new Port());
        this.game.getBoard().getTile(new Position(1, 5)).setPlayer(this.player1);

    } 
    
    
    @Test 
    public void doNothingTest(){
        this.game.doNothing(this.player1);
        assertTrue(this.game.getNext());
    }
    
    
    @Test 
    public void changeRessourceTest() throws NotEnoughRessourcesException{
        this.game.changeResource(player1, "wood", "sheep");
        assertEquals(this.player1.getRessource().get("wood"), 11);
        assertEquals(this.player1.getRessource().get("sheep"), 7);
    }

    @Test 
    public void playerOnIslandTest() throws WrongPositionException{
        assertTrue(this.game.playerOnIsland(this.player1, new Position(1, 5)));
        assertFalse(this.game.playerOnIsland(this.player1, new Position(1, 1)));
        assertFalse(this.game.playerOnIsland(this.player2, new Position(1, 1)));
    }
 
    @Test 
    public void playerHasPortTest() throws WrongPositionException{
        assertTrue(this.game.playerHasPort(this.player1, new Position(1, 5)));
        assertFalse(this.game.playerHasPort(this.player1, new Position(1, 1)));
    }

    @Test 
    public void playerBuildingOnIslandTest() throws WrongPositionException{
        assertEquals(this.game.playerBuildingOnIsland(player1, new Position(0, 5)), 3);
        assertEquals(this.game.playerBuildingOnIsland(player1, new Position(1,1)), 0);
    }

    @Test 
    public void getBoardTest(){
        assertTrue(this.game.getBoard() instanceof Board);
    }

    @Test 
    public void getNext(){
        assertTrue(this.game.getNext()==true || this.game.getNext()==false);
    }

    @Test 
    public void setNext(){
        this.game.setNext(false);
        assertTrue(this.game.getNext() == false);
    }

    @Test
    public void createPortTest() throws NoTileNearSeaException, WrongPositionException {
        Position pos = new Position(1, 1);
        assertNotNull(pos, "La position près de la mer ne doit pas être nulle");
        List<Position> ile = this.board.getIslandPos(pos);
        assertNotNull(ile, "La liste des positions de l'île ne doit pas être nulle");
        this.board.getTile(ile.get(0)).setPlayer(player1);
        assertTrue(this.board.isNearSea(this.board.getTile(pos)), "La position doit être près de la mer");
        this.game.createPort(player1, pos);
        assertTrue(this.board.getTile(pos).getBuilding() instanceof Port, "Le bâtiment doit être un port" + this.player1.getRessource());
    }

    @Test 
    public void collectRessourcesTest() {
        Position pos = new Position(0, 4);
        int init = this.player1.getRessource().get("wood");
        this.player1.addRessourceBuildings(Type.FOREST, 1);
        this.game.collectRessources();
        assertTrue(this.player1.getRessource().get("wood")> init);
    }


    @Test
    public void addFirstBuildingTest(){
    Position validPosition = new Position(3, 2);
    Building farm = new Farm();
    assertTrue(this.game.addFirstBuilding(this.player1, validPosition, farm));
    assertEquals(this.board.getTile(validPosition).getPlayer(), this.player1);
    assertTrue(this.board.getTile(validPosition).getBuilding() instanceof Farm);

    
    Position invalidPosition = new Position(0, 0); 
    Building farm2 = new Farm();
    assertFalse(this.game.addFirstBuilding(this.player1, invalidPosition, farm2));
    assertNotEquals(this.board.getTile(invalidPosition).getPlayer(), this.player1);

    Position occupiedPosition = new Position(0, 4);
    assertFalse(this.game.addFirstBuilding(this.player1, occupiedPosition, new Farm()));
    assertFalse(this.board.getTile(occupiedPosition).getBuilding() instanceof Farm);
    }

    


}