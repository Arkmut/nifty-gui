package de.lessvoid.nifty.examples.textalign;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.examples.NiftyExample;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import javax.annotation.Nonnull;

/**
 * @author void
 */
public class TextAlignStartScreen implements ScreenController, NiftyExample {
  private Nifty nifty;

  @Override
  public final void bind(@Nonnull final Nifty newNifty, @Nonnull final Screen newScreen) {
    this.nifty = newNifty;
  }

  @Override
  public final void onStartScreen() {
  }

  @Override
  public final void onEndScreen() {
  }

  public final void quit() {
    nifty.setAlternateKeyForNextLoadXml("fade");
    nifty.fromXml("all/intro.xml", "menu");
  }

  @Nonnull
  @Override
  public String getStartScreen() {
    return "start";
  }

  @Nonnull
  @Override
  public String getMainXML() {
    return "textalign/textalign.xml";
  }

  @Nonnull
  @Override
  public String getTitle() {
    return "Nifty Textalign Example";
  }

  @Override
  public void prepareStart(Nifty nifty) {
    // nothing to do
  }
}
