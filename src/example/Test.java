package example;

import example.client.CalTaxServiceLocator;
import example.client.CalTax_PortType;

public class Test {
    public static void main(String[] argv) {
        try {
            CalTaxServiceLocator locator = new CalTaxServiceLocator();
            CalTax_PortType service = locator.getCalTax();
            // If authorization is required
            //((HelloWorldSoapBindingStub)service).setUsername("user3");
            //((HelloWorldSoapBindingStub)service).setPassword("pass3");
            // invoke business method
            System.out.println(service.calTax("8000"));
        } catch (javax.xml.rpc.ServiceException ex) {
            ex.printStackTrace();
        } catch (java.rmi.RemoteException ex) {
            ex.printStackTrace();
        }
    }
}