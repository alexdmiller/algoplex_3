package spacefiller;

import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import spacefiller.graph.GridUtils;
import spacefiller.mapping.GraphTransformer;
import spacefiller.mapping.RShapeTransformer;
import spacefiller.mapping.Transformable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Component {
  private static final int PADDING = 20;

  private static Set<String> componentIDs = new HashSet<>();

  private String componentID;
  private GraphTransformer graphTransformer;
  private PVector position;
  private RShape shape;
  private List<Behavior> behaviors;
  private RShape mask;

  protected PGraphics canvas;
  protected PApplet parent;

  public Component(String componentID, PApplet parent) {
    this.shape = RG.loadShape("vector/" + componentID + ".svg");
//    RG.setPolygonizerLength(10f);
//    shape.polygonize();


    // TODO: check for duplicate names here
    int i = 0;
    while (componentIDs.contains(componentID + "-" + i)) {
      i++;
    }

    this.componentID = componentID + "-" + i;
    componentIDs.add(this.componentID);

    this.parent = parent;
    this.position = new PVector();
    this.behaviors = new ArrayList<>();
    this.canvas = parent.createGraphics((int) shape.getWidth() + PADDING * 2, (int) shape.getHeight() + PADDING * 2, parent.P2D);

    shape.translate(PADDING, PADDING);

    loadOrCreateTransformer(10);

    if (shape.children.length > 0) {
      mask = RShape.createRectangle(0, 0, canvas.width, canvas.height).diff(shape.children[0]);
    } else {
      mask = RShape.createRectangle(0, 0, canvas.width, canvas.height).diff(shape);
    }

    mask.setFill(0xff000000);
    mask.setStrokeWeight(0);

    // TODO: Load RShapeTransformer

    if (shape.children.length > 0) {
      int id = 0;
      for (RShape child : shape.children) {
        getGraphTransformer().addChild(loadOrCreateRShapeTransformer(child, id));
        id++;
      }
    } else {
      getGraphTransformer().addChild(loadOrCreateRShapeTransformer(shape, 0));
    }
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

  public Component setPosition(float x, float y) {
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

  private void loadOrCreateTransformer(int divisions) {
    try {
      FileInputStream fileIn = new FileInputStream("serialized/" + componentID + ".ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      graphTransformer = (GraphTransformer) in.readObject();
      in.close();
      fileIn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException i) {
      i.printStackTrace();
    } catch (ClassNotFoundException c) {
      c.printStackTrace();
    }

    if (graphTransformer == null) {
      graphTransformer = new GraphTransformer(GridUtils.createSimpleGrid(
          divisions, divisions, canvas.width / divisions, canvas.height / divisions));
    }

    graphTransformer.setCanvas(canvas);
    graphTransformer.translate(position.x, position.y);
  }

  private RShapeTransformer loadOrCreateRShapeTransformer(RShape shape, int id) {
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

  public List<Behavior> getBehaviors() {
    return behaviors;
  }

  public Component add(Behavior behavior) {
    behavior.setCanvas(canvas);
    behavior.setParent(parent);
    behavior.setShape(shape);

    behaviors.add(behavior);
    behavior.setup();
    return this;
  }

  public void draw() {
    canvas.clear();
    for (Behavior behavior : behaviors) {
      behavior.draw();
    }

    mask.draw(canvas);
  }

  public void drawCalibration() {
    shape.setFill(0);
    shape.setStroke(0xffffffff);
    shape.setStrokeWeight(2);
    for (RShape child : shape.children) {
      child.setFill(0);
      child.setStroke(0xffffffff);
      child.setStrokeWeight(2);
    }
    shape.draw(canvas);
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
    if (!behaviors.isEmpty()) {
      int index = (int) Math.floor(Math.random() * behaviors.size());
      behaviors.get(index).trigger();
    }
  }
}
