package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Components.*;
import de.bellmannjan.javaguibuilder.Tools.ComponentCloner;
import de.bellmannjan.javaguibuilder.Tools.JavaCodeGenerator;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Project {

    private CustomFrame customFrame;
    private ResizeableComponent selectedComponent;
    private final ArrayList<ResizeableComponent> resizeableComponents = new ArrayList<>();

    private int projectid;
    private String className;
    private LocalDateTime lastUpdated;
    public void update() {
        this.lastUpdated = LocalDateTime.now();
    }



    /**
     * Erstellen eines Internel-Frames.
     * Hinzufügen des Freames zum Hauptprogramm
     */
    public Project(int id, String name) {
        if(GUI.getUser() != null) {
            this.className = name;
            this.projectid = id;

            customFrame = new CustomFrame();
            GUI.getFramePanel().add(customFrame);
            GUI.getTabbedPane().setEnabledAt(1, true);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(customFrame);
            topFrame.setTitle(topFrame.getTitle() + " - " + name);
        }
    }

    /**
     * Schließen des Internal-Frames.
     * Leeren der Komponentenliste (Liste unten Rechts).
     * Leeren der Attributeinstellung (Tabelle oben Rechts)
     */
    public void closeSession() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(customFrame);
        topFrame.setTitle("Java GUI Builder");

        customFrame.removeCustomFrame();
        GUI.getComponentPanel().getComponentListModel().clear();
        GUI.getAttributPanel().setDefaulTable();
        GUI.getTabbedPane().setSelectedIndex(0);
        GUI.getTabbedPane().setEnabledAt(1, false);
        GUI.getCodeOutputPanel().setText(new JavaCodeGenerator().generateDefaultCode());
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
    public ResizeableComponent createResizableComponent(String componentString, int id) {
        ResizeableComponent resizeableComponent;
        switch (componentString) {
            case "JLabel" -> {
                resizeableComponent = new ResizeableText(new JLabel("Text"), "Label", id);
                resizeableComponent.setBounds(10, 10, 50, 20);
            }
            case "JTextField" -> {
                resizeableComponent = new ResizeableInput(new JTextField(), "TextField", id);
                resizeableComponent.setBounds(10, 10, 100, 20);
            }
            case "JButton" -> {
                resizeableComponent = new ResizeableButton(new JButton("Button"),"Button", id);
                resizeableComponent.setBounds(10, 10, 70, 20);
            }
            case "JTextArea" -> {
                resizeableComponent = new ResizeableTextArea(new JScrollPane(new JTextArea()),"TextArea", id);
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

        return resizeableComponent;
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

    public void saveProject() {
        if(GUI.getMySQL().isConnected()) {
            update();
            GUI.getMySQL().createSQL("SELECT `projectid` FROM `project` WHERE `projectid` = " + projectid);
            GUI.getMySQL().createTable();
            if(GUI.getMySQL().getList().size() != 0) {
                GUI.getMySQL().update("UPDATE `project` SET `name` = '" + className + "', `width` = " + customFrame.getWidth() + ", `height` = "
                        + customFrame.getHeight() + ", `title` = '" + customFrame.getTitle() + "', `resizable` = "
                        + customFrame.isResizable() + ", `lastUpdated` = '" + lastUpdated + "' WHERE `project`.`projectid` = " + projectid);
            }else {
                GUI.getMySQL().update("INSERT INTO `project` (`projectid`, `name`, `width`, `height`, `title`, `resizable`, `lastUpdated`, `userid`) VALUES (" + projectid + ", '"
                        + className + "', " + customFrame.getWidth() + ", " + customFrame.getHeight() + ", '" + customFrame.getTitle() + "', "
                        + customFrame.isResizable() + ", '" + lastUpdated + "', " + GUI.getUser().getUid() + ")");
            }

            getResizableComponents().forEach(resizeableComponent -> {
                boolean editable = false;
                boolean hasClickEvent = false;
                if(resizeableComponent.getComponentInformations() instanceof JTextField textField) {
                    editable = textField.isEditable();
                }
                if(resizeableComponent.getComponentInformations() instanceof JTextArea textArea) {
                    editable = textArea.isEditable();
                }
                if(resizeableComponent instanceof ResizeableButton resizeableButton) {
                    hasClickEvent = resizeableButton.hasEvent();
                }
                if(resizeableComponent instanceof ResizeableInput resizeableInput) {
                    hasClickEvent = resizeableInput.hasEvent();
                }

                if(resizeableComponent.getComponentID() == 0) {
                    GUI.getMySQL().update("INSERT INTO `component` (`componentid`, `name`, `x`, `y`, `height`, `width`, `tooltiptext`, " +
                            "`fontsize`, `enabled`, `visible`, `text`, `editable`, `clickevent`, `componentType`, `projectid`) " +
                            "VALUES (NULL, '" + resizeableComponent.getName() + "', " + resizeableComponent.getX() + ", " + resizeableComponent.getY() + ", "
                            + resizeableComponent.getHeight() + ", " + resizeableComponent.getWidth() + ", '" + resizeableComponent.getComponentInformations().getToolTipText() + "', "
                            + resizeableComponent.getComponentInformations().getFont().getSize() + ", "
                            + resizeableComponent.getComponentInformations().isEnabled() + ", " + resizeableComponent.getComponentInformations().isVisible() + ", '"
                            + resizeableComponent.getText() + "', " + editable + ", " + hasClickEvent + ", '" + resizeableComponent.getComponentType() + "', " + projectid + ")");
                }else {
                   GUI.getMySQL().update("UPDATE `component` SET `name` = '" + resizeableComponent.getName() + "', `x` = " + resizeableComponent.getX() + ", `y` = "
                           + resizeableComponent.getY() + ", `height` = " + resizeableComponent.getHeight() + ", `width` = " + resizeableComponent.getWidth() + ", `tooltiptext` = '"
                           + resizeableComponent.getComponentInformations().getToolTipText() + "', `fontsize` = " + resizeableComponent.getComponentInformations().getFont().getSize() +
                           ", `enabled` = " + resizeableComponent.getComponentInformations().isEnabled() + ", `visible` = "
                           + resizeableComponent.getComponentInformations().isVisible() + ", `text` = '" + resizeableComponent.getText() + "', `editable` = "
                           + editable + ", `clickevent` = " + hasClickEvent +  " WHERE `componentid` = " + resizeableComponent.getComponentID());
                }
            });

        }
    }
    public void openProject() {

        if(GUI.getMySQL().isConnected()) {
            GUI.getMySQL().createSQL("SELECT * FROM `project` WHERE `projectid`=" + projectid);
            GUI.getMySQL().createTable();

            customFrame.setSize(Integer.parseInt(GUI.getMySQL().getList().get(0).get(2)), Integer.parseInt(GUI.getMySQL().getList().get(0).get(3)));
            customFrame.setTitle(GUI.getMySQL().getList().get(0).get(4));
            customFrame.setResizable(Boolean.parseBoolean(GUI.getMySQL().getList().get(0).get(5)));

            GUI.getMySQL().createSQL("SELECT * FROM `component` WHERE `projectid`=" + projectid);
            GUI.getMySQL().createTable();

            for (int row = 0; row < GUI.getMySQL().getList().size(); row++) {
                ResizeableComponent resizeableComponent = createResizableComponent(GUI.getMySQL().getList().get(row).get(13), Integer.parseInt(GUI.getMySQL().getList().get(row).get(0)));
                resizeableComponent.setBounds(Integer.parseInt(GUI.getMySQL().getList().get(row).get(2)), Integer.parseInt(GUI.getMySQL().getList().get(row).get(3)),Integer.parseInt(GUI.getMySQL().getList().get(row).get(5)),Integer.parseInt(GUI.getMySQL().getList().get(row).get(4)));
                if(!GUI.getMySQL().getList().get(row).get(6).equals("null"))
                    resizeableComponent.getComponentInformations().setToolTipText(GUI.getMySQL().getList().get(row).get(6));
                resizeableComponent.getComponentInformations().setFont(new Font("Arial", Font.BOLD, Integer.parseInt(GUI.getMySQL().getList().get(row).get(7))));
                resizeableComponent.getComponentInformations().setEnabled(Boolean.parseBoolean(GUI.getMySQL().getList().get(row).get(8)));
                resizeableComponent.getComponentInformations().setVisible(Boolean.parseBoolean(GUI.getMySQL().getList().get(row).get(9)));
                resizeableComponent.setText(GUI.getMySQL().getList().get(row).get(10));
                if(resizeableComponent.getComponentType().equals("JButton")) {
                    ResizeableButton resizeableButton = (ResizeableButton)resizeableComponent;
                    resizeableButton.setActionPerformed(Boolean.parseBoolean(GUI.getMySQL().getList().get(row).get(12)));
                }
                if(resizeableComponent.getComponentType().equals("JTextField")) {
                    ResizeableInput resizeableInput = (ResizeableInput) resizeableComponent;
                    resizeableInput.setActionPerformed(Boolean.parseBoolean(GUI.getMySQL().getList().get(row).get(12)));
                    JTextField textField = (JTextField) resizeableComponent.getComponentInformations();
                    textField.setEditable(Boolean.parseBoolean(GUI.getMySQL().getList().get(row).get(11)));
                }
                if(resizeableComponent.getComponentType().equals("JTextArea")) {
                    JTextArea textArea = (JTextArea) resizeableComponent.getComponentInformations();
                    textArea.setEditable(Boolean.parseBoolean(GUI.getMySQL().getList().get(row).get(11)));
                }
            }
            customFrame.updateUI();
            GUI.getComponentPanel().updateList();
        }

    }
}
