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


        GUI.getAttributPanel().addTableEntry(0, "Name", new JTextField(getName()));
        GUI.getAttributPanel().addTableEntry(1, "Text", new JTextField(label.getText()));
        GUI.getAttributPanel().addTableEntry(2, "Tooltip", new JTextField(label.getToolTipText()));
        JComboBox comboBox = new JComboBox<>(new Object[]{8,10,12,14,16,20,24,30});
        comboBox.setSelectedItem(label.getFont().getSize());
        GUI.getAttributPanel().addTableEntry(3, "Size", comboBox);
        GUI.getAttributPanel().addTableEntry(4, "Visible", new JComboBox(new Object[]{label.isVisible(), !label.isVisible()}));

        GUI.getAttributPanel().updateTable();
    }

    /**
     * Einträge aus Attributeinstellungen an JButton übergeben
     */
    public void updateAttributes() {

        JTextField textField1 = (JTextField)GUI.getAttributPanel().getAttributetable().get(0).get(1);
        if(textField1.getText().equals(""))
            textField1.setText(getName());
        else setName( textField1.getText());

        JTextField textField2 = (JTextField)GUI.getAttributPanel().getAttributetable().get(1).get(1);
        label.setText(textField2.getText());

        JTextField textField3 = (JTextField)GUI.getAttributPanel().getAttributetable().get(2).get(1);
        if(!textField3.getText().equals(""))
            label.setToolTipText(textField3.getText());

        JComboBox comboBox = (JComboBox) GUI.getAttributPanel().getAttributetable().get(3).get(1);
        label.setFont(new Font("Arial", Font.BOLD, Integer.parseInt(comboBox.getSelectedItem().toString())));

        JComboBox comboBox2 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(4).get(1);
        label.setVisible(Boolean.parseBoolean(comboBox2.getSelectedItem().toString()));
    }
    public JComponent getComponentInformations() {
        return label;
    }
    public String getText() {
        return label.getText();
    }
    public void setText(String text) {
        label.setText(text);
    }
}
