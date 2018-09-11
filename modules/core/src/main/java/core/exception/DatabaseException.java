package core.exception;

/**
 * 数据库异常。
 * <p>
 * 通常是底层执行时，抛出的各种异常，这里将它们融为一体。
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
