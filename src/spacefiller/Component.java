package spacefiller;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import spacefiller.graph.GridUtils;
import spacefiller.graph.renderer.BasicGraphRenderer;
import spacefiller.mapping.GraphTransformer;
import spacefiller.mapping.Transformable;

import java.util.ArrayList;
import java.util.List;

public class Component {
  private GraphTransformer graphTransformer;
  private BasicGraphRenderer graphRenderer;
  private PVector position;

  protected PGraphics canvas;
  protected PApplet parent;

  protected float energy;

  public Component(PApplet parent) {
    this.parent = parent;
    this.position = new PVector();
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
    graphTransformer = new GraphTransformer(GridUtils.createSimpleGrid(
        divisions, divisions, width / divisions, height / divisions));
    graphTransformer.translate(position.x, position.y);
    graphRenderer = new BasicGraphRenderer(2);
    graphRenderer.setColor(parent.color(255));
    canvas = parent.createGraphics(
        (int) graphTransformer.getPreTransformGrid().getWidth(),
        (int) graphTransformer.getPreTransformGrid().getHeight(),
        parent.P2D);
  }

  public void draw() {
  }

  public void drawCalibration() {

  }

  public GraphTransformer getGraphTransformer() {
    return graphTransformer;
  }

  protected void addChild(Transformable transformable) {
    getGraphTransformer().addChild(transformable);
  }

  public PGraphics getCanvas() {
    return canvas;
  }

  public boolean initialized() {
    return canvas != null;
  }

  public void trigger() {
    energy = 1;
    Ani.to(this, 3f, "energy", 0f);
  }

  public boolean isActive() {
    return energy > 0;
  }
}
