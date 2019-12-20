package conversores;

import controle.TipoControle;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.Tipo;

/**
 *
 * @author mathe
 */
@FacesConverter(forClass = Tipo.class)
public class TipoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = new Long(value);
        TipoControle controle = (TipoControle) context.getApplication().
                getELResolver().getValue(context.getELContext(), null, "tipoControle");
        
        return controle.buscarTipoPorId(id);
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Tipo) {
            Tipo tipo = (Tipo) object;
            return tipo.getID() == null ? "" : tipo.getID().toString();
        } else {
            throw new IllegalArgumentException("Objeto é do tipo "+ object.getClass().getName());
        }
    }    
}
