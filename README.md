# INF1009_P3_02

#Abstract Engine Simulation Project Description
The Abstract Engine Simulation Project is a modular, class-based simulation framework designed to demonstrate the practical application of object-oriented programming principles in a structured game-like environment. The project focuses on building a reusable engine architecture rather than a single-purpose application, emphasizing separation of concerns, scalability, and extensibility.

At its core, the engine provides a layered system architecture that separates execution flow, entity management, movement coordination, collision handling, and input/output operations into distinct subsystems. The system operates through a scene-based structure managed by a SceneManager, allowing smooth transitions between different states such as the main menu, settings, simulation, pause, and end scenes. This ensures that application state control remains independent from simulation logic.

Within the simulation environment, entities such as the Player, Bot, and Obstacle inherit from a shared abstract Entity superclass, allowing them to be managed polymorphically. The EntityManager serves as a central registry that stores, updates, and categorizes these entities while maintaining controlled access to key objects. Movement behavior is handled separately through a MovementManager, which updates entity positions based on user input or AI-driven logic.

Collision handling is coordinated by the CollisionManager, which implements a handler-based architecture using the CollisionHandler interface and specific strategy classes such as PlayerBotCollision, PlayerObstacleCollision, and BotObstacleCollision. A CollisionContext object is created each frame to track previous positions and collision outcomes, ensuring consistent and accurate resolution. Supporting utilities such as CollisionUtil centralize shared detection and revert logic, while a CollisionCounter tracks meaningful interaction events.

The engine also incorporates an InputOutputManager to abstract input detection and output operations, including keyboard input, audio feedback, and user-defined settings such as volume and brightness. By isolating these external interaction systems from the core simulation logic, the architecture maintains decoupling and improves maintainability.

Overall, the Abstract Engine Simulation Project demonstrates a manager-driven, class-based architecture that integrates scene management, entity lifecycle control, movement coordination, collision processing, and input/output abstraction into a cohesive system. The design prioritizes modularity, extensibility, and clear responsibility separation, making it suitable as a foundational framework for building scalable simulation-based applications.

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

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
