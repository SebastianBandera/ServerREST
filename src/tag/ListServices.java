package tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import tag.render.Finder;

public class ListServices extends SimpleTagSupport {

	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public void doTag() throws JspException, IOException {
		if (type == null) {
			return;
		}
		
		String list;
		if (type.startsWith("G")) {
			list = (new Finder("GET")).toString();
		} else {
			list = (new Finder("POST")).toString();
		}
		
		
		getJspContext().getOut().println(list);
	}
}