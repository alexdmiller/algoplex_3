package spacefiller;

import static spacefiller.Algoplex3.THEME_1;

public class ColorScroll extends Behavior {
  private float spacing = 10;
  private float speed = 5;

  @Override
  public void draw() {
    for (float y = 0; y < canvas.height; y += spacing) {
      canvas.stroke(THEME_1.getColor(y / 50f + energy).toARGB());
      canvas.strokeWeight((float) (Math.sin(y / 50f + energy * speed) + 1) / 2f * 20 * energy);
      canvas.line(0, y, canvas.width, y);
    }
  }

  @Override
  public void trigger() {
    super.trigger();

    spacing = (float) (Math.random() * 20 + 5);
    speed = (float) (Math.random() * 20 - 10);
  }
}
