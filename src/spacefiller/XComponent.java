package spacefiller;

import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;

public class XComponent extends Component {
  RShape shape;

  public XComponent(PApplet parent) {
    super(parent);
    shape = RG.loadShape("vector/x.svg");
    initCanvas((int) shape.getWidth(), (int) shape.getHeight(), 100);
  }

  @Override
  public void draw() {
//    RG.centerIn(shape, getCanvas());
    shape.setFill(parent.lerpColor( 0, 0xFFFFFFFF, energy));
    shape.setStroke(0xffffffff);
    shape.setStrokeWeight(5);
    shape.draw(canvas);
  }
}

