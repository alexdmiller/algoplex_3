package spacefiller;

public class Scroll extends Behavior {
  private float spacing = 10;
  private float speed = 5;
  private boolean vertical = true;

  @Override
  public void draw() {
    if (vertical) {
      for (float x = 0; x < canvas.width; x += spacing) {
        canvas.stroke(255);
        canvas.strokeWeight((float) (Math.sin(x / 50f + energy * speed) + 1) / 2f * 20 * energy);
        canvas.line(x, 0, x, canvas.height);
      }
    } else {
      for (float y = 0; y < canvas.height; y += spacing) {
        canvas.stroke(255);
        canvas.strokeWeight((float) (Math.sin(y / 50f + energy * speed) + 1) / 2f * 20 * energy);
        canvas.line(0, y, canvas.width, y);
      }
    }
  }

  @Override
  public void setup() {

  }

  @Override
  public void trigger() {
    super.trigger();

    spacing = (float) (Math.random() * 40 + 5);
    speed = (float) (Math.random() * 20 - 10);
    vertical = Math.random() > 0.5f;
  }
}
