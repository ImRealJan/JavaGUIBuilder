package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Builder.JButtonAsText;
import de.bellmannjan.javaguibuilder.Builder.JFrameBuilder;
import de.bellmannjan.javaguibuilder.Builder.Screensize;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BetterStartScreen {

    private final ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/background.png")));
    private final Image imageBackground = background.getImage();

    public BetterStartScreen() {
        super();
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
            init();
        });
    }

    public void init() {

        JFrame frame = new JFrameBuilder("Java GUI Builder", Screensize.WINDOWED)
                .setResizable(false)
                .setLayout(new GridLayout(4,1))
                .setBackgroundPicture(imageBackground)
                .setIcon("../images/icon.gif")
                .build();

        //Placeholder
        JPanel pPlaceholder = new JPanel();
        pPlaceholder.setOpaque(false);
        frame.getContentPane().add(pPlaceholder);


        //Header
        JPanel pHeader = new JPanel();
        pHeader.setOpaque(false);
        pHeader.setLayout(new BoxLayout(pHeader, BoxLayout.PAGE_AXIS));

        JLabel lTitle = new JLabel("<html><p>Java <b>GUI</b> Builder</p></html>");
        lTitle.setFont(new Font("Arial", Font.PLAIN, 40));
        lTitle.setForeground(Color.WHITE);
        lTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pHeader.add(lTitle);

        JLabel version = new JLabel("Version 1.0");
        version.setFont(new Font("Arial", Font.PLAIN, 20));
        version.setForeground(new Color(0xC0C0C0));
        version.setAlignmentX(Component.CENTER_ALIGNMENT);
        pHeader.add(version);

        frame.getContentPane().add(pHeader);


        //Content
        JPanel pContent = new JPanel();
        pContent.setOpaque(false);
        pContent.setLayout(new BoxLayout(pContent, BoxLayout.PAGE_AXIS));

        JButton bCreateClass = new JButtonAsText(Color.WHITE, Color.GRAY, "../images/Icon material-desktop-windows.png")
                .setText("Klasse erstellen")
                .addCreateFileFuntion()
                .toText();
        bCreateClass.setFont(new Font("Arial", Font.PLAIN, 20));
        bCreateClass.setAlignmentX(Component.CENTER_ALIGNMENT);
        pContent.add(bCreateClass);

        JButton bOpenClass = new JButtonAsText(Color.WHITE, Color.GRAY, "../images/Icon material-folder-open.png")
                .setText("Datei Öffnen")
                .addOpenFileFunction()
                .toText();
        bOpenClass.setFont(new Font("Arial", Font.PLAIN, 20));
        bOpenClass.setAlignmentX(Component.CENTER_ALIGNMENT);
        pContent.add(bOpenClass);

        frame.getContentPane().add(pContent);


        //Footer
        JPanel pFooter = new JPanel();
        pFooter.setOpaque(false);
        pFooter.setLayout(new BorderLayout());

        JButton bImportClass = new JButtonAsText(Color.WHITE, Color.GRAY, "../images/Icon awesome-file-import.png")
                .setText("Importieren (Java-Editor)")
                .addImportFileFuntion()
                .toText();
        bImportClass.setFont(new Font("Arial", Font.PLAIN, 10));
        pFooter.add(bImportClass, BorderLayout.PAGE_END);

        frame.getContentPane().add(pFooter);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BetterStartScreen();
    }
}
