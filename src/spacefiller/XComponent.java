package spacefiller;

import de.looksgood.ani.Ani;
import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import spacefiller.mapping.RShapeTransformer;

public class XComponent extends Behavior {
  @Override
  public void draw() {

  }
//  private RShape shape;
//  private RShape x;
//  private RShape border;
//  private float angle;
//
//  public XComponent(PApplet parent) {
//    super(parent);
//    shape = RG.loadShape("vector/x.svg");
//    loadOrCreateTransformer((int) shape.getWidth(), (int) shape.getHeight(), 10);
//
//    x = shape.getChild("x");
//    border = shape.getChild("border");
//
//    addChild(new RShapeTransformer(x));
//    addChild(new RShapeTransformer(border));
//
//  }
//
//  @Override
//  public void draw() {
////    border.setFill(parent.lerpColor(0, 0xFFFFFFFF, energy));
////    border.setStroke(0xffffffff);
////    border.setStrokeWeight(5);
////    border.draw(canvas);
////    x.rotate(0.1f);
////    RG.centerIn(shape, getCanvas());
//
//    canvas.translate(x.getCentroid().x, x.getCentroid().y);
//    canvas.rotate(angle);
//    canvas.translate(-x.getCentroid().x, -x.getCentroid().y);
//
//    x.setFill(parent.lerpColor( 0xFFFFFFFF,0, energy));
//    x.setStroke(0xffffffff);
//    x.setStrokeWeight(5);
//    x.draw(canvas);
//  }
//
//  @Override
//  public void trigger() {
//    Ani.to(this, 1.5f, "angle", (float) (angle + (Math.round(Math.random()) * 2 - 1) * Math.PI / 2f));
//    super.trigger();
//  }
}

