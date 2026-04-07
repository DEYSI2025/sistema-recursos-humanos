package com.empresa.recursoshumanos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.recursoshumanos.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Empleado findByDni(String dni);
    
    List<Empleado> findByActivoTrue();

    Optional<Empleado> findByEmail(String email);

    List<Empleado> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre, String apellido);


}
