import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

public class TablePanel extends JPanel {

    private DefaultTableModel model;
    private JTable table;

    private JScrollPane scrollPane;

    private String[] columns;
    private Object[][] data;
    private String[] row;
    private String[][] rows;


    public TablePanel(DefaultTableModel model) {
        this.model = model;
//        this.model.setRowCount(0);
        table = new JTable(model);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        sorter.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                double arg1 = Double.parseDouble(o1);
                double arg2 = Double.parseDouble(o2);
                return arg1 <= arg2 ? -1: 1;
            }
        });
//        sorter.setSortsOnUpdates(true);
        table.setRowSorter(sorter);

        this.model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.println(e);
            }
        });

//        table.setModel(model);
//        this.model.fireTableDataChanged();

        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setPreferredSize(new Dimension(380,280));
        JPanel panel = new JPanel();
        this.add(scrollPane);
    }

    public TablePanel(String[] columns, Map<Double, Double> data) {
        this.columns = columns;

        this.data = new Object[data.keySet().size()][2];
        Iterator it = data.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Object key = it.next();
            this.data[i][0] = Double.parseDouble(key.toString());
            this.data[i][1] = data.get(key);
            i++;
//            points.add(new Point(Double.parseDouble(key.toString()), (double) model.get(key)));
        }

        this.data = new Object[][] {
            {0, 0},
            {1, 1},
            {2, 4}
        };

        table = new JTable(this.data, this.columns);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(380,280));
        this.add(scrollPane);
    }

    public JTable getTable() {
        return table;
    }

    public void updateModel(DefaultTableModel model) {
        this.model = model;
        table = new JTable(model);
    }

    public void updateData(Map<Double, Double> data) {
        rows = convertData(data);

//        model.addRow(this.data);
//        table.setModel(model);
//        model.fireTableDataChanged();

        model = new CustomTableModel();
        for (int i = 0; i < rows.length; i++) {
            model.addRow(rows[i]);
        }
        table.setModel(model);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        sorter.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                double arg1 = Double.parseDouble(o1);
                double arg2 = Double.parseDouble(o2);
                return arg1 <= arg2 ? -1: 1;
            }
        });
        sorter.setSortsOnUpdates(true);
        table.setRowSorter(sorter);
//        model.fireTableDataChanged();

//        table = new JTable(this.data, columns);


//        scrollPane = new JScrollPane(table);
//        scrollPane.setPreferredSize(new Dimension(380,280));
//        this.add(scrollPane);

//        table.repaint();
    }

    /*private Object[][] convertData(Map<Double, Double> data) {
        Object[][] result = new Object[data.keySet().size()][2];
        Iterator it = data.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Object key = it.next();
            result[i][0] = Double.parseDouble(key.toString());
            result[i][1] = data.get(key);
            i++;
        }
        return result;
    }*/

    private String[][] convertData(Map<Double, Double> data) {
        String[][] result = new String[data.keySet().size()][2];
        Iterator it = data.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Object key = it.next();
            result[i][0] = key.toString();
            result[i][1] = data.get(key).toString();
            i++;
        }
        return result;
    }
}
