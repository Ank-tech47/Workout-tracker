package com.anktech.workout_tracker.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutExercise {
    private String exerciseId;
    private int sets;
    private int repetitions;
    private float weight;
}
