package com.glis.domain.model;

import com.glis.led.RgbValues;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    public final static Profile EMPTY_PROFILE = new Profile();

    /**
     * All tags associated with this profile.
     */
    private String tag;

    /**
     * All songs that get played when this profile is picked.
     */
    private List<String> spotifySongIdentifiers;

    /**
     * The {@link RgbValues} for the profile.
     */
    private RgbValues rgbValues;

    /**
     * A default constructor.
     */
    public Profile() {
    }
}
