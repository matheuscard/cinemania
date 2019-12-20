package conversores;

import controle.FilmeControle;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.Filme;

/**
 *
 * @author mathe
 */
@FacesConverter(forClass = Filme.class)
public class FilmeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = new Long(value);
        FilmeControle controle = (FilmeControle) context.getApplication().
                getELResolver().getValue(context.getELContext(), null, "filmeControle");
        
        return controle.buscarFilmePorId(id);
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Filme) {
            Filme filme = (Filme) object;
            return filme.getId() == null ? "" : filme.getId().toString();
        } else {
            throw new IllegalArgumentException("Objeto é do tipo "+ object.getClass().getName());
        }
    }    
}
