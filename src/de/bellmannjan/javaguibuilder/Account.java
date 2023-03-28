package de.bellmannjan.javaguibuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

//TODO hübsch machen
public class Account extends JDialog {

    JPanel loginPanel;
    public Account(JFrame owner, boolean modal) {
        super(owner, modal);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(owner);
        setTitle("Accountdetails");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setResizable(false);

        JPanel header = new JPanel();
        JLabel title = new JLabel(GUI.getUser().getUsername().toUpperCase());
        title.setBorder(new EmptyBorder(10,0,0,0));
        title.setFont(new Font("Arial", Font.BOLD, 40));
        header.add(title);
        cp.add(header, BorderLayout.PAGE_START);

        JPanel loginPanel = new JPanel(null);


        JTextField usernameInput = new JTextField(GUI.getUser().getUsername());
        usernameInput.setBounds(92, 20, 200, 30);
        usernameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(usernameInput.getText().contains(" ") || usernameInput.getText().length() > 16) {
                    usernameInput.setBackground(Color.red);
                }else {
                    usernameInput.setBackground(Color.white);
                }
            }
        });
        loginPanel.add(usernameInput);

        JPasswordField passwordInput = new JPasswordField(GUI.getUser().getPassword());
        char passwordChar = passwordInput.getEchoChar();
        passwordInput.setBounds(92, 60, 200, 30);
        passwordInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(passwordInput.getText().equals("Passwort")) {
                    passwordInput.setText("");
                    passwordInput.setForeground(Color.black);
                    passwordInput.setEchoChar(passwordChar);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(passwordInput.getText().equals("")) {
                    passwordInput.setText("Passwort");
                    passwordInput.setForeground(Color.gray);
                    passwordInput.setEchoChar((char)0);
                }
            }
        });
        loginPanel.add(passwordInput);

        JPasswordField passwordInput2 = new JPasswordField(GUI.getUser().getPassword());
        char passwordChar2 = passwordInput2.getEchoChar();
        passwordInput2.setBounds(92, 100, 200, 30);
        passwordInput2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!passwordInput2.getText().equals(passwordInput.getText())) {
                    passwordInput2.setBackground(Color.red);
                }else {
                    passwordInput2.setBackground(Color.white);
                }
            }
        });
        passwordInput2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(passwordInput2.getText().equals("Passwort")) {
                    passwordInput2.setText("");
                    passwordInput2.setForeground(Color.black);
                    passwordInput2.setEchoChar(passwordChar2);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(passwordInput2.getText().equals("")) {
                    passwordInput2.setText("Passwort");
                    passwordInput2.setForeground(Color.gray);
                    passwordInput2.setEchoChar((char)0);
                }
            }
        });

        passwordInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(passwordInput.getText().length() < 8) {
                    passwordInput.setBackground(Color.red);
                }else {
                    passwordInput.setBackground(Color.white);
                }
                if(!passwordInput2.getText().equals(passwordInput.getText())) {
                    passwordInput2.setBackground(Color.red);
                }else {
                    passwordInput2.setBackground(Color.white);
                }
            }
        });
        loginPanel.add(passwordInput2);

        JButton bupdate = new JButton("Aktualisieren");
        bupdate.setBounds(122, 141, 130, 40);
        bupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!usernameInput.getText().equals("") && passwordInput.getPassword().length != 0) {
                    if(usernameInput.getBackground() != Color.red && passwordInput.getBackground() != Color.red && passwordInput2.getBackground() != Color.red) {
                        if(usernameInput.getForeground() != Color.gray && passwordInput.getForeground() != Color.gray && passwordInput2.getForeground() != Color.gray) {
                            if(GUI.getMySQL().isConnected()) {

                                GUI.getMySQL().createSQL("SELECT `userid` FROM `user` WHERE `username`='" + usernameInput.getText() + "';");
                                GUI.getMySQL().createTable();
                                if(GUI.getMySQL().getList().size() == 0 || Integer.parseInt(GUI.getMySQL().getList().get(0).get(0)) == GUI.getUser().getUid()) {
                                    GUI.getMySQL().update("UPDATE `user` SET `username` = '" + usernameInput.getText() + "', `password` = '" + passwordInput.getText() + "' WHERE `user`.`userid` = " + GUI.getUser().getUid());
                                    GUI.getUser().setPassword(passwordInput.getText());
                                    GUI.getUser().setUsername(usernameInput.getText());
                                    title.setText(GUI.getUser().getUsername().toUpperCase());
                                    GUI.getGuiMenu().getAccountName().setText(usernameInput.getText());
                                    JOptionPane.showMessageDialog(null, "Accountdetails wurden aktualisiert!");
                                }else {
                                    JOptionPane.showMessageDialog(null, "Der Benutzername existiert bereits!");
                                }
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen!");
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Die Eingabe in manchen Feldern ist ungültig!");
                    }
                }
            }
        });

        loginPanel.add(bupdate);
        cp.add(loginPanel, BorderLayout.CENTER);


        setVisible(true);
    }
}
