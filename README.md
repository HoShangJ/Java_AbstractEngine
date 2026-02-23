# Abstract Engine Simulation Project Description

## Overview

The Abstract Engine Simulation Project is a modular, class-based simulation framework designed to demonstrate the practical application of object-oriented programming principles within a structured, game-like environment. Rather than focusing on a single-purpose application, the project emphasizes building a reusable engine architecture that prioritizes separation of concerns, scalability, and extensibility.

At its core, the engine adopts a layered system architecture that separates execution flow, entity management, movement coordination, collision handling, and input/output operations into distinct subsystems. The application operates through a scene-based structure managed by a SceneManager, enabling smooth transitions between states such as the main menu, settings, simulation, pause, and end scenes. This design ensures that application state control remains independent from simulation logic.

# Engine Architecture

## Entity System

Within the simulation environment, entities such as Player, Bot, and Obstacle inherit from a shared abstract Entity superclass. This allows them to be managed polymorphically through a unified interface.

The EntityManager acts as a central registry responsible for storing, updating, and categorizing entities while maintaining controlled access to key objects.

## Movement System

Movement behavior is coordinated by the MovementManager, which updates entity positions based on user input or random movement logic. This separation ensures that movement logic remains independent from entity state storage.

## Collision System

Collision handling is managed by the CollisionManager, which implements a handler-based architecture using the CollisionHandler interface and specific strategy classes such as:
- PlayerBotCollision
- PlayerObstacleCollision
- BotObstacleCollision

Each frame, a CollisionContext object tracks previous positions and collision outcomes to ensure consistent and accurate resolution.

Shared collision logic is centralized in CollisionUtil, while a CollisionCounter tracks meaningful interaction events between entities.

## Input/Output System

Input/Output System

The engine includes an InputOutputManager that abstracts input detection and output operations, including:
- Keyboard input
- Audio feedback
- User-defined settings (e.g., volume and brightness)

By isolating interaction systems from core simulation logic, the engine maintains strong decoupling and improved maintainability.

## Design Philosophy

The Abstract Engine demonstrates a manager-driven, class-based architecture that integrates:
- Scene management
- Entity lifecycle control
- Movement coordination
- Collision processing
- Input/output abstraction

The system emphasizes modularity, extensibility, and clear responsibility separation, making it suitable as a foundational framework for scalable simulation-based applications.

## Built with

libGDX and generated using gdx-liftoff

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Build & Run (Gradle)

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
