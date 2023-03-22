package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Components.ResizableButton;
import de.bellmannjan.javaguibuilder.Components.ResizableComponent;
import de.bellmannjan.javaguibuilder.Components.ResizableInput;
import de.bellmannjan.javaguibuilder.Components.ResizableText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Session {

    //TODO implement komponent list
    private JInternalFrame internalFrame;
    private ArrayList<ResizableComponent> resizableComponents = new ArrayList<>();

    public Session() {
        initInternalFrame();
        initComponentButtonFunction();
        GUI.getFramePanel().add(getInternalFrame());
    }
    public void closeSession() {
        getInternalFrame().setVisible(false);
        getInternalFrame().getContentPane().removeAll();
        getResizableComponents().clear();
        getInternalFrame().getRootPane().remove(getInternalFrame());
        uninstallComponentButtonFunction();

    }

    public ArrayList<ResizableComponent> getResizableComponents() {
        return resizableComponents;
    }
    public ResizableComponent getResizableComponent(Component component) {
        for (ResizableComponent resizableComponent : getResizableComponents()) {
            if (resizableComponent == component) {
                return resizableComponent;
            }
        }
        return null;
    }

    public void createResizableComponent(String componentString) {
        ResizableComponent resizableComponent;
        switch (componentString) {
            case "JLabel":
                resizableComponent = new ResizableText(new JLabel("Text"), false);
                resizableComponent.setBounds(10, 10, 50, 20);
                break;
            case "JTextField":
                resizableComponent = new ResizableInput(new JTextField(), false);
                resizableComponent.setBounds(10, 10, 100, 20);
                break;
            case "JButton":
                resizableComponent = new ResizableButton(new JButton("Button"), false);
                resizableComponent.setBounds(10, 10, 70, 20);
                break;
            default:
                resizableComponent = null;
        }
        handleComponentClick();
        getResizableComponents().add(resizableComponent);
        getInternalFrame().getContentPane().add(resizableComponent);
        getInternalFrame().updateUI();
    }
    public void removeResizableComponent() {
        GUI.getComponentPanel().getComponentListModel().remove(1);
    }

    public void handleComponentClick(ResizableComponent resizableComponent) {
        getResizableComponents().forEach(resizableComponent1 -> resizableComponent1.setClicked(false));
        resizableComponent.setClicked(true);
    }
    public void handleComponentClick() {
        getResizableComponents().forEach(resizableComponent1 -> resizableComponent1.setClicked(false));
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
    public void initComponentButtonFunction() {
        for (Component component  : GUI.getComponentPanel().getContentPanel().getComponents()) {
            if(component instanceof JButton button) {
                button.setEnabled(true);
                button.addActionListener(e -> {
                    createResizableComponent(button.getToolTipText());
                });
            }
        }
    }
    public void uninstallComponentButtonFunction() {
        for (Component component  : GUI.getComponentPanel().getContentPanel().getComponents()) {
            if(component instanceof JButton button) {
                button.setEnabled(false);
                for (ActionListener actionListener : button.getActionListeners()) {
                    button.removeActionListener(actionListener);
                }
            }
        }
    }
}
