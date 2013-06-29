package datatypes;

public class PointProps {
  private final int clazz;
  private final int triangles, trianglesCNC;
  private final int tetragons, tetragonsCNC, tetragonsCC, tetragonsNCNC,
      tetragonsNCC;
  private final int pentagons, pentagonsCNC, pentagonsCC, pentagonsNCNC,
      pentagonsNCC;

  public PointProps(int clazz, int triangles, int trianglesCNC, int tetragons,
      int tetragonsCNC, int tetragonsCC, int tetragonsNCNC, int tetragonsNCC,
      int pentagons, int pentagonsCNC, int pentagonsCC, int pentagonsNCNC,
      int pentagonsNCC) {
    this.clazz = clazz;
    this.triangles = triangles;
    this.trianglesCNC = trianglesCNC;
    this.tetragons = tetragons;
    this.tetragonsCC = tetragonsCC;
    this.tetragonsCNC = tetragonsCNC;
    this.tetragonsNCC = tetragonsNCC;
    this.tetragonsNCNC = tetragonsNCNC;
    this.pentagons = pentagons;
    this.pentagonsCC = pentagonsCC;
    this.pentagonsCNC = pentagonsCNC;
    this.pentagonsNCC = pentagonsNCC;
    this.pentagonsNCNC = pentagonsNCNC;

  }

  public int getClazz() {
    return clazz;
  }

  public int getTriangles() {
    return triangles;
  }

  public int getTrianglesConvexNotCollinear() {
    return trianglesCNC;
  }

  public int getTetragons() {
    return tetragons;
  }

  public int getTetragonsConvexNotCollinear() {
    return tetragonsCNC;
  }

  public int getTetragonsConvexCollinear() {
    return tetragonsCC;
  }

  public int getTetragonsNotConvexNotCollinear() {
    return tetragonsNCNC;
  }

  public int getTetragonsNotConvexCollinear() {
    return tetragonsNCC;
  }

  public int getPentagons() {
    return pentagons;
  }

  public int getPentagonsConvecNotCollinear() {
    return pentagonsCNC;
  }

  public int getPentagonsConvexCollinear() {
    return pentagonsCC;
  }

  public int getPentagonsNotConvexNotCollinear() {
    return pentagonsNCNC;
  }

  public int getPentagonsNotConvexCollinear() {
    return pentagonsNCC;
  }

  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("Class: ").append(clazz).append("\n\n");

    buf.append("\t").append("Empty triangles: ").append(triangles).append("\n");
    buf.append("\t\t").append("Empty convex / not collinear triangles:     ")
        .append(trianglesCNC).append("\n\n");

    buf.append("\t").append("Empty tetragons: ").append(tetragons).append("\n");
    buf.append("\t\t").append("Empty convex / not collinear tetragons:     ")
        .append(tetragonsCNC).append("\n");
    buf.append("\t\t").append("Empty convex / collinear tetragons:         ")
        .append(tetragonsCC).append("\n");
    buf.append("\t\t").append("Empty not convex / not collinear tetragons: ")
        .append(tetragonsNCNC).append("\n");
    buf.append("\t\t").append("Empty not convex / collinear tetragons:     ")
        .append(tetragonsNCC).append("\n\n");

    buf.append("\t").append("Empty pentagons: ").append(pentagons).append("\n");
    buf.append("\t\t").append("Empty convex / not collinear pentagons:     ")
        .append(pentagonsCNC).append("\n");
    buf.append("\t\t").append("Empty convex / collinear pentagons:         ")
        .append(pentagonsCC).append("\n");
    buf.append("\t\t").append("Empty not convex / not collinear pentagons: ")
        .append(pentagonsNCNC).append("\n");
    buf.append("\t\t").append("Empty not convex / collinear pentagons:     ")
        .append(pentagonsNCC);
    return buf.toString();

  }
}
