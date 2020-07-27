package com.galvanize.pethealthtrackerbackend.repository;

import com.galvanize.pethealthtrackerbackend.models.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetRepository extends MongoRepository<Pet, String> {
}
