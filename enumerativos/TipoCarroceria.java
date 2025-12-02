package enumerativos;

public enum TipoCarroceria {
    SEDAN("Sedán"),
    HATCHBACK("Hatchback"),
    SUV("SUV"),
    PICKUP("Pick-up"),
    COUPE("Coupé"),
    STATION_WAGON("Rural");
    
    private String descripcion;
    
    TipoCarroceria(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}