package tools;

import java.security.spec.PSSParameterSpec;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class PositionTest {
    
    private Position pos1;
    private Position pos2;
    private Position pos3;
    private List<Position> neighboorPos1;
    private List<Position> neighboorPos2;


    @BeforeEach
    public void before(){
        this.pos1 = new Position(4,6,false);
        this.pos2 = new Position(8,5,false);
        this.pos3 = new Position(0,0,false);
        this.neighboorPos1 = new ArrayList<Position>();
        this.neighboorPos1.add(new Position(3,6,false));
        this.neighboorPos1.add(new Position(5,6,false));
        this.neighboorPos1.add(new Position(4,5,false));
        this.neighboorPos1.add(new Position(4,7,false));
        this.neighboorPos2 = new ArrayList<Position>();
        this.neighboorPos2.add(new Position(0,1,false));
        this.neighboorPos2.add(new Position(1,0,false));
    }

    @Test 
    public void getXTest(){
        assertEquals(4, this.pos1.getX());
        assertEquals(8, this.pos2.getX());
    }
    
    @Test 
    public void getYTest(){
        assertEquals(6, this.pos1.getY());
        assertEquals(5, this.pos2.getY());
    }

    @Test 
    public void getRandomNeighboorPositionTest(){
        Position neighboorPos1 = this.pos1.getRandomNeighboorPosition(7,6);
        assertTrue(this.neighboorPos1.contains(neighboorPos1), "pos: "+neighboorPos1);

        Position neighboorPos2 = this.pos3.getRandomNeighboorPosition(7, 6);
        assertTrue(this.neighboorPos2.contains(neighboorPos2), "pos: "+neighboorPos2);

    }

    @Test 
    public void equalsTest(){
        Position pos2bis = new Position(8,5,false);
        assertFalse(this.pos1.equals(this.pos2));
        assertTrue(this.pos2.equals(pos2bis));
    }

    @Test 
    public void getAllNeighboorPosTest(){
        List<Position> list = this.pos3.getAllNeighboorPos(10, 10);
        Position pos1 = new Position(1, 0);
        Position pos2 = new Position(0, 1);
        List<Position> exceptedList = new ArrayList<Position>();
        exceptedList.add(pos1);
        exceptedList.add(pos2);
        for(Position pos : exceptedList){
            assertTrue(list.contains(pos));
        }      
        for(Position pos : list){
            System.out.println(pos);
            assertTrue(exceptedList.contains(pos));
        }
    }
}
