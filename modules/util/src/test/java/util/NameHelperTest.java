package util;

import java.awt.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class NameHelperTest {

  @Test
  public void firstLetter() {
    try {
      String firstLetter = NameHelper.firstLetter(null);
      assertNotNull(firstLetter);
    } catch (NullPointerException ignore) {
    }

    String defaultLetter = NameHelper.firstLetter("");
    assertNotNull(defaultLetter);
    System.out.println(defaultLetter);

    String mrzhqiang = NameHelper.firstLetter("play-home");
    assertNotNull(mrzhqiang);
    System.out.println(mrzhqiang);
  }

  @Test
  public void color() {
    try {
      int color = NameHelper.color(null);
      assertEquals(color, 0);
    } catch (NullPointerException ignore) {
    }

    int color = NameHelper.color("mrzhqiang");
    Color decode = Color.decode(color + "");
    System.out.println(decode);
  }
}