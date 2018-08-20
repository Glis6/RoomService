package com.glis.log.model;

import com.glis.domain.model.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * A log that logs an event that happened.
 *
 * @author Glis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Log extends Model {
    /**
     * The {@link Date} that the log was created.
     */
    private Date timestamp = new Date();

    /**
     * The type of log it is.
     */
    private String logType = getClass().getSimpleName();
}
