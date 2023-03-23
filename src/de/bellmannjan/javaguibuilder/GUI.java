package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.FrameBuilder.AttributPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.CodeOutputPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.ComponentPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.GUIMenu;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class GUI extends JFrame {

  private static Session session;
  private static JDesktopPane guiDeskPane;
  private static AttributPanel attributPanel;
  private static CodeOutputPanel codeOutputPanel;
  private static ComponentPanel componentPanel;

  public static AttributPanel getAttributPanel() {
    return attributPanel;
  }
  public static CodeOutputPanel getCodeOutputPanel() {
    return codeOutputPanel;
  }
  public static ComponentPanel getComponentPanel() {
    return componentPanel;
  }
  public static JDesktopPane getFramePanel() {
    return guiDeskPane;
  }


  public static Session getSession() {
    return session;
  }
  public static void setSession(Session session) {
    GUI.session = session;
  }


  /**
   * In dieser Klasse wird das Design des Hauptprogramm-Fensters festgelegt.
   */

  public GUI() {
    super();
    EventQueue.invokeLater(() -> {

      //Einstellungen an JFrame vornehmen
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setSize(Toolkit.getDefaultToolkit().getScreenSize());
      setLocationRelativeTo(null);
      setTitle("Java GUI Builder");
      setLayout(new BorderLayout());

      //Menu
      GUIMenu guiMenu = new GUIMenu();
      setJMenuBar(guiMenu);

      //Content
      //Erstellen von 2 Tabs für Codegenerierung und GUI-Design
      JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

      JSplitPane designSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
      JSplitPane codeSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

      tabbedPane.addTab("Design", designSplitPane);
      tabbedPane.addTab("Code", codeSplitPane);
      add(tabbedPane, BorderLayout.CENTER);

      //Code-Fenster
      JDesktopPane optionDesktopPane = new JDesktopPane();
      optionDesktopPane.setLayout(new BorderLayout());

      //Linke Seite
      JPanel optionHeaderPanel = new JPanel();
      optionHeaderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
      JLabel optionLabel = new JLabel("Optionen:");
      optionLabel.setFont(new Font("Arial", Font.BOLD, 12));
      optionHeaderPanel.add(optionLabel);
      optionDesktopPane.add(optionHeaderPanel, BorderLayout.PAGE_START);

      JPanel optionContentPanel = new JPanel();

      JButton generateCodeButton = new JButton("Code generieren");
      optionContentPanel.add(generateCodeButton);
      JButton runCodeButton = new JButton("Debug JFrame");
      optionContentPanel.add(runCodeButton);
      optionDesktopPane.add(optionContentPanel, BorderLayout.CENTER);


      //Rechte Seite

      codeOutputPanel = new CodeOutputPanel();
      JScrollPane outputScrollPanel = new JScrollPane(codeOutputPanel);
      outputScrollPanel.setMinimumSize(new Dimension(1000,1000));

      optionDesktopPane.setBackground(Color.LIGHT_GRAY);

      codeSplitPane.setLeftComponent(optionDesktopPane);
      codeSplitPane.setRightComponent(outputScrollPanel);
      optionDesktopPane.setMinimumSize(new Dimension(200,300));


      //Design-Fenster
      //Aufteilen in zwei Fenster
      guiDeskPane = new JDesktopPane();
      guiDeskPane.setBackground(Color.WHITE);
      guiDeskPane.setMinimumSize(new Dimension(1000,500));

      JSplitPane settingsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
      settingsSplitPane.setBackground(Color.LIGHT_GRAY);

      designSplitPane.setResizeWeight(.95);
      settingsSplitPane.setMinimumSize(new Dimension(500,500));

      designSplitPane.setLeftComponent(guiDeskPane);
      designSplitPane.setRightComponent(settingsSplitPane);

      //Settings Panel aufteilen in zwei Panel für Eigenschaften und Component Builder
      componentPanel = new ComponentPanel();
      componentPanel.setMinimumSize(new Dimension(500,300));
      attributPanel = new AttributPanel();

      settingsSplitPane.setResizeWeight(.05);

      settingsSplitPane.setTopComponent(attributPanel);
      settingsSplitPane.setBottomComponent(componentPanel);

      //Footer

      JPanel bottomPanel = new JPanel();
      JLabel copyrightText = new JLabel("\u00A92023 JavaGUIBuilder by Jan Bellmann");
      copyrightText.setAlignmentX(SwingConstants.LEFT);
      bottomPanel.add(copyrightText);
      add(bottomPanel, BorderLayout.PAGE_END);

      setExtendedState(JFrame.MAXIMIZED_BOTH);
      setVisible(true);
    });
  }
  public static void main(String[] args) {
    /**
     *     try {
     *       UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
     *     } catch(Exception ignored){}
     */
    new GUI();
  }
}
