package businesslogic;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import datatypes.GridPoint;
import datatypes.LambdaMatrix;

public class Worker implements Runnable {

  private List<LambdaMatrix> mats;
  private ConcurrentLinkedQueue<Set<GridPoint>> buffer;
  
  public Worker(List<LambdaMatrix> matrices, ConcurrentLinkedQueue<Set<GridPoint>> buffer){
    mats=matrices;
    this.buffer=buffer;
  }
  @Override
  public void run() {
    for(LambdaMatrix mat:mats)
      try {
        buffer.add(new CheckLambda(mat).realize());
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
      }
    // TODO Auto-generated method stub

  }

}
