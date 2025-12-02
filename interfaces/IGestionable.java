package interfaces;

public interface IGestionable {
    void agregar(Object item);
    void eliminar(String id);
    Object buscar(String id);
    int cantidadTotal();
}