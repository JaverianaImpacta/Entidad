package edu.javeriana.ingenieria.social.entidad.servicio;

import edu.javeriana.ingenieria.social.entidad.dominio.Entidad;
import edu.javeriana.ingenieria.social.entidad.repositorio.RepositorioEntidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class ServicioEntidad {

    @Autowired
    private RepositorioEntidad repositorio;

    public List<Entidad> obtenerEntidades(){
        return repositorio.findAll();
    }

    public Entidad obtenerEntidad(Integer id){
        return repositorio.findById(id).orElse(null);
    }

    public Entidad obtenerEntidadPorConvenio(Integer convenio){
        return repositorio.findOneByConvenio(convenio);
    }

    public Entidad obtenerEntidadPorNit(Integer nit){
        return repositorio.findOneByNit(nit);
    }

    public List<Entidad> obtenerEntidades(boolean aprobacion){
        return repositorio.findAllByAprobacion(aprobacion);
    }

    public List<Entidad> obtenerEntidades(String actividadEconomica){
        return repositorio.findAllByActividadEconomica(actividadEconomica);
    }

    public Entidad crearEntidad(Entidad entidad){
        return repositorio.save(entidad);
    }

    public Entidad actualizarEntidad(Integer id, Entidad entidad){
        if(repositorio.findById(id).orElse(null) == null){
            return null;
        }
        entidad.setId(id);
        return repositorio.save(entidad);
    }

    public Entidad borrarEntidad(Integer id){
        Entidad aux = repositorio.findById(id).orElse(null);
        if(aux == null){
            return aux;
        }
        repositorio.delete(aux);
        return aux;
    }

    public boolean entidadExiste(Integer id){
        return repositorio.existsById(id);
    }

    public boolean entidadExistePorConvenio(Integer convenio){
        return repositorio.existsByConvenio(convenio);
    }

    public boolean entidadExistePorNit(Integer nit) {
        return repositorio.existsByNit(nit);
    }

    public boolean entidadExiste(String correo){
        return repositorio.existsByCorreo(correo);
    }
}
