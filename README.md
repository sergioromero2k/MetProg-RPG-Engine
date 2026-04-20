# MetProg URJC — Combat Video Game

Application developed for the **Methodology of Programming** course at Universidad Rey Juan Carlos, academic year 2025/2026.
The system simulates an ecosystem of fantasy creatures (Vampires, Lycanthropes, and Hunters) competing against each other through a challenge-based combat system. It is built following strict Object-Oriented Programming principles and several Design Patterns to ensure scalability and maintainability.

---

## Authors

- Lucca Manfredotti García
- Sergio Alejandro Romero López
- Neville Gil Ortiz

---

## System Description

The application is a Command Line Interface (CLI) that allows users to create characters, equip them with weapons and armor, manage minions, and challenge other players to automated combat. The system distinguishes between two user roles: the Player, who manages their character and participates in combat, and the Operator, who administrates the system and validates challenges.

### Main Features

- Character management: create and customize Vampires, Lycanthropes, and Hunters with their attributes, equipment, special abilities, strengths, weaknesses, and minions.
- Combat system: automated engine based on attack and defense potential, active modifiers, and each race's special abilities.
- Role-based access control: separate interfaces for Players and Operators.
- Challenge workflow: state-based system for proposing, validating, and executing combat between players.
- Persistence: storage and retrieval of users, characters, challenges, and combat records between executions using Java serialization.

---

## Project Structure

```
src/
├── main/
│   └── java/
│       └── metprog/
│           ├── Main.java                       # Application entry point
│           ├── model/                          # Domain entities
│           │   ├── Personaje.java              # Abstract base class for characters
│           │   ├── Vampiro.java                # bloodPoints, age
│           │   ├── Licantropo.java             # rage (0-3)
│           │   ├── Cazador.java                # willpower (0-3)
│           │   ├── HabilidadEspecial.java      # Abstract class
│           │   ├── Disciplina.java             # Vampire special ability
│           │   ├── Don.java                    # Lycanthrope special ability
│           │   ├── Talento.java                # Hunter special ability
│           │   ├── Equipo.java                 # Abstract class
│           │   ├── Arma.java                   # One-handed or two-handed weapon
│           │   ├── Armadura.java               # Armor
│           │   ├── Modificador.java            # Abstract class
│           │   ├── Fortaleza.java              # Strength modifier
│           │   ├── Debilidad.java              # Weakness modifier
│           │   ├── Esbirro.java                # Abstract class
│           │   ├── EsbirroHumano.java          # Loyalty: HIGH, NORMAL, LOW
│           │   ├── EsbirroGhoul.java           # Dependency (1-5)
│           │   ├── EsbirroDemonio.java         # Pact + own minions (recursive)
│           │   ├── Usuario.java                # nick, password, registration number LNNLL
│           │   ├── Operador.java               # System administrator
│           │   ├── Desafio.java                # goldBet, state, contenders
│           │   └── Combate.java                # rounds, date, winner, goldWon
│           ├── state/                          # State Pattern — challenge lifecycle
│           │   ├── EstadoDesafio.java          # Interface
│           │   ├── Pendiente.java              # Pending validation
│           │   ├── Publicado.java               # Validated by operator
│           │   ├── EnCombate.java               # Accepted by challenged player
│           │   └── Rechazado.java              # Rejected — penalty applied
│           ├── strategy/                       # Strategy Pattern — potential calculation
│           │   ├── IEstrategiaPotencial.java   # Interface
│           │   ├── ContextoPotencial.java      # Uses the correct strategy per character
│           │   ├── EstrategiaVampiro.java      # power + discipline + equipment + (2 if blood >= 5)
│           │   ├── EstrategiaLicantropo.java   # power + gift + equipment + rage
│           │   └── EstrategiaCazador.java      # power + talent + equipment + willpower
│           ├── factory/                        # Abstract Factory Pattern — character creation
│           │   ├── FabricaPersonaje.java       # Interface
│           │   ├── FabricaVampiro.java         # Creates Vampire with Discipline and initial gear
│           │   ├── FabricaLicantropo.java      # Creates Lycanthrope with Gift and initial gear
│           │   └── FabricaCazador.java         # Creates Hunter with Talent and initial gear
│           ├── observer/                       # Observer Pattern — notifications
│           │   ├── INotificador.java           # Interface
│           │   ├── ServicioNotificaciones.java # Manages subscribers and dispatches events
│           │   ├── InterfazJugador.java        # Displays messages to the player in console
│           │   ├── LoggerSistema.java          # Logs events to a text file
│           │   └── HistorialCombates.java      # Saves combat records to persistence
│           ├── service/                        # Business logic
│           │   ├── MotorCombate.java           # Combat engine
│           │   ├── GestorUsuarios.java         # Authentication and user management
│           │   ├── GestorDesafios.java         # Full challenge workflow logic
│           │   └── Persistencia.java           # File read and write operations
│           └── ui/                             # Console-based user interface
│               ├── MenuPrincipal.java          # Welcome screen, login, registration
│               ├── MenuJugador.java            # Full player menu
│               └── MenuOperador.java           # Full operator menu
└── test/
    └── java/
        └── metprog/
            ├── VampiroTest.java
            ├── LicantropoTest.java
            ├── CazadorTest.java
            ├── GestorUsuariosTest.java
            ├── GestorDesafiosTest.java
            ├── MotorCombateTest.java
            └── PersistenciaTest.java
```

---

## Design Patterns Implemented

**State Pattern**
Manages the lifecycle of a challenge. A challenge transitions through the states Pending, Validated, Accepted, and Rejected. Each state encapsulates its own logic, eliminating the need for conditional branches in the main class and making it straightforward to add new states in the future.

**Strategy Pattern**
Encapsulates the attack and defense potential calculation algorithm for each character type. Vampires, Lycanthropes, and Hunters use completely different formulas. This allows the combat engine to work with any character type without needing to know its internal details, and makes adding new races as simple as creating a new strategy class.

**Abstract Factory Pattern**
Decouples character creation from its usage. Guarantees that each character type is always created with the correct initial equipment and special ability for its race, preventing incompatible combinations such as a Vampire receiving a Hunter ability.

**Observer Pattern**
Manages system-wide notifications. When a relevant event occurs (challenge received, combat finished, user blocked), the NotificationService notifies all registered subscribers. This decouples the event source from the components that react to it, making it easy to add new observers without modifying existing code.

---

## Prerequisites

- Java JDK 21 or higher
- IntelliJ IDEA (recommended by the course)
- Git

---

## Installation and Execution

Clone the repository:

```bash
git clone https://github.com/your-username/metprog-combat-game.git
```

Open the project in IntelliJ IDEA as a standard Java project and build from the IDE, or compile from the terminal:

```bash
javac -d bin src/main/java/metprog/**/*.java src/main/java/metprog/Main.java
```

Run the application:

```bash
java -cp bin metprog.Main
```

---

## Testing

Unit tests are located in `src/test/java/metprog/` and are developed with **JUnit 5**. They cover the following areas:

- Attack and defense potential calculations in combat.
- User registration, authentication, and blocking logic.
- State transitions in the challenge workflow.
- Attribute range constraints (health, power, blood points, rage, willpower, gold).
- Persistence: correctly saving and recovering all data between executions.

To run all tests from IntelliJ IDEA, right-click the `test` folder and select "Run All Tests".

---

## Technologies Used

- Java JDK 21
- JUnit 5 for unit testing
- Native Java serialization for persistence
- PlantText for UML diagram generation
- GitHub for version control

---

## AI Usage Disclosure

Artificial intelligence was used during this project as a support tool for the following tasks: drafting and structuring documentation, generating boilerplate code associated with design patterns, and assisting in debugging complex logic in the combat engine. All design decisions, architecture, and technical choices are the responsibility of the development team.

---

*Course: Methodology of Programming — Software Engineering — URJC 2025/2026*
