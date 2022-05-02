package tui;

/**
 * Defines some static utility methods for printing on the console.
 */
public class Utils {

    /**
     * Auxiliary function to print easily an object.
     * @param message the message to print.
     */
    public static void print(Object message) {
        System.out.println(message);
    }

    /**
     * Auxiliary function to print multiple objects each on a new line.
     * @param messages the messages to print one per line.
     */
    public static void print(Object... messages) {
        for (Object message : messages) {
            print(message);
        }
    }

    /**
     * Auxiliary function to print a multiple choice menu.
     */
    public static void printOptions(String... options) {
        print("Enter the number of what you want to do next:");
        for (int i = 0; i < options.length; i++) {
            print("(" + (i+1) + ") " + options[i]);
        }
    }
}
