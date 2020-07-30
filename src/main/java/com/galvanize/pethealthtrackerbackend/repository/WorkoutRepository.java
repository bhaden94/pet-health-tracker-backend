package com.galvanize.pethealthtrackerbackend.repository;

import com.galvanize.pethealthtrackerbackend.models.Workout;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WorkoutRepository extends MongoRepository<Workout, String> {
    List<Workout> findByType(String type);

    @Query("{'pet.id': ?0}")
    List<Workout> findByPetId(String id);
    @Query("{'pet.name': ?0}")
    List<Workout> findByPetName(String name);

}
