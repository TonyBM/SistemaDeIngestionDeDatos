package mx.uady.ingestionDeDatos.service;

import javax.transaction.Transactional;
import mx.uady.ingestionDeDatos.model.Direccion;
import mx.uady.ingestionDeDatos.model.request.DireccionRequest;
import mx.uady.ingestionDeDatos.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
