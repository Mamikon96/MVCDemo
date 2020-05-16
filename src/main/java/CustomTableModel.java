import org.jfree.data.xy.XYSeries;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.*;

public class CustomTableModel extends DefaultTableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<Point> points = new ArrayList<Point>();

    @Override
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        super.fireTableRowsUpdated(firstRow, lastRow);
    }

    public CustomTableModel(List<Point> points) {
        this.points = points;
    }

    public CustomTableModel() {
    }

    public CustomTableModel(Map<Double, Double> model) {
        Iterator it = model.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            points.add(new Point(Double.parseDouble(key.toString()), (double) model.get(key)));
        }
    }

    public void updateModel(Map<Double, Double> model) {
        Iterator it = model.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            points.add(new Point(Double.parseDouble(key.toString()), (double) model.get(key)));
        }
    }




    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "X";
            case 1:
                return "Y";
        }
        return "";
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
//        return columnIndex == 0;
    }

    /*public Object getValueAt(int rowIndex, int columnIndex) {
        Point bean = points.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bean.getX();
            case 1:
                return bean.getY();
        }
        return "";
    }*/

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
}
