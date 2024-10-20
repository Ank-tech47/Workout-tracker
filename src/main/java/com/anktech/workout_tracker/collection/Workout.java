package com.anktech.workout_tracker.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Document(collection = "workouts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Workout {
    @Id
    private String id;
    private String userId;
    private LocalDate date;
    private String comments;
    private
    List<WorkoutExercise> exercises;


}
