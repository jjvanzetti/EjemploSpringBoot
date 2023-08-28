package com.soluciones.Facturacion.repository.gestion.general;
import com.soluciones.Facturacion.domain.gestion.general.MotivoDeuda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MotivoDeudaRepository  extends JpaRepository<MotivoDeuda, Integer>{
       
    @Query("select p from MotivoDeuda p  where p.estado=0 and p.codigo =?1")
    MotivoDeuda findFirstByCodigo(int codigo);
    
    MotivoDeuda findFirstById(Integer id);
    
    @Query("select p from MotivoDeuda p  where p.estado=0 order by p.denominacion")
    List<MotivoDeuda> buscarNoEliminados();
}
