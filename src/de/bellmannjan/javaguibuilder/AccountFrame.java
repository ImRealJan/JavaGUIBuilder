package de.bellmannjan.javaguibuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AccountFrame extends JDialog {

    public AccountFrame(JFrame owner, boolean modal) {
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

        JPanel accountPanel = new JPanel(null);


        JTextField usernameInput = new JTextField(GUI.getUser().getUsername());
        usernameInput.setBounds(92, 20, 200, 30);
        usernameInput.addKeyListener(new KeyAdapter() {
            /**
             * @param e the event to be processed
             * @Description Wenn der Benutzername Leerzeichen enthält oder länger als 16 Zeichen ist soll das Textfield rot werden
             */
            @Override
            public void keyReleased(KeyEvent e) {
                if(usernameInput.getText().contains(" ") || usernameInput.getText().length() > 16) {
                    usernameInput.setBackground(Color.red);
                }else {
                    usernameInput.setBackground(Color.white);
                }
            }
        });
        accountPanel.add(usernameInput);

        JPasswordField passwordInput = new JPasswordField(GUI.getUser().getPassword());
        char passwordChar = passwordInput.getEchoChar();
        passwordInput.setBounds(92, 60, 200, 30);
        passwordInput.addFocusListener(new FocusListener() {
            /**
             * @param e the event to be processed
             * @Description Wenn auf das Benutzernamenfeld gedrückt wird soll der Platzhalter-Text gelöscht werden
             */
            @Override
            public void focusGained(FocusEvent e) {
                if(passwordInput.getText().equals("Passwort")) {
                    passwordInput.setText("");
                    passwordInput.setForeground(Color.black);
                    passwordInput.setEchoChar(passwordChar);
                }
            }
            /**
             * @param e the event to be processed
             * @Description Wenn auf das Benutzernamenfeld verlassen wird und nichts drinnen steht soll wieder der Platzhalter eingefügt werden
             */
            @Override
            public void focusLost(FocusEvent e) {
                if(passwordInput.getText().equals("")) {
                    passwordInput.setText("Passwort");
                    passwordInput.setForeground(Color.gray);
                    passwordInput.setEchoChar((char)0);
                }
            }
        });
        accountPanel.add(passwordInput);

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
            /**
             * @param e the event to be processed
             * @Description Wenn auf das Benutzernamenfeld gedrückt wird soll der Platzhalter-Text gelöscht werden
             */
            @Override
            public void focusGained(FocusEvent e) {
                if(passwordInput2.getText().equals("Passwort")) {
                    passwordInput2.setText("");
                    passwordInput2.setForeground(Color.black);
                    passwordInput2.setEchoChar(passwordChar2);
                }
            }
            /**
             * @param e the event to be processed
             * @Description Wenn auf das Benutzernamenfeld verlassen wird und nichts drinnen steht soll wieder der Platzhalter eingefügt werden
             */
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
            /**
             * @param e the event to be processed
             * @Description Wenn das Passwort kürzer als 8 Zeichen ist soll das Feld rot werden
             */
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
        accountPanel.add(passwordInput2);

        JButton bupdate = new JButton("Aktualisieren");
        bupdate.setBounds(122, 141, 130, 40);
        bupdate.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             * @Description Wenn die Eingabefelder keine Fehler melden, Wird übeprüft ob der Nutzer in
             * der DB steht und ob der Benutzername schon existiert, ist das der Fall gibt es eine Fehlermeldung
             *
             * @Description Wenn die Eingabefelder keine Fehler melden und der Button "Registrieren" lautet: Wird überprüft
             * ob der Benutzername schon existiert. Wenn Ja Fehlermeldung. Wenn Nein Nutzer wird angelegt
             */
            @Override
            public void actionPerformed(ActionEvent e) {
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
                                    GUI.getGuiMenu().getAccountName().setText("Eingeloggt als: " + GUI.getUser().getUsername());
                                    JOptionPane.showMessageDialog(null, "Accountdetails wurden aktualisiert!");
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
        });

        accountPanel.add(bupdate);
        cp.add(accountPanel, BorderLayout.CENTER);


        setVisible(true);
    }
}
