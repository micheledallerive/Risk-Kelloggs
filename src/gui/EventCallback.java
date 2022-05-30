package gui;

/**
 * This class is used to create a callback for the click event.
 *
 * @author dallem @usi.ch
 */
public interface EventCallback {
    /**
     * Called when the user clicks on the GUI.
     *
     * @param id   The ID of the event in order to make it handle.
     * @param args Extra arguments
     */
    void onEvent(int id, Object... args);
}
