package com.anktech.workout_tracker.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private int sets;
    private int repetitions;
    private float weight;
    private String name;
    private String description;
    private String category;
    private String muscleGroup;
}
