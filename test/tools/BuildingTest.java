package tools;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;


public class BuildingTest {
    private Building building;


    @BeforeEach
    public void before(){
        this.building = new Army(3);
    } 


    @Test
    public void setDimTest(){
        this.building.setDim(4);
        assertTrue(this.building.getDimension() == 4);
    }  


    @Test
    public void getDimensionTest(){
        assertTrue(this.building.getDimension() == 3);
    }  


    @Test
    public void getRessourcesTest(){
        assertTrue(this.building.getRessources() == 1);
    }  


    @Test
    public void getCostRessourcesTest(){
        HashMap<String, Integer> cost = new HashMap<>();
        cost.put("wood", 1);
        cost.put("Wheat",1);
        cost.put("sheep",1);

        assertEquals(cost, this.building.getCostRessources());
    }   


    
}  