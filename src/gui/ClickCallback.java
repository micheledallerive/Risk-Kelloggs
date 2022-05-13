package gui;

/**
 * This class is used to create a callback for the click event.
 */
public interface ClickCallback {
    /**
     * Called when the user clicks on the GUI.
     * @param id The ID of the event in order to make it handle.
     */
    public void onClick(int id);
}
