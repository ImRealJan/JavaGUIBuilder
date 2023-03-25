package de.bellmannjan.javaguibuilder.Components;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;


/**
 * Angepasstes Skript aus dem Internet
 * Link: https://zetcode.com/javaswing/resizablecomponent/
 */
public class ResizeableBorder implements Border {

    private final ResizeableComponent resizeableComponent;

    /**
     * @param resizeableComponent Übergeben der Komponente die Veränderbar ist (Größer/Kleiner)
     */
    public ResizeableBorder(ResizeableComponent resizeableComponent) {
        this.resizeableComponent = resizeableComponent;
    }

    /**
     * @param component the component for which this border insets value applies
     * @return Der Rand bekommt keinen extra Abstand (ist direkt an die Komponente gezeichnet)
     */
    @Override
    public Insets getBorderInsets(Component component) {
        return new Insets(0,0,0,0);
    }

    /**
     * @return Undurchsichtigkeit
     */
    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    /**
     * @param component the component for which this border is being painted
     * @param g         the paint graphics
     * @param x         the x position of the painted border
     * @param y         the y position of the painted border
     * @param w         the width of the painted border
     * @param h         the height of the painted border
     * @Description Wenn die Komponente ausgewählt ist wird der Auswahlrand erzeugt, womit die Komponente verändert werden kann.
     */
    @Override
    public void paintBorder(Component component, Graphics g, int x, int y,
                            int w, int h) {

        if(GUI.getSession() != null) {
            if (GUI.getSession().getSelectedComponent() == resizeableComponent) {

                g.setColor(Color.black);
                g.drawRect(x / 2, y / 2, w - 1, h - 1);

                Rectangle rect = getRectangle(x, y, w, h);

                g.setColor(Color.darkGray);
                g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
                g.setColor(Color.black);
                g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }else {
                if(component instanceof JButton || component instanceof  JTextField) {
                    g.setColor(Color.gray);
                    g.drawRect(x / 2, y / 2, w - 1, h - 1);
                }
            }
        }
    }

    /**
     * @param x
     * @param y
     * @param w
     * @param h
     * @return Ein Viereck an dem die Größe der Kompenente verändert werden kann
     */
    private Rectangle getRectangle(int x, int y, int w, int h) {
        return new Rectangle(x + w -10, y + h -10, 10, 10);
    }

    /**
     * @param me
     * @return Einen "Move-Cursor" wenn die Maus über die Komponente ist oder einen "Resize-Cursor" wenn die Maus über dem Viereck ist
     */
    public int getCursor(MouseEvent me) {

        Component c = me.getComponent();
        int w = c.getWidth();
        int h = c.getHeight();

            Rectangle rect = getRectangle(0, 0, w, h);
            if (rect.contains(me.getPoint())) {
                return Cursor.SE_RESIZE_CURSOR;
            }

        return Cursor.MOVE_CURSOR;
    }
}