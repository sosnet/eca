package gui;

import gui.widgets.Grid;
import gui.widgets.GridPointListModel;
import gui.widgets.GridPointRenderer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businesslogic.Computer;
import businesslogic.conf.ComputationConstants;

import types.GridPoint;
import types.LambdaMatrix;

public class Gui extends JFrame {

  private int label = 0;
  private Grid grid;
  private JList<GridPoint> points;
  private GridPoint tmp;
  private JPanel sidePane;
  private JTextArea output;
  private JToggleButton btnConstruct;
  private boolean constructionMode;
  private LambdaMatrix constructionMatrix;

  public Gui() {
    super();
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException
        | IllegalAccessException | UnsupportedLookAndFeelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    constructionMode = false;
    initComponents();

  }

  private void initComponents() {
    grid = new Grid();
    grid.setSize(800, 600);
    sidePane = new JPanel();
    btnConstruct = new JToggleButton("Construct PointSet");
    output = new JTextArea();
    output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    output.setRows(15);
    output.setColumns(15);
    output.setTabSize(2);
    output.setAutoscrolls(false);
    output.setEditable(false);
    JScrollPane scrollp = new JScrollPane(output);
    scrollp.setBounds(new Rectangle(80, 300));
    this.points = new JList<GridPoint>(new GridPointListModel<GridPoint>());
    points.setVisibleRowCount(20);
    // this.getContentPane().add(grid, BorderLayout.CENTER);
    JScrollPane scroll = new JScrollPane(points);
    sidePane.setLayout(new BorderLayout());
    sidePane.add(scroll, BorderLayout.NORTH);
    sidePane.add(scrollp, BorderLayout.CENTER);
    sidePane.add(btnConstruct, BorderLayout.SOUTH);

    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, grid,
        sidePane);
    this.getContentPane().add(split);
    this.points.setCellRenderer(new GridPointRenderer<GridPoint>());
    this.points
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    this.points.addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent e) {

        JList<GridPoint> lsm = (JList<GridPoint>) e.getSource();
        int[] sel = lsm.getSelectedIndices();
        for (int i = 0; i < lsm.getModel().getSize(); ++i) {
          lsm.getModel().getElementAt(i)
              .setHighlighted(sel[0] <= i && i <= sel[sel.length - 1]);
        }
        grid.repaint();
      }
    });

    split.setDividerLocation(600);
    this.pack();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponentListener(new ComponentListener() {
      public void componentResized(ComponentEvent evt) {
        Component c = (Component) evt.getSource();
      }

      @Override
      public void componentMoved(ComponentEvent e) {
      }

      @Override
      public void componentShown(ComponentEvent e) {
      }

      @Override
      public void componentHidden(ComponentEvent e) {
      }
    });

    grid.addMouseMotionListener(new MouseMotionListener() {

      @Override
      public void mouseMoved(MouseEvent e) {
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        gridMouseDragged(e);
      }
    });

    grid.addMouseListener(new MouseListener() {

      @Override
      public void mouseReleased(MouseEvent e) {
        gridMouseReleased(e);
      }

      @Override
      public void mousePressed(MouseEvent e) {
        gridMousePressed(e);
      }

      @Override
      public void mouseExited(MouseEvent e) {
      }

      @Override
      public void mouseEntered(MouseEvent e) {
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        gridMouseClicked(e);
      }
    });

    btnConstruct.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnConstructActionPerformed(e);
      }
    });
  }

  private void btnConstructActionPerformed(ActionEvent e) {
    constructionMatrix = grid.getLambdaMatrix();
    try {
      System.out.println(grid.getLambdaMatrix().realize());
    } catch (Exception e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    try {
      label = 0;
      grid.setPoints(new TreeSet<GridPoint>());
      ((GridPointListModel<GridPoint>) points.getModel()).clear();
      constructionMode = btnConstruct.getModel().isSelected();
      grid.setConstructionMode(constructionMode);
      grid.setConstructionMatrix(constructionMatrix);

      GridPointListModel<GridPoint> model = (GridPointListModel<GridPoint>) points
          .getModel();
      model.add(new GridPoint(8, 1));
      GridPoint p = new GridPoint(16, 1);
      p.setName(++label);
      model.add(p);
      TreeSet<GridPoint> pnt = new TreeSet<GridPoint>(model.getAll());
      grid.setPoints(pnt);

    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    grid.repaint();

  }

  private void gridMouseClicked(MouseEvent e) {

    if (e.getButton() != MouseEvent.BUTTON1)
      return;
   
    GridPointListModel<GridPoint> model = (GridPointListModel<GridPoint>) points
        .getModel();

    GridPoint p = new GridPoint(grid.getX(e.getX()), grid.getY(e.getY()));
    GridPoint toRemove = null;
    for (int i = 0; i < model.getSize(); ++i) {
      if (model.get(i).compareTo(p) == 0) {
        toRemove = model.get(i);
      }
    }
    if (toRemove == null) {
      if ((!constructionMode)
          || (constructionMode && model.getSize() < constructionMatrix
              .getDimenstion())) {
        p.setName(++label);
        model.addElement(p);
      }
    } else {
     
        --label;
        model.removeElement(toRemove);
      
    }
    TreeSet<GridPoint> pnt = new TreeSet<>();

    for (int i = 0; i < model.getSize(); ++i) {
      model.get(i).setHighlighted(false);
      pnt.add(model.get(i));
    }
    try {
      grid.setPoints(pnt);
      model.sort();
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    grid.repaint();
    points.clearSelection();

    if (!constructionMode) {
      output.setText(grid.getLambdaMatrix().toString());
      output.append("\n\n");
      output.append(grid.getLambdaMatrix().getCollinearPoints().toString());
      output.setCaretPosition(1);
    } else {
      if (model.getSize() == constructionMatrix.getDimenstion()) {
        try {
          if (constructionMatrix.equals(Computer
              .fingerprint(new TreeSet<GridPoint>(model.getAll())))) {
            JOptionPane.showMessageDialog(this, "Valid!");

            grid.setPoints(new TreeSet<GridPoint>(model.copyContents()));
          } else {
            JOptionPane.showMessageDialog(this,
                Computer.fingerprint(new TreeSet<GridPoint>(model.getAll()))
                    .toString(), "invalid Lambda Matrix",
                JOptionPane.ERROR_MESSAGE);
          }
        } catch (Exception e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    }
  }

  private void gridMousePressed(MouseEvent e) {
    if (e.getButton() != e.BUTTON3)
      return;
    GridPoint p = new GridPoint(grid.getX(e.getX()), grid.getY(e.getY()));
    GridPointListModel<GridPoint> model = (GridPointListModel<GridPoint>) points
        .getModel();
    tmp = null;
    for (int i = 0; i < model.getSize(); ++i) {
      if (model.get(i).compareTo(p) == 0) {
        tmp = model.get(i);
      }
    }
  }

  private void gridMouseReleased(MouseEvent e) {
    grid.clearOutline();
    if (e.getButton() != e.BUTTON3)
      return;
    GridPoint p = new GridPoint(grid.getX(e.getX()), grid.getY(e.getY()));
    GridPointListModel<GridPoint> model = (GridPointListModel<GridPoint>) points
        .getModel();
    if (tmp == null) {

    } else {
      p.setName(tmp.getName());
      model.removeElement(tmp);
      TreeSet<GridPoint> pnt = new TreeSet<>();
      model.add(p);
      for (int i = 0; i < model.getSize(); ++i) {
        model.get(i).setHighlighted(false);
        pnt.add(model.get(i));
      }
      try {
        grid.setPoints(pnt);
        model.sort();
      } catch (Exception e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      tmp = null;
      grid.repaint();
      points.clearSelection();
      if (!constructionMode) {
        output.setText(grid.getLambdaMatrix().toString());
        output.append(grid.getLambdaMatrix().getCollinearPoints().toString());
        output.setCaretPosition(1);
      }
    }
  }

  private void gridMouseDragged(MouseEvent e) {
    if (tmp != null) {
      grid.drawOutline((e.getX()), (e.getY()));
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println(new File(".").getAbsolutePath());
    System.out.println(ComputationConstants.PATH_PROLOG_DB);
    Gui g = new Gui();
    g.setSize(800, 600);
    g.setVisible(true);
  }
}
