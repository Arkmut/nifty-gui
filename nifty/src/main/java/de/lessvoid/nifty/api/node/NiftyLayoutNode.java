/*
 * Copyright (c) 2015, Nifty GUI Community
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.lessvoid.nifty.api.node;

import de.lessvoid.nifty.api.NiftyLayout;
import de.lessvoid.nifty.api.types.Rect;
import de.lessvoid.nifty.api.types.Size;

import javax.annotation.Nonnull;

/**
 * This interface defines the simple layout node for Nifty. These nodes are the backbone of the layout system in Nifty.
 * They measure and arrange their children and report the sizes required to the parent.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public interface NiftyLayoutNode extends NiftyNode {
  /**
   * Check if the measure of this node is valid.
   * <p />
   * The return value of this function is only reliable during the layout phase.
   * <p />
   * A valid measurement in general means that the desired size of this component is known and valid. If the
   * measurement is turned invalid the arrangement of the component is turned invalid as well.
   *
   * @return {@code true} in case the measured value is valid
   */
  boolean isMeasureValid();

  /**
   * Check if the arrangement of this component is valid.
   * <p />
   * A valid arrangement means that the positions of the child nodes are known and valid. It also means that the
   * measurement is valid as a changed size of a component automatically causes the placement of the component to
   * become invalid.
   *
   * @return {@code true} if the arrangement is valid
   */
  boolean isArrangeValid();

  /**
   * Set the measurement value to invalid. This flags the component to be measured again during the next run of the
   * layout phase.
   * <p><b>Implementations of this method are expected to report to {@link NiftyLayout} in case the measure
   * turns invalid.</b></p>
   */
  void invalidateMeasure();

  /**
   * Set the arrangement of this node to invalid. This means that the positions of the children of this node have to
   * change and during the next run of the layout phase this node has to fix the arrangement of it's children.
   *
   * <p><b>Implementations of this method are expected to report to {@link NiftyLayout} in case the arrangement
   * turns invalid.</b></p>
   */
  void invalidateArrage();

  /**
   * The size that was calculated during the last measuring.
   * <p />
   * The return value of this function is only valid in case the measuring values are reported to be valid and
   * during the layout phase.
   *
   * @return the size that is desired to be covered by this element
   */
  @Nonnull
  Size getDesiredSize();

  /**
   * Perform the measuring operation for this node. This has to measure the size of all children to this node and
   * calculate the size required.
   *
   * @param availableSize the size available that is offered by the outer element. One or two dimensions of the
   *                      size may be infinite
   * @return the size measured that is required by this node.
   */
  @Nonnull
  Size measure(@Nonnull Size availableSize);

  /**
   * Arrange this component and it's children inside specified area.
   *
   * @param area this component and it's children should fill
   */
  void arrange(@Nonnull Rect area);
}
