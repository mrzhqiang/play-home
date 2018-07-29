package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public class UrlHelperTest {

  @Test
  public void simpleCheck() {
    assertTrue(UrlHelper.simpleCheck("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=elasticsearch%20%E9%9B%86%E7%BE%A4%20%E7%AE%80%E4%B9%A6&oq=elasticsearch%2520%25E9%259B%2586%25E7%25BE%25A4&rsv_pq=b0984d1f00004663&rsv_t=0ee09jkw39E%2Fpyn%2FkDRJcfOOH88tPxR%2Bk90R6hyvMcKQnClcLGuCJo4HlTM&rqlang=cn&rsv_enter=0&inputT=3623&rsv_sug3=67&rsv_sug1=65&rsv_sug7=100&rsv_sug2=0&rsv_sug4=3623"));
  }

  @Test
  public void autoComplete() {
    assertEquals("http://www.baidu.com", UrlHelper.autoComplete("www.baidu.com"));
  }

  @Test
  public void forceHttp() {
    assertEquals("http://www.baidu.com", UrlHelper.forceHttp("https://www.baidu.com"));
  }
}