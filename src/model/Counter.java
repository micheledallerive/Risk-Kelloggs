package model;


// TODO: Replace this class with
//       several classes modeling your game
public class Counter {

    private int value;
    
    public Counter(int initialValue) {
        value = initialValue;
    }
    
    public int getValue() {
        return value;
    }
    
    public void increment() {
        value++;
    }
    
    public void decrement() {
        value--;
    }

}
