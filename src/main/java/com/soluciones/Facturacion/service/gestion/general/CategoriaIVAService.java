/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soluciones.Facturacion.service.gestion.general;

import com.soluciones.Facturacion.domain.gestion.general.CategoriaIVA;
import com.soluciones.Facturacion.repository.gestion.general.CategoriaIVARepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaIVAService {
    @Autowired
    private CategoriaIVARepository modelRepository;

    public  List<CategoriaIVA> buscarNoEliminados() {
        return modelRepository.buscarNoEliminados();
    }

    public CategoriaIVA findFirstById(Integer id) {
        return modelRepository.findFirstById(id);
    }
    
    public  void save(CategoriaIVA model) {
        modelRepository.save(model);
    }

    public CategoriaIVA findFirstByCodigo(int id) {
        return modelRepository.findFirstByCodigo(id);
    }

    public Optional<CategoriaIVA>  findById(int id) {
         return   modelRepository.findById(id);
    }
    
    public List<CategoriaIVA> buscar(String consulta) {
        return modelRepository.buscarNoEliminados(consulta);
    }
      

}
