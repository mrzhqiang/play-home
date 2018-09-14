package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public final class NameHelperTest {
  @Test
  public void firstLetter() {
    String defaultLetter = NameHelper.firstLetter("");
    assertNotNull(defaultLetter);
    assertEquals("m", defaultLetter);

    String mrzhqiang = NameHelper.firstLetter("play-home");
    assertNotNull(mrzhqiang);
    assertEquals("p", mrzhqiang);
  }

  @Test
  public void color() {
    int color = NameHelper.color("mrzhqiang");
    assertEquals(-12627531, color);
  }

  @Test
  public void checkName() {
    assertTrue(NameHelper.checkName("中2"));
  }

  @Test
  public void checkNameAndLength() {
    assertTrue(NameHelper.checkName("中国No1", 2, 10));
  }

  @Test
  public void checkChinese() {
    assertFalse(NameHelper.checkChinese("false"));
  }

  @Test
  public void checkChineseAndLength() {
    assertFalse(NameHelper.checkChinese("中华人民共和国", 1, 4));
  }

  @Test
  public void checkEnglish() {
    assertFalse(NameHelper.checkEnglish("007"));
  }

  @Test
  public void checkEnglishAndLength() {
    assertFalse(NameHelper.checkEnglish("哈哈", 1, 2));
  }

  @Test
  public void checkNumber() {
    assertFalse(NameHelper.checkNumber("number"));
  }

  @Test
  public void checkNumberAndLength() {
    assertFalse(NameHelper.checkNumber("one", 1, 3));
  }
}