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


//Angepasstes Skript aus dem Internet
//Link: https://zetcode.com/javaswing/resizablecomponent/
public class ResizableBorder implements Border {

    private ResizableComponent resizableComponent;
    public ResizableBorder(ResizableComponent resizableComponent) {
        this.resizableComponent = resizableComponent;
    }

    @Override
    public Insets getBorderInsets(Component component) {
        return new Insets(0,0,0,0);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component component, Graphics g, int x, int y,
                            int w, int h) {


        if (resizableComponent.isClicked()) {

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

    private Rectangle getRectangle(int x, int y, int w, int h) {
        return new Rectangle(x + w -10, y + h -10, 10, 10);
    }

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