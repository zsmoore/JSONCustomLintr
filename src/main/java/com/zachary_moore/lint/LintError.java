package com.zachary_moore.lint;

import java.util.Objects;


public class LintError {

  private final String jsonPath;
  private final String message;

  private LintError(String jsonPath, String message) {
    this.jsonPath = jsonPath;
    this.message = message;
  }

  public String getJsonPath() {
    return jsonPath;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LintError lintError = (LintError) o;
    return jsonPath.equals(lintError.jsonPath) && message.equals(lintError.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jsonPath, message);
  }

  /**
   * Builder pattern class for building our {@link LintError}
   */
  public static class Builder {

    private String jsonPath;
    private String message;

    public LintError.Builder setJsonPath(String jsonPath) {
      this.jsonPath = jsonPath;
      return this;
    }

    public LintError.Builder setMessage(String message) {
      this.message = message;
      return this;
    }

    public LintError build() {
      return new LintError(jsonPath, message);
    }
  }
}
