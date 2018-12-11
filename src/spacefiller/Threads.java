package spacefiller;

import geomerative.RPoint;
import processing.core.PVector;

public class Threads extends Behavior {
  private float spacing = 0.01f;
  private int tangentDirection = 1;

  public Threads(int tangentDirection) {
    this.tangentDirection = tangentDirection;
  }

  public Threads() {

  }

  @Override
  public void setup() {

  }

  @Override
  public void draw() {
    if (isActive()) {
      canvas.stroke(255);
      canvas.strokeWeight(3);
      for (float t = 0; t < 1; t += spacing) {
        try {
          RPoint point = shape.getPoint(t);
          RPoint velocity = shape.getTangent(t);

          velocity.normalize();
          velocity.scale(50 * energy * tangentDirection);
          velocity.rotate((float) (-Math.PI / 2));

          canvas.pushMatrix();

          canvas.beginShape();
          canvas.stroke(255);
          canvas.noFill();
          canvas.vertex(point.x, point.y);
          for (int i = 0; i < 5; i++) {
            float theta = (float) (parent.noise(point.x / 100f, point.y / 100f, parent.frameCount / 50f) * 4 * Math.PI);
            RPoint field = new RPoint(Math.cos(theta), Math.sin(theta));
            field.scale(2 * linearEnergy);
            velocity.add(field);
            point.add(velocity);
            canvas.vertex(point.x, point.y);
          }
          canvas.endShape();
          canvas.popMatrix();
        } catch (ArrayIndexOutOfBoundsException e) {

        }
      }
    }
  }
}
