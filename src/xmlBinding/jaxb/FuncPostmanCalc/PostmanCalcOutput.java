package xmlBinding.jaxb.FuncPostmanCalc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PostmanCalcOutput")
public class PostmanCalcOutput {
	private int resultado;

	public PostmanCalcOutput() {}
	
	public PostmanCalcOutput(int resultado) {
		super();
		this.resultado = resultado;
	}
	
	@XmlElement(name = "resultado")
	public int getresultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
}
