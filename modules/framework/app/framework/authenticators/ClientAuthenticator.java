package framework.authenticators;

import com.google.inject.Inject;
import core.repository.ClientRepository;
import core.repository.TokenRepository;
import framework.ErrorResponse;
import java.util.Optional;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 客户端认证器。
 *
 * @author mrzhqiang
 */
public final class ClientAuthenticator extends Security.Authenticator {
  private final ClientRepository clientRepository;
  private final TokenRepository tokenRepository;

  @Inject
  public ClientAuthenticator(ClientRepository clientRepository, TokenRepository tokenRepository) {
    this.clientRepository = clientRepository;
    this.tokenRepository = tokenRepository;
  }

  @Override public String getUsername(Http.Context ctx) {
    Optional<String> optional = ctx.request().header(Http.HeaderNames.AUTHORIZATION);
    // 客户端权限不保存有效信息到当前会话
    return optional.flatMap(clientRepository::authenticate)
        .map(client -> "")
        .orElse(optional.flatMap(tokenRepository::authenticate)
            .map(token -> "")
            .orElse(null));
  }

  @Override public Result onUnauthorized(Http.Context ctx) {
    return unauthorized(Json.toJson(ErrorResponse.ofAuthenticator("关于权限访问，请联系QQ：287431404。")));
  }
}
