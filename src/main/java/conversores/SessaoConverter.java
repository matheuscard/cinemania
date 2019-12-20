package conversores;

import controle.SessaoControle;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.Sessao;

/**
 *
 * @author mathe
 */
@FacesConverter(forClass = Sessao.class)
public class SessaoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = new Long(value);
        SessaoControle controle = (SessaoControle) context.getApplication().
                getELResolver().getValue(context.getELContext(), null, "sessaoControle");
        
        return controle.buscarSessaoPorId(id);
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Sessao) {
            Sessao sessao = (Sessao) object;
            return sessao.getNumero()== null ? "" : sessao.getNumero().toString();
        } else {
            throw new IllegalArgumentException("Objeto é do tipo "+ object.getClass().getName());
        }
    }    
}
