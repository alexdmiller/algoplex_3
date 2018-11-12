package spacefiller;

import geomerative.RPoint;
import org.omg.CORBA.TCKind;
import toxi.color.TColor;

import static spacefiller.Algoplex3.THEME_1;

public class Gradient extends Behavior {
  private float spread;
  private float start;
  private float speed;

  @Override
  public void draw() {
    TColor color1 = new TColor(THEME_1.getColor(energy * speed + start));
    TColor color2 = new TColor(THEME_1.getColor(energy * speed + spread/2f + start));
    TColor color3 = new TColor(THEME_1.getColor(energy * speed + spread + start));
    TColor color4 = new TColor(THEME_1.getColor(energy * speed + spread*3/2f + start));

    color1.setAlpha(linearEnergy);
    color2.setAlpha(linearEnergy);
    color3.setAlpha(linearEnergy);
    color4.setAlpha(linearEnergy);

    canvas.beginShape();
    canvas.fill(color1.toARGB());
    canvas.vertex(0, 0);
    canvas.fill(color2.toARGB());
    canvas.vertex(canvas.width, 0);
    canvas.fill(color3.toARGB());
    canvas.vertex(canvas.width, canvas.height);
    canvas.fill(color4.toARGB());
    canvas.vertex(0, canvas.height);
    canvas.endShape();
  }

  @Override
  public void setup() {

  }

  @Override
  public void trigger() {
    super.trigger();

    spread = (float) (Math.random() * Math.PI);
    start =  (float) (Math.random() * Math.PI * 2 - Math.PI);
    speed =  (float) (Math.random() * Math.PI * 2 - Math.PI);
  }
}
