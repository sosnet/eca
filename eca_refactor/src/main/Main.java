package main;

import io.FileReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
    String inputPath;
    String outputPath;
    if (args.length > 0) {
      inputPath = args[0];
      outputPath = args[1];
    } else {
      System.out.println(new File(".").getAbsolutePath());
      System.out.print("Enter path input file>");
      inputPath = new Scanner(System.in).nextLine();
    }
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

    ConcurrentHashMap<LambdaMatrix, Set<GridPoint>> realisations = new ConcurrentHashMap<>();
    int numThreads = NUM_THREADS;
    if (lmSet.size() < numThreads)
      numThreads = lmSet.size();

    double workload = lmSet.size() / numThreads;
    double workloadFloor = Math.floor((double) (lmSet.size() / numThreads));

    List<Thread> workers = new LinkedList<>();

    for (int j = 0; j < numThreads; j++) {
      List<LambdaMatrix> mats = lmSet.subList((int) (j * workloadFloor),
          (int) ((j + 1) * workloadFloor));
      Thread t = new Thread(new Worker(mats, realisations));
      workers.add(t);
      t.start();
    }

    if (Double.compare(workload, workloadFloor) != 0) {
      List<LambdaMatrix> matsRest = lmSet.subList(
          (int) (numThreads * workloadFloor), lmSet.size() - 1);
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

    for (LambdaMatrix m : realisations.keySet()) {
      System.out.println(m.toString() + ": ");
      if (realisations.get(m).size() == 0)
        System.out.println("INVALID!");
      else {
        for (GridPoint p : realisations.get(m)) {
          System.out.println(p.toString());
        }

      }
      System.out.println("-------------");
    }

  }

}
