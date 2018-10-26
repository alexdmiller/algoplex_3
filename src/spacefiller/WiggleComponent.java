package spacefiller;

import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;

public class WiggleComponent extends Component {
  private RShape shape;
  private int color;

  public WiggleComponent(PApplet parent) {
    super(parent);
    shape = RG.loadShape("vector/wiggle.svg");
    System.out.println(shape.getWidth());
    System.out.println(shape.getHeight());
    initCanvas((int) shape.getWidth(), (int) shape.getHeight(), 100);
  }

  @Override
  public void draw() {
    canvas.clear();
    color = parent.lerpColor( 0, 0xFFFFFFFF, energy);
    shape.setFill(color);
    shape.setStrokeWeight(0);
    shape.draw(canvas);
  }
}

