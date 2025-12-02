package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import enumerativos.*;
import excepciones.PersistenciaException;
import interfaces.IPersistencia;
import modelo.*;

public class PersistenciaVehiculos implements IPersistencia<Vehiculo> {

    private static final String SEPARADOR = ";";

    @Override
    public void guardar(List<Vehiculo> vehiculos, String rutaArchivo) throws PersistenciaException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {//Cabecera del CSV
            writer.write("TIPO;ID;MARCA;MODELO;ANIO;COLOR;CONDICION;KILOMETRAJE;SUBTIPO;ESTADO_MANT;ESTADO_LAV");
            writer.newLine();

            for (Vehiculo v : vehiculos) {
                StringBuilder linea = new StringBuilder();
                //Determinar tipo y subtipo
                String tipo = (v instanceof AutomovilCamioneta) ? "AUTO" : "MOTO";
                String subTipo = "";
                String estadoMant = "PENDIENTE";
                String estadoLav = "PENDIENTE";
                Integer km = 0;

                if (v instanceof AutomovilCamioneta) {
                    AutomovilCamioneta auto = (AutomovilCamioneta) v;
                    subTipo = auto.getTipoCarroceria().name();
                    km = auto.getKilometraje();
                    EstadoMantenimiento emAuto = (auto.getEstadoMantenimientoEnum() != null) ? (EstadoMantenimiento) auto.getEstadoMantenimientoEnum() : null;
                    EstadoLavado elAuto = (auto.getEstadoLavadoEnum() != null) ? (EstadoLavado) auto.getEstadoLavadoEnum() : null;
                    estadoMant = (emAuto != null) ? emAuto.name() : "NO_APLICA";
                    estadoLav = (elAuto != null) ? elAuto.name() : "NO_APLICA";
                } else if (v instanceof Motocicleta) {
                    Motocicleta moto = (Motocicleta) v;
                    subTipo = moto.getTipoMotocicleta().name();
                    km = moto.getKilometraje();
                    EstadoMantenimiento emMoto = (moto.getEstadoMantenimientoEnum() != null) ? (EstadoMantenimiento) moto.getEstadoMantenimientoEnum() : null;
                    EstadoLavado elMoto = (moto.getEstadoLavadoEnum() != null) ? (EstadoLavado) moto.getEstadoLavadoEnum() : null;
                    estadoMant = (emMoto != null) ? emMoto.name() : "NO_APLICA";
                    estadoLav = (elMoto != null) ? elMoto.name() : "NO_APLICA";
                }

                linea.append(tipo).append(SEPARADOR)
                     .append(v.getIdentificador()).append(SEPARADOR)
                     .append(v.getMarca()).append(SEPARADOR)
                     .append(v.getModelo()).append(SEPARADOR)
                     .append(v.getAnio()).append(SEPARADOR)
                     .append(v.getColor()).append(SEPARADOR)
                     .append(v.getCondicion().name()).append(SEPARADOR)
                     .append(km).append(SEPARADOR)
                     .append(subTipo).append(SEPARADOR)
                     .append(estadoMant).append(SEPARADOR)
                     .append(estadoLav);

                writer.write(linea.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar vehículos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Vehiculo> cargar(String rutaArchivo) throws PersistenciaException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) return vehiculos; //Retorna lista vacía si no existe el archivo

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine(); //Leer cabecera

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(SEPARADOR);
                if (datos.length < 11) continue; //Ignorar líneas corruptas

                try {
                    String tipo = datos[0];
                    String id = datos[1];
                    String marca = datos[2];
                    String modelo = datos[3];
                    Integer anio = Integer.parseInt(datos[4]);
                    String color = datos[5];
                    CondicionVehiculo condicion = CondicionVehiculo.valueOf(datos[6]);
                    Integer km = Integer.parseInt(datos[7]);
                    String subTipoStr = datos[8];
                    EstadoMantenimiento estMant = EstadoMantenimiento.valueOf(datos[9]);
                    EstadoLavado estLav = EstadoLavado.valueOf(datos[10]);

                    Vehiculo v = null;

                    if (tipo.equals("AUTO")) {
                        TipoCarroceria carroceria = TipoCarroceria.valueOf(subTipoStr);
                        AutomovilCamioneta auto = new AutomovilCamioneta(marca, modelo, anio, color, condicion, id, carroceria, km);
                        auto.setEstadoMantenimiento(estMant);
                        auto.setEstadoLavado(estLav);
                        v = auto;
                    } else if (tipo.equals("MOTO")) {
                        TipoMotocicleta tipoMoto = TipoMotocicleta.valueOf(subTipoStr);
                        Motocicleta moto = new Motocicleta(marca, modelo, anio, color, condicion, id, tipoMoto, km);
                        moto.setEstadoMantenimiento(estMant);
                        moto.setEstadoLavado(estLav);
                        v = moto;
                    }

                    if (v != null) vehiculos.add(v);

                } catch (Exception e) {
                    System.err.println("Error al procesar línea: " + linea + " -> " + e.getMessage());//Continuar con el siguiente registro (Tolerancia a fallos)
                }//Continuar con el siguiente registro 
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al leer archivo de vehículos", e);
        }
        return vehiculos;
    }

    @Override
    public void realizarRespaldo(String rutaOrigen, String rutaDestino) throws PersistenciaException {
        try (InputStream in = new FileInputStream(rutaOrigen);
             OutputStream out = new FileOutputStream(rutaDestino)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al crear respaldo", e);
        }
    }
}