package de.lessvoid.nifty.controls.listbox;

import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import org.easymock.Capture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class ListBoxNotifyTest {
  private ListBoxImpl<TestItem> listBox = new ListBoxImpl<TestItem>(null);
  private ListBoxView<TestItem> view;
  private TestItem o1 = new TestItem("o1");
  private TestItem o2 = new TestItem("o2");
  private Capture<ListBoxSelectionChangedEvent<TestItem>> lastEvent = Capture.newInstance();
  private SelectionCheck selectionCheck = new SelectionCheck(listBox);

  @Before
  public void before() {
    listBox.addItem(o1);
    listBox.addItem(o2);

    view = createMock(ListBoxView.class);
    listBox.bindToView(view, 2);
  }

  @After
  public void after() {
    verify(view);
  }

  @Test
  public void testSelectByItem() {
    view.display(ListBoxTestTool.buildValues(o1, o2), 0, ListBoxTestTool.buildValuesSelection(0));
    view.display(ListBoxTestTool.buildValues(o1, o2), 0, ListBoxTestTool.buildValuesSelection(0));
    view.publish(capture(lastEvent));
    replay(view);

    listBox.selectItem(o1);

    selectionCheck.assertChangedEventSelection(lastEvent.getValue(), o1);
    selectionCheck.assertChangedEventSelectionIndices(lastEvent.getValue(), 0);
  }

  @Test
  public void testSelectByItemIndex() {
    view.display(ListBoxTestTool.buildValues(o1, o2), 0, ListBoxTestTool.buildValuesSelection(0));
    view.display(ListBoxTestTool.buildValues(o1, o2), 0, ListBoxTestTool.buildValuesSelection(0));
    view.publish(capture(lastEvent));
    replay(view);

    listBox.selectItemByIndex(0);

    selectionCheck.assertChangedEventSelection(lastEvent.getValue(), o1);
    selectionCheck.assertChangedEventSelectionIndices(lastEvent.getValue(), 0);
  }
}
