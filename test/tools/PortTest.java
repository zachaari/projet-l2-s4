package tools;

import java.beans.Transient;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;


public class PortTest {


    private Port port;

    @BeforeEach 
    public void before(){
        this.port = new Port();
    }

    @Test 
    public void isBuiltTest(){
        assertTrue(this.port.isBuilt());
    }

    @Test 
    public void setDimTest(){
        assertTrue(this.port.getDimension() == 0);
        this.port.setDim(5);
        assertTrue(this.port.getDimension() == 5);
    }

    @Test 
    public void getDimensionTest(){
        assertTrue(this.port.getDimension() == 0);
    }
}
