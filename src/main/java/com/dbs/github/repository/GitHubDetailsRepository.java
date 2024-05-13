package com.dbs.github.repository;

import com.dbs.github.model.GitHubRepositoryDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitHubDetailsRepository extends CrudRepository<GitHubRepositoryDetail, Long> {

    GitHubRepositoryDetail findByFullName(String fullName);
}
