package businesslogic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import datatypes.GridPoint;
import datatypes.LambdaMatrix;

public class Worker implements Runnable {

  private List<LambdaMatrix> mats;
  ConcurrentHashMap<LambdaMatrix, Set<GridPoint>> buffer;

  public Worker(List<LambdaMatrix> matrices,
      ConcurrentHashMap<LambdaMatrix, Set<GridPoint>> buffer) {
    mats = matrices;
    this.buffer = buffer;
  }

  @Override
  public void run() {
    for (LambdaMatrix mat : mats)
      try {
      // System.out.println(mat.toString());
        Set<GridPoint> set = new CheckLambda(mat).realize();
        if (set.size() != mat.getDimenstion())
          ;
        set = new HashSet<GridPoint>();
        buffer.put(mat, set);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        // e.printStackTrace();
      }
    // TODO Auto-generated method stub

  }

}
