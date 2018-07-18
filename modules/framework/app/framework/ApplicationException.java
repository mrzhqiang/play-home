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

  public static ApplicationException badRequest(String message) {
    return new BadRequestException(message);
  }

  public static ApplicationException badRequest(Throwable cause) {
    return new BadRequestException(cause.getMessage());
  }

  public static ApplicationException unauthorized(String message) {
    return new UnauthorizedException(message);
  }

  public static ApplicationException unauthorized(Throwable cause) {
    return new UnauthorizedException(cause.getMessage());
  }

  public static ApplicationException forbidden(String message) {
    return new ForbiddenException(message);
  }

  public static ApplicationException forbidden(Throwable cause) {
    return new ForbiddenException(cause.getMessage());
  }

  public static ApplicationException notFound(String message) {
    return new NotFoundException(message);
  }

  public static ApplicationException notFound(Throwable cause) {
    return new NotFoundException(cause.getMessage());
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
