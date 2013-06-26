package main;

import java.util.Set;

import datatypes.LambdaMatrix;

import io.FileReader;

public class Main {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String inputPath = args[0];
    String outputPath = args[1];
    
    try {
      Set<LambdaMatrix> lmSet = FileReader.getInstance().read(inputPath);
      for (LambdaMatrix m: lmSet) {
        System.out.println(m.toString());
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
    
  }

}
