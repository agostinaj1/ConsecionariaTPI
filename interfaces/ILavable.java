package interfaces;

public interface ILavable {
    void realizarLavadoBasico();
    void realizarDetalladoExhibicion();
    boolean necesitaLavado();
    String getEstadoLavado();
}