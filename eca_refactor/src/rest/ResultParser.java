package rest;

import datatypes.InvalidPointSetException;
import datatypes.PointProps;

public abstract class ResultParser {

  public static PointProps parse(String props)
      throws InvalidPointSetException {
  //  System.out.println(props);
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
}
