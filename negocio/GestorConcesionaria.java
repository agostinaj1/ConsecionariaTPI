package negocio;

import java.util.ArrayList;
import java.util.List;
import excepciones.InventarioException;
import excepciones.LavaderoException;
import excepciones.PersistenciaException;
import excepciones.TallerException;
import excepciones.VehiculoInvalidoException;
import modelo.Vehiculo;
import persistencia.PersistenciaColas;
import persistencia.PersistenciaVehiculos;

public class GestorConcesionaria {
    private ColaRevisionMecanica taller;
    private ColaLavadero lavadero;
    private InventarioVehiculos inventario;
    
    private PersistenciaVehiculos persistenciaVehiculos;
    private PersistenciaColas persistenciaColas;
    
    private static final String ARCHIVO_VEHICULOS = "vehiculos.csv";
    private static final String ARCHIVO_COLA_TALLER = "cola_taller.txt";
    private static final String ARCHIVO_COLA_LAVADERO = "cola_lavadero.txt";
    private static final String SUFIJO_BACKUP = "_backup";

    public GestorConcesionaria() {
        this.taller = new ColaRevisionMecanica();
        this.lavadero = new ColaLavadero();
        this.inventario = new InventarioVehiculos();
        this.persistenciaVehiculos = new PersistenciaVehiculos();
        this.persistenciaColas = new PersistenciaColas();
    }
//Guarda todo el estado del sistema (Inventario y Colas)
    public void guardarDatos() throws PersistenciaException {//Guardar Vehículos
        List<Vehiculo> listaVehiculos = new ArrayList<>(inventario.obtenerTodosVehiculos());
        persistenciaVehiculos.guardar(listaVehiculos, ARCHIVO_VEHICULOS);
//Guardar Cola Taller (Solo IDs)
        List<String> idsTaller = new ArrayList<>();
        for(Vehiculo v : taller.obtenerVehiculosEnCola()) {
            idsTaller.add(v.getIdentificador());
        }
        persistenciaColas.guardar(idsTaller, ARCHIVO_COLA_TALLER);
//Guardar Cola Lavadero (Solo IDs)
        List<String> idsLavadero = new ArrayList<>();
        for(Vehiculo v : lavadero.obtenerVehiculosEnCola()) {
            idsLavadero.add(v.getIdentificador());
        }
        persistenciaColas.guardar(idsLavadero, ARCHIVO_COLA_LAVADERO);
    }

    public void cargarDatos() throws PersistenciaException, VehiculoInvalidoException, InventarioException, TallerException, LavaderoException {
        inventario = new InventarioVehiculos();
        taller.limpiarCola();
        lavadero.limpiarCola();
//cargar Vehículos al Inventario
        List<Vehiculo> vehiculosCargados = persistenciaVehiculos.cargar(ARCHIVO_VEHICULOS);
        for(Vehiculo v : vehiculosCargados) {
            inventario.agregarVehiculo(v);
        }
//Reconstruir Cola Taller
        List<String> idsTaller = persistenciaColas.cargar(ARCHIVO_COLA_TALLER);
        for(String id : idsTaller) {
            Vehiculo v = inventario.buscarVehiculo(id);
            if(v != null) {
                taller.agregarACola(v);
            }
        }
//Reconstruir Cola Lavadero
        List<String> idsLavadero = persistenciaColas.cargar(ARCHIVO_COLA_LAVADERO);
        for(String id : idsLavadero) {
            Vehiculo v = inventario.buscarVehiculo(id);
            if(v != null) {
                lavadero.agregarACola(v);
            }
        }
    }
//Genera copias de seguridad de los archivos actuales
    public void generarRespaldo() throws PersistenciaException {
        persistenciaVehiculos.realizarRespaldo(ARCHIVO_VEHICULOS, "vehiculos" + SUFIJO_BACKUP + ".csv");
        persistenciaColas.realizarRespaldo(ARCHIVO_COLA_TALLER, "cola_taller" + SUFIJO_BACKUP + ".txt");
        persistenciaColas.realizarRespaldo(ARCHIVO_COLA_LAVADERO, "cola_lavadero" + SUFIJO_BACKUP + ".txt");
    }

    public void autoGuardar() {
        try {
            guardarDatos();
        } catch (PersistenciaException e) {
            System.err.println("Error en auto-guardado: " + e.getMessage());
        }
    }

    public InventarioVehiculos getInventario() { return inventario; }
    public ColaRevisionMecanica getTaller() { return taller; }
    public ColaLavadero getLavadero() { return lavadero; }

    public String obtenerEstadisticas() {
        int totalVehiculos = inventario.getCantidadVehiculos();
        int usados = inventario.getCantidadUsados();
        int nuevos = inventario.getCantidadNuevos();
        int enTaller = taller.getCantidadEnTaller();
        int enLavadero = lavadero.getCantidadEnLavadero();

        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS DEL SISTEMA ===\n");
        sb.append("Total de vehículos: ").append(totalVehiculos).append("\n");
        sb.append("Vehículos nuevos: ").append(nuevos).append("\n");
        sb.append("Vehículos usados: ").append(usados).append("\n");
        sb.append("Vehículos en taller: ").append(enTaller).append("\n");
        sb.append("Vehículos en lavadero: ").append(enLavadero).append("\n");

        return sb.toString();
    }
}