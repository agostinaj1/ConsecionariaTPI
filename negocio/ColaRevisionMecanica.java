package negocio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import excepciones.TallerException;
import interfaces.IMantenible;
import modelo.Vehiculo;

// usa queue como en cola lavadero
public class ColaRevisionMecanica {

    private Queue<Vehiculo> colaEspera;
    private Set<String> vehiculosEnCola;// Para evitar duplicados

    public ColaRevisionMecanica() {
        this.colaEspera = new LinkedList<>();
        this.vehiculosEnCola = new HashSet<>();
    }
//Agrega un vehículo a la cola de espera
    public void agregarACola(modelo.Vehiculo v) throws excepciones.TallerException {
        if (v == null) throw new TallerException("El vehículo no puede ser nulo");
        if (!v.esUsado()) throw new TallerException("Solo los vehículos usados necesitan revisión mecánica");
        if (vehiculosEnCola.contains(v.getIdentificador())) throw new TallerException("El vehículo ya está en la cola de espera");

        // Al entrar a la cola, se resetea su estado a "Pendiente" automáticamente
        if (v instanceof modelo.AutomovilCamioneta) {
            ((modelo.AutomovilCamioneta) v).setEstadoMantenimiento(enumerativos.EstadoMantenimiento.PENDIENTE);
        } else if (v instanceof modelo.Motocicleta) {
            ((modelo.Motocicleta) v).setEstadoMantenimiento(enumerativos.EstadoMantenimiento.PENDIENTE);
        }

        v.resetearEstadoMantenimiento();

        boolean agregado = colaEspera.offer(v);
        if (agregado) {
            vehiculosEnCola.add(v.getIdentificador());
        }
    }

    public Vehiculo atenderSiguiente() throws TallerException {
        if (colaEspera.isEmpty()) throw new TallerException("No hay vehículos en la cola de espera");

        Vehiculo vehiculo = colaEspera.poll(); // Saca el vehículo
        if (vehiculo != null) {
            vehiculosEnCola.remove(vehiculo.getIdentificador());
            if (vehiculo instanceof IMantenible) {// Realizar el mantenimiento
                ((IMantenible) vehiculo).realizarMantenimiento();
            }
        }
        return vehiculo;
    }

    public Vehiculo verSiguiente() { return colaEspera.peek(); }//Ver el siguiente vehículo sin sacarlo de la cola
    public int cantidadEnEspera() { return colaEspera.size(); }//Cantidad de vehículos en espera
    public boolean estaVacia() { return colaEspera.isEmpty(); }//Verifica si la cola está vacía
    public List<Vehiculo> obtenerVehiculosEnCola() { return new ArrayList<>(colaEspera); }//Obtiene todos los vehículos en la cola
    public void limpiarCola() { colaEspera.clear(); vehiculosEnCola.clear(); }
    public int getCantidadEnTaller() { return cantidadEnEspera(); }
}