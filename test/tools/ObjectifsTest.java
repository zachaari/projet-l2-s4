package tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.*;


public class ObjectifsTest {
    private Objectif objectif1;     

    @BeforeEach 
    public void before(){
        this.objectif1 = new Objectif("Conquérir","Conquérir 12 tuiles", 12, 0 );
    }


    @Test 
    public void getTypeTest(){
        assertTrue(this.objectif1.getType() == "Conquérir");
    }

    @Test 
    public void getRequiredTilestest(){
        assertTrue(this.objectif1.getRequiredTiles() == 12);
    }

    @Test 
    public void getDescriptionTest(){
        assertTrue(this.objectif1.getDescription() == "Conquérir 12 tuiles");
    }


}
