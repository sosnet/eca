package gui.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import types.GridPoint;

public class GridPointListModel<E extends GridPoint> extends
    AbstractListModel<E> {

  private class LabelComparator<E extends GridPoint> implements Comparator<E> {

    @Override
    public int compare(E o1, E o2) {
      return Integer.compare(o1.getName(), o2.getName());
    }

  }

  private LinkedList<E> values;
  private LabelComparator<E> cmp;

  public GridPointListModel() {
    values = new LinkedList<>();
    cmp = new LabelComparator<>();
  }

  public void add(E elem) {
    values.add(elem);
    fireContentsChanged(this, 0, getSize());
  }

  public void remove(E elem) {
    values.remove(elem);
    fireContentsChanged(this, 0, getSize());
  }

  @Override
  public int getSize() {
    return values.size();
  }

  @Override
  public E getElementAt(int index) {
    return values.get(index);
  }

  public E get(int index) {
    return getElementAt(index);
  }

  public void addElement(E elem) {
    add(elem);
  }

  public void removeElement(E elem) {
    remove(elem);
  }

  public void sort() {
    Collections.sort(values, cmp);
    fireContentsChanged(this, 0, getSize());
  }

  public void clear() {
    values.clear();
    fireContentsChanged(this, 0, getSize());
  }

  public Collection<E> getAll() {
    return values;
  }

  public List<E> copyContents(){
    List<E> cpy = new ArrayList<E>(values.size());
    Collections.copy(cpy, values);
    return cpy;
  }
}
