# Image Processing Pipeline

<p align="center">
  <em>An event-driven backend application for asynchronous image processing.</em>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring_Boot-Framework-6DB33F?logo=springboot" />
  <img src="https://img.shields.io/badge/RabbitMQ-Message_Broker-FF6600?logo=rabbitmq" />
  <img src="https://img.shields.io/badge/MongoDB-Database-47A248?logo=mongodb" />
  <img src="https://img.shields.io/badge/Docker-Containerization-2496ED?logo=docker" />
  <img src="https://img.shields.io/badge/Status-In_Development-yellow" />
</p>

---

## Project Status

**🟡 In Development**

<p align="justify">
The initial backend structure, authentication and image upload functionality have been implemented, with uploaded images stored in MongoDB using GridFS. The current focus is integrating RabbitMQ to handle image processing asynchronously.
</p>

## Overview

<p align="justify">
A backend application that allows authenticated users to upload images and request their asynchronous processing. The application uses an <strong>event-driven architecture</strong> to handle processing tasks independently of the upload flow.
</p>

<p align="justify">
For the current implementation, <b>image processing consists of applying a user-defined watermark</b> to the original image and generating a separate copy that is available for download, leaving the original unchanged.
</p>

### Main Goals

Explore and gain practical experience with:

- Event-driven architecture.
- Asynchronous communication.
- Message-driven workflows using RabbitMQ.
- Background job processing.
- Containerization and service orchestration with Docker.

## Architecture Overview

<p align="justify">
The application separates the API responsible for handling user requests from the background processes responsible for image processing. RabbitMQ acts as the communication layer between these components, allowing processing tasks to be handled asynchronously.
</p>

### Processing Workflow

1. **Authentication:** the user authenticates and obtains a JWT.
2. **Image Upload:** the API receives the image and stores the original file in MongoDB using GridFS.
3. **Processing Request:** the user requests image processing by specifying the watermark to be applied.
4. **Event Publishing:** the application creates a processing request and publishes an event containing the image reference and watermark configuration to RabbitMQ.
5. **Event Consumption:** a background worker consumes the event from the message broker.
6. **Image Processing:** the worker retrieves the original image and applies the requested watermark, generating a separate copy while keeping the original unchanged.
7. **Status Update:** the processing result and corresponding status are persisted.
8. **Image Download:** once processing is complete, the watermarked image is available for download.

### Architectural Goals

The architecture is designed around the following principles:

- **Decoupling:** the API and image processing tasks operate independently, avoiding direct dependencies between the upload flow and processing logic.
- **Scalability:** background workers can be scaled independently of the API.
- **Reliability:** message handling mechanisms help manage processing failures and prevent tasks from being lost.
- **Responsiveness:** the API does not need to wait for image processing to finish before responding to the user.

---

> **Note:** This project is a work in progress. Features, implementation details and documentation may evolve during development.
