package de.bellmannjan.javaguibuilder;

import de.bellmannjan.javaguibuilder.Tools.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
public class LoginFrame extends JDialog {

    /**
     * @param owner das JFrame des Hauptprogramms
     * @param modal wenn true ist der JDialog immer ganz vorn
     * @Description Festlegen des Designs des Anmelde und Registrierformulars.
     */
    public LoginFrame(JFrame owner, boolean modal) {
        super(owner, modal);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400,330);
        setLocationRelativeTo(owner);
        setTitle("Login");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            /**
             * @param e the event to be processed
             * @Description wenn der JDialog geschlossen wird und kein Nutzer angemeldet wurde, soll sich das gesamte Programm schließen.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if(GUI.getUser() == null) {
                    owner.dispose();
                }
            }
        });

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBorder(new EmptyBorder(10,0,0,0));
        titlePanel.add(title);
        cp.add(titlePanel, BorderLayout.PAGE_START);


        JPanel contentPanel = new JPanel(null);

        JTextField usernameInput = new JTextField("Benutzername");
        usernameInput.setBounds(92, 10, 200, 30);
        usernameInput.setForeground(Color.gray);

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
        usernameInput.addFocusListener(new FocusListener() {
            /**
             * @param e the event to be processed
             * @Description Wenn auf das Benutzernamenfeld gedrückt wird soll der Platzhalter-Text gelöscht werden
             */
            @Override
            public void focusGained(FocusEvent e) {
                if(usernameInput.getText().equals("Benutzername")) {
                    usernameInput.setText("");
                    usernameInput.setForeground(Color.black);
                }
            }

