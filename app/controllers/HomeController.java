package controllers;

import play.mvc.*;

/**
 * 主页控制器。
 *
 * @author playframework
 * @author mrzhqiang
 */
public final class HomeController extends Controller {
  /** 首页，展示宝藏列表。 */
  public Result index() {
    return ok(views.html.index.render());
  }
}
