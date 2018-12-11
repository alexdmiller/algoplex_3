package spacefiller;

import geomerative.RG;
import geomerative.RPoint;
import geomerative.RShape;
import spacefiller.particles.Particle;
import spacefiller.particles.behaviors.ParticleBehavior;

import java.util.List;

public class ShapeBounds extends ParticleBehavior {
  private RShape shape;

  public ShapeBounds(RShape shape) {
    RG.setPolygonizerLength(50);
    this.shape = new RShape(shape);
    this.shape.polygonize();
    RG.setPolygonizerLength(1);
  }

  @Override
  public void apply(Particle particle, List<Particle> list) {
    RPoint p = new RPoint(particle.position.x, particle.position.y);
    RPoint closest = null;
    float closestDistance = 0;

    for (RPoint point : shape.getPoints()) {
      float distance = point.dist(p);
      if (closest == null || distance < closestDistance) {
        closest = point;
        closestDistance = distance;
      }
    }

    if (!shape.contains(p)) {
      particle.position = new Vector(closest.x, closest.y);
      closest.sub(p);
      closest.scale(0.01f);
      particle.applyForce(new Vector(closest.x, closest.y));
    } else if (closestDistance < 10) {
      closest.sub(p);
      closest.scale(0.1f);
      particle.applyForce(new Vector(-closest.x, -closest.y));
    }
  }
}
