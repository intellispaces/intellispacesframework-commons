package tech.intellispaces.commons.exception;

/**
 * Exception processing functions.
 */
public interface ExceptionFunctions {

  static RuntimeException coverIfChecked(Throwable e) {
    if (e instanceof RuntimeException) {
      return (RuntimeException) e;
    } else {
      return new CoveredCheckedException(e);
    }
  }
}
