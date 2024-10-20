package com.anktech.workout_tracker.sevice;


import com.anktech.workout_tracker.collection.*;
import com.anktech.workout_tracker.repository.ExerciseRepository;
import com.anktech.workout_tracker.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;

    public Workout createWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public List<WorkoutDTO> listWorkouts(String userId) {
        List<Workout> workouts= workoutRepository.findByUserIdOrderByDateAsc(userId);
        return workouts.stream().map(workout -> {
            WorkoutDTO workoutDTO = new WorkoutDTO();
            workoutDTO.setWorkOutId(workout.getId());
            workoutDTO.setDate(workout.getDate());
            workoutDTO.setComments(workout.getComments());
            List<ExerciseDTO> exerciseDTOS=workout.getExercises().stream().map(workoutExercise ->{
                ExerciseDTO exerciseDTO = new ExerciseDTO();
                Optional<Exercise> exerciseOptional = exerciseRepository.findById(workoutExercise.getExerciseId());
                exerciseOptional.ifPresent(exercise -> {
                    exerciseDTO.setName(exercise.getName());
                    exerciseDTO.setDescription(exercise.getDescription());
                    exerciseDTO.setCategory(exercise.getCategory());
                    exerciseDTO.setMuscleGroup(exercise.getMuscleGroup());
                });
                exerciseDTO.setSets(workoutExercise.getSets());
                exerciseDTO.setRepetitions(workoutExercise.getRepetitions());
                exerciseDTO.setWeight(workoutExercise.getWeight());
                return exerciseDTO;
            }).collect(Collectors.toList());;
            workoutDTO.setExercises(exerciseDTOS);
            return workoutDTO;
        }).collect(Collectors.toList());
    }

    public Workout updateWorkout(String workoutId, Workout workout) {
        Optional<Workout> existingWorkoutOpt = workoutRepository.findById(workoutId);
        if (!existingWorkoutOpt.isPresent()) {
            return null; // Workout not found
        }

        Workout existingWorkout = existingWorkoutOpt.get();
        existingWorkout.setDate(workout.getDate());
        existingWorkout.setComments(workout.getComments());
        existingWorkout.setExercises(workout.getExercises());

        return workoutRepository.save(existingWorkout);
    }

    public boolean deleteWorkout(String workoutId) {
        workoutRepository.deleteById(workoutId);
        return true;
    }
    public WorkoutReportDTO generateWorkoutReport(String startDate, String endDate, String userId) {
        // Parse dates
        LocalDate start = (startDate != null) ? LocalDate.parse(startDate) : LocalDate.MIN;
        LocalDate end = (endDate != null) ? LocalDate.parse(endDate) : LocalDate.now();

        // Fetch workouts for the user in the date range
        List<Workout> workouts = workoutRepository.findWorkoutsByUserIdAndDateRange(userId, start, end);

        if (workouts.isEmpty()) {
            throw new NoSuchElementException("No workouts found for user ID: " + userId + " in the specified date range.");
        }

        // Generate report
        WorkoutReportDTO report = new WorkoutReportDTO();
        report.setUserId(userId);
        report.setTotalWorkouts(workouts.size());

        int totalDuration = 0;
        Map<String, ExerciseStats> exerciseStatsMap = new HashMap<>();
        Map<String, Integer> muscleGroupMap = new HashMap<>();

        for (Workout workout : workouts) {
            totalDuration += workout.getExercises().size() * 15; // Assuming each exercise takes 15 minutes

            for (WorkoutExercise exercise : workout.getExercises()) {
                Exercise exercise1 = exerciseRepository.findById(exercise.getExerciseId())
                        .orElseThrow(() -> new NoSuchElementException("Exercise not found for ID: " + exercise.getExerciseId()));

                ExerciseStats stats = exerciseStatsMap.getOrDefault(exercise1.getName(), new ExerciseStats());
                stats.incrementSets(exercise.getSets());
                stats.incrementRepetitions(exercise.getRepetitions());
                stats.incrementWeight((int) exercise.getWeight());
                exerciseStatsMap.put(exercise1.getName(), stats);

                muscleGroupMap.put(exercise1.getMuscleGroup(),
                        muscleGroupMap.getOrDefault(exercise1.getMuscleGroup(), 0) + 1);
            }
        }

        if (!workouts.isEmpty()) {
            report.setAverageWorkoutDuration(totalDuration / workouts.size());
        }

        report.setMostFrequentExercise(exerciseStatsMap.entrySet().stream()
                .max(   Comparator.comparingInt(e -> e.getValue().getTotalSets()))
                .map(Map.Entry::getKey)
                .orElse("N/A"));

        report.setMuscleGroupSummary(muscleGroupMap);

        return report;
    }
}


