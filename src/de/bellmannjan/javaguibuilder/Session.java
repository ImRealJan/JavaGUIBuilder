package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Components.ResizableComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Session {

    /**
     * Arraylist mit allen components
     * isclicked handler
     * internaljframe erst wenn session da is means internalframe in session initialisieren?
     *
     * */

    private JInternalFrame internalFrame;
    private ArrayList<ResizableComponent> resizableComponents = new ArrayList<>();

    public Session() {
        initInternalFrame();
    }
    public void closeSession() {
        getInternalFrame().setVisible(false);
        getInternalFrame().getContentPane().removeAll();
        getResizableComponents().clear();
        getInternalFrame().updateUI();
        getInternalFrame().setLocation(20,20);
        getInternalFrame().setSize(new Dimension(500,500));
        getInternalFrame().setVisible(true);

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
}
