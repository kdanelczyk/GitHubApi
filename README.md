
# GitHubApi

## Overview

GitHubApi is a reactive microservice built with Quarkus and Java, designed to fetch a GitHub user's repositories and retrieve detailed branch information, including branch names and last commit SHAs. The service excludes repositories that are forks. If the specified GitHub user is not found, the service returns a custom 404 error response in JSON format.

This project is built using Gradle and follows industry standards. It uses GitHub's REST API to gather repository and branch information.

## Features

- Fetch all non-fork GitHub repositories for a user.
- For each repository, list the branch names and the last commit SHA for each branch.
- Gracefully handles errors by returning a custom error response when the GitHub user is not found.
- Uses a reactive approach with Quarkus to handle the GitHub API requests.

## Requirements

- **Java 21**
- **Gradle** for building the project
- **Quarkus 3.19.2** for the framework

## Setup

### Prerequisites

Before you begin, ensure that you have the following installed on your local machine:

1. [JDK 21 or later](https://adoptopenjdk.net/)
2. [Gradle](https://gradle.org/install/)
3. [Quarkus CLI](https://quarkus.io/guides/cli-tool)

### Cloning the Repository

```bash
git clone https://github.com/your-username/GitHubApi.git
cd GitHubApi
```

### Building the Project

To build the project, use Gradle:

```bash
./gradlew build
```

### Running the Application

To run the application in development mode:

```bash
./gradlew quarkusDev
```

The application will be available at `http://localhost:8080`.

### API Endpoints

#### Fetch Repositories and Branch Details

**GET** `/github/{username}/repos`

- **Path Parameters**: 
  - `username` (required): The GitHub username of the user whose repositories you want to retrieve.

- **Response**: 
  - Returns a list of repositories (excluding forks) with their branch names and last commit SHAs.

  Example:

  ```json
  [
    {
      "repository_name": "repo-name-1",
      "owner_login": "owner-name",
      "branches": [
        {
          "branch_name": "main",
          "last_commit_sha": "abc1234def5678"
        },
        {
          "branch_name": "dev",
          "last_commit_sha": "def9876ghij432"
        }
      ]
    },
    {
      "repository_name": "repo-name-2",
      "owner_login": "owner-name",
      "branches": [
        {
          "branch_name": "main",
          "last_commit_sha": "hijk987lmn2345"
        }
      ]
    }
  ]
  ```

#### Error Response for Non-Existent GitHub User

**GET** `/github/{username}/repos`

- **Response**:
  - If the user does not exist, a `404` error is returned with the following structure:

  ```json
  {
    "status": 404,
    "message": "User not found"
  }
  ```

## Testing

### Integration Test

The project includes an integration test that verifies the happy path scenario—when a valid GitHub username is provided. The test ensures the correct response format, including repository details and branch information.

To run the tests:

```bash
./gradlew test
```

### Error Handling Test

The service also includes a test to check the error handling when an invalid GitHub username is requested.

```bash
./gradlew test
```

## Project Structure

- `src/main/java/com/githubapi/`: Contains the main application code.
- `src/test/java/com/githubapi/`: Contains the test cases for the service.
- `src/main/resources/application.properties`: Configuration file for Quarkus.
- `build.gradle`: Gradle build configuration file.

## Dependencies

- **Quarkus** - Framework for building reactive Java microservices.
- **GitHub API** - Used for retrieving repositories and branch data.
- **JUnit** - Testing framework used for the integration tests.
- **RESTEasy** - For creating REST endpoints in Quarkus.

## Error Handling

The service ensures that any non-existent GitHub users return a 404 error with a custom error message in the following format:

```json
{
  "status": 404,
  "message": "User not found"
}
```
