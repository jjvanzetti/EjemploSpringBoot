/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soluciones.Facturacion.service.gestion.general;

import com.soluciones.Facturacion.domain.gestion.general.MotivoDeuda;
import com.soluciones.Facturacion.repository.gestion.general.MotivoDeudaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotivoDeudaService {
    @Autowired
    private MotivoDeudaRepository modelRepository;

    
    public  List<MotivoDeuda> buscarNoEliminados() {
        return modelRepository.buscarNoEliminados();
    }

    public  MotivoDeuda findFirstById(Integer id) {
        return modelRepository.findFirstById(id);
    }
    
    public  void save(MotivoDeuda model) {
        modelRepository.save(model);
    }

    public  MotivoDeuda findFirstByCodigo(int id) {
        return modelRepository.findFirstByCodigo(id);
    }

    public Optional<MotivoDeuda>  findById(int id) {
         return   modelRepository.findById(id);
    }

  
}
