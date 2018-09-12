package core.repository;

import com.google.inject.ImplementedBy;
import core.Repository;
import core.entity.Token;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 令牌仓库。
 *
 * @author mrzhqiang
 */
@ImplementedBy(TokenEBeanRepository.class)
public interface TokenRepository extends Repository<Long, Token> {
  String BEARER = "Bearer ";

  /**
   * 查询指定的访问令牌是否存在。
   *
   * @param accessToken 访问令牌。
   * @return 如果存在，说明数据库中有；不存在，则没有找到令牌。
   */
  @Nonnull Optional<Token> find(String accessToken);

  /**
   * 认证 TOKEN 权限。
   *
   * @param bearerToken HTTP 头中的权限字段值。
   * @return 如果存在，说明权限认证成功；不存在，说明权限认证失败。
   */
  @Nonnull default Optional<Token> authenticate(String bearerToken) {
    return Optional.ofNullable(bearerToken)
        .filter(s -> s.startsWith(BEARER))
        .map(s -> s.replaceFirst(BEARER, ""))
        .flatMap(this::find);
  }
}
