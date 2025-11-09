package tools;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;


public class PlayerTest {
    
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @BeforeEach 
    public void before(){
        this.player1 = new Player("Zachari");
        this.player2 = new Player("Bilal");
        this.player3 = new Player("Billal");
        this.player4 = new Player("Sofiane");
    }

    @Test 
    public void setSecretWeapons(){
        assertFalse(this.player1.haveSecretWeapon());
        this.player1.setSecretWeapon();
        assertTrue(this.player1.haveSecretWeapon());
    }

    @Test 
    public void getName(){
        assertTrue(this.player1.getName() == "Zachari");
        assertTrue(this.player3.getName() == "Billal");
    }

    @Test 
    public void addRessourceBuildings(){
        assertTrue(this.player4.getBuildings().get("wood") == 0);
        this.player4.addRessourceBuildings(Type.FOREST, 5);
        assertTrue(this.player4.getBuildings().get("wood") == 5);
    }

    @Test 
    public void getBuildings(){
        HashMap<String , Integer>buildings = new HashMap<>();
        for(Type type : Type.values()){
            if(type != Type.SEA){
                buildings.put(type.getRessource(), 0);
            }
        }
        assertEquals(this.player1.getBuildings(),buildings);
    }

    @Test 
    public void getRessource(){
        HashMap<String , Integer>ressource = new HashMap<>();
        for(Type type : Type.values()){
            if(type != Type.SEA){
                ressource.put(type.getRessource(), 0);
            }
        }
        assertEquals(this.player1.getRessource(),ressource);
    }

    @Test 
    public void haveSecretWeaponTest(){
        assertTrue(this.player1.haveSecretWeapon()==false);
    }

    @Test 
    public void getWarriorsStockTest(){
        assertTrue(player1.getWarriorsStock() == 30);
    }

    @Test 
    public void addRessourceTest(){
        this.player1.addRessource(Type.FOREST, 5);
        assertTrue(this.player1.getRessource().get("wood") == 5);
    }

    @Test 
    public void removeRessourceTest(){
        this.player1.addRessource(Type.FOREST, 5);
        assertTrue(this.player1.getRessource().get("wood") == 5);
        this.player1.removeRessource(Type.FOREST, 2);
        assertTrue(this.player1.getRessource().get("wood") == 3);
    }

    @Test 
    public void addSecretWeaponTest(){
        this.player1.addSecretWeapon();
        assertTrue(this.player1.haveSecretWeapon());
    }

    @Test 
    public void addWarriorsTest(){
        assertEquals(this.player1.getWarriorsStock(), 30);
        this.player1.addWarriors(60);
        assertEquals(this.player1.getWarriorsStock(),90);
    }

    @Test 
    public void equalsTest(){
        Player n_p = new Player("Zachari");
        assertEquals(this.player1, n_p);
    }

    @Test 
    public void getHasPortTest(){
        assertFalse(this.player1.getHasPort());
    }

    @Test 
    public void setPortTest(){
        this.player1.setPort(true);
        assertTrue(this.player1.getHasPort());
    }

    @Test  
    public void setObjectifTest(){
        Objectif objectif = new Objectif(new TestingBoard());
        this.player1.setObjectif(objectif);
        assertTrue(this.player1.getObjectif() == objectif);
    }

    @Test
    public void getObjectifTest(){
        assertNull(this.player1.getObjectif());
        Objectif objectif = new Objectif(new TestingBoard());
        this.player1.setObjectif(objectif);
        assertTrue(this.player1.getObjectif() == objectif);   
    }
}
