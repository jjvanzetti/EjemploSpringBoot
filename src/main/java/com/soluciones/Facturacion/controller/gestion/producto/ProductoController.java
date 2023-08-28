package com.soluciones.Facturacion.controller.gestion.producto;

import com.soluciones.Facturacion.domain.gestion.producto.Producto;
import com.soluciones.Facturacion.repository.gestion.producto.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(path = "/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository productosRepository;
    
    @Value("${path_producto}")
    String path;

    @GetMapping(value = "/agregar")
    public String agregarProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return path+"/agregar_producto";
    }

    @GetMapping(value = "/mostrar")
    public String mostrarProductos(Model model) {
        model.addAttribute("productos", productosRepository.buscarProductosNoEliminados());
        return path+"/ver_productos";
    }

    @PostMapping(value = "/eliminar")
    public String eliminarProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttrs) {
        redirectAttrs
                .addFlashAttribute("mensaje", "Eliminado correctamente")
                .addFlashAttribute("clase", "warning");       
        Producto posibleProductoExistente = productosRepository.findFirstById(producto.getId());
        if ( posibleProductoExistente == null ) {           
            redirectAttrs
                    .addFlashAttribute("mensaje", "no existe un producto con ese código")
                    .addFlashAttribute("clase", "warning");
        }else{
            posibleProductoExistente.setEstado(1);
            productosRepository.save(posibleProductoExistente);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Eliminado correctamente")
                    .addFlashAttribute("clase", "success");          
        }
       return "redirect:/productos/mostrar";
    }

    // Se colocó el parámetro ID para eso de los errores, ya sé el id se puede recuperar
    // a través del modelo, pero lo que yo quiero es que se vea la misma URL para regresar la vista con
    // los errores en lugar de hacer un redirect, ya que si hago un redirect, no se muestran los errores del formulario
    // y por eso regreso mejor la vista ;)
    @PostMapping(value = "/editar/{id}")
    public String actualizarProducto(@ModelAttribute @Valid Producto producto, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (producto.getId() != null) {
                return path+"/agregar_producto";
            }
            return "redirect:/productos/mostrar";
        }
        Producto posibleProductoExistente = productosRepository.findFirstByCodigo(producto.getCodigo());

        if (posibleProductoExistente != null && !posibleProductoExistente.getId().equals(producto.getId())) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/productos/agregar";
        }
        productosRepository.save(producto);
        redirectAttrs
                .addFlashAttribute("mensaje", "Editado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/productos/mostrar";
    }

    @GetMapping(value = "/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("producto", productosRepository.findById(id).orElse(null));
        return path+"/agregar_producto";
    }

    @PostMapping(value = "/agregar")
    public String guardarProducto(@ModelAttribute @Valid Producto producto, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return path+"/agregar_producto";
        }
        if (productosRepository.findFirstByCodigo(producto.getCodigo()) != null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/productos/agregar";
        }
        productosRepository.save(producto);
        redirectAttrs
                .addFlashAttribute("mensaje", "Agregado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/productos/agregar";
    }
    
    @PostMapping(value = "/save/{id}")
    public String guardarActualizarProducto(@ModelAttribute @Valid Producto producto, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "redirect:/productos/mostrar";
        }
        
            Producto posibleProductoExistente = productosRepository.findFirstByCodigo(producto.getCodigo());

            if (posibleProductoExistente != null && !posibleProductoExistente.getId().equals(producto.getId())) {
                redirectAttrs
                        .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
                        .addFlashAttribute("clase", "warning");
                return "redirect:/productos/agregar";
            }
        if(producto.getId()!=null){
            productosRepository.save(producto);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Editado correctamente")
                    .addFlashAttribute("clase", "success");
        }else{
            productosRepository.save(producto);
            redirectAttrs
                .addFlashAttribute("mensaje", "Agregado correctamente")
                .addFlashAttribute("clase", "success");
            }
        return "redirect:/productos/mostrar";
    }
}
