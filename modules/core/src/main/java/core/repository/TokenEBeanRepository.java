package core.repository;

import com.google.common.base.Verify;
import com.google.inject.Singleton;
import core.entity.Token;
import core.util.Tokens;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的令牌仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class TokenEBeanRepository extends EBeanRepository<Long, Token>
    implements TokenRepository {
  TokenEBeanRepository() {
    super(Token.class);
  }

  @Nonnull @Override public Optional<Token> find(String accessToken) {
    Verify.verify(Tokens.checkAccessToken(accessToken), "invalid accessToken: %s", accessToken);

    return provide(() -> finder.query().where()
        .eq(Token.COL_ACCESS_TOKEN, accessToken)
        .findOneOrEmpty());
  }
}
