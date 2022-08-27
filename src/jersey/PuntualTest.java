package jersey;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class PuntualTest {
	
	public void main() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Integer.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     
	    //Marshal the employees list in console
	    //jaxbMarshaller.marshal
	}
}
