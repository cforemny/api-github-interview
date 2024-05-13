package com.dbs.github.service;

import com.dbs.github.connector.GitHubApiConnector;
import com.dbs.github.model.GitHubRepositoryDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dbs.github.TestConstants.OWNER;
import static com.dbs.github.TestConstants.REPOSITORY_NAME;
import static com.dbs.github.TestUtils.createdGitHubRepositoryDetail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubRepositoryServiceTest {


    @Mock
    private GitHubApiConnector gitHubApiConnector;
    @Mock
    private CacheService cacheService;
    @InjectMocks
    private GitHubRepositoryService gitHubRepositoryService;

    @Test
    void shouldGetGitHubRepositoryDetailFromApiAndAddToCache() {
        GitHubRepositoryDetail gitRepositoryDetail = createdGitHubRepositoryDetail();
        when(cacheService.getFromCache(OWNER, REPOSITORY_NAME)).thenReturn(null);
        when(gitHubApiConnector.getGitRepositoryDetails(OWNER, REPOSITORY_NAME)).thenReturn(gitRepositoryDetail);

        GitHubRepositoryDetail result = gitHubRepositoryService.getGitHubRepositoryDetailsByOwnerAndName(OWNER, REPOSITORY_NAME);

        assertEquals(gitRepositoryDetail, result);
        verify(cacheService).addToCache(gitRepositoryDetail);
    }

    @Test
    void shouldGetGitHubRepositoryDetailFromCache() {
        GitHubRepositoryDetail gitRepositoryDetail = createdGitHubRepositoryDetail();
        when(cacheService.getFromCache(OWNER, REPOSITORY_NAME)).thenReturn(gitRepositoryDetail);

        GitHubRepositoryDetail result = gitHubRepositoryService.getGitHubRepositoryDetailsByOwnerAndName(OWNER, REPOSITORY_NAME);

        assertEquals(gitRepositoryDetail, result);
        verifyNoInteractions(gitHubApiConnector);
    }
}