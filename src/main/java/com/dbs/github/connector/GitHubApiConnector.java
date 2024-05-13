package com.dbs.github.connector;


import com.dbs.github.dto.GitHubApiResponseDto;
import com.dbs.github.exception.GitHubApiConnectionException;
import com.dbs.github.model.GitHubRepositoryDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Component
@Slf4j
public class GitHubApiConnector {

    private final String gitHubUrl;
    private final RestTemplate restTemplate;

    public GitHubApiConnector(@Value("${external-api.github.repo-url}") String gitHubUrl,
                              RestTemplate restTemplate) {
        this.gitHubUrl = gitHubUrl;
        this.restTemplate = restTemplate;
    }

    public GitHubRepositoryDetail getGitRepositoryDetails(String owner, String repositoryName) {
        try {
            log.info("Calling git hub api for owner: {} and repository name: {}", owner, repositoryName);
            GitHubApiResponseDto response = restTemplate.getForObject(format("%s%s/%s", gitHubUrl, owner, repositoryName), GitHubApiResponseDto.class);
            log.info("Successfully called git hub api for owner: {} and repository name: {}", owner, repositoryName);

            return mapToGitHubRepositoryDetail(response);
        } catch (Exception ex) {
            throw new GitHubApiConnectionException(format("Error occurs while calling git hub api for owner: %s and repository name: %s", owner, repositoryName));
        }
    }

    private GitHubRepositoryDetail mapToGitHubRepositoryDetail(GitHubApiResponseDto gitHubApiResponseDto) {
        return gitHubApiResponseDto == null ? GitHubRepositoryDetail.builder().build() :
                GitHubRepositoryDetail.builder()
                        .stars(gitHubApiResponseDto.getStars())
                        .cloneUrl(gitHubApiResponseDto.getCloneUrl())
                        .description(gitHubApiResponseDto.getDescription())
                        .fullName(gitHubApiResponseDto.getFullName())
                        .createdAt(gitHubApiResponseDto.getCreatedAt())
                        .build();
    }
}
