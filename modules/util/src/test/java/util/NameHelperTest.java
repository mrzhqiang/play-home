package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public final class NameHelperTest {
  @Test
  public void firstLetter() {
    try {
      String firstLetter = NameHelper.firstLetter(null);
      assertNotNull(firstLetter);
    } catch (NullPointerException ignore) {
    }

    String defaultLetter = NameHelper.firstLetter("");
    assertNotNull(defaultLetter);
    assertEquals("m", defaultLetter);

    String mrzhqiang = NameHelper.firstLetter("play-home");
    assertNotNull(mrzhqiang);
    assertEquals("p", mrzhqiang);
  }

  @Test
  public void color() {
    try {
      int color = NameHelper.color(null);
      assertEquals(color, 0);
    } catch (NullPointerException ignore) {
    }

    int color = NameHelper.color("mrzhqiang");
    assertEquals(-12627531, color);
  }
}