package com.anktech.workout_tracker.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutReportDTO {

    private String userId;
    private int totalWorkouts;
    private int averageWorkoutDuration;
    private String mostFrequentExercise;
    private Map<String, Integer> muscleGroupSummary;

    // Getters and Setters
}

