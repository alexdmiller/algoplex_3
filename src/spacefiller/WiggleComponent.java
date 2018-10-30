package spacefiller;

import geomerative.RG;
import geomerative.RPoint;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import spacefiller.mapping.RShapeTransformer;

import static processing.core.PConstants.ROUND;

public class WiggleComponent extends Component {
  private static final float AMPLITUDE = 100;
  private RShape shape;
  private RShape outer;
  private RShape inner;

  private int color;
  private float noiseX;
  private float noiseY;

  public WiggleComponent(PApplet parent) {
    super(parent);

    shape = RG.loadShape("vector/wiggle.svg");
    outer = shape.getChild("outer");
    inner = shape.getChild("inner");

    initCanvas(shape.getWidth(), shape.getHeight(), 10);

    addChild(new RShapeTransformer(outer));
  }

  @Override
  public void draw() {
    noiseX += energy / 30f;
    noiseY += energy / 30f;

    canvas.clear();
    color = parent.lerpColor( 0, 0xFFFFFFFF, energy);

    outer.setFill(color);
    outer.setStrokeWeight(0);
    outer.draw(canvas);

    canvas.beginShape();
    canvas.stroke(0);
    canvas.strokeWeight(10);
    canvas.strokeCap(ROUND);
    canvas.noFill();

    for (int i = 0; i < 10; i++) {
      RPoint p= inner.getPoint(i/10f);
      canvas.curveVertex(p.x + (parent.noise(p.x / 200f, noiseX) * AMPLITUDE - AMPLITUDE/2f), p.y + (parent.noise(p.y / 200f, noiseY) * AMPLITUDE - AMPLITUDE/2f));
    }

    canvas.endShape();


//    canvas.strokeWeight(4);
//    canvas.stroke(255);
//    canvas.noFill();
//
//    canvas.beginShape();
//    for (float t = 0; t < energy; t += 0.01) {
//      RPoint point = shape.getPoint(t);
//      canvas.vertex(point.x, point.y);
//    }
//    canvas.endShape();
  }

  @Override
  public void drawCalibration() {
    outer.setFill(0);
    outer.setStroke(0xffffffff);
    outer.setStrokeWeight(5);
    outer.draw(canvas);
  }
}

