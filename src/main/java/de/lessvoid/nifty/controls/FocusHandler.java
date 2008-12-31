package de.lessvoid.nifty.controls;

import java.util.ArrayList;
import java.util.logging.Logger;

import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;

/**
 * FocusHandler.
 * @author void
 */
public class FocusHandler {
  private Logger log = Logger.getLogger(FocusHandler.class.getName());

  /**
   * all elements that this focus handler controls.
   */
  private ArrayList < Element > entries = new ArrayList < Element >();

  /**
   * buffer to save stuff.
   */
  private ArrayList < ArrayList < Element >> elementBuffer = new ArrayList < ArrayList < Element >>();

  private Element mouseFocusElement;
  private ArrayList < Element > mouseFocusElementBuffer = new ArrayList < Element >();

  private Element keyboardFocusElement;
  private ArrayList < Element > keyboardFocusElementBuffer = new ArrayList < Element >();

  public FocusHandler() {
    mouseFocusElement = null;
    keyboardFocusElement = null;
  }

  /**
   * add an element to the focus handler.
   * @param element element to add
   */
  public void addElement(final Element element) {
    entries.add(element);
  }

  /**
   * get next element.
   * @param current current element
   * @return next element
   */
  public Element getNext(final Element current) {
    if (entries.isEmpty()) {
      return current;
    }

    int index = entries.indexOf(keyboardFocusElement);
    if (index == -1) {
      return current;
    }

    index++;
    if (index >= entries.size()) {
      index = 0;
    }
    Element nextElement = entries.get(index);
    if (nextElement.isFocusable()) {
      return nextElement;
    } else {
      return getNext(nextElement);
    }
  }

  /**
   * get prev element.
   * @param current current element
   * @return prev element
   */
  public Element getPrev(final Element current) {
    if (entries.isEmpty()) {
      return current;
    }

    int index = entries.indexOf(keyboardFocusElement);
    if (index == -1) {
      return current;
    }

    index--;
    if (index < 0) {
      index = entries.size() - 1;
    }
    Element prevElement = entries.get(index);
    if (prevElement.isFocusable()) {
      return prevElement;
    } else {
      return getPrev(prevElement);
    }
  }

  /**
   * remove this element.
   * @param element element
   */
  public void remove(final Element element) {
    entries.remove(element);
    lostKeyboardFocus(element);
    lostMouseFocus(element);
  }

  /**
   * get first entry.
   * @return first
   */
  public Element getFirstFocusElement() {
    if (entries.isEmpty()) {
      return null;
    }
    return entries.get(0);
  }

  /**
   * save all states.
   */
  public void pushState() {
    ArrayList < Element > copy = new ArrayList < Element >();
    copy.addAll(entries);
    elementBuffer.add(copy);

    entries.clear();

    keyboardFocusElementBuffer.add(keyboardFocusElement);
    keyboardFocusElement = null;

    mouseFocusElementBuffer.add(mouseFocusElement);
    mouseFocusElement = null;
  }

  /**
   * restore all states.
   */
  public void popState() {
    entries.clear();
    entries.addAll(elementBuffer.get(elementBuffer.size() - 1));

    setKeyFocus(keyboardFocusElementBuffer.remove(keyboardFocusElementBuffer.size() - 1));
    mouseFocusElement = mouseFocusElementBuffer.remove(mouseFocusElementBuffer.size() - 1);

    // TODO: review this later for the moment let's just clear the mouseFocusElement
    //
    // background: at the moment a popup is opened there is a mouseFocusElement available with exclusive
    // access to the mouse. this element gets saved when the popup is opened and restored here. the mouse event
    // that informs the release of the mouse is long gone and leads to the effect that the mouse is still
    // exclusiv to this element.
    mouseFocusElement = null;
  }

  public void resetFocusElements() {
    keyboardFocusElement = null;
    mouseFocusElement = null;
  }

  /**
   * set the focus to the given element.
   * @param newFocusElement new focus element
   */
  public void setKeyFocus(final Element newFocusElement) {
    if (keyboardFocusElement == newFocusElement) {
      return;
    }

    if (keyboardFocusElement != null) {
      keyboardFocusElement.stopEffect(EffectEventId.onFocus);
      keyboardFocusElement.startEffect(EffectEventId.onLostFocus);
    }

    keyboardFocusElement = newFocusElement;
    log.fine("keyboard focus element now changed to [" + keyboardFocusElement.toString() + "]");

    if (keyboardFocusElement != null) {
      keyboardFocusElement.startEffect(EffectEventId.onFocus, null);
    }
  }

  public void lostKeyboardFocus(final Element elementThatLostFocus) {
    log.fine("lostKeyboardFocus for [" + elementThatLostFocus.toString() + "]");
    if (keyboardFocusElement == elementThatLostFocus) {
      keyboardFocusElement.stopEffect(EffectEventId.onFocus);
      keyboardFocusElement.startEffect(EffectEventId.onLostFocus);
      keyboardFocusElement = null;
    }
  }

  public void keyEvent(final KeyboardInputEvent inputEvent) {
    if (keyboardFocusElement != null) {
      keyboardFocusElement.keyEvent(inputEvent);
    }
  }

  public void requestExclusiveMouseFocus(final Element newFocusElement) {
    if (mouseFocusElement == newFocusElement) {
      return;
    }
    mouseFocusElement = newFocusElement;
    log.fine("requestExclusiveMouseFocus for [" + mouseFocusElement.toString() + "]");
  }

  public boolean canProcessMouseEvents(final Element element) {
    if (mouseFocusElement == null) {
      return true;
    }

    boolean canProcess = mouseFocusElement == element;
    log.fine(
        "canProcessMouseEvents for [" + element.toString() + "] ==> "
        + canProcess + " (" + mouseFocusElement.toString() + ")");
    return canProcess;
  }

  public void lostMouseFocus(final Element elementThatLostFocus) {
    log.fine("lostMouseFocus for [" + elementThatLostFocus.toString() + "]");
    if (mouseFocusElement == elementThatLostFocus) {
      mouseFocusElement = null;
    }
  }

  public String toString() {
    String mouseFocusString = "---";
    if (mouseFocusElement != null) {
      mouseFocusString = mouseFocusElement.toString();
    }

    String keyboardFocusString = "---";
    if (keyboardFocusElement != null) {
      keyboardFocusString = keyboardFocusElement.toString();
    }

    return
         "focus element (mouse):    " + mouseFocusString + "\n"
       + "focus element (keyboard): " + keyboardFocusString + "\n"
       + "focus element size: " + entries.size();
  }

  public boolean hasAnyElementTheKeyboardFocus() {
    return keyboardFocusElement != null;
  }
}
