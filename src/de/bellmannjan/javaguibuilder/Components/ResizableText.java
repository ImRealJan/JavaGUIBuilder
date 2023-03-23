package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;

public class ResizableText extends ResizableComponent {

    JLabel label = (JLabel)jComponent;

    public ResizableText(JComponent comp, String name, boolean hasScrollPane) {
        super(comp, name, hasScrollPane);
    }

    public void getAttributes() {

        GUI.getAttributPanel().getTableModel().setValueAt("Name", 0, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(getComponentName(), 0, 1);

        GUI.getAttributPanel().getTableModel().setValueAt("Text", 1, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(label.getText(), 1, 1);

        GUI.getAttributPanel().getTableModel().setValueAt("Size", 2, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(label.getFont().getSize(), 2, 1);
    }

    @Override
    public void updateAttributes() {

        if(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString().equals(""))
            GUI.getAttributPanel().getTableModel().setValueAt(getComponentName(), 0,1);
        try {
            Integer.parseInt(GUI.getAttributPanel().getTableModel().getValueAt(2,1).toString());
        } catch (Exception ex) {
            GUI.getAttributPanel().getTableModel().setValueAt(label.getFont().getSize(), 2,1);
        }

        label.setText(GUI.getAttributPanel().getTableModel().getValueAt(1,1).toString());
        label.setFont(new Font("Arial", Font.PLAIN, Integer.parseInt(GUI.getAttributPanel().getTableModel().getValueAt(2,1).toString())));
        setName(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString());
    }
}
