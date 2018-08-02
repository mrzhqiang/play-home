package core.exception;

/**
 * 底层数据库执行异常。
 *
 * @author qiang.zhang
 */
public final class DatabaseException extends RuntimeException {
  public DatabaseException(String message) {
    super(message);
  }

  public DatabaseException(String message, Throwable cause) {
    super(message, cause);
  }
}
