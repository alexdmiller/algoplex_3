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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Algoplex3 extends PApplet {
  private static final int ACTIVATION_MIN = 1;
  private static final int ACTIVATION_MAX = 1;

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

//    for (int i = 0; i < 20; i++) {
//      ConcentricCircles c = new ConcentricCircles(this);
//      c.setPosition((i % 8) * 200, (i / 8) * 200);
//      components.add(c);
//    }

//    components.add(new WiggleComponent(this));
//    components.add(new XComponent(this));
//    components.add(new ConcentricCircles(this, RG.loadShape("vector/concentric-circles.svg")));
//    components.add(new ConcentricCircles(this, RG.loadShape("vector/concentric-squares.svg")));
//    components.add(new Waves(this));

    components.add(
        new Component(RG.loadShape("vector/pills.svg"), this)
            .add(new FlashBehavior())
            .add(new ConcentricBehavior()));

//
//
//    components.get(1).setPosition(550, 0);
//    components.get(2).setPosition(0, 550);

    mapper = new Mapper(this);

    for (Component c : components) {
      mapper.addTransformable(c.getGraphTransformer());
    }

    minim = new Minim(this);
    in = minim.getLineIn();
    beat = new BeatDetect();
    beat.detectMode(BeatDetect.FREQ_ENERGY);

//    beat.setSensitivity(200);
  }

  @Override
  public void draw() {
    background(0);
    for (Component component : components) {
      if (component.initialized()) {
        component.getCanvas().beginDraw();
        component.getCanvas().clear();

        if (calibrate) {
          component.drawCalibration();
          component.getGraphTransformer().renderUI(getGraphics());
        } else {
          component.draw();
        }

        component.getGraphTransformer().drawImage(getGraphics());
        component.getCanvas().endDraw();
      }
    }

    stroke(255);

    beat.detect(in.mix);
    fill(0);
    if (beat.isKick()) {
      components.toArray();

      List<Component> s = components.stream().filter(component -> !component.isActive()).collect(Collectors.toList());
      Collections.shuffle(s);
      int numToActivate = (int) Math.round(Math.random() * (ACTIVATION_MAX - ACTIVATION_MIN) + ACTIVATION_MIN);
      // TODO: figure out how to trigget behaviors
      for (int i = 0; i < numToActivate && i < s.size(); i++) {
        s.get(i).triggerRandom();
      }

      fill(255);
    }
    ellipse(100, 20, 20, 20);

    fill(255);
    text(frameRate, 10, 30);
  }

  @Override
  public void keyPressed() {
    if (key == ' ') {
      calibrate = !calibrate;
    }
  }
}
