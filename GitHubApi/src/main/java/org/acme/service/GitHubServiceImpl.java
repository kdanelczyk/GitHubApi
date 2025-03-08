package org.acme.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.acme.exception.UserNotFoundException;
import org.acme.external.GitHubRepositoryResponse;
import org.acme.model.BranchDto;
import org.acme.model.RepositoryDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class GitHubServiceImpl implements GitHubService {

    @RestClient
    GitHubClient gitHubClient;

    @Override
    public Uni<List<RepositoryDto>> getUserRepositories(String username) {
        return gitHubClient.getRepositories(username)
                .onFailure(WebApplicationException.class)
                .recoverWithUni(ex -> {
                    if (((WebApplicationException) ex).getResponse().getStatus() == 404) {
                        return Uni.createFrom().failure(new UserNotFoundException(username));
                    }
                    return Uni.createFrom().failure(ex);
                })
                .flatMap(repos -> {
                    List<Uni<RepositoryDto>> repoUnis = repos.stream()
                            .filter(repo -> !repo.isFork())
                            .map(repo -> mapToRepositoryDto(repo, username))
                            .collect(Collectors.toList());

                    if (repoUnis.isEmpty()) {
                        return Uni.createFrom().item(Collections.emptyList());
                    }

                    return Uni.combine().all().unis(repoUnis)
                            .with(results -> results.stream()
                                    .map(obj -> (RepositoryDto) obj)
                                    .collect(Collectors.toList()));
                });
    }

    private Uni<RepositoryDto> mapToRepositoryDto(GitHubRepositoryResponse repo, String username) {
        String owner = repo.owner().login();
        String repoName = repo.name();

        return gitHubClient.getBranches(owner, repoName)
                .onFailure().recoverWithItem(Collections.emptyList())
                .map(branches -> new RepositoryDto(
                        repoName,
                        owner,
                        branches.stream()
                                .map(branch -> new BranchDto(branch.name(), branch.commit().sha()))
                                .collect(Collectors.toList())));
    }
}
