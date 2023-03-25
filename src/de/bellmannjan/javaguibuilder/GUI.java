package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.FrameBuilder.AttributPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.CodeOutputPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.ComponentPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.GUIMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;

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

      KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
      manager.addKeyEventDispatcher(new KeyEventDispatcher() {
        /**
         * @Description alle Tasteneingaben der Tastatur werden hier abgefangen. Bei Taste "Entf" wird ausgewählte Komponente gelöscht.
         */
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
          if (e.getID() == KeyEvent.KEY_PRESSED) {
            if(e.getKeyCode() == 127) {
              if(getSession().getSelectedComponent() != null)
                getSession().removeResizableComponent(getSession().getSelectedComponent());
            }
          }
          return false;
        }
      });

      //Einstellungen an JFrame vornehmen
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setSize(Toolkit.getDefaultToolkit().getScreenSize());
      setLocationRelativeTo(null);
      setTitle("Java GUI Builder");
      setLayout(new BorderLayout());

      //Menüleiste
      GUIMenu guiMenu = new GUIMenu();
      setJMenuBar(guiMenu);
      requestFocus();

      //Content
      //Erstellen von 2 Tabs für Codegenerierung und GUI-Design
      JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

      JSplitPane designSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
      designSplitPane.setResizeWeight(.95);
      JPanel codePanel = new JPanel(new BorderLayout());

      tabbedPane.addTab("Design", designSplitPane);
      tabbedPane.addTab("Code", codePanel);
      add(tabbedPane, BorderLayout.CENTER);

      //Code-Fenster

      //Toolbar mit Button zum Ausführen und Generieren von Code
      JToolBar toolBar = new JToolBar("Optionen:", JToolBar.VERTICAL);
      toolBar.addSeparator();
      toolBar.setMargin(new Insets(5,0,0,5));

      JButton runButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/run.gif"))));
      runButton.setToolTipText("GUI starten");
      runButton.setMargin(new Insets(0,0,0,0));

      JButton outputButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/generate.gif"))));
      outputButton.setToolTipText("Code generieren");
      outputButton.setMargin(new Insets(0,0,0,0));

      toolBar.add(runButton);
      toolBar.add(outputButton);
      codePanel.add(toolBar, BorderLayout.LINE_START);

      //JEditorPane indem der Code fürs JFrame ausgegeben wird
      codeOutputPanel = new CodeOutputPanel();
      JScrollPane codeScrollPane = new JScrollPane(codeOutputPanel);
      codePanel.add(codeScrollPane, BorderLayout.CENTER);

      //Design-Fenster
      //Aufteilen in zwei Panel
      guiDeskPane = new JDesktopPane();
      guiDeskPane.setBackground(Color.WHITE);

      JSplitPane settingsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
      settingsSplitPane.setBackground(Color.LIGHT_GRAY);
      settingsSplitPane.setResizeWeight(.05);

      designSplitPane.setLeftComponent(guiDeskPane);
      designSplitPane.setRightComponent(settingsSplitPane);

      //Settings Panel aufteilen in zwei Panel für Attribute und Erstellen/Verwalten der Komponenten
      componentPanel = new ComponentPanel();
      attributPanel = new AttributPanel();

      settingsSplitPane.setTopComponent(attributPanel);
      settingsSplitPane.setBottomComponent(componentPanel);

      //Footer

      JPanel bottomPanel = new JPanel();
      JLabel copyrightText = new JLabel("\u00A92023 JavaGUIBuilder by Jan Bellmann");
      bottomPanel.add(copyrightText);
      add(bottomPanel, BorderLayout.PAGE_END);

      setExtendedState(JFrame.MAXIMIZED_BOTH);
      setVisible(true);
    });
  }
  public static void main(String[] args) {
    new GUI();
  }
}
