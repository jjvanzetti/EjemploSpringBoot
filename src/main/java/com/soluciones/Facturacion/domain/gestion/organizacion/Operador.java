package com.soluciones.Facturacion.domain.gestion.organizacion;

import com.soluciones.Facturacion.domain.gestion.general.CategoriaIVA;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Operador implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operador_id_seq")
    @SequenceGenerator(name = "operador_id_seq", sequenceName = "operador_id_seq", allocationSize = 1)  
    private Integer id;

    private int codigo;
   
    @NotNull(message = "Debes especificar la razón social")
    @Column(columnDefinition = "TEXT")
    private String razonSocial;
    private int estado;
    private int sistema; // 0 normal  -  1 no se puede borrar -  2 no se puede editar - 3 no se puede ni borrar ni editar
    
   @Column(columnDefinition = "TEXT")
    private String letra;
    
    @Column(columnDefinition = "TEXT")
    private String contacto;
    
    private int tipoOperador;  //0 cliente 1 proveedor 2cliente/proveedor  3/transportista   4 Banco
        public static final int CLIENTE=0;
        public static final int PROVEEDOR=1;
        public static final int CLIENTE_PROVEEDOR=2;
        public static final int TRANSPORTISTA=3;
        public static final int BANCO=4;
        public static final int PERSONAL=5;
        public static final int CONDUCTOR=6;
        public static final int LIBRADOR=7;
        public static final int ORGANIZACION=8;
        public static final int TARJETA=10;
        public static final int ENCARGADO=11;

    private int tipoProveedor;  
        public static final int TODOS=0;
        public static final int MATERIA_PRIMA=1;
        public static final int GASTOS_GENERALES=2;

    private int diasVencimiento;
    private boolean noCliente;

    @Column(columnDefinition = "TEXT")
    private String denominacionAfip;

    private Date fechaInicio;

    @ManyToOne
    private CategoriaIVA categoriaIva;
    
}
