package com.anktech.workout_tracker.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private String muscleGroup;

    public Exercise(String name, String description, String category, String muscleGroup) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.muscleGroup = muscleGroup;
    }

    // Getters and setters
}
