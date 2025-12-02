package presentacion;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import negocio.GestorConcesionaria;
import utilidades.ValidadorEntrada;
import enumerativos.CondicionVehiculo;
import enumerativos.TipoCarroceria;
import enumerativos.TipoMotocicleta;
import excepciones.InventarioException;
import excepciones.TallerException;
import excepciones.LavaderoException;
import excepciones.PersistenciaException;
import excepciones.VehiculoInvalidoException;
import modelo.Vehiculo;
import modelo.AutomovilCamioneta;
import modelo.Motocicleta;
import utilidades.FormateadorSalida;

public class MenuPrincipal {

    private Scanner scanner;
    private ValidadorEntrada validador;
    private GestorConcesionaria gestor;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.validador = new ValidadorEntrada(scanner);
        this.gestor = new GestorConcesionaria();
    }
//Preguntar si desea cargar datos previos al inicio
    public void ejecutar() {
        System.out.println("¿Desea cargar los datos de la sesión anterior? (S/N)");
        Boolean cargarDatos = validador.leerBooleano("Respuesta");

        if (cargarDatos) {
            cargarDatosSistema();
        } else {
            System.out.println("Iniciando sin cargar datos...");
        }
        boolean salir = false;

        while (!salir) {
            try {
                mostrarMenuPrincipal();
                Integer opcion = validador.leerOpcionMenu(1, 14); 

                switch (opcion) {
                    case 1: registrarVehiculo(); break;
                    case 2: listarVehiculos(); break;
                    case 3: buscarVehiculo(); break;
                    case 4: eliminarVehiculo(); break;
                    case 5: gestionarTallerRevision(); break;
                    case 6: gestionarLavadero(); break;
                    case 7: verColaRevisionMecanica(); break;
                    case 8: verColaLavadero(); break;
                    case 9: verEstadisticas(); break;
                    case 10: listarVehiculosQueNecesitanMantenimiento(); break;
                    case 11: guardarDatosSistema(); break;
                    case 12: cargarDatosSistema(); break;
                    case 13: generarRespaldo(); break;
                    case 14:
                        salir = true;
                        guardarDatosSistema(); 
                        FormateadorSalida.imprimirInfo("¡Gracias por usar el sistema! Datos guardados.");
                        break;
                }
            } catch (Exception e) {
                FormateadorSalida.imprimirError("Error inesperado: " + e.getMessage());
                validador.pausa();
            }
        }
        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        FormateadorSalida.limpiarPantalla();
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║    SISTEMA DE GESTIÓN DE CONCESIONARIA           ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║  1. Registrar nuevo vehículo                     ║");
        System.out.println("║  2. Listar todos los vehículos                   ║");
        System.out.println("║  3. Buscar vehículo por patente                  ║");
        System.out.println("║  4. Eliminar vehículo                            ║");
        System.out.println("║  5. Gestionar Taller de Revisión Mecánica        ║");
        System.out.println("║  6. Gestionar Lavadero                           ║");
        System.out.println("║  7. Ver Cola de Revisión Mecánica                ║");
        System.out.println("║  8. Ver Cola de Lavadero                         ║");
        System.out.println("║  9. Ver Estadísticas                             ║");
        System.out.println("║  10. Vehículos que necesitan mantenimiento       ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║  11. Guardar Datos (Manual)                      ║");
        System.out.println("║  12. Cargar Datos                                ║");
        System.out.println("║  13. Generar Copia de Seguridad (Backup)         ║");
        System.out.println("║  14. Guardar y Salir                             ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    private void guardarDatosSistema() {
        try {
            gestor.guardarDatos();
            FormateadorSalida.imprimirExito("Datos guardados correctamente en disco.");
        } catch (PersistenciaException e) {
            FormateadorSalida.imprimirError("Error al guardar: " + e.getMessage());
        }
        validador.pausa();
    }

    private void cargarDatosSistema() {
        try {
            gestor.cargarDatos();
            FormateadorSalida.imprimirExito("Datos cargados correctamente.");
        } catch (Exception e) {
            FormateadorSalida.imprimirError("Error al cargar: " + e.getMessage());
        }
        validador.pausa();
    }

    private void generarRespaldo() {
        try {
            gestor.generarRespaldo();
            FormateadorSalida.imprimirExito("Respaldo generado (archivos *_backup).");
        } catch (PersistenciaException e) {
            FormateadorSalida.imprimirError("Error al respaldar: " + e.getMessage());
        }
        validador.pausa();
    }
//Registra un nuevo vehículo
    private void registrarVehiculo() {
        FormateadorSalida.imprimirTitulo("REGISTRO DE NUEVO VEHÍCULO");
        try {
            System.out.println("\n¿Qué tipo de vehículo desea registrar?");
            System.out.println("1. Automóvil/Camioneta");
            System.out.println("2. Motocicleta");

            Integer tipo = validador.leerOpcionMenu(1, 2);

            String id = validador.leerPatente("PATENTE del vehículo (ej: AAA000AA o AAA 000): ", false);
            String marca = validador.leerTexto("Marca: ", false);
            String modelo = validador.leerTexto("Modelo: ", false);
            Integer anio = validador.leerEntero("Año: ", 1900, 2026);
            String color = validador.leerTextoAlfabetico("Color: ", false);

            System.out.println("\n¿Es un vehículo usado?");
            Boolean esUsado = validador.leerBooleano("Respuesta");

            CondicionVehiculo condicion = esUsado ? CondicionVehiculo.USADO : CondicionVehiculo.NUEVO;
            Integer kilometraje = 0;
            if (esUsado) {
                kilometraje = validador.leerEntero("Kilometraje: ", 0, null);
            }

            Vehiculo vehiculo = null;
    //Automóvil/Camioneta
            if (tipo == 1) {
                System.out.println("\nSeleccione el tipo de carrocería:");
                TipoCarroceria[] carrocerias = TipoCarroceria.values();
                for (int i = 0; i < carrocerias.length; i++) {
                    System.out.println((i + 1) + ". " + carrocerias[i].getDescripcion());
                }
                Integer opCarroceria = validador.leerOpcionMenu(1, carrocerias.length);
                TipoCarroceria carroceria = carrocerias[opCarroceria - 1];
                vehiculo = new AutomovilCamioneta(marca, modelo, anio, color, condicion, id, carroceria, kilometraje);

            } /*Motocicleta*/else {
                System.out.println("\nSeleccione el tipo de motocicleta:");
                TipoMotocicleta[] tiposMoto = TipoMotocicleta.values();
                for (int i = 0; i < tiposMoto.length; i++) {
                    System.out.println((i + 1) + ". " + tiposMoto[i].getDescripcion());
                }
                Integer opMoto = validador.leerOpcionMenu(1, tiposMoto.length);
                TipoMotocicleta tipoMoto = tiposMoto[opMoto - 1];
                vehiculo = new Motocicleta(marca, modelo, anio, color, condicion, id, tipoMoto, kilometraje);
            }
// Agregar al inventario
            gestor.getInventario().agregarVehiculo(vehiculo);
            FormateadorSalida.imprimirExito("Vehículo registrado correctamente");
//Si es usado, preguntar si agregar a taller y/o lavadero
            if (esUsado) {
                Boolean agregarATaller = validador.leerBooleano("\n¿Desea agregar este vehículo a la cola de revisión mecánica?");
                if (agregarATaller) {
                    gestor.getTaller().agregarACola(vehiculo);
                    FormateadorSalida.imprimirExito("Vehículo agregado a la cola de revisión");
                }
                Boolean agregarALavadero = validador.leerBooleano("\n¿Desea agregar este vehículo al lavadero?");
                if (agregarALavadero) {
                    gestor.getLavadero().agregarACola(vehiculo);
                    FormateadorSalida.imprimirExito("Vehículo agregado a la cola del lavadero");
                }
            }
            gestor.autoGuardar();//Auto-guardado

        } catch (VehiculoInvalidoException | InventarioException | TallerException | LavaderoException e) {
            FormateadorSalida.imprimirError(e.getMessage());
        }
        validador.pausa();
    }

    private void listarVehiculos() {
        FormateadorSalida.imprimirTitulo("LISTADO DE VEHÍCULOS");
        Collection<Vehiculo> vehiculos = gestor.getInventario().obtenerTodosVehiculos();
        if (vehiculos.isEmpty()) {
            FormateadorSalida.imprimirAdvertencia("No hay vehículos registrados");
        } else {
            System.out.println("\nTotal de vehículos: " + vehiculos.size());
            System.out.println(FormateadorSalida.repetirCaracter("─", 80));
            for (Vehiculo v : vehiculos) {
                System.out.println("\n" + v.obtenerInformacionCompleta());
                System.out.println(FormateadorSalida.repetirCaracter("─", 80));
            }
        }
        validador.pausa();
    }
//Busca un vehículo por patente
    private void buscarVehiculo() {
        FormateadorSalida.imprimirTitulo("BUSCAR VEHÍCULO");
        String id = validador.leerPatente("Ingrese la patente del vehículo (ej: AAA000AA o AAA 000): ", false);
        Vehiculo vehiculo = gestor.getInventario().buscarVehiculo(id);
        if (vehiculo == null) {
            FormateadorSalida.imprimirAdvertencia("No se encontró ningún vehículo con ese patente");
        } else {
            System.out.println("\n" + vehiculo.obtenerInformacionCompleta());
        }
        validador.pausa();
    }
//Elimina un vehículo
    private void eliminarVehiculo() {
        FormateadorSalida.imprimirTitulo("ELIMINAR VEHÍCULO");
        try {
            String id = validador.leerPatente("Ingrese la patente del vehículo a eliminar (ej: AAA000AA o AAA 000): ", false);
            Vehiculo vehiculo = gestor.getInventario().buscarVehiculo(id);
            if (vehiculo == null) {
                FormateadorSalida.imprimirAdvertencia("No existe un vehículo con ese patente");
            } else {
                System.out.println("\nVehículo encontrado:");
                System.out.println(vehiculo.toString());
                Boolean confirmar = validador.leerBooleano("\n¿Está seguro de eliminar este vehículo?");
                if (confirmar) {
                    gestor.getInventario().eliminarVehiculo(id);
                    gestor.autoGuardar();
                    FormateadorSalida.imprimirExito("Vehículo eliminado correctamente");
                } else {
                    FormateadorSalida.imprimirInfo("Operación cancelada");
                }
            }
        } catch (InventarioException e) {
            FormateadorSalida.imprimirError(e.getMessage());
        }
        validador.pausa();
    }
//Gestiona el taller de revisión mecánica
    private void gestionarTallerRevision() {
        FormateadorSalida.imprimirTitulo("GESTIÓN DE TALLER DE REVISIÓN MECÁNICA");
        System.out.println("\n1. Agregar vehículo a la cola");
        System.out.println("2. Atender siguiente vehículo");
        System.out.println("3. Ver siguiente en cola");
        System.out.println("4. Volver al menú principal");
        Integer opcion = validador.leerOpcionMenu(1, 4);
        try {
            switch (opcion) {
                case 1: agregarVehiculoATaller(); break;
                case 2: atenderVehiculoTaller(); break;
                case 3: verSiguienteEnTaller(); break;
                case 4: return;
            }
            gestor.autoGuardar();//Auto-guardado tras cambios en cola
        } catch (TallerException e) {
            FormateadorSalida.imprimirError(e.getMessage());
        }
        validador.pausa();
    }

    private void agregarVehiculoATaller() throws TallerException {
        String id = validador.leerPatente("Patente del vehículo (ej: AAA000AA o AAA 000): ", false);
        Vehiculo vehiculo = gestor.getInventario().buscarVehiculo(id);
        if (vehiculo == null) {
            FormateadorSalida.imprimirAdvertencia("No existe un vehículo con ese patente");
        } else {
            gestor.getTaller().agregarACola(vehiculo);
            FormateadorSalida.imprimirExito("Vehículo agregado a la cola de revisión");
        }
    }

    private void atenderVehiculoTaller() throws TallerException {
        Vehiculo vehiculo = gestor.getTaller().atenderSiguiente();
        FormateadorSalida.imprimirExito("Vehículo atendido y mantenimiento completado");
        System.out.println("\n" + vehiculo.obtenerInformacionCompleta());
    }

    private void verSiguienteEnTaller() {
        Vehiculo siguiente = gestor.getTaller().verSiguiente();
        if (siguiente == null) {
            FormateadorSalida.imprimirInfo("No hay vehículos en la cola");
        } else {
            System.out.println("\nSiguiente vehículo a atender:");
            System.out.println(siguiente.obtenerInformacionCompleta());
        }
    }
//Gestiona el lavadero
    private void gestionarLavadero() {
        FormateadorSalida.imprimirTitulo("GESTIÓN DE LAVADERO");
        System.out.println("\n1. Agregar vehículo usado a la cola");
        System.out.println("2. Realizar lavado básico");
        System.out.println("3. Realizar detallado para exhibición");
        System.out.println("4. Ver siguiente en cola");
        System.out.println("5. Volver al menú principal");
        Integer opcion = validador.leerOpcionMenu(1, 5);
        try {
            switch (opcion) {
                case 1: agregarVehiculoALavadero(); break;
                case 2: realizarLavadoBasico(); break;
                case 3: realizarDetalladoExhibicion(); break;
                case 4: verSiguienteEnLavadero(); break;
                case 5: return;
            }
            gestor.autoGuardar();
        } catch (LavaderoException e) {
            FormateadorSalida.imprimirError(e.getMessage());
        }
        validador.pausa();
    }

    private void agregarVehiculoALavadero() throws LavaderoException {
        String id = validador.leerPatente("Patente del vehículo (ej: AAA000AA o AAA 000): ", false);
        Vehiculo vehiculo = gestor.getInventario().buscarVehiculo(id);
        if (vehiculo == null) {
            FormateadorSalida.imprimirAdvertencia("No existe un vehículo con ese patente");
        } else {
            gestor.getLavadero().agregarACola(vehiculo);
            FormateadorSalida.imprimirExito("Vehículo agregado a la cola del lavadero");
        }
    }

    private void realizarLavadoBasico() throws LavaderoException {
        Vehiculo vehiculo = gestor.getLavadero().realizarLavadoBasico();
        FormateadorSalida.imprimirExito("Lavado básico completado");
        System.out.println("\n" + vehiculo.obtenerInformacionCompleta());
        FormateadorSalida.imprimirInfo("El vehículo sigue en cola para detallado");
    }

    private void realizarDetalladoExhibicion() throws LavaderoException {
        Vehiculo vehiculo = gestor.getLavadero().realizarDetalladoExhibicion();
        FormateadorSalida.imprimirExito("Detallado completado - Vehículo listo para exhibición");
        System.out.println("\n" + vehiculo.obtenerInformacionCompleta());
    }

    private void verSiguienteEnLavadero() {
        Vehiculo siguiente = gestor.getLavadero().verSiguiente();
        if (siguiente == null) {
            FormateadorSalida.imprimirInfo("No hay vehículos en la cola del lavadero");
        } else {
            System.out.println("\nSiguiente vehículo en lavadero:");
            System.out.println(siguiente.obtenerInformacionCompleta());
        }
    }
//Ver Cola de Revisión Mecánica
    private void verColaRevisionMecanica() {
        FormateadorSalida.imprimirTitulo("COLA DE REVISIÓN MECÁNICA");
        List<Vehiculo> cola = gestor.getTaller().obtenerVehiculosEnCola();
        if (cola.isEmpty()) {
            FormateadorSalida.imprimirInfo("No hay vehículos en la cola de espera");
        } else {
            System.out.println("\nVehículos en espera: " + cola.size());
            System.out.println(FormateadorSalida.repetirCaracter("─", 80));
            int posicion = 1;
            for (Vehiculo v : cola) {
                System.out.println("\nPosición #" + posicion++);
                System.out.println(v.obtenerInformacionCompleta());
                 System.out.println(FormateadorSalida.repetirCaracter("─", 80));
            }
        }
        validador.pausa();
    }
//Ver Cola de Lavadero
    private void verColaLavadero() {
        FormateadorSalida.imprimirTitulo("COLA DE LAVADERO");
        List<Vehiculo> cola = gestor.getLavadero().obtenerVehiculosEnCola();
        if (cola.isEmpty()) {
            FormateadorSalida.imprimirInfo("No hay vehículos en la cola del lavadero");
        } else {
            System.out.println("\nVehículos en espera: " + cola.size());
             System.out.println(FormateadorSalida.repetirCaracter("─", 80));
            int posicion = 1;
            for (Vehiculo v : cola) {
                System.out.println("\nPosición #" + posicion++);
                System.out.println(v.obtenerInformacionCompleta());
                 System.out.println(FormateadorSalida.repetirCaracter("─", 80));
            }
        }
        validador.pausa();
    }
//Muestra estadísticas
    private void verEstadisticas() {
        FormateadorSalida.imprimirTitulo("ESTADÍSTICAS DEL SISTEMA");
        System.out.println(gestor.obtenerEstadisticas());
        validador.pausa();
    }
//Lista vehículos que necesitan mantenimiento
    private void listarVehiculosQueNecesitanMantenimiento() {
        FormateadorSalida.imprimirTitulo("VEHÍCULOS QUE NECESITAN MANTENIMIENTO");
        List<Vehiculo> necesitanMantenimiento = gestor.getInventario().obtenerVehiculosQueNecesitanMantenimiento();
        if (necesitanMantenimiento.isEmpty()) {
            FormateadorSalida.imprimirExito("¡Todos los vehículos están al día con el mantenimiento!");
        } else {
            System.out.println("\nVehículos pendientes: " + necesitanMantenimiento.size());
            System.out.println(FormateadorSalida.repetirCaracter("─", 80));
            for (Vehiculo v : necesitanMantenimiento) {
                System.out.println("\n" + v.obtenerInformacionCompleta());
                System.out.println(FormateadorSalida.repetirCaracter("─", 80));
            }
        }
        validador.pausa();
    }
}