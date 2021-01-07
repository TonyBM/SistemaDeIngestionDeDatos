package mx.uady.ingestionDeDatos.service;

import javax.transaction.Transactional;
import mx.uady.ingestionDeDatos.model.Direccion;
import mx.uady.ingestionDeDatos.model.request.DireccionRequest;
import mx.uady.ingestionDeDatos.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.ingestionDeDatos.exception.NotFoundException;

@Service
public class DireccionService {
    
    @Autowired
    private DireccionRepository direccionRepository;
    
    @Transactional
    public Direccion crearDireccion(DireccionRequest request) {
        Direccion nuevaDireccion = new Direccion();
        
        nuevaDireccion.setCalle(request.getCalle());
        nuevaDireccion.setNumero(request.getNumero());
        nuevaDireccion.setCruzamientos(request.getCruzamientos());
        nuevaDireccion.setColonia(request.getColonia());
        nuevaDireccion.setCodigoPostal(request.getCodigoPostal());
        
        Direccion direccionGuardada = direccionRepository.save(nuevaDireccion);
        
        return direccionGuardada;
    }

    @Transactional
    public Direccion editarDireccion(Integer idDireccion, DireccionRequest request){
        return direccionRepository.findById(idDireccion)
        .map(direccion -> {
            direccion.setCalle(request.getCalle());
            direccion.setNumero(request.getNumero());
            direccion.setCruzamientos(request.getCruzamientos());
            direccion.setColonia(request.getColonia());
            direccion.setCodigoPostal(request.getCodigoPostal());
            return direccionRepository.save(direccion);
        })
        .orElseThrow(() -> new NotFoundException("La entidad direccion no pudo ser encontrada."));
    }
}
