package tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HelloTag extends SimpleTagSupport {

	private String message1;
	private String message2;

	public void setMessage1(String msg) {
		this.message1 = msg;
	}
	
	public void setMessage2(String msg) {
		this.message2 = msg;
	}

	public void doTag() throws JspException, IOException {
		if (message1 != null && message2 != null) {
			getJspContext().getOut().println(message1 + " - " + message2);
		} else {
			getJspContext().getOut().println("sin msg");
		}
	}
}