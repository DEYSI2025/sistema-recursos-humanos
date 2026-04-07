package com.example.empleados.dto;

import com.example.empleados.model.Asistencia.EstadoAsistencia;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsistenciaDTO {

    private Long id;

    @NotNull(message = "El ID del empleado es obligatorio")
    private Long empleadoId;

    private String empleadoNombre;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    private LocalTime horaEntrada;

    private LocalTime horaSalida;

    @NotNull(message = "El estado es obligatorio")
    private EstadoAsistencia estado;

    @Size(max = 255)
    private String observacion;
}
