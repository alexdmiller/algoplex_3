package spacefiller;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import de.looksgood.ani.Ani;
import geomerative.RG;
import processing.core.PApplet;
import processing.opengl.PJOGL;
import spacefiller.color.SmoothColorTheme;
import spacefiller.mapping.*;
import themidibus.MidiBus;
import toxi.color.ColorList;
import toxi.color.ColorRange;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Algoplex3 extends PApplet {


  public static SmoothColorTheme[] THEMES = new SmoothColorTheme[] {
      new SmoothColorTheme(
          new ColorList(new int[] {
              0xffff0000,
              0xffff0000,
              0xffff00ff,
              0xff0000ff
          }), 200),

      new SmoothColorTheme(
          new ColorList(new int[] {
              0xff00ffff,
              0xff00ff00,
              0xffff00ff,
              0xff0000ff
          }), 200),

      new SmoothColorTheme(
          ColorRange.BRIGHT, 10, 200),

      new SmoothColorTheme(
          ColorRange.INTENSE, 10, 200),

      new SmoothColorTheme(
          new ColorList(new int[] {
              0xffffffff,
              0xffffffff,
              0xffffffff,
              0x00000000
          }), 200),

  };

  public static SmoothColorTheme THEME_1 = THEMES[0];
  private boolean essentials = true;

  public static void main(String[] args) {
    main("spacefiller.Algoplex3");
  }

  private List<RShapeComponent> components;
  private boolean calibrate = false;
  private Mapper mapper;
  private Minim minim;
  private AudioInput in;
  private BeatDetect beat;
  private MidiBus bus;
  private float dimmer;
  private int maxActive = 2;
  private GridAnimation gridAnimation;

  @Override
  public void settings() {
    fullScreen(P2D, SPAN);
    size(1920, 1080, P3D);
    PJOGL.profile = 1;
  }

  @Override
  public void setup() {
    MidiBus.list();
    MidiBus.findMidiDevices();

    bus = new MidiBus(this, "Launch Control XL", "Launch Control XL");

    RG.init(this);
    Ani.init(this);
    // RG.ignoreStyles();

    minim = new Minim(this);
    in = minim.getLineIn();

    beat = new BeatDetect();
    beat.detectMode(BeatDetect.FREQ_ENERGY);
    beat.setSensitivity(300);

    components = new ArrayList<>();

//    for (int i = 0; i < 20; i++) {
//      ConcentricCircles c = new ConcentricCircles(this);
//      c.setPosition((i % 8) * 200, (i / 8) * 200);
//      components.add(c);
//    }
//
    /*

    .add(new Gradient())
    .add(new Marching())
    .add(new Scroll())
    .add(new ColorScroll())
    .add(new ConcentricBehavior())
    .add(new Particles())
    .add(new Tangents())
    .add(new Threads())
    .add(new Particles())
    .add(new ColorLayerScroll())
    .add(new LayerScroll())
     */


//    components.add(
//          RShapeComponent.createFromFile("grid", this)
//              .add(new Gradient())
//              .add(new Marching())
//              .add(new Scroll())
//              .add(new ColorScroll())
//              .add(new Particles())
//              .add(new Tangents())
////              .add(new Threads())
//              .add(new Particles())
//              .add(new ColorLayerScroll())
//              .add(new LayerScroll())
//     );
//
//    components.add(
//        RShapeComponent.createFromFile("topographic", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
////            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("concentric-circles", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
////            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("concentric-squares", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
////            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("concentric-circles", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
////            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("concentric-squares", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
////            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("u-left", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
//            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("u-right", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
//            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("triangle", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
//            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("sun-chip", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
////            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("sun-chip", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
////            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("circle", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents(-1))
//            .add(new Threads(-1))
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//
//    components.add(
//        RShapeComponent.createFromFile("donut", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
//            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("pills", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
//            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("squiggle", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents(-1))
//            .add(new Threads(-1))
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("wiggle", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
//            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("swoosh", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents(-1))
//            .add(new Threads(-1))
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("wave-top", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents(-1))
//            .add(new Threads(-1))
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("wave-bottom", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents(-1))
//            .add(new Threads(-1))
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("x", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
//            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );
//
//    components.add(
//        RShapeComponent.createFromFile("x", this)
//            .add(new Gradient())
//            .add(new Marching())
//            .add(new Scroll())
//            .add(new ColorScroll())
//            .add(new Particles())
//            .add(new Tangents())
//            .add(new Threads())
//            .add(new Particles())
//            .add(new ColorLayerScroll())
//            .add(new LayerScroll())
//    );

    RShapeComponent[] gridComponents = new RShapeComponent[] {
        RShapeComponent.createTriangleGrid(4, 1, 150, this),
        RShapeComponent.createTriangleGrid(4, 1, 150, this),
        RShapeComponent.createTriangleGrid(4, 1, 150, this),
        RShapeComponent.createTriangleGrid(4, 1, 150, this),
        RShapeComponent.createTriangleGrid(4, 1, 150, this),
        RShapeComponent.createTriangleGrid(4, 1, 150, this),
        RShapeComponent.createTriangleGrid(4, 1, 150, this),
        RShapeComponent.createTriangleGrid(4, 1, 150, this),
    };

    gridAnimation = new GridAnimation(gridComponents, in, beat);

    for (RShapeComponent component : gridComponents) {
      component.setEnabled(false);
      components.add(component);
    }

    mapper = new Mapper(this);

    for (RShapeComponent c : components) {
      mapper.addTransformable(c.getGraphTransformer());
    }


//    beat.setSensitivity(200);
  }

  @Override
  public void draw() {
    background(0);
    for (RShapeComponent component : components) {
      if (component.initialized()) {
        component.getCanvas().beginDraw();
        //component.getCanvas().clear();

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

    gridAnimation.draw();

    fill(0);

    if (beat.isKick()) {
      List<RShapeComponent> active = components.stream().filter(component -> component.isActive() && component.isEnabled()).collect(Collectors.toList());
      List<RShapeComponent> filtered = components.stream().filter(component -> !component.isActive() && component.isEnabled()).collect(Collectors.toList());
      if (!filtered.isEmpty() && active.size() < maxActive) {
        int random = (int) (Math.random() * filtered.size());
        filtered.get(random).triggerRandom();
      }
      fill(255);
    }

    if (essentials) {
      ellipse(100, 20, 20, 20);
      fill(255);
      text(frameRate, 10, 30);
    }

    fill(0, 0, 0, dimmer * 255);
    noStroke();
    rect(0, 0, width, height);

  }

  public void noteOn(int channel, int pitch, int velocity) {
    // 41
    if (pitch == 41) {
      THEME_1 = THEMES[0];
    } else if (pitch == 42) {
      THEME_1 = THEMES[1];
    } else if (pitch == 43) {
      THEME_1 = THEMES[2];
    } else if (pitch == 44) {
      THEME_1 = THEMES[3];
    } else if (pitch == 57) {
      THEME_1 = THEMES[4];
    }

    System.out.println(pitch);
  }

  public void controllerChange(int channel, int number, int value) {
    // 77
    if (number == 77) {
      beat.setSensitivity(1000 - (int) (value / 127f * 1000));
    } else if (number == 78) {
      maxActive = (int) (value / 127f * 10);
    } else if (number == 79) {
      dimmer = value / 127f;
    }
  }

  @Override
  public void keyPressed() {
    if (key == ' ') {
      calibrate = !calibrate;
      for (RShapeComponent component : components) {
        component.save();
        component.updateMask();
      }
    } else if (key == 'e') {
      essentials = !essentials;
      if (essentials) cursor();
      else noCursor();
    } else if (key == 'd') {
      List<RShapeComponent> selected = components.stream().filter(component -> component.getGraphTransformer() == mapper.getTransformTarget()).collect(Collectors.toList());
      if (!selected.isEmpty()) {
        selected.get(0).setEnabled(!selected.get(0).isEnabled());
      }
    }

  }

  @Override
  public void mouseReleased() {
    for (RShapeComponent component : components) {
      component.save();
      component.updateMask();
    }
  }
}
