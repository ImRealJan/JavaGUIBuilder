package de.bellmannjan.javaguibuilder.Builder;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class JButtonAsText {

    private final JButton jButton;
    private String text;
    private final Color color;
    private final Color colorHovered;
    private final String icon;

    private final FutureTask<JFileChooser> futureOpenFile = new FutureTask<>(JFileChooser::new);
    private final FutureTask<JFileChooser> futureCreateFile = new FutureTask<>(JFileChooser::new);
    private final FutureTask<JFileChooser> futureImportFile = new FutureTask<>(JFileChooser::new);

    public JButtonAsText(Color color, Color colorHovered, String icon) {
        jButton = new JButton();
        this.color = color;
        this.colorHovered = colorHovered;
        this.icon = icon;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(futureCreateFile);
        executor.execute(futureImportFile);
        executor.execute(futureOpenFile);

    }
    public JButtonAsText setText(String text) {
        this.text = text;
        return this;
    }
    public JButtonAsText addOpenFileFunction() {
        jButton.addActionListener(e -> {

            try {
                JFileChooser chooser = futureOpenFile.get();
                chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
                FileFilter filter = new FileFilter(){
                    public boolean accept(File f){
                        if(f.isDirectory()) return true;
                        else return f.getName().endsWith(".jfrm");
                    }
                    public String getDescription(){
                        return "Java GUI Builder";
                    }
                };
                chooser.setFileFilter(filter);
                if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null, "Soon the File will be opened");
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return this;
    }
    public JButtonAsText addCreateFileFuntion() {
        jButton.addActionListener(e -> {
            try {
                JFileChooser chooser = futureCreateFile.get();
                chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
                FileFilter filter = new FileFilter(){
                    public boolean accept(File f){
                        if(f.isDirectory()) return true;
                        else return f.getName().endsWith(".jfrm");
                    }
                    public String getDescription(){
                        return "Java GUI Builder";
                    }
                };
                chooser.setFileFilter(filter);
                if (chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    if (file == null) {
                        return;
                    }
                    if (!file.getName().toLowerCase().endsWith(".jfrm")) {
                        file = new File(file.getParentFile(), file.getName() + ".jfrm");
                    }
                    file.createNewFile();
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return this;
    }
    public JButtonAsText addImportFileFuntion() {
        jButton.addActionListener(e -> {
            try {
                JFileChooser chooser = futureImportFile.get();
                chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
                FileFilter filter = new FileFilter(){
                    public boolean accept(File f){
                        if(f.isDirectory()) return true;
                        else if(f.getName().endsWith(".jfm")) return true;
                        else return false;
                    }
                    public String getDescription(){
                        return "Java-Editor";
                    }
                };
                chooser.setFileFilter(filter);
                if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null, "Soon the File will be imported");
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return this;
    }
    public JButton toText() {
        jButton.setText(text);
        ImageIcon buttonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(icon)));
        jButton.setForeground(color);
        jButton.setIcon(buttonIcon);
        jButton.setIconTextGap(8);
        jButton.setContentAreaFilled(false);
        jButton.setOpaque(false);
        jButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jButton.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if(model.isRollover()) {
                jButton.setForeground(colorHovered);
            }else {
                jButton.setForeground(color);
            }
        });
        return jButton;
    }
}
