package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.AccountData;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

@Singleton
public final class AccountController extends Controller {

  private final Form<AccountData> accountForm;

  @Inject
  public AccountController(FormFactory formFactory) {
    this.accountForm = formFactory.form(AccountData.class);
  }

  public Result addAccount() {
    Form<AccountData> bindForm = accountForm.bindFromRequest();
    if (bindForm.hasErrors()) {
      Logger.error("error = {}", bindForm.allErrors());
      return badRequest();
    }
    AccountData data = bindForm.get();
    return ok(data.toString());
  }
}
