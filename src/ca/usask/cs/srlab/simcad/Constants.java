package ca.usask.cs.srlab.simcad;

public class Constants {

	public static final String VERSION = "1.0";

	public static final String EXTERNAL_CONFIGURATION_FILE 	= "simcad.cfg.xml";
	public static final String LOCAL_CONFIGURATION_FILE 	= "default.cfg.xml";

	public static final String STRICT_ON_MEMBERSHIP 	= "simcad.settings.advance.strictOnMembership";
	public static final String CLUSTER_MEMBERSHIP_RATIO = "simcad.settings.advance.clusterMembershipRatio";
	public static final String LOC_TOLERANCE 			= "simcad.settings.advance.locTolerance";
	public static final String TYPE3CLONE_SIMTHRESHOLD 	= "simcad.settings.advance.type3clone.simthreshold";
	public static final int TYPE3CLONE_SIMTHRESHOLD_MAX_VAL = 13;
	public static final int TYPE3CLONE_SIMTHRESHOLD_MIM_VAL = 1;

	public static final String MIN_CLUSTER_SIZE 		= "simcad.settings.general.minClusterSize";
	public static final String MIN_SIZE_OF_GRANULARITY 	= "simcad.settings.general.minSizeOfGranularity";

	public static final String REGULAR_HASH_PROVIDER 	= "simcad.settings.general.preprocess.regularhash_class";
	public static final String TOKEN_BUILDER	 		= "simcad.settings.general.preprocess.tokenbuilder_class";
	public static final String INDEX_HOLDER 			= "simcad.settings.general.index.indexholder_class";
	public static final String TXL_SCRIPT_URL 			= "simcad.settings.general.preprocess.txlscript.url";

	public static final String CLONE_GRANULARITY_FUNTIONS 	= "functions";
	public static final String CLONE_GRANULARITY_BLOCKS 	= "blocks";

	public static final String CLONE_SET_TYPE_GROUP = "group";
	public static final String CLONE_SET_TYPE_PAIR 	= "pair";

	public static final String CLONE_TYPE_1 = "Type-1";
	public static final String CLONE_TYPE_2 = "Type-2";
	public static final String CLONE_TYPE_3 = "Type-3";
	//public static final String CLONE_TYPE_4 = "Type-4";
	
	public static final String[] CLONE_TYPE_12 = new String[] {CLONE_TYPE_1, CLONE_TYPE_2};
	public static final String[] CLONE_TYPE_21 = CLONE_TYPE_12;
	public static final String[] CLONE_TYPE_23 = new String[] {CLONE_TYPE_2, CLONE_TYPE_3};
	public static final String[] CLONE_TYPE_32 = CLONE_TYPE_23;
	public static final String[] CLONE_TYPE_13 = new String[] {CLONE_TYPE_1, CLONE_TYPE_3};
	public static final String[] CLONE_TYPE_31 = CLONE_TYPE_13;
	public static final String[] CLONE_TYPE_NEARMISS = CLONE_TYPE_23;
	public static final String[] CLONE_TYPE_123 = new String[] {CLONE_TYPE_1, CLONE_TYPE_2, CLONE_TYPE_3};
	public static final String[] CLONE_TYPE_ALL = CLONE_TYPE_123;
	

}
