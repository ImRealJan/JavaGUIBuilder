package de.bellmannjan.javaguibuilder.Tools;

import de.bellmannjan.javaguibuilder.GUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaCodeGenerator {

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

        GUI.getSession().getResizableComponents().forEach(resizeableComponent -> code += "   " + asConstructor("private") + " "
                + asObject(resizeableComponent.getComponentType()) + " " + resizeableComponent.getName() + " = "
                + asConstructor("new") + " " + asObject(resizeableComponent.getComponentType()) + "();\n");

        code += "\n   " + asConstructor("public") + " " + GUI.getSession().getClassName() + "() {\n" +
                "       " + asConstructor("super") + "()\n" +
                "       setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);\n" +
                "       setSize(" + asString(GUI.getSession().getCustomFrame().getWidth()+ "") + ", " + asString(GUI.getSession().getCustomFrame().getHeight() + "") +");\n" +
                "       setLocation(" + asString(GUI.getSession().getCustomFrame().getX()+"") + ", " + asString(GUI.getSession().getCustomFrame().getY()+"") + ");\n" +
                "       setLocationRelativeTo("+ asConstructor("null") +");\n" +
                "       setTitle(" + asString("\"" + GUI.getSession().getCustomFrame().getTitle() + "\"") + ");\n" +
                "       setResizable(" + asConstructor(GUI.getSession().getCustomFrame().isResizable()+"") + ");\n" +
                "       Container cp = getContentPane();\n" +
                "       cp.setLayout(" + asConstructor("null") + ");\n";

        //TODO hier die einzelnen komponenten anlegen

        code += "       setVisible("+ asConstructor("true") + ");\n" +
                "   }\n";
        code += "   "+ asConstructor("public static void") + " main(" + asObject("String") + "[] args) {\n" +
                "       " + asConstructor("new") + " " + GUI.getSession().getClassName() + "();\n" +
                "   }\n";


        //TODO hier events anlegen
        code += "}";
        code += "</pre></code></html>";

        GUI.getCodeOutputPanel().setText(code);
    }
}
