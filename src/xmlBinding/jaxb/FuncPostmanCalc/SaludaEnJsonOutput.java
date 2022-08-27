package xmlBinding.jaxb.FuncPostmanCalc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SaludaEnJson")
public class SaludaEnJsonOutput {
	private String resultado;

	public SaludaEnJsonOutput() {}
	
	public SaludaEnJsonOutput(String resultado) {
		super();
		this.resultado = resultado;
	}
	
	@XmlElement(name = "resultado")
	public String getresultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
}
