package org.acme.controller;

import java.util.List;

import org.acme.model.RepositoryDto;
import org.acme.service.GitHubService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/github")
@Produces(MediaType.APPLICATION_JSON)
public class GitHubController {

    @Inject
    private GitHubService gitHubService;

    @GET
    @Path("/{username}/repos")
    public Uni<List<RepositoryDto>> getUserRepositories(@PathParam("username") String username) {
        return gitHubService.getUserRepositories(username);
    }

}
