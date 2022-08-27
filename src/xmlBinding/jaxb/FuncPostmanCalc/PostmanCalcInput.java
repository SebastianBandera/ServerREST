package xmlBinding.jaxb.FuncPostmanCalc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PostmanCalc")
public class PostmanCalcInput {
	private String a;
	private String b;

	public PostmanCalcInput() {}
	
	public PostmanCalcInput(String a, String b) {
		super();
		this.a = a;
		this.a = b;
	}
	
	@XmlElement(name = "a")
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
	
	@XmlElement(name = "b")
	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
}
