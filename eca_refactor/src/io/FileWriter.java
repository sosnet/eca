package io;

public class FileWriter {

  private static FileWriter instance;
  
  private FileWriter() {};
  
  public static FileWriter getInstance(){
    if (instance == null) {
      instance = new FileWriter();
    }
    return instance;
  }
  
}
