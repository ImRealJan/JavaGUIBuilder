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
        GUI.getAttributPanel().getTableModel().setValueAt("Name", 0, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(getName(), 0, 1);

        GUI.getAttributPanel().getTableModel().setValueAt("Text", 1, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(textArea.getText(), 1, 1);
    }

    /**
     * Einträge aus Attributeinstellungen an JButton übergeben
     */
    public void updateAttributes() {

        if(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString().equals(""))
            GUI.getAttributPanel().getTableModel().setValueAt(getName(), 0,1);

        textArea.setText(GUI.getAttributPanel().getTableModel().getValueAt(1,1).toString());

        setName(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString());

    }
}
