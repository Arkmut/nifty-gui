package de.lessvoid.nifty.internal.layout;

import org.junit.Before;
import org.junit.Test;

import de.lessvoid.nifty.api.UnitValue;
import de.lessvoid.nifty.api.VAlign;
import de.lessvoid.nifty.internal.common.Box;
import de.lessvoid.nifty.internal.layout.InternalBoxConstraints;
import de.lessvoid.nifty.internal.layout.InternalLayoutCenter;

public class InternalLayoutCenterVerticallPercentWithBorderTest {
  private InternalLayoutCenter layout;
  private Box rootBox;
  private Box box;
  private InternalBoxConstraints constraint;
  private InternalBoxConstraints rootBoxConstraints;

  @Before
  public void setUp() throws Exception {
    layout = new InternalLayoutCenter();
    rootBox = new Box(0, 0, 640, 480);
    rootBoxConstraints = new InternalBoxConstraints();
    rootBoxConstraints.setPadding(new UnitValue("50px"));
    box = new Box();
    constraint = new InternalBoxConstraints();
    constraint.setHeight(new UnitValue("50%"));
  }

  @Test
  public void testVerticalAlignTopPercentWidth() {
    constraint.setVerticalAlign(VAlign.top);
    layout.handleVerticalAlignment(rootBox, rootBoxConstraints, box, constraint);
    int height = (480 - 100) / 2;
    Assert.assertBoxTopHeight(box, 50, height);
  }

  @Test
  public void testVerticalAlignBottomPercentWidth() {
    constraint.setVerticalAlign(VAlign.bottom);
    layout.handleVerticalAlignment(rootBox, rootBoxConstraints, box, constraint);
    int height = (480 - 100) / 2;
    Assert.assertBoxTopHeight(box, 480 - height - 50 + 50, height);
  }

  @Test
  public void testVerticalAlignCenterPercentWidth() {
    constraint.setVerticalAlign(VAlign.center);
    layout.handleVerticalAlignment(rootBox, rootBoxConstraints, box, constraint);
    Assert.assertBoxTopHeight(box, 50 + ((480 - 100) / 2) / 2, (480 - 100) / 2);
  }
}
