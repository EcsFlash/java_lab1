package mavenprojjavalabs;

//я не стал самостоятельно придумывать велосипед и решил просто реализовать слайс из golang
//ссылка на исходный код гошного слайса: https://go.dev/src/runtime/slice.go

 /**
 * IntSlice - контейнер, хранит тип int.
 **/

public class IntSlice {
	/**
	 * Array that keeps elements
	 */
    private int[] elements;  
    /**
     *  Current length of slice
     */
    private int length;      
    /**
     * Create empty slice
     * @return [] (empty IntSlice)
     */
    public IntSlice() {
        this(0, 0);
    }

    /**
     * Create slice with start length to minimize future allocations, all elements are 0.
     * @param cap start capacity of slice
     * @throws IllegalArgumentException if input length less than 0
     * @return IntSlice
     * @see IntSlice(6) -> [0,0,0,0,0,0]
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
     * @param len start length 
     * @param cap start capacity
     * @see IntSlice(6, 9) -> [0,0,0,0,0,0], but can keep 3 more elemnts without growing
     * @throws IllegalArgumentException if input length or capacity less than 0
     * @return IntSlice
     */
    public IntSlice(int len, int cap) {
        if (len < 0 || cap < len) {
            throw new IllegalArgumentException("invalid len or cap");
        }
        elements = new int[cap];
        length = len;
    }

        

    /**
     * @return int value - current length of slice(count of elements)
     */
    public int len() {
        return length;
    }

    /**
     * @return int value - current capacity of slice(max count of elements at current moment)
     */
    public int cap() {
        return elements.length;
    }

    /**
     * Appends value to end of slice, grows array if necessary.
     * @param value - value that should appends to end of slice
     */
    public void append(int value) {
        grow(length + 1);
        elements[length++] = value;
    }

    /**
     * Appends multiple values to end of slice, keeping order of incoming values.
     * @param values - values that should appends to end of slice
     */
    public void append(int... values) {
        if (values.length == 0) return;
        grow(length + values.length);
        System.arraycopy(values, 0, elements, length, values.length);
        length += values.length;
    }

    /**
     * Get element by its index
     * @param index - index of element from range[0, length-1]
     * @return int value
     */
    public int get(int index) {
        checkIndex(index);
        return elements[index];
    }

    /**
     * Sets value by index
     * @param value - value to set
     * @param index - where to set
     */
    public void set(int index, int value) {
        checkIndex(index);
        elements[index] = value;
    }

    /**
     * Remove element by index
     * @param index - index where to remove
     * @return int value - removed element
     */
    public int remove(int index) {
        checkIndex(index);
        int removed = elements[index];
        System.arraycopy(elements, index + 1, elements, index, length - index - 1);
        length--;
        return removed;
    }

    /**
     * Checks index
     * @param index  - input index of element
     *@throws IndexOutOfBoundsException if index out of slice range
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException(
                "Index: " + index + ", Length: " + length);
        }
    }
    /**
     * Grows slice to new minimum capacity
     * @param minCap - new minimum capacity
     */
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
    /**
     * Get string representation of slice
     * @return string value - string like [element1, element2, ... elementN]
     */
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
    /**
     * Function to check, equal two slices or no
     * @return boolean value -  true if two slices have same length, elements and their order.
     */
	@Override
	public boolean equals(Object arg0) {
		if(arg0 != null) {
			if(arg0 instanceof IntSlice) {
				boolean result = true;
				IntSlice s = (IntSlice)arg0;
				if(this.length == s.length) {
					for(int i = 0; i < this.length && result; i++) {
						if(this.elements[i] != s.elements[i]) {
							result = false;
						}
					}
					return result;
				}
			}
			else {
				throw new IllegalArgumentException("comparing object must be IntSlice type");
			}
			
		} else {
			throw new IllegalArgumentException("comparing object cannot be null");
		}
		return false; //?линтер сказал надо
	}

    
}
