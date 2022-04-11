package org.pitest.mutationtest.statistics;

@FunctionalInterface
public interface MutationPercentageComputation {
  long calculate(long totalMutation, long totalDetectedMutation);
}
