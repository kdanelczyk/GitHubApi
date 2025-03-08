package org.acme.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubRepositoryResponse(
        String name,
        @JsonProperty("fork") boolean isFork,
        GitHubOwner owner) {
}
