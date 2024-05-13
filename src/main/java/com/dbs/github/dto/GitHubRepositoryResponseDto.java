package com.dbs.github.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GitHubRepositoryResponseDto {

    private String fullName;
    private String description;
    private String cloneUrl;
    private Integer stars;
    private String createdAt;
}
