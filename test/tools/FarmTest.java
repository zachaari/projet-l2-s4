package tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.*;

public class FarmTest {
    
    private Farm farm;

    @BeforeEach
    public void before(){
        this.farm = new Farm();
    }

    @Test 
    public void getRessourcesTest(){
        assertTrue(this.farm.getRessources() == 1);
    }

    @Test 
    public void getDimensionTest(){
        assertTrue(this.farm.getDimension()==1);
    }

    @Test
    public void getCostRessourcesTest(){
        HashMap<String, Integer> cost = new HashMap<>();
        cost.put("wood", 1);
        cost.put("ore",1);

        assertEquals(cost, this.farm.getCostRessources());
    }  


    @Test
    public void toStringTest(){
        assertEquals("Ferme: 1 🪵, 1 💎", this.farm.toString());
    }
}
