package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomFrame extends JInternalFrame {


    /**
     * Erstellen des Internal-Frames und Einstellungen vornehmen.
     * @MouseListener Setzt ausgewählte Komponente auf Null. (Internal-Frame ist jetzt ausgewählt)
     */
    public CustomFrame() {
        super("Meine GUI-Anwendung", true, false,false, false);
        setLocation(20,20);
        setSize(new Dimension(500,500));
        getContentPane().setLayout(null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                GUI.getSession().setSelectedComponent(null);
            }
        });

        show();
    }

    /**
     * Löschen des Internal-Frames
     */
    public void removeCustomFrame() {
        getContentPane().removeAll();
        getRootPane().remove(this);
        dispose();
    }

    /**
     * Attribute des Internal-Frames an Attributeinstellungen übergeben.
     */
    public void getAttributes() {
        GUI.getAttributPanel().addTableEntry(0, "Titel", new JTextField(getTitle()));
        GUI.getAttributPanel().addTableEntry(1, "Resizeable", new JComboBox(new Object[]{isResizable(), !isResizable()}));

        GUI.getAttributPanel().updateTable();
    }

    /**
     * Einträge aus Attributeinstellungen an JFrame übergeben.
     */
    public void updateAttributes() {
        JTextField textField1 = (JTextField)GUI.getAttributPanel().getAttributetable().get(0).get(1);
        if(textField1.getText().equals(""))
            textField1.setText(getTitle());
        else setTitle( textField1.getText());

        JComboBox comboBox = (JComboBox) GUI.getAttributPanel().getAttributetable().get(1).get(1);
        setResizable( Boolean.parseBoolean(comboBox.getSelectedItem().toString()));
    }
}