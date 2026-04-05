package com.empresa.recursoshumanos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empresa.recursoshumanos.entity.Empleado;
import com.empresa.recursoshumanos.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }
    
    public java.util.List<Empleado> listar() {
    return empleadoRepository.findAll();
    }
}
