package enumerativos;

public enum CondicionVehiculo {
    NUEVO("0 Km"),
    USADO("Usado");
    
    private String descripcion;
    
    CondicionVehiculo(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}