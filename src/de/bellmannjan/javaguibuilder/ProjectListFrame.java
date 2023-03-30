package de.bellmannjan.javaguibuilder;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ProjectListFrame extends JDialog {

    public ProjectListFrame(JFrame owner, boolean modal) {
        super(owner, modal);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(owner);
        setTitle("Projekt auwählen");
        Container cp = getContentPane();
        cp.setLayout(null);
        setResizable(false);

        DefaultListModel projectListModel = new DefaultListModel();
        JList projectList = new JList(projectListModel);
        JScrollPane scrollPane = new JScrollPane(projectList);
        scrollPane.setBounds(40, 15, 300, 180);
        if(GUI.getMySQL().isConnected()) {
            GUI.getMySQL().createSQL("SELECT * FROM `project` WHERE `userid` = " + GUI.getUser().getUid());
            GUI.getMySQL().createTable();
            if(GUI.getMySQL().getList().size() != 0) {
                for (int row = 0; row < GUI.getMySQL().getList().size(); row++) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
                    LocalDateTime date = LocalDateTime.parse(GUI.getMySQL().getList().get(row).get(6));
                    projectListModel.addElement(GUI.getMySQL().getList().get(row).get(0) + ":" + GUI.getMySQL().getList().get(row).get(1) + " | " + date.format(formatter));
                }
            }
        }
        cp.add(scrollPane);

        JButton selectButton = new JButton();
        selectButton.setText("Auswählen");
        selectButton.setBounds(60, 220, 100, 30);
        selectButton.addActionListener(e -> {
            if(projectList.getSelectedIndex() != -1) {
                if(GUI.getMySQL().isConnected()) {
                    GUI.getMySQL().createSQL("SELECT * FROM `project` WHERE `userid` = " + GUI.getUser().getUid() + " AND `projectid`='" + projectList.getSelectedValue().toString().split(":")[0] + "'");
                    GUI.getMySQL().createTable();
                    GUI.setProject(new Project(Integer.parseInt(GUI.getMySQL().getList().get(0).get(0)), GUI.getMySQL().getList().get(0).get(1)));
                    GUI.getProject().openProject();
                    dispose();
                }
            }
        });
        cp.add(selectButton);

        JButton cancelButton = new JButton();
        cancelButton.setText("Abbrechen");
        cancelButton.setBounds(220, 220, 100, 30);
        cancelButton.addActionListener(e -> dispose());
        cp.add(cancelButton);

        setVisible(true);
    }
}
