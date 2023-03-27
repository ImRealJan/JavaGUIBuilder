package de.bellmannjan.javaguibuilder.Tools;

import de.bellmannjan.javaguibuilder.Components.ResizeableButton;
import de.bellmannjan.javaguibuilder.Components.ResizeableInput;
import de.bellmannjan.javaguibuilder.Components.ResizeableTextArea;
import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaCodeGenerator {

    //TODO code aufteilen in Sinnabschnitte (Attribute, Frame, Komponenten, Methoden)

    String code = "";

    private String asDescription(String description) {
        return "<span style='color:#800'>" + description+ "</span>";
    }
    private String asObject(String object) {
        return "<span style='color:#606'>" + object + "</span>";
    }
    private String asConstructor(String constructor) {
        return "<span style='color:#008'>" + constructor + "</span>";
    }
    private String asString(String string) {
        return "<span style='color:#080'>" + string + "</span>";
    }

    public String generateDefaultCode() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();

        code += "<html><code><pre>";

        code+= asDescription("/**\n" +
                " *\n" +
                " * Beschreibung\n" +
                " *\n" +
                " * @version 1.0 vom " + dtf.format(now) +"\n" +
                " * @author\n" +
                " */<br><br>");

        code += asConstructor("public class ") + "MeineKlasse {<br>";
        code += asDescription(" //Hier wird später dein Code erscheinen!<br>");
        code += "}";
        code += "</pre></code></html>";

        return code;
    }

    public void generateCode() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();

        code += "<html><code><pre>";
        code += asConstructor("import") + " java.awt.*;\n" +
                asConstructor("import") + " java.awt.event.*;\n" +
                asConstructor("import") + " javax.swing.*;\n" +
                asConstructor("import") + " javax.swing.event.*;\n\n";

        code+= asDescription(
                "/**\n" +
                " *\n" +
                " * Beschreibung\n" +
                " *\n" +
                " * @version 1.0 vom " + dtf.format(now) +"\n" +
                " * @author\n" +
                " */\n\n");

        code += asConstructor("public class ") + GUI.getSession().getClassName() + " " + asConstructor("extends") + " JFrame {\n\n";

        GUI.getSession().getResizableComponents().forEach(resizeableComponent -> {
            code += "  " + asConstructor("private") + " "
                + asObject(resizeableComponent.getComponentType()) + " " + resizeableComponent.getName() + " = "
                + asConstructor("new") + " " + asObject(resizeableComponent.getComponentType()) + "();\n\n";
            if(resizeableComponent instanceof ResizeableTextArea) {
            code += "  " + asConstructor("private") + " " + asObject("JScrollPane") + " " + resizeableComponent.getName() + "ScrollPane = "
                    + asConstructor("new") + " " + asObject("JScrollPane") + "(" + resizeableComponent.getName() + ");";
            }
        });

        code += "\n  " + asConstructor("public") + " " + GUI.getSession().getClassName() + "() {\n" +
                "    " + asConstructor("super") + "();\n" +
                "    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);\n" +
                "    setSize(" + asString((GUI.getSession().getCustomFrame().getWidth()+5) + "") + ", " + asString((GUI.getSession().getCustomFrame().getHeight()+5) + "") +");\n" +
                "    setLocation(" + asString(GUI.getSession().getCustomFrame().getX()+"") + ", " + asString(GUI.getSession().getCustomFrame().getY()+"") + ");\n" +
                "    setLocationRelativeTo("+ asConstructor("null") +");\n" +
                "    setTitle(" + asString("\"" + GUI.getSession().getCustomFrame().getTitle() + "\"") + ");\n" +
                "    setResizable(" + asConstructor(GUI.getSession().getCustomFrame().isResizable()+"") + ");\n" +
                "    Container cp = getContentPane();\n" +
                "    cp.setLayout(" + asConstructor("null") + ");\n\n";

        GUI.getSession().getResizableComponents().forEach(resizeableComponent -> {
            String componentName = resizeableComponent.getName();
            if(resizeableComponent instanceof ResizeableTextArea) {
                componentName += "ScrollPane";
            }
            code += "    " + componentName + ".setBounds(" + asString(resizeableComponent.getX()+"") + ","
                    + asString(resizeableComponent.getY()+"") + ","
                    + asString(resizeableComponent.getComponentInformations().getWidth()+"") + ","
                    + asString(resizeableComponent.getComponentInformations().getHeight()+"") + ");\n";
            if(resizeableComponent.getText() != null)
                code += "    " + componentName.replaceAll("ScrollPane", "") + ".setText(" + asString("\"" + resizeableComponent.getText() + "\"") + ");\n";
            if(resizeableComponent.getComponentInformations().getToolTipText() != null && !resizeableComponent.getComponentInformations().getToolTipText().equals(""))
                code += "    " + componentName + ".setToolTipText(" + asString("\"" + resizeableComponent.getComponentInformations().getToolTipText() + "\"") + ");\n";
            code += "    " + componentName + ".setFont(" + asConstructor("new") + " Font("+ asString("\"Arial\"") + ", Font.BOLD, "+ asString(resizeableComponent.getComponentInformations().getFont().getSize() + "") + "));\n";
            code += "    " + componentName + ".setEnabled(" + asConstructor(resizeableComponent.getComponentInformations().isEnabled()+"") + ");\n";
            code += "    " + componentName + ".setVisible(" + asConstructor(resizeableComponent.getComponentInformations().isVisible()+"") + ");\n";
            if(resizeableComponent.getComponentInformations() instanceof JTextField textField)
                code += "    " + componentName.replaceAll("ScrollPane", "") + ".setEditable(" + asConstructor(textField.isEditable()+"") + ");\n";
            if(resizeableComponent.getComponentInformations() instanceof JTextArea textArea)
                code += "    " + componentName.replaceAll("ScrollPane", "") + ".setEditable(" + asConstructor(textArea.isEditable()+"") + ");\n";
            if((resizeableComponent instanceof ResizeableButton resizeableButton && resizeableButton.hasEvent()) || (resizeableComponent instanceof ResizeableInput resizeableInput && resizeableInput.hasEvent())) {
                code += "    " + componentName + ".addActionListener(" + asConstructor("new") + " ActionListener(){\n" +
                        "      " + asConstructor("public void") + " actionPerformed(ActionEvent evt) {\n" +
                        "        " + componentName + "_ActionPerformed(evt);\n" +
                        "      }\n" +
                        "    });\n";
            }
            code +="    cp.add(" + componentName + ");\n\n";
                });

        code += "    setVisible("+ asConstructor("true") + ");\n" +
                "  }\n\n";
        code += "  "+ asConstructor("public static void") + " main(" + asObject("String") + "[] args) {\n" +
                "    " + asConstructor("new") + " " + GUI.getSession().getClassName() + "();\n" +
                "  }\n\n";

        GUI.getSession().getResizableComponents().forEach(resizeableComponent -> {

            if((resizeableComponent instanceof ResizeableButton resizeableButton && resizeableButton.hasEvent()) || (resizeableComponent instanceof ResizeableInput resizeableInput && resizeableInput.hasEvent()))
                code += "  " + asConstructor("public void") + " " + resizeableComponent.getName() + "_ActionPerformed(ActionEvent evt) {\n" +
                        "    // TODO hier Quelltext einfügen\n" +
                        "    \n" +
                        "  }\n\n";
        });
        code += "}";
        code += "</pre></code></html>";

        GUI.getCodeOutputPanel().setText(code);
    }
}
