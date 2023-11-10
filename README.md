# AssurApp Insurance App

AssurApp is an insurance application designed to streamline the interaction between clients, insurance policies, and experts. This repository contains three directories, each representing a different component of the application:

1. **Angular Frontend:** This directory contains the code for the user interface of AssurApp, built using the Angular framework. Users will interact with this frontend to browse insurance policies, submit claims, and manage their insurance-related tasks.

2. **Spring Boot API:** The Spring Boot API directory holds the server-side logic of AssurApp. It provides endpoints and handles requests from the frontend and backend, serving as the bridge between the user interface and the database.

3. **Flutter Backend:** This directory contains the code for the backend of the AssurApp mobile application, developed using the Flutter framework. The mobile app is designed for insurance experts and agents to manage client interactions and insurance policies on the go.

## Prerequisites

Before you can set up and run AssurApp, make sure you have the following prerequisites installed:

- **Node.js:** Required for running the Angular frontend.
- **Angular CLI:** Install globally using `pnpm install -g @angular/cli`.
- **Java Development Kit (JDK):** Needed for running the Spring Boot API.
- **Flutter:** Required for running the Flutter backend.

## Getting Started

Follow the steps below to set up and run AssurApp:

### Frontend (Angular)

1. Navigate to the `assurapp-web` directory.
2. Run `pnpm install` to install the frontend dependencies.
3. Run `ng serve` to start the Angular development server. The frontend should now be accessible at `http://localhost:4200`.

### Backend (Spring Boot)

1. Navigate to the `assurapp-api` directory.
2. Open the project in your preferred Java IDE.
3. Configure your database connection in the `application.properties` file.
4. Run the Spring Boot application.

### Flutter Backend

1. Navigate to the `assurapp-mobile` directory.
2. Run `flutter pub get` to install the backend dependencies.
3. Configure the backend to connect to the Spring Boot API and database.
4. Run the Flutter backend using `flutter run`.

## Features

AssurApp provides the following key features:

- User registration and authentication.
- Browse and purchase insurance policies.
- Submit and track insurance claims.
- Communication between clients, insurance experts, and agents.
- Secure handling of sensitive client data.
- Mobile access for insurance experts and agents.

## Contributing

We welcome contributions to AssurApp. If you would like to contribute to the project, please follow our [contribution guidelines](CONTRIBUTING.md).

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Support

If you encounter any issues or have questions, please feel free to open an issue in the repository, and our team will do their best to assist you.

Thank you for using AssurApp Insurance App! We hope it helps streamline insurance management for both clients and experts.