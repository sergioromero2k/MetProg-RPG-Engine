package metprog.service;

import metprog.model.*;
import metprog.state.Publicado;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona el ciclo de vida completo de los desafíos entre jugadores.
 *
 * <p>Controla la creación, validación por parte del operador, aceptación,
 * rechazo y finalización de desafíos, aplicando todas las reglas de negocio
 * definidas en el enunciado.
 */
public class GestorDesafios {

    private final List<Desafio> desafios = new ArrayList<>();
    private final List<Combate> historialCombates = new ArrayList<>();

    // ── Crear desafío ─────────────────────────────────────────────────────────

    /**
     * Crea un nuevo desafío entre dos usuarios validando todas las reglas.
     *
     * <p>Restricciones comprobadas:
     * <ul>
     *   <li>El desafiante no puede desafiar a sí mismo.</li>
     *   <li>El desafiante no puede estar bloqueado.</li>
     *   <li>Ambos usuarios deben tener equipo activo.</li>
     *   <li>El oro apostado debe ser positivo y no superar el oro del desafiante.</li>
     *   <li>El desafiado no puede tener ya un desafío publicado/pendiente activo.</li>
     *   <li>No se puede desafiar a un usuario que perdió en las últimas 24 h.</li>
     * </ul>
     *
     * @param desafiante  usuario que lanza el reto.
     * @param desafiado   usuario al que se desafía.
     * @param oroApostado cantidad de oro apostada.
     * @return el desafío creado, o {@code null} si alguna regla se incumple.
     */
    public Desafio crearDesafio(Usuario desafiante, Usuario desafiado, int oroApostado) {
        if (desafiante == null || desafiado == null) {
            return null;
        }
        // No puede desafiarse a sí mismo
        if (desafiante == desafiado || desafiante.getNick().equals(desafiado.getNick())) {
            return null;
        }
        // Desafiante bloqueado
        if (desafiante.isBloqueado()) {
            return null;
        }
        // Ambos deben tener equipo activo
        if (!desafiante.puedeDesafiar()) {
            return null;
        }
        if (desafiado.getPersonaje() == null || !desafiado.getPersonaje().tieneEquipoActivo()) {
            return null;
        }
        // Restricciones de oro
        if (oroApostado < 0) {
            return null;
        }
        if (desafiante.getPersonaje() != null
                && oroApostado > desafiante.getPersonaje().getOro()) {
            return null;
        }
        // El desafiado no puede tener ya un desafío activo (pendiente o publicado)
        if (tieneDesafioActivo(desafiado)) {
            return null;
        }
        // No se puede desafiar a alguien que perdió en las últimas 24 h
        if (desafiado.haPerdidoEnUltimas24h()) {
            return null;
        }

        try {
            Desafio d = new Desafio(desafiante, desafiado, oroApostado);
            desafios.add(d);
            return d;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // ── Validar desafío (operador) ────────────────────────────────────────────

    /**
     * El operador valida el desafío y decide qué modificadores están presentes.
     *
     * <p>Tras la validación el desafío pasa al estado {@code Publicado} y el
     * desafiado recibe la notificación la próxima vez que entre al sistema.
     *
     * @param desafio             desafío a validar.
     * @param fortDesafiante      fortalezas presentes del desafiante.
     * @param debDesafiante       debilidades presentes del desafiante.
     * @param fortDesafiado       fortalezas presentes del desafiado.
     * @param debDesafiado        debilidades presentes del desafiado.
     * @return {@code true} si la transición se realizó correctamente.
     */
    public boolean validarDesafio(Desafio desafio,
                                  List<Fortaleza> fortDesafiante,
                                  List<Debilidad> debDesafiante,
                                  List<Fortaleza> fortDesafiado,
                                  List<Debilidad> debDesafiado) {
        if (desafio == null) {
            return false;
        }
        desafio.setFortalezasDesafiante(fortDesafiante != null ? fortDesafiante : new ArrayList<>());
        desafio.setDebilidadesDesafiante(debDesafiante != null ? debDesafiante : new ArrayList<>());
        desafio.setFortalezasDesafiado(fortDesafiado != null ? fortDesafiado : new ArrayList<>());
        desafio.setDebilidadesDesafiado(debDesafiado != null ? debDesafiado : new ArrayList<>());

        desafio.validar(); // Pendiente → Publicado
        return desafio.getEstado() instanceof Publicado;
    }

    // ── Aceptar desafío ───────────────────────────────────────────────────────

    /**
     * El desafiado acepta el desafío publicado.
     *
     * <p>Aplica los modificadores acordados al personaje de cada jugador y
     * transiciona el estado a {@code EnCombate}.
     *
     * @param desafio desafío a aceptar.
     * @return {@code true} si la transición se realizó correctamente.
     */
    public boolean aceptarDesafio(Desafio desafio) {
        if (desafio == null) {
            return false;
        }
        String estadoAntes = desafio.getEstado().getClass().getSimpleName();
        desafio.aceptar();
        // Aplicar modificadores a los personajes para el combate
        Personaje pDesafiante = desafio.getDesafiante().getPersonaje();
        Personaje pDesafiado  = desafio.getDesafiado().getPersonaje();
        if (pDesafiante != null) {
            pDesafiante.setFortalezasPresentes(
                    new ArrayList<>(desafio.getFortalezasDesafiante()));
            pDesafiante.setDebilidadesPresentes(
                    new ArrayList<>(desafio.getDebilidadesDesafiante()));
        }
        if (pDesafiado != null) {
            pDesafiado.setFortalezasPresentes(
                    new ArrayList<>(desafio.getFortalezasDesafiado()));
            pDesafiado.setDebilidadesPresentes(
                    new ArrayList<>(desafio.getDebilidadesDesafiado()));
        }
        return !desafio.getEstado().getClass().getSimpleName().equals(estadoAntes);
    }

    // ── Rechazar desafío ─────────────────────────────────────────────────────

    /**
     * El desafiado rechaza el desafío publicado.
     *
     * <p>Se aplica automáticamente la penalización del 10 % del oro apostado.
     *
     * @param desafio desafío a rechazar.
     */
    public void rechazarDesafio(Desafio desafio) {
        if (desafio == null) {
            return;
        }
        desafio.rechazar();
        desafio.aplicarPenalizacionRechazo();
    }

    // ── Finalizar desafío ─────────────────────────────────────────────────────

    /**
     * Registra el resultado del combate y transfiere el oro al vencedor.
     *
     * <p>Si el combate es un empate, nadie pierde oro.
     * Si hay vencedor, recibe el oro apostado procedente del perdedor.
     * El perdedor registra la fecha de su derrota (necesaria para la regla de 24 h).
     *
     * @param desafio desafío que se finaliza.
     * @param combate resultado del combate calculado por el MotorCombate.
     */
    public void finalizarDesafio(Desafio desafio, Combate combate) {
        if (desafio == null || combate == null) {
            return;
        }
        desafio.setCombate(combate);
        combate.setOroGanado(desafio.getOroApostado());

        if (!combate.esEmpate()) {
            Usuario vencedor = combate.getVencedor();
            // El perdedor es el otro contendiente
            Usuario perdedor = vencedor == desafio.getDesafiante()
                    ? desafio.getDesafiado()
                    : desafio.getDesafiante();

            Personaje pVencedor = vencedor.getPersonaje();
            Personaje pPerdedor = perdedor.getPersonaje();

            if (pVencedor != null && pPerdedor != null) {
                int oro = desafio.getOroApostado();
                pVencedor.setOro(pVencedor.getOro() + oro);
                pPerdedor.setOro(Math.max(0, pPerdedor.getOro() - oro));

                vencedor.registrarGananciaOro(oro,
                        "Victoria vs " + perdedor.getNick());
                perdedor.registrarPerdidaOro(oro,
                        "Derrota vs " + vencedor.getNick());
            }
            // Registrar fecha de derrota para la restricción de 24 h
            perdedor.setUltimaDerrota(combate.getFechaCombate());
        }

        desafio.finalizar(); // EnCombate → Completado
        historialCombates.add(combate);
    }

    // ── Consultas ─────────────────────────────────────────────────────────────

    /**
     * Devuelve todos los desafíos en estado {@code Pendiente} (pendientes de
     * validación por el operador).
     *
     * @return lista de desafíos pendientes.
     */
    public List<Desafio> getDesafiosPendientes() {
        List<Desafio> resultado = new ArrayList<>();
        for (Desafio d : desafios) {
            if ("Pendiente".equals(d.getEstado().getClass().getSimpleName())) {
                resultado.add(d);
            }
        }
        return resultado;
    }

    /**
     * Devuelve el desafío publicado que tiene como destinatario al usuario dado,
     * si existe alguno.
     *
     * @param desafiado usuario desafiado.
     * @return el desafío publicado, o {@code null} si no hay ninguno.
     */
    public Desafio getDesafioPublicadoParaUsuario(Usuario desafiado) {
        for (Desafio d : desafios) {
            if ("Publicado".equals(d.getEstado().getClass().getSimpleName())
                    && d.getDesafiado() == desafiado) {
                return d;
            }
        }
        return null;
    }

    /**
     * Devuelve todos los desafíos registrados en el sistema.
     *
     * @return lista completa de desafíos.
     */
    public List<Desafio> getDesafios() {
        return new ArrayList<>(desafios);
    }

    /**
     * Devuelve el historial completo de combates finalizados.
     *
     * @return lista de combates.
     */
    public List<Combate> getHistorialCombates() {
        return new ArrayList<>(historialCombates);
    }

    /**
     * Reemplaza la lista interna de desafíos (usado al cargar desde persistencia).
     *
     * @param lista nueva lista de desafíos.
     */
    public void setDesafios(List<Desafio> lista) {
        desafios.clear();
        desafios.addAll(lista);
    }

    /**
     * Reemplaza el historial de combates (usado al cargar desde persistencia).
     *
     * @param lista nueva lista de combates.
     */
    public void setHistorialCombates(List<Combate> lista) {
        historialCombates.clear();
        historialCombates.addAll(lista);
    }

    // ── Utilidades privadas ───────────────────────────────────────────────────

    /**
     * Comprueba si el usuario ya es destinatario de un desafío activo
     * (pendiente de validación o publicado esperando respuesta).
     */
    private boolean tieneDesafioActivo(Usuario usuario) {
        for (Desafio d : desafios) {
            String estado = d.getEstado().getClass().getSimpleName();
            if (d.getDesafiado() == usuario
                    && ("Pendiente".equals(estado) || "Publicado".equals(estado))) {
                return true;
            }
        }
        return false;
    }
}