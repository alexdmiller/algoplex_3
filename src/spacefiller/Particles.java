package spacefiller;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniConstants;
import geomerative.RPoint;
import spacefiller.particles.Bounds;
import spacefiller.particles.Particle;
import spacefiller.particles.ParticleSystem;
import spacefiller.particles.behaviors.FlockParticles;
import spacefiller.particles.behaviors.ParticleFriction;
import spacefiller.particles.behaviors.ReflectiveBounds;

public class Particles extends Behavior {
  private float maxSpeed;
  private float particleSize;
  private ParticleSystem system;
  private FlockParticles flock;
  private float separate;

  @Override
  public void setup() {
    system = new ParticleSystem(new Bounds(canvas.width, canvas.height), 300, 15);

    while (system.getParticles().size() < 300) {
      Vector pos = new Vector((float) Math.random() * canvas.width, (float) Math.random() * canvas.height);
      if (shape.contains(new RPoint(pos.x, pos.y))); {
        system.createParticle(pos, 2);
      }
    }

    system.addBehavior(new ReflectiveBounds());

    flock = new FlockParticles(3, 1, 1, 12, 15, 15, 0.1f, 4);
    //system.addBehavior(new ParticleFriction(0.9f));
    system.addBehavior(new ShapeBounds(shape));

    system.addBehavior(flock);
  }

  @Override
  public void draw() {
    try {
      system.update();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    canvas.stroke(255);
    canvas.strokeWeight(particleSize);
    for (Particle p : system.getParticles()) {
      canvas.point(p.position.x, p.position.y);
    }

    flock.setMaxSpeed(maxSpeed);
    flock.setDesiredSeparation(separate);
  }

  @Override
  public void trigger() {
    maxSpeed = 10;
    particleSize = 20;
    separate = 15;

    Ani.to(this, 2f, "maxSpeed", 1f);
    Ani.to(this, 3f, "particleSize", 0f);
    Ani.to(this, 2f, "separate", (float) (Math.random() * 5), AniConstants.LINEAR);

    super.trigger();
  }

}
