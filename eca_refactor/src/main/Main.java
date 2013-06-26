package main;

import io.FileReader;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import businesslogic.CheckLambda;
import businesslogic.Worker;

import datatypes.GridPoint;
import datatypes.LambdaMatrix;

public class Main {

  static final int NUM_THREADS = 4;

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String inputPath = args[0];
    String outputPath = args[1];
    LinkedList<LambdaMatrix> lmSet;

    try {
      lmSet = new LinkedList<>();
      lmSet.addAll(FileReader.getInstance().read(inputPath));
      // for (LambdaMatrix m: lmSet) {
      // System.out.println(m.toString());
      // }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return;
    }

    ConcurrentLinkedQueue<Set<GridPoint>> realisations = new ConcurrentLinkedQueue<>();
    int numThreads = NUM_THREADS;
    if (lmSet.size() < numThreads)
      numThreads = lmSet.size();

    double workload = lmSet.size() / numThreads;
    double workloadFloor = Math.floor((double) (lmSet.size() / numThreads));

    List<Thread> workers = new LinkedList<>();
    
    for (int j = 0; j < numThreads; j++) {
      List<LambdaMatrix> mats = lmSet.subList((int) (j*workloadFloor), (int) ((j + 1) * workloadFloor));
      Thread t = new Thread(new Worker(mats, realisations));
      workers.add(t);
      t.start();
    }
    
    if (Double.compare(workload, workloadFloor) != 0) {
      List<LambdaMatrix> matsRest = lmSet.subList((int) (numThreads*workloadFloor), lmSet.size()-1);
      Thread t = new Thread(new Worker(matsRest, realisations));
      workers.add(t);
      t.start();
    }
    
    for (Thread t : workers) {
      try {
        t.join();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    for (Set<GridPoint> realisation : realisations) {
      for (GridPoint p : realisation) {
        System.out.println(p.toString());
      }
    }
    
    

    
    
   

  }

}
