package org.acme.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubBranchResponse(
        String name,
        @JsonProperty("commit") GitHubCommit commit) {
}