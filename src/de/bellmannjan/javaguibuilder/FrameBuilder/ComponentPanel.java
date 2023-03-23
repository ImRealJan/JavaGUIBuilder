package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Objects;

public class ComponentPanel extends JPanel {

    private JPanel contentPanel;
    private JList<String> componentList;
    private DefaultListModel<String> componentListModel;

    public JPanel getContentPanel() {
        return contentPanel;
    }
    public JList<String> getComponentList() {
        return componentList;
    }

    public DefaultListModel<String> getComponentListModel() {
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
        contentPanel.setBorder(BorderFactory.createTitledBorder("Komponenten:"));

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
                button.setMargin(new Insets(0,0,0,0));
                button.addActionListener(e -> {
                    if(GUI.getSession() != null)
                        GUI.getSession().createResizableComponent(button.getToolTipText());
                });
            }
        }

        JPanel selectComponentPanel = new JPanel(new BorderLayout(10,0));
        selectComponentPanel.setBorder(BorderFactory.createTitledBorder("Komponentenauswahl:"));

        componentListModel = new DefaultListModel<>();
        componentList = new JList<>(componentListModel);
        componentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(componentList.getSelectedIndex() != -1) {
                    String name = componentList.getSelectedValue().toString().substring(0, componentList.getSelectedValue().toString().indexOf(" "));
                    GUI.getSession().handleComponentClick(GUI.getSession().getResizableComponent(name));
                }
            }
        });
        JScrollPane scrollableComponentList = new JScrollPane(componentList);
        scrollableComponentList.setPreferredSize(scrollableComponentList.getPreferredSize());

        JPanel componentButtonsPanel = new JPanel(new GridLayout(3,0));
        JPanel listButtons = new JPanel();
        JButton componentBackButton = new JButton("<<");
        componentBackButton.addActionListener(e -> {
            if(GUI.getSession() != null) {
                if(componentList.getSelectedIndex() != -1) {
                    componentList.setSelectedIndex(componentList.getSelectedIndex() - 1);
                    String name = componentList.getSelectedValue().toString().substring(0, componentList.getSelectedValue().toString().indexOf(" "));
                    GUI.getSession().handleComponentClick(GUI.getSession().getResizableComponent(name));
                }else {
                    componentList.setSelectedIndex(0);
                }
            }
        });
        JButton componentNextButton = new JButton(">>");
        componentNextButton.addActionListener(e -> {
            if(GUI.getSession() != null) {
                if(componentList.getSelectedIndex() != -1) {
                    componentList.setSelectedIndex(componentList.getSelectedIndex()+1);
                    String name = componentList.getSelectedValue().toString().substring(0, componentList.getSelectedValue().toString().indexOf(" "));
                    GUI.getSession().handleComponentClick(GUI.getSession().getResizableComponent(name));
                }else {
                    componentList.setSelectedIndex(0);
                }
            }
        });
        listButtons.add(componentBackButton);
        listButtons.add(componentNextButton);
        JButton componentDeleteButton = new JButton("Löschen");
        componentDeleteButton.addActionListener(e -> {
            if(GUI.getSession() != null) {
                if(componentList.getSelectedIndex() != -1) {
                    String name = componentList.getSelectedValue().toString().substring(0, componentList.getSelectedValue().toString().indexOf(" "));
                    GUI.getSession().removeResizableComponent(name);
                    componentListModel.remove(componentList.getSelectedIndex());
                }
            }
        });
        componentButtonsPanel.add(listButtons);
        componentButtonsPanel.add(componentDeleteButton);

        selectComponentPanel.add(scrollableComponentList, BorderLayout.CENTER);
        selectComponentPanel.add(componentButtonsPanel, BorderLayout.LINE_END);

        add(componentHeaderPanel,BorderLayout.PAGE_START);
        add(contentPanel, BorderLayout.CENTER);
        add(selectComponentPanel, BorderLayout.PAGE_END);
    }
}
