package gui.widgets;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import types.GridPoint;

public class GridPointRenderer<E extends GridPoint> extends JLabel implements
    ListCellRenderer<E> {

  public GridPointRenderer() {
    setOpaque(true);
  }

  public Component getListCellRendererComponent(JList list, E value, int index,
      boolean isSelected, boolean cellHasFocus) {
    setText(String.format("%2d: [%2d, %2d]", value.getName(),
        (int) value.getX(), (int) value.getY()));

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    list.repaint();
    return this;
  }
}
