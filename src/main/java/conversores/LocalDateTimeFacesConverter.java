package conversores;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@FacesConverter(value = "localDateTimeFacesConverter")
public class LocalDateTimeFacesConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String stringValue) {
        System.out.println(stringValue);
        if (null == stringValue || stringValue.isEmpty()) {
            return null;
        }

        DateFormat df = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        try {
            date = df.parse(stringValue);
            System.out.println(date.toString());
        } catch (ParseException ex) {
            Logger.getLogger(LocalDateTimeFacesConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        LocalDateTime localDateTime;

        try {

            localDateTime = date.toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();

        } catch (DateTimeParseException e) {

            throw new ConverterException("O formato da data e hora deve ser 13/11/2015 12:00.");
        }

        //DateTimeFormatter formatador = DateTimeFormatter
              //  .ofLocalizedDateTime(FormatStyle.SHORT)
                //.withLocale(new Locale("pt", "br"));
        return localDateTime;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object localDateTimeValue) {

        if (null == localDateTimeValue) {

            return "";
        }

        return ((LocalDateTime) localDateTimeValue)
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")
                        .withZone(ZoneId.systemDefault()));
    }
}
