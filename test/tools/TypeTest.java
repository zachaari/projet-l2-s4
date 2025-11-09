package tools;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import tools.exception.WrongRessourceException;

public class TypeTest{

    private Type type1;
    private Type type2;
    private Type type3; 

    @BeforeEach
    public void before(){
        this.type1 = Type.SEA;
        this.type2 = Type.FOREST;
        this.type3 = Type.MOUNTAIN;
    }

    @Test
    public void ressourceTest(){
        assertEquals(this.type2.getRessource(), "wood");
        assertEquals(this.type1.getRessource(), null);
    }    

    @Test
    public void randomTest(){
        assertTrue(Type.random() instanceof Type);
    }

    @Test 
    public void getTypeTest() throws WrongRessourceException{
        assertEquals(Type.getType("wood"), this.type2);
        assertEquals(Type.getType("ore"), this.type3);
        assertThrows(WrongRessourceException.class, ()->Type.getType("bois"));

    }
}