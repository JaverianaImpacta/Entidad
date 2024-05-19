package edu.javeriana.ingenieria.social.entidad.controlador;

import edu.javeriana.ingenieria.social.entidad.dominio.Entidad;
import edu.javeriana.ingenieria.social.entidad.servicio.ServicioEntidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("api/entidades")
public class ControladorEntidad {
    @Autowired
    private ServicioEntidad servicio;

    @GetMapping("listar")
    public List<Entidad> obtenerEntidades(){
        return servicio.obtenerEntidades();
    }

    @GetMapping("obtenerAprobacion")
    public ResponseEntity<List<Entidad>> obtenerEntidades(@RequestParam boolean aprobacion){
        if(servicio.obtenerEntidades(aprobacion).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerEntidades(aprobacion), HttpStatus.OK);
    }

    @GetMapping("obtenerActividadEconomica")
    public ResponseEntity<List<Entidad>> obtenerEntidades(@RequestParam String actividadEconomica){
        if(actividadEconomica == null || actividadEconomica.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.obtenerEntidades(actividadEconomica).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerEntidades(actividadEconomica), HttpStatus.OK);
    }
    
    @GetMapping("obtener")
    public ResponseEntity<Entidad> obtenerEntidad(@RequestParam Integer id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.obtenerEntidad(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerEntidad(id), HttpStatus.OK);
    }

    @GetMapping("obtenerConvenio")
    public ResponseEntity<Entidad> obtenerEntidadPorConvenio(@RequestParam Integer convenio){
        if (convenio == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!servicio.entidadExistePorConvenio(convenio)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerEntidadPorConvenio(convenio), HttpStatus.OK);
    }

    @GetMapping("obtenerNit")
    public ResponseEntity<Entidad> obtenerEntidadPorNit(@RequestParam Integer nit){
        if (nit == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!servicio.entidadExistePorNit(nit)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerEntidadPorNit(nit), HttpStatus.OK);
    }

    @PostMapping("crear")
    public ResponseEntity<Entidad> crearEntidad(@RequestBody Entidad entidad){
        if(entidad == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.entidadExistePorNit(entidad.getNit()) || servicio.entidadExiste(entidad.getId()) || servicio.entidadExistePorConvenio(entidad.getConvenio()) || servicio.entidadExiste(entidad.getCorreo())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(servicio.crearEntidad(entidad), HttpStatus.CREATED);
    }

    @PutMapping("actualizar")
    public ResponseEntity<Entidad> actualizarEntidad(@RequestParam Integer id, @RequestBody Entidad entidad){
        if(id == null || entidad == null || !id.equals(entidad.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.actualizarEntidad(id, entidad) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entidad, HttpStatus.OK);
    }

    @DeleteMapping("eliminar")
    public ResponseEntity<HttpStatus> borrarEntidad(@RequestParam Integer id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.borrarEntidad(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
