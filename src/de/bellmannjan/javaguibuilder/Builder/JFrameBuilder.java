package de.bellmannjan.javaguibuilder.Builder;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JFrameBuilder {

    private final JFrame frame;

    private final String title;
    private LayoutManager manager;
    private boolean resizable = false;


    public JFrameBuilder(String title, int frameHeight, int frameWidth) {
        this.frame = new JFrame();
        this.title = title;

        frame.setSize(frameWidth, frameHeight);
    }
    public JFrameBuilder(String title, Screensize screen) {
        this.frame = new JFrame();
        this.title = title;

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        switch (screen) {
            case FULLSCREEN -> frame.setSize(d.width, d.height);
            case WINDOWED -> frame.setSize(d.width/2, d.height/2);

        }
    }
    public JFrameBuilder setLayout(LayoutManager manager) {
        this.manager = manager;
        return this;
    }
    public JFrameBuilder setBackgroundPicture(Image image) {

        JPanel backgroundPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, null);
            }
        };
        backgroundPanel.setOpaque(false);
        frame.setContentPane(backgroundPanel);
        return this;
    }
    public JFrameBuilder setIcon(String source) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(source)));
        frame.setIconImage(icon.getImage());
        return this;
    }
    public JFrameBuilder setResizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public JFrame build() {
        frame.setTitle(title);

        frame.setLayout(manager);
        frame.setResizable(resizable);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        return frame;
    }
}
