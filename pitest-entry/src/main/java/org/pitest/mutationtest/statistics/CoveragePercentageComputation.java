package org.pitest.mutationtest.statistics;

@FunctionalInterface
public interface CoveragePercentageComputation {
  int calculate(long numberOfLinesCovered, long numberOfLines);
}
