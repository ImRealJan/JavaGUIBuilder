package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;

public class ResizeableTextArea extends ResizeableComponent {

    private JTextArea textArea;

    public ResizeableTextArea(JComponent comp, String name) {
        super(comp, name);

        JViewport jViewport = (JViewport)comp.getComponent(0);
        resizeableComponent = (JComponent) jViewport.getView();
        textArea = (JTextArea) resizeableComponent;
        textArea.setEditable(false);
        textArea.setBackground(Color.white);
        init();
    }

    /**
     * Attribute der Komponente werden an Attributeinstellungen übergeben.
     */
    public void getAttributes() {
        attributTableModel.setValueAt("Name", 0, 0);
        attributTableModel.setValueAt(getName(), 0, 1);

        attributTableModel.setValueAt("Text", 1, 0);
        attributTableModel.setValueAt(textArea.getText(), 1, 1);
    }

    /**
     * Einträge aus Attributeinstellungen an JButton übergeben
     */
    public void updateAttributes() {

        if(attributTableModel.getValueAt(0,1).toString().equals(""))
            attributTableModel.setValueAt(getName(), 0,1);

        textArea.setText(attributTableModel.getValueAt(1,1).toString());

        setName(attributTableModel.getValueAt(0,1).toString());

    }
}
