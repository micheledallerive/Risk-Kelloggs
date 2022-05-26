package gui.components;

import gui.EventCallback;

import javax.swing.*;

public class MessageDialog extends ImageBackgroundDialog {

    JFrame parent;
    EventCallback callback;

    public MessageDialog(JFrame parent, String title, boolean modal) {
        super(parent, title, modal, "src/gui/assets/images/dialog_texture.jpg", 1f);
        this.parent = parent;
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void addDisposeListener(EventCallback callback) {
        this.callback = callback;
    }

    public void triggerDisposeListener() {
        if(callback!=null) {
            callback.onEvent(0);
        }
    }
}
