package com.anktech.workout_tracker.controller;

import com.anktech.workout_tracker.collection.WorkOutResponse;
import com.anktech.workout_tracker.collection.Workout;
import com.anktech.workout_tracker.collection.WorkoutDTO;
import com.anktech.workout_tracker.collection.WorkoutReportDTO;
import com.anktech.workout_tracker.sevice.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
    @RequestMapping("/api/workouts")
    public class WorkoutController {
        @Autowired
        private WorkoutService workoutService;

        @Operation(summary = "Create a new workout", description = "Allows a user to create a workout plan")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Workout created successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid input")
        })
        @PostMapping("/createWorkout/{userId}")
        public ResponseEntity<Workout> createWorkout(@PathVariable String userId, @RequestBody Workout workout) {
            workout.setUserId(userId);
            Workout createdWorkout = workoutService.createWorkout(workout);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkout);
        }

        @Operation(summary = "List all workouts for a user", description = "Returns a list of workouts for a specific user")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Workouts retrieved successfully"),
                @ApiResponse(responseCode = "404", description = "No WorkOut found")
        })
        @GetMapping("/getAllWorkouts/{userId}")
        public ResponseEntity<WorkOutResponse> getAllWorkoutsByUserId(@PathVariable String userId) {
            List<WorkoutDTO> workouts = workoutService.listWorkouts(userId);
            if (workouts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            else {
                WorkOutResponse workOutResponse = new WorkOutResponse();
                workOutResponse.setUserId(userId);
                workOutResponse.setWorkouts(workouts);
                return ResponseEntity.ok(workOutResponse);
            }
        }
        @Operation(summary = "Update an existing workout", description = "Allows a user to update an existing workout")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Workout updated successfully"),
                @ApiResponse(responseCode = "404", description = "Workout not found")
        })
        @PutMapping("/updateWorkout/{workoutId}")
        public ResponseEntity<Workout> updateWorkout(@PathVariable String workoutId, @RequestBody Workout workout) {
            Workout updatedWorkout = workoutService.updateWorkout(workoutId, workout);
            if (updatedWorkout == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(updatedWorkout);
        }
        @Operation(summary = "Delete a workout", description = "Allows a user to delete a workout")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Workout deleted successfully"),
                @ApiResponse(responseCode = "404", description = "Workout not found")
        })
        @DeleteMapping("/deleteWorkout/{workoutId}")
        public ResponseEntity<Void> deleteWorkout(@PathVariable String workoutId) {
            boolean isDeleted = workoutService. deleteWorkout(workoutId);
            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok().build();
        }

        @Operation(summary = "Generate Workout Report", description = "Generates a report of workouts for a specified user in a date range.")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of the workout report"),
            @ApiResponse(responseCode = "404", description = "No workouts found for the specified user in the date range"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
        })
        @GetMapping("/reports")
        public ResponseEntity<WorkoutReportDTO> getWorkoutReport(
                @RequestParam(required = false) String startDate,
                @RequestParam(required = false) String endDate,
                @RequestParam String userId) {

            try {
                WorkoutReportDTO report = workoutService.generateWorkoutReport(startDate, endDate, userId);
                return ResponseEntity.ok(report);
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if no workouts found
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return 500 for other exceptions
            }
        }
}




