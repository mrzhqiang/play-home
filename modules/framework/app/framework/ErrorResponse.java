package framework;

import com.google.common.base.MoreObjects;
import java.util.Locale;
import java.util.Objects;
import play.mvc.Http;

/**
 * 错误/异常响应。
 * <p>
 * 一般在生产环境下响应，开发环境触发默认流程。
 *
 * @author mrzhqiang
 */
public final class ErrorResponse {
  private static final int HTTP_STATUS = 500;
  private static final int CODE = -1;
  private static final String MESSAGE = "A unknown error occurred.";
  private static final String DEVELOPER_MESSAGE = "Reference [moreInfo].";
  private static final String MORE_INFO = "http://developer.randall.top";

  private static final String APP_PACKAGES_REGEX =
      "^(controllers|models|util|core|framework|service|rest).*";

  final int httpStatus;
  private final int code;
  private final String message;
  private final String developerMessage;
  private final String moreInfo;

  @SuppressWarnings("unused")
  // Json 序列化需要
  public ErrorResponse() {
    this(HTTP_STATUS, CODE, MESSAGE, DEVELOPER_MESSAGE, MORE_INFO);
  }

  private ErrorResponse(int httpStatus, int code, String message, String developerMessage,
      String moreInfo) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
    this.developerMessage = developerMessage;
    this.moreInfo = moreInfo;
  }

  public static ErrorResponse ofAuthenticator(String message) {
    return new ErrorResponse(
        Http.Status.UNAUTHORIZED, message.hashCode(), message, DEVELOPER_MESSAGE, MORE_INFO);
  }

  static ErrorResponse unknownError(int code) {
    return new ErrorResponse(HTTP_STATUS, code, MESSAGE, DEVELOPER_MESSAGE, MORE_INFO);
  }

  static ErrorResponse clientError(int httpStatus, int code, String message) {
    return new ErrorResponse(httpStatus, code, message, DEVELOPER_MESSAGE, MORE_INFO);
  }

  static ErrorResponse serverError(Throwable throwable) {
    return new ErrorResponse(HTTP_STATUS, CODE, "A server error occurred.",
        findMessageFromStackTrace(throwable), MORE_INFO);
  }

  private static String findMessageFromStackTrace(Throwable throwable) {
    if (throwable == null) {
      return "Not content.";
    }

    for (StackTraceElement trace : throwable.getStackTrace()) {
      String className = trace.getClassName();
      String packageName = className.substring(0, className.indexOf("."));
      if (packageName.matches(APP_PACKAGES_REGEX)) {
        return String.format(Locale.getDefault(),
            "Exception: %s, StackTrace: %s",
            throwable, trace);
      }
    }
    return throwable.toString();
  }

  @Override public int hashCode() {
    return Objects.hash(httpStatus, code, message, developerMessage, moreInfo);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof ErrorResponse)) {
      return false;
    }

    ErrorResponse other = (ErrorResponse) obj;
    return Objects.equals(httpStatus, other.httpStatus)
        && Objects.equals(code, other.code)
        && Objects.equals(message, other.message)
        && Objects.equals(developerMessage, other.developerMessage)
        && Objects.equals(moreInfo, other.moreInfo);
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("httpStatus", httpStatus)
        .add("code", code)
        .add("message", message)
        .add("developerMessage", developerMessage)
        .add("moreInfo", moreInfo)
        .toString();
  }
}
