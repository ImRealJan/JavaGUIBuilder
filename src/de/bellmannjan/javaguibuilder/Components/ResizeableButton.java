package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;

public class ResizeableButton extends ResizeableComponent {

    private JButton button = (JButton) resizeableComponent;
    public ResizeableButton(JComponent comp, String name) {
        super(comp, name);
        init();
    }

    //TODO add (addListener) function
    /**
     * Attribute der Komponente werden an Attributeinstellungen übergeben.
     */
    public void getAttributes() {

        attributTableModel.setValueAt("Name", 0, 0);
        attributTableModel.setValueAt(getName(), 0, 1);

        attributTableModel.setValueAt("Text", 1, 0);
        attributTableModel.setValueAt(button.getText(), 1, 1);

        attributTableModel.setValueAt("Size", 2, 0);
        attributTableModel.setValueAt(button.getFont().getSize(), 2, 1);

        //TODO render boolean as checkbox
        attributTableModel.setValueAt("Enabled", 3, 0);
        attributTableModel.setValueAt(button.isEnabled(), 3, 1);
    }

    /**
     * Einträge aus Attributeinstellungen an JButton übergeben
     */
    public void updateAttributes() {

        if(attributTableModel.getValueAt(0,1).toString().equals(""))
            attributTableModel.setValueAt(getName(), 0,1);
        try {
            Boolean.parseBoolean(attributTableModel.getValueAt(3,1).toString());
            Integer.parseInt(attributTableModel.getValueAt(2,1).toString());
        } catch (Exception ex) {
            attributTableModel.setValueAt(button.getFont().getSize(), 2,1);
            attributTableModel.setValueAt(button.isEnabled(), 3,1);
        }

        button.setText(attributTableModel.getValueAt(1,1).toString());
        button.setFont(new Font("Arial", Font.BOLD, Integer.parseInt(attributTableModel.getValueAt(2,1).toString())));
        setName(attributTableModel.getValueAt(0,1).toString());
        button.setEnabled(Boolean.parseBoolean(attributTableModel.getValueAt(3,1).toString()));
    }
}
