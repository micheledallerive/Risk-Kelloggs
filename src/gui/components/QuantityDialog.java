package gui.components;

import gui.utils.FontUtils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class QuantityDialog extends BaseDialog {

    private int selectedQuantity;

    private int min;
    private int max;
    private String message;

    public QuantityDialog(JFrame parent, String message, int min, int max) {
        super(parent, "", true, 100);
        this.min = min;
        this.max = max;
        this.message = message;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 0;
        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontUtils.addLetterSpacing(label.getFont().deriveFont(Font.PLAIN, 18f), .1f));
        add(label, constraints);

        constraints.gridy = 1;
        constraints.insets = new Insets(20, 0, 30, 0);
        JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(1, min, max, 1));
        spinner.setPreferredSize(new Dimension(200, 35));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        add(spinner, constraints);

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridy = 2;
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            selectedQuantity = (int) spinner.getValue();
            dispose();
        });
        add(okButton, constraints);
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

}