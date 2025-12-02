package interfaces;

import excepciones.VehiculoInvalidoException;

public interface IVehiculo {
    String obtenerInformacionCompleta();
    void validar() throws VehiculoInvalidoException;
    boolean esUsado();
    String getIdentificador();
}