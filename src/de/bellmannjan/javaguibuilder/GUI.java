package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.FrameBuilder.AttributPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.CodeOutputPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.ComponentPanel;
import de.bellmannjan.javaguibuilder.FrameBuilder.GUIMenu;
import de.bellmannjan.javaguibuilder.Tools.JavaCodeGenerator;
import de.bellmannjan.javaguibuilder.Tools.MySQL;
import de.bellmannjan.javaguibuilder.Tools.User;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUI extends JFrame {

  private static Project project;
  private static User user;
  private static MySQL mySQL;

  private static GUIMenu guiMenu;
  private static JDesktopPane guiDeskPane;
  private static AttributPanel attributPanel;
  private static CodeOutputPanel codeOutputPanel;
  private static ComponentPanel componentPanel;
  private static JTabbedPane tabbedPane;

  public static GUIMenu getGuiMenu() {
    return guiMenu;
  }
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
  public static JTabbedPane getTabbedPane() {
    return tabbedPane;
  }
  public static Project getProject() {
    return project;
  }
  public static void setProject(Project project) {
    GUI.project = project;
  }
  public JFrame getInstance() {
    return this;
  }
  public static MySQL getMySQL() {
    return mySQL;
  }
  public static User getUser() {
    return user;
  }
  public static void setUser(User user) {
    GUI.user = user;
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
              if(getProject().getSelectedComponent() != null)
                getProject().removeResizableComponent(getProject().getSelectedComponent());
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
      List<Image> icons = new ArrayList<>();
      icons.add(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/iconTaskBar.gif"))).getImage());
      icons.add(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/iconWindow.gif"))).getImage());
      setIconImages(icons);

      //Menüleiste
      guiMenu = new GUIMenu();
      setJMenuBar(guiMenu);
      requestFocus();

      //Content
      //Erstellen von 2 Tabs für Codegenerierung und GUI-Design
      tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

      JPanel designPanel = new JPanel(new BorderLayout());
      JPanel codePanel = new JPanel(new BorderLayout());

      tabbedPane.addTab("Design", designPanel);
      tabbedPane.addTab("Code", codePanel);
      tabbedPane.setEnabledAt(1, false);
      add(tabbedPane, BorderLayout.CENTER);

      //Code-Fenster

      //Toolbar mit Button zum Ausführen und Generieren von Code
      JToolBar toolBar = new JToolBar("Optionen:", JToolBar.HORIZONTAL);
      toolBar.addSeparator();
      toolBar.setMargin(new Insets(5,0,0,5));

      JButton runButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/run.gif"))));
      runButton.setToolTipText("GUI starten");
      runButton.addActionListener(e -> {
        if(getProject() != null) {
          getProject().runTestFrame(getInstance());
        }
      });
      runButton.setMargin(new Insets(0,0,0,0));

      JButton outputButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/generate.gif"))));
      outputButton.setToolTipText("Code generieren");
      outputButton.addActionListener(e -> {
        if(getProject() != null) {
          new JavaCodeGenerator().generateCode();
        }
      });
      outputButton.setMargin(new Insets(0,0,0,0));

      toolBar.add(runButton);
      toolBar.add(outputButton);
      codePanel.add(toolBar, BorderLayout.PAGE_START);

      //JEditorPane indem der Code fürs JFrame ausgegeben wird
      codeOutputPanel = new CodeOutputPanel();
      JScrollPane codeScrollPane = new JScrollPane(codeOutputPanel);
      codePanel.add(codeScrollPane, BorderLayout.CENTER);

      //Design-Fenster
      //Aufteilen in zwei Panel
      guiDeskPane = new JDesktopPane();
      guiDeskPane.setBackground(Color.WHITE);

      JToolBar toolBar2 = new JToolBar("Einstellungen:", JToolBar.VERTICAL);
      toolBar2.addSeparator();
      toolBar2.setMargin(new Insets(5,0,0,5));
      toolBar2.setRollover(true);

      JPanel settingsPanel = new JPanel(new BorderLayout());

      toolBar2.add(settingsPanel);

      designPanel.add(guiDeskPane, BorderLayout.CENTER);
      designPanel.add(toolBar2, BorderLayout.LINE_END);

      //Settings Panel aufteilen in zwei Panel für Attribute und Erstellen/Verwalten der Komponenten
      componentPanel = new ComponentPanel();
      componentPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
      attributPanel = new AttributPanel();
      attributPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), BorderFactory.createBevelBorder(BevelBorder.LOWERED)));

      settingsPanel.add(attributPanel, BorderLayout.PAGE_START);
      settingsPanel.add(componentPanel, BorderLayout.CENTER);

      //Footer

      JPanel bottomPanel = new JPanel();
      JLabel copyrightText = new JLabel("\u00A92023 JavaGUIBuilder by Jan Bellmann");
      bottomPanel.add(copyrightText);
      add(bottomPanel, BorderLayout.PAGE_END);

      addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          if(getProject() != null) {
            int answer = JOptionPane.showConfirmDialog(null, "Das Projekt wurde noch nicht gespeichert!\nJetzt speichern?", "", JOptionPane.YES_NO_CANCEL_OPTION);
            if(answer == JOptionPane.YES_OPTION) {
              GUI.getProject().saveProject();
            }
            if(answer == JOptionPane.CANCEL_OPTION)
              return;
            getProject().closeSession();
            setProject(null);
          }
        }
      });

      setExtendedState(JFrame.MAXIMIZED_BOTH);
      setVisible(true);

      mySQL = new MySQL("javaguibuilder");
      if(mySQL.connect()) {
        new LoginFrame(this, true);
      }else {
        JOptionPane.showMessageDialog(null, "Keine Verbindung zur Datenbank!");
        this.dispose();
      }
    });
  }


  public static void main(String[] args) {
    new GUI();
  }
}
