package com.soluciones.Facturacion.controller.gestion.general;
import com.soluciones.Facturacion.domain.gestion.general.MotivoDeuda;
import com.soluciones.Facturacion.service.gestion.general.MotivoDeudaService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/motivosDeuda")
public class MotivoDeudaController {
  
    @Autowired
    private MotivoDeudaService objetoService;
  
    @Value("${path_general}")
    String path;
    
    
    @GetMapping(value = "/agregar")
    public String agregar(Model model) {
        model.addAttribute("modelo", new MotivoDeuda());
        return path+"agregar_motivo_deuda";
    }

    @GetMapping(value = "/mostrar")
    public String mostrar(Model model) {
        model.addAttribute("motivos", objetoService.buscarNoEliminados());
        return path+"ver_motivo_deuda";
    }

    @PostMapping(value = "/eliminar")
    public String eliminar(@ModelAttribute MotivoDeuda motivo, RedirectAttributes redirectAttrs) {
        redirectAttrs
            .addFlashAttribute("mensaje", "Eliminado correctamente")
            .addFlashAttribute("clase", "warning");       
        MotivoDeuda objetoExistente = objetoService.findFirstById(motivo.getId());
        if ( objetoExistente == null ) {           
            redirectAttrs
                .addFlashAttribute("mensaje", "no existe un motivo deuda con ese código")
                .addFlashAttribute("clase", "warning");
        }
        else{
            objetoExistente.setEstado(1);
            objetoService.save(objetoExistente);
            redirectAttrs
                .addFlashAttribute("mensaje", "Eliminado correctamente")
                .addFlashAttribute("clase", "success");          
        }
       return "redirect:/motivosDeuda/mostrar";
    }

    // Se colocó el parámetro ID para eso de los errores, ya sé el id se puede recuperar
    // a través del modelo, pero lo que yo quiero es que se vea la misma URL para regresar la vista con
    // los errores en lugar de hacer un redirect, ya que si hago un redirect, no se muestran los errores del formulario
    // y por eso regreso mejor la vista ;)
    @PostMapping(value = "/editar/{id}")
    public String actualizar(@ModelAttribute @Valid MotivoDeuda model, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (model.getId() != null) {
                return path+"agregar_motivo_deuda";
            }
            return "redirect:/motivosDeudas/mostrar";
        }
        MotivoDeuda objetoExistente = objetoService.findFirstByCodigo(model.getCodigo());

        if (objetoExistente != null && !objetoExistente.getId().equals(model.getId())) {
            redirectAttrs
                .addFlashAttribute("mensaje", "Ya existe una categoría IVA con ese código")
                .addFlashAttribute("clase", "warning");
            return "redirect:/motivosDeuda/agregar";
        }
        objetoService.save(model);
        redirectAttrs
            .addFlashAttribute("mensaje", "Editado correctamente")
            .addFlashAttribute("clase", "success");
        return "redirect:/motivosDeuda/mostrar";
    }

    @GetMapping(value = "/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("modelo", objetoService.findById(id).orElse(null));
        return path+"agregar_motivo_deuda";
    }

    @PostMapping(value = "/agregar")
    public String guardar(@ModelAttribute @Valid MotivoDeuda model, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return path+"agregar_motivo_deuda";
        }
        if (objetoService.findFirstByCodigo(model.getCodigo()) != null) {
            redirectAttrs
                .addFlashAttribute("mensaje", "Ya existe una categoría IVA con ese código")
                .addFlashAttribute("clase", "warning");
            return "redirect:/categoriasIVA/agregar";
        }
        objetoService.save(model);
        redirectAttrs
            .addFlashAttribute("mensaje", "Agregado correctamente")
            .addFlashAttribute("clase", "success");
        return "redirect:/motivosDeuda/agregar";
    }
    
    @PostMapping(value = "/save/{id}")
    public String guardarActualizar(@ModelAttribute @Valid MotivoDeuda model, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "redirect:/motivosDeuda/mostrar";
        }
        
        MotivoDeuda objetoExistente =objetoService.findFirstByCodigo(model.getCodigo());

        if (objetoExistente != null && !objetoExistente.getId().equals(model.getId())) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe una categoría iva con ese código")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/motivosDeuda/agregar";
        }
        if(model.getId()!=null){
            objetoService.save(model);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Editado correctamente")
                    .addFlashAttribute("clase", "success");
        }
        else{
            objetoService.save(model);
            redirectAttrs
                .addFlashAttribute("mensaje", "Agregado correctamente")
                .addFlashAttribute("clase", "success");
            }
        return "redirect:/motivosDeuda/mostrar";
    }

}
