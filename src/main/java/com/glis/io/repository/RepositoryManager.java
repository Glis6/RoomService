package com.glis.io.repository;

import com.glis.domain.model.ClientIdentity;
import com.glis.domain.model.Profile;
import com.glis.log.model.Log;

/**
 * @author Glis
 */
public interface RepositoryManager {
    /**
     * @return The {@link Repository} that handles with all traffic around {@link Profile}s.
     */
    ProfileRepository getProfileRepository();

    /**
     * @return The {@link LogRepository} that handles with all traffic around {@link Log}s.
     */
    LogRepository getLogRepository();

    /**
     * @return The {@link ClientIdentityRepository} that handles with all traffic around {@link ClientIdentity}s.
     */
    ClientIdentityRepository getClientIdentityRepository();
}
