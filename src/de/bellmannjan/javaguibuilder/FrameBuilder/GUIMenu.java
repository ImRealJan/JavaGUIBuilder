package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

public class GUIMenu extends JMenuBar {

    private JMenuItem accountName;

    public JMenuItem getAccountName() {
        return accountName;
    }

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
        openHelpMenuItem.addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(URI.create("https://docs.oracle.com/en/java/javase/19/docs/api/index.html"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JMenuItem openJavaHelpMenuItem = new JMenuItem("Javahilfe");
        openJavaHelpMenuItem.addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(URI.create("https://docs.oracle.com/en/java/javase/19/docs/api/index.html"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JMenu accountMenu = new JMenu("Konto");
        accountName = new JMenuItem();
        JSeparator separator = new JSeparator();
        JMenuItem kontoDetailMenuItem = new JMenuItem("Kontodetails");
        kontoDetailMenuItem.addActionListener(e -> {
                new AccountFrame((JFrame) SwingUtilities.getRoot(this), true);
        });
        JMenuItem logoutMenuItem = new JMenuItem("Abmelden");
        logoutMenuItem.addActionListener(e -> {
            if(GUI.getProject() != null) {
                handleSaveOption();
            }
            GUI.setUser(null);
            GUI.getGuiMenu().getAccountName().setText("");
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
        boolean isCanceled= false;
        if(GUI.getProject() != null) {
           if(handleSaveOption()) isCanceled = true;
        }
        if(GUI.getMySQL().isConnected()) {
            if(!isCanceled)
                new ProjectListFrame((JFrame) SwingUtilities.getRoot(this), true);
        }
    }

    private void click_saveMenuItem(ActionEvent e) {
        if(GUI.getProject() != null) {
            GUI.getProject().saveProject();
            JOptionPane.showMessageDialog(null, "Projekt wurde gespeichert!");
        }else {
            JOptionPane.showMessageDialog(null, "Es ist kein Projekt geöffnet!");
        }
    }

    private void click_newMenuItem(ActionEvent e) {
        boolean isCanceled= false;
        if(GUI.getProject() != null) {
            if(handleSaveOption()) isCanceled = true;
        }
        if(!isCanceled) {
            String projectname = JOptionPane.showInputDialog("Gebe einen Projektnamen ein:");
            if(projectname!= null) {
                if(projectname.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Projektname darf kein Leerzeichen enthalten!");
                    return;
                }
                if(GUI.getMySQL().isConnected()) {
                    GUI.getMySQL().createSQL("SELECT MAX(`projectid`) FROM `project`;");
                    try {
                        if (GUI.getMySQL().getResultSet().next()) {
                            int id = 1;
                            if(GUI.getMySQL().getResultSet().getObject(1) != null) {
                                id = GUI.getMySQL().getResultSet().getInt(1) + 1;
                            }
                            GUI.setProject(new Project(id, projectname));
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
    public void click_exitMenuItem(ActionEvent e) {
        if(GUI.getProject() != null) {
            handleSaveOption();
        }else {
            JOptionPane.showMessageDialog(null, "Es ist kein Projekt geöffnet!");
        }
    }

    public boolean handleSaveOption() {
        int answer = JOptionPane.showConfirmDialog(null, "Das Projekt wurde noch nicht gespeichert!\nJetzt speichern?", "", JOptionPane.YES_NO_CANCEL_OPTION);
        if(answer == JOptionPane.YES_OPTION) {
            GUI.getProject().saveProject();
        }
        if(answer == JOptionPane.CANCEL_OPTION) {
            return true;
        }
        GUI.getProject().closeSession();
        GUI.setProject(null);
        return false;
    }
}
