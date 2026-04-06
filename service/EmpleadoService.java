package com.empresa.recursoshumanos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.empresa.recursoshumanos.entity.Empleado;
import com.empresa.recursoshumanos.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }

    public Empleado buscarPorDni(String dni) {
        return empleadoRepository.findByDni(dni);
    }
}
