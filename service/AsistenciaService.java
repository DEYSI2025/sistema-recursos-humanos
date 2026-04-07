package com.example.empleados.service;

import com.example.empleados.dto.AsistenciaDTO;
import com.example.empleados.model.Asistencia;
import com.example.empleados.model.Empleado;
import com.example.empleados.repository.AsistenciaRepository;
import com.example.empleados.repository.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public List<AsistenciaDTO> findAll() {
        return asistenciaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AsistenciaDTO findById(Long id) {
        return asistenciaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Asistencia no encontrada con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<AsistenciaDTO> findByEmpleado(Long empleadoId) {
        if (!empleadoRepository.existsById(empleadoId)) {
            throw new EntityNotFoundException("Empleado no encontrado con id: " + empleadoId);
        }
        return asistenciaRepository.findByEmpleadoId(empleadoId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AsistenciaDTO> findByFecha(LocalDate fecha) {
        return asistenciaRepository.findByFecha(fecha)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AsistenciaDTO> findByEmpleadoYRango(Long empleadoId, LocalDate inicio, LocalDate fin) {
        return asistenciaRepository.findByEmpleadoIdAndFechaBetween(empleadoId, inicio, fin)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AsistenciaDTO save(AsistenciaDTO dto) {
        if (asistenciaRepository.existsByEmpleadoIdAndFecha(dto.getEmpleadoId(), dto.getFecha())) {
            throw new IllegalArgumentException(
                    "Ya existe una asistencia para el empleado " + dto.getEmpleadoId() +
                    " en la fecha " + dto.getFecha());
        }
        Asistencia asistencia = toEntity(dto);
        return toDTO(asistenciaRepository.save(asistencia));
    }

    public AsistenciaDTO update(Long id, AsistenciaDTO dto) {
        Asistencia existente = asistenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asistencia no encontrada con id: " + id));

        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + dto.getEmpleadoId()));

        existente.setEmpleado(empleado);
        existente.setFecha(dto.getFecha());
        existente.setHoraEntrada(dto.getHoraEntrada());
        existente.setHoraSalida(dto.getHoraSalida());
        existente.setEstado(dto.getEstado());
        existente.setObservacion(dto.getObservacion());

        return toDTO(asistenciaRepository.save(existente));
    }

    public void delete(Long id) {
        if (!asistenciaRepository.existsById(id)) {
            throw new EntityNotFoundException("Asistencia no encontrada con id: " + id);
        }
        asistenciaRepository.deleteById(id);
    }

    // Mappers
    public AsistenciaDTO toDTO(Asistencia a) {
        String nombreCompleto = a.getEmpleado().getNombre() + " " + a.getEmpleado().getApellido();
        return AsistenciaDTO.builder()
                .id(a.getId())
                .empleadoId(a.getEmpleado().getId())
                .empleadoNombre(nombreCompleto)
                .fecha(a.getFecha())
                .horaEntrada(a.getHoraEntrada())
                .horaSalida(a.getHoraSalida())
                .estado(a.getEstado())
                .observacion(a.getObservacion())
                .build();
    }

    public Asistencia toEntity(AsistenciaDTO dto) {
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + dto.getEmpleadoId()));

        return Asistencia.builder()
                .id(dto.getId())
                .empleado(empleado)
                .fecha(dto.getFecha())
                .horaEntrada(dto.getHoraEntrada())
                .horaSalida(dto.getHoraSalida())
                .estado(dto.getEstado())
                .observacion(dto.getObservacion())
                .build();
    }
}
