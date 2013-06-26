package io;

import java.util.HashSet;
import java.util.Set;

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
  
  public Set<LambdaMatrix> read (String path) {
    Set<LambdaMatrix> buffer = new HashSet<>();
    
    
  }
  
}
