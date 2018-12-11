package spacefiller;

import java.util.ArrayList;
import java.util.List;

public class TriggerGroup implements Triggerable {
  private List<Triggerable> triggerables;

  public TriggerGroup() {
    this.triggerables = new ArrayList<>();
  }

  @Override
  public void trigger() {
    if (!triggerables.isEmpty()) {
      int index = (int) Math.floor(Math.random() * triggerables.size());
      triggerables.get(index).trigger();
    }
  }

  @Override
  public boolean isActive() {
    for (Triggerable t : triggerables) {
      if (t.isActive()) {
        return true;
      }
    }
    return false;
  }
}
