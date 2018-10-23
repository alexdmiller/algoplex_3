package spacefiller;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PJOGL;
import spacefiller.graph.GridUtils;
import spacefiller.graph.Node;
import spacefiller.graph.renderer.BasicGraphRenderer;
import spacefiller.mapping.GraphTransformer;
import spacefiller.mapping.Grid;
import spacefiller.mapping.Quad;

import java.util.ArrayList;
import java.util.List;

public class Algoplex3 extends PApplet {
  public static void main(String[] args) {
    main("spacefiller.Algoplex3");
  }

  private List<Component> components;

  @Override
  public void settings() {
    fullScreen();
    size(1920, 1080, P3D);
    PJOGL.profile = 1;
  }

  @Override
  public void setup() {
    components = new ArrayList<>();
    components.add(new Component(this));
    components.add(new Component(this));

    components.get(1).getGraphTransformer().translate(500, 0);
  }

  @Override
  public void draw() {
    background(0);

    for (Component component : components) {
      component.getGraphTransformer().drawImage(getGraphics(), component.getCanvas());
      component.getGraphTransformer().drawUI(getGraphics());
    }
  }

  @Override
  public void mousePressed() {
    for (Component component : components) {
      component.getGraphTransformer().mouseDown(mouseX, mouseY);
    }
  }

  @Override
  public void mouseDragged() {
    for (Component component : components) {
      component.getGraphTransformer().mouseDragged(mouseX, mouseY);
    }
  }

  @Override
  public void mouseReleased() {
    for (Component component : components) {
      component.getGraphTransformer().mouseUp(mouseX, mouseY);
    }
  }
}
