package com.glis.domain.model;

import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import lombok.Data;
import lombok.NonNull;

@Data
@IgnoreExtraProperties
public class Model {
    /**
     * The id of the object.
     */
    @Exclude
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