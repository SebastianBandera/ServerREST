package tag.render;

import java.io.StringWriter;
import java.util.List;
import java.util.function.BiFunction;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import discover.ItemServiceInfo;

import org.w3c.dom.Node;

public class DocumentHelper {

	private final static String INFO_NAME = "infoName";
	private final static String INFO_VALUE = "infoValue";

	public DocumentHelper() {

	}

	public Element processListItem(Element eListItemOriginal, ItemServiceInfo itemServiceInfo) {
		Document doc = eListItemOriginal.getOwnerDocument();

		// Clono el nodo 'li' y lo altero
		Element eListItem = (Element) eListItemOriginal.cloneNode(true);
		eListItem.removeAttribute("id");

		//Obtengo un subNodo que contiene dos campos (nombre y valor)
		Element eInfoOriginal = getElementById(eListItem, "#clone_info");

		//Lo suprimo, sera insertado N veces despues
		Element eSubList = (Element) eInfoOriginal.getParentNode();
		eSubList.removeChild(eInfoOriginal);
		
		//Cada addItemList debe clonar el nodo 'div' -> '#clone_info'
		addItemList(eInfoOriginal, eSubList, doc, "Nombre", itemServiceInfo.getName());
		
		if (!itemServiceInfo.getAlias().isEmpty()) {
			addItemList(eInfoOriginal, eSubList, doc, "Alias", prettyJoin(itemServiceInfo.getAlias(), ", ", ", "));
		}
		
		Element hrefNode = doc.createElement("a");
		hrefNode.setAttribute("href", itemServiceInfo.getPath());
		hrefNode.setAttribute("target", "_blank");
		hrefNode.appendChild(createTextNode(doc, itemServiceInfo.getPath()));
		addItemList(eInfoOriginal, eSubList, doc, "Ruta", hrefNode);
		
		if (!itemServiceInfo.getQueryParams().isEmpty()) {
			if (itemServiceInfo.getQueryParams().size() > 1) {
				addItemList(eInfoOriginal, eSubList, doc, "Parametros", prettyJoin(itemServiceInfo.getQueryParams(), ", ", ", "));
			} else {
				addItemList(eInfoOriginal, eSubList, doc, "Parametro", prettyJoin(itemServiceInfo.getQueryParams(), ", ", ", "));
			}
		}
		
		if (itemServiceInfo.getMethod().equals("POST")) {
			addItemList(eInfoOriginal, eSubList, doc, "Consume", itemServiceInfo.getConsumes());
		} else {
			if (!itemServiceInfo.getQueryParams().isEmpty()) {
				if (itemServiceInfo.getQueryParams().size() > 1) {
					addItemList(eInfoOriginal, eSubList, doc, "Consume", "Parametros");
				} else {
					addItemList(eInfoOriginal, eSubList, doc, "Consume", "Parametro");
				}
			}
		}
		
		addItemList(eInfoOriginal, eSubList, doc, "Produce", itemServiceInfo.getProduces());
		
		return eListItem;
	}

	private void addItemList(Element eListItemOriginal, Element eList, Document doc, String name, String value) {
		Element eInfo = (Element) eListItemOriginal.cloneNode(true);
		eInfo.removeAttribute("id");
		getFirstElementByClassName(eInfo, INFO_NAME).appendChild(createTextNode(doc, name));
		getFirstElementByClassName(eInfo, INFO_VALUE).appendChild(createTextNode(doc, value));
		eList.appendChild(eInfo);
	}
	
	private void addItemList(Element eListItemOriginal, Element eList, Document doc, String name, Element node) {
		Element eInfo = (Element) eListItemOriginal.cloneNode(true);
		eInfo.removeAttribute("id");
		getFirstElementByClassName(eInfo, INFO_NAME).appendChild(createTextNode(doc, name));
		getFirstElementByClassName(eInfo, INFO_VALUE).appendChild(node);
		eList.appendChild(eInfo);
	}

