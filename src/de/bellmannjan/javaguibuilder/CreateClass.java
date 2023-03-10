package de.bellmannjan.javaguibuilder;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;


public class CreateClass extends JFrame {

  
  public CreateClass() {
    super();
    //Design an Anwendungsumgebung anpassen (ich mag das Java-Design nicht)
    EventQueue.invokeLater(() -> {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
               UnsupportedLookAndFeelException ex) {
        ex.printStackTrace();
      }
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
      JMenuItem openMenuItem = new JMenuItem("Öffnen");
      JMenuItem saveMenuItem = new JMenuItem("Speichern");

      JMenu bearbeitenMenu = new JMenu("Bearbeiten");

      JMenu hilfeMenu = new JMenu("Hilfe");
      JMenuItem openHelpMenuItem = new JMenuItem("Programmhilfe");
      JMenuItem openJavaHelpMenuItem = new JMenuItem("Javahilfe");


      menuBar.add(dateiMenu);
      dateiMenu.add(newMenuItem);
      dateiMenu.add(openMenuItem);
      dateiMenu.add(saveMenuItem);

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
          JLabel optionLabel = new JLabel("Optionen:");
          optionHeaderPanel.add(optionLabel);
          optionHeaderPanel.setBackground(Color.WHITE);
          optionDesktopPane.add(optionHeaderPanel, BorderLayout.PAGE_START);

          JPanel optionContentPanel = new JPanel();
          optionContentPanel.setBackground(Color.LIGHT_GRAY);

          JButton generateCodeButton = new JButton("Code generieren");
          optionContentPanel.add(generateCodeButton);
          optionDesktopPane.add(optionContentPanel, BorderLayout.CENTER);

          JTextArea codeOutputTextArea = new JTextArea();
          codeOutputTextArea.setText("Console console = System.console();\n" +
                  "\n" +
                  "if(console == null) {\n" +
                  "\tSystem.out.println(\"Console is not available to current JVM process\");\n" +
                  "\treturn;\n" +
                  "}\n" +
                  "\n" +
                  "Reader consoleReader = console.reader();\n" +
                  "Scanner scanner = new Scanner(consoleReader);\n" +
                  "\n" +
                  "System.out.println(\"Enter age:\");\n" +
                  "int age = scanner.nextInt();\n" +
                  "System.out.println(\"Entered age: \" + age);\n" +
                  "\n" +
                  "scanner.close();");
          JScrollPane outputScrollPanel = new JScrollPane(codeOutputTextArea);
          optionDesktopPane.setBackground(Color.LIGHT_GRAY);

          codeSplitPane.setLeftComponent(optionDesktopPane);
          codeSplitPane.setRightComponent(outputScrollPanel);
          codeSplitPane.setDividerLocation((int)(getWidth()*.10));

          codeOutputTextArea.setLineWrap(true);
          codeOutputTextArea.setEditable(false);



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
          attributHeaderPanel.setBackground(Color.WHITE);
          JLabel lattribute = new JLabel("Attribute festlegen:");
          attributHeaderPanel.add(lattribute);

          JPanel attributContentPanel = new JPanel();


          attributPanel.add(attributHeaderPanel,BorderLayout.PAGE_START);
          attributPanel.add(attributContentPanel, BorderLayout.CENTER);

          //ConpomentPanel Design
          componentPanel.setLayout(new BorderLayout());

          JPanel componentHeaderPanel = new JPanel();
          componentHeaderPanel.setBackground(Color.WHITE);
          JLabel lcomponent = new JLabel("Komponente auswählen:");
          componentHeaderPanel.add(lcomponent);

          JPanel componentContentPanel = new JPanel(new FlowLayout());
          JButton buttonAddText = new JButton("JLabel");
          JButton buttonAddButton = new JButton("JButton");
          JButton buttonAddTextField = new JButton("JTextField");
          componentContentPanel.add(buttonAddText);
          componentContentPanel.add(buttonAddButton);
          componentContentPanel.add(buttonAddTextField);


          componentPanel.add(componentHeaderPanel,BorderLayout.PAGE_START);
          componentPanel.add(componentContentPanel, BorderLayout.CENTER);

          //GUI Panel
          JInternalFrame internalFrame = new JInternalFrame("Meine GUI-Anwendung", true, false,false, false);
          guiDeskPane.add(internalFrame);
          internalFrame.setLocation(300,250);
          internalFrame.setSize(new Dimension(500,500));
          internalFrame.show();

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
    new CreateClass();
  }

}
