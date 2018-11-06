package spacefiller;

import de.looksgood.ani.Ani;
import geomerative.RG;
import geomerative.RPoint;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import spacefiller.graph.GridUtils;
import spacefiller.graph.renderer.BasicGraphRenderer;
import spacefiller.mapping.GraphTransformer;
import spacefiller.mapping.RShapeTransformer;
import spacefiller.mapping.Transformable;

import java.util.ArrayList;
import java.util.List;

public class Component {
  private GraphTransformer graphTransformer;
  private BasicGraphRenderer graphRenderer;
  private PVector position;
  private RShape shape;
  private RShape mask;
  private List<Behavior> behaviors;

  protected PGraphics canvas;
  protected PApplet parent;

  public Component(RShape shape, PApplet parent) {
    this.shape = shape;
    this.parent = parent;
    this.position = new PVector();
    this.behaviors = new ArrayList<>();

    initCanvas((int) shape.getWidth(), (int) shape.getHeight(), 10);

    mask = RShape.createRectangle(0, 0, canvas.width, canvas.height).diff(shape);
    mask.setFill(0xff000000);
    mask.setStroke(0xff000000);
    mask.setStrokeWeight(1);

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

  public void setPosition(float x, float y) {
    setPosition(new PVector(x, y));
  }

  public void initCanvas(float width, float height, int divisions) {
    canvas = parent.createGraphics(
        (int) width, (int) height,
        parent.P2D);

    graphTransformer = new GraphTransformer(GridUtils.createSimpleGrid(
        divisions, divisions, width / divisions, height / divisions), canvas);
    graphTransformer.translate(position.x, position.y);
    graphRenderer = new BasicGraphRenderer(2);
    graphRenderer.setColor(parent.color(255));
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
