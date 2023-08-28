package com.soluciones.Facturacion.repository.gestion.general;


import com.soluciones.Facturacion.domain.gestion.general.CategoriaIVA;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaIVARepository  extends JpaRepository<CategoriaIVA, Integer>{
       
    @Query("select p from CategoriaIVA p  where p.estado=0 and p.codigo =?1")
    CategoriaIVA findFirstByCodigo(int codigo);
    
    CategoriaIVA findFirstById(Integer id);
    
    @Query("select p from CategoriaIVA p  where p.estado=0 order by p.denominacion")
    List<CategoriaIVA> buscarNoEliminados();
    
    
    @Query("select p from CategoriaIVA p  where p.estado=0 and p.denominacion like %?1% order by p.denominacion")
    List<CategoriaIVA> buscarNoEliminados(String consulta);
    
    List<CategoriaIVA> findByDenominacionContaining(String consulta); //igual que el buscar poniendo findBy y containig es una alternativa
}
