package ca.usask.cs.srlab.simcad.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ca.usask.cs.srlab.simcad.model.ICloneSet;

public final class ProcessorDisptacher {

	private static final ProcessorDisptacher INSTANCE = new ProcessorDisptacher();
	private List<IProcessor> processorList;

	private ProcessorDisptacher() {
		processorList = new ArrayList<IProcessor>();
	}

	public static ProcessorDisptacher getInstance() {
		return INSTANCE;
	}

	public ProcessorDisptacher addProcessor(IProcessor rocessor) {
		processorList.add(rocessor);
		return this;
	}

	public ProcessorDisptacher removeProcessor(IProcessor rocessor) {
		processorList.remove(rocessor);
		return this;
	}

	public ProcessorDisptacher cleanUp() {
		processorList.clear();
		return this;
	}

	public Collection<ICloneSet> applyOn(Collection<ICloneSet> inputCloneSets) {
		Iterator<IProcessor> processorIterator = processorList.listIterator();
		for (; processorIterator.hasNext();) {
			IProcessor processor = processorIterator.next();
			try {
				Collection<ICloneSet> outputCloneSets = new ArrayList<ICloneSet>();
				processor.process(inputCloneSets, outputCloneSets);

				if (processorIterator.hasNext())
					inputCloneSets = Collections
							.unmodifiableCollection(outputCloneSets);
				else
					return outputCloneSets;

			} catch (Exception e) {
				throw new IllegalStateException(
						"A processing error occured in processor: "
								+ processor.getNmae(), e);
			}
		}
		return inputCloneSets;
	}

}
