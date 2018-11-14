package spacefiller;

import geomerative.RG;
import geomerative.RShape;

import static spacefiller.Algoplex3.THEME_1;

public class ColorLayerScroll extends Behavior {
  @Override
  public void setup() {
  }

  @Override
  public void draw() {
    for (int i = 0; i < shape.children.length; i++) {
      RShape child = shape.children[i];
      child.setStroke(THEME_1.getColor((float) i / shape.children.length + parent.frameCount / 10f).toARGB());
      child.setFill(0);
      child.setStrokeWeight((float) (Math.sin(i / 5f + parent.frameCount / 5f) + 1) / 2f * 20 * linearEnergy);
      child.draw(canvas);
    }
  }

}
