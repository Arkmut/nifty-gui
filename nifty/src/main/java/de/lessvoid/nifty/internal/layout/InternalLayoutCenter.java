package de.lessvoid.nifty.internal.layout;

import java.util.List;

import de.lessvoid.nifty.api.HAlign;
import de.lessvoid.nifty.api.VAlign;
import de.lessvoid.nifty.internal.common.Box;

/**
 * CenterLayout centers all child elements. If there are more than one child elements all elements will be centered
 * (and over layed above each other). Remember that center probably makes only sense if the centered element has some
 * width and height constraints set.
 *
 * @author void
 */
public class InternalLayoutCenter implements InternalLayout {

  @Override
  public void layoutElements(final InternalLayoutable rootElement, final List <? extends InternalLayoutable> elements) {
    if (rootElement == null || elements == null || elements.size() == 0) {
      return;
    }

    Box rootBox = rootElement.getLayoutPos();
    InternalBoxConstraints rootBoxConstraints = rootElement.getBoxConstraints();

    for (int i=0; i<elements.size(); i++) {
      layoutElement(elements.get(i), rootBox, rootBoxConstraints);
    }
  }

  private int leftMargin(final InternalBoxConstraints boxConstraints, final int rootBoxWidth) {
    return boxConstraints.getMarginLeft().getValueAsInt(rootBoxWidth);
  }

  private int rightMargin(final InternalBoxConstraints boxConstraints, final int rootBoxWidth) {
    return boxConstraints.getMarginRight().getValueAsInt(rootBoxWidth);
  }

  private int topMargin(final InternalBoxConstraints boxConstraints, final int rootBoxHeight) {
    return boxConstraints.getMarginTop().getValueAsInt(rootBoxHeight);
  }

  private int bottomMargin(final InternalBoxConstraints boxConstraints, final int rootBoxHeight) {
    return boxConstraints.getMarginBottom().getValueAsInt(rootBoxHeight);
  }

  private void layoutElement(final InternalLayoutable element, Box rootBox, InternalBoxConstraints rootBoxConstraints) {
    Box box = element.getLayoutPos();
    InternalBoxConstraints constraint = element.getBoxConstraints();

    if (constraint.getWidth() != null && constraint.getWidth().hasHeightSuffix()) {
      handleVerticalAlignment(rootBox, rootBoxConstraints, box, constraint);
      handleHorizontalAlignment(rootBox, rootBoxConstraints, box, constraint);
    } else if (constraint.getHeight() != null && constraint.getHeight().hasWidthSuffix()) {
      handleHorizontalAlignment(rootBox, rootBoxConstraints, box, constraint);
      handleVerticalAlignment(rootBox, rootBoxConstraints, box, constraint);
    } else {
      handleVerticalAlignment(rootBox, rootBoxConstraints, box, constraint);
      handleHorizontalAlignment(rootBox, rootBoxConstraints, box, constraint);
    }

    box.setX(box.getX() + leftMargin(constraint, rootBox.getWidth()) - rightMargin(constraint, rootBox.getWidth()));
    box.setY(box.getY() + topMargin(constraint, rootBox.getHeight()) - bottomMargin(constraint, rootBox.getHeight()));
  }

  void handleHorizontalAlignment(
      final Box rootBox,
      final InternalBoxConstraints rootBoxConstraints,
      final Box box,
      final InternalBoxConstraints constraint) {
    if (constraint.getWidth() != null) {
      handleWidthConstraint(rootBox, rootBoxConstraints, box, constraint);
    } else {
      box.setX(
          0
          + rootBoxConstraints.getPaddingLeft().getValueAsInt(rootBox.getWidth()));
      box.setWidth(
          rootBox.getWidth()
          - rootBoxConstraints.getPaddingLeft().getValueAsInt(rootBox.getWidth())
          - rootBoxConstraints.getPaddingRight().getValueAsInt(rootBox.getWidth()));
    }
  }

  void handleVerticalAlignment(
      final Box rootBox,
      final InternalBoxConstraints rootBoxConstraints,
      final Box box,
      final InternalBoxConstraints constraint) {
    if (constraint.getHeight() != null) {
      handleHeightConstraint(rootBox, rootBoxConstraints, box, constraint);
    } else {
      box.setY(
          0
          + rootBoxConstraints.getPaddingTop().getValueAsInt(rootBox.getHeight()));
      box.setHeight(
          rootBox.getHeight()
          - rootBoxConstraints.getPaddingTop().getValueAsInt(rootBox.getHeight())
          - rootBoxConstraints.getPaddingBottom().getValueAsInt(rootBox.getHeight()));
    }
  }

  private void handleWidthConstraint(
      final Box rootBox,
      final InternalBoxConstraints rootBoxConstraints,
      final Box box,
      final InternalBoxConstraints constraint) {
    int rootBoxX = 0 + rootBoxConstraints.getPaddingLeft().getValueAsInt(rootBox.getWidth());
    int rootBoxWidth = rootBox.getWidth() -
                       rootBoxConstraints.getPaddingLeft().getValueAsInt(rootBox.getWidth()) -
                       rootBoxConstraints.getPaddingRight().getValueAsInt(rootBox.getWidth());

    int boxWidth = (int) constraint.getWidth().getValue(rootBoxWidth);
    if (constraint.getWidth().hasHeightSuffix()) {
      boxWidth = (int) constraint.getWidth().getValue(box.getHeight()); 
    }
    box.setWidth(boxWidth);

    if (constraint.getHorizontalAlign() == HAlign.left) {
      box.setX(rootBoxX);
    } else if (constraint.getHorizontalAlign() == HAlign.right) {
      box.setX(rootBoxX +
               rootBox.getWidth() -
               rootBoxConstraints.getPaddingRight().getValueAsInt(rootBox.getWidth()) - boxWidth);
    } else {
      // default and center is the same in here
      box.setX(rootBoxX + (rootBoxWidth - boxWidth) / 2);
    }
  }

  private void handleHeightConstraint(
      final Box rootBox,
      final InternalBoxConstraints rootBoxConstraints,
      final Box box,
      final InternalBoxConstraints constraint) {
    int rootBoxY = 0 + rootBoxConstraints.getPaddingTop().getValueAsInt(rootBox.getHeight());
    int rootBoxHeight = rootBox.getHeight() -
                        rootBoxConstraints.getPaddingTop().getValueAsInt(rootBox.getHeight()) -
                        rootBoxConstraints.getPaddingBottom().getValueAsInt(rootBox.getHeight());

    int boxHeight = (int) constraint.getHeight().getValue(rootBoxHeight);
    if (constraint.getHeight().hasWidthSuffix()) {
      boxHeight = (int) constraint.getHeight().getValue(box.getWidth());
    }
    box.setHeight(boxHeight);

    if (constraint.getVerticalAlign() == VAlign.top) {
      box.setY(rootBoxY);
    } else if (constraint.getVerticalAlign() == VAlign.bottom) {
      box.setY(rootBoxY +
               rootBox.getHeight() -
               rootBoxConstraints.getPaddingBottom().getValueAsInt(rootBox.getHeight()) - boxHeight);
    } else {
      // center is default in here
      box.setY(rootBoxY + (rootBoxHeight - boxHeight) / 2);
    }
  }
}