	public void trimTextNodes(Node node) {
		NodeList childNodes = node.getChildNodes();

		int size = childNodes.getLength() - 1;
		//For inverso para poder eliminar sin mover los elementos de la lista
		for (int n = size; n >= 0; n--) {
			Node child = childNodes.item(n);
			short nodeType = child.getNodeType();

			if (nodeType == Node.ELEMENT_NODE) {
				trimTextNodes(child);
			} else if (nodeType == Node.TEXT_NODE) {
				String nodeValue = child.getNodeValue();
				nodeValue = nodeValue.replaceAll("\n|\r|\t", "");
				String trimmedNodeVal = nodeValue.trim();
				if (trimmedNodeVal.length() == 0) {
					node.removeChild(child);
				} else {
					child.setNodeValue(trimmedNodeVal);
				}
			} else if (nodeType == Node.COMMENT_NODE)
				node.removeChild(child);
		}
	}

	public String prettyJoin(List<String> list, String sepNormal, String sepFinal) {
		StringBuilder sb = new StringBuilder();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				if (i == size - 1) {
					sb.append(sepFinal);
				} else {
					sb.append(sepNormal);
				}
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	private Node createTextNode(Document doc, String string) {
		return doc.createTextNode(string);
	}

	public String parseDocumentToString(Document doc, Transformer transformer) throws TransformerException {
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult strResult = new StreamResult(writer);
		transformer.transform(source, strResult);
		return writer.toString();
	}

	public Element getFirstElementByClassName(Element element, String value) {
		Element elem = null;
		try {
			Seeker info = new Seeker("class", value, (elemValue, valueToSearch) -> elemValue.contains(valueToSearch));

			elem = info.search(element);
		} catch (Exception e) {
			e.printStackTrace();
			elem = null;
		}
		return elem;
	}

	public Element getFirstElementByClassName(Document doc, String value) {
		return getFirstElementByClassName(doc.getDocumentElement(), value);
	}

	public Element getElementById(Element element, String value) {
		Element elem = null;
		try {
			Seeker info = new Seeker("id", value, (elemValue, valueToSearch) -> elemValue.equals(valueToSearch));

			elem = info.search(element);
		} catch (Exception e) {
			e.printStackTrace();
			elem = null;
		}
		return elem;
	}

	public Element getElementById(Document doc, String value) {
		return getElementById(doc.getDocumentElement(), value);
	}

	private class Seeker {
		public boolean encontrado = false;
		private Element result = null;
		private String attName;
		private String value;
		private BiFunction<String, String, Boolean> foundCodition;

		public Seeker(String attName, String value, BiFunction<String, String, Boolean> foundCodition) {
			this.attName = attName == null ? "" : attName;
			this.value = value == null ? "" : value;

			if (foundCodition == null) {
				foundCodition = (a, b) -> a != null && b != null && a.equals(b);
			}
			this.foundCodition = foundCodition;
		}

		public Element search(Node node) {
			if (node == null || !(node instanceof Element)) {
				return this.result;
			}

			search_rec(node);
			return result;
		}

		private void search_rec(Node node) {
			if (node == null || encontrado || !(node instanceof Element)) {
				return;
			}

			process(node);

			if (encontrado) {
				return;
			}

			NodeList list = node.getChildNodes();
			if (list != null) {
				int length = list.getLength();
				for (int i = 0; i < length; i++) {
					Node childnode = list.item(i);
					if (childnode != null) {
						search_rec(childnode);
					}
				}
			}
		}

		private void process(Node node) {
			NamedNodeMap mapAttributes = node.getAttributes();

			if (mapAttributes == null) {
				return;
			}

			Node att = mapAttributes.getNamedItem(attName);
			if (att == null) {
				return;
			}

			String attValue = att.getNodeValue();

			if (attValue == null) {
				attValue = "";
			}

			if (foundCodition.apply(attValue, value)) {
				this.result = (Element) node;
				this.encontrado = true;
			}
		}
	}
}
