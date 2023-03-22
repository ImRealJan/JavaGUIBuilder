package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Objects;

public class ComponentPanel extends JPanel {

    private JPanel contentPanel;
    private JList componentList;
    private DefaultListModel componentListModel;

    public JPanel getContentPanel() {
        return contentPanel;
    }
    public JList getComponentList() {
        return componentList;
    }

    public DefaultListModel getComponentListModel() {
        return componentListModel;
    }

    public ComponentPanel() {
        setLayout(new BorderLayout());

        JPanel componentHeaderPanel = new JPanel();
        componentHeaderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JLabel lcomponent = new JLabel("Objekt hinzufügen:");
        lcomponent.setFont(new Font("Arial", Font.BOLD, 14));
        componentHeaderPanel.add(lcomponent);

        contentPanel = new JPanel(new FlowLayout());

        JButton buttonAddText = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/componentIcons/jlabel.gif"))));
        buttonAddText.setToolTipText("JLabel");
        JButton buttonAddButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/componentIcons/jbutton.gif"))));
        buttonAddButton.setToolTipText("JButton");
        JButton buttonAddTextField = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/componentIcons/jtextfield.gif"))));
        buttonAddTextField.setToolTipText("JTextField");

        contentPanel.add(buttonAddText);
        contentPanel.add(buttonAddButton);
        contentPanel.add(buttonAddTextField);

        for (Component component  : contentPanel.getComponents()) {
            if(component instanceof JButton button) {
                button.setEnabled(false);
                button.setMargin(new Insets(0,0,0,0));
            }
        }

        JPanel selectComponentPanel = new JPanel(new BorderLayout(10,0));
        selectComponentPanel.setBorder(BorderFactory.createTitledBorder("Komponentliste:"));

        componentListModel = new DefaultListModel();
        componentList = new JList(componentListModel);
        JScrollPane scrollableComponentList = new JScrollPane(componentList);

        JPanel componentButtonsPanel = new JPanel(new GridLayout(3,0));
        JPanel listButtons = new JPanel();
        JButton componentBackButton = new JButton("<<");
        componentBackButton.addActionListener(e -> {
            if(componentList.getSelectedIndex() != -1) componentList.setSelectedIndex(componentList.getSelectedIndex() - 1);
        });
        JButton componentNextButton = new JButton(">>");
        componentNextButton.addActionListener(e -> componentList.setSelectedIndex(componentList.getSelectedIndex()+1));
        listButtons.add(componentBackButton);
        listButtons.add(componentNextButton);
        JButton componentDeleteButton = new JButton("Löschen");
        //TODO fehler wenn noch kein Projekt geöffnet
        componentDeleteButton.addActionListener(e -> GUI.getSession().removeResizableComponent());
        componentButtonsPanel.add(listButtons);
        componentButtonsPanel.add(componentDeleteButton);

        selectComponentPanel.add(scrollableComponentList, BorderLayout.CENTER);
        selectComponentPanel.add(componentButtonsPanel, BorderLayout.LINE_END);

        add(componentHeaderPanel,BorderLayout.PAGE_START);
        add(contentPanel, BorderLayout.CENTER);
        add(selectComponentPanel, BorderLayout.PAGE_END);
    }
}
