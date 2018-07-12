package com.github.mrzhqiang.util.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationExceptionTest {

  @Test
  public void badRequest() {
    assertEquals(ApplicationException.badRequest("Bad request.").statusCode(), 400);
    assertEquals(ApplicationException.badRequest(new RuntimeException()).statusCode(), 400);
  }

  @Test
  public void unauthorized() {
    assertEquals(ApplicationException.unauthorized("Unauthorized.").statusCode(), 401);
    assertEquals(ApplicationException.unauthorized(new RuntimeException()).statusCode(), 401);
  }

  @Test
  public void forbidden() {
    assertEquals(ApplicationException.forbidden("Forbidden.").statusCode(), 403);
    assertEquals(ApplicationException.forbidden(new RuntimeException()).statusCode(), 403);
  }

  @Test
  public void notFound() {
    assertEquals(ApplicationException.notFound("Not found.").statusCode(), 404);
    assertEquals(ApplicationException.notFound(new RuntimeException()).statusCode(), 404);
  }

}