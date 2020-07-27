package com.galvanize.pethealthtrackerbackend.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pets")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pet {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String size; // small, medium, large, extra-large


}
