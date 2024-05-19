package edu.javeriana.ingenieria.social.entidad.dominio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String documento, correo;
    @Column(name="actividad_economica")
    private String actividadEconomica;
    @Column(name="razon_social")
    private String razonSocial;
    @Column(name="cedula_rep")
    private Integer cedulaRepresentante;
    private Integer nit, convenio, telefono;
    private boolean aprobacion;
}
