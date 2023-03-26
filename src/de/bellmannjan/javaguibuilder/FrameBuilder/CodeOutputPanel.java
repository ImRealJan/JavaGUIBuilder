package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.Tools.JavaCodeGenerator;

import javax.swing.*;

public class CodeOutputPanel extends JEditorPane {

    public CodeOutputPanel() {
        setContentType("text/html");
        setEditable(false);
        setText(new JavaCodeGenerator().generateDefaultCode());

    }
}
