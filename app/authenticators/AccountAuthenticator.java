package authenticators;

import com.google.inject.Inject;
import framework.ErrorResponse;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import service.account.AccountService;

/**
 * 账号认证器。
 *
 * @author mrzhqiang
 */
public final class AccountAuthenticator extends Security.Authenticator {
  private final AccountService accountService;

  @Inject
  public AccountAuthenticator(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override public String getUsername(Http.Context ctx) {
    String accessToken = ctx.request().cookie("accessToken").value();
    // 检查是否过期，过期则抛出 403 异常；没有过期则将账户存入 Redis 中，返回存储的 Key 到当前会话
    return accessToken;
  }

  @Override public Result onUnauthorized(Http.Context ctx) {
    return unauthorized(Json.toJson(ErrorResponse.ofAuthenticator("Token 无效，请重新登录。")));
  }
}
