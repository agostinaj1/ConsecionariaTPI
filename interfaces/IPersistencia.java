package interfaces;

import java.util.List;
import excepciones.PersistenciaException;

public interface IPersistencia<T> {
    void guardar(List<T> datos, String rutaArchivo) throws PersistenciaException;
    List<T> cargar(String rutaArchivo) throws PersistenciaException;
    void realizarRespaldo(String rutaOrigen, String rutaDestino) throws PersistenciaException;
}