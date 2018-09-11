package core.exception;

import com.google.common.base.Preconditions;
import play.mvc.Http;

/**
 * 应用异常。
 * <p>
 * 类似 {@link NullPointerException}、{@link IllegalArgumentException} 和
 * {@link IllegalStateException} 等等原生异常类，都是针对 Java 的对象和方法所建立，
 * 开发者应该自定义应用级别的异常类，以识别相关异常实际上是客户端错误。
 *
 * @author mrzhqiang
 */
public abstract class ApplicationException extends RuntimeException {
  private ApplicationException(String message) {
    super(message);
  }

  /**
   * HTTP 状态码，通常是 400 - 500 的客户端错误。
   */
  public abstract int statusCode();

  /**
   * 通过原因生成 badRequest 的异常类。
   */
  public static ApplicationException badRequest(String cause) {
    Preconditions.checkNotNull(cause);
    return new BadRequestException(cause);
  }

  /**
   * 如果表达式不成立，直接抛出 badRequest 的异常。
   */
  public static void badRequest(boolean expression, String message) {
    Preconditions.checkNotNull(message, "message");
    if (!expression) {
      throw new BadRequestException(message);
    }
  }

  /**
   * 如果表达式不成立，直接抛出 badRequest 的异常。
   */
  public static void badRequest(boolean expression, Throwable cause) {
    Preconditions.checkNotNull(cause, "cause");
    if (!expression) {
      throw new BadRequestException(cause.getMessage());
    }
  }

  /**
   * 通过原因生成 unauthorized 的异常类。
   */
  public static ApplicationException unauthorized(String cause) {
    Preconditions.checkNotNull(cause, "cause");
    return new UnauthorizedException(cause);
  }

  /**
   * 如果表达式不成立，直接抛出 unauthorized 的异常。
   */
  public static void unauthorized(boolean expression, String message) {
    Preconditions.checkNotNull(message, "message");
    if (!expression) {
      throw new UnauthorizedException(message);
    }
  }

  /**
   * 如果表达式不成立，直接抛出 unauthorized 的异常。
   */
  public static void unauthorized(boolean expression, Throwable cause) {
    Preconditions.checkNotNull(cause, "cause");
    if (!expression) {
      throw new UnauthorizedException(cause.getMessage());
    }
  }

  /**
   * 通过原因生成 forbidden 的异常类。
   */
  public static ApplicationException forbidden(String cause) {
    Preconditions.checkNotNull(cause, "cause");
    return new ForbiddenException(cause);
  }

  /**
   * 如果表达式不成立，直接抛出 forbidden 的异常。
   */
  public static void forbidden(boolean expression, String message) {
    Preconditions.checkNotNull(message, "message");
    if (!expression) {
      throw new ForbiddenException(message);
    }
  }

  /**
   * 如果表达式不成立，直接抛出 forbidden 的异常。
   */
  public static void forbidden(boolean expression, Throwable cause) {
    Preconditions.checkNotNull(cause, "cause");
    if (!expression) {
      throw new ForbiddenException(cause.getMessage());
    }
  }

  /**
   * 通过原因生成 notFound 异常类。
   */
  public static ApplicationException notFound(String cause) {
    Preconditions.checkNotNull(cause, "cause");
    return new NotFoundException(cause);
  }

  /**
   * 如果表达式不成立，直接抛出 notFound 异常。
   */
  public static void notFound(boolean expression, String message) {
    Preconditions.checkNotNull(message, "message");
    if (!expression) {
      throw new NotFoundException(message);
    }
  }

  /**
   * 如果表达式不成立，直接抛出 notFound 异常。
   */
  public static void notFound(boolean expression, Throwable cause) {
    Preconditions.checkNotNull(cause, "cause");
    if (!expression) {
      throw new NotFoundException(cause.getMessage());
    }
  }

  private static final class BadRequestException extends ApplicationException {
    BadRequestException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return Http.Status.BAD_REQUEST;
    }
  }

  private static final class UnauthorizedException extends ApplicationException {
    UnauthorizedException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return Http.Status.UNAUTHORIZED;
    }
  }

  private static final class ForbiddenException extends ApplicationException {
    ForbiddenException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return Http.Status.FORBIDDEN;
    }
  }

  private static final class NotFoundException extends ApplicationException {
    NotFoundException(String message) {
      super(message);
    }

    @Override public int statusCode() {
      return Http.Status.NOT_FOUND;
    }
  }
}
