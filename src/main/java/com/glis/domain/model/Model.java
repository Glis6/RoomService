package com.glis.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    /**
     * The id of the object.
     */
    public String id;

    /**
     * @param id The id of the object.
     * @param <T> The type of the object.
     * @return A new instance of the object.
     */
    @SuppressWarnings("unchecked")
    public <T extends Model> T withId(@NonNull final String id) {
        this.id = id;
        return (T) this;
    }
}