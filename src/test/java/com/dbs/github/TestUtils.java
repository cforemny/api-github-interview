package com.dbs.github;

import com.dbs.github.dto.GitHubApiResponseDto;
import com.dbs.github.model.GitHubRepositoryDetail;
import lombok.experimental.UtilityClass;

import static com.dbs.github.TestConstants.*;

@UtilityClass
public class TestUtils {

    public static GitHubRepositoryDetail createdGitHubRepositoryDetail() {
        return GitHubRepositoryDetail.builder()
                .id(null)
                .createdAt(CREATED_AT)
                .stars(STARS)
                .description(DESCRIPTION)
                .fullName(FULL_NAME)
                .cloneUrl(CLONE_URL)
                .build();
    }

    public static GitHubApiResponseDto createGitHubApiResponseDto() {
        return GitHubApiResponseDto.builder()
                .fullName(FULL_NAME)
                .createdAt(CREATED_AT)
                .stars(STARS)
                .cloneUrl(CLONE_URL)
                .description(DESCRIPTION)
                .build();
    }

}
