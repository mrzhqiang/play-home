package framework;

import java.util.Locale;

/**
 * 错误/异常响应。
 * <p>
 * 一般在生产环境下响应，开发环境触发默认流程。
 *
 * @author mrzhqiang
 */
final class ErrorResponse {

  private static final int HTTP_STATUS = 500;
  private static final int CODE = -1;
  private static final String MESSAGE = "A unknown error occurred.";
  private static final String DEVELOPER_MESSAGE = "Reference [moreInfo].";
  private static final String MORE_INFO = "http://developer.randall.top";

  private static final String APP_PACKAGES_REGEX =
      "^(controllers|models|util|framework|core|service|rest).*";

  final int httpStatus;
  final int code;
  final String message;
  final String developerMessage;
  final String moreInfo;

  private ErrorResponse(int httpStatus, int code, String message, String developerMessage,
      String moreInfo) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
    this.developerMessage = developerMessage;
    this.moreInfo = moreInfo;
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
}
