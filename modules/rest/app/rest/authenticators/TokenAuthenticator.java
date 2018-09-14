package rest.authenticators;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import core.Redis;
import core.exception.ApplicationException;
import framework.ErrorResponse;
import framework.SimpleParser;
import java.util.Optional;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 令牌认证器。
 *
 * @author mrzhqiang
 */
public final class TokenAuthenticator extends Security.Authenticator {
  private final Redis redis;
  private final TokenRepository tokenRepository;

  @Inject
  public TokenAuthenticator(Redis redis, TokenRepository tokenRepository) {
    this.redis = redis;
    this.tokenRepository = tokenRepository;
  }

  @Override public String getUsername(Http.Context ctx) {
    Optional<String> optional = ctx.request().header(Http.HeaderNames.AUTHORIZATION);
    // 检查是否过期，过期则抛出 403 异常；没有过期则将账户存入 Redis 中，返回存储的 Key 到当前会话
    return optional.flatMap(tokenRepository::authenticate)
        .flatMap(token -> {
          ApplicationException.forbidden(token.isValid(), "Token 已过期，请刷新。");
          return redis.get(jedis -> {
            JsonNode jsonNode = Json.toJson(token.account);
            String key = "account:" + token.account.id;
            jedis.hmset(key, SimpleParser.fromMapString(jsonNode));
            return key;
          });
        })
        .orElse(null);
  }

  @Override public Result onUnauthorized(Http.Context ctx) {
    return unauthorized(Json.toJson(ErrorResponse.ofAuthenticator("Token 无效，请重新登录。")));
  }
}
