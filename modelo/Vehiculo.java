package modelo;

import java.io.Serializable;
import enumerativos.CondicionVehiculo;
import excepciones.VehiculoInvalidoException;
import interfaces.IVehiculo;

/**
 * Clase base abstracta. No se puede instanciar directamente.
 * Implementa Serializable para poder guardarse en archivos.
 */
public abstract class Vehiculo implements IVehiculo, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private CondicionVehiculo condicion;
    private String identificador; // Patente
    private Integer id; // ID interno autoincremental
    private static int contadorIds = 1;
    
    public Vehiculo() {
        this.id = contadorIds++;
    }
    
    // Método abstracto que las clases hijas deben implementar obligatoriamente
    public abstract void resetearEstadoMantenimiento();

    public Vehiculo(String marca, String modelo, Integer anio, String color, 
                    CondicionVehiculo condicion, String identificador) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.condicion = condicion;
        this.identificador = identificador;
        this.id = contadorIds++;
    }
    
    // Getters y Setters
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public CondicionVehiculo getCondicion() { return condicion; }
    public void setCondicion(CondicionVehiculo condicion) { this.condicion = condicion; }
    
    public String getIdentificador() { return identificador; }
    public void setIdentificador(String identificador) { this.identificador = identificador; }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
        if (id != null && id >= contadorIds) {
            contadorIds = id + 1;
        }
    }
    
    @Override
    public boolean esUsado() {
        return condicion == CondicionVehiculo.USADO;
    }
    
    @Override
    public void validar() throws VehiculoInvalidoException {
        if (marca == null || marca.trim().isEmpty()) throw new VehiculoInvalidoException("La marca no puede estar vacía");
        if (modelo == null || modelo.trim().isEmpty()) throw new VehiculoInvalidoException("El modelo no puede estar vacío");
        if (anio == null || anio < 1900 || anio > 2026) throw new VehiculoInvalidoException("El año debe estar entre 1900 y 2026");
        if (color == null || color.trim().isEmpty()) throw new VehiculoInvalidoException("El color no puede estar vacío");
        if (identificador == null || identificador.trim().isEmpty()) throw new VehiculoInvalidoException("El identificador no puede estar vacío");
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %d - Color: %s - Condición: %s",
                marca, modelo, anio, color, condicion.getDescripcion());
    }
}