package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;

public class ResizeableText extends ResizeableComponent {

    private JLabel label = (JLabel)resizeableComponent;

    public ResizeableText(JComponent comp, String name) {
        super(comp, name);
        init();
    }

    /**
     * Attribute der Komponente werden an Attributeinstellungen übergeben.
     */
    public void getAttributes() {

        attributTableModel.setValueAt("Name", 0, 0);
        attributTableModel.setValueAt(getName(), 0, 1);

        attributTableModel.setValueAt("Text", 1, 0);
        attributTableModel.setValueAt(label.getText(), 1, 1);

        attributTableModel.setValueAt("Size", 2, 0);
        attributTableModel.setValueAt(label.getFont().getSize(), 2, 1);
    }

    /**
     * Einträge aus Attributeinstellungen an JButton übergeben
     */
    public void updateAttributes() {

        if(attributTableModel.getValueAt(0,1).toString().equals(""))
            attributTableModel.setValueAt(getName(), 0,1);
        try {
            Integer.parseInt(attributTableModel.getValueAt(2,1).toString());
        } catch (Exception ex) {
            attributTableModel.setValueAt(label.getFont().getSize(), 2,1);
        }

        label.setText(attributTableModel.getValueAt(1,1).toString());
        label.setFont(new Font("Arial", Font.BOLD, Integer.parseInt(attributTableModel.getValueAt(2,1).toString())));
        setName(attributTableModel.getValueAt(0,1).toString());
    }
}
