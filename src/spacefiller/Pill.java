package spacefiller;

import de.looksgood.ani.Ani;
import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import spacefiller.mapping.RShapeTransformer;

public class Pill extends Behavior {
  @Override
  public void draw() {

  }
//  private RShape inner;
//  private RShape shape;
//  private RShape outer;
//  private RShape mask;
//
//  public Pill(PApplet parent) {
//    super(parent);
//    shape = RG.loadShape("vector/pills.svg");
//    initCanvas((int) shape.getWidth(), (int) shape.getHeight(), 10);
//
//    inner = shape.getChild("inner");
//    outer = shape.getChild("outer");
//
//    mask = outer.diff(inner);
//
//    addChild(new RShapeTransformer(outer));
//    addChild(new RShapeTransformer(inner));
//  }
//
//  @Override
//  public void draw() {
////    outer.setFill(parent.color(0, 0, energy * 255));
////    outer.setStrokeWeight(0);
////    outer.draw(canvas);
////
////    inner.setFill(parent.color(energy * 255, 0, 0));
////    inner.setStrokeWeight(0);
////    inner.draw(canvas);
//
////    canvas.fill(255, 0, 255);
////    canvas.rect(0, 0, canvas.width, canvas.height);
////
//
//    RShape copy = new RShape(inner);
//    for (int i = 0; i < 10; i++) {
//      copy.setFill(0);
//      copy.setStrokeWeight(5);
//      copy.setStroke(parent.color(0, (float) ((Math.sin(i / 1f + energy*10) + 1) / 2f * 255), 255, energy * 255));
//      copy.draw(canvas);
//      copy.scale(0.9f, inner.getCentroid());
//    }
////
////    for (float y = 0; y < canvas.height; y += 10) {
////      canvas.stroke(255);
////      canvas.strokeWeight((float) ((Math.sin(y / 20f + parent.frameCount / 20f) + 1) / 2 * energy * 20));
////      canvas.line(0, y, canvas.width, y);
////    }
//
//    mask.setFill(0xff000000);
//    mask.setStroke(0xff000000);
//    mask.setStrokeWeight(5);
//    mask.draw(canvas);
//  }
//
//  @Override
//  public void drawCalibration() {
//    outer.setFill(0xffcccccc);
//    outer.setStrokeWeight(0);
//    outer.draw(canvas);
//
//    inner.setFill(0xff00cccc);
//    inner.setStrokeWeight(0);
//    inner.draw(canvas);
//  }
}
