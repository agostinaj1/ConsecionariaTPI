package negocio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import excepciones.LavaderoException;
import interfaces.ILavable;
import modelo.Vehiculo;
/*Cola de espera para el Lavadero
   Utiliza LinkedList que implementa Queue*/
public class ColaLavadero {

    private Queue<Vehiculo> colaEspera;
    private Set<String> vehiculosEnCola; 

    public ColaLavadero() {
        this.colaEspera = new LinkedList<>();
        this.vehiculosEnCola = new HashSet<>();
    }
//Agrega un vehículo usado a la cola del lavadero
    public void agregarACola(Vehiculo v) throws LavaderoException {
        if (v == null) throw new LavaderoException("El vehículo no puede ser nulo");
        if (!v.esUsado()) throw new LavaderoException("Solo los vehículos USADOS pueden entrar al lavadero");
        if (vehiculosEnCola.contains(v.getIdentificador())) throw new LavaderoException("El vehículo ya está en la cola del lavadero");

        boolean agregado = colaEspera.offer(v);
        if (agregado) vehiculosEnCola.add(v.getIdentificador());
    }
//Realiza lavado básico al siguiente vehículo
    public Vehiculo realizarLavadoBasico() throws LavaderoException {
        if (colaEspera.isEmpty()) throw new LavaderoException("No hay vehículos en la cola del lavadero");

        Vehiculo vehiculo = colaEspera.peek(); 
        if (vehiculo instanceof ILavable) {
            ILavable lavable = (ILavable) vehiculo;
            lavable.realizarLavadoBasico();
        }
        return vehiculo;
    }
//Realiza detallado para exhibición y saca el vehículo de la cola
    public Vehiculo realizarDetalladoExhibicion() throws LavaderoException {
        if (colaEspera.isEmpty()) throw new LavaderoException("No hay vehículos en la cola del lavadero");

        Vehiculo vehiculo = colaEspera.poll(); 
        if (vehiculo != null) {
            vehiculosEnCola.remove(vehiculo.getIdentificador());
            if (vehiculo instanceof ILavable) {
                ILavable lavable = (ILavable) vehiculo;
                lavable.realizarDetalladoExhibicion();
            }
        }
        return vehiculo;
    }
//Ver el siguiente vehículo sin sacarlo de la cola
    public Vehiculo verSiguiente() { return colaEspera.peek(); }
    public int cantidadEnEspera() { return colaEspera.size(); }
    public boolean estaVacia() { return colaEspera.isEmpty(); }
    public List<Vehiculo> obtenerVehiculosEnCola() { return new ArrayList<>(colaEspera); }
    public void limpiarCola() { colaEspera.clear(); vehiculosEnCola.clear(); }
    public int getCantidadEnLavadero() { return cantidadEnEspera(); }
}