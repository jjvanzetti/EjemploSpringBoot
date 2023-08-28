package com.soluciones.Facturacion.repository.gestion.producto;

import com.soluciones.Facturacion.domain.gestion.producto.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("select p from Producto p  where p.estado=0 and p.codigo like ?1")
    Producto findFirstByCodigo(String codigo);
    
    Producto findFirstById(Integer id);
    
    @Query("select p from Producto p  where p.estado=0 order by p.nombre")
    List<Producto> buscarProductosNoEliminados();
}
