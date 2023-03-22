package de.bellmannjan.javaguibuilder.FrameBuilder;

import javax.swing.*;

public class CodeOutputPanel extends JEditorPane {

    public CodeOutputPanel() {
        setContentType("text/html");
        setEditable(false);
        setText("<html>Console console = System.console();<br>" +
                "<br>" +
                "if(console == null) {<br>" +
                "&emsp;System.out.println(\"Console is not available to current JVM process\")<br>" +
                "&emsp;return;<br>" +
                "}<br>" +
                "<br>" +
                "Reader consoleReader = console.reader();<br>" +
                "Scanner scanner = new Scanner(consoleReader);<br>" +
                "<br>" +
                "System.out.println(\"Enter age:\");<br>" +
                "int age = scanner.nextInt();<br>" +
                "System.out.println(\"Entered age: \" + age);<br>" +
                "<br>" +
                "scanner.close();</html>");
    }
}
