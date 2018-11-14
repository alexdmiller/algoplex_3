package spacefiller;

import geomerative.RG;
import geomerative.RShape;

import java.security.interfaces.RSAKey;

public class LayerScroll extends Behavior {
  @Override
  public void setup() {
    RG.setPolygonizerLength(10f);
    shape.polygonize();
  }

  @Override
  public void draw() {
    for (int i = 0; i < shape.children.length; i++) {
      RShape child = shape.children[i];
      child.setFill(parent.color((float) (Math.sin(i + parent.frameCount / 10f) + 1) / 2 * 255 * energy));
      child.setStrokeWeight(0); //(float) (Math.sin(i / 100f + parent.frameCount /10) + 1) / 2f * 20 + 1);
      child.draw(canvas);
    }
  }
}
