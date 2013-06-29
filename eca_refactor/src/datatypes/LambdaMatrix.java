package datatypes;

public class LambdaMatrix {

  private int[][] matrix;
  private int gridsize;

  public LambdaMatrix(int dimension, int gridsize) {
    matrix = new int[dimension][dimension];
    this.gridsize = gridsize;
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();

    buf.append("     ");
    int dimension = matrix.length;
    for (int a = 0; a < dimension; ++a)
      buf.append(String.format("[%02d] ", a));
    for (int a = 0; a < dimension; ++a) {
      buf.append(String.format("\n[%02d]  ", a));
      for (int b = 0; b < dimension; ++b) {
        buf.append(((matrix[a][b] != -1) ? (String.format("%2d   ",
            matrix[a][b])) : ("--   ")));
      }
    }

    return buf.toString();

  }

  public int getDimenstion() {
    return matrix.length;
  }

  public int get(int p1, int p2) {
    if (p1 < 0 || p2 < 0) {
      System.err.println("p1: " + p1 + ", p2: " + p2);
      return -1;
    }

    return matrix[p1][p2];
  }

  public void set(int p1, int p2, int value) {
    matrix[p1][p2] = value;
  }

  public int getGridsize() {
    return gridsize;
  }

}
