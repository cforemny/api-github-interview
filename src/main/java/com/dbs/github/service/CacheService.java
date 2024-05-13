package com.dbs.github.service;

import com.dbs.github.exception.CacheException;
import com.dbs.github.model.GitHubRepositoryDetail;
import com.dbs.github.repository.GitHubDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService {

    private static final String FULL_NAME_PATTERN = "%s/%s";
    private final GitHubDetailsRepository gitHubDetailsRepository;

    public void addToCache(GitHubRepositoryDetail gitRepositoryDetail) {
        try {
            log.info("Adding new GitRepositoryDetails to cache.");
            gitHubDetailsRepository.save(gitRepositoryDetail);
            log.info("Successfully added new GitRepositoryDetails to cache.");
        } catch (Exception ex) {
            throw new CacheException("Error occurs while adding new GitRepositoryDetails to cache.");
        }
    }

    public GitHubRepositoryDetail getFromCache(String owner, String repositoryName) {
        try {
            log.info("Searching GitHubRepositoryDetail for owner: {} and repository name: {} in cache.", owner, repositoryName);
            GitHubRepositoryDetail gitHubRepositoryDetail = gitHubDetailsRepository.findByFullName(String.format(FULL_NAME_PATTERN, owner, repositoryName));
            log.info("Found GitHubRepositoryDetail inf cache for owner: {} and repository name: {}", owner, repositoryName);

            return gitHubRepositoryDetail;
        } catch (Exception ex) {
            throw new CacheException("Error occurs while searching for  GitRepositoryDetails in cache.");
        }
    }
}
