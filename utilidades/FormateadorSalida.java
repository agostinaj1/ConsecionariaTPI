package utilidades;

public class FormateadorSalida {

    
    public static String repetirCaracter(String caracter, int veces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < veces; i++) {
            sb.append(caracter);
        }
        return sb.toString();
    }

    public static void imprimirTitulo(String titulo) {
        int largo = titulo.length() + 4;
        
        String borde = repetirCaracter("═", largo);

        System.out.println("\n╔" + borde + "╗");
        System.out.println("║  " + titulo + "  ║");
        System.out.println("╚" + borde + "╝");
    }

    public static void imprimirExito(String mensaje) {
        
        System.out.println("" + mensaje);
    }

    public static void imprimirError(String mensaje) {
        System.out.println(" ERROR: " + mensaje);
    }

    public static void imprimirAdvertencia(String mensaje) {
        System.out.println(" " + mensaje);
    }

    public static void imprimirInfo(String mensaje) {
        System.out.println("* " + mensaje);
    }

    public static void limpiarPantalla() {
        // Simula limpieza de pantalla con líneas en blanco
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}