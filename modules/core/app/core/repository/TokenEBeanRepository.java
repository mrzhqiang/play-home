package core.repository;

import com.google.inject.Singleton;
import core.entity.Token;
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
    return Optional.ofNullable(accessToken)
        .flatMap(s -> dispose(() ->
            finder.query().where()
                .eq(Token.COL_ACCESS_TOKEN, s)
                .findOneOrEmpty()));
  }
}