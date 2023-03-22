package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

//Angepasstes Skript aus dem Internet
//Link: https://zetcode.com/javaswing/resizablecomponent/
//ToDo make Class Abstract with ResizableText, ResizableButton
//ToDo add keyboard function (entf, arrow)
public abstract class ResizableComponent extends JComponent {


    private JComponent jComponent;
    private boolean isClicked = false;

    public ResizableComponent(JComponent comp, boolean hasScrollPane) {
        setLayout(new BorderLayout());
        add(comp);
        if(hasScrollPane) {
            JViewport jViewport = (JViewport)comp.getComponent(0);
            jComponent = (JComponent) jViewport.getView();
        }else this.jComponent = comp;
        if(this.jComponent instanceof JTextField || this.jComponent instanceof  JTextArea) {
            ((JTextComponent) this.jComponent).setEditable(false);
            this.jComponent.setBackground(Color.white);
        }
        this.jComponent.addMouseListener(resizeListener);
        this.jComponent.addMouseMotionListener(resizeListener);
        this.jComponent.setBorder(new ResizableBorder(this));
        this.jComponent.setRequestFocusEnabled(false);
    }

    private void resize() {

        if (getParent() != null) {
            getParent().revalidate();
        }
    }

    private JComponent getComponent() {
        return this;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void getAttributes() {
        //TODO attribute ausgeben
    }



    private int cursor;

    private  Point startpoint;
    private Point startPos;


    MouseInputListener resizeListener = new MouseInputAdapter() {
        @Override
        public void mouseMoved(MouseEvent me) {
            if (isClicked()) {

                ResizableBorder resizableBorder = (ResizableBorder) jComponent.getBorder();
                jComponent.setCursor(Cursor.getPredefinedCursor(resizableBorder.getCursor(me)));
            }
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            jComponent.setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void mousePressed(MouseEvent me) {

            ResizableBorder resizableBorder = (ResizableBorder) jComponent.getBorder();
            cursor = resizableBorder.getCursor(me);
            startPos = me.getPoint();
            startpoint = SwingUtilities.convertPoint(getComponent(), me.getPoint(), getParent());

            GUI.getSession().handleComponentClick((ResizableComponent) getComponent());

            JFrame frame = (JFrame) SwingUtilities.getRoot(getComponent());
            for (Component component : frame.getComponents()) {
                component.repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (startPos != null) {

                int dx = me.getX() - startPos.x;
                int dy = me.getY() - startPos.y;

                Point location = SwingUtilities.convertPoint(getComponent(), me.getPoint(), getParent());
                if(getParent().getBounds().contains(location)) {
                    switch (cursor) {

                        case Cursor.SE_RESIZE_CURSOR -> {

                            if (!(getWidth() + dx < getPreferredSize().getWidth()) && !(getHeight() + dy < getPreferredSize().getHeight())) {
                                setBounds(getX(), getY(), getWidth() + dx, getHeight() + dy);
                                startPos = me.getPoint();
                                resize();
                            }
                        }

                        case Cursor.MOVE_CURSOR -> {
                            Point newLocation = getLocation();
                            newLocation.translate(location.x - startpoint.x, location.y - startpoint.y);
                            newLocation.x = Math.max(newLocation.x, 0);
                            newLocation.y = Math.max(newLocation.y, 0);
                            newLocation.x = Math.min(newLocation.x, getParent().getWidth() - getWidth());
                            newLocation.y = Math.min(newLocation.y, getParent().getHeight() - getHeight());
                            setLocation(newLocation);
                            startpoint = location;
                        }
                    }
                }
                jComponent.setCursor(Cursor.getPredefinedCursor(cursor));
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

            startPos = null;
        }
    };
}
