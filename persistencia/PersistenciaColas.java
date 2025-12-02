package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import excepciones.PersistenciaException;
import interfaces.IPersistencia;
import modelo.Vehiculo;
import negocio.InventarioVehiculos;

public class PersistenciaColas implements IPersistencia<String> {

    @Override
    public void guardar(List<String> ids, String rutaArchivo) throws PersistenciaException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (String id : ids) {
                writer.write(id);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar cola: " + e.getMessage(), e);
        }
    }
//Carga solo los IDs. Para reconstruir la cola real de objetos
    @Override
    public List<String> cargar(String rutaArchivo) throws PersistenciaException {
        List<String> ids = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return ids;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) ids.add(linea.trim());
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al cargar cola", e);
        }
        return ids;
    }

    @Override
    public void realizarRespaldo(String rutaOrigen, String rutaDestino) throws PersistenciaException {
        File origen = new File(rutaOrigen);
        if(!origen.exists()) return;

        try (InputStream in = new FileInputStream(rutaOrigen);
             OutputStream out = new FileOutputStream(rutaDestino)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al crear respaldo de cola", e);
        }
    }
    //Método auxiliar para convertir IDs a Objetos usando el inventario
    public List<Vehiculo> obtenerObjetosPorIds(List<String> ids, InventarioVehiculos inventario) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        for(String id : ids) {
            Vehiculo v = inventario.buscarVehiculo(id);
            if(v != null) vehiculos.add(v);
        }
        return vehiculos;
    }
}