package spacefiller;

import geomerative.RPoint;
import processing.core.PVector;

public class Threads extends Behavior {
  private float spacing = 0.02f;

  @Override
  public void setup() {

  }

  @Override
  public void draw() {
    if (isActive()) {
      canvas.stroke(255);
      canvas.strokeWeight(3);
      for (float t = 0; t < 1; t += spacing) {
        RPoint point = shape.getPoint(t);
        RPoint velocity = shape.getTangent(t);

        velocity.normalize();
        velocity.scale(10 * energy);
        velocity.rotate((float) (Math.PI / 2));

        canvas.pushMatrix();

        canvas.beginShape();
        canvas.stroke(255);
        canvas.noFill();
        canvas.vertex(point.x, point.y);
        for (int i = 0; i < 5; i++) {
          float theta = (float) (parent.noise(point.x / 100f, point.y / 100f, parent.frameCount / 200f) * 4 * Math.PI);
          RPoint field = new RPoint(Math.cos(theta), Math.sin(theta));
          field.scale(0.5f * linearEnergy);
          velocity.add(field);
          point.add(velocity);
          canvas.vertex(point.x, point.y);
        }
        canvas.endShape();
        canvas.popMatrix();

      }
    }
  }
}
