package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;

public class ResizableButton extends ResizableComponent {
    public ResizableButton(JComponent comp, String name, boolean hasScrollPane) {
        super(comp, name, hasScrollPane);
    }

    JButton button = (JButton) jComponent;

    //TODO add (addListener) function
    public void getAttributes() {

        GUI.getAttributPanel().getTableModel().setValueAt("Name", 0, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(getComponentName(), 0, 1);

        GUI.getAttributPanel().getTableModel().setValueAt("Text", 1, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(button.getText(), 1, 1);

        GUI.getAttributPanel().getTableModel().setValueAt("Size", 2, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(button.getFont().getSize(), 2, 1);

        //TODO render boolean as checkbox
        GUI.getAttributPanel().getTableModel().insertRow(3,new Object[]{"Enabled", Boolean.TRUE});
    }

    @Override
    public void updateAttributes() {

        if(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString().equals(""))
            GUI.getAttributPanel().getTableModel().setValueAt(getComponentName(), 0,1);
        try {
            Boolean.parseBoolean(GUI.getAttributPanel().getTableModel().getValueAt(3,1).toString());
            Integer.parseInt(GUI.getAttributPanel().getTableModel().getValueAt(2,1).toString());
        } catch (Exception ex) {
            GUI.getAttributPanel().getTableModel().setValueAt(button.getFont().getSize(), 2,1);
            GUI.getAttributPanel().getTableModel().setValueAt(button.isEnabled(), 3,1);
        }

        button.setText(GUI.getAttributPanel().getTableModel().getValueAt(1,1).toString());
        button.setFont(new Font("Arial", Font.BOLD, Integer.parseInt(GUI.getAttributPanel().getTableModel().getValueAt(2,1).toString())));
        setName(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString());
        button.setEnabled(Boolean.parseBoolean(GUI.getAttributPanel().getTableModel().getValueAt(3,1).toString()));

        //TODO update List maybe as function
    }
}
