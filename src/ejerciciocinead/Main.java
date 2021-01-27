/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciocinead;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import mapping.Disponible;
import mapping.PaseSala;
import mapping.Peliculas;

/**
 *
 * @author pabli
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        CineDTO cdto = new CineDTO();
        DisponibleDTO dto = new DisponibleDTO();
        PaseSalaDTO pdto = new PaseSalaDTO();
        List<Peliculas> peliculas = null;
        List<Disponible> disponibles = null;
        do {
            System.out.println("MENÚ");
            System.out.println("1. Ver Peliculas existentes");
            System.out.println("2. Insertar nueva película");
            System.out.println("3. Modificar película");
            System.out.println("4. Borrar película");
            System.out.println("5. Consultar Película");
            System.out.println("6. Número de peliculas");
            System.out.println("7. Películas donde trabaja un actor");
            System.out.println("8. Reservar sala");
            System.out.println("9. Todos los horarios de una pelicula");
            System.out.println("0. Salir");
            System.out.print("Introduce opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    peliculas = null;
                    verPeliculas(cdto, peliculas);
                    break;
                case 2:
                    insertarNuevaPelicula(cdto, sc);
                    break;
                case 3:
                    modificarPelicula(cdto, peliculas, sc);
                    break;
                case 4:
                    borrarPelicula(cdto, sc, peliculas);
                    break;
                case 5:
                    dameEstaPelícula(sc, cdto, peliculas);
                    break;
                case 6:
                    numeroDePelículas(cdto);
                    break;
                case 7:
                    verPeliculasDeActor(cdto, sc);
                    break;
                case 8:
                    verSalaDisponible(disponibles, sc, dto);
                    break;
                case 9:
                    verHorariosDeEstaPelicula(peliculas,cdto,sc,pdto);
            }
        } while (opcion != 0);
    }

    private static void insertarNuevaPelicula(CineDTO cdto, Scanner sc) {
        Peliculas p = new Peliculas();
        System.out.print("Titulo: ");
        p.setTitulo(sc.nextLine());
        System.out.print("Director: ");
        p.setDirector(sc.nextLine());
        System.out.print("Nacionalidad: ");
        p.setNacionalidad(sc.nextLine());
        System.out.print("Género: ");
        p.setGenero(sc.nextLine());
        System.out.print("Clasificación: ");
        p.setClasificacion(sc.nextLine());
        System.out.print("Descripción: ");
        p.setDescr(sc.nextLine());
        System.out.print("Duración[decimal]: ");
        p.setDuracion(sc.nextDouble());
        sc.nextLine();
        System.out.print("Actores: ");
        p.setActores(sc.nextLine());
        System.out.print("Link Externo: ");
        p.setLinkExterior(sc.nextLine());
        System.out.print("Link de la imagen: ");
        p.setLinkImagen(sc.nextLine());
        System.out.println("Estado[entero]: ");
        p.setEstado(sc.nextInt());
        sc.nextLine();

        int id = cdto.nuevaPelicula(p);
        System.out.println("Película insertada con éxito, su id es " + id);

    }

    private static void verPeliculas(CineDTO cdto, List<Peliculas> peliculas) {
        peliculas = cdto.obtenListaPeliculas();
        System.out.println("PID   TITULO   DIRECTOR     NACIONALIDAD   GÉNERO   CLASIFICACION    DESCR   DURACIÓN    ACTORES    LINK_EXT     LINK_IMAGEN    ESTADO");
        peliculas.forEach(p -> {
            System.out.println(p.getPid() + " " + p.getTitulo() + " " + p.getDirector()
                    + " " + p.getNacionalidad() + p.getGenero() + " " + p.getClasificacion()
                    + " " + p.getDescr() + p.getDuracion() + " " + p.getActores() + " "
                    + p.getLinkExterior() + " " + p.getLinkImagen() + " " + p.getEstado());
        });

    }

    private static void modificarPelicula(CineDTO cdto, List<Peliculas> peliculas, Scanner sc) {
        verPeliculas(cdto, peliculas);
        String respuesta = null;
        System.out.print("Introduzca id para modificar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Peliculas peliculaMod = cdto.obtenPelicula(id);
        System.out.print("¿Quieres actualizar el título?[si/no]");
        respuesta = sc.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            System.out.print("Nuevo titulo: ");
            peliculaMod.setTitulo(sc.nextLine());
        }
        System.out.print("¿Quieres actualizar el director?[si/no]");
        respuesta = sc.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            System.out.print("Nuevo Director: ");
            peliculaMod.setDirector(sc.nextLine());
        }
        System.out.print("¿Quieres actualizar el género?[si/no]");
        respuesta = sc.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            System.out.print("Nuevo Género: ");
            peliculaMod.setGenero(sc.nextLine());
        }
        cdto.actualizaPelicula(peliculaMod);
        System.out.println("Datos actualizados");
    }

    private static void borrarPelicula(CineDTO cdto, Scanner sc, List<Peliculas> peliculas) {
        verPeliculas(cdto, peliculas);
        System.out.print("ID de la pelicula a borrar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Peliculas borrar = cdto.obtenPelicula(id);
        cdto.eliminaPelicula(borrar);
        System.out.println("Película eliminada");
    }

    private static void dameEstaPelícula(Scanner sc, CineDTO cdto, List<Peliculas> peliculas) {
        verPeliculas(cdto, peliculas);
        System.out.print("¿Qué película quieres ver en concreto, su id?");
        int id = sc.nextInt();
        sc.nextLine();
        Peliculas p = cdto.obtenPelicula(id);
        System.out.println("PELÍCULA------");
        System.out.println(p.toString());

    }

    private static void verPeliculasDeActor(CineDTO cdto, Scanner sc) {
        System.out.print("Nombre del actor: ");
        String respuesta = sc.nextLine();
        List<Peliculas> temporal = cdto.peliculasDeEsteActor(respuesta);
        temporal.forEach(System.out::println);
    }

    private static void verSalaDisponible(List<Disponible> disponibles, Scanner sc, DisponibleDTO dto) {
        disponibles = dto.listaDisponibles();
        disponibles.forEach(System.out::println);
        System.out.print("Introduce el id de la sala que quieras reservar: ");
        int respuesta = sc.nextInt();
        sc.nextLine();
        Disponible aux = dto.obtenerDisponible(respuesta);
        System.out.println("Evaluando condiciones");
        dto.reservar(aux);
    }

    private static void numeroDePelículas(CineDTO cdto) {
        System.out.println("Hay un total de " + cdto.numeroPeliculas() + " de películas en la BBDD.");
    }

    private static void verHorariosDeEstaPelicula(List<Peliculas> peliculas, CineDTO cdto, Scanner sc, PaseSalaDTO pdto) {
        verPeliculas(cdto, peliculas);
        List<PaseSala> pases_salas;
        System.out.print("Elige el id de la pelicula para ver: ");
        int idPelicula = sc.nextInt();
        sc.nextLine();
        Peliculas pelicula = cdto.obtenPelicula(idPelicula);
        pases_salas = pdto.dimeHorariosDePelicula(pelicula);
        if (pases_salas != null) {
            pases_salas.forEach(p -> {
            String hora = null;
            switch(p.getTid()) {
                case 1:
                    hora = "16:00";
                    break;
                case 2:
                    hora = "16:30";
                    break;
                case 3:
                    hora = "17:00";
                    break;
                case 4:
                    hora = "17:30";
                    break;
                case 5:
                    hora = "18:00";
                    break;
                case 7:
                    hora = "18:30";
                    break;
                case 8:
                    hora = "19:00";
                    break;
            }
            System.out.println("Sala " + p.getSid() + " a las " + hora);
        });
        } else {
            System.out.println("No hay horarios para esta película");
        }
        
    }

}
