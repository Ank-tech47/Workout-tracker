package com.anktech.workout_tracker.util;

import com.anktech.workout_tracker.collection.Exercise;
import com.anktech.workout_tracker.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) throws Exception {
        if (exerciseRepository.count() == 0) { // Only seed if the database is empty
            List<Exercise> exercises = Arrays.asList(
                    new Exercise("Push-Up", "A bodyweight exercise for chest, shoulders, and triceps.", "Strength", "Chest"),
                    new Exercise("Squat", "A lower-body strength exercise that targets the thighs, hips, and buttocks.", "Strength", "Legs"),
                    new Exercise("Plank", "An isometric core strength exercise that involves maintaining a position similar to a push-up.", "Strength", "Core"),
                    new Exercise("Running", "A cardiovascular exercise that improves stamina and endurance.", "Cardio", "Legs"),
                    new Exercise("Jumping Jacks", "A full-body workout that improves cardiovascular endurance.", "Cardio", "Full Body"),
                    new Exercise("Yoga Stretch", "A flexibility exercise focusing on stretching and balance.", "Flexibility", "Full Body"),
                    new Exercise("Deadlift", "A weightlifting exercise that targets the back, glutes, and hamstrings.", "Strength", "Back")
            );

            exerciseRepository.saveAll(exercises);
            System.out.println("Exercise data seeded.");
        } else {
            System.out.println("Database already has exercise data.");
        }
    }
}