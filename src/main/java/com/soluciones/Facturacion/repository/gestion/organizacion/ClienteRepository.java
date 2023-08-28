package com.soluciones.Facturacion.repository.gestion.organizacion;


import com.soluciones.Facturacion.domain.gestion.organizacion.Operador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository  extends JpaRepository<Operador, Integer>{
       
    @Query("select p from Operador p  where p.estado=0 and p.codigo =?1")
    Operador findFirstByCodigo(int codigo);
    

    
    Operador findFirstById(Integer id);
    
    @Query("select p from Operador p  where p.estado=0 order by p.razonSocial")
    List<Operador> buscarNoEliminados();
    
    @Query("select p from Operador p  where p.estado=0 and p.razonSocial like %?1% order by p.razonSocial")
    List<Operador> buscarNoEliminados(String consulta);
    
    List<Operador> findByRazonSocialContaining(String consulta); //igual que el buscar poniendo findBy y containig es una alternativa
}
