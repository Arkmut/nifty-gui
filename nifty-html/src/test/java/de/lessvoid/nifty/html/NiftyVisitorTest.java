package de.lessvoid.nifty.html;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.ParagraphTag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.lessvoid.nifty.builder.PanelBuilder;

public class NiftyVisitorTest {
  private NiftyVisitor visitor;
  private NiftyBuilderFactory builderFactoryMock;

  @Before
  public void before() {
    builderFactoryMock = createMock(NiftyBuilderFactory.class);
    visitor = new NiftyVisitor(null, builderFactoryMock, null, null);
  }

  @After
  public void after() {
    verify(builderFactoryMock);
  }

  @Test
  public void simpleBodyTagSuccess() throws Exception {
    PanelBuilder bodyPanelBuilder = new PanelBuilder();

    expect(builderFactoryMock.createBodyPanelBuilder()).andReturn(bodyPanelBuilder);
    replay(builderFactoryMock);

    BodyTag bodyTag = new BodyTag();
    visitor.visitTag(bodyTag);
    visitor.visitEndTag(bodyTag);

    assertEquals(bodyPanelBuilder, visitor.builder());
    assertTrue(bodyPanelBuilder.getElementBuilders().isEmpty());
  }

  @Test
  public void paragraphTagRequiresBody() throws Exception {
    replay(builderFactoryMock);

    ParagraphTag p = new ParagraphTag();
    visitor.visitTag(p);
    visitor.visitEndTag(p);

    try {
      visitor.builder();
    } catch (Exception e) {
      assertEquals("This looks like HTML with a missing <body> tag\n", e.getMessage());
    }
  }
}
