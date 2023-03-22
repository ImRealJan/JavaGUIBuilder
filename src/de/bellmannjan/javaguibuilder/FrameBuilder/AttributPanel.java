package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class AttributPanel extends JPanel {

    private JPanel attributContentPanel;

    public JPanel getContentPanel() {
        return attributContentPanel;
    }

    public AttributPanel() {
        setLayout(new BorderLayout());

        JPanel attributHeaderPanel = new JPanel();
        attributHeaderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JLabel lattribute = new JLabel("Attribute festlegen:");
        lattribute.setFont(new Font("Arial", Font.BOLD, 14));
        attributHeaderPanel.add(lattribute);

        attributContentPanel = new JPanel();
        attributContentPanel.setBorder(BorderFactory.createTitledBorder("Attribute:"));


        add(attributHeaderPanel,BorderLayout.PAGE_START);
        add(attributContentPanel, BorderLayout.CENTER);
    }
}
