package spacefiller;

import ddf.minim.AudioInput;
import ddf.minim.analysis.BeatDetect;
import de.looksgood.ani.Ani;
import geomerative.RShape;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;
import spacefiller.graph.Node;
import spacefiller.graph.renderer.CrosshairGraphRenderer;
import spacefiller.graph.renderer.DottedLineGraphRenderer;
import spacefiller.graph.renderer.GraphRenderer;
import spacefiller.mapping.Quad;

import java.util.*;

public class GridAnimation {
  private List<RShapeComponent> components;
  private AudioInput in;
  private BeatDetect beat;
  private Map<RShapeComponent, List<QuadAnimation>> componentToQuad;
  private List<QuadAnimation> quadAnimations;

  public GridAnimation(RShapeComponent[] components, AudioInput in, BeatDetect beat) {
    this.in = in;
    this.beat = beat;
    this.components = Arrays.asList(components);
    this.componentToQuad = new HashMap<>();
    this.quadAnimations = new ArrayList<>();

    for (RShapeComponent component : components) {
      List<QuadAnimation> componentAnimations = new ArrayList<>();
      for (Quad quad : component.getGraphTransformer().getPreTransformGrid().getSquares()) {
        QuadAnimation anim = new QuadAnimation(quad, component.getCanvas());
        componentAnimations.add(anim);
        quadAnimations.add(anim);
      }
      componentToQuad.put(component, componentAnimations);
    }
  }

  public List<RShapeComponent> getComponents() {
    return components;
  }

  public void draw() {
    if (beat.isKick()) {
      float delaySpread = (float) Math.random() * 0.01f;
      float duration = (float) (Math.random() * 0.1 + 0.9f);

      int choice = (int) (Math.random() * 15);
      switch (choice) {
        case 0: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "xRadius", 1f);
          }
          break;
        }
        case 1: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "xRadius", 0.5f);
          }
          break;
        }
        case 2: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "xRadius", 0f);
          }
          break;
        }
        case 3: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "xRotation", (float) (Math.floor(Math.random() * 8 - 4) * Math.PI / 4f));
          }
          break;
        }
        case 4: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "xRotation", 0);
          }
          break;
        }
        case 5: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "lRadius", (float) Math.round(Math.random()));
          }
          break;
        }
        case 6: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "lRadius", 1);
          }
          break;
        }
        case 7: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "rectRadius", 1);
          }
          break;
        }
        case 8: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "rectRadius", 0);
          }
          break;
        }
        case 9: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "colorFade", 1);
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "colorRotate", 1);
          }
          break;
        }
        case 10: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "colorRotate", 0);
          }
          break;
        }
        case 11: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "colorFade", 0);
          }
          break;
        }
        case 12: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "rectRadius", 0.5f);
          }
          break;
        }
        case 13: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "colorFade", 1);
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "colorRotate", 1);
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "speed", 1);
          }
          break;
        }
        case 14: {
          for (int i = 0; i < quadAnimations.size(); i++) {
            Ani.to(quadAnimations.get(i), duration, i * delaySpread, "speed", 0);
          }
          break;
        }
      }
    }

    for (RShapeComponent component : components) {
      component.getCanvas().beginDraw();
      component.getCanvas().background(0);

      for (QuadAnimation quadAnimation : componentToQuad.get(component)) {
        quadAnimation.draw();
      }

      component.getCanvas().endDraw();
    }

//    for (RShapeComponent component : components) {
//      PGraphics graphics = component.getCanvas();
//
//      graphics.beginDraw();
//      graphics.background(0);
//      graphics.stroke(255);
//      graphics.strokeWeight(2);
//
//      int index = 0;
//      for (Quad quad : component.getGraphTransformer().getPreTransformGrid().getSquares()) {
//        index++;
//      }
//
//      graphics.endDraw();
//    }
  }

  private class QuadAnimation {
    private Quad quad;
    private PGraphics graphics;
    public float xRadius;
    public float lRadius;
    public float xRotation;
    public float rectRadius;
    public float colorRotate;
    public float colorFade;
    public float speed;
    private float t;

    public QuadAnimation(Quad quad, PGraphics graphics) {
      this.quad = quad;
      this.graphics = graphics;
    }

    public void draw() {
      t += speed / 100f;
      graphics.noStroke();

      Node[][] triangles = quad.getTriangles();
      for (int i = 0; i < triangles.length; i++) {
        graphics.beginShape();
        graphics.fill(graphics.color(74, 123, 125,
            (float) ((Math.sin(i / 4f + colorRotate * Math.PI * 2 + t) + 1) / 2 * 255 * colorFade)));
        Node[] tri = triangles[i];
        graphics.vertex(tri[0].position.x, tri[0].position.y);
        graphics.vertex(tri[1].position.x, tri[1].position.y);
        graphics.vertex(tri[2].position.x, tri[2].position.y);
        graphics.endShape();
      }

      graphics.stroke(255);
      graphics.strokeWeight(3);

      Node center = quad.getCenter();

      graphics.pushMatrix();
      graphics.translate(center.position.x, center.position.y);

      graphics.pushMatrix();
      graphics.rotate(xRotation);
      float radius = xRadius * quad.getWidth() / 3f;
      graphics.line(-radius * lRadius, -radius * lRadius, radius * lRadius, radius * lRadius);
      graphics.line(radius, -radius, -radius, radius);
      graphics.popMatrix();

      graphics.rectMode(PConstants.CENTER);
      graphics.noFill();
      graphics.stroke(255);
      graphics.strokeWeight(3);
      graphics.rect(0, 0, rectRadius * quad.getWidth(), rectRadius * quad.getWidth());

      graphics.popMatrix();

    }
  }
}
