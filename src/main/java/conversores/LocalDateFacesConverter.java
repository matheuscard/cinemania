package conversores;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author mathe
 */
@FacesConverter(value = "localDateFacesConverter")
public class LocalDateFacesConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String stringValue) {
        System.out.println(stringValue);
        if (null == stringValue || stringValue.isEmpty()) {
            return null;
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = df.parse(stringValue);
            System.out.println(date.toString());
        } catch (ParseException ex) {
            Logger.getLogger(LocalDateFacesConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        LocalDate localDate;

        try {

            localDate = date.toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDate();

        } catch (DateTimeParseException e) {

            throw new ConverterException("O formato da data deve ser horas:minutos");
        }

        //DateTimeFormatter formatador = DateTimeFormatter
              //  .ofLocalizedDateTime(FormatStyle.SHORT)
                //.withLocale(new Locale("pt", "br"));
        return localDate;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object localDateValue) {

        if (null == localDateValue) {

            return "";
        }

        return ((LocalDateTime) localDateValue)
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        .withZone(ZoneId.systemDefault()));
    }
}
