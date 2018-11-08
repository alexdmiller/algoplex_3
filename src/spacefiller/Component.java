package spacefiller;

import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import spacefiller.graph.GridUtils;
import spacefiller.mapping.GraphTransformer;
import spacefiller.mapping.RShapeTransformer;

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

    mask = RShape.createRectangle(0, 0, canvas.width, canvas.height).diff(shape);
    mask.setFill(0xff000000);
    mask.setStrokeWeight(0);

    getGraphTransformer().addChild(new RShapeTransformer(shape));
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

  public List<Behavior> getBehaviors() {
    return behaviors;
  }

  public Component add(Behavior behavior) {
    behavior.setCanvas(canvas);
    behavior.setParent(parent);
    behavior.setShape(shape);

    behaviors.add(behavior);
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
