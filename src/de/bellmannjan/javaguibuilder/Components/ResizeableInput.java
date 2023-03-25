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
        GUI.getAttributPanel().getTableModel().setValueAt("Name", 0, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(getName(), 0, 1);

        GUI.getAttributPanel().getTableModel().setValueAt("Text", 1, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(textField.getText(), 1, 1);
    }

    /**
     * Einträge aus Attributeinstellungen an JButton übergeben
     */
    public void updateAttributes() {

        if(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString().equals(""))
            GUI.getAttributPanel().getTableModel().setValueAt(getName(), 0,1);

        textField.setText(GUI.getAttributPanel().getTableModel().getValueAt(1,1).toString());

        setName(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString());

    }
}
