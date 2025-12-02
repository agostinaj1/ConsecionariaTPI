package excepciones;

public class InventarioException extends Exception {
    public InventarioException(String mensaje) {
        super(mensaje);
    }
}