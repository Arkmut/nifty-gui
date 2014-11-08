package de.lessvoid.nifty.api.event;

import de.lessvoid.nifty.api.NiftyNode;

public class NiftyPointerReleasedEvent implements NiftyEvent {
  private final NiftyNode niftyNode;
  private final int button;
  private final int x;
  private final int y;

  public NiftyPointerReleasedEvent(final NiftyNode niftyNode, final int button, final int x, final int y) {
    this.niftyNode = niftyNode;
    this.button = button;
    this.x = x;
    this.y = y;
  }

  public int getMouseButton() {
    return button;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public String toString() {
    return "pointer released [" + niftyNode.toString() + "]: " + button + " (" + x + ", " + y + ")";
  }
}