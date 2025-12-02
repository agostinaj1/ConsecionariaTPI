package excepciones;

public class VehiculoInvalidoException extends Exception {
    public VehiculoInvalidoException(String mensaje) {
        super(mensaje);
    }
    
    public VehiculoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}