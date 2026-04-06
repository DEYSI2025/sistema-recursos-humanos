package com.empresa.recursoshumanos.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.empresa.recursoshumanos.service.AsistenciaService;

@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/{dni}")
    public String marcar(@PathVariable String dni) {
        return asistenciaService.registrar(dni);
    }
}
