package com.dbs.github.connector;

import com.dbs.github.dto.GitHubApiResponseDto;
import com.dbs.github.exception.GitHubApiConnectionException;
import com.dbs.github.model.GitHubRepositoryDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static com.dbs.github.TestConstants.OWNER;
import static com.dbs.github.TestConstants.REPOSITORY_NAME;
import static com.dbs.github.TestUtils.createGitHubApiResponseDto;
import static com.dbs.github.TestUtils.createdGitHubRepositoryDetail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GitHubApiConnectorTest {

    private static final String GITHUB_API_URL = "http://www.github-test.com/";
    @Mock
    private RestTemplate restTemplate;

    private GitHubApiConnector gitHubApiConnector;

    @BeforeEach
    void setUp() {
        gitHubApiConnector = new GitHubApiConnector(GITHUB_API_URL, restTemplate);
    }

    @Test
    void shouldGetGitHubApiResponseDto() {
        when(restTemplate.getForObject(GITHUB_API_URL + OWNER + "/" + REPOSITORY_NAME, GitHubApiResponseDto.class)).thenReturn(createGitHubApiResponseDto());

        GitHubRepositoryDetail result = gitHubApiConnector.getGitRepositoryDetails(OWNER, REPOSITORY_NAME);

        assertEquals(createdGitHubRepositoryDetail(), result);
    }

    @Test
    void shouldReturnNullIfThereShouldReturnEmptyGitHubRepositoryDetailIfNotFound() {
        when(restTemplate.getForObject(GITHUB_API_URL + OWNER + "/" + REPOSITORY_NAME, GitHubApiResponseDto.class)).thenReturn(null);

        GitHubRepositoryDetail result = gitHubApiConnector.getGitRepositoryDetails(OWNER, REPOSITORY_NAME);

        assertEquals(GitHubRepositoryDetail.builder().build(), result);
    }

    @Test
    void shouldThrowGitHubApiConnectionException() {
        when(restTemplate.getForObject(GITHUB_API_URL + OWNER + "/" + REPOSITORY_NAME, GitHubApiResponseDto.class)).thenThrow(new RuntimeException("connection exception"));

        assertThrows(GitHubApiConnectionException.class, () -> gitHubApiConnector.getGitRepositoryDetails(OWNER, REPOSITORY_NAME));
    }


}