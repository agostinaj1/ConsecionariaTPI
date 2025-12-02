package enumerativos;

public enum EstadoLavado {
    PENDIENTE("Pendiente de lavado"),
    EN_LAVADO("En proceso de lavado"),
    LAVADO_BASICO("Lavado básico completado"),
    DETALLADO_COMPLETO("Detallado y listo para exhibición"),
    NO_APLICA("No aplica (0 Km)");

    private String descripcion;

    EstadoLavado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}