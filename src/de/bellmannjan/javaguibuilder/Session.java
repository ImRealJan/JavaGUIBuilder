package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Components.*;
import de.bellmannjan.javaguibuilder.Tools.ComponentCloner;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Session {

    private final CustomFrame customFrame;
    private ResizeableComponent selectedComponent;
    public int componentCounter = 0;
    private String className = "MeineKlasse";
    private final ArrayList<ResizeableComponent> resizeableComponents = new ArrayList<>();

    /**
     * Erstellen eines Internel-Frames.
     * Hinzufügen des Freames zum Hauptprogramm
     */
    public Session() {
        customFrame = new CustomFrame();
        GUI.getFramePanel().add(customFrame);
    }

    /**
     * Schließen des Internal-Frames.
     * Leeren der Komponentenliste (Liste unten Rechts).
     * Leeren der Attributeinstellung (Tabelle oben Rechts)
     */
    public void closeSession() {
        customFrame.removeCustomFrame();
        GUI.getComponentPanel().getComponentListModel().clear();
        GUI.getAttributPanel().setDefaulTable();

    }

    /**
     * @return Alle Komponenten die auf der Internal-Frame hinzugefügt wurden
     */
    public ArrayList<ResizeableComponent> getResizableComponents() {
        return resizeableComponents;
    }

    /**
     * @param componentString Variablenname der Komponente.
     * @Description Erstellen einer neuen Komponente für das Internal-Frame. Festlegen der größe je nach Komponentenart. Komponente auswählen. Hinzufügen der Komponente in Liste und diese updaten.
     */
    public void createResizableComponent(String componentString) {
        ResizeableComponent resizeableComponent;
        switch (componentString) {
            case "JLabel" -> {
                resizeableComponent = new ResizeableText(new JLabel("Text"), "Label");
                resizeableComponent.setBounds(10, 10, 50, 20);
            }
            case "JTextField" -> {
                resizeableComponent = new ResizeableInput(new JTextField(), "TextField");
                resizeableComponent.setBounds(10, 10, 100, 20);
            }
            case "JButton" -> {
                resizeableComponent = new ResizeableButton(new JButton("Button"),"Button");
                resizeableComponent.setBounds(10, 10, 70, 20);
            }
            case "JTextArea" -> {
                resizeableComponent = new ResizeableTextArea(new JScrollPane(new JTextArea()),"TextArea");
                resizeableComponent.setBounds(10, 10, 150, 80);
            }
            default -> resizeableComponent = null;
        }
        setSelectedComponent(resizeableComponent);
        getResizableComponents().add(resizeableComponent);
        customFrame.getContentPane().add(resizeableComponent);
        customFrame.getContentPane().setComponentZOrder(resizeableComponent, 0);
        customFrame.updateUI();

        GUI.getComponentPanel().updateList();
        componentCounter++;
    }

    /**
     * @param resizeableComponent Komponente die entfernt werden soll
     * @Description Entfernen der Komponente vom Internal-Frame. Ausgewählte Komponente auf Null setzen. Komponente aus der Liste entfernen und diese updaten
     */
    public void removeResizableComponent(ResizeableComponent resizeableComponent) {
        setSelectedComponent(null);
        getResizableComponents().remove(resizeableComponent);
        customFrame.getContentPane().remove(resizeableComponent);
        customFrame.updateUI();

        GUI.getComponentPanel().updateList();
    }

    /**
     * @return Internal-Frame
     */
    public CustomFrame getCustomFrame() {
        return customFrame;
    }

    /**
     * @return Die derzeit ausgewählte Komponente. (kann null sein)
     */
    public ResizeableComponent getSelectedComponent() {
        return selectedComponent;
    }

    /**
     * @param selectedComponent Komponente die ausgewählt werden soll
     * @Description Alle Komponenten repainten (Sichtbarkeit der Auswahlränder). Attributeinstellung leeren.
     * @Wenn Komponente ausgewählt: Dessen Attributeinstellungen in Tabelle eintragen.
     * @Sonst Attribute der Internal-Frame in Attributeinstellungen eintragen.
     */
    public void setSelectedComponent(ResizeableComponent selectedComponent) {
        this.selectedComponent = selectedComponent;
        for (Component component : customFrame.getComponents()) {
            component.repaint();
        }
        GUI.getAttributPanel().setDefaulTable();

        if(selectedComponent != null) {
            selectedComponent.getAttributes();
            GUI.getComponentPanel().getComponentList().setSelectedIndex(getResizableComponents().indexOf(selectedComponent));
        }else {
            customFrame.getAttributes();
            GUI.getComponentPanel().getComponentList().clearSelection();
        }
    }

    /**
     * @param owner Übergabe des JFrames vom Hauptprogramm
     * @Description Erstellt ein JDialog indem man sieht wie das GUI-Fenster am Ende aussehen wird. Informationen vom JFrame werden an JDialog übergeben. Jede Komponente wird geklont und hinzugefügt.
     */
    public void runTestFrame(JFrame owner){
        JDialog testframe = new JDialog(owner, true);
        testframe.setTitle(customFrame.getTitle());
        testframe.setResizable(customFrame.isResizable());
        testframe.setLayout(null);
        testframe.setSize(new Dimension((int)customFrame.getSize().getWidth() + 5, (int)customFrame.getSize().getHeight() + 5));

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - customFrame.getWidth()) / 2;
        int y = (d.height - customFrame.getHeight()) / 2;
        testframe.setLocation(x, y);

        getResizableComponents().forEach(resizeableComponent -> {
            JComponent component = new ComponentCloner(resizeableComponent).build();
            testframe.add(component);
        });

        testframe.setVisible(true);
    }

    /**
     * @return Den gewählten Namen der Klasse
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className gewählter Name der Klasse
     * @Description Setzt den Namen für die Klasse
     */
    public void setClassName(String className) {
        this.className = className;
    }
}
