package modelo;

import enumerativos.CondicionVehiculo;
import enumerativos.EstadoMantenimiento;
import enumerativos.EstadoLavado;
import enumerativos.TipoMotocicleta;
import excepciones.VehiculoInvalidoException;
import interfaces.IMantenible;
import interfaces.ILavable;

// Clase concreta para Motos
public class Motocicleta extends Vehiculo implements IMantenible, ILavable {

    private static final long serialVersionUID = 1L;

    private TipoMotocicleta tipoMotocicleta;
    private Integer kilometraje;
    private EstadoMantenimiento estadoMantenimiento;
    private EstadoLavado estadoLavado;

    public Motocicleta() {
        super();
    }

    public Motocicleta(String marca, String modelo, Integer anio, String color,
            CondicionVehiculo condicion, String identificador,
            TipoMotocicleta tipoMotocicleta, Integer kilometraje) {
        super(marca, modelo, anio, color, condicion, identificador);
        this.tipoMotocicleta = tipoMotocicleta;
        this.kilometraje = kilometraje;
        this.estadoMantenimiento = esUsado() ? EstadoMantenimiento.PENDIENTE : EstadoMantenimiento.NO_APLICA;
        this.estadoLavado = esUsado() ? EstadoLavado.PENDIENTE : EstadoLavado.NO_APLICA;
    }

    public TipoMotocicleta getTipoMotocicleta() { return tipoMotocicleta; }
    public void setTipoMotocicleta(TipoMotocicleta tipoMotocicleta) { this.tipoMotocicleta = tipoMotocicleta; }

    public Integer getKilometraje() { return kilometraje; }
    public void setKilometraje(Integer kilometraje) { this.kilometraje = kilometraje; }

    public EstadoMantenimiento getEstadoMantenimientoEnum() { return estadoMantenimiento; }
    public EstadoLavado getEstadoLavadoEnum() { return estadoLavado; }

    public void setEstadoMantenimiento(EstadoMantenimiento estadoMantenimiento) { this.estadoMantenimiento = estadoMantenimiento; }
    public void setEstadoLavado(EstadoLavado estadoLavado) { this.estadoLavado = estadoLavado; }

    @Override
    public String getEstadoMantenimiento() {
        return estadoMantenimiento != null ? estadoMantenimiento.getDescripcion() : "No disponible";
    }

    @Override
    public boolean necesitaMantenimiento() {
        return esUsado() && estadoMantenimiento != EstadoMantenimiento.COMPLETADO;
    }

    @Override
    public void realizarMantenimiento() {
        if (esUsado()) { this.estadoMantenimiento = EstadoMantenimiento.COMPLETADO; }
    }

    @Override
    public boolean necesitaLavado() {
        return esUsado() && estadoLavado != EstadoLavado.DETALLADO_COMPLETO;
    }

    @Override
    public void realizarLavadoBasico() {
        if (esUsado()) { this.estadoLavado = EstadoLavado.LAVADO_BASICO; }
    }

    @Override
    public void realizarDetalladoExhibicion() {
        if (esUsado()) { this.estadoLavado = EstadoLavado.DETALLADO_COMPLETO; }
    }

    @Override
    public String getEstadoLavado() {
        return estadoLavado != null ? estadoLavado.getDescripcion() : "No disponible";
    }

    @Override
    public void validar() throws VehiculoInvalidoException {
        super.validar();
        if (tipoMotocicleta == null) throw new VehiculoInvalidoException("El tipo de motocicleta no puede ser nulo");
        if (esUsado() && (kilometraje == null || kilometraje < 0)) throw new VehiculoInvalidoException("El kilometraje debe ser mayor o igual a 0");
    }

    @Override
    public String obtenerInformacionCompleta() {
        StringBuilder info = new StringBuilder();
        info.append("=== MOTOCICLETA ===\n");
        info.append("ID interno: ").append(getId()).append("\n");
        info.append("Patente: ").append(getIdentificador()).append("\n");
        info.append("Marca: ").append(getMarca()).append("\n");
        info.append("Modelo: ").append(getModelo()).append("\n");
        info.append("Año: ").append(getAnio()).append("\n");
        info.append("Color: ").append(getColor()).append("\n");
        if (tipoMotocicleta != null) info.append("Tipo: ").append(tipoMotocicleta.getDescripcion()).append("\n");
        info.append("Condición: ").append(getCondicion().getDescripcion()).append("\n");
        if (esUsado()) {
            info.append("Kilometraje: ").append(kilometraje).append(" km\n");
            info.append("Mantenimiento: ").append(getEstadoMantenimiento()).append("\n");
            info.append("Lavado: ").append(getEstadoLavado()).append("\n");
        }
        return info.toString();
    }
    
    @Override
    public void resetearEstadoMantenimiento() {
        this.setEstadoMantenimiento(enumerativos.EstadoMantenimiento.PENDIENTE);
    }
}