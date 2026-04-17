# MetProg URJC — Videojuego de Combate

Aplicación desarrollada para la asignatura **Metodología de la Programación** de la Universidad Rey Juan Carlos, curso 2025/2026.
El sistema simula un ecosistema de criaturas fantásticas (Vampiros, Licántropos y Cazadores) que compiten entre sí mediante un sistema de desafíos por consola. Está construido siguiendo principios estrictos de Programación Orientada a Objetos y varios patrones de diseño para garantizar escalabilidad y mantenibilidad.

---

## Autores

- Lucca Manfredotti García
- Sergio Alejandro Romero López
- Neville Gil Ortiz

---

## Descripción del sistema

La aplicación es una interfaz de línea de comandos (CLI) que permite a los usuarios crear personajes, equiparlos con armas y armaduras, gestionar esbirros y retar a otros jugadores en combates automatizados. El sistema diferencia dos tipos de usuario: el Jugador, que gestiona su personaje y participa en combates, y el Operador, que administra el sistema y valida los desafíos.

### Funcionalidades principales

- Gestión de personajes: crear y personalizar Vampiros, Licántropos y Cazadores con sus atributos, equipo, habilidades especiales, fortalezas, debilidades y esbirros.
- Sistema de combate: motor automático basado en potencial de ataque y defensa, modificadores activos y habilidades especiales de cada raza.
- Control de acceso por rol: interfaces diferenciadas para Jugadores y Operadores.
- Flujo de desafíos: sistema basado en estados para proponer, validar y ejecutar combates entre jugadores.
- Persistencia: almacenamiento y recuperación de usuarios, personajes, desafíos y combates entre ejecuciones mediante serialización Java.

---

## Estructura del proyecto

```
src/
├── main/
│   └── java/
│       └── metprog/
│           ├── Main.java                       # Punto de entrada de la aplicación
│           ├── model/                          # Entidades del dominio
│           │   ├── Personaje.java              # Clase abstracta base de personajes
│           │   ├── Vampiro.java                # puntosSangre, edad
│           │   ├── Licantropo.java             # rabia (0-3)
│           │   ├── Cazador.java                # voluntad (0-3)
│           │   ├── HabilidadEspecial.java      # Clase abstracta
│           │   ├── Disciplina.java             # Habilidad del Vampiro
│           │   ├── Don.java                    # Habilidad del Licántropo
│           │   ├── Talento.java                # Habilidad del Cazador
│           │   ├── Equipo.java                 # Clase abstracta
│           │   ├── Arma.java                   # Una mano o dos manos
│           │   ├── Armadura.java
│           │   ├── Modificador.java            # Clase abstracta
│           │   ├── Fortaleza.java
│           │   ├── Debilidad.java
│           │   ├── Esbirro.java                # Clase abstracta
│           │   ├── EsbirroHumano.java          # Lealtad: ALTA, NORMAL, BAJA
│           │   ├── EsbirroGhoul.java           # Dependencia (1-5)
│           │   ├── EsbirroDemonio.java         # Pacto + esbirros propios (recursivo)
│           │   ├── Usuario.java                # nick, password, numRegistro LNNLL
│           │   ├── Operador.java               # Administrador del sistema
│           │   ├── Desafio.java                # oroApuesta, estado, contendientes
│           │   └── Combate.java                # rondas, fecha, vencedor, oroGanado
│           ├── state/                          # Patron State — ciclo de vida del desafio
│           │   ├── EstadoDesafio.java          # Interfaz
│           │   ├── Pendiente.java
│           │   ├── Validado.java
│           │   ├── Aceptado.java
│           │   └── Rechazado.java
│           ├── strategy/                       # Patron Strategy — calculo de potencial
│           │   ├── IEstrategiaPotencial.java   # Interfaz
│           │   ├── ContextoPotencial.java
│           │   ├── EstrategiaVampiro.java
│           │   ├── EstrategiaLicantropo.java
│           │   └── EstrategiaCazador.java
│           ├── factory/                        # Patron Abstract Factory — creacion de personajes
│           │   ├── FabricaPersonaje.java       # Interfaz
│           │   ├── FabricaVampiro.java
│           │   ├── FabricaLicantropo.java
│           │   └── FabricaCazador.java
│           ├── observer/                       # Patron Observer — notificaciones
│           │   ├── INotificador.java           # Interfaz
│           │   ├── ServicioNotificaciones.java
│           │   ├── InterfazJugador.java
│           │   ├── LoggerSistema.java
│           │   └── HistorialCombates.java
│           ├── service/                        # Logica de negocio
│           │   ├── MotorCombate.java           # Motor de combate
│           │   ├── GestorUsuarios.java         # Autenticacion y gestion de usuarios
│           │   ├── GestorDesafios.java         # Flujo completo de desafios
│           │   └── Persistencia.java           # Lectura y escritura de ficheros
│           └── ui/                             # Interfaz de usuario por consola
│               ├── MenuPrincipal.java
│               ├── MenuJugador.java
│               └── MenuOperador.java
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

## Patrones de diseño implementados

**State Pattern**
Gestiona el ciclo de vida de un desafío. Un desafío pasa por los estados Pendiente, Validado, Aceptado y Rechazado. Cada estado encapsula su propia lógica, eliminando la necesidad de condicionales en la clase principal.

**Strategy Pattern**
Encapsula el algoritmo de cálculo de potencial de ataque y defensa para cada tipo de criatura. Vampiros, Licántropos y Cazadores tienen fórmulas distintas. Permite añadir nuevos tipos sin modificar el motor de combate.

**Abstract Factory Pattern**
Desacopla la creación de personajes de su uso. Garantiza que cada tipo de personaje se cree con el equipo inicial y la habilidad especial correctos para su raza.

**Observer Pattern**
Gestiona las notificaciones del sistema. Cuando ocurre un evento relevante (desafío recibido, combate finalizado, usuario bloqueado), el ServicioNotificaciones avisa a todos los suscriptores registrados.

---

## Requisitos previos

- Java JDK 21 o superior
- IntelliJ IDEA (recomendado por el enunciado de la práctica)
- Git

---

## Instrucciones de instalación y ejecución

Clonar el repositorio:

```bash
git clone https://github.com/tu-usuario/metprog-combat-game.git
```

Abrir el proyecto en IntelliJ IDEA como proyecto Java estándar y compilar desde el IDE, o compilar desde terminal:

```bash
javac -d bin src/main/java/metprog/**/*.java src/main/java/metprog/Main.java
```

Ejecutar la aplicación:

```bash
java -cp bin metprog.Main
```

---

## Pruebas

Los tests unitarios se encuentran en `src/test/java/metprog/` y están desarrollados con **JUnit 5**. Cubren los siguientes aspectos:

- Cálculo de potencial de ataque y defensa en combate.
- Lógica de registro, autenticación y bloqueo de usuarios.
- Transiciones de estado en el flujo de desafíos.
- Restricciones de rango en atributos (salud, poder, sangre, rabia, voluntad, oro).
- Persistencia: guardar y recuperar datos correctamente entre ejecuciones.

Para ejecutar los tests desde IntelliJ IDEA, hacer clic derecho sobre la carpeta `test` y seleccionar "Run All Tests".

---

## Tecnologías utilizadas

- Java JDK 21
- JUnit 5 para pruebas unitarias
- Serialización nativa de Java para persistencia
- PlantText para la generación de diagramas UML
- GitHub para control de versiones

---

## Declaración de uso de inteligencia artificial

Durante el desarrollo de este proyecto se ha utilizado inteligencia artificial como herramienta de apoyo en las siguientes tareas: redacción y estructuración de documentación, generación de código repetitivo asociado a los patrones de diseño, y depuración de la lógica del motor de combate. El diseño, la arquitectura y las decisiones técnicas son responsabilidad del equipo de desarrollo.

---

*Asignatura: Metodología de la Programación — Ingeniería del Software — URJC 2025/2026*
