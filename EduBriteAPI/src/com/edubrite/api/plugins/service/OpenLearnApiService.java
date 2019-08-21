package com.edubrite.api.plugins.service;

import com.edubrite.api.plugins.vo.Metadata;
import com.edubrite.api.plugins.vo.PagedList;

public interface OpenLearnApiService {
	
	/**
	 * Fetches list of Open earning lessons
	 * 
	 * @param searchName To filter results for the given searchName. Supported
	 *                   prefix:<br/>
	 *                   <ul>
	 *                   <li><b>No prefix</b> (for searching by name of lesson)</li>
	 *                   <li><b>cat:</b> (for searching by category tagged on
	 *                   lesson)</li>
	 *                   <li><b>author:</b> (for searching by author name of the
	 *                   lesson)</li>
	 *                   </ul>
	 * @param video      to filter results to show only video type lessons
	 * @param file       to filter results to show only file type lessons
	 * @param youtube    to filter results to show only YouTube type lessons
	 * @param vimeo      to filter results to show only Vimeo type lessons
	 * @param metadata   metadata filters
	 * @param pagination Paging related parameters
	 *                   <ul>
	 *                   <li><b>doc.document.name</b>: sort by name</li>
	 *                   <li><b>doc.traceData.createdDt</b>: sort by created date</li>
	 *                   <li><b>item.sequence,doc.sequence</b>: sort by created date</li>
	 *                   <li><b>random</b>: sort randomly</li>
	 *                   </ul>
	 * @return list of open learning lessons
	 */
	public String getLessonsSummary(String searchName, Boolean video, Boolean file, Boolean youtube, Boolean vimeo, Metadata metadata, PagedList pagination);
}
