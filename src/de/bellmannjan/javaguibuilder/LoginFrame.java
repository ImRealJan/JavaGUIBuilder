package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Tools.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//TODO hübsch machen
public class LoginFrame extends JDialog {

    JPanel loginPanel;
    JPanel registerPanel;
    public LoginFrame(JFrame owner, boolean modal) {
        super(owner, modal);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(owner);
        setTitle("Login");
        Container cp = getContentPane();
        cp.setLayout(null);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(GUI.getUser() == null) {
                    owner.dispose();
                }
            }
        });


        loginPanel = createLoginPage();
        loginPanel.setBounds(0,0,400,300);
        cp.add(loginPanel);

        registerPanel = createRegisterPage();
        registerPanel.setBounds(0,0,400,300);
        cp.add(registerPanel);


        setVisible(true);
    }

    public JPanel createLoginPage() {

        JPanel loginPanel = new JPanel(null);

        JLabel title = new JLabel("LOGIN");
        title.setBounds(124, 15, 134, 50);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        loginPanel.add(title);

        JTextField usernameInput = new JTextField("Benutzername");
        usernameInput.setBounds(92, 80, 200, 30);
        usernameInput.setForeground(Color.gray);

        usernameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(usernameInput.getText().contains(" ")) {
                    usernameInput.setBackground(Color.red);
                }else {
                    usernameInput.setBackground(Color.white);
                }
            }
        });
        usernameInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(usernameInput.getText().equals("Benutzername")) {
                    usernameInput.setText("");
                    usernameInput.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(usernameInput.getText().equals("")) {
                    usernameInput.setText("Benutzername");
                    usernameInput.setForeground(Color.gray);
                }
            }
        });
        loginPanel.add(usernameInput);

        JPasswordField passwordInput = new JPasswordField("Passwort");
        char passwordChar = passwordInput.getEchoChar();
        passwordInput.setEchoChar((char)0);
        passwordInput.setForeground(Color.gray);
        passwordInput.setBounds(92, 122, 200, 30);

        passwordInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(passwordInput.getText().length() < 8) {
                    passwordInput.setBackground(Color.red);
                }else {
                    passwordInput.setBackground(Color.white);
                }
            }
        });
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

        JButton bAnmelden = new JButton("Anmelden");
        bAnmelden.setBounds(132, 171, 120, 40);
        bAnmelden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!usernameInput.getText().equals("") && passwordInput.getPassword().length != 0) {
                    if(usernameInput.getBackground() != Color.red && passwordInput.getBackground() != Color.red) {
                        if(usernameInput.getForeground() != Color.gray && passwordInput.getForeground() != Color.gray) {
                            if(GUI.getMySQL().isConnected()) {

                                GUI.getMySQL().createSQL("SELECT `userid` FROM `user` WHERE `username`='" + usernameInput.getText() + "' AND password='" + passwordInput.getText() + "';");
                                GUI.getMySQL().createTable();
                                if(GUI.getMySQL().getList().size() != 0) {
                                    GUI.setUser(new User(Integer.parseInt(GUI.getMySQL().getList().get(0).get(0)), usernameInput.getText(), passwordInput.getText()));
                                    dispose();
                                }else {
                                    JOptionPane.showMessageDialog(null, "Der eingebenen Daten konnten in der Datenbank nicht gefunden werden!");
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
        loginPanel.add(bAnmelden);

        JButton register = new JButton("Noch keinen Account?");
        register.setBounds(107, 225, 165, 20);
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        register.setOpaque(false);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPanel.setVisible(false);
                setSize(400,330);
                setTitle("Registrieren");
                registerPanel.setVisible(true);
            }
        });
        loginPanel.add(register);


        loginPanel.setVisible(true);
        return loginPanel;
    }

    public JPanel createRegisterPage() {

        JPanel registerPanel = new JPanel(null);

        JLabel title = new JLabel("REGISTRIEREN");
        title.setBounds(48, 15, 302, 50);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        registerPanel.add(title);

        JTextField usernameInput = new JTextField("Benutzername");
        usernameInput.setBounds(92, 80, 200, 30);
        usernameInput.setForeground(Color.gray);

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
        usernameInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(usernameInput.getText().equals("Benutzername")) {
                    usernameInput.setText("");
                    usernameInput.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(usernameInput.getText().equals("")) {
                    usernameInput.setText("Benutzername");
                    usernameInput.setForeground(Color.gray);
                }
            }
        });
        registerPanel.add(usernameInput);

        JPasswordField passwordInput = new JPasswordField("Passwort");
        char passwordChar = passwordInput.getEchoChar();
        passwordInput.setEchoChar((char)0);
        passwordInput.setForeground(Color.gray);
        passwordInput.setBounds(92, 130, 200, 30);
        passwordInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(passwordInput.getText().length() < 8) {
                    passwordInput.setBackground(Color.red);
                }else {
                    passwordInput.setBackground(Color.white);
                }
            }
        });
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
        registerPanel.add(passwordInput);

        JPasswordField passwordInput2 = new JPasswordField("Passwort");
        char passwordChar2 = passwordInput2.getEchoChar();
        passwordInput2.setEchoChar((char)0);
        passwordInput2.setForeground(Color.gray);
        passwordInput2.setBounds(92, 170, 200, 30);
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
        registerPanel.add(passwordInput2);


        JButton bRegistrieren = new JButton("Registrieren");
        bRegistrieren.setBounds(132, 211, 120, 40);
        bRegistrieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!usernameInput.getText().equals("") && passwordInput.getPassword().length != 0) {
                    if(usernameInput.getBackground() != Color.red && passwordInput.getBackground() != Color.red && passwordInput2.getBackground() != Color.red) {
                        if(usernameInput.getForeground() != Color.gray && passwordInput.getForeground() != Color.gray && passwordInput2.getForeground() != Color.gray) {
                            if(GUI.getMySQL().isConnected()) {

                                GUI.getMySQL().createSQL("SELECT `userid` FROM `user` WHERE `username`='" + usernameInput.getText() + "';");
                                GUI.getMySQL().createTable();
                                if(GUI.getMySQL().getList().size() == 0) {
                                    GUI.getMySQL().update("INSERT INTO `user` (`userid`, `username`, `password`) VALUES (NULL, '" + usernameInput.getText() + "', '" + passwordInput.getText() + "')");
                                    GUI.getMySQL().createSQL("SELECT `userid` FROM `user` WHERE `username`='" + usernameInput.getText() + "';");
                                    GUI.getMySQL().createTable();
                                    GUI.setUser(new User(Integer.parseInt(GUI.getMySQL().getList().get(0).get(0)), usernameInput.getText(), passwordInput.getText()));
                                    GUI.getGuiMenu().getAccountName().setText(GUI.getUser().getUsername());
                                    //TODO nutzername wird nicht in Menu gesetzt
                                    dispose();
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
        registerPanel.add(bRegistrieren);

        JButton back = new JButton("Zurück");
        back.setBounds(152, 262, 80, 20);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setOpaque(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerPanel.setVisible(false);
                setSize(400,300);
                setTitle("Login");
                loginPanel.setVisible(true);
            }
        });
        registerPanel.add(back);

        registerPanel.setVisible(false);

        return registerPanel;
    }
}
