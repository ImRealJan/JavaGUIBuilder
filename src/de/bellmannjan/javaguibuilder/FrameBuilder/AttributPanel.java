package de.bellmannjan.javaguibuilder.FrameBuilder;

import de.bellmannjan.javaguibuilder.Components.ResizeableComponent;
import de.bellmannjan.javaguibuilder.GUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


//TODO change color of cell when component is selected
public class AttributPanel extends JPanel {

    private final JPanel attributContentPanel;

    private final JTable attributtable;
    private DefaultTableModel tableModel;

    public AttributPanel() {
        setLayout(new BorderLayout());

        JPanel attributHeaderPanel = new JPanel();
        attributHeaderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JLabel lattribute = new JLabel("Attribute festlegen:");
        lattribute.setFont(new Font("Arial", Font.BOLD, 14));
        attributHeaderPanel.add(lattribute);

        attributContentPanel = new JPanel(new BorderLayout(10, 10));
        attributContentPanel.setBorder(BorderFactory.createTitledBorder("Attribute:"));

        attributtable = new JTable(15, 2) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if (GUI.getSession() != null) {
                    if (colIndex == 0 || tableModel.getValueAt(rowIndex, colIndex - 1) == null)
                        return false;

                    if (!tableModel.getValueAt(rowIndex, colIndex - 1).equals(""))
                        return true;
                }
                return false;
            }
        };
        attributtable.setOpaque(false);
        attributtable.setGridColor(Color.gray);
        attributtable.setBorder(BorderFactory.createLineBorder(Color.gray));
        attributtable.setBackground(null);
        attributtable.setRowHeight(25);
        tableModel = (DefaultTableModel) attributtable.getModel();
        DefaultCellEditor dce = (DefaultCellEditor) attributtable.getDefaultEditor(Object.class);
        JTextField editor = (JTextField) dce.getComponent();

        dce.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                if (GUI.getSession() != null) {
                    ResizeableComponent resizeableComponent = GUI.getSession().getSelectedComponent();
                    if(resizeableComponent != null) {
                        resizeableComponent.updateAttributes();
                        int index = GUI.getComponentPanel().getComponentList().getSelectedIndex();
                        GUI.getComponentPanel().updateList();
                        GUI.getComponentPanel().getComponentList().setSelectedIndex(index);
                    }
                    else {
                        GUI.getSession().getCustomFrame().updateAttributes();
                    }
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });

        attributContentPanel.add(attributtable, BorderLayout.CENTER);


        add(attributHeaderPanel, BorderLayout.PAGE_START);
        add(attributContentPanel, BorderLayout.CENTER);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTable getAttributTable() {
        return attributtable;
    }

    public JPanel getContentPanel() {
        return attributContentPanel;
    }

    public void clearTable() {
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            tableModel.setValueAt(null, r, 0);
            tableModel.setValueAt(null, r, 1);
        }
    }
}
