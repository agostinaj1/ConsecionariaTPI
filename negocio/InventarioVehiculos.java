package negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import enumerativos.CondicionVehiculo;
import excepciones.InventarioException;
import excepciones.VehiculoInvalidoException;
import interfaces.IMantenible;
import modelo.Vehiculo;

public class InventarioVehiculos {
    //utilizamos HashMap para búsquedas eficientes por ID (O(1))
    private Map<String, Vehiculo> inventario;
    
    public InventarioVehiculos() {
        this.inventario = new HashMap<>();
    }
    //Agrega un vehículo al inventario
    public void agregarVehiculo(Vehiculo vehiculo) throws VehiculoInvalidoException, InventarioException {
        if (vehiculo == null) throw new InventarioException("El vehículo no puede ser nulo");
        vehiculo.validar();
        if (inventario.containsKey(vehiculo.getIdentificador())) {
            throw new InventarioException("Ya existe un vehículo con la patente: " + vehiculo.getIdentificador());
        }
        inventario.put(vehiculo.getIdentificador(), vehiculo);
    }
    //Elimina un vehículo del inventario
    public boolean eliminarVehiculo(String identificador) throws InventarioException {
        if (identificador == null || identificador.trim().isEmpty()) throw new InventarioException("El identificador no puede estar vacío");
        Vehiculo removido = inventario.remove(identificador);
        return removido != null;
    }
    //Busca un vehículo por identificador
    public Vehiculo buscarVehiculo(String identificador) {
        return inventario.get(identificador);
    }
    //Obtiene todos los vehículos del inventario
    public Collection<Vehiculo> obtenerTodosVehiculos() {
        return new ArrayList<>(inventario.values());
    }
    //Obtiene solo los vehículos usados
    public List<Vehiculo> obtenerVehiculosUsados() {
        List<Vehiculo> usados = new ArrayList<>();
        for (Vehiculo v : inventario.values()) {
            if (v.esUsado()) usados.add(v);
        }
        return usados;
    }
    
    public int cantidadTotal() { return inventario.size(); }
    public int cantidadUsados() { return obtenerVehiculosUsados().size(); }
    //Obtiene vehículos que necesitan mantenimiento
    public List<Vehiculo> obtenerVehiculosQueNecesitanMantenimiento() {
        List<Vehiculo> necesitanMantenimiento = new ArrayList<>();
        for (Vehiculo v : inventario.values()) {
            if (v instanceof IMantenible) {
                IMantenible mantenible = (IMantenible) v;
                if (mantenible.necesitaMantenimiento()) necesitanMantenimiento.add(v);
            }
        }
        return necesitanMantenimiento;
    }
// Devuelve la cantidad total de vehículos
    public int getCantidadVehiculos() {
        Collection<Vehiculo> todos = obtenerTodosVehiculos(); 
        return todos == null ? 0 : todos.size();
    }
// Devuelve la cantidad de vehículos usados
    public int getCantidadUsados() {
        Collection<Vehiculo> todos = obtenerTodosVehiculos();
        if (todos == null) return 0;
        return (int) todos.stream().filter(v -> v.getCondicion() == CondicionVehiculo.USADO).count();
    }
// Devuelve la cantidad de vehículos nuevos
    public int getCantidadNuevos() {
        Collection<Vehiculo> todos = obtenerTodosVehiculos();
        if (todos == null) return 0;
        return (int) todos.stream().filter(v -> v.getCondicion() == CondicionVehiculo.NUEVO).count();
    }
}