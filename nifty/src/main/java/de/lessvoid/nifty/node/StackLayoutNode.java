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

package de.lessvoid.nifty.node;

import de.lessvoid.nifty.spi.node.NiftyNode;
import de.lessvoid.nifty.spi.node.NiftyNodeImpl;

import javax.annotation.Nonnull;

/**
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class StackLayoutNode implements NiftyNode {
  @Nonnull
  private final StackLayoutNodeImpl implementation;

  public StackLayoutNode() {
    this(Orientation.Vertical);
  }

  public StackLayoutNode(@Nonnull final Orientation orientation) {
    this(orientation, false);
  }

  public StackLayoutNode(@Nonnull final Orientation orientation, final boolean stretchLast) {
    this(new StackLayoutNodeImpl(orientation, stretchLast));
  }

  StackLayoutNode(@Nonnull final StackLayoutNodeImpl implementation) {
    this.implementation = implementation;
  }

  @Nonnull
  public Orientation getOrientation() {
    return implementation.getOrientation();
  }

  public void setOrientation(@Nonnull final Orientation orientation) {
    implementation.setOrientation(orientation);
  }

  public boolean isStretchLast() {
    return implementation.isStretchLast();
  }

  public void setStretchLast(final boolean stretchLast) {
    implementation.setStretchLast(stretchLast);
  }

  @Nonnull
  NiftyNodeImpl<StackLayoutNode> getImpl() {
    return implementation;
  }
}