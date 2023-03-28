package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.AccountFrame;
import de.bellmannjan.javaguibuilder.GUI;
import de.bellmannjan.javaguibuilder.LoginFrame;
import de.bellmannjan.javaguibuilder.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUIMenu extends JMenuBar {

    private JMenuItem accountName;

    public JMenuItem getAccountName() {
        return accountName;
    }

    private JFileChooser openChooser = new JFileChooser();
    private JFileChooser saveChooser = new JFileChooser();

    public GUIMenu() {

        JMenu dateiMenu = new JMenu("Datei");
        JMenuItem newMenuItem = new JMenuItem("Neues Projekt");
        newMenuItem.addActionListener(e -> click_newMenuItem(e));
        JMenuItem openMenuItem = new JMenuItem("Projekt öffnen");
        openMenuItem.addActionListener(e -> click_openMenuItem(e));
        JMenuItem saveMenuItem = new JMenuItem("Projekt speichern");
        saveMenuItem.addActionListener(e -> click_saveMenuItem(e));
        JMenuItem exitMenuItem = new JMenuItem("Projekt schließen");
        exitMenuItem.addActionListener(e -> click_exitMenuItem(e));

        JMenu hilfeMenu = new JMenu("Hilfe");
        JMenuItem openHelpMenuItem = new JMenuItem("Programmhilfe");
        JMenuItem openJavaHelpMenuItem = new JMenuItem("Javahilfe");

        JMenu accountMenu = new JMenu("Konto");
        accountName = new JMenuItem();
        JSeparator separator = new JSeparator();
        JMenuItem kontoDetailMenuItem = new JMenuItem("Kontodetails");
        kontoDetailMenuItem.addActionListener(e -> {
                new AccountFrame((JFrame) SwingUtilities.getRoot(this), true);
        });
        JMenuItem logoutMenuItem = new JMenuItem("Abmelden");
        logoutMenuItem.addActionListener(e -> {
            if(GUI.getSession() != null) {
                GUI.getSession().closeSession();
                GUI.setSession(null);
            }
            GUI.setUser(null);
            new LoginFrame((JFrame) SwingUtilities.getRoot(this), true);
        });


        add(dateiMenu);
        dateiMenu.add(newMenuItem);
        dateiMenu.add(openMenuItem);
        dateiMenu.add(saveMenuItem);
        dateiMenu.add(exitMenuItem);

        add(hilfeMenu);
        hilfeMenu.add(openHelpMenuItem);
        hilfeMenu.add(openJavaHelpMenuItem);

        add(accountMenu);
        accountMenu.add(accountName);
        accountMenu.add(separator);
        accountMenu.add(kontoDetailMenuItem);
        accountMenu.add(logoutMenuItem);
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
        if(GUI.getSession() == null) {
            GUI.setSession(new Session());
        }else {
            //TODO speicher abfrage
            JOptionPane.showConfirmDialog(null, "Das Projekt wurde noch nicht gespeichert!", "", JOptionPane.OK_CANCEL_OPTION);
        }
    }
    public void click_exitMenuItem(ActionEvent e) {
        if(GUI.getSession() != null) {
            //ToDO speicher abfrage danach:
            GUI.getSession().closeSession();
            GUI.setSession(null);
        }else {
            JOptionPane.showMessageDialog(null, "Es ist kein Projekt geöffnet!");
        }
    }
}
