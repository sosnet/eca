package main;

import io.FileReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import rest.RestClient;
import rest.ResultParser;

import businesslogic.Worker;
import datatypes.GridPoint;
import datatypes.InvalidPointSetException;
import datatypes.LambdaMatrix;
import datatypes.PointSet;

public class Main {

  static final int NUM_THREADS = 4;

  /**
   * @param args
   */
  public static void main(String[] args) {

    String inputPath;
    if (args.length > 0) {
      inputPath = args[0];
    } else {
      System.out.println(new File(".").getAbsolutePath());
      System.out.print("Enter path input file>");
      inputPath = new Scanner(System.in).nextLine();
    }

    Set<PointSet> points = null;
    try {
      points = FileReader.getInstance().readPointSet(inputPath);
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    for (PointSet p : points) {
      try {
        p.setPointProps(ResultParser.parseProps(RestClient.getInstance()
            .queryPoints(p)));
      } catch (InvalidPointSetException e) {
        // TODO Auto-generated catch block
        // e.printStackTrace();
      }
      System.out.println(p.toString());
    }
  }


}
