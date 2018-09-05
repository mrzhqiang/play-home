package core.repository;

import core.entity.Token;

/**
 * @author mrzhqiang
 */
public interface TokenRepository extends Repository<Long, Token> {
  String BEARER = "Bearer ";

}
