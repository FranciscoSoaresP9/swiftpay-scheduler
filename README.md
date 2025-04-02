# Swiftpay Scheduler - Setup Guide

Swiftpay Scheduler is an API designed to enable fast and secure scheduling of bank transfers. Its primary goal is to streamline payment automation, allowing users to easily create accounts, make deposits, and transfer funds between users.

## Core Features

- **Account Management**: Create and manage user accounts.
- **Deposits & Transfers**: Perform secure deposits and fund transfers between users.
- **Secure Authentication & Authorization**: Integrated with **AWS Cognito** to ensure robust user authentication and authorization.
- **Automated Transfer Processing: A scheduled service runs every 30 minutes to process bank transfers, ensuring timely execution of transactions.

## Infrastructure & Deployment

- **Reliable Data Storage**: Utilizes **AWS RDS (Relational Database Service) with PostgreSQL** to securely store all scheduling information and user data.
- **Flexible Deployment**: Developed with **Spring Boot**, the API is hosted on **AWS Elastic Beanstalk**, using **EC2 containers** for scalability and ease of management.
- **Local Deployment Option**: The application can also be run locally using Docker containers.

---

## Getting Started

You can use the API hosted on AWS or run the project locally. To run it locally, you can use Docker or execute it directly in an IDE, such as IntelliJ IDEA.

## API Access

The API is available at: [http://swiftpay-scheduler.eu-north-1.elasticbeanstalk.com](http://swiftpay-scheduler.eu-north-1.elasticbeanstalk.com)

Use this endpoint to test the application's functionality.

## Prerequisites

Before running the application locally, make sure you have the following installed and configured:

- **JDK 21** (Check the installation with `java -version`)
- **PostgreSQL 15 or higher** (only needed for local setup)
- **Docker** (optional for local deployment)
- **IntelliJ IDEA** (or any IDE of your choice)



## Local Machine Setup (Using an IDE)

### Setup Steps

1. **Configure the Development Profile**  
   Ensure that the `dev` profile is active in the `application.yml` file.

2. **Configure AWS Cognito Environment Variables**  
  The .env file already contains the necessary configuration. You can either use the provided environment variables or configure your own user pool in AWS Cognito. Just make sure the environment variables are properly set in your machine or IDE.

3. **Configure the Database**  
   Create a PostgreSQL 15 database.  
   The following environment variables need to be configured for database connection:

   - **DB_URL**: The connection URL for the database.  
     Example: `jdbc:postgresql://localhost:5432/swiftpay_scheduler`
   
   - **DB_USER**: The database username.  
     Example: `postgres`
   
   - **DB_PASSWORD**: The database user password.  
     Example: `your_password`

4. **Run the Application**  
   Open the `SwiftpaySchedulerApplication.java` class in your IDE and click **Run**.

## Setup using docker

### Setup Steps

1. **Set Up the Project Root**  
   Ensure you are in the project's root directory.

2. **Activate the Local Profile**  
   Ensure that the `local` profile is active in the `application.yml` file.

3. **Create the JAR File**  
   Run the following command to generate the JAR file:
   ```sh
   mvn clean package
   ```

4. **Build the Docker Image**  
   Execute the command below to build the Docker image:
   ```sh
   docker-compose build
   ```

5. **Start the Containers**  
   Use the following command to start the containers in detached mode:
   ```sh
   docker-compose up -d
   ```

6. **Stop the Containers**  
   To stop and remove the running containers, execute:
   ```sh
   docker-compose down
   ```

---

### AWS Cognito Integration Warning

Before you start using the app, please note that it is integrated with AWS Cognito. This means that if you initialize multiple databases using the same Cognito configuration, user data may fall out of sync with the API. For example, if you create a local database and then register a user with the email user@example.com through the API, that user is created both in your local database and in AWS's user pool. If you then delete your local database and create a new one, you won't be able to register a user with the email user@example.com again because, even though the user no longer exists locally, it still exists in AWS.

However, please note that the user pool provided for local testing is different from the one hosted on AWS. Therefore, this issue does not apply to the AWS-hosted API.

---

### Flow for the First Transfer

1. **User Registration:**  
   To use the API, you must first register a user. It is recommended to use a valid email address since you will need to confirm your identity to complete the registration. After creating the user, you will receive an email containing a confirmation code. With that code, you can confirm your account and start using the API.

2. **Creating a Target User:**  
   You should also create a second user (this one does not require a real email address) who will serve as the recipient for your transfers. Make sure to note down this user's IBAN, as you will use it to schedule transfers.

3. **Logging In:**  
   After creating these two users, log in with the primary user. You will receive an access token (valid for 1 hour) and a refresh token (valid for 30 days).

4. **Scheduling Transfers:**  
   Finally, use the transfer scheduling endpoint, specifying the target user's IBAN as the destination.

**Note:** All requests (except for registration and login) must include the `Authorization` header with the Bearer token obtained at login.

---

## API Documentation

## User

### Register New User
**Method:** POST  
**Endpoint:** `/api/auth/register`

#### Description
Registers a new user and returns the account details.

#### Request Body
```json
{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "securePassword@123",
  "balance": 250.00
}
```
#### Response
**Status Code:** 200 OK
```json
{
  "id": 12345,
  "name": "John Doe",
  "iban": "GB29XABC10161234567801",
  "accountId": 67890,
  "balance": 250.00
}
```

#### Error Responses
- `InvalidBalanceException`: Balance is less than or equal to 200. (400 Bad Request)
- `InvalidPasswordException`: Password does not meet the complexity requirements. (400 Bad Request)
- `InvalidUsernameException`: The email address is invalid. (400 Bad Request)
- `UsernameAlreadyExistsException`: The provided email already exists. (400 Bad Request)

---

#### Confirm Registration
**Method:** POST  
**Endpoint:** `/api/auth/confirm-registration`

#### Description
Confirms a user's registration using a verification code.

#### Request Body
```json
{
  "email": "johndoe@example.com",
  "code": "123456"
}
```

#### Response
**Status Code:** 200 OK

---

### Login
**Method:** POST  
**Endpoint:** `/api/auth`

#### Description
Authenticates the user and returns an access token.

#### Request Body
```json
{
  "username": "user123",
  "password": "Password@123"
}
```

#### Response
```json
{
  "accessToken": "someAccessToken",
  "refreshToken": "someRefreshToken",
  "expiresIn": 3600
}
```

#### Error Responses
- `InvalidCredentialsException`: Invalid username or password. (401 Unauthorized)

---

## Transfers

#### Get All Transfers

**Method:** GET  
**Endpoint:** `/api/transfers`

#### Description
Retrieves a paginated list of transfers for the logged-in user

#### Request Parameters
- **page:** (Optional) The page number (0-indexed).
- **size:** (Optional) The number of transfers per page.
- **sort:** (Optional) Sorting criteria in the format: `property(,asc|desc)`.

#### Response
```json
{
  "content": [
    {
      "id": 1,
      "receiverName": "John Doe",
      "amount": 1000.00,
      "scheduleDate": "2025-04-01",
      "amountIncludingFees": 1020.00,
      "status": "PENDING"
    }
  ],
  "pageable": { /* pageable details */ },
  "totalElements": 100,
  "totalPages": 10,
  "last": false,
  "first": true,
  "size": 10,
  "number": 0,
  "sort": { /* sorting details */ },
  "numberOfElements": 10,
  "empty": false
}
```

---

#### Get Transfer By Id

**Method:** GET  
**Endpoint:** `/api/transfers/{id}`

#### Description
Retrieves detailed information about a specific transfer identified by its ID

#### Path Parameter
- **id:** The unique identifier of the transfer.

#### Response
**Status Code:** 200 OK

Example response:

```json
{
  "id": 1,
  "receiverName": "John Doe",
  "receiverIban": "GB29XABC10161234567801",
  "amount": 1000.00,
  "scheduleDate": "2025-04-01",
  "taxPercentage": 2.0,
  "fixedFee": 20.00,
  "amountIncludingFees": 1020.00,
  "status": "PENDING"
}
```

---

### Create Transfer
**Method:** POST  
**Endpoint:** `/api/transfers`

#### Description
Creates a new transfer.

#### Request Body
```json
{
  "receiverIban": "GB29XABC10161234567801",
  "amount": 1000.00,
  "scheduleDate": "2025-04-01"
}
```

#### Error Responses
- `InvalidTransferAmountException`: Amount must be greater than zero. (400 Bad Request)
- `InvalidBalanceException`: Insufficient funds. (400 Bad Request)
- `InvalidTransferDateException`: Date cannot be in the past. (400 Bad Request)

---

### Update Transfer
**Method:** PUT  
**Endpoint:** `/api/transfers/{id}`

#### Description
Updates an existing transfer.

#### Path Parameter
- **id:** The unique identifier of the transfer.

#### Request Body
```json
{
  "amount": 1200.00,
  "scheduleDate": "2025-05-01"
}
```

#### Error Responses
- `TransferModificationNotAllowedException`: Cannot update completed or cancelled transfers. (400 Bad Request)
- `InvalidTransferAmountException`: Amount must be greater than zero. (400 Bad Request)
- `InvalidBalanceException`: Insufficient funds. (400 Bad Request)
- `InvalidTransferDateException`: Date cannot be in the past. (400 Bad Request)

---

### Cancel Transfer
**Method:** PATCH  
**Endpoint:** `/api/transfers/{id}/cancel`

#### Description
Cancels a transfer.

#### Path Parameter
- **id:** The unique identifier of the transfer.

#### Error Responses
- `TransferCancellationNotAllowedException`: Cannot cancel completed or already cancelled transfers. (400 Bad Request)

---

### Delete Transfer
**Method:** DELETE  
**Endpoint:** `/api/transfers/{id}`

#### Description
Deletes a cancelled transfer.

#### Error Responses
- `TransferDeletionNotAllowedException`: Transfer must be cancelled before deletion. (400 Bad Request)

---

## Bank Account

### Get Current User Bank Account
**Method:** GET  
**Endpoint:** `/api/bank-accounts`

#### Description
Retrieves the authenticated user's bank account details.

#### Response
```json
{
  "id": 12345,
  "iban": "GB29XABC10161234567801",
  "balance": 1000.00
}
```

---

### Deposit to Bank Account
**Method:** PATCH  
**Endpoint:** `/api/bank-accounts`

#### Description
Deposits an amount into the user's bank account.

#### Request Body
```json
{
  "amount": 500.00
}
```

#### Error Responses
- `InvalidTransferAmountException`: Deposit amount must be greater than zero. (400 Bad Request)

---

## User Management

### Get Current User
**Method:** GET  
**Endpoint:** `/api/users`

#### Description
Retrieves details of the authenticated user.

#### Response
```json
{
  "id": 12345,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "iban": "GB29XABC10161234567801"
}
```

---

### Update User Name
**Method:** PATCH  
**Endpoint:** `/api/users`

#### Description
Updates the authenticated user's name.

#### Request Body
```json
{
  "name": "Jane Doe"
}
