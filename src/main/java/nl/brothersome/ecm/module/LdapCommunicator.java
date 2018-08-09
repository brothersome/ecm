package nl.brothersome.ecm.module;

import javafx.util.Pair;
import nl.brothersome.ecm.configuration.LdapConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class LdapCommunicator implements ILdapCommunicator, Closeable {
	private static Logging logger = new Logging(LdapCommunicator.class);

	@Autowired
	private LdapConfiguration ldapConfiguration;

	private static LdapTemplate ldapTemplate = null;

	public LdapCommunicator () {
		//
	}

	public boolean authenticate() {
		boolean result;
		DirContext contextSource;
		ldapTemplate = ldapConfiguration.ldapTemplate();
		contextSource = null;
		try {
			contextSource = ldapConfiguration.getContextSource().getContext(ldapConfiguration.getUser(), ldapConfiguration.getPassword());
			result = true;
		} catch (Exception e) {
			// Context creation failed - authentication did not succeed
			logger.error("Login failed", e);
			result =  false;
		} finally {
			// It is imperative that the created DirContext instance is always closed
			LdapUtils.closeContext(contextSource);
		}
		System.out.println("Authenticated: " + result);

		return result;
	}
	public void createEntry(Name dn, List<String> objectClasses, Map<String,String> attributes) {
		System.out.println("Creating entry begin");
		DirContextAdapter context = new DirContextAdapter(dn);
		if (objectClasses != null) {
			if (objectClasses.size() > 0) {
				String[] objscls = objectClasses.toArray(new String[0]);
				context.setAttributeValues("objectclass", objscls);
			}
		}
		if (attributes != null) {
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				context.setAttributeValue(entry.getKey(), entry.getValue());
			}
		}
		ldapTemplate.bind(context);
		System.out.println("Creating entry end");
	}
	public Name buildDn_cn(String s1) {
		return LdapNameBuilder.newInstance()
				.add("CN",s1)
				.build();
	}
	public Name buildDn_cn(String s1,String s2) {
		return LdapNameBuilder.newInstance()
				.add("CN",s1)
				.add("CN",s2)
				.build();
	}
	public Name buildDn_cn(String s1,String s2,String s3) {
		return LdapNameBuilder.newInstance()
				.add("CN",s1)
				.add("CN",s2)
				.add("CN",s3)
				.build();
	}
	public Name buildDn_cn(String s1,String s2,String s3,String s4) {
		return LdapNameBuilder.newInstance()
				.add("CN",s1)
				.add("CN",s2)
				.add("CN",s3)
				.add("CN",s4)
				.build();
	}

	public Name buildDn_cn(String s1,String s2,String s3,String s4, String s5) {
		return LdapNameBuilder.newInstance()
				.add("CN",s1)
				.add("CN",s2)
				.add("CN",s3)
				.add("CN",s4)
				.add("CN",s5)
				.build();
	}

	@Override
	public void createContainer(Name dn,String name) {
		List<String> ocs = new ArrayList<>();
		Map<String,String> atrs = new HashMap<>();
		ocs.add("top");
		ocs.add("container");
		atrs.put("cn",name);
		atrs.put("name",name);
		createEntry(dn,ocs,atrs);
	}
	public Pair<String,String> newPair(String k, String v) {
		return new Pair(k,v);
	}

	public void createContainer(String name) {
		Name dn = buildDn_cn(name);
		createContainer(dn,name);
	}



	public void createUser(Name dn, String name, Map<String,String> extraAttribs) {
		List<String> ocs = new ArrayList<>();
		Map<String,String> atrs = new HashMap<>();
		ocs.add("top");
		ocs.add("user");
		if (extraAttribs!=null) {
			atrs.putAll(extraAttribs);
		}
		atrs.put("cn",name);
		atrs.put("name",name);
		createEntry(dn,ocs,atrs);
	}


	@Override
	public void createGroup(Name dn,String name,Map<String,String> extraAttribs) {
		List<String> ocs = new ArrayList<>();
		Map<String,String> atrs = new HashMap<>();
		ocs.add("top");
		ocs.add("group");
		if (extraAttribs!=null) {
			atrs.putAll(extraAttribs);
		}
		atrs.put("cn",name);
		atrs.put("name",name);
		createEntry(dn,ocs,atrs);
	}

	@Override
	public int removeEntry(String dn) {
		return 0;
	}

	@Override
	public List<String> getAttributesFromObjectClass(String objectClassName, String attributeName) {
		return ldapTemplate.search(
				query().where("objectclass").is(objectClassName),
				new AttributesMapper<String>() {
					public String mapFromAttributes(Attributes attrs)
							throws NamingException, javax.naming.NamingException {
						return attrs.get("cn").get().toString();
					}
				});
	}

	@Override
	public List<String> getObjectClasses(String dn) {
		return ldapTemplate.search(
				query().base(dn),
				new AttributesMapper<String>() {
					public String mapFromAttributes(Attributes attrs)
							throws NamingException, javax.naming.NamingException {
						return attrs.get("objectclass").get().toString();
					}
				});
	}
	@Override
	public boolean exist(String rdn) {
		try {
			ldapTemplate.lookup(rdn);
			// System.out.println("RDN exists: " + rdn);
			return true;
		} catch (org.springframework.ldap.NamingException ne) {
			// System.out.println("RDN does not exists: " + rdn);
			return false;
		}
	}
	@Override
	public boolean exist(Name dn) {
		try {
			ldapTemplate.lookup(dn);
			return true;
		} catch (org.springframework.ldap.NamingException ne) {
			return false;
		}
	}

	public void hello() {
		System.out.println("Hello");
	}

	@Override
	public void close() throws IOException {

	}
}
