package interfaces;

public interface IMantenible {
    boolean necesitaMantenimiento();
    void realizarMantenimiento();
    String getEstadoMantenimiento();
}