package com.empresa.recursoshumanos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.empresa.recursoshumanos.entity.Asistencia;
import com.empresa.recursoshumanos.entity.Empleado;
import com.empresa.recursoshumanos.repository.AsistenciaRepository;
import com.empresa.recursoshumanos.repository.EmpleadoRepository;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public String registrar(String dni) {

        Empleado emp = empleadoRepository.findByDni(dni);

        if (emp == null) {
            return "Empleado no encontrado";
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setDni(dni);
        asistencia.setFecha(LocalDateTime.now());

        asistenciaRepository.save(asistencia);

        return "Asistencia registrada";
    }
}
