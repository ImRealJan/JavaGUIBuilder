package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;

public class ResizeableInput extends ResizeableComponent {

    private JTextField textField = (JTextField) resizeableComponent;
    public ResizeableInput(JComponent comp, String name) {
        super(comp, name);
        textField.setEditable(false);
        textField.setBackground(Color.white);
        init();
    }


    /**
     * Attribute der Komponente werden an Attributeinstellungen übergeben.
     */
    public void getAttributes() {
        attributTableModel.setValueAt("Name", 0, 0);
        attributTableModel.setValueAt(getName(), 0, 1);

        attributTableModel.setValueAt("Text", 1, 0);
        attributTableModel.setValueAt(textField.getText(), 1, 1);
    }

    /**
     * Einträge aus Attributeinstellungen an JButton übergeben
     */
    public void updateAttributes() {

        if(attributTableModel.getValueAt(0,1).toString().equals(""))
            attributTableModel.setValueAt(getName(), 0,1);

        textField.setText(attributTableModel.getValueAt(1,1).toString());

        setName(attributTableModel.getValueAt(0,1).toString());

    }
}
