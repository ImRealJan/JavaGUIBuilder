package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ResizeableTextArea extends ResizeableComponent {

    private JTextArea textArea;

    public ResizeableTextArea(JComponent comp, String name) {
        super(comp, name);

        JViewport jViewport = (JViewport)comp.getComponent(0);
        resizeableComponent = (JComponent) jViewport.getView();
        textArea = (JTextArea) resizeableComponent;
        textArea.setBackground(Color.white);
        textArea.addKeyListener(new KeyAdapter() {
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
        GUI.getAttributPanel().addTableEntry(1, "Text", new JTextField(textArea.getText()));
        GUI.getAttributPanel().addTableEntry(2, "Tooltip", new JTextField(textArea.getToolTipText()));
        GUI.getAttributPanel().addTableEntry(3, "Enabled", new JComboBox(new Object[]{textArea.isEnabled(), !textArea.isEnabled()}));
        GUI.getAttributPanel().addTableEntry(4, "Visible", new JComboBox(new Object[]{textArea.isVisible(), !textArea.isVisible()}));
        GUI.getAttributPanel().addTableEntry(5, "Editable", new JComboBox(new Object[]{textArea.isEditable(), !textArea.isEditable()}));

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
        textArea.setText(textField2.getText());

        JTextField textField3 = (JTextField)GUI.getAttributPanel().getAttributetable().get(2).get(1);
        if(!textField3.getText().equals(""))
            textArea.setToolTipText(textField3.getText());

        JComboBox comboBox1 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(3).get(1);
        textArea.setEnabled(Boolean.parseBoolean(comboBox1.getSelectedItem().toString()));

        JComboBox comboBox2 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(4).get(1);
        textArea.setVisible(Boolean.parseBoolean(comboBox2.getSelectedItem().toString()));

        JComboBox comboBox3 = (JComboBox) GUI.getAttributPanel().getAttributetable().get(5).get(1);
        textArea.setEditable(Boolean.parseBoolean(comboBox3.getSelectedItem().toString()));

    }
    public JComponent getComponentInformations() {
        return textArea;
    }
    public String getText() {
        return textArea.getText();
    }
}
