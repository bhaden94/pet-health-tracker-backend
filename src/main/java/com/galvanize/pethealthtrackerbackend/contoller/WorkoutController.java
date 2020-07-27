package com.galvanize.pethealthtrackerbackend.contoller;

import com.galvanize.pethealthtrackerbackend.models.Pet;
import com.galvanize.pethealthtrackerbackend.models.Workout;
import com.galvanize.pethealthtrackerbackend.repository.PetRepository;
import com.galvanize.pethealthtrackerbackend.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    PetRepository petRepository;

    /************************     GET Mapping      ************************/

    // all pets
    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return this.petRepository.findAll();
    }

    // all workouts
    // can add query for workouts by type
    @GetMapping("/workouts")
    public List<Workout> getAllWorkouts(@RequestParam(required = false) String type) {
        if (type != null) {
            return this.workoutRepository.findByType(type);
        } else {
            return this.workoutRepository.findAll();
        }
    }

    // all workouts for a pet by name
    @GetMapping("/workouts/pet")
    public List<Workout> findByPetName(@RequestParam String name) {
        return this.workoutRepository.findByPetName(name);
    }

    // all workouts for a pet by id
    @GetMapping("/workouts/pet/{id}")
    public List<Workout> findByPetId(@PathVariable String id) {
        return this.workoutRepository.findByPetId(id);
    }

    // get total distance ran/walked by a certain pet id
    // example return -> {"total_distance":3.0}
    @GetMapping("/workouts/pet/{id}/total_distance")
    public Map<String, Double> getTotalDistanceTravelled(@PathVariable String id) {
        Map<String, Double> map = new HashMap<>();
        List<Workout> workouts = this.workoutRepository.findByPetId(id);
        double sum = workouts.stream().mapToDouble(workout -> workout.getDistance()).sum();
        map.put("total_distance", sum);
        return map;
    }

    // get total calories by a certain pet id
    // example return -> {"total_calories":200}
    @GetMapping("/workouts/pet/{id}/total_calories")
    public Map<String, Integer> getTotalCaloriesBurned(@PathVariable String id) {
        Map<String, Integer> map = new HashMap<>();
        List<Workout> workouts = this.workoutRepository.findByPetId(id);
        int sum = workouts.stream().mapToInt(workout -> workout.getCaloriesBurned()).sum();
        map.put("total_calories", sum);
        return map;
    }

    /************************     POST Mapping      ************************/

    // add 1 pet
    @PostMapping("/add_pet")
    public Pet addPet(@RequestBody Pet pet) {
        return this.petRepository.insert(pet);
    }

    // add list of pets
    @PostMapping("/add_pets")
    public List<Pet> addPets(@RequestBody List<Pet> pets) {
        return this.petRepository.insert(pets);
    }

    // add 1 workout
    @PostMapping("/add_workout")
    public Workout addWorkout(@RequestBody Workout workout) {
        return this.workoutRepository.insert(workout);
    }

    // add list of workouts
    @PostMapping("/add_workouts")
    public List<Workout> addWorkouts(@RequestBody List<Workout> workouts) {
        return this.workoutRepository.insert(workouts);
    }

    /************************     PUT Mapping     ************************/

    // gets Workout object from frontend and updates the corresponding
    // db element to reflect any changes
    @PutMapping("/workouts/{id}")
    public Workout updateWorkout(@PathVariable String id, @RequestBody Workout updates) {
        return this.workoutRepository.findById(id).map(workout -> {
            workout.setType(updates.getType());
            workout.setDateTime(updates.getDateTime());
            workout.setIntensity(updates.getIntensity());
            workout.setCaloriesBurned(updates.getCaloriesBurned());
            workout.setDistance(updates.getDistance());
            workout.setWorkoutDuration(updates.getWorkoutDuration());
            workout.setPet(updates.getPet());
            return this.workoutRepository.save(workout);
        }).get();
    }

    // gets Pet object from frontend and updates the corresponding
    // db element to reflect any changes
    // also updates any Workout that reference this pet to reflect updates
    @PutMapping("/pets/{id}")
    public Pet updatePet(@PathVariable String id, @RequestBody Pet updates) {
        Pet updatedPet = this.petRepository.findById(id).map(pet -> {
            pet.setName(updates.getName());
            pet.setSize(updates.getSize());
            return this.petRepository.save(pet);
        }).get();

        this.workoutRepository.findByPetId(updatedPet.getId()).stream()
                .forEach(workout -> {
                    workout.setPet(updatedPet);
                    this.workoutRepository.save(workout);
                });

        return updatedPet;
    }

    /************************     DELETE Mapping      ************************/

    // remove one pet by id
    @DeleteMapping("/remove_pet/{id}")
    public void removePet(@PathVariable String id) {
        this.petRepository.deleteById(id);
        System.out.println("Removed pet with ID " + id);
    }

    // remove one workout by id
    @DeleteMapping("/remove_workout/{id}")
    public void removeWorkout(@PathVariable String id) {
        this.workoutRepository.deleteById(id);
        System.out.println("Removed workout with ID " + id);
    }

    // clears the workouts collection
    @DeleteMapping("/workouts/clear_table")
    public void clearWorkoutsTable() {
        List<Workout> workouts = this.workoutRepository.findAll();
        workouts.stream().forEach(workout -> this.workoutRepository.deleteById(workout.getId()));
        System.out.println("workouts table cleared");
    }

    // clears the pets collection
    @DeleteMapping("/pets/clear_table")
    public void clearPetsTable() {
        List<Pet> pets = this.petRepository.findAll();
        pets.stream().forEach(pet -> this.petRepository.deleteById(pet.getId()));
        System.out.println("pets table cleared");
    }
}
