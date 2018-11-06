package spacefiller;

import geomerative.RG;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import spacefiller.mapping.RShapeTransformer;

public class Waves extends Behavior {
  @Override
  public void draw() {

  }

//  private RShape top;
//  private RShape bottom;
//
//  public Waves(PApplet parent) {
//    super(parent);
//
//    RShape shape = RG.loadShape("vector/waves.svg");
//    top = shape.getChild("top");
//    bottom = shape.getChild("bottom");
//    initCanvas((int) shape.getWidth(), (int) shape.getHeight(), 10);
//
//    for (RShape child : shape.children) {
//      addChild(new RShapeTransformer(child));
//    }
//  }
//
//  @Override
//  public void draw() {
//    top.setFill(parent.color(energy * 255, 0, 0));
//    top.setStrokeWeight(0);
//    top.draw(canvas);
//
//    bottom.setFill(parent.color(0, 0, energy * 255));
//    bottom.setStrokeWeight(0);
//    bottom.draw(canvas);
//  }
//
//  @Override
//  public void drawCalibration() {
//    top.setFill(0xffcccccc);
//    top.setStrokeWeight(0);
//    top.draw(canvas);
//
//    bottom.setFill(0xffcccccc);
//    bottom.setStrokeWeight(0);
//    bottom.draw(canvas);
//  }
}
