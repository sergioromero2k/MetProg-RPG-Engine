# Cómo trabajar con ramas en GitHub

## Antes de empezar — clonar el repositorio

Cada persona ejecuta esto una sola vez en su ordenador:

```bash
git clone https://github.com/nombre-del-repo/metprog-combat-game.git
cd metprog-combat-game
```

---

## Crear tu rama personal

Cada programador crea su propia rama. Solo se hace una vez al principio.

**Programador 1:**
```bash
git checkout -b rama-p1
```

**Programador 2:**
```bash
git checkout -b rama-p2
```

**Programador 3:**
```bash
git checkout -b rama-p3
```

El comando `git checkout -b` crea la rama Y te mueve a ella automáticamente.
Para comprobar en qué rama estás en cualquier momento:

```bash
git branch
```

La rama marcada con `*` es en la que estás trabajando.

---

## Flujo de trabajo diario

Esto es lo que hace cada programador cada vez que trabaja en el proyecto.

**Paso 1 — Asegúrate de estar en tu rama:**
```bash
git checkout rama-p1
```

**Paso 2 — Descarga los últimos cambios de main:**
```bash
git pull origin main
```

**Paso 3 — Programa tu código en IntelliJ normalmente.**

**Paso 4 — Ver qué archivos has modificado:**
```bash
git status
```

**Paso 5 — Añadir los cambios:**
```bash
git add .
```

**Paso 6 — Guardar los cambios con un mensaje descriptivo:**
```bash
git commit -m "Añade clase Vampiro con puntosSangre y edad"
```

**Paso 7 — Subir tu rama a GitHub:**
```bash
git push origin rama-p1
```

Repite los pasos 3 al 7 cada vez que termines de trabajar ese día.

---

## Normas de los mensajes de commit

El mensaje del commit debe explicar qué hiciste. El profesor ve el historial.

```bash
# Bien
git commit -m "Implementa patrón State con estados Pendiente y Validado"
git commit -m "Añade clase EsbirroDemonio con jerarquía recursiva"
git commit -m "Conecta MenuJugador con GestorUsuarios"

# Mal
git commit -m "fix"
git commit -m "cambios"
git commit -m "cosas"
```

---

## Lo que hace el Programador 1 al final de cada día

El Programador 1 es el jefe técnico y es el único que hace el merge a main.

**Paso 1 — Cambiar a main:**
```bash
git checkout main
```

**Paso 2 — Descargar los cambios de las tres ramas:**
```bash
git pull origin rama-p1
git pull origin rama-p2
git pull origin rama-p3
```

**Paso 3 — Subir main actualizado:**
```bash
git push origin main
```

Si hay conflictos (dos personas modificaron el mismo archivo), IntelliJ tiene una herramienta visual para resolverlos. Ir a `Git > Resolve Conflicts` en el menú superior.

---

## Resumen visual

```
main         <- Solo el Programador 1 hace merge aquí al final del día
  │
  ├── rama-p1   <- Programador 1 trabaja aquí
  ├── rama-p2   <- Programador 2 trabaja aquí
  └── rama-p3   <- Programador 3 trabaja aquí
```

---

## Reglas básicas

- Nunca hacer `git push origin main` directamente. Solo el Programador 1 toca main.
- Siempre hacer `git pull origin main` antes de empezar a trabajar para tener la última versión.
- Commit al menos una vez al día aunque no hayas terminado la tarea.
- Si algo se rompe, `git status` y `git log` son tus mejores amigos para ver qué pasó.