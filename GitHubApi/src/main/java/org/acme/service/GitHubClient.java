package org.acme.service;

import java.util.List;

import org.acme.external.GitHubBranchResponse;
import org.acme.external.GitHubRepositoryResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "github-api")
@Produces(MediaType.APPLICATION_JSON)
public interface GitHubClient {

    @GET
    @Path("/users/{username}/repos")
    Uni<List<GitHubRepositoryResponse>> getRepositories(@PathParam("username") String username);

    @GET
    @Path("/repos/{username}/{repo}/branches")
    Uni<List<GitHubBranchResponse>> getBranches(@PathParam("username") String username, @PathParam("repo") String repo);

}
