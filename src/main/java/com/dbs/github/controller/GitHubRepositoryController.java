package com.dbs.github.controller;

import com.dbs.github.dto.GitHubRepositoryResponseDto;
import com.dbs.github.service.GitHubRepositoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GitHubRepositoryController {

    private final GitHubRepositoryService gitHubService;
    private final ModelMapper modelMapper;

    @GetMapping("/repositories/{owner}/{repository-name}")
    public GitHubRepositoryResponseDto getGitHubRepositoryDetailsByOwnerAndName(@PathVariable("owner") String owner,
                                                                                @PathVariable("repository-name") String repositoryName) {
        return modelMapper.map(gitHubService.getGitHubRepositoryDetailsByOwnerAndName(owner, repositoryName), GitHubRepositoryResponseDto.class);
    }
}
