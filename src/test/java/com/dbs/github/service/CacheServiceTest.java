package com.dbs.github.service;

import com.dbs.github.exception.CacheException;
import com.dbs.github.model.GitHubRepositoryDetail;
import com.dbs.github.repository.GitHubDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dbs.github.TestConstants.OWNER;
import static com.dbs.github.TestConstants.REPOSITORY_NAME;
import static com.dbs.github.TestUtils.createdGitHubRepositoryDetail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheServiceTest {

    @Mock
    private GitHubDetailsRepository gitHubDetailsRepository;
    @InjectMocks
    private CacheService cacheService;

    @Test
    void shouldAddGitHubRepositoryDetailToCache() {
        GitHubRepositoryDetail gitRepositoryDetail = createdGitHubRepositoryDetail();

        cacheService.addToCache(gitRepositoryDetail);

        verify(gitHubDetailsRepository).save(gitRepositoryDetail);
    }

    @Test
    void shouldGetGitHubRepositoryDetailFromCache() {
        GitHubRepositoryDetail gitRepositoryDetail = createdGitHubRepositoryDetail();
        when(gitHubDetailsRepository.findByFullName(OWNER + "/" + REPOSITORY_NAME)).thenReturn(gitRepositoryDetail);

        GitHubRepositoryDetail result = cacheService.getFromCache(OWNER, REPOSITORY_NAME);

        assertEquals(gitRepositoryDetail, result);
    }

    @Test
    void shouldNotFindGitHubRepositoryDetailInCache() {
        GitHubRepositoryDetail gitRepositoryDetail = createdGitHubRepositoryDetail();
        when(gitHubDetailsRepository.findByFullName(OWNER + "/" + REPOSITORY_NAME)).thenReturn(null);

        GitHubRepositoryDetail result = cacheService.getFromCache(OWNER, REPOSITORY_NAME);

        assertNull(result);
    }

    @Test
    void shouldThrowCacheExceptionWhenGetFromCache() {
        when(gitHubDetailsRepository.findByFullName(OWNER + "/" + REPOSITORY_NAME)).thenThrow(new RuntimeException("error"));

        assertThrows(CacheException.class, () -> cacheService.getFromCache(OWNER, REPOSITORY_NAME));
    }

    @Test
    void shouldThrowCacheExceptionWhenAddToCache() {
        GitHubRepositoryDetail gitHubRepositoryDetail = createdGitHubRepositoryDetail();
        when(gitHubDetailsRepository.save(gitHubRepositoryDetail)).thenThrow(new RuntimeException("error"));

        assertThrows(CacheException.class, () -> cacheService.addToCache(gitHubRepositoryDetail));
    }
}