/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soluciones.Facturacion.service.gestion.organizacion;

import com.soluciones.Facturacion.domain.gestion.organizacion.Operador;
import com.soluciones.Facturacion.repository.gestion.organizacion.ClienteRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository modelRepository;

    public  List<Operador> buscarNoEliminados() {
        return modelRepository.buscarNoEliminados();
    }

    public  List<Operador> buscarNoEliminados(String consulta) {
        return modelRepository.buscarNoEliminados(consulta);
    }
  
    public  Operador findFirstById(Integer id) {
        return modelRepository.findFirstById(id);
    }
    
    public  void save(Operador model) {
        modelRepository.save(model);
    }

    public  Operador findFirstByCodigo(int id) {
        return modelRepository.findFirstByCodigo(id);
    }

    public Optional<Operador>  findById(int id) {
         return   modelRepository.findById(id);
    }
    public List<Operador> buscar(String consulta) {
        return modelRepository.findByRazonSocialContaining(consulta);
    }
    

    public Page<Operador> findPaginated(Pageable pageable,List<Operador> operadores) {
  
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Operador> list;
      System.out.println(operadores.size());
        if (operadores.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, operadores.size());
            list = operadores.subList(startItem, toIndex);
        }

        Page<Operador> bookPage
          = new PageImpl<Operador>(list, PageRequest.of(currentPage, pageSize), operadores.size());

        return bookPage;
    }
  
  
}
