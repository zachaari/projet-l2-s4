package tools;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import tools.exception.NotEnoughRessourcesException;


public class DiceTest{

    private Dice dice;


    @BeforeEach
    public void before(){
        this.dice = new Dice();
    }   



    @Test
    public void nbLancerTestWithDimensionLessthan1() throws NotEnoughRessourcesException{
        assertThrows(NotEnoughRessourcesException.class, () ->{dice.nbLancer(0,false);
        });
    } 



    @Test
    public void nbLancerTestWithDimension1to3() throws NotEnoughRessourcesException{
        assertEquals(1,dice.nbLancer(3,false));
    }   


    @Test
    public void nbLancerTestWithDimension4to7() throws NotEnoughRessourcesException{
        assertEquals(2,dice.nbLancer(5,false));
    }  


    @Test
    public void nbLancerTestWithDimensionMorethan7() throws NotEnoughRessourcesException{
        assertEquals(3,dice.nbLancer(9,false));
    }  



    @Test
    public void nbLancerTestWithSecretWeapon() throws NotEnoughRessourcesException{
        assertEquals(3,dice.nbLancer(5,true));
    }  


    @Test
    public void nbLancerTestInvalidDimension() throws NotEnoughRessourcesException{
        assertThrows(NotEnoughRessourcesException.class, () -> {dice.nbLancer(0,false);
        });
    }  



    @Test
    public void lancerTest(){
        assertTrue(dice.lancer(3) >= 3 && dice.lancer(3) <= 18);
    }  


    @Test
    public void lancerTestSingleRoll(){
        assertTrue(dice.lancer(1) >= 1 && dice.lancer(1) <= 6);
    }  





} 