package com.glis.led;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Glis
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RgbValues {
    /**
     * The colors that the RGB contains.
     */
    private int red, green, blue;
}
