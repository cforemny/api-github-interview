package com.dbs.github.controller;

import com.dbs.github.dto.GitHubApiResponseDto;
import com.dbs.github.model.GitHubRepositoryDetail;
import com.dbs.github.service.CacheService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static com.dbs.github.TestConstants.OWNER;
import static com.dbs.github.TestConstants.REPOSITORY_NAME;
import static com.dbs.github.TestUtils.createGitHubApiResponseDto;
import static com.dbs.github.TestUtils.createdGitHubRepositoryDetail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GitHubRepositoryControllerTest {

    private static final String GITHUB_API_URL = "https://api.github.com/repos/" + OWNER + "/" + REPOSITORY_NAME;
    private static final String LOCAL_API_URL = "http://localhost:8080/repositories/" + OWNER + "/" + REPOSITORY_NAME;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;
    @SpyBean
    private CacheService cacheService;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldGetResponseFromCApiThenFromCache() throws Exception {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        GitHubRepositoryDetail gitHubRepositoryDetail = createdGitHubRepositoryDetail();
        when(restTemplate.getForObject(GITHUB_API_URL, GitHubApiResponseDto.class))
                .thenReturn(createGitHubApiResponseDto());

        this.mockMvc.perform(get(LOCAL_API_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(gitHubRepositoryDetail)));

        this.mockMvc.perform(get(LOCAL_API_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(gitHubRepositoryDetail)));

        verify(cacheService, times(2)).getFromCache(OWNER, REPOSITORY_NAME);
        verify(cacheService).addToCache(any(GitHubRepositoryDetail.class));
    }
}