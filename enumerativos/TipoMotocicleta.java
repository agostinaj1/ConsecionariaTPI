package enumerativos;

public enum TipoMotocicleta {
    DEPORTIVA("Deportiva"),
    TOURING("Touring"),
    CRUISER("Cruiser"),
    SCOOTER("Scooter"),
    ENDURO("Enduro"),
    CUSTOM("Custom");
    
    private String descripcion;
    
    TipoMotocicleta(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}