package com.zachary_moore.lint;

import java.util.Objects;


public class Error {

  private final String path;

  private final String message;

  public Error(String path, String message) {
    this.path = path;
    this.message = message;
  }

  public String getPath() {
    return path;
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
    Error error = (Error) o;
    return path.equals(error.path) && message.equals(error.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, message);
  }

  @Override
  public String toString() {
    return "Error{" + "path='" + path + '\'' + ", message='" + message + '\'' + '}';
  }
}
