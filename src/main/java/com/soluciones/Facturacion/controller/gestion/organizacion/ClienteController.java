package com.soluciones.Facturacion.controller.gestion.organizacion;
import com.soluciones.Facturacion.domain.gestion.organizacion.Operador;
import com.soluciones.Facturacion.service.gestion.general.CategoriaIVAService;
import com.soluciones.Facturacion.service.gestion.organizacion.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/clientes")
public class ClienteController {
  
    @Autowired
    private ClienteService clienteService;
  
    @Autowired
    private CategoriaIVAService categoriaIVAService;
    
    @Value("${path_organizacion}")
    String path;
    
    @RequestMapping("/buscarPage") 
    public String buscarClientes(@RequestParam("q") String consulta, Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, HttpServletRequest request) { 
        List<Operador> destacados = clienteService.buscarNoEliminados(consulta);   
       // System.out.println(destacados);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Operador> bookPage = clienteService.findPaginated(PageRequest.of(currentPage - 1, pageSize),destacados);
        
        model.addAttribute("datos", bookPage);
        model.addAttribute("lista", bookPage.getContent());
        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return path+"ver_cliente";
    }
    
    @GetMapping(value = "/agregar")
    public String agregar(Model model) {
        model.addAttribute("categoriasIva", categoriaIVAService.buscarNoEliminados());
        model.addAttribute("modelo", new Operador());
        return path+"agregar_cliente";
    }

    @GetMapping(value = "/mostrar")
    public String mostrar(Model model) {
        model.addAttribute("clientes", clienteService.buscarNoEliminados());
        return path+"ver_cliente";
    }

    
    @GetMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes redirectAttrs) {
        redirectAttrs
            .addFlashAttribute("mensaje", "Eliminado correctamente")
            .addFlashAttribute("clase", "warning");       
        Operador objetoExistente = clienteService.findFirstById(id);
        if ( objetoExistente == null ) {           
            redirectAttrs
                    .addFlashAttribute("mensaje", "no existe un producto con ese código")
                    .addFlashAttribute("clase", "warning");
        }
        else{
            objetoExistente.setEstado(1);
            clienteService.save(objetoExistente);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Eliminado correctamente")
                    .addFlashAttribute("clase", "success");          
        }
       return "redirect:/clientes/mostrar";
    }

    // Se colocó el parámetro ID para eso de los errores, ya sé el id se puede recuperar
    // a través del modelo, pero lo que yo quiero es que se vea la misma URL para regresar la vista con
    // los errores en lugar de hacer un redirect, ya que si hago un redirect, no se muestran los errores del formulario
    // y por eso regreso mejor la vista ;)
    @PostMapping(value = "/editar/{id}")
    public String actualizar(@ModelAttribute @Valid Operador model, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (model.getId() != null) {
                return path+"agregar_cliente";
            }
            return "redirect:/clientes/mostrar";
        }
        Operador objetoExistente = clienteService.findFirstByCodigo(model.getCodigo());

        if (objetoExistente != null && !objetoExistente.getId().equals(model.getId())) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/clientes/agregar";
        }
        clienteService.save(model);
        redirectAttrs
                .addFlashAttribute("mensaje", "Editado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/clientes/mostrar";
    }

    @GetMapping(value = "/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("categoriasIva", categoriaIVAService.buscarNoEliminados());
        model.addAttribute("modelo", clienteService.findById(id).orElse(null));
        return path+"agregar_cliente";
    }

    @PostMapping(value = "/agregar")
    public String guardar(@ModelAttribute @Valid Operador model, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return path+"agregar_cliente";
        }
        if (clienteService.findFirstByCodigo(model.getCodigo()) != null) {
            redirectAttrs
                .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
                .addFlashAttribute("clase", "warning");
            return "redirect:/clientes/agregar";
        }
        clienteService.save(model);
        redirectAttrs
                .addFlashAttribute("mensaje", "Agregado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/clientes/agregar";
    }
    
    @PostMapping(value = "/save/{id}")
    public String guardarActualizar(@ModelAttribute @Valid Operador model, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "redirect:/clientes/mostrar";
        }
        
        Operador posibleProductoExistente =clienteService.findFirstByCodigo(model.getCodigo());

        if (posibleProductoExistente != null && !posibleProductoExistente.getId().equals(model.getId())) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/clientes/agregar";
        }
        if(model.getId()!=null){
            clienteService.save(model);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Editado correctamente")
                    .addFlashAttribute("clase", "success");
        }
        else{
            clienteService.save(model);
            redirectAttrs
                .addFlashAttribute("mensaje", "Agregado correctamente")
                .addFlashAttribute("clase", "success");
            }
        return "redirect:/clientes/mostrar";
    }
  
    @RequestMapping("/buscar") 
    public String buscar(@RequestParam("q") String consulta, Model model) { 
        List<Operador> destacados = clienteService.buscar(consulta); 
        model.addAttribute("clientes", destacados); 
        return path+"ver_cliente";
    } 
    
}
