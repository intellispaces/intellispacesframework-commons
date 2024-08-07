package tech.intellispaces.commons.function;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import tech.intellispaces.commons.TestFunctionSamples;
import tech.intellispaces.commons.exception.CoveredCheckedException;
import tech.intellispaces.commons.exception.PossibleViolationException;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for class {@link RunnableFunctions}
 */
public class RunnableFunctionsTest {

  @Test
  public void testUncover_whenRunnableAndExactMatch() {
    // When
    ThrowableAssert.ThrowingCallable callable = () -> RunnableFunctions.runAndUncoverIfCovered(
        () -> Stream.of("a", "", "b")
          .map(Functions.coveredThrowableFunction(TestFunctionSamples::throwingCheckedFunction))
          .toList(),
        PossibleViolationException.class);

    // Then
    assertThatThrownBy(callable).isExactlyInstanceOf(PossibleViolationException.class);
  }

  @Test
  public void testUncover_whenRunnableAndBaseExceptionSpecified() {
    // When
    ThrowableAssert.ThrowingCallable callable = () -> RunnableFunctions.runAndUncoverIfCovered(
        () -> Stream.of("a", "", "b")
          .map(Functions.coveredThrowableFunction(TestFunctionSamples::throwingCheckedFunction))
          .toList(),
        Exception.class);

    // Then
    assertThatThrownBy(callable).isExactlyInstanceOf(PossibleViolationException.class);
  }

  @Test
  public void testUncover_whenRunnableAndOtherExceptionSpecified() {
    // When
    ThrowableAssert.ThrowingCallable callable = () -> RunnableFunctions.runAndUncoverIfCovered(
        () -> Stream.of("a", "", "b")
          .map(Functions.coveredThrowableFunction(TestFunctionSamples::throwingCheckedFunction))
          .toList(),
        IOException.class);

    // Then
    assertThatThrownBy(callable).isExactlyInstanceOf(CoveredCheckedException.class)
        .hasCauseExactlyInstanceOf(PossibleViolationException.class);
  }
}
