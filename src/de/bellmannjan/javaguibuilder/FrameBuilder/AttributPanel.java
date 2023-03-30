package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.Components.ResizeableComponent;
import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class AttributPanel extends JPanel {

    private final JPanel attributContentPanel;

    private final ArrayList<ArrayList<JComponent>> attributetable = new ArrayList<>();

    public ArrayList<ArrayList<JComponent>> getAttributetable() {
        return attributetable;
    }

    /**
     * Design des Attributpanels (Toolbar oben link) festlegen, und in die Tabelle die Standardwerte eintragen
     */
    public AttributPanel() {
        setLayout(new BorderLayout());

        JLabel lattribute = new JLabel("Attribute festlegen:", SwingConstants.CENTER);
        lattribute.setBorder(new EmptyBorder(5, 0, 0, 0));
        lattribute.setFont(new Font("Arial", Font.BOLD, 14));


        attributContentPanel = new JPanel(new GridLayout(10, 2));
        attributContentPanel.setPreferredSize(new Dimension(450, 350));
        attributContentPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Attribute:"), new EmptyBorder(5, 5, 5, 5)));

        setDefaulTable();

        add(lattribute, BorderLayout.PAGE_START);
        add(attributContentPanel, BorderLayout.CENTER);
    }

    /**
     * "Tabelle" updaten. 1. Alle Komponenten entfernen 2. Mit schleifen ermitteln, wo man sich befindet und entsprechend eine Border malen 3. Komponente der Tabelle hinzufügen
     */
    public void updateTable() {
        attributContentPanel.removeAll();

        for (ArrayList<JComponent> rowList : attributetable) {
            for (JComponent component : rowList) {
                if (attributetable.indexOf(rowList) == 0) {
                    if (rowList.indexOf(component) == 0) {
                        // Obere Linke Spalte "alle Ränder"
                        component.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    } else {
                        // Obere Spalte Rechts "alle Seiten außer links"
                        component.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
                    }
                } else {
                    if (rowList.indexOf(component) == 0) {
                        // Linke Spalte "alle Ränder außer oben"
                        component.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
                    } else {
                        // Rechte Spalte "nur Rechte und untere Rand"
                        component.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
                    }
                }
                attributContentPanel.add(component);
            }
        }
        attributContentPanel.updateUI();
    }

    /**
     * Die Tabelle "leeren" indem überall unsichtbare JLabels erzeugt werden. Und anschließen die Tabelle updaten.
     */
    public void setDefaulTable() {
        attributetable.clear();
        for (int row = 0; row < 10; row++) {
            ArrayList<JComponent> column = new ArrayList<>();
            for (int col = 0; col < 2; col++) {
                final JLabel label = new JLabel("Text");
                label.setForeground(label.getBackground());
                attributContentPanel.add(label);
                column.add(label);
            }
            attributetable.add(column);
        }

        updateTable();
    }

    /**
     * @param row die Reihe der Tabelle
     * @param description die Attributbeschreibung
     * @param inputComponent Komponente die auf der Rechten Spalte benutzt wird (JTextField/JCombobox)
     * @Description Komponenten werden an der entsprechenden Stelle in ArrayList eingefügt. Komponente wird ein Listener hinzugefügt um zu überprüfen ob sich dessen Inhalt verändert hat.
     */
    public void addTableEntry(int row, String description, JComponent inputComponent) {
        ArrayList<JComponent> components = new ArrayList<>();
        components.add(new JLabel(description));
        if (inputComponent instanceof JTextField textField) {
            textField.addActionListener(e -> componentUpdater());
        } else if (inputComponent instanceof JComboBox comboBox) {
            comboBox.addItemListener(e -> componentUpdater());
        }
        components.add(inputComponent);
        attributetable.set(row, components);
    }

    /**
     * Wird ausgeführt wenn sich der Inhalt einer Komponente in der Rechten Attributspalte ändert. Attribute werden an die ausgewählte ResizeableComponent übergeben und die Komponentenauswahl geupdatet.
     */
    private void componentUpdater() {
        if (GUI.getProject() != null) {
            ResizeableComponent resizeableComponent = GUI.getProject().getSelectedComponent();
            if (resizeableComponent != null) {
                resizeableComponent.updateAttributes();
                int index = GUI.getComponentPanel().getComponentList().getSelectedIndex();
                GUI.getComponentPanel().updateList();
                GUI.getComponentPanel().getComponentList().setSelectedIndex(index);
            } else {
                GUI.getProject().getCustomFrame().updateAttributes();
            }
        }
    }
}