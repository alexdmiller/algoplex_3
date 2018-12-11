package spacefiller;

import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import spacefiller.graph.GridUtils;
import spacefiller.graph.Node;
import spacefiller.mapping.GraphTransformer;
import spacefiller.mapping.RShapeTransformer;
import spacefiller.mapping.Transformable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RShapeComponent {
  private static final int PADDING = 20;
  private static final int DIVISIONS = 10;
  private static Set<String> componentIDs = new HashSet<>();

  public static RShapeComponent createTriangleGrid(int rows, int cols, float cellSize, PApplet parent) {
    String componentID = createUniqueID("triangle-grid");

    GraphTransformer transformer = null;
    try {
      transformer = loadTransformer(componentID);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    if (transformer == null) {
      transformer = new GraphTransformer(GridUtils.createTriangleGrid(rows, cols, cellSize));
    }

    RShape shape = new RShape();

    for (Node[] tri : transformer.getPreTransformGrid().getTriangles()) {
      RShape triShape = new RShape();

      triShape.addMoveTo(tri[0].position.x, tri[0].position.y);
      triShape.addLineTo(tri[1].position.x, tri[1].position.y);
      triShape.addLineTo(tri[2].position.x, tri[2].position.y);
      triShape.addLineTo(tri[0].position.x, tri[0].position.y);

      shape.addChild(triShape);
    }

    RShapeComponent component = new RShapeComponent(shape, componentID, parent);

    PGraphics canvas = parent.createGraphics((int) shape.getWidth() + PADDING * 2, (int) shape.getHeight() + PADDING * 2, parent.P2D);
    component.canvas = canvas;



    transformer.setCanvas(canvas);
    transformer.translate(component.position.x, component.position.y);
    component.graphTransformer = transformer;

    component.updateMask();

    return component;
  }

  public static RShapeComponent createFromFile(String componentID, PApplet parent) {
    RShape shape = RG.loadShape("vector/" + componentID + ".svg");
    componentID = createUniqueID(componentID);

    RShapeComponent component = new RShapeComponent(shape, componentID, parent);

    PGraphics canvas = parent.createGraphics((int) shape.getWidth() + PADDING * 2, (int) shape.getHeight() + PADDING * 2, parent.P2D);
    component.canvas = canvas;

    GraphTransformer transformer = null;
    try {
      transformer = loadTransformer(componentID);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    if (transformer == null) {
      transformer = new GraphTransformer(GridUtils.createSimpleGrid(
          DIVISIONS, DIVISIONS, canvas.width / DIVISIONS, canvas.height / DIVISIONS));
      // graphTransformer = new GraphTransformer(GridUtils.createTriangleGrid(10 ,10, 50));
    }

    transformer.setCanvas(canvas);
    transformer.translate(component.position.x, component.position.y);
    component.graphTransformer = transformer;

    shape.translate(PADDING, PADDING);

    if (shape.children.length > 0) {
      int id = 0;
      for (RShape child : shape.children) {
        transformer.addChild(loadOrCreateRShapeTransformer(componentID, child, id));
        id++;
      }
    } else {
      transformer.addChild(loadOrCreateRShapeTransformer(componentID, shape, 0));
    }

    component.updateMask();

    return component;
  }

  private static String createUniqueID(String componentID) {
    int i = 0;
    while (componentIDs.contains(componentID + "-" + i)) {
      i++;
    }

    componentID = componentID + "-" + i;
    componentIDs.add(componentID);
    return componentID;
  }


  private static GraphTransformer loadTransformer(String componentID) throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream("serialized/" + componentID + ".ser");
    ObjectInputStream in = new ObjectInputStream(fileIn);
    GraphTransformer graphTransformer = (GraphTransformer) in.readObject();
    in.close();
    fileIn.close();

    return graphTransformer;
  }

  private static RShapeTransformer loadOrCreateRShapeTransformer(String componentID, RShape shape, int id) {
    RShapeTransformer transformer = null;
    try {
      FileInputStream fileIn = new FileInputStream("serialized/" + componentID + "-shape" + id + ".ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      transformer = (RShapeTransformer) in.readObject();
      in.close();
      fileIn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException i) {
      i.printStackTrace();
    } catch (ClassNotFoundException c) {
      c.printStackTrace();
    }

    if (transformer != null) {
      transformer.setShape(shape);
      transformer.matchHandlesToShape();
    } else {
      transformer = new RShapeTransformer();
      transformer.setShape(shape);
      transformer.createHandlesFromShape();
    }

    return transformer;
  }

  private String componentID;
  private GraphTransformer graphTransformer;
  private PVector position;
  private RShape shape;
  private List<Behavior> behaviors;
  private RShape mask;
  private boolean enabled = true;

  protected PGraphics canvas;
  protected PApplet parent;




  // TODO:
  // Create a new version of this that doesn't look up shape in a file;
  // instead, creates shape automatically based on graph.
  // also, add new edit mode that edits points of graph transformer rather than children




  public RShapeComponent(RShape shape, String componentID, PApplet parent) {
    this.shape = shape;
    this.componentID = componentID;
    this.parent = parent;
    this.position = new PVector();
    this.behaviors = new ArrayList<>();
  }

  public void updateMask() {
    if (shape.children != null && shape.children.length > 0) {
      RShape sum = new RShape(shape.children[0]);

      for (RShape child : shape.children) {
        sum = child.union(sum);
      }

      mask = RShape.createRectangle(0, 0, canvas.width, canvas.height).diff(sum);
    } else {
      mask = RShape.createRectangle(0, 0, canvas.width, canvas.height).diff(shape);
    }
    mask.setFill(0xff000000);
    mask.setStrokeWeight(0);
  }

  public PVector getPosition() {
    return position;
  }

  public void setPosition(PVector position) {
    if (graphTransformer != null) {
      PVector delta = PVector.sub(position, this.position);
      graphTransformer.translate(delta.x, delta.y);
    }

    this.position = position;
    // todo: update canvas if it's already there
  }

  public RShapeComponent setPosition(float x, float y) {
    setPosition(new PVector(x, y));
    return this;
  }

  public void save() {
    try {
      FileOutputStream fileOut =
          new FileOutputStream("serialized/" + componentID + ".ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(graphTransformer);
      out.close();
      fileOut.close();

      int id = 0;
      for (Transformable transformer : graphTransformer.getChildren()) {
        RShapeTransformer rt = (RShapeTransformer) transformer;

        fileOut =
            new FileOutputStream("serialized/" + componentID + "-shape" + id + ".ser");
         out = new ObjectOutputStream(fileOut);
        out.writeObject(rt);
        out.close();
        fileOut.close();

        id++;
      }
    } catch (IOException i) {
      i.printStackTrace();
    }
  }

  public List<Behavior> getBehaviors() {
    return behaviors;
  }

  public RShapeComponent add(Behavior behavior) {
    behavior.setCanvas(canvas);
    behavior.setParent(parent);
    behavior.setShape(shape);

    behaviors.add(behavior);
    behavior.setup();
    return this;
  }

  public void draw() {
    if (enabled) {
      canvas.clear();
      for (Behavior behavior : behaviors) {
        if (behavior.isActive()) {
          behavior.draw();
        }
      }

      mask.draw(canvas);
    }
  }

  public void drawCalibration() {
    if (enabled) {
      shape.setFill(0);
      shape.setStroke(0xffffffff);
      shape.setStrokeWeight(2);
      if (shape.children != null) {
        for (RShape child : shape.children) {
          child.setFill(0);
          child.setStroke(0xffffffff);
          child.setStrokeWeight(2);
        }
      }
      shape.draw(canvas);
    }
  }

  public GraphTransformer getGraphTransformer() {
    return graphTransformer;
  }

  public PGraphics getCanvas() {
    return canvas;
  }

  public boolean initialized() {
    return canvas != null;
  }

  public boolean isActive() {
    for (Behavior b : behaviors) {
      if (b.isActive()) {
        return true;
      }
    }

    return false;
  }

  public void triggerRandom() {
    if (enabled) {
      if (!behaviors.isEmpty()) {
        int index = (int) Math.floor(Math.random() * behaviors.size());
        behaviors.get(index).trigger();
      }
    }
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
