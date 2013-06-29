package rest;

import datatypes.GridPoint;
import datatypes.InvalidPointSetException;
import datatypes.PointProps;
import datatypes.PointSet;

public abstract class ResultParser {

  public static PointProps parseProps(String props)
      throws InvalidPointSetException {
     System.out.println(props);
    if (!props.contains("_"))
      throw new InvalidPointSetException("Invalid Point Set!");
    String[] result = props.split("_");
    return new PointProps(Integer.parseInt(result[0]),
        Integer.parseInt(result[1]), Integer.parseInt(result[2]),
        Integer.parseInt(result[3]), Integer.parseInt(result[4]),
        Integer.parseInt(result[5]), Integer.parseInt(result[6]),
        Integer.parseInt(result[7]), Integer.parseInt(result[8]),
        Integer.parseInt(result[9]), Integer.parseInt(result[10]),
        Integer.parseInt(result[11]), Integer.parseInt(result[12]));

  }

  public static PointSet parsePointSet(String pointset)
      throws InvalidPointSetException {
    // System.out.println(props);
    if (!pointset.contains(","))
      throw new InvalidPointSetException("Invalid Point Set!");
    String[] result = pointset.split(",");
    GridPoint[] points = new GridPoint[result.length / 2];
    for (int i = 0; i < result.length; i += 2) {
      points[i / 2] = new GridPoint(i / 2, Integer.parseInt(result[i]),
          Integer.parseInt(result[i + 1]));
    }
    return new PointSet(points, null);

  }
}
