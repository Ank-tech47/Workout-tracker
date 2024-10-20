package com.anktech.workout_tracker.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDTO {
    private String workOutId;
    private LocalDate date;
    private String comments;
    private List<ExerciseDTO> exercises;


}
