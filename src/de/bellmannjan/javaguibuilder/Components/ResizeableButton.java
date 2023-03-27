package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;

public class ResizeableButton extends ResizeableComponent {

    private JButton button = (JButton) resizeableComponent;

    private boolean actionPerformed = false;
    public ResizeableButton(JComponent comp, String name) {
        super(comp, name);
        init();
    }
    /**
     * Attribute der Komponente werden an Attributeinstellungen übergeben.
     */
    public void getAttributes() {

        GUI.getAttributPanel().addTableEntry(0, "Name", new JTextField(getName()));
        GUI.getAttributPanel().addTableEntry(1, "Text", new JTextField(button.getText()));
        GUI.getAttributPanel().addTableEntry(2, "Tooltip", new JTextField(button.getToolTipText()));
        JComboBox comboBox = new JComboBox(new Object[]{8,10,12,14,16,20,24,30});
        comboBox.setSelectedItem(button.getFont().getSize());
        GUI.getAttributPanel().addTableEntry(3, "Size", comboBox);
        GUI.getAttributPanel().addTableEntry(4, "Enabled", new JComboBox(new Object[]{button.isEnabled(), !button.isEnabled()}));
        GUI.getAttributPanel().addTableEntry(5, "Visible", new JComboBox(new Object[]{button.isVisible(), !button.isVisible()}));
        GUI.getAttributPanel().addTableEntry(6, "hasClickEvent", new JComboBox(new Object[]{actionPerformed, !actionPerformed}));

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
        button.setText(textField2.getText());

        JTextField textField3 = (JTextField)GUI.getAttributPanel().getAttributetable().get(2).get(1);
        button.setToolTipText(textField3.getText());

        JComboBox comboBox1 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(3).get(1);
        button.setFont(new Font("Arial", Font.BOLD, Integer.parseInt(comboBox1.getSelectedItem().toString())));

        JComboBox comboBox2 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(4).get(1);
        button.setEnabled(Boolean.parseBoolean(comboBox2.getSelectedItem().toString()));

        JComboBox comboBox3 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(5).get(1);
        button.setVisible(Boolean.parseBoolean(comboBox3.getSelectedItem().toString()));

        JComboBox comboBox4 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(6).get(1);
        actionPerformed = Boolean.parseBoolean(comboBox4.getSelectedItem().toString());
    }

    public JComponent getComponentInformations() {
        return button;
    }
    public String getText() {
        return button.getText();
    }

    public boolean hasEvent() {
        return actionPerformed;
    }
}
