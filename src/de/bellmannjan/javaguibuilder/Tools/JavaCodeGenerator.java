package de.bellmannjan.javaguibuilder.Tools;

import de.bellmannjan.javaguibuilder.Components.ResizeableButton;
import de.bellmannjan.javaguibuilder.Components.ResizeableInput;
import de.bellmannjan.javaguibuilder.Components.ResizeableTextArea;
import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaCodeGenerator {

    String attribute = "";
    String components = "";
    String methods = "";
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

        methods += "  "+ asConstructor("public static void") + " main(" + asObject("String") + "[] args) {\n" +
                   "    " + asConstructor("new") + " " + GUI.getProject().getClassName() + "();\n" +
                   "  }\n\n";

        GUI.getProject().getResizableComponents().forEach(resizeableComponent -> {
            //Hinzufügen der Attribute für jede Komponente
            attribute += "  " + asConstructor("private") + " "
                + asObject(resizeableComponent.getComponentType()) + " " + resizeableComponent.getName() + " = "
                + asConstructor("new") + " " + asObject(resizeableComponent.getComponentType()) + "();\n";
            if(resizeableComponent instanceof ResizeableTextArea) {
            attribute += "  " + asConstructor("private") + " " + asObject("JScrollPane") + " " + resizeableComponent.getName() + "ScrollPane = "
                    + asConstructor("new") + " " + asObject("JScrollPane") + "(" + resizeableComponent.getName() + ");\n";
            }

            //Hinzufügen der Eigenschaften der einzelnen Komponenten
            String componentName = resizeableComponent.getName();
            if(resizeableComponent instanceof ResizeableTextArea) {
                componentName += "ScrollPane";
            }
            components += "    " + componentName + ".setBounds(" + asString(resizeableComponent.getX()+"") + ","
                    + asString(resizeableComponent.getY()+"") + ","
                    + asString((resizeableComponent.getComponentInformations().getWidth()+1)+"") + ","
                    + asString(resizeableComponent.getComponentInformations().getHeight()+"") + ");\n";
            if(resizeableComponent.getText() != null)
                components += "    " + componentName.replaceAll("ScrollPane", "") + ".setText(" + asString("\"" + resizeableComponent.getText() + "\"") + ");\n";
            if(resizeableComponent.getComponentInformations().getToolTipText() != null && !resizeableComponent.getComponentInformations().getToolTipText().equals(""))
                components += "    " + componentName + ".setToolTipText(" + asString("\"" + resizeableComponent.getComponentInformations().getToolTipText() + "\"") + ");\n";
            components += "    " + componentName + ".setFont(" + asConstructor("new") + " Font("+ asString("\"Arial\"") + ", Font.BOLD, "+ asString(resizeableComponent.getComponentInformations().getFont().getSize() + "") + "));\n";
            components += "    " + componentName + ".setEnabled(" + asConstructor(resizeableComponent.getComponentInformations().isEnabled()+"") + ");\n";
            components += "    " + componentName + ".setVisible(" + asConstructor(resizeableComponent.getComponentInformations().isVisible()+"") + ");\n";
            if(resizeableComponent.getComponentInformations() instanceof JTextField textField)
                components += "    " + componentName.replaceAll("ScrollPane", "") + ".setEditable(" + asConstructor(textField.isEditable()+"") + ");\n";
            if(resizeableComponent.getComponentInformations() instanceof JTextArea textArea)
                components += "    " + componentName.replaceAll("ScrollPane", "") + ".setEditable(" + asConstructor(textArea.isEditable()+"") + ");\n";
            if((resizeableComponent instanceof ResizeableButton resizeableButton && resizeableButton.hasEvent()) || (resizeableComponent instanceof ResizeableInput resizeableInput && resizeableInput.hasEvent())) {
                components += "    " + componentName + ".addActionListener(" + asConstructor("new") + " ActionListener(){\n" +
                        "      " + asConstructor("public void") + " actionPerformed(ActionEvent evt) {\n" +
                        "        " + componentName + "_ActionPerformed(evt);\n" +
                        "      }\n" +
                        "    });\n";
            }
            components +="    cp.add(" + componentName + ");\n\n";


            //Hinzufügen der Events der einzelnen Komponenten
            if((resizeableComponent instanceof ResizeableButton resizeableButton && resizeableButton.hasEvent()) || (resizeableComponent instanceof ResizeableInput resizeableInput && resizeableInput.hasEvent()))
                methods += "  " + asConstructor("public void") + " " + resizeableComponent.getName() + "_ActionPerformed(ActionEvent evt) {\n" +
                        "    " + asDescription("// TODO hier Quelltext einfügen") + "\n" +
                        "  }\n\n";
        });

        //Zusammenführen der Strings zu einem Produkt
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
                " * @author " + GUI.getUser().getUsername() + "\n" +
                " */\n\n");
        code += asConstructor("public class ") + GUI.getProject().getClassName() + " " + asConstructor("extends") + " JFrame {\n\n";
        code += attribute;
        code += "\n\n  " + asConstructor("public") + " " + GUI.getProject().getClassName() + "() {\n" +
                "    " + asConstructor("super") + "();\n" +
                "    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);\n" +
                "    setSize(" + asString((GUI.getProject().getCustomFrame().getWidth()+5) + "") + ", " + asString((GUI.getProject().getCustomFrame().getHeight()+5) + "") +");\n" +
                "    setLocationRelativeTo("+ asConstructor("null") +");\n" +
                "    setTitle(" + asString("\"" + GUI.getProject().getCustomFrame().getTitle() + "\"") + ");\n" +
                "    setResizable(" + asConstructor(GUI.getProject().getCustomFrame().isResizable()+"") + ");\n" +
                "    Container cp = getContentPane();\n" +
                "    cp.setLayout(" + asConstructor("null") + ");\n\n";
        code += components;
        code += "    setVisible("+ asConstructor("true") + ");\n";
        code += "  }\n\n";
        code += methods;
        code += "}";
        code += "</pre></code></html>";

        GUI.getCodeOutputPanel().setText(code);
    }
}
