package spacefiller;

import geomerative.RPoint;
import toxi.color.TColor;

import static spacefiller.Algoplex3.THEME_1;

public class Tangents extends Behavior {
  private float time = 0;
  private float offset = 0;
  private float spacing = 0;

  @Override
  public void draw() {
    if (isActive()) {
      time += 0.001f;

      canvas.strokeWeight(10);
      for (float t = 0; t < 1; t += spacing) {
        canvas.stroke(THEME_1.getColor((float) (t * Math.PI * 2f + offset + time)).toARGB());
        RPoint point = shape.getPoint((t + time + energy / 10f) % 1);
        RPoint tangent = shape.getTangent((t + time + energy / 10f) % 1);
        tangent.normalize();
        tangent.scale(energy * 50);

        canvas.pushMatrix();
        canvas.translate(point.x, point.y);
        canvas.rotate((float) (-Math.PI / 2));
        canvas.line(0, 0, tangent.x, tangent.y);
        canvas.popMatrix();
      }
    }
  }

  @Override
  public void setup() {

  }

  @Override
  public void trigger() {
    spacing = (float) (Math.random() * 0.03 + 0.01);
    offset = (float) Math.random();

    super.trigger();
  }
}
