package discover;

import java.util.ArrayList;
import java.util.List;

public class ItemServiceInfo {
	private String method;
	private String path;
	private String name;
	private List<String> alias;
	private String produces;
	private String consumes;
	private List<String> queryParams;

	public ItemServiceInfo() {
		method = "";
		name = "";
		alias = new ArrayList<String>();
		produces = "";
		consumes = "";
		queryParams = new ArrayList<String>();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAlias() {
		return alias;
	}
	
	public String getProduces() {
		return produces;
	}

	public void setProduces(String produces) {
		this.produces = produces;
	}

	public String getConsumes() {
		return consumes;
	}

	public void setConsumes(String consumes) {
		this.consumes = consumes;
	}

	public List<String> getQueryParams() {
		return queryParams;
	}
}