package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.Components.ResizableComponent;
import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


//TODO change color of cell when component is selected
public class AttributPanel extends JPanel {

    private JPanel attributContentPanel;

    public JPanel getContentPanel() {
        return attributContentPanel;
    }

    private JTable attributtable;
    private DefaultTableModel tableModel;

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTable getAttributTable() {
        return attributtable;
    }

    public AttributPanel() {
        setLayout(new BorderLayout());

        JPanel attributHeaderPanel = new JPanel();
        attributHeaderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JLabel lattribute = new JLabel("Attribute festlegen:");
        lattribute.setFont(new Font("Arial", Font.BOLD, 14));
        attributHeaderPanel.add(lattribute);

        attributContentPanel = new JPanel(new BorderLayout(10,10));
        attributContentPanel.setBorder(BorderFactory.createTitledBorder("Attribute:"));

        attributtable = new JTable(15,2){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if(GUI.getSession() != null) {
                    if (colIndex == 0)
                        return false;

                    for (ResizableComponent resizableComponent : GUI.getSession().getResizableComponents()) {
                        if (resizableComponent.isClicked()) {
                            if(!tableModel.getValueAt(rowIndex, colIndex-1).equals(""))
                                return true;
                        }
                    }
                }
                return false;
            }};
        attributtable.setOpaque(false);
        attributtable.setGridColor(Color.gray);
        attributtable.setBorder(BorderFactory.createLineBorder(Color.gray));
        attributtable.setBackground(null);
        attributtable.setRowHeight(25);
        tableModel = (DefaultTableModel)attributtable.getModel();
        DefaultCellEditor dce = (DefaultCellEditor)attributtable.getDefaultEditor(Object.class);
        JTextField editor = (JTextField)dce.getComponent();

        dce.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                if(GUI.getSession() != null) {
                    ResizableComponent resizableComponent = null;
                    for (ResizableComponent resizableComponent1 : GUI.getSession().getResizableComponents()) {
                        if (resizableComponent1.isClicked()) {
                            resizableComponent = resizableComponent1;
                            break;
                        }
                    }
                    if(resizableComponent == null) tableModel.setValueAt("", attributtable.getSelectedRow(), attributtable.getSelectedColumn());
                    else resizableComponent.updateAttributes();
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });

        attributContentPanel.add(attributtable, BorderLayout.CENTER);



        add(attributHeaderPanel,BorderLayout.PAGE_START);
        add(attributContentPanel, BorderLayout.CENTER);
    }
}
