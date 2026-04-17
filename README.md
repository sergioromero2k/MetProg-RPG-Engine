# MetProg Battle Arena: World of Creatures

## Resumen del Proyecto
Desarrollo de un sistema de simulación de combates entre criaturas fantásticas (Vampiros, Licántropos y Cazadores). El proyecto implementa un ecosistema completo donde los usuarios pueden gestionar personajes, equipar armas/armaduras y participar en una economía basada en oro mediante desafíos validados por operadores.

##  Planificación y Fases de Desarrollo
| Fase | Descripción | Tiempo Estimado |
|------|-------------|------------------|
| 1. Análisis de Requisitos | Definición de lógica de negocio y casos de uso. | 1 Semana |
| 2. Diseño de Arquitectura | Creación de diagramas UML (Clases, Actividad, Secuencia, Estados). | 2 Semanas |
| 3. Core de Dominio (POO) | Implementación de la jerarquía de personajes y esbirros. | 2 Semanas |
| 4. Motor de Combate | Programación de la lógica de dados, éxitos y modificadores. | 2 Semanas |
| 5. Persistencia y Admin | Serialización de datos y panel de control para el Operador. | 2 Semanas |
| 6. QA y Refactorización | Pruebas unitarias intensivas y corrección de errores. | 2 Semanas |

## Teoría Aplicada por Fase
### Fase 1: Ingeniería de Software
- **Análisis Funcional**: Identificación de los roles de Usuario y Operador.
- **Reglas de Negocio**: Gestión de apuestas (pago del 10% por rechazo) y restricciones de tiempo (bloqueo de 24h).

### Fase 2: Modelado UML
- **Diagramas de Estructura**: Definición de herencia para Personajes y tipos de Esbirros.
- **Diagramas de Comportamiento**: Flujo de validación de desafíos por parte del Operador.

### Fase 3: Programación Orientada a Objetos (Java)
- **Herencia y Polimorfismo**: Implementación de clases base para Personajes con especializaciones como Vampiro (sangre), Licántropo (rabia) y Cazador (voluntad).
- **Recursividad**: Manejo de la jerarquía de esbirros demonios que pueden contener otros esbirros.
- **Encapsulamiento**: Protección de atributos como salud (0-5) y poder (1-5).

### Fase 4: Algoritmia y Lógica de Juego
- **Probabilidad**: Lanzamiento de dados (1-6) donde 5 y 6 son éxitos.
- **Modificadores**: Cálculo dinámico de ataque/defensa sumando equipo, habilidades y estados (fortalezas/debilidades).

### Fase 5: Persistencia y Gestión de Datos
- **Serialización**: Guardado persistente de usuarios, personajes e historial de combates (ganador, rondas, fecha).
- **Colecciones (Collections API)**: Gestión de inventarios de armas y armaduras activas.

### Fase 6: Quality Assurance
- **Pruebas Unitarias**: Verificación de que el daño se resta primero a los esbirros y luego al personaje.
- **Validación de Datos**: Asegurar que el Nick es único y la contraseña tiene entre 8 y 12 caracteres.

## Stack Tecnológico
- **Lenguaje**: Java JDK 21.
- **IDE**: IntelliJ.
- **Control de Versiones**: GitHub.
- **Modelado**: PlantText (UML).

## Normas y Recomendaciones
- **Roles Obligatorios**: Analista Funcional, Analista Programador, Ingeniero de Desarrollo y QA.
- **Compilación**: Si el código no compila o no ejecuta, no será evaluado.
- **Defensa**: Cada integrante será preguntado individualmente sobre el código.
- **Consejo**: Prioriza la lógica del combate y la persistencia antes que la interfaz de usuario.
