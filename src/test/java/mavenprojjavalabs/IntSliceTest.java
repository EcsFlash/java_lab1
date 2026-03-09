package mavenprojjavalabs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IntSliceTest {

	@Test
    void testDefaultConstructor() {
        IntSlice s = new IntSlice();
        assertEquals(0, s.len());
        assertEquals(0, s.cap());
        assertEquals("[]", s.toString());
    }

    @Test
    void testLenConstructor() {
        IntSlice s = new IntSlice(5);
        assertEquals(5, s.len());
        assertEquals(5, s.cap());
        assertEquals("[0, 0, 0, 0, 0]", s.toString());
    }

    @Test
    void testLenConstructorNegative() {
        assertThrows(IllegalArgumentException.class, () -> new IntSlice(-1));
    }

    @Test
    void testLenCapConstructor() {
        IntSlice s = new IntSlice(3, 9);
        assertEquals(3, s.len());
        assertEquals(9, s.cap());
        assertEquals("[0, 0, 0]", s.toString());
    }

    @Test
    void testLenCapConstructorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new IntSlice(5, 3));
        assertThrows(IllegalArgumentException.class, () -> new IntSlice(-1, 5));
        assertThrows(IllegalArgumentException.class, () -> new IntSlice(0, -1));
    }

    @Test
    void testLenAndCapMethods() {
        IntSlice s = new IntSlice(2, 10);
        s.append(99);
        assertEquals(3, s.len());
        assertEquals(10, s.cap());
    }

    @Test
    void testAppendSingle() {
        IntSlice s = new IntSlice();
        s.append(42);
        assertEquals(1, s.len());
        assertEquals(1, s.cap());
        assertEquals("[42]", s.toString());
    }

    @Test
    void testAppendVararg() {
        IntSlice s = new IntSlice();
        s.append(3, 4, 5);
        assertEquals(3, s.len());
        assertEquals(3, s.cap());
        assertEquals("[3, 4, 5]", s.toString());
    }

    @Test
    void testAppendEmptyVararg() {
        IntSlice s = new IntSlice(1, 2);
        s.append(); 
        assertEquals(1, s.len());
        assertEquals(2, s.cap());
    }

    @Test
    void testAppendNoGrowNeeded() {
        IntSlice s = new IntSlice(3, 10);
        s.append(4, 5);
        assertEquals(5, s.len());
        assertEquals(10, s.cap());
    }

    @Test
    void testAppendGrowMinCapGreaterThanDouble() {
        IntSlice s = new IntSlice(5); 
        s.append(1, 2, 3, 4, 5, 6); 
        assertEquals(11, s.len());
        assertEquals(11, s.cap());
    }

    @Test
    void testAppendGrowOldCap1024Plus() {
        IntSlice s = new IntSlice(1024); 
        s.append(42); 
        assertEquals(1025, s.len());
        assertEquals(1280, s.cap()); 
    }

    @Test
    void testGetValid() {
        IntSlice s = new IntSlice();
        s.append(10, 20, 30);
        assertEquals(20, s.get(1));
    }

    @Test
    void testGetOutOfBounds() {
        IntSlice s = new IntSlice(2);
        assertThrows(IndexOutOfBoundsException.class, () -> s.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> s.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> s.get(100));
    }

    @Test
    void testSetValid() {
        IntSlice s = new IntSlice();
        s.append(10, 20, 30);
        s.set(1, 99);
        
        assertEquals(99, s.get(1));
        assertEquals("[10, 99, 30]", s.toString());
    }

    @Test
    void testSetOutOfBounds() {
        IntSlice s = new IntSlice();
        assertThrows(IndexOutOfBoundsException.class, () -> s.set(0, 1));
    }

    @Test
    void testRemoveValid() {
        IntSlice s = new IntSlice();
        s.append(10, 20, 30, 40);
        int removed = s.remove(1);
        assertEquals(20, removed);
        assertEquals(3, s.len());
        assertEquals(4, s.cap()); 
        assertEquals("[10, 30, 40]", s.toString());

        
        removed = s.remove(2);
        assertEquals(40, removed);
        assertEquals(2, s.len());
        assertEquals(4, s.cap()); 
        assertEquals("[10, 30]", s.toString());
    }

    @Test
    void testRemoveOutOfBounds() {
        IntSlice s = new IntSlice(1);
        assertThrows(IndexOutOfBoundsException.class, () -> s.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> s.remove(1));
    }

    @Test
    void testToString() {
    	IntSlice sliceEmpty = new IntSlice();
    	IntSlice sliceWithLen = new IntSlice(3);
    	IntSlice sliceWithElems = new IntSlice();
    	sliceWithElems.append(1,2,3);
        assertEquals("[]", sliceEmpty.toString());
        assertEquals("[0, 0, 0]", sliceWithLen.toString());
        assertEquals("[1, 2, 3]", sliceWithElems.toString());
    }

    @Test
    void testEqualsNull() {
        IntSlice s = new IntSlice();
        assertThrows(IllegalArgumentException.class, () -> s.equals(null));
    }

    @Test
    void testEqualsWrongType() {
        IntSlice s = new IntSlice();
        assertThrows(IllegalArgumentException.class, () -> s.equals("not a slice"));
        assertThrows(IllegalArgumentException.class, () -> s.equals(123));
    }

    @Test
    void testEqualsSameEmpty() {
        IntSlice s1 = new IntSlice();
        IntSlice s2 = new IntSlice();
        assertTrue(s1.equals(s2));
    }

  
    @Test
    void testEqualsSameLength() {
        IntSlice s1 = new IntSlice(1, 2);
        IntSlice s2 = new IntSlice(1, 99);
        assertTrue(s1.equals(s2));

     
        IntSlice s3 = new IntSlice();
        s3.append(1,2,3,5,6);
        IntSlice s4 = new IntSlice();
        s4.append(1,2,3,5,6);
        assertTrue(s3.equals(s4));
    }
}
