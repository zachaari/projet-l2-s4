package game;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import tools.*;
import tools.exception.*;

import java.util.*;

public class DemeterGameTest {

    private DemeterGame game;
    private Player player1, player2;
    private Board board;
    private Position pos1, pos2, pos3, pos4;
    private Tile tile1, tile2, tile3, tile4;

    @BeforeEach
    public void before() {
    this.player1 = new Player("Player1");
    this.player2 = new Player("Player2");
    
    ArrayList<Player> players = new ArrayList<>();
    players.add(this.player1);
    players.add(this.player2);

    this.board = new TestingBoard();
    this.board.fillBoard();
    this.board.fillIslands();
    
    this.game = new DemeterGame(players, this.board, 3);

    this.pos1 = new Position(0, 4, false);
    this.pos2 = new Position(0, 5, false);
    this.pos3 = new Position(1, 5, false);
    this.pos4 = new Position(1, 4, false);
    
    this.board.getTile(this.pos2).setBuilding(new Farm());
    this.board.getTile(this.pos2).setPlayer(this.player1);
    
    this.board.getTile(this.pos3).setBuilding(new Port());
    this.board.getTile(this.pos3).setPlayer(this.player1);


        
    }

    @Test
    public void createFarmTestSuccess() {
        this.player1.addRessource(Type.FOREST, 1);
        this.player1.addRessource(Type.MOUNTAIN, 1);
        this.game.createFarm(this.player1, this.pos1);
        assertTrue(this.board.getTile(this.pos1).getBuilding() instanceof Farm);
    }

    @Test
    public void createFarmTestNotEnoughResources() {
        this.player1.addRessource(Type.FOREST, 0);
        this.player1.addRessource(Type.MOUNTAIN, 0);
        this.game.createFarm(this.player1, this.pos1);
        assertNull(this.board.getTile(this.pos1).getBuilding());
    }


    @Test
    public void replaceFarmTestSuccess() {
        this.player1.addRessource(Type.FOREST, 2);
        this.player1.addRessource(Type.PASTURE, 1);
        this.player1.addRessource(Type.FIELD, 1);
        this.game.createFarm(this.player1, this.pos1);
        this.game.replaceFarm(this.player1, this.pos2);
        assertTrue(this.board.getTile(this.pos2).getBuilding() instanceof Exploitation);
    }


    @Test
    public void replaceFarmTestNotEnoughResources() {
        this.player1.addRessource(Type.FOREST, 1);
        this.player1.addRessource(Type.MOUNTAIN, 1);
        this.game.createFarm(this.player1, this.pos1);
        assertTrue(this.board.getTile(this.pos1).getBuilding() instanceof Farm, "On devrait avoir une Farm créée ici");
        this.player1.getRessource().put("wheat", 0);
        this.player1.getRessource().put("sheep", 0);
        this.game.replaceFarm(this.player1, this.pos1);
        assertTrue(this.board.getTile(this.pos1).getBuilding() instanceof Farm);
    }

    @Test
    public void buySecretWeaponTestSuccess() {
        assertFalse(this.player1.haveSecretWeapon());
        this.player1.addRessource(Type.MOUNTAIN, 1);
        this.player1.addRessource(Type.FOREST, 1);
        this.player1.addRessource(Type.PASTURE, 1);
        this.game.buySecretWeapon(this.player1);
        assertTrue(this.game.getNbThieves()==2);
        assertTrue(this.player1.haveSecretWeapon(), this.player1.haveSecretWeapon()+"");
    }

    @Test
    public void buySecretWeaponTestNotEnoughResources() {
        this.player1.addRessource(Type.MOUNTAIN, 0);
        this.player1.addRessource(Type.FOREST, 0);
        this.player1.addRessource(Type.PASTURE, 0);
        this.game.buySecretWeapon(this.player1);
        assertFalse(this.player1.haveSecretWeapon());
    }

    @Test
    public void buySecretWeaponTestAlreadyHaveWeapon() {
        this.player1.setSecretWeapon();
        this.game.buySecretWeapon(this.player1);
        assertTrue(this.player1.haveSecretWeapon());
    }

    @Test
    public void changeResourceWithPortTestSuccess() {
        this.player1.addRessource(Type.FOREST, 2);
        this.game.createPort(this.player1,this.pos1);
        this.game.changeResourceWithPort(this.player1, this.pos1, "ore", "wood");
        assertEquals(0, this.player1.getRessource().get("wood"));
        assertEquals(1, this.player1.getRessource().get("ore"));
    }

    @Test
    public void changeResourceWithPortTestNotEnoughResources() {
        this.player1.addRessource(Type.FOREST, 1);
        this.game.changeResourceWithPort(this.player1, this.pos1, "ore", "wood");
        assertEquals(1, this.player1.getRessource().get("wood"));
        assertEquals(0, this.player1.getRessource().get("ore"));
    }

    @Test
    public void playThiefTestSuccess() {
        this.player1.addRessource(Type.FOREST, 1);
        this.player1.addRessource(Type.MOUNTAIN, 1);
        this.player1.addRessource(Type.PASTURE, 1);
        this.player1.setSecretWeapon();
        this.player2.addRessource(Type.FOREST, 2);
        this.player2.addRessource(Type.MOUNTAIN, 1);
        this.game.playThief(this.player1, "wood");
        assertEquals(3, this.player1.getRessource().get("wood"));
        assertEquals(0, this.player2.getRessource().get("wood"));
    }

    @Test
    public void playThiefTestNoSecretWeapon() {
        this.game.playThief(this.player1, "wood");
        assertEquals(0, this.player1.getRessource().get("wood"));
    }

    @Test
    public void playerOnTileTest() throws WrongPositionException {
        Position pos = new Position(3, 4);
         this.board.getTile(pos).setPlayer(this.player1);
        assertTrue(this.game.playerOnTile(this.player1, pos), "Le joueur devrait posséder la tuile à cette position.");
    }  


    @Test
    public void getNbThievesTest(){
        assertTrue(this.game.getNbThieves() == 3, "Le nombre de voleurs devrait être de 3.");
    }  


    @Test
    public void calculatePointsTest() {
        assertEquals(0, this.game.calculatePoints(player2));
        this.board.getTile(new Position(1, 1)).setPlayer(player2);  
        this.board.getTile(new Position(0, 4)).setPlayer(player2);  
        this.board.getTile(new Position(1, 1)).setBuilding(new Farm());  
        this.board.getTile(new Position(0, 4)).setBuilding(new Farm());  
        assertEquals(3, this.game.calculatePoints(player2));
    }
 

}
