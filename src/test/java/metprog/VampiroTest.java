package metprog;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import metprog.model.Arma;
import metprog.model.Armadura;
import metprog.model.Disciplina;
import metprog.model.EsbirroDemonio;
import metprog.model.EsbirroGhoul;
import metprog.model.EsbirroHumano;
import metprog.model.Vampiro;
import metprog.model.enums.Lealtad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitarios para la clase Vampiro.
 *
 * <p>Cubre la lógica de gestión de puntos de sangre, restricciones de esbirros,
 * uso de Disciplinas y estado del equipo activo.
 */
class VampiroTest {

  private Vampiro vampiro;
  private Disciplina disciplina;
  private Arma espada;
  private Armadura capa;

  @BeforeEach
  void setUp() {
    vampiro = new Vampiro("Drácula", 5, 4, 100);
    disciplina = new Disciplina("Dominar", 2, 1, 2);
    espada = new Arma("Espada Oscura", 2, 0, false);
    capa = new Armadura("Capa de Sombras", 0, 2);
    vampiro.setHabilidad(disciplina);
    vampiro.equiparArma(espada);
    vampiro.agregarArmadura(capa);
  }

  // --- Pruebas de Puntos de Sangre ---

  @Test
  void puntosSangreInicialesSon5() {
    assertEquals(5, vampiro.getPuntosSangre());
  }

  @Test
  void gastarSangreConSuficienteReduce() {
    assertTrue(vampiro.gastarSangre(2));
    assertEquals(3, vampiro.getPuntosSangre());
  }

  @Test
  void gastarSangreSinSaldoDevuelveFalseYNoModifica() {
    vampiro.setPuntosSangre(1);
    assertFalse(vampiro.gastarSangre(3));
    assertEquals(1, vampiro.getPuntosSangre());
  }

  @Test
  void recuperarSangreNoSuperaMaximoDeDiez() {
    vampiro.setPuntosSangre(8);
    vampiro.recuperarSangre(5);
    assertEquals(10, vampiro.getPuntosSangre());
  }

  @Test
  void setPuntosSangreNegativoSeCorrigeACero() {
    vampiro.setPuntosSangre(-1);
    assertEquals(0, vampiro.getPuntosSangre());
  }

  @Test
  void setPuntosSangreMayorDeDiezSeCorrigeADiez() {
    vampiro.setPuntosSangre(11);
    assertEquals(10, vampiro.getPuntosSangre());
  }

  // --- Pruebas de Disciplina ---

  @Test
  void getDisciplinaDevuelveLaAsignada() {
    assertNotNull(vampiro.getDisciplina());
    assertEquals("Dominar", vampiro.getDisciplina().getNombre());
    assertEquals(2, vampiro.getDisciplina().getCosteSangre());
  }

  @Test
  void disciplinaConCosteInvalidoSeCorrigeAUno() {
    Disciplina d = new Disciplina("Test", 1, 1, 5);
    assertEquals(1, d.getCosteSangre());
  }

  // --- Regla: sin esbirros humanos ---

  @Test
  void anadirEsbirroHumanoLanzaExcepcion() {
    EsbirroHumano h = new EsbirroHumano("Sirviente", 2, Lealtad.ALTA);
    assertThrows(UnsupportedOperationException.class, () -> vampiro.agregarEsbirro(h));
  }

  @Test
  void anadirEsbirroGhoulFuncionaCorrectamente() {
    EsbirroGhoul g = new EsbirroGhoul("Igor", 3, 4);
    assertDoesNotThrow(() -> vampiro.agregarEsbirro(g));
    assertEquals(1, vampiro.getEsbirros().size());
  }

  @Test
  void anadirEsbirroDemonioFuncionaCorrectamente() {
    EsbirroDemonio d = new EsbirroDemonio("Asmodeo", 2, "Pacto eterno");
    assertDoesNotThrow(() -> vampiro.agregarEsbirro(d));
  }

  // --- Pruebas de Equipo Activo ---

  @Test
  void tieneEquipoActivoConArmaYArmadura() {
    vampiro.setArmasActivas(List.of(espada));
    vampiro.setArmaduraActiva(capa);
    assertTrue(vampiro.tieneEquipoActivo());
  }

  @Test
  void sinArmaActivaNotieneEquipoActivo() {
    vampiro.limpiarArmasActivas();
    vampiro.setArmaduraActiva(capa);
    assertFalse(vampiro.tieneEquipoActivo());
  }

  // --- Pruebas de Reinicio de Combate ---

  @Test
  void reinicioRestauraSangreYSalud() {
    vampiro.setPuntosSangre(1);
    vampiro.setSalud(2);
    vampiro.reiniciarParaCombate();
    assertEquals(5, vampiro.getPuntosSangre());
    assertEquals(5, vampiro.getSalud());
  }
}