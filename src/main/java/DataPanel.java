import rx.functions.Action1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class DataPanel extends JPanel {

    private JPanel tablePanel;
    private JPanel crudPanel;

    private JTextField addValueField;
    private JTextField editValueField;

    private DataService dataService;

    private double cur = 7;


    public DataPanel(LayoutManager layoutManager2) {
        super(layoutManager2);
        dataService = DataService.getInstance();

        tablePanel = createTablePanel();
//        tablePanel.setBackground(Color.YELLOW);

        crudPanel = createCRUDPanel();
//        crudPanel.setBackground(Color.GREEN);

//        this.setLayout(new CardLayout());
        add(crudPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.SOUTH);


        dataService.getData().subscribe(
                new Action1<Map<Double, Double>>() {
                    public void call(Map<Double, Double> response) {
                        updateTableModel(response);
                    }
                }
        );
    }

    private JPanel createTablePanel() {
        DefaultTableModel model = new CustomTableModel();
        model.addRow(new String[] {"1", "10"});

        JPanel panel = new TablePanel(model);
        return panel;
    }

    private void updateTableModel(Map<Double, Double> model) {
        ((TablePanel)tablePanel).updateData(model);
    }

    private JPanel createCRUDPanel() {
        JPanel panel = new JPanel();

        addValueField = new JTextField("", 10);
        panel.add(addValueField);

        addButton(panel, "Add", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataService.addPoint(Double.parseDouble(addValueField.getText()));
                addValueField.setText("");
            }
        });
        editValueField = new JTextField("", 10);
        panel.add(editValueField);
        addButton(panel, "Edit", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTable table = ((TablePanel)tablePanel).getTable();
                int[] rows = table.getSelectedRows();
                TableModel model = table.getModel();
                int i = 0;
                while(i < rows.length) {
                    int index = table.getRowSorter().convertRowIndexToModel(rows[i++]);
                    String value = ((DefaultTableModel)model).getValueAt(index, 0).toString();
                    dataService.changePoint(Double.parseDouble(value), Double.parseDouble(editValueField.getText()));
                    editValueField.setText("");
                }
                table.clearSelection();
            }
        });
        addButton(panel, "Delete", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTable table = ((TablePanel)tablePanel).getTable();
                int[] rows = table.getSelectedRows();
                TableModel model = table.getModel();
                int i = 0;
                double[] values = new double[rows.length];
                while (i < rows.length) {
                    int index = table.getRowSorter().convertRowIndexToModel(rows[i]);
                    String value = ((DefaultTableModel)model).getValueAt(index, 0).toString();
                    values[i++] = Double.parseDouble(value);
//                    dataService.deletePoint(Double.parseDouble(value));
                }
                table.clearSelection();
                dataService.deletePoints(values);
            }
        });
        return panel;
    }

    public void addButton (Container container, String title, ActionListener listener){
        JButton button = new JButton(title);
        container.add(button);
        button.addActionListener(listener);
    }
}
