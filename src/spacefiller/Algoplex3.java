package spacefiller;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import de.looksgood.ani.Ani;
import geomerative.RG;
import processing.core.PApplet;
import processing.opengl.PJOGL;
import spacefiller.mapping.*;

import java.util.ArrayList;
import java.util.List;

public class Algoplex3 extends PApplet {
  public static void main(String[] args) {
    main("spacefiller.Algoplex3");
  }

  private List<Component> components;
  private boolean calibrate;
  private Mapper mapper;
  private Minim minim;
  private AudioInput in;
  private BeatDetect beat;


  @Override
  public void settings() {
    fullScreen();
    size(1920, 1080, P3D);
    PJOGL.profile = 1;
  }

  @Override
  public void setup() {
    RG.init(this);
    Ani.init(this);

    components = new ArrayList<>();
    components.add(new WiggleComponent(this));
    components.add(new XComponent(this));

    components.get(1).setPosition(550, 0);

    mapper = new Mapper(this);

    for (Component c : components) {
      mapper.addTransformable(c.getGraphTransformer());
    }

    minim = new Minim(this);
    in = minim.getLineIn();
    beat = new BeatDetect();
    beat.setSensitivity(200);
  }

  @Override
  public void draw() {
    background(0);
    for (Component component : components) {
      if (component.initialized()) {
        component.getCanvas().beginDraw();
        component.draw();

        component.getGraphTransformer().drawImage(getGraphics(), component.getCanvas());

        if (calibrate) {
          component.getGraphTransformer().drawUI(getGraphics());
        }

        component.getCanvas().endDraw();
      }
    }

    stroke(255);

    beat.detect(in.mix);
    fill(0);
    if (beat.isOnset()) {
      for (Component component : components) {
        component.trigger();
      }
    }
  }

//  @Override
//  public void mousePressed() {
//    for (Component component : components) {
//      if (component.initialized()) {
//        component.getGraphTransformer().mouseDown(mouseX, mouseY);
//      }
//    }
//  }
//
//  @Override
//  public void mouseDragged() {
//    for (Component component : components) {
//      if (component.initialized()) {
//        component.getGraphTransformer().mouseDragged(mouseX, mouseY);
//      }
//    }
//  }
//
//  @Override
//  public void mouseReleased() {
//    for (Component component : components) {
//      if (component.initialized()) {
//        component.getGraphTransformer().mouseUp(mouseX, mouseY);
//      }
//    }
//  }

  @Override
  public void keyPressed() {
    if (key == ' ') {
      calibrate = !calibrate;
    }
  }
}
