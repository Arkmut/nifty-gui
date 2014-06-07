package de.lessvoid.nifty.builder;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.dynamic.ScreenCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class ScreenBuilderWithLayerTest {
  private Nifty niftyMock;
  private ScreenCreator screenCreator;
  private Screen screen;
  private Element screenRootElement;
  private Element layerElement;

  @Before
  public void before() {
    screenRootElement = createMock(Element.class);
    replay(screenRootElement);

    layerElement = createMock(Element.class);
    replay(layerElement);

    niftyMock = createMock(Nifty.class);
    replay(niftyMock);

    screen = createMock(Screen.class);
    expect(screen.getRootElement()).andReturn(screenRootElement);

    screenCreator = createMock(ScreenCreator.class);
    expect(screenCreator.create(niftyMock)).andReturn(screen);
    replay(screenCreator);
  }

  @After
  public void after() {
    verify(niftyMock);
    verify(screenCreator);
    verify(screen);
    verify(screenRootElement);
    verify(layerElement);
  }

  @Test
  public void testWithOneLayer() {
    replay(screen);

    LayerBuilder layerBuilder = createLayerBuilder();

    ScreenBuilder screenBuilder = new ScreenBuilderCreatorMock();
    screenBuilder.layer(layerBuilder);
    assertEquals(screen, screenBuilder.build(niftyMock));

    verify(layerBuilder);
  }

  @Test
  public void testWithTwoLayers() {
    replay(screen);

    LayerBuilder layerBuilder1 = createLayerBuilder();
    LayerBuilder layerBuilder2 = createLayerBuilder();

    ScreenBuilder screenBuilder = new ScreenBuilderCreatorMock();
    screenBuilder.layer(layerBuilder1);
    screenBuilder.layer(layerBuilder2);
    assertEquals(screen, screenBuilder.build(niftyMock));

    verify(layerBuilder1);
    verify(layerBuilder2);
  }

  @Test
  public void testWithOneLayerParentAlreadySet() {
    replay(screen);

    LayerBuilder layerBuilder = createLayerBuilderWithParent();

    ScreenBuilder screenBuilder = new ScreenBuilderCreatorMock();
    screenBuilder.layer(layerBuilder);
    assertEquals(screen, screenBuilder.build(niftyMock));

    verify(layerBuilder);
  }

  private LayerBuilder createLayerBuilder() {
    LayerBuilder layerBuilder = createMock(LayerBuilder.class);
    expect(layerBuilder.build(niftyMock, screen, screenRootElement)).andReturn(layerElement);
    replay(layerBuilder);
    return layerBuilder;
  }

  private LayerBuilder createLayerBuilderWithParent() {
    LayerBuilder layerBuilder = createMock(LayerBuilder.class);
    expect(layerBuilder.build(niftyMock, screen, screenRootElement)).andReturn(layerElement);
    replay(layerBuilder);
    return layerBuilder;
  }

  private class ScreenBuilderCreatorMock extends ScreenBuilder {
    public ScreenBuilderCreatorMock() {
      super("myid");
    }

    @Override
    ScreenCreator createScreenCreator(@Nonnull final String id) {
      return screenCreator;
    }
  }
}
