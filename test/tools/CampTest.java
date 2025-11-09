package tools;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;

public class CampTest {
    
    private Camp camp;

    @BeforeEach 
    public void before(){
        this.camp = new Camp(15);
    }

    @Test 
    public void getRessourcesTest(){
        assertEquals(this.camp.getRessources(),2);
    }

    @Test 
    public void getDimensionTest(){
        assertEquals(this.camp.getDimension(), 15);
    }

    @Test
    public void getCostRessourcesTest(){
        HashMap<String, Integer> cost = new HashMap<>();
        cost.put("wood",2);
        cost.put("ore",3);

        assertEquals(cost, this.camp.getCostRessources());
    }

    @Test
    public void toStringTest(){
        assertEquals("Camp: 2 🪵, 3 💎", this.camp.toString());
    }  

    @Test 
    public void instanceTest(){
        assertTrue(this.camp instanceof Camp);
    }

}
