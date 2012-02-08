package ca.usask.cs.srlab.simcad.hash;

import ca.usask.cs.srlab.simcad.token.ITokenBuilder;
import ca.usask.cs.srlab.simcad.token.TokenBuildStrategy;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;


public class SimhashGenerator {
	private ITokenBuilder tokenBuilder;
	private IRegularHashGenerator regularHashGenerator;

	@SuppressWarnings("unused")
	private SimhashGenerator(){}
	
	public SimhashGenerator(ITokenBuilder tokenBuilder, IRegularHashGenerator regularHashGenerator){
		this.tokenBuilder = tokenBuilder;
		this.regularHashGenerator = regularHashGenerator;
	}
	
	public long[] generateSimhash(String rawData){
		int v1[]= new int [64];
		int v2[]= new int [64];
		
		int seconderyHashMinFreq = 1;
		
		Multiset<String> tokenMap = HashMultiset.create(tokenBuilder.generateToken(rawData));
		
		/*
		System.out.println("########################");
		for(Entry<String, Short> entry: tokenMap.entrySet()){
			System.out.println(entry.getValue()+" : " + hash+" : " + entry.getKey());
		}
		System.out.println("########################\n");
		*/
		
		for(Multiset.Entry<String> token : tokenMap.entrySet()) {
		
			int originalTokenFrequency = token.getCount();
			int modifiedTokenFrequency = originalTokenFrequency;
			long tokenHash = regularHashGenerator.generate64BitHashFor(token.getElement());
				//HashFactory.buildLookup3ycs64Hash(token.getElement());
				 				
				for (int c=0; c<64; c++){
					if (tokenBuilder.getBuildStrategy()
							.isTokenFrequencyNormalizationOn()) {
						int normalizationThreshold = tokenBuilder.getBuildStrategy()
								.isTokenFrequencyNormalizationThreshold();
						int valueOverThreshold = tokenBuilder.getBuildStrategy()
								.isTokenFrequencyNormalizationValueOverThreshold();
						modifiedTokenFrequency = originalTokenFrequency > normalizationThreshold ? valueOverThreshold
								: originalTokenFrequency;
					}
					v1[c] += (tokenHash & (1l << c)) == 0 ? -modifiedTokenFrequency : modifiedTokenFrequency;
				}
			
			//secondary hash for better precision
			if(originalTokenFrequency > seconderyHashMinFreq) {
				tokenHash =  regularHashGenerator.generate64BitHashFor(token.getElement(), 32767l);
				for (int c=0; c<64; c++){		
					v2[c] += (tokenHash & (1l << c)) == 0 ? -modifiedTokenFrequency : modifiedTokenFrequency;
				}
			}
	    }

		long simhash[] = { 0, 0 };
		for (int c = 0; c < 64; c++)
			simhash[0] |= (v1[c] > 0 ? 1l : 0l) << c;

		for (int c = 0; c < 64; c++)
			simhash[1] |= (v2[c] > 0 ? 1l : 0l) << c;

		return simhash;
	}

	public SimhashGenerator applyTokenBuildStrategy(TokenBuildStrategy strategy) {
		//TODO: verify if it a valid strategy
		this.tokenBuilder.setBuildStrategy(strategy);
		return this;
	}
	
}