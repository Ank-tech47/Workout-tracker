package com.anktech.workout_tracker.controller;

import com.anktech.workout_tracker.collection.Exercise;
import com.anktech.workout_tracker.sevice.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;


    @Operation(summary = "Retrieve all exercises", description = "Fetches a list of all available exercises")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercises retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No exercises found")
    })
    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    @Operation(summary = "Add a new exercise", description = "Creates a new exercise entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exercise created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid exercise input")
    })
    @PostMapping
    public ResponseEntity<Exercise> addExercise(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.addExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise);
    }
}

