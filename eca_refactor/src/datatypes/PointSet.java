package datatypes;

public class PointSet {
  GridPoint[] points;
  PointProps props;
  LambdaMatrix matrix;

  public PointSet(GridPoint[] points, LambdaMatrix matrix) {
    this.points = points;
    this.matrix = matrix;
  }

  public GridPoint[] getPoints() {
    return points;
  }

  public LambdaMatrix getLambdaMatrix() {
    return matrix;
  }

  public PointProps getPointPros() {
    return props;
  }

  public void setPointProps(PointProps props) {
    this.props = props;
  }

  public String stringify() {
    StringBuilder buf = new StringBuilder();
    for (GridPoint p : points) {
      buf.append(p.x).append("_").append(p.y).append("_");
    }
    return buf.substring(0, buf.length() - 1);
  }

  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("Points:");
    for (GridPoint p : points) {
      buf.append("\n").append(p.toString());
    }
    if (matrix != null)
      buf.append("\nLambda Matrix:\n").append(matrix.toString());
    if (props != null)
      buf.append("\nPoint Properties:\n").append(props.toString());
    return buf.toString();

  }
}
