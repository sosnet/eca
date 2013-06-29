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

}
