package spacefiller;

import de.looksgood.ani.Ani;
import spacefiller.marching.MarchingSquares;

public class Marching extends Behavior {
  private MarchingSquares marchingSquares;
  private float noiseScale;

  @Override
  public void setup() {
    marchingSquares = new MarchingSquares(canvas.width, canvas.height);
    marchingSquares.setCellSize(10);
    marchingSquares.setFunction((x, y) -> parent.noise(
        (x - canvas.width/2) * noiseScale + canvas.width/2,
        (y - canvas.height/2) * noiseScale + canvas.height/2,
        parent.frameCount / 200f));
  }

  @Override
  public void draw() {
    if (energy > 0) {
      canvas.strokeWeight(5);
      canvas.stroke(255);
      canvas.noFill();
      marchingSquares.draw(canvas);
    }
  }

  @Override
  public void trigger() {
    marchingSquares.setCellSize((float) (Math.random() * 10 + 10));
    noiseScale = 0.1f;
    Ani.to(this, 2f, "noiseScale", 0.001f);
    super.trigger();
  }
}
