package spacefiller;

import de.looksgood.ani.Ani;
import geomerative.RG;
import geomerative.RPoint;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import spacefiller.mapping.RShapeTransformer;
import spacefiller.particles.Bounds;
import spacefiller.particles.Particle;
import spacefiller.particles.ParticleSystem;
import spacefiller.particles.behaviors.FlockParticles;
import spacefiller.particles.behaviors.ParticleFriction;
import spacefiller.particles.behaviors.ReflectiveBounds;

import static processing.core.PConstants.ROUND;

public class WiggleComponent extends Behavior {
  @Override
  public void draw() {

  }
//  private RShape shape;
//  private RShape outer;
//  private float maxSpeed;
//  private float particleSize;
//  private ParticleSystem system;
//  private FlockParticles flock;
//
//  public WiggleComponent(PApplet parent) {
//    super(parent);
//
//    shape = RG.loadShape("vector/wiggle.svg");
//    outer = shape.getChild("outer");
//
//    initCanvas(shape.getWidth(), shape.getHeight(), 10);
//
//    addChild(new RShapeTransformer(outer));
//
//    system = new ParticleSystem(new Bounds(canvas.width, canvas.height), 500, 30, 1);
//
//    while (system.getParticles().size() < 300) {
//      Vector pos = new Vector((float) Math.random() * canvas.width, (float) Math.random() * canvas.height);
//      if (outer.contains(new RPoint(pos.x, pos.y))); {
//        system.createParticle(pos, 2);
//      }
//    }
//
//    system.addBehavior(new ReflectiveBounds());
//
//    flock = new FlockParticles(3, 1, 1, 20, 30, 30, 0.1f, 4);
//    system.addBehavior(flock);
//    //system.addBehavior(new ParticleFriction(0.f));
//    system.addBehavior(new ShapeBounds(outer));
//
//    RG.setPolygonizerLength(30);
//    outer.polygonize();
//  }
//
//  @Override
//  public void draw() {
//    outer.setStroke(parent.color(energy * 255));
//    outer.setFill(0);
//    outer.setStrokeWeight(5);
//    outer.draw(canvas);
//
//    try {
//      system.update();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//
//    canvas.stroke(255);
//    canvas.strokeWeight(particleSize);
//    for (Particle p : system.getParticles()) {
//      canvas.point(p.position.x, p.position.y);
//    }
//
//    flock.setMaxSpeed(maxSpeed);
//  }
//
//  @Override
//  public void drawCalibration() {
//    outer.setFill(0);
//    outer.setStroke(0xffffffff);
//    outer.setStrokeWeight(5);
//    outer.draw(canvas);
//  }
//
//  @Override
//  public void trigger() {
//    maxSpeed = 10;
//    particleSize = 20;
//
//    Ani.to(this, 4f, "maxSpeed", 1f);
//    Ani.to(this, 2f, "particleSize", 0f);
//    super.trigger();
//  }
}

