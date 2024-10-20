package com.anktech.workout_tracker.sevice;

import com.anktech.workout_tracker.collection.Exercise;
import com.anktech.workout_tracker.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }
    public Optional<Exercise> getExerciseById(String id){
        return exerciseRepository.findById(id);
    }

    public Exercise addExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
}

