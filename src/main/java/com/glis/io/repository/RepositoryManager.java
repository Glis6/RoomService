package com.glis.io.repository;

import com.glis.domain.model.Profile;

/**
 * @author Glis
 */
public interface RepositoryManager {
    /**
     * @return The {@link Repository} that handles with all traffic around {@link Profile}s.
     */
    ProfileRepository getProfileRepository();
}
