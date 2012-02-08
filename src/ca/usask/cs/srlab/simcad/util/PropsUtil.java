package ca.usask.cs.srlab.simcad.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import ca.usask.cs.srlab.simcad.Constants;
import ca.usask.cs.srlab.simcad.SimcadException;

public final class PropsUtil {
		
	private static Properties properties;
	
	{
		InputStream userConfig = getResourceAsStream(Constants.EXTERNAL_CONFIGURATION_FILE);
		InputStream localConfig = getResourceAsStream(Constants.LOCAL_CONFIGURATION_FILE);
			if(localConfig == null)
				throw new RuntimeException("Unable to load default config file :"+Constants.LOCAL_CONFIGURATION_FILE);

		try {
			properties.loadFromXML(localConfig);
			localConfig.close();

			// overrides the local config
			if (userConfig != null) {
				properties.loadFromXML(userConfig);
				userConfig.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new SimcadException(e);
		}

	}	
	
	private PropsUtil(){}
	
	public static boolean isStrictOnMembership(){
		return Boolean.valueOf(properties.getProperty(Constants.STRICT_ON_MEMBERSHIP, "false"));
	}
	
	public static Double getClusterMembershipRatio(){
		return Double.valueOf(properties.getProperty(Constants.CLUSTER_MEMBERSHIP_RATIO, "0.5"));
	}
	
	public static Double getLocTolerance(){
		return Double.valueOf(properties.getProperty(Constants.LOC_TOLERANCE, "1.0"));
	}
	
	public static Integer getMinClusterSize(){
		return Integer.valueOf(properties.getProperty(Constants.MIN_CLUSTER_SIZE, "2"));
	}
	
	public static Integer getMinSizeOfGranularity(){
		return Integer.valueOf(properties.getProperty(Constants.MIN_SIZE_OF_GRANULARITY, "5"));
	}
	
	public static String getTokenBuilderClassName(){
		return properties.getProperty(Constants.TOKEN_BUILDER);
	}
	
	public static String getRegularHashProviderClassName(){
		return properties.getProperty(Constants.REGULAR_HASH_PROVIDER);
	}
	
	public static String getIndexBuilderClassName(){
		return properties.getProperty(Constants.INDEX_BUILDER);
	}
	
	public static String getTxlScriptUrl(){
		return properties.getProperty(Constants.TXL_SCRIPT_URL);
	}
	
	
	
	
	
	
	public static InputStream getResourceAsStream(String resource) {
		String stripped = resource.startsWith("/") ?
				resource.substring(1) : resource;

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader!=null) {
			stream = classLoader.getResourceAsStream( stripped );
			System.out.println("got by Thread.currentThread().getContextClassLoader()");
		}
		if ( stream == null ) {
			stream = Constants.class.getResourceAsStream( resource );
			System.out.println("got by Environment.class");
		}
		if ( stream == null ) {
			stream = Constants.class.getClassLoader().getResourceAsStream( stripped );
			System.out.println("got by Environment.class.getClassLoader()");
		}
		return stream;
	}
	
}
