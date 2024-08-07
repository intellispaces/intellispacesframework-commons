package tech.intellispaces.commons.function;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import tech.intellispaces.commons.TestFunctionSamples;
import tech.intellispaces.commons.exception.CoveredCheckedException;
import tech.intellispaces.commons.exception.PossibleViolationException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for class {@link Functions}.
 */
public class FunctionsTests {

  @Test
  public void testCoveredThrowableFunction_whenCheckedException() {
    // When
    ThrowableAssert.ThrowingCallable callable = () -> Stream.of("a", "", "b")
        .map(Functions.coveredThrowableFunction(TestFunctionSamples::throwingCheckedFunction))
        .toList();

    // Then
    assertThatThrownBy(callable).isExactlyInstanceOf(CoveredCheckedException.class)
        .hasCauseExactlyInstanceOf(PossibleViolationException.class);
  }

  @Test
  public void testCoveredThrowableFunction_whenUncheckedException() {
    // When
    ThrowableAssert.ThrowingCallable callable = () -> Stream.of("a", "", "b")
        .map(Functions.coveredThrowableFunction(TestFunctionSamples::throwingUncheckedFunction))
        .toList();

    // Then
    assertThatThrownBy(callable).isExactlyInstanceOf(RuntimeException.class);
  }

  @Test
  public void testCoveredThrowableFunction_whenCheckedExceptionAndExceptionFactory() {
    // When
    ThrowableAssert.ThrowingCallable callable = () -> Stream.of("a", "", "b")
        .map(Functions.coveredThrowableFunction(TestFunctionSamples::throwingCheckedFunction, IllegalArgumentException::new))
        .toList();

    // Then
    assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class)
        .hasCauseExactlyInstanceOf(PossibleViolationException.class);
  }

  @Test
  public void testCoveredThrowableFunctionn_whenUncheckedExceptionAndExceptionFactory() {
    // When
    ThrowableAssert.ThrowingCallable callable = () -> Stream.of("a", "", "b")
        .map(Functions.coveredThrowableFunction(TestFunctionSamples::throwingUncheckedFunction, IllegalArgumentException::new))
        .toList();

    // Then
    assertThatThrownBy(callable).isExactlyInstanceOf(RuntimeException.class)
        .hasNoCause();
  }

}
