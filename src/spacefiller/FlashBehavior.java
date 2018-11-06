package spacefiller;

import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;

public class FlashBehavior extends Behavior {
  @Override
  public void draw() {
    canvas.fill(energy * 255);
    canvas.rect(0, 0, canvas.width, canvas.height);
  }
}
