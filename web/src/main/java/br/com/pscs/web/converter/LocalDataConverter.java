package br.com.pscs.web.converter;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("locaDataConverter")
public class LocalDataConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		try {
			String[] data = value.split("[//|-]");
			LocalDate date = LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]),
					Integer.parseInt(data[0]));
			return date;
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Problemas na conversão da data.",
					"Ele deve ser informado com  dia mes e ano. Ex: 01/01/2021");

			throw new ConverterException(facesMessage);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		LocalDate date = (LocalDate) value;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formated=format.format(date);
		return formated ;
	}

}
