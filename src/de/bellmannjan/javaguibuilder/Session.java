package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Components.ResizableButton;
import de.bellmannjan.javaguibuilder.Components.ResizableComponent;
import de.bellmannjan.javaguibuilder.Components.ResizableInput;
import de.bellmannjan.javaguibuilder.Components.ResizableText;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Session {
    private JInternalFrame internalFrame;
    public int componentCounter = 0;
    private ArrayList<ResizableComponent> resizableComponents = new ArrayList<>();

    public Session() {
        initInternalFrame();
        GUI.getFramePanel().add(getInternalFrame());
    }
    public void closeSession() {
        getInternalFrame().setVisible(false);
        getInternalFrame().getContentPane().removeAll();
        getResizableComponents().clear();
        getInternalFrame().getRootPane().remove(getInternalFrame());
        GUI.getComponentPanel().getComponentListModel().clear();

    }

    public ArrayList<ResizableComponent> getResizableComponents() {
        return resizableComponents;
    }
    public ResizableComponent getResizableComponent(String componentName) {
        for (ResizableComponent resizableComponent : getResizableComponents()) {
            if (resizableComponent.getComponentName().equals(componentName)) {
                return resizableComponent;
            }
        }
        return null;
    }

    public void createResizableComponent(String componentString) {
        ResizableComponent resizableComponent;
        switch (componentString) {
            case "JLabel" -> {
                resizableComponent = new ResizableText(new JLabel("Text"), "jLabel", false);
                resizableComponent.setBounds(10, 10, 50, 20);
            }
            case "JTextField" -> {
                resizableComponent = new ResizableInput(new JTextField(), "jTextField", false);
                resizableComponent.setBounds(10, 10, 100, 20);
            }
            case "JButton" -> {
                resizableComponent = new ResizableButton(new JButton("Button"),"jButton", false);
                resizableComponent.setBounds(10, 10, 70, 20);
            }
            default -> resizableComponent = null;
        }
        handleComponentClick();
        getResizableComponents().add(resizableComponent);
        getInternalFrame().getContentPane().add(resizableComponent);
        getInternalFrame().updateUI();

        GUI.getComponentPanel().getComponentListModel().addElement(resizableComponent.getComponentName() + " : "
                + resizableComponent.getComponentType().toUpperCase());
        GUI.getComponentPanel().getComponentList().setSelectedIndex(getResizableComponents().size());

        componentCounter++;
    }
    public void removeResizableComponent(String componentName) {
        ResizableComponent resizableComponent = getResizableComponent(componentName);
        getResizableComponents().remove(resizableComponent);
        getInternalFrame().getContentPane().remove(resizableComponent);
        getInternalFrame().updateUI();
        handleComponentClick();
    }

    public void handleComponentClick(ResizableComponent resizableComponent) {
        handleComponentClick();
        resizableComponent.setClicked(true);
        resizableComponent.getAttributes();
    }
    public void handleComponentClick() {
        getResizableComponents().forEach(resizableComponent1 -> resizableComponent1.setClicked(false));
        for (int r = 0; r < GUI.getAttributPanel().getTableModel().getRowCount(); r++) {
            GUI.getAttributPanel().getTableModel().setValueAt("", r, 0);
            GUI.getAttributPanel().getTableModel().setValueAt("", r, 1);
        }
    }

    public JInternalFrame getInternalFrame() {
        return internalFrame;
    }

    public void initInternalFrame() {
        internalFrame = new JInternalFrame("Meine GUI-Anwendung", true, false,false, false);
        internalFrame.setLocation(20,20);
        internalFrame.setSize(new Dimension(500,500));
        internalFrame.getContentPane().setLayout(null);
        internalFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                handleComponentClick();
                for (Component component : internalFrame.getComponents()) {
                    component.repaint();
                }
            }
        });

        internalFrame.show();
    }
}
