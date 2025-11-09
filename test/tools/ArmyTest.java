package tools;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;

public class ArmyTest {
    
    private Army army;

    @BeforeEach
    public void before(){
        this.army = new Army(2);
    }

    @Test 
    public void setDimTest(){
        this.army.setDim(4);
        assertTrue(this.army.getDimension()==4);
    }

    @Test 
    public void getDimensionTest(){
        assertTrue(this.army.getDimension()==2);
    }

    @Test 
    public void getRessourcesTest(){
        assertTrue(this.army.getRessources()==1);
    }


    @Test
    public void getCostRessourcesTest(){
        HashMap<String, Integer> cost = new HashMap<>();
        cost.put("wood",1);
        cost.put("Wheat",1);
        cost.put("sheep",1);

        assertEquals(cost, this.army.getCostRessources());
    }  


    @Test
    public void toStringTest(){
        assertEquals("Armée: 1 🪵, 1 🐑, 1 🌾", this.army.toString());
    }  

}
