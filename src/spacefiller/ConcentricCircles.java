package spacefiller;

import de.looksgood.ani.Ani;
import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import spacefiller.mapping.RShapeTransformer;

public class ConcentricCircles extends Behavior {
  @Override
  public void draw() {

  }

  @Override
  public void setup() {

  }


//  public ConcentricCircles(PApplet parent, RShape shape) {
//    super(parent);
//    this.shape = shape;
//    loadOrCreateTransformer((int) shape.getWidth(), (int) shape.getHeight(), 10);
//
//    for (RShape child : shape.children) {
//      addChild(new RShapeTransformer(child));
//    }
//  }
//
//  @Override
//  public void draw() {
//    for (int i = 0; i < shape.children.length; i++) {
//      RShape c = shape.children[i];
//      c.setStroke(0xffffffff);
//      c.setFill(0);
//      c.setStrokeWeight(Math.abs(((float) i / shape.children.length) - energy) < 0.2 ? energy * 20 : 0);
//      c.draw(canvas);
//    }
//  }
//
//  @Override
//  public void drawCalibration() {
//    for (int i = 0; i < shape.children.length; i++) {
//      RShape c = shape.children[i];
//      c.setStroke(0xffffffff);
//      c.setFill(0);
//      c.setStrokeWeight(4);
//      c.draw(canvas);
//    }
//  }
}