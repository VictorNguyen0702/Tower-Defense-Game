# BTD 5 Clone

A clone of Bloons Tower Defense 5 built using the Processing library and Gradle.

## Features
- Tower defense gameplay similar to BTD 5
- Multiple tower types with unique abilities
- Enemy waves with increasing difficulty
- Gradle-based project structure for easy builds and dependency management
- Smooth animations and interactions using the Processing library

## Installation & Setup

### Prerequisites
- Java 17+ (Ensure it's installed and configured in your environment)
- Gradle (Optional, as the project includes the Gradle wrapper)

### Clone the Repository
```sh
git clone https://github.com/VictorNguyen0702/Tower-Defense-Game
```

### Running the Game
To run the game using Gradle, use the following command:
```sh
./gradlew run  # Linux/Mac
```
or
```sh
gradlew.bat run  # Windows
```

### Building the Project
To build the project, run:
```sh
./gradlew build
```
This will generate the compiled JAR in `build/libs/`.

## Project Structure
```
├── src/
│   ├── main/
│   │   ├── java/WizardTD/
│   │   │   ├── App.java  # Main application entry point
│   │   │   ├── Fireball.java  # Fireball projectile logic
│   │   │   ├── Mana.java  # Handles mana resources
│   │   │   ├── Monster.java  # Defines enemy behavior
│   │   │   ├── MoveableObject.java  # Base class for moving objects
│   │   │   ├── Projectile.java  # Base class for projectiles
│   │   │   ├── Ring.java  # Defines rings used in the game
│   │   │   ├── Shooter.java  # Tower type with single shot attack
│   │   │   ├── ShortestPathAlgorithm.java  # Pathfinding algorithm for enemies
│   │   │   ├── Sprayer.java  # Tower type with spray attack
│   │   │   ├── TileNode.java  # Represents a tile in the map grid
│   │   │   ├── Tower.java  # Tower logic and upgrades
│   │   │   ├── Wave.java  # Manages enemy waves
│   │   ├── resources/
├── build.gradle  # Gradle build file
├── settings.gradle  # Gradle settings
├── README.md  # Project documentation
```

## Dependencies
This project uses the following dependencies:
- Processing Library (Core for rendering and interaction)

---
Enjoy shooting enemies! 🎈🔥

