package nl.brothersome.ecm.module;

import javax.naming.Name;
import java.util.List;
import java.util.Map;

public interface ILdapCommunicator {
	public void createEntry(Name dn,List<String> objectClasses, Map<String,String> attributes);
	public void createUser(Name dn,String name,Map<String,String> extraAttribs);
	public void createContainer(Name dn,String name);
	public void createGroup(Name dn,String name,Map<String,String> extraAttribs);
	int removeEntry(String dn);
	List<String> getAttributesFromObjectClass(String objectClassName,String attributeName);
	List<String> getObjectClasses(String dn);
	boolean exist(String dn);
	boolean exist(Name dn);
}
