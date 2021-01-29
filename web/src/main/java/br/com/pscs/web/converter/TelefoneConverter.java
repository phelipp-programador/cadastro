package br.com.pscs.web.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.pscs.domain.model.Telefone;

@FacesConverter("telefoneConverter")
public class TelefoneConverter implements Converter<Telefone> {
	private final String regex = "\\D";

	@Override
	public Telefone getAsObject(FacesContext context, UIComponent component, String value) {
		String somenteNumero = value.replaceAll(regex, "");
		try {
			String ddd = somenteNumero.substring(0, 2);
			String numero = somenteNumero.substring(2);
			System.out.println(ddd);
			return new Telefone().builder().ddd(ddd).numero(numero).build();
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Problemas na conversão do telefone.",
					"Ele deve ser informado com  de área e o número. Ex: (11) 2626-9415");

			throw new ConverterException(facesMessage);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Telefone value) {
		if (value == null) {
			return null;
		}

		String ddd = value.getDdd().replaceAll(regex, "");
		return " (" + ddd + ") " + value.getNumero();

	}

}
