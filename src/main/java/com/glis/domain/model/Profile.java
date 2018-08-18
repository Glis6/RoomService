package com.glis.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * @author Glis
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Profile extends Model {
    /**
     * An empty profile to use when there is no profile available.
     */
    public final static Profile EMPTY_PROFILE = new Profile("", Collections.emptyList());

    /**
     * All tags associated with this profile.
     */
    private String tag;

    /**
     * All songs that get played when this profile is picked.
     */
    private List<String> spotifySongIdentifiers;

    /**
     * @param tag The tag associated with the profile.
     * @param spotifySongIdentifiers All songs that get played when this profile is picked.
     */
    private Profile(String tag, List<String> spotifySongIdentifiers) {
        this.tag = tag;
        this.spotifySongIdentifiers = spotifySongIdentifiers;
    }

    /**
     * A default constructor.
     */
    public Profile() {
    }
}
