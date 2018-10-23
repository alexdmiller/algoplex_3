package spacefiller;

import processing.core.PApplet;
import processing.core.PGraphics;
import spacefiller.graph.GridUtils;
import spacefiller.graph.renderer.BasicGraphRenderer;
import spacefiller.mapping.GraphTransformer;
import spacefiller.mapping.Grid;

public class Component {
  private GraphTransformer graphTransformer;
  private BasicGraphRenderer graphRenderer;
  private PGraphics canvas;

  public Component(PApplet parent) {
    graphTransformer = GridUtils.createGraphTransformer(5, 5, 100);
    graphRenderer = new BasicGraphRenderer(2);
    graphRenderer.setColor(parent.color(255));
    canvas = parent.createGraphics(
        (int) graphTransformer.getPreTransformGrid().getWidth(),
        (int) graphTransformer.getPreTransformGrid().getHeight(),
        parent.P3D);
  }

  public void draw() {
    canvas.beginDraw();
    graphRenderer.render(canvas, graphTransformer.getPreTransformGrid());
    canvas.endDraw();
  }

  public GraphTransformer getGraphTransformer() {
    return graphTransformer;
  }

  public PGraphics getCanvas() {
    return canvas;
  }
}
