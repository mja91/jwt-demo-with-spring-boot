# JWT Demo with Spring Boot

This project demonstrates JWT (JSON Web Token) generation, validation, and reissuance using Spring Boot and the jjwt library. It includes both Access Token and Refresh Token management for user authentication, using Refresh Token Rotation (RTR) to securely renew tokens when the access token expires.

## Tech Stack
- Spring Boot
- Java JWT (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)
- Lombok

## API Endpoints

### 1. Generate JWT
- **POST /api/v1/jwt/{userId}**
- **Description:** Generates access and refresh tokens using the provided `userId` via the path. This is primarily for testing purposes.
- **Response:**
    ```json
    {
      "userId": "user001",
      "accessToken": "<access_token>",
      "refreshToken": "<refresh_token>",
      "active": true
    }
    ```

### 2. Validate JWT
- **POST /api/v1/jwt/validation**
- **Description:** Validates the provided access or refresh token.
- **Response:**
    ```json
    {
      "userId": "user001",
      "token": "<token>",
      "active": true
    }
    ```

### 3. Reissue JWT
- **POST /api/v1/jwt/reissuance**
- **Description:** Reissues new access and refresh tokens using the provided valid refresh token.
- **Response:**
    ```json
    {
      "userId": "user001",
      "accessToken": "<access_token>",
      "refreshToken": "<refresh_token>",
      "success": true
    }
    ```
    
## Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/spring-jwt-demo.git
   cd spring-jwt-demo

2. **Build and run the project:**
   ```bash
   ./gradlew build
   ./gradlew bootRun

3. **Token expiration settings: Modify token expiration times in the JwtUtil class if necessary:**
   ```bash
   private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30; // 30 minutes
   private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 21; // 21 days
