package com.anktech.workout_tracker.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseStats {
    private int totalSets;
    private int totalRepetitions;
    private int totalWeight;
    public void incrementSets(int sets) {
        this.totalSets += sets;
    }

    public void incrementRepetitions(int repetitions) {
        this.totalRepetitions += repetitions;
    }

    public void incrementWeight(int weight) {
        this.totalWeight += weight;
    }

}

