package spacefiller;

import geomerative.RPoint;
import geomerative.RShape;

public class ConcentricBehavior extends Behavior {

  @Override
  public void draw() {
    if (energy > 0) {
      RShape copy = new RShape(shape);
      for (int i = 0; i < 10; i++) {
        copy.setFill(0);
        float stroke = (float) ((Math.sin((i + energy * 20)) + 1) / 2f) * 10 * energy;
        copy.setStrokeWeight(stroke < 1 ? 0 : stroke);
        // copy.setStroke(parent.color(0, (float) ((Math.sin(i / 1f + energy * 10) + 1) / 2f * 255), 255));
        copy.setStroke(parent.color(255, 255, 255));
        copy.draw(canvas);

        copy.scale(0.9f, copy.getCenter());
      }
    }
  }

  @Override
  public void setup() {

  }
}
