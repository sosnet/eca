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
    StringBuffer buf = new StringBuffer();
    for (GridPoint p : points) {
      buf.append(p.x).append("_").append(p.y).append("_");
    }
    return buf.substring(0, buf.length() - 1);
  }

}
