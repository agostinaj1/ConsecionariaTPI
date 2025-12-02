package enumerativos;

public enum EstadoMantenimiento {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En proceso"),
    COMPLETADO("Completado"),
    NO_APLICA("No aplica (0 Km)");
    
    private String descripcion;
    
    EstadoMantenimiento(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}