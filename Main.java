import presentacion.MenuPrincipal;

/**
 * Clase principal del sistema
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║                                                      ║");
        System.out.println("║     SISTEMA DE GESTIÓN DE CONCESIONARIA              ║");
        System.out.println("║                                                      ║");
        System.out.println("║           Bienvenido al Sistema                      ║");
        System.out.println("║                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        
        try {
            MenuPrincipal menu = new MenuPrincipal();
            menu.ejecutar();
        } catch (Exception e) {
            System.err.println("\n Error crítico en el sistema: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║          Sistema finalizado correctamente            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
}