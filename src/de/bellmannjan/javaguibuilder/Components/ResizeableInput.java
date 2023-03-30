package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ResizeableInput extends ResizeableComponent {


    private boolean actionPerformed = false;

    private JTextField textField = (JTextField) resizeableComponent;
    public ResizeableInput(JComponent comp, String name, int id) {
        super(comp, name, id);
        textField.setBackground(Color.white);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.consume();
            }
        });
        init();
    }


    /**
     * Attribute der Komponente werden an Attributeinstellungen übergeben.
     */
    public void getAttributes() {

        GUI.getAttributPanel().addTableEntry(0, "Name", new JTextField(getName()));
        GUI.getAttributPanel().addTableEntry(1, "Text", new JTextField(textField.getText()));
        GUI.getAttributPanel().addTableEntry(2, "Tooltip", new JTextField(textField.getToolTipText()));
        GUI.getAttributPanel().addTableEntry(3, "Enabled", new JComboBox(new Object[]{textField.isEnabled(), !textField.isEnabled()}));
        GUI.getAttributPanel().addTableEntry(4, "Visible", new JComboBox(new Object[]{textField.isVisible(), !textField.isVisible()}));
        GUI.getAttributPanel().addTableEntry(5, "Editable", new JComboBox(new Object[]{textField.isEditable(), !textField.isEditable()}));
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
        textField.setText(textField2.getText());

        JTextField textField3 = (JTextField)GUI.getAttributPanel().getAttributetable().get(2).get(1);
        if(!textField3.getText().equals(""))
            textField.setToolTipText(textField3.getText());

        JComboBox comboBox1 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(3).get(1);
        textField.setEnabled(Boolean.parseBoolean(comboBox1.getSelectedItem().toString()));

        JComboBox comboBox2 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(4).get(1);
        textField.setVisible(Boolean.parseBoolean(comboBox2.getSelectedItem().toString()));

        JComboBox comboBox3 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(5).get(1);
        textField.setEditable(Boolean.parseBoolean(comboBox3.getSelectedItem().toString()));

        JComboBox comboBox4 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(6).get(1);
        actionPerformed = Boolean.parseBoolean(comboBox4.getSelectedItem().toString());

    }

    public JComponent getComponentInformations() {
        return textField;
    }
    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public boolean hasEvent() {
        return actionPerformed;
    }

    public void setActionPerformed(boolean actionPerformed) {
        this.actionPerformed = actionPerformed;
    }
}
