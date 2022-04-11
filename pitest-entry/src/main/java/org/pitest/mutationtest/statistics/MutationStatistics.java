/*
 * Copyright 2011 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.statistics;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class MutationStatistics {
  private final Iterable<Score> scores;
  private final long totalMutations;
  private final long numberOfTestsRun;
  private final long totalDetected;
  private final long totalWithCoverage;

  public MutationStatistics(Iterable<Score> scores, long totalMutations,
                            long totalDetected, long totalWithCoverage, long numberOfTestsRun) {
    this.scores = scores;
    this.totalMutations = totalMutations;
    this.totalDetected = totalDetected;
    this.numberOfTestsRun = numberOfTestsRun;
    this.totalWithCoverage = totalWithCoverage;
  }

  public Iterable<Score> getScores() {
    return this.scores;
  }

  public long getTotalMutations() {
    return this.totalMutations;
  }

  public long getTotalDetectedMutations() {
    return this.totalDetected;
  }

  public long getTotalMutationsWithCoverage() {
    return this.totalWithCoverage;
  }

  private long getTotalMutationsWithoutCoverage() {
    return this.totalMutations - this.totalWithCoverage;
  }

  public long getTotalSurvivingMutations() {
    return getTotalMutations() - getTotalDetectedMutations();
  }

  public long getPercentageDetected(boolean roundUpMutation) {
    if (getTotalMutations() == 0) {
      return 100;
    }

    if (getTotalDetectedMutations() == 0) {
      return 0;
    }

    if (getTotalMutations() == getTotalDetectedMutations()) {
      return 100;
    }

    MutationPercentageComputation computation;
    if (roundUpMutation) {
      computation = (long x, long y)
              -> (Math.round((100f / x) * y));
    } else {
      computation = (long x, long y)
              -> (long) ((100f / x) * y);
    }

    return computation.calculate(getTotalMutations(),
            getTotalDetectedMutations());
  }

  public void report(final PrintStream out, boolean roundUpMutation) {
    out.println(">> Generated " + this.getTotalMutations()
        + " mutations Killed " + this.getTotalDetectedMutations() + " ("
        + this.getPercentageDetected(roundUpMutation) + "%)");
    out.println(">> Mutations with no coverage " + this.getTotalMutationsWithoutCoverage()
            + ". Test strength " + this.getTestStrength() + "%");
    out.println(">> Ran " + this.numberOfTestsRun + " tests ("
        + getTestsPerMutation() + " tests per mutation)");

    out.println("\nPitest development is currently supported by GroupCDG.");
    out.println("Enhanced functionality available at https://pitest.groupcdg.com/");
  }

  private String getTestsPerMutation() {
    if (this.getTotalMutations() == 0) {
      return "0";
    }

    final float testsPerMutation = this.numberOfTestsRun
        / (float) this.getTotalMutations();
    return new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH))
        .format(testsPerMutation);
  }

  public long getTestStrength() {
    if (getTotalMutations() == 0) {
      return 100;
    }

    if (getTotalMutationsWithCoverage() == 0) {
      return 0;
    }

    return Math.round((100f / getTotalMutationsWithCoverage())
            * getTotalDetectedMutations());
  }
}
