package framework;

import play.mvc.Http;

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

  static ApplicationException badRequest(String cause) {
    return new BadRequestException(cause);
  }

  public static void badRequest(boolean expression, String message) {
    if (!expression) {
      throw new BadRequestException(message);
    }
  }

  public static void badRequest(boolean expression, Throwable cause) {
    if (!expression) {
      throw new BadRequestException(cause.getMessage());
    }
  }

  public static ApplicationException unauthorized(String cause) {
    return new UnauthorizedException(cause);
  }

  public static void unauthorized(boolean expression, String message) {
    if (!expression) {
      throw new UnauthorizedException(message);
    }
  }

  public static void unauthorized(boolean expression, Throwable cause) {
    if (!expression) {
      throw new UnauthorizedException(cause.getMessage());
    }
  }

  public static ApplicationException forbidden(String cause) {
    return new ForbiddenException(cause);
  }

  public static void forbidden(boolean expression, String message) {
    if (!expression) {
      throw new ForbiddenException(message);
    }
  }

  public static void forbidden(boolean expression, Throwable cause) {
    if (!expression) {
      throw new ForbiddenException(cause.getMessage());
    }
  }

  public static ApplicationException notFound(String cause) {
    return new NotFoundException(cause);
  }

  public static void notFound(boolean expression, String message) {
    if (!expression) {
      throw new NotFoundException(message);
    }
  }

  public static void notFound(boolean expression, Throwable cause) {
    if (!expression) {
      throw new NotFoundException(cause.getMessage());
    }
  }

  private static class BadRequestException extends ApplicationException {
    BadRequestException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return Http.Status.BAD_REQUEST;
    }
  }

  private static class UnauthorizedException extends ApplicationException {
    UnauthorizedException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return Http.Status.UNAUTHORIZED;
    }
  }

  private static class ForbiddenException extends ApplicationException {
    ForbiddenException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return Http.Status.FORBIDDEN;
    }
  }

  private static class NotFoundException extends ApplicationException {
    NotFoundException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return Http.Status.NOT_FOUND;
    }
  }
}
