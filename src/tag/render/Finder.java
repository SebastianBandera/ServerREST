package tag.render;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import discover.ItemServiceInfo;
import discover.ServiceInfo;
import params.Global;
import system.InstancesManager;

public class Finder {

	private String methodName;

	private InstancesManager instancesManager;

	public Finder(String methodName) {
		this.methodName = methodName;

		this.instancesManager = new InstancesManager();
	}

	@Override
	public String toString() {
		String result = "";

		try {
			DocumentBuilder docBuilder = instancesManager.getDocumentBuilder();
			Transformer transformer = instancesManager.getTransformer();
			DocumentHelper docHelper = new DocumentHelper();

			String fileLoc = Global.go(Global.getAppPath(), "templates", "html", "ListServices.html");
			Document doc = docBuilder.parse(new File(fileLoc));

			Element eListItemOriginal = docHelper.getElementById(doc, "#clone_li");
			Element eList = (Element) eListItemOriginal.getParentNode();
			//Remueve el nodo, lo insertara clonado N veces despues
			eList.removeChild(eListItemOriginal);

			List<ItemServiceInfo> listService;
			if (this.methodName.equals("GET")) {
				listService = ServiceInfo.getServicesGET();
			} else {
				listService = ServiceInfo.getServicesPOST();
			}
			
			for (int i = 0; i < listService.size(); i++) {
				//Proceso el nodo que esta desconectado de la estructura principal
				Element eListItem = docHelper.processListItem(eListItemOriginal, listService.get(i));
				
				eList.appendChild(eListItem);
			}
			
			docHelper.trimTextNodes(doc);
			
			if (listService.size() == 0) {
				Node empty = doc.createTextNode("Ninguno");
				
				eList.appendChild(empty);
			}

			result = docHelper.parseDocumentToString(doc, transformer);
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}

		return result;
	}
}
