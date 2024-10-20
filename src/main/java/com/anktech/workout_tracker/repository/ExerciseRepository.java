package com.anktech.workout_tracker.repository;

import com.anktech.workout_tracker.collection.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExerciseRepository extends MongoRepository<Exercise, String> {
}

