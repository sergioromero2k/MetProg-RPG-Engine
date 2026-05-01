# MetProg — Videojuego de Combate

Aplicación desarrollada para la asignatura **Metodología de la Programación** de la Universidad Rey Juan Carlos, curso 2025/2026.

El sistema simula un ecosistema de criaturas fantásticas (Vampiros, Licántropos y Cazadores) que compiten entre sí mediante un sistema de desafíos por consola. Está construido siguiendo principios estrictos de Programación Orientada a Objetos y varios patrones de diseño para garantizar escalabilidad y mantenibilidad.

---

## Autores

- Lucca Manfredotti García
- Sergio Alejandro Romero-López
- Neville Gil Ortiz

---

## Descripción del proyecto

La aplicación es una interfaz de línea de comandos (CLI) que permite a los usuarios crear personajes, equiparlos con armas y armaduras, gestionar esbirros y retar a otros jugadores en combates automatizados.

El objetivo de la práctica es implementar un sistema de gestión de combates entre criaturas fantásticas aplicando los patrones de diseño State, Strategy, Abstract Factory y Observer, junto con persistencia de datos mediante serialización Java.

El sistema diferencia dos tipos de usuario:
- **Jugador**: gestiona su personaje y participa en combates apostando oro.
- **Operador**: administra el sistema, valida los desafíos y gestiona los usuarios.

---

## Requisitos previos

Para compilar y ejecutar el proyecto es necesario tener instalado:

- **Java JDK 21** o superior. Descargar desde [adoptium.net](https://adoptium.net) o [oracle.com](https://www.oracle.com/java/technologies/downloads/)
- **IntelliJ IDEA** (recomendado) o cualquier IDE compatible con Java
- **Git** para clonar el repositorio

Para verificar que Java está instalado correctamente ejecutar en terminal:

```bash
java -version
javac -version
```

Ambos comandos deben mostrar la versión 21 o superior.

---

## Cómo compilar y ejecutar el proyecto

### Opción 1 — Desde IntelliJ IDEA (recomendado)

1. Clonar el repositorio:
```bash
git clone https://github.com/tu-usuario/metprog-combat-game.git
```

2. Abrir IntelliJ IDEA → `File → Open` → seleccionar la carpeta del proyecto.

3. Verificar que el SDK es Java 21: `File → Project Structure → SDK`.

4. Compilar y ejecutar: abrir `src/main/java/metprog/Main.java` y pulsar el botón ▶️ o `Shift + F10`.

### Opción 2 — Desde terminal

1. Clonar el repositorio:
```bash
git clone https://github.com/tu-usuario/metprog-combat-game.git
cd metprog-combat-game
```

2. Compilar todas las clases:
```bash
javac -d out/production/MetProg-RPG-Engine src/main/java/metprog/**/*.java src/main/java/metprog/Main.java
```

3. Ejecutar la aplicación:
```bash
java -cp out/production/MetProg-RPG-Engine metprog.Main
```

---

## Qué pide el programa al usuario

El programa funciona completamente por consola mediante entrada de teclado. No requiere ninguna ruta de archivo ni configuración adicional. Al arrancar crea automáticamente la carpeta `datos/` donde guarda los ficheros de persistencia.

**Reglas importantes antes de empezar:**
- La contraseña debe tener **entre 8 y 12 caracteres** exactos. Si no cumple este requisito el registro será rechazado con un mensaje de error.
- El nick debe ser único en el sistema.
- Los datos se guardan automáticamente al cerrar el programa y se recuperan al volver a abrirlo.

---

## Guía rápida para probar el sistema completo

A continuación se detalla el flujo completo paso a paso para que la profesora pueda probar todas las funcionalidades del sistema desde cero.

---

### Paso 1 — Arrancar el programa

Al ejecutar `Main.java` aparece el menú principal:

```
=== Bienvenido a MetProg RPG Engine ===
1. Jugar
2. Operador
0. Salir
Selecciona una opción:
```

---

### Paso 2 — Registrar el operador

Seleccionar `2` para acceder al menú de operador y luego `2` para registrar un operador:

```
Selecciona una opción: 2

=== ACCESO OPERADOR ===
1. Iniciar sesión
2. Registrar operador
0. Volver
Selecciona una opción: 2

Nombre: Admin
Nick: admin123
Contraseña (entre 8 y 12 caracteres): admin1234
Operador registrado correctamente: admin123
```

Volver al menú principal pulsando `0`.

---

### Paso 3 — Registrar el jugador 1

Seleccionar `1` para acceder al menú de jugador y luego `2` para registrar:

```
Selecciona una opción: 1

=== ACCESO JUGADOR ===
1. Iniciar sesión
2. Registrar usuario
0. Volver
Selecciona una opción: 2

Nombre: Sergio
Nick: sergio123
Contraseña (entre 8 y 12 caracteres): sergio123
Usuario registrado: sergio123 | Número de registro: A23BC
```

Una vez registrado el sistema entra directamente en el menú del jugador. Crear el personaje seleccionando `1`:

```
=== MENÚ JUGADOR ===
Selecciona una opción: 1

Elige tipo de personaje:
1. Vampiro
2. Licántropo
3. Cazador
Opción: 1

Nombre del personaje: Drácula
Personaje creado: Vampiro Drácula [Salud:5/5 Poder:3 Oro:100]
```

Cerrar sesión pulsando `0`.

---

### Paso 4 — Registrar el jugador 2

Repetir el proceso del paso 3 con datos diferentes:

```
Nombre: Arturo
Nick: arturo123
Contraseña (entre 8 y 12 caracteres): arturo123
```

Crear el personaje:

```
Elige tipo de personaje: 2
Nombre del personaje: LunaFeralis
```

Cerrar sesión pulsando `0`.

---

### Paso 5 — Jugador 1 lanza un desafío

Iniciar sesión con el jugador 1:

```
=== ACCESO JUGADOR ===
1. Iniciar sesión
Selecciona una opción: 1

Nick: sergio123
Contraseña: sergio123
Bienvenido, sergio123.
```

Seleccionar `6` para lanzar un desafío:

```
Selecciona una opción: 6

Nick del desafiado: arturo123
Oro apostado: 50
Desafío creado correctamente.
```

Cerrar sesión pulsando `0`.

---

### Paso 6 — Operador valida el desafío

Iniciar sesión como operador:

```
=== ACCESO OPERADOR ===
1. Iniciar sesión
Selecciona una opción: 1

Nick: admin123
Contraseña: admin1234
Bienvenido, operador admin123.
```

Seleccionar `7` para validar el desafío:

```
Selecciona una opción: 7

1. sergio123 vs arturo123 oro:50
Selecciona un desafío: 1

Fortalezas del desafiante: (pulsar Enter para ninguna)
Debilidades del desafiante: (pulsar Enter para ninguna)
Fortalezas del desafiado: (pulsar Enter para ninguna)
Debilidades del desafiado: (pulsar Enter para ninguna)

Desafío validado y publicado.
```

Si se quieren añadir modificadores el formato es `nombre:valor` separado por comas:
```
Fortalezas del desafiante: luna llena:3, fuerza oscura:2
```

Cerrar sesión pulsando `0`.

---

### Paso 7 — Jugador 2 acepta el desafío y combaten

Iniciar sesión con el jugador 2:

```
Nick: arturo123
Contraseña: arturo123
Bienvenido, arturo123.
AVISO: Tienes un desafío pendiente de sergio123
```

Seleccionar `8` para aceptar el desafío. El combate se ejecuta automáticamente:

```
Selecciona una opción: 8

Desafío aceptado. Iniciando combate...

═══════════════════════════════════════
  RESULTADO DEL COMBATE
  sergio123 vs arturo123
  Fecha: 2025-05-01
  Rondas: 4
───────────────────────────────────────
  Ronda 1: ...
  Ronda 2: ...
  Ronda 3: ...
  Ronda 4: ...
───────────────────────────────────────
  VENCEDOR: sergio123
  ORO GANADO: 50
═══════════════════════════════════════
```

---

### Otras acciones disponibles

**Ver ranking global** — opción `10` del menú jugador. Muestra todos los jugadores ordenados por oro acumulado.

**Rechazar un desafío** — opción `9` del menú jugador. El desafiado paga el 10% del oro apostado como penalización.

**Bloquear un usuario** — opción `5` del menú operador. El usuario bloqueado no puede iniciar sesión ni emitir desafíos.

**Ver historial de oro** — opción `12` del menú jugador. Muestra todas las ganancias y pérdidas de oro.

---

## Estructura del proyecto

```
src/
├── main/
│   └── java/
│       └── metprog/
│           ├── Main.java
│           ├── model/
│           ├── state/
│           ├── strategy/
│           ├── factory/
│           ├── observer/
│           ├── service/
│           └── ui/
└── test/
    └── java/
        └── metprog/
```

---

## Patrones de diseño implementados

**State Pattern** — Gestiona el ciclo de vida de un desafío a través de los estados Pendiente, Publicado, EnCombate, Completado y Rechazado.

**Strategy Pattern** — Encapsula el algoritmo de cálculo de potencial de ataque y defensa para cada tipo de criatura.

**Abstract Factory Pattern** — Garantiza que cada personaje se cree con el equipo y habilidad correctos para su raza.

**Observer Pattern** — Gestiona las notificaciones del sistema cuando ocurren eventos relevantes.

---

## Pruebas

Los tests unitarios se encuentran en `src/test/java/metprog/` y están desarrollados con **JUnit 5**. Para ejecutarlos desde IntelliJ IDEA hacer clic derecho sobre la carpeta `test` y seleccionar `Run All Tests`.

---

## Tecnologías utilizadas

- Java JDK 21
- JUnit 5 para pruebas unitarias
- Serialización nativa de Java para persistencia en ficheros `.ser`
- PlantText para la generación de diagramas UML
- GitHub para control de versiones

---

## Declaración de uso de inteligencia artificial

Durante el desarrollo de este proyecto se ha utilizado inteligencia artificial como herramienta de apoyo en las siguientes tareas: redacción y estructuración de documentación, generación de código repetitivo asociado a los patrones de diseño, y depuración de la lógica del motor de combate. El diseño, la arquitectura y las decisiones técnicas son responsabilidad del equipo de desarrollo.

---

*Asignatura: Metodología de la Programación — Ingeniería del Software — URJC 2025/2026*
