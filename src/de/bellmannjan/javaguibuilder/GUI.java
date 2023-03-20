package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Components.ResizableComponent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Objects;

public class GUI extends JFrame {

  private static Session session;

  private JFileChooser openChooser = new JFileChooser();
  private JFileChooser saveChooser = new JFileChooser();

  /**
   * In dieser Klasse wird das Design des Hauptprogramm-Fensters festgelegt.
   */

  public static Session getSession() {
    return session;
  }
  public GUI() {
    super();
    //Design an Anwendungsumgebung anpassen (ich mag das Java-Design nicht)
    EventQueue.invokeLater(() -> {

      //Einstellungen an JFrame vornehmen
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setSize(Toolkit.getDefaultToolkit().getScreenSize());
      setLocationRelativeTo(null);
      setTitle("Java GUI Builder");
      setLayout(new BorderLayout());

      //Menu
      JMenuBar menuBar = new JMenuBar();

      JMenu dateiMenu = new JMenu("Datei");
      JMenuItem newMenuItem = new JMenuItem("Neu");
      newMenuItem.addActionListener(e -> click_newMenuItem(e));
      JMenuItem openMenuItem = new JMenuItem("Öffnen");
      openMenuItem.addActionListener(e -> click_openMenuItem(e));
      JMenuItem saveMenuItem = new JMenuItem("Speichern");
      saveMenuItem.addActionListener(e -> click_saveMenuItem(e));
      JMenuItem exitMenuItem = new JMenuItem("Schließen");
      exitMenuItem.addActionListener(e -> click_exitMenuItem(e));

      JMenu bearbeitenMenu = new JMenu("Bearbeiten");

      JMenu hilfeMenu = new JMenu("Hilfe");
      JMenuItem openHelpMenuItem = new JMenuItem("Programmhilfe");
      JMenuItem openJavaHelpMenuItem = new JMenuItem("Javahilfe");


      menuBar.add(dateiMenu);
      dateiMenu.add(newMenuItem);
      dateiMenu.add(openMenuItem);
      dateiMenu.add(saveMenuItem);
      dateiMenu.add(exitMenuItem);

      menuBar.add(bearbeitenMenu);

      menuBar.add(hilfeMenu);
      hilfeMenu.add(openHelpMenuItem);
      hilfeMenu.add(openJavaHelpMenuItem);

      setJMenuBar(menuBar);

      //Content
        //Erstellen von 2 Tabs für Codegenerierung und GUI-Design
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        JSplitPane designSplitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane codeSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabbedPane.addTab("Design", designSplitPane);
        tabbedPane.addTab("Code", codeSplitPane);
        add(tabbedPane, BorderLayout.CENTER);

          //Code-Fenster
          JDesktopPane optionDesktopPane = new JDesktopPane();
          optionDesktopPane.setLayout(new BorderLayout());

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

          JEditorPane codeOutputEditorPane = new JEditorPane();
          codeOutputEditorPane.setContentType("text/html");
          codeOutputEditorPane.setEditable(false);
          codeOutputEditorPane.setText("<html>Console console = System.console();<br>" +
                  "<br>" +
                  "if(console == null) {<br>" +
                  "&emsp;System.out.println(\"Console is not available to current JVM process\")<br>" +
                  "&emsp;return;<br>" +
                  "}<br>" +
                  "<br>" +
                  "Reader consoleReader = console.reader();<br>" +
                  "Scanner scanner = new Scanner(consoleReader);<br>" +
                  "<br>" +
                  "System.out.println(\"Enter age:\");<br>" +
                  "int age = scanner.nextInt();<br>" +
                  "System.out.println(\"Entered age: \" + age);<br>" +
                  "<br>" +
                  "scanner.close();</html>");
          JScrollPane outputScrollPanel = new JScrollPane(codeOutputEditorPane);
          optionDesktopPane.setBackground(Color.LIGHT_GRAY);

          codeSplitPane.setLeftComponent(optionDesktopPane);
          codeSplitPane.setRightComponent(outputScrollPanel);
          codeSplitPane.setDividerLocation((int)(getWidth()*.10));




          //Design-Fenster
          JDesktopPane guiDeskPane = new JDesktopPane();
          JSplitPane settingsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
          guiDeskPane.setBackground(Color.WHITE);
          settingsSplitPane.setBackground(Color.LIGHT_GRAY);

          int xLocation = (int) (getWidth()*.75);
          designSplitPane.setDividerLocation(xLocation);

          designSplitPane.setLeftComponent(guiDeskPane);
          designSplitPane.setRightComponent(settingsSplitPane);

          //Settings Panel aufteilen in zwei Panel für Eigenschaften und Component Builder
          JPanel componentPanel = new JPanel();
          JPanel attributPanel = new JPanel();

          settingsSplitPane.setDividerLocation(getHeight()/2);

          settingsSplitPane.setTopComponent(attributPanel);
          settingsSplitPane.setBottomComponent(componentPanel);

          //AttributPanel Design
          attributPanel.setLayout(new BorderLayout());

          JPanel attributHeaderPanel = new JPanel();
          attributHeaderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
          JLabel lattribute = new JLabel("Attribute festlegen:");
          lattribute.setFont(new Font("Arial", Font.BOLD, 12));
          attributHeaderPanel.add(lattribute);

          JPanel attributContentPanel = new JPanel();

          attributPanel.add(attributHeaderPanel,BorderLayout.PAGE_START);
          attributPanel.add(attributContentPanel, BorderLayout.CENTER);

          //ConpomentPanel Design
          componentPanel.setLayout(new BorderLayout());

          JPanel componentHeaderPanel = new JPanel();
          componentHeaderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
          JLabel lcomponent = new JLabel("Objekt erstellen:");
          lcomponent.setFont(new Font("Arial", Font.BOLD, 12));
          componentHeaderPanel.add(lcomponent);

          JPanel componentContentPanel = new JPanel(new FlowLayout());

          JButton buttonAddText = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/componentIcons/jlabel.gif"))));
          buttonAddText.setToolTipText("JLabel");
          JButton buttonAddButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/componentIcons/jbutton.gif"))));
          buttonAddButton.setToolTipText("JButton");
          JButton buttonAddTextField = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/componentIcons/jtextfield.gif"))));
          buttonAddTextField.setToolTipText("JTextField");
          componentContentPanel.add(buttonAddText);
          componentContentPanel.add(buttonAddButton);
          componentContentPanel.add(buttonAddTextField);


          buttonAddText.addActionListener(e -> {
            ResizableComponent resizableComponent = new ResizableComponent(new JLabel("Text"), false);
            resizableComponent.setBounds(120, 120, 100, 50);
            session.handleComponentClick();
            session.getResizableComponents().add(resizableComponent);
            session.getInternalFrame().getContentPane().add(resizableComponent);
            session.getInternalFrame().updateUI();
          });

          for (Component component  : componentContentPanel.getComponents()) {
            if(component instanceof JButton button) {
              button.setMargin(new Insets(0,0,0,0));

              //TODO add Component function but object has to be new
            }
          }

          componentPanel.add(componentHeaderPanel,BorderLayout.PAGE_START);
          componentPanel.add(componentContentPanel, BorderLayout.CENTER);

          //GUI Panel
          session = new Session();
          guiDeskPane.add(session.getInternalFrame());
        //Footer

        JPanel bottomPanel = new JPanel();
        JLabel copyrightText = new JLabel("©2023 JavaGUIBuilder by Jan Bellmann");
        copyrightText.setAlignmentX(SwingConstants.LEFT);
        bottomPanel.add(copyrightText);
        add(bottomPanel, BorderLayout.PAGE_END);

      setExtendedState(JFrame.MAXIMIZED_BOTH);
      setVisible(true);
    });
  }
  public static void main(String[] args) {
    new GUI();
  }
  private void click_openMenuItem(ActionEvent e) {
    /**try {
      openChooser.removeChoosableFileFilter(openChooser.getAcceptAllFileFilter());
      FileFilter filter = new FileFilter(){
        public boolean accept(File f){
          if(f.isDirectory()) return true;
          else return f.getName().endsWith(".jfrm");
        }
        public String getDescription(){
          return "Java GUI Builder";
        }
      };
      openChooser.setFileFilter(filter);
      if (openChooser.showOpenDialog(openChooser) == JFileChooser.APPROVE_OPTION) {
        File file = openChooser.getSelectedFile();
      }
    }catch (Exception ex) {
      ex.printStackTrace();
    }*/
  }

  private void click_saveMenuItem(ActionEvent e) {
    /**try {
      saveChooser.removeChoosableFileFilter(saveChooser.getAcceptAllFileFilter());
      FileFilter filter = new FileFilter(){
        public boolean accept(File f){
          if(f.isDirectory()) return true;
          else return f.getName().endsWith(".jfrm");
        }
        public String getDescription(){
          return "Java GUI Builder";
        }
      };
      saveChooser.setFileFilter(filter);
      if (saveChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        File file = saveChooser.getSelectedFile();
        if (file == null) {
          return;
        }
        if (!file.getName().toLowerCase().endsWith(".jfrm")) {
          file = new File(file.getParentFile(), file.getName() + ".jfrm");
        }
        if (!file.createNewFile()) {
          JOptionPane.showMessageDialog(saveChooser, "Diese Datei existiert schon!");
        }
      }
    }catch (Exception ex) {
      ex.printStackTrace();
    }*/
  }

  private void click_newMenuItem(ActionEvent e) {
    //TODO speicher abfrage
    //TODO danach
    session.closeSession();
  }
  public void click_exitMenuItem(ActionEvent event) {
    //ToDO speicher abfrage
    dispose();
  }
}
