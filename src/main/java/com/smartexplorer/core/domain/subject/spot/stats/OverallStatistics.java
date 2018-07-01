package com.smartexplorer.core.domain.subject.spot.stats;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

/**
 * @Author
 * Karol Meksuła
 * 30-06-2018
 * */

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OverallStatistics {
    private long allVisits;
    private long allOpinions;
    private long allSearches;
    private long spotMakers;
    private long explorers;
    private long spots;

    public OverallStatistics(final OverallStatisticsBuilder builder) {
        this.allVisits = builder.allVisits;
        this.allOpinions = builder.allOpinions;
        this.allSearches = builder.allSearches;
        this.spotMakers = builder.spotMakers;
        this.explorers = builder.explorers;
        this.spots = builder.spots;
    }

    public static class OverallStatisticsBuilder {
        private long allVisits;
        private long allOpinions;
        private long allSearches;
        private long spotMakers;
        private long explorers;
        private long spots;

        public OverallStatisticsBuilder allVisits(final long allVisits) {
            this.allVisits = allVisits;
            return this;
        }

        public OverallStatisticsBuilder allOpinions(final long allOpinions) {
            this.allOpinions = allOpinions;
            return this;
        }

        public OverallStatisticsBuilder allSearches(final long allSearches) {
            this.allSearches = allSearches;
            return this;
        }

        public OverallStatisticsBuilder spotMakers(final long spotMakers) {
            this.spotMakers = spotMakers;
            return this;
        }

        public OverallStatisticsBuilder explorers(final long explorers) {
            this.explorers = explorers;
            return this;
        }

        public OverallStatisticsBuilder spots(final long spots) {
            this.spots = spots;
            return this;
        }

        public OverallStatistics build() {
            return new OverallStatistics(this);
        }

    }

}
