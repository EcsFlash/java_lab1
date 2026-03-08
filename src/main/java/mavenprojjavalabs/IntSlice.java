package mavenprojjavalabs;

//я не стал самостоятельно придумывать велосипед и решил просто реализовать слайс из golang
//ссылка на исходный код гошного слайса: https://go.dev/src/runtime/slice.go
// https://github.com/golang/go/blob/master/src/slices/slices.go отсюда я не все функции буду реализовывать, но думаю что этого будет достаточно
/**
 * IntSlice - контейнер, хранит тип int.
 **/

public class IntSlice {

    private int[] elements;  
    private int length;      
    /**
     * Create empty slice
     * IntSlice() -> []
     */
    public IntSlice() {
        this(0, 0);
    }

    /**
     * Create slice with concrete length, all elements are 0.
     * IntSlice(6) -> [0,0,0,0,0,0]
     */
    public IntSlice(int len) {
        if (len < 0) {
            throw new IllegalArgumentException("len cannot be negative");
        }
        elements = new int[len];
        length = len;
    }

    /**
     * Create a slice with length and capacity. 
     * IntSlice(6, 9) -> [0,0,0,0,0,0]
     */
    public IntSlice(int len, int cap) {
        if (len < 0 || cap < len) {
            throw new IllegalArgumentException("invalid len or cap");
        }
        elements = new int[cap];
        length = len;
    }

    /**
     * Create slice from multiple values
     * IntSlice(2,5,6,3,5,6) -> [2,5,6,3,5,6]
     */
    public IntSlice(int... values) {
        elements = new int[values.length];
        System.arraycopy(values, 0, elements, 0, values.length);
        length = values.length;
    }

    

    /**
     * Returns current length of slice(count of elements)
     */
    public int len() {
        return length;
    }

    /**
     * Returns current capacity of slice(max count of elements at current moment)
     */
    public int cap() {
        return elements.length;
    }

    /**
     * Appends value to end of slice, grows array if necessary.
     */
    public void append(int value) {
        grow(length + 1);
        elements[length++] = value;
    }

    /**
     * Appends multiple values to end of slice, keeping order of incoming values.
     * 
     */
    public void append(int... values) {
        if (values.length == 0) return;
        grow(length + values.length);
        System.arraycopy(values, 0, elements, length, values.length);
        length += values.length;
    }

    /**
     * Returns element by its index
     */
    public int get(int index) {
        checkIndex(index);
        return elements[index];
    }

    /**
     * Sets value by index
     */
    public void set(int index, int value) {
        checkIndex(index);
        elements[index] = value;
    }

    /**
     * Remove element by index
     * Returns removed element
     */
    public int remove(int index) {
        checkIndex(index);
        int removed = elements[index];
        System.arraycopy(elements, index + 1, elements, index, length - index - 1);
        length--;
        return removed;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException(
                "Index: " + index + ", Length: " + length);
        }
    }

    private void grow(int minCap) {
        if (minCap <= elements.length) {
            return;
        }

        int oldCap = elements.length;
        int newCap;

       
        int doubleCap = (oldCap > Integer.MAX_VALUE / 2) ? Integer.MAX_VALUE : oldCap * 2;

        if (minCap > doubleCap) {
            newCap = minCap;
        } else if (oldCap < 1024) {
            newCap = doubleCap;
        } else {
            
            newCap = oldCap;
            while (newCap < minCap) {
                int add = newCap / 4;
                if (newCap > Integer.MAX_VALUE - add) {
                    newCap = Integer.MAX_VALUE;
                    break;
                }
                newCap += add;
            }
        }

        
        int[] newElements = new int[newCap];
        System.arraycopy(elements, 0, newElements, 0, length);
        elements = newElements;
    }

    @Override
    public String toString() {
        if (length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            sb.append(elements[i]);
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    
}
