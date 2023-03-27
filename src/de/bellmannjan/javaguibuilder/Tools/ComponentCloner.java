package de.bellmannjan.javaguibuilder.Tools;

import de.bellmannjan.javaguibuilder.Components.*;

import javax.swing.*;
import java.awt.*;

public class ComponentCloner {

    ResizeableComponent component;
    JComponent clonedComponent;

    /**
     * @param component Übergabe der Komponente die geklont werden soll. Und den Komponenten-Typ zuordnen
     */
    public ComponentCloner(ResizeableComponent component) {
        this.component = component;
        setComponentType();
    }

    /**
     * Abfrage welche Instance die Komponente und dementsprechend eine neue Komponente erstellen.
     */
    public void setComponentType() {
        if (component.getComponentInformations() instanceof JButton button) {
            clonedComponent = new JButton(button.getText());
        }else if (component.getComponentInformations()  instanceof  JTextField textField) {
            JTextField newTextField = new JTextField(textField.getText());
            newTextField.setEditable(textField.isEditable());
            newTextField.setBackground(Color.white);
            clonedComponent = newTextField;
        } else if (component.getComponentInformations()  instanceof JLabel label) {
            clonedComponent = new JLabel(label.getText());
        } else if (component.getComponentInformations()  instanceof JTextArea textArea) {
            JTextArea newTextArea = new JTextArea(textArea.getText());
            newTextArea.setEditable(textArea.isEditable());
            clonedComponent = new JScrollPane(newTextArea);
        }
    }


    /**
     * @return Die fertig geklonte Komponente mit allen wichtigen Informationen.
     */
    public JComponent build() {
        clonedComponent.setBounds(component.getBounds());
        clonedComponent.setName(component.getComponentInformations().getName());
        if(component.getComponentInformations().getToolTipText() != null && !component.getComponentInformations().getToolTipText().equals(""))
            clonedComponent.setToolTipText(component.getComponentInformations().getToolTipText());
        clonedComponent.setFont(component.getComponentInformations().getFont());
        clonedComponent.setEnabled(component.getComponentInformations().isEnabled());
        clonedComponent.setVisible(component.getComponentInformations().isVisible());
        return clonedComponent;
    }
}
