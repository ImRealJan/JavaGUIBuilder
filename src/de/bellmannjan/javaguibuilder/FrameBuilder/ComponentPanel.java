package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Objects;

public class ComponentPanel extends JPanel {
    private final JList<String> componentList;
    private final DefaultListModel<String> componentListModel;

    public JList<String> getComponentList() {
        return componentList;
    }

    public DefaultListModel<String> getComponentListModel() {
        return componentListModel;
    }

    public ComponentPanel() {
        setLayout(new BorderLayout());

        JLabel lcomponent = new JLabel("Objekt hinzufügen:", SwingConstants.CENTER);
        lcomponent.setBorder(new EmptyBorder(5,0,0,0));
        lcomponent.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel contentPanel = new JPanel(new FlowLayout());
        contentPanel.setBorder(BorderFactory.createTitledBorder("Komponenten:"));

        JButton buttonAddText = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/componentIcons/jlabel.gif"))));
        buttonAddText.setToolTipText("JLabel");
        JButton buttonAddButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/componentIcons/jbutton.gif"))));
        buttonAddButton.setToolTipText("JButton");
        JButton buttonAddTextField = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/componentIcons/jtextfield.gif"))));
        buttonAddTextField.setToolTipText("JTextField");
        JButton buttonAddTextArea = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/componentIcons/jtextarea.gif"))));
        buttonAddTextArea.setToolTipText("JTextArea");

        contentPanel.add(buttonAddText);
        contentPanel.add(buttonAddButton);
        contentPanel.add(buttonAddTextField);
        contentPanel.add(buttonAddTextArea);

        for (Component component : contentPanel.getComponents()) {
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
                    GUI.getSession().setSelectedComponent(GUI.getSession().getResizableComponents().get(componentList.getSelectedIndex()));
                }
            }
        });
        JScrollPane scrollableComponentList = new JScrollPane(componentList);

        JPanel componentButtonsPanel = new JPanel(new GridLayout(3,0));
        JPanel listButtons = new JPanel();
        JButton componentBackButton = new JButton("<<");
        componentBackButton.addActionListener(e -> {
            if(GUI.getSession() != null && GUI.getSession().getResizableComponents().size() != 0) {
                if(GUI.getSession().getSelectedComponent() != null) {
                    if(componentList.getSelectedIndex()-1 >= 0) {
                       GUI.getSession().setSelectedComponent(GUI.getSession().getResizableComponents().get(componentList.getSelectedIndex()-1));
                   }
                }else {
                    GUI.getSession().setSelectedComponent(GUI.getSession().getResizableComponents().get(0));
                }
            }
        });
        JButton componentNextButton = new JButton(">>");
        componentNextButton.addActionListener(e -> {
            if(GUI.getSession() != null && GUI.getSession().getResizableComponents().size() != 0) {
                if(GUI.getSession().getSelectedComponent() != null) {
                    if (componentList.getSelectedIndex()+1 <= componentListModel.size()-1) {
                        GUI.getSession().setSelectedComponent(GUI.getSession().getResizableComponents().get(componentList.getSelectedIndex()+1));
                    }
                }else {
                    GUI.getSession().setSelectedComponent(GUI.getSession().getResizableComponents().get(componentListModel.size()-1));
                }
            }
        });
        listButtons.add(componentBackButton);
        listButtons.add(componentNextButton);
        JButton componentDeleteButton = new JButton("Löschen");
        componentDeleteButton.addActionListener(e -> {
            if(GUI.getSession() != null) {
                if(GUI.getSession().getSelectedComponent() != null) {
                    GUI.getSession().removeResizableComponent(GUI.getSession().getSelectedComponent());
                }
            }
        });
        componentButtonsPanel.add(listButtons);
        componentButtonsPanel.add(componentDeleteButton);

        selectComponentPanel.add(scrollableComponentList, BorderLayout.CENTER);
        selectComponentPanel.add(componentButtonsPanel, BorderLayout.LINE_END);

        add(lcomponent,BorderLayout.PAGE_START);
        add(contentPanel, BorderLayout.CENTER);
        add(selectComponentPanel, BorderLayout.PAGE_END);
    }

    public void updateList() {
        componentListModel.clear();
        GUI.getSession().getResizableComponents().forEach(resizableComponent1 -> GUI.getComponentPanel().getComponentListModel().addElement(resizableComponent1.getName() + " : "
                + resizableComponent1.getComponentType().toUpperCase()));
        GUI.getComponentPanel().getComponentList().setSelectedIndex(GUI.getSession().getResizableComponents().size()-1);
    }
}
