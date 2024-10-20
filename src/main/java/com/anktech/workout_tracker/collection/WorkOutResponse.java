package com.anktech.workout_tracker.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOutResponse {
    private String userId;
    private List<WorkoutDTO> workouts;

}
