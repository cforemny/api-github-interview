package com.dbs.github.service;

import com.dbs.github.connector.GitHubApiConnector;
import com.dbs.github.model.GitHubRepositoryDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubRepositoryService {

    private final GitHubApiConnector gitHubApiConnector;
    private final CacheService cacheService;

    public GitHubRepositoryDetail getGitHubRepositoryDetailsByOwnerAndName(String owner,
                                                                           String repositoryName) {
        GitHubRepositoryDetail cachedGitHubRepositoryDetail = cacheService.getFromCache(owner, repositoryName);
        if (cachedGitHubRepositoryDetail != null) {
            return cachedGitHubRepositoryDetail;
        }
        GitHubRepositoryDetail gitRepositoryDetail = gitHubApiConnector.getGitRepositoryDetails(owner, repositoryName);
        cacheService.addToCache(gitRepositoryDetail);

        return gitRepositoryDetail;
    }
}
