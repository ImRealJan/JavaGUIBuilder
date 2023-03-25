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
        GUI.getAttributPanel().getTableModel().setValueAt("Titel", 0, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(getTitle(), 0, 1);

        GUI.getAttributPanel().getTableModel().setValueAt("Resizable", 1, 0);
        GUI.getAttributPanel().getTableModel().setValueAt(isResizable(), 1, 1);
    }

    /**
     * Einträge aus Attributeinstellungen an JFrame übergeben.
     */
    public void updateAttributes() {
        if(GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString().equals(""))
            GUI.getAttributPanel().getTableModel().setValueAt(getTitle(), 0,1);
        try {
            Boolean.parseBoolean(GUI.getAttributPanel().getTableModel().getValueAt(1,1).toString());
        } catch (Exception ex) {
            GUI.getAttributPanel().getTableModel().setValueAt(isResizable(), 1,1);
        }
        setTitle( GUI.getAttributPanel().getTableModel().getValueAt(0,1).toString());
        setResizable( Boolean.parseBoolean(GUI.getAttributPanel().getTableModel().getValueAt(1,1).toString()));
    }
}