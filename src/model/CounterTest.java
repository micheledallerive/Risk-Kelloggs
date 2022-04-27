package model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


// TODO: replace this test class with
//       several test classes testing your model
public class CounterTest {
    
    @Test
    public void testInitGet() {
        assertEquals(0, new Counter(0).getValue());
        assertEquals(99, new Counter(99).getValue());
    }
    
    @Test
    public void testInitDecGet() {
        Counter c = new Counter(2);
        c.decrement();
        assertEquals(1, c.getValue());
    }

}
