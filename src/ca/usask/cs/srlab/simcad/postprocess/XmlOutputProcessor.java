package ca.usask.cs.srlab.simcad.postprocess;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ca.usask.cs.srlab.simcad.SimcadException;
import ca.usask.cs.srlab.simcad.model.ICloneFragment;
import ca.usask.cs.srlab.simcad.model.ICloneSet;
import ca.usask.cs.srlab.simcad.processor.IProcessor;
import ca.usask.cs.srlab.simcad.util.XMLUtil;

public class XmlOutputProcessor implements IProcessor {

	private String outputFolderName;
	private DetectionSettings detectionSettings;
	
	@SuppressWarnings("unused")
	private XmlOutputProcessor(){}
	
	public XmlOutputProcessor(DetectionSettings detectionSettings, String outputFolderName) {
		super();
		this.detectionSettings = detectionSettings;
		this.outputFolderName = outputFolderName;
	}

	@Override
	public boolean process(Collection<ICloneSet> inputCloneSets, Collection<ICloneSet> outputCloneSets) {
		String simThreshold = detectionSettings.getSimThreshold().toString();
		String logFileName = outputFolderName + System.getProperty("file.separator") + "simcad_"+detectionSettings.getCloneGranularity()+"-clones-"+simThreshold+".log";
		PrintWriter logPrinter;
		
		try {
			logPrinter = new PrintWriter(new FileWriter(logFileName));
		} catch (IOException e) {
			e.printStackTrace();
			throw new SimcadException("Can not create output file", e);
		}
		
		System.out.println("\nGenerating output file...");
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = null;
		try {
			docBuilder = dbfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	    Document doc = docBuilder.newDocument();
	    
	    Element root = doc.createElement("clones");
	    doc.appendChild(root);

		int nGroup = 0;
		int nFragment = 0;
	    
		for(ICloneSet cs: inputCloneSets){
//			if(cs.isSubsumed()) {
//				//System.out.println("ignored subsumed clone group...");
//				continue;
//			}
				
			Node child = doc.createElement("clone_group");
			((Element)child).setAttribute("groupid", String.valueOf(nGroup++));
			((Element)child).setAttribute("nfragments", String.valueOf(cs.size()));
			
			nFragment += cs.size();
			
			ICloneFragment lastdisplayed = cs.getMember(0) ;
			for(ICloneFragment cloneFragment : cs.getCloneFragments()){

				Node child2 = doc.createElement("clone_fragment");
				((Element)child2).setAttribute("file", cloneFragment.getFileName());
				((Element)child2).setAttribute("startline", cloneFragment.getFromLine().toString());
				((Element)child2).setAttribute("endline", cloneFragment.getToLine().toString());
				((Element)child2).setAttribute("pcid", cloneFragment.getId().toString());
				((Element)child2).setAttribute("hamdist", String.valueOf(Long.bitCount(cloneFragment.getSimhash1() ^ lastdisplayed.getSimhash1())));
				
				child2.appendChild(doc.createCDATASection(cloneFragment.getOriginalCodeBlock()));
				child.appendChild(child2);
				lastdisplayed = cloneFragment;
			}
	        root.appendChild(child);
		}
		
	    root.setAttribute("ngroups", Integer.toString(nGroup));
	    root.setAttribute("nfragments", Integer.toString(nFragment));
	    root.setAttribute("hamthreshold",  simThreshold);
	    
	    System.out.println("Total clone class : "+ nGroup );
	    System.out.println("Total clone frag : "+ nFragment );
	    
	    logPrinter.println("Total clone class : "+ nGroup );
	    logPrinter.println("Total clone frag : "+ nFragment );	
	    
	    System.out.println("Done!\n");
	    logPrinter.close();
	    
	    String outputFileName = outputFolderName + System.getProperty("file.separator") + "simcad_"+detectionSettings.getCloneGranularity()+"-clones-"+simThreshold+".xml";
	    
//	    File outputFile = new File(outputFileName);
		try {
//			if (outputFile.exists()) {
//				outputFile.delete();
//			} else {
//				outputFile.createNewFile();
//			}
			XMLUtil.writeXmlFile(doc, outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SimcadException("Can not create output file", e);
		}
		
		outputCloneSets.addAll(inputCloneSets);
		return true;
	}

	@Override
	public String getNmae() {
		return this.getClass().getSimpleName();
	}

}
