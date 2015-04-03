package com.anemortalkid.csl.pagescrub;

/**
 * An {@link IScrubber} is a page scrubber which scrubs a particular url for
 * specific data, that data is returned once the scrub method is called
 * 
 * @author JMonterrubio
 *
 * @param <S>
 *            the results from scrubbing the desired page
 */
public interface IScrubber<S> {

	/**
	 * Scrubs the desired page and returns the specified result
	 * 
	 * @return the result of running the scrubber against a desired page
	 */
	S scrub();
}
