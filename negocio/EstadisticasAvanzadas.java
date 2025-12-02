package negocio;

import java.util.ArrayList;
import java.util.List;
import modelo.Vehiculo;
import modelo.AutomovilCamioneta;
import modelo.Motocicleta;
import utilidades.Contenedor;
import utilidades.Par;
//Clase que utiliza Genéricos para generar estadísticas avanzadas
public class EstadisticasAvanzadas {
    
    public static Par<Contenedor<AutomovilCamioneta>, Contenedor<Motocicleta>> agruparPorTipo(List<Vehiculo> vehiculos) {
        //Agrupa vehículos por tipo usando Contenedores genéricos
        Contenedor<AutomovilCamioneta> autos = new Contenedor<>("Automóviles/Camionetas");
        Contenedor<Motocicleta> motos = new Contenedor<>("Motocicletas");
        
        for (Vehiculo v : vehiculos) {
            if (v instanceof AutomovilCamioneta) {
                autos.agregar((AutomovilCamioneta) v);
            } else if (v instanceof Motocicleta) {
                motos.agregar((Motocicleta) v);
            }
        }
        
        return new Par<>(autos, motos);
    }
    //Genera un reporte de estadísticas por tipo
    public static String generarReportePorTipo(List<Vehiculo> vehiculos) {
        Par<Contenedor<AutomovilCamioneta>, Contenedor<Motocicleta>> grupos = agruparPorTipo(vehiculos);
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== ESTADÍSTICAS POR TIPO DE VEHÍCULO ===\n\n");
        
        Contenedor<AutomovilCamioneta> autos = grupos.getClave();
        Contenedor<Motocicleta> motos = grupos.getValor();
        
        reporte.append(autos.toString()).append("\n");
        reporte.append(motos.toString()).append("\n\n");
        // Calcular porcentajes
        int total = autos.cantidad() + motos.cantidad();
        if (total > 0) {
            double porcentajeAutos = (autos.cantidad() * 100.0) / total;
            double porcentajeMotos = (motos.cantidad() * 100.0) / total;
            
            reporte.append("Distribución:\n");
            reporte.append(String.format("  - Automóviles/Camionetas: %.1f%%\n", porcentajeAutos));
            reporte.append(String.format("  - Motocicletas: %.1f%%\n", porcentajeMotos));
        }
        return reporte.toString();
    }
    //Genera una lista de pares con estadísticas clave-valor
    public static List<Par<String, Integer>> generarEstadisticasGenerales(
            InventarioVehiculos inventario, ColaRevisionMecanica taller, ColaLavadero lavadero) {
        
        List<Par<String, Integer>> estadisticas = new ArrayList<>();
        
        estadisticas.add(new Par<>("Total de vehículos", inventario.getCantidadVehiculos()));
        estadisticas.add(new Par<>("Vehículos nuevos", inventario.getCantidadNuevos()));
        estadisticas.add(new Par<>("Vehículos usados", inventario.getCantidadUsados()));
        estadisticas.add(new Par<>("En taller mecánico", taller.getCantidadEnTaller()));
        estadisticas.add(new Par<>("En lavadero", lavadero.getCantidadEnLavadero()));
        estadisticas.add(new Par<>("Necesitan mantenimiento", inventario.obtenerVehiculosQueNecesitanMantenimiento().size()));
        
        return estadisticas;
    }
    //Formatea las estadísticas generales para mostrar
    public static String formatearEstadisticas(List<Par<String, Integer>> estadisticas) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS GENERALES ===\n\n");
        
        for (Par<String, Integer> par : estadisticas) {
            sb.append(String.format("%-30s: %d\n", par.getClave(), par.getValor()));
        }
        return sb.toString();
    }
}