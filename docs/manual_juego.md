# Manual de Usuario — MetProg RPG Engine

## Introducción

MetProg RPG Engine es un videojuego de combate por consola donde criaturas fantásticas (Vampiros, Licántropos y Cazadores) se enfrentan entre sí apostando oro. Existen dos tipos de usuario: el **Jugador** que gestiona su personaje y participa en combates, y el **Operador** que administra el sistema y valida los desafíos.

---

## Requisitos

- Java JDK 21 o superior instalado
- Ejecutar desde IntelliJ IDEA o desde terminal

---

## Cómo ejecutar el programa

Desde IntelliJ IDEA abre `Main.java` y pulsa el botón ▶️ o `Shift + F10`.

---

## Flujo completo de juego paso a paso

### Paso 1 — Registrar dos jugadores

Al arrancar el programa verás el menú principal:

```
=== Bienvenido a MetProg RPG Engine ===
1. Jugar
2. Operador
0. Salir
```

Selecciona **1 → Jugar** y luego **2 → Registrar usuario**:

```
Nombre: Sergio
Nick: sergio123
Contraseña (entre 8 y 12 caracteres): sergio123
```

La contraseña debe tener entre 8 y 12 caracteres obligatoriamente.

Sal con **0** y repite el proceso para registrar un segundo jugador:

```
Nombre: Arturo
Nick: arturo123
Contraseña (entre 8 y 12 caracteres): arturo123
```

---

### Paso 2 — Registrar un operador

Desde el menú principal selecciona **2 → Operador** y luego **2 → Registrar operador**:

```
Nombre: Admin
Nick: admin123
Contraseña (entre 8 y 12 caracteres): admin1234
```

Sal con **0**.

---

### Paso 3 — Crear personaje con jugador 1

Desde el menú principal selecciona **1 → Jugar → 1 → Iniciar sesión** con las credenciales del jugador 1.

Una vez dentro del menú jugador selecciona **1 → Crear personaje**:

```
Elige tipo de personaje:
1. Vampiro
2. Licántropo
3. Cazador
Opción: 1
Nombre del personaje: Drácula
```

El personaje se crea automáticamente con equipo inicial (arma y armadura). Ya está listo para combatir.

---

### Paso 4 — Crear personaje con jugador 2

Sal con **0** e inicia sesión con el jugador 2. Repite el proceso de crear personaje.

---

### Paso 5 — Jugador 1 lanza un desafío

Inicia sesión con jugador 1 y selecciona **6 → Lanzar desafío**:

```
Nick del desafiado: arturo123
Oro apostado: 50
```

El desafío queda pendiente de validación por el operador.

---

### Paso 6 — Operador valida el desafío

Sal con **0** e inicia sesión como operador. Selecciona **7 → Validar desafío**.

Verás el desafío pendiente. Selecciónalo y el sistema te pedirá los modificadores activos. Si no quieres añadir ninguno pulsa Enter directamente:

```
Fortalezas del desafiante: (Enter para ninguna)
Debilidades del desafiante: (Enter para ninguna)
Fortalezas del desafiado: (Enter para ninguna)
Debilidades del desafiado: (Enter para ninguna)
```

Si quieres añadir modificadores el formato es `nombre:valor` separados por coma:

```
Fortalezas del desafiante: luna llena:3, fuerza oscura:2
```

El desafío queda publicado y el jugador 2 recibirá la notificación.

---

### Paso 7 — Jugador 2 acepta el desafío y combaten

Sal con **0** e inicia sesión con jugador 2. El sistema te avisará:

```
AVISO: Tienes un desafío pendiente de sergio123
```

Selecciona **8 → Aceptar desafío recibido**. El combate se ejecuta automáticamente y verás el resultado:

```
═══════════════════════════════════════
  RESULTADO DEL COMBATE
  sergio123 vs arturo123
  Fecha: 2024-05-01
  Rondas: 5
───────────────────────────────────────
  Ronda 1: ...
  Ronda 2: ...
───────────────────────────────────────
  VENCEDOR: sergio123
  ORO GANADO: 50
═══════════════════════════════════════
```

---

## Opciones del menú jugador

| Opción | Descripción |
|--------|-------------|
| 1 | Crear personaje (Vampiro, Licántropo o Cazador) |
| 2 | Ver datos de tu personaje |
| 3 | Ver inventario de armas |
| 4 | Ver inventario de armaduras |
| 5 | Ver esbirros |
| 6 | Lanzar desafío a otro jugador |
| 7 | Ver desafío recibido |
| 8 | Aceptar desafío recibido |
| 9 | Rechazar desafío recibido (penalización del 10% del oro apostado) |
| 10 | Ver ranking global ordenado por oro |
| 11 | Ver historial de combates |
| 12 | Ver historial de oro ganado y perdido |
| 0 | Cerrar sesión |

---

## Opciones del menú operador

| Opción | Descripción |
|--------|-------------|
| 1 | Registrar nuevo operador |
| 2 | Registrar nuevo usuario |
| 3 | Dar de baja a un usuario |
| 4 | Dar de baja a un operador |
| 5 | Bloquear usuario |
| 6 | Desbloquear usuario |
| 7 | Validar desafío pendiente |
| 8 | Ver desafíos pendientes de validación |
| 9 | Ver todos los desafíos registrados |
| 10 | Ver historial de combates |
| 11 | Guardar datos manualmente |
| 12 | Cargar datos manualmente |
| 0 | Cerrar sesión |

---

## Reglas del juego

**Personajes:**
- Vampiro: tiene puntos de sangre (0-10). Si tiene 5 o más sangre suma 2 al potencial de ataque. Recupera 4 puntos de sangre al atacar con éxito.
- Licántropo: tiene rabia (0-3). Empieza en 0 cada combate y sube 1 al recibir daño. La rabia suma al potencial de ataque.
- Cazador: tiene voluntad (0-3). Empieza en 3 cada combate y baja 1 al recibir daño. La voluntad suma al potencial de ataque.

**Combate:**
- Cada ronda ambos personajes atacan y defienden simultáneamente.
- El potencial de ataque y defensa determina cuántos dados se lanzan.
- Un dado cuenta como éxito si sale 5 o 6.
- Si los éxitos de ataque son iguales o mayores que los de defensa el rival pierde 1 punto de salud.
- Los esbirros actúan como escudo antes de que el daño llegue al personaje.

**Desafíos:**
- El oro apostado no puede ser negativo ni superar el oro disponible.
- No se puede desafiar a un jugador que perdió en las últimas 24 horas.
- Si el desafiado rechaza el desafío paga el 10% del oro apostado como penalización.
- En caso de empate nadie pierde oro.

**Restricciones:**
- Los vampiros no pueden tener esbirros humanos.
- La contraseña debe tener entre 8 y 12 caracteres.
- El nick debe ser único en el sistema.

---

## Persistencia

Los datos se guardan automáticamente al cerrar el programa en la carpeta `datos/`. Al volver a abrir el programa todos los usuarios, personajes, desafíos y combates se recuperan automáticamente.

---

*MetProg RPG Engine — URJC 2023/2024 — Metodología de la Programación*
