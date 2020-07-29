package com.galvanize.pethealthtrackerbackend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "workouts")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Workout {

   @Id
   private String id;
   @NonNull
   private String type;

   @JsonProperty("date_time")
   @NonNull
   private Date dateTime;

   @NonNull
   private int intensity;           // 1-3, low-high

   @JsonProperty("calories_burned")
   private int caloriesBurned;

   private double distance;         // miles

   @JsonProperty("workout_duration")
   @NonNull
   private int workoutDuration;

   @NonNull
   private Pet pet;

}
