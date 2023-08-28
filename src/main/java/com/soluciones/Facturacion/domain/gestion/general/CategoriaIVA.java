package com.soluciones.Facturacion.domain.gestion.general;

import jakarta.persistence.Entity;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class CategoriaIVA extends TipoObjeto{
    
    @NotNull(message = "Debes especificar la alicuota IVA")
    private double alicuota; 

    
   @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoriaIVA other = (CategoriaIVA) obj;
        return Objects.equals(this.getId(), other.getId());
    }
    
    @Override
    public String toString (){
       return this.getDenominacion();
    }
    
}
