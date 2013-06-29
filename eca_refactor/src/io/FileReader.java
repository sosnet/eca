package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import datatypes.LambdaMatrix;

public class FileReader {

  private static FileReader instance;
  
  private FileReader() {};
  
  public static FileReader getInstance(){
    if (instance == null) {
      instance = new FileReader();
    }
    return instance;
  }
  
  public Set<LambdaMatrix> readLambdaMatrix (String path) throws Exception {
    Set<LambdaMatrix> buffer = new HashSet<>();
    
    java.io.FileReader fr = new java.io.FileReader(path);
    BufferedReader bfr = new BufferedReader(fr);
    
    for(;;){
      try {
        buffer.add(readMatrix(bfr));
      }
      catch (Exception e) {
        bfr.close();
        return buffer;
      }
    }
  }
  
  private LambdaMatrix readMatrix (BufferedReader br) throws Exception {
    int gsize = Integer.parseInt(br.readLine());
    int points = Integer.parseInt(br.readLine());
    
    LambdaMatrix lm = new LambdaMatrix(points, gsize);
    String matrixString = br.readLine();
//    [-1 0 1;1 -1 0;0 1 -1]
    StringTokenizer stRow = new StringTokenizer(matrixString,"[;]");
    for (int i = 0; i < points; i++) {
      String row = stRow.nextToken();
      StringTokenizer stCol = new StringTokenizer(row," ");
      for (int j = 0; j < points; j++) {
        lm.set(i, j, Integer.parseInt(stCol.nextToken()));
      }
    }
    
    br.readLine(); //empty line in input file
    
    return lm;
    
    
  }
  
}
