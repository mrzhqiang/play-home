package util.exception;

/**
 * 应用异常。
 * <p>
 * 类似 {@link NullPointerException}、{@link IllegalArgumentException} 和
 * {@link IllegalStateException} 等等原生异常类，都是针对 Java 的对象和方法所建立的，
 * 开发者应该自定义应用级别的异常类，以免造成误解。
 *
 * @author mrzhqiang
 */
public abstract class ApplicationException extends RuntimeException {

  ApplicationException(String message) {
    super(message);
  }

  public abstract int statusCode();

  public static void badRequest(String message) {
    throw new BadRequestException(message);
  }

  public static void badRequest(Throwable cause) {
    throw new BadRequestException(cause.getMessage());
  }

  public static void forbidden(String message) {
    throw new ForbiddenException(message);
  }

  public static void forbidden(Throwable cause) {
    throw new ForbiddenException(cause.getMessage());
  }

  public static void notFound(String message) {
    throw new NotFoundException(message);
  }

  public static void notFound(Throwable cause) {
    throw new NotFoundException(cause.getMessage());
  }

  private static class BadRequestException extends ApplicationException {
    BadRequestException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return 400;
    }
  }

  private static class ForbiddenException extends ApplicationException {
    ForbiddenException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return 403;
    }
  }

  private static class NotFoundException extends ApplicationException {
    NotFoundException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return 404;
    }
  }
}
