package com.anktech.workout_tracker.repository;

import com.anktech.workout_tracker.collection.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutRepository extends MongoRepository<Workout, String> {
    List<Workout> findByUserIdOrderByDateAsc(String userId);

    @Query("{ 'userId': ?0, 'date': { $gte: ?1, $lte: ?2 } }")
    List<Workout> findWorkoutsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate);

}
