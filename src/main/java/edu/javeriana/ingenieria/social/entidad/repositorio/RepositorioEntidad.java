package edu.javeriana.ingenieria.social.entidad.repositorio;

import edu.javeriana.ingenieria.social.entidad.dominio.Entidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioEntidad extends JpaRepository<Entidad, Integer> {
    boolean existsByConvenio(Integer convenio);

    Entidad findOneByConvenio(Integer convenio);

    boolean existsByNit(Integer nit);

    Entidad findOneByNit(Integer nit);

    List<Entidad> findAllByAprobacion(boolean aprobacion);

    List<Entidad> findAllByActividadEconomica(String actividadEconomica);

    boolean existsByCorreo(String correo);
}
