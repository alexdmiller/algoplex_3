package spacefiller;

import de.looksgood.ani.Ani;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;

public abstract class Behavior {
  protected RShape shape;
  protected PGraphics canvas;
  protected PApplet parent;
  protected float energy;

  public RShape getShape() {
    return shape;
  }

  public void setShape(RShape shape) {
    this.shape = shape;
  }

  public PApplet getParent() {
    return parent;
  }

  public void setParent(PApplet parent) {
    this.parent = parent;
  }

  public PGraphics getCanvas() {
    return canvas;
  }

  public void setCanvas(PGraphics canvas) {
    this.canvas = canvas;
  }

  abstract public void draw();

  public void trigger() {
    energy = 1;
    Ani.to(this, 1f, "energy", 0f);
  }

  public boolean isActive() {
    return energy > 0;
  }
}
