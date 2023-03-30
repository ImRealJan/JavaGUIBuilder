package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Angepasstes Skript aus dem Internet
 * Link: https://zetcode.com/javaswing/resizablecomponent/
 */
public abstract class ResizeableComponent extends JComponent {

    private final String componentType;
    protected JComponent resizeableComponent;

    private int componentid;

    private int cursor;
    private  Point startpoint;
    private Point startPos;

    /**
     * @param comp Komponente die Veränderbar werden soll
     * @param name Attributname für die Komponente
     */
    public ResizeableComponent(JComponent comp, String name, int id) {
        setLayout(new BorderLayout());
        add(comp);

        componentType = "J" + name;
        componentid = id;

        resizeableComponent = comp;

        int counter = 1;
        for (ResizeableComponent resizeableComponent1 : GUI.getProject().getResizableComponents()) {
            if(resizeableComponent1 == resizeableComponent) return;
            if(resizeableComponent1.getName().equals("j" + name + counter)) counter++;
        }
        setName("j" + name + counter);
    }

    /**
     * @param name Setzt den Namen der Komponente. Dabei wird überprüft ob der Name schon exisitiert.
     */
    @Override
    public void setName(String name) {
        for (ResizeableComponent resizeableComponent : GUI.getProject().getResizableComponents()) {
            if (resizeableComponent.getName().equals(name)) {
                return;
            }
        }
        super.setName(name);
    }

    /**
     * @return Die Komponente
     */
    private JComponent getComponent() {
        return this;
    }

    /**
     * Attribute der Komponente werden an Attributeinstellungen übergeben.(Weitere Spezifikation in Unterklassen)
     */
    public abstract void getAttributes();

    /**
     * Einträge aus Attributeinstellungen an Komponente übergeben. (Weitere Spezifikation in Unterklassen)
     */
    public abstract void updateAttributes();

    /**
     * @return Die Komponente mit allen Attributen (Schift, Größe...)
     */
    public abstract JComponent getComponentInformations();

    /**
     * @return Den Text der Komponente
     */
    public abstract String getText();

    public abstract void setText(String text);

    /**
     * @return Den Typ der Komponente
     */
    public String getComponentType() {
        return componentType;
    }

    /**
     * @return Die ID der Komponente
     */
    public int getComponentID() {
        return componentid;
    }

    /**
     * Hinzufügen von Maus-Events und der ResizeableBorder zur Komponente
     */
    public void init() {
        resizeableComponent.addMouseListener(new MouseAdapter() {
            /**
             * @Description Holen von Informationen (Border, Mausposition, Aktueller Cursor). Komponente als ausgewählte Komponente markieren
             */
            @Override
            public void mousePressed(MouseEvent e) {
                ResizeableBorder resizeableBorder = (ResizeableBorder) resizeableComponent.getBorder();
                cursor = resizeableBorder.getCursor(e);
                startPos = e.getPoint();
                startpoint = SwingUtilities.convertPoint(getComponent(), e.getPoint(), getParent());

                GUI.getProject().setSelectedComponent((ResizeableComponent)getComponent());
            }

            /**
             * @Description Cursor zum Standardcursor setzen, wenn Komponente verlassen wird
             */
            @Override
            public void mouseExited(MouseEvent e) {
                resizeableComponent.setCursor(Cursor.getDefaultCursor());
            }
        });
        resizeableComponent.addMouseMotionListener(new MouseMotionListener() {
            /**
             * Bei gedrückt halten der Maustaste wird die Komponente verändert/verschoben
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (startPos != null) {

                    int dx = e.getX() - startPos.x;
                    int dy = e.getY() - startPos.y;

                    Point location = SwingUtilities.convertPoint(getComponent(), e.getPoint(), getParent());
                    if(getParent() != null && getParent().getBounds().contains(location)) {

                        /**
                         * Switch/Case ob der Cursor "Resize-Cursor" o. "Move-Cursor" ist
                         */
                        switch (cursor) {

                            case Cursor.SE_RESIZE_CURSOR -> {

                                /**
                                 * Größe der Komponente ändern unter Beachtung, dass diese nicht kleiner als ihre Prefferierte Größe wird
                                 */
                                if (!(getWidth() + dx < getPreferredSize().getWidth()) && !(getHeight() + dy < getPreferredSize().getHeight())) {
                                    setBounds(getX(), getY(), getWidth() + dx, getHeight() + dy);
                                    startPos = e.getPoint();
                                    if (getParent() != null) {
                                        getParent().revalidate();
                                    }
                                }
                            }

                            case Cursor.MOVE_CURSOR -> {
                                /**
                                 * Position der Komponente ändern unter Beachtung, dass diese nicht außerhalb des Frames gezogen werden kann
                                 */
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
                    resizeableComponent.setCursor(Cursor.getPredefinedCursor(cursor));
                }
            }

            /**
             * @Description Beim bewegen der Maus wird der Cursor und die Border verändert wenn man über eine ausgewählte Komponente schwebt
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                if (GUI.getProject().getSelectedComponent() == getComponent()) {

                    ResizeableBorder resizeableBorder = (ResizeableBorder) resizeableComponent.getBorder();
                    resizeableComponent.setCursor(Cursor.getPredefinedCursor(resizeableBorder.getCursor(e)));
                }
            }
        });

        resizeableComponent.setBorder(new ResizeableBorder(this));
        resizeableComponent.setRequestFocusEnabled(false);
    }
}
