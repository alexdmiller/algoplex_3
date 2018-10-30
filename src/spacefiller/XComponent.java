package spacefiller;

import de.looksgood.ani.Ani;
import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import spacefiller.mapping.RShapeTransformer;

public class XComponent extends Component {
  RShape shape;
  RShape x;
  RShape outer;

  float angle;

  public XComponent(PApplet parent) {
    super(parent);
    shape = RG.loadShape("vector/x.svg");
    initCanvas((int) shape.getWidth(), (int) shape.getHeight(), 10);

    x = shape.getChild("x");
    addChild(new RShapeTransformer(x));

  }

  @Override
  public void draw() {
    canvas.clear();

    canvas.translate(canvas.width / 2f, canvas.height / 2f);
    canvas.rotate(angle);
    canvas.translate(-canvas.width / 2f, -canvas.height / 2f);

//    x.rotate(0.1f);
//    RG.centerIn(shape, getCanvas());
    x.setFill(parent.lerpColor( 0, 0xFFFFFFFF, energy));
    x.setStroke(0xffffffff);
    x.setStrokeWeight(5);
    x.draw(canvas);
  }

  @Override
  public void trigger() {
    Ani.to(this, 1.5f, "angle", (float) (angle + (Math.round(Math.random()) * 2 - 1) * Math.PI / 2f));
    super.trigger();
  }
}

