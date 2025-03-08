package org.acme.service;

import java.util.List;

import org.acme.model.RepositoryDto;

import io.smallrye.mutiny.Uni;

public interface GitHubService {

    Uni<List<RepositoryDto>> getUserRepositories(String username);

}
