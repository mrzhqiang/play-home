package controllers.admin;

import com.google.inject.Singleton;
import play.mvc.Controller;
import play.mvc.Result;

@Singleton
public final class AdminController extends Controller {

  public Result index() {
    return ok(views.html.admin.index.render());
  }

  public Result login() {
    return ok(views.html.admin.login.render());
  }

  public Result loginPost() {
    return ok();
  }

  public Result suggest() {
    return ok(views.html.admin.apply.render());
  }
}