            /**
             * @param e the event to be processed
             * @Description Wenn auf das Benutzernamenfeld verlassen wird und nichts drinnen steht soll wieder der Platzhalter eingefügt werden
             */
            @Override
            public void focusLost(FocusEvent e) {
                if(usernameInput.getText().equals("")) {
                    usernameInput.setText("Benutzername");
                    usernameInput.setForeground(Color.gray);
                }
            }
        });
        contentPanel.add(usernameInput);

        JPasswordField passwordInput = new JPasswordField("Passwort");
        passwordInput.setEchoChar((char)0);
        passwordInput.setForeground(Color.gray);
        passwordInput.setBounds(92, 50, 200, 30);

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
            }
        });
        passwordInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                handlePasswordFocus(passwordInput, e);
            }
            @Override
            public void focusLost(FocusEvent e) {
                handlePasswordFocusLost(passwordInput, e);
            }
        });
        contentPanel.add(passwordInput);

        JPasswordField passwordInput2 = new JPasswordField("Passwort");
        passwordInput2.setEchoChar((char)0);
        passwordInput2.setForeground(Color.gray);
        passwordInput2.setBounds(92, 92, 200, 30);
        passwordInput2.setVisible(false);
        passwordInput2.addKeyListener(new KeyAdapter() {
            /**
             * @param e the event to be processed
             * @Description Wenn das Passwort nicht mit dem Passwort aus Feld 1 übereinstimmt soll das Feld rot werden
             */
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
                handlePasswordFocus(passwordInput2, e);
            }
            @Override
            public void focusLost(FocusEvent e) {
                handlePasswordFocusLost(passwordInput2, e);
            }
        });
        contentPanel.add(passwordInput2);

        JButton bAnmelden = new JButton("Anmelden");
        bAnmelden.setBounds(132, 140, 120, 40);
        bAnmelden.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             * @Description Wenn die Eingabefelder keine Fehler melden und der Button "Anmelden" lautet: Wird übeprüft ob der Nutzer in
             * der DB steht und ob das Passwort übereinstimmt. Ist das der Fall wird der Benutzer im System angelegt.
             * Wenn nicht kommt eine Fehlermeldung
             *
             * @Description Wenn die Eingabefelder keine Fehler melden und der Button "Registrieren" lautet: Wird überprüft
             * ob der Benutzername schon existiert. Wenn Ja Fehlermeldung. Wenn Nein Nutzer wird angelegt
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(usernameInput.getBackground() != Color.red && passwordInput.getBackground() != Color.red) {
                        if(usernameInput.getForeground() != Color.gray && passwordInput.getForeground() != Color.gray) {
                            if(GUI.getMySQL().isConnected()) {
                                if(bAnmelden.getText().equals("Anmelden")) {
                                    GUI.getMySQL().createSQL("SELECT `userid` FROM `user` WHERE `username`='" + usernameInput.getText() + "' AND password='" + passwordInput.getText() + "';");
                                    GUI.getMySQL().createTable();
                                    if(GUI.getMySQL().getList().size() != 0) {
                                        GUI.setUser(new User(Integer.parseInt(GUI.getMySQL().getList().get(0).get(0)), usernameInput.getText(), passwordInput.getText()));
                                        GUI.getGuiMenu().getAccountName().setText("Eingeloggt als: " + GUI.getUser().getUsername());
                                        dispose();
                                    }else {
                                        JOptionPane.showMessageDialog(null, "Der eingebenen Daten konnten in der Datenbank nicht gefunden werden!");
                                    }
                                }else {
                                    GUI.getMySQL().createSQL("SELECT `userid` FROM `user` WHERE `username`='" + usernameInput.getText() + "';");
                                    GUI.getMySQL().createTable();
                                    if(GUI.getMySQL().getList().size() == 0) {
                                        GUI.getMySQL().update("INSERT INTO `user` (`userid`, `username`, `password`) VALUES (NULL, '" + usernameInput.getText() + "', '" + passwordInput.getText() + "')");
                                        GUI.getMySQL().createSQL("SELECT `userid` FROM `user` WHERE `username`='" + usernameInput.getText() + "';");
                                        GUI.getMySQL().createTable();
                                        GUI.setUser(new User(Integer.parseInt(GUI.getMySQL().getList().get(0).get(0)), usernameInput.getText(), passwordInput.getText()));
                                        GUI.getGuiMenu().getAccountName().setText("Eingeloggt als: " + GUI.getUser().getUsername());
                                        dispose();
                                    }else {
                                        JOptionPane.showMessageDialog(null, "Der Benutzername existiert bereits!");
                                    }
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
        contentPanel.add(bAnmelden);



        cp.add(contentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton register = new JButton("Noch keinen Account?");
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        register.setOpaque(false);
        /**
         * @param e the event to be processed
         * @Description Wenn der Text "Zurück" lautet stellt sich der JDialog zum Loginfenster um, andernfalls zum Registrierfenster
         */
        register.addActionListener(e -> {
            if(register.getText().equals("Noch keinen Account?")) {
                setTitle("Registrieren");
                title.setText("REGISTRIEREN");
                bAnmelden.setText("Registrieren");
                passwordInput2.setVisible(true);
                register.setText("Zurück");
            }else {
                setTitle("Login");
                title.setText("LOGIN");
                bAnmelden.setText("Anmelden");
                passwordInput2.setVisible(false);
                register.setText("Noch keinen Account?");
            }
        });
        bottomPanel.add(register);
        cp.add(bottomPanel, BorderLayout.PAGE_END);


        setVisible(true);
    }

    /**
     * @param passwordInput
     * @param e
     * @Description Wird das Passwortfeld angeklickt wird der Platzhalter entfernt und das Feld unkenntlich gemacht
     */
    public void handlePasswordFocus(JPasswordField passwordInput, FocusEvent e) {
        if(passwordInput.getText().equals("Passwort")) {
            passwordInput.setText("");
            passwordInput.setForeground(Color.black);
            passwordInput.setEchoChar('*');
        }
    }
    /**
     * @param passwordInput
     * @param e
     * @Description Wird das Passwortfeld verlassen wird, wird der Platzhalter eingefügt und das Feld kenntlich gemacht
     */
    public void handlePasswordFocusLost(JPasswordField passwordInput, FocusEvent e) {
        if(passwordInput.getText().equals("")) {
            passwordInput.setText("Passwort");
            passwordInput.setForeground(Color.gray);
            passwordInput.setEchoChar((char)0);
        }
    }
}
