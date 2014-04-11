package com.edubrite.api.example.plugins.connector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.edubrite.api.example.plugins.common.Pair;
import com.edubrite.api.example.plugins.common.StringUtils;

public class ApiStatisticsManager {
	private static final Logger log = Logger
	.getLogger(ApiStatisticsManager.class.getName());
	private final Map<ApiCall, ApiStat> statsMap = new ConcurrentHashMap<ApiCall, ApiStat>(50);
	private final Pair<ApiCall, String> [] errors = new Pair[50];
	private AtomicInteger errorIndex = new AtomicInteger(0);
	
	private static final ApiStatisticsManager singleton = new ApiStatisticsManager();
	
	private ApiStatisticsManager() {
	}
	
	public static ApiStatisticsManager getInstance() {
		return singleton;
	}

	public ApiStat getStats(ApiCall entry){
		return statsMap.get(entry);
	}
	
	private String parseError(String response){
		if(StringUtils.isBlankNull(response)){
			return null;
		}
		if(!response.trim().startsWith("<response ")){
			return null;
		}
		try{
			int p1 = response.indexOf("[CDATA[");
			if(p1 < 0){
				return response;
			}
			p1 = p1 + "[CDATA[".length();
			int p2 = response.lastIndexOf("]]");
			if(p2 < 0){
				return response;
			}
			return response.substring(p1, p2);
		}catch(Exception e){
			
		}
		return response;
	}
	
	public void addStats(ApiCall entry, int time, boolean error, String response,
			CommunicationError er, Exception ex){
		try{
			ApiStat oldStats = statsMap.get(entry);
			int appTime = entry.getAppTime();
			
			if((er != null && er != CommunicationError.NO_ERROR) || ex != null){
				response = (er != null ? er.name(): "");
				if(ex != null){
					response += " " + ex.getMessage();
				}
				error = true;
			}
			
			if(error || (!StringUtils.isBlankNull(response) && response.trim().startsWith("<response "))){
				int i = errorIndex.getAndIncrement();
				if(i >= errors.length){
					errorIndex.set(0);
				}
				i = errorIndex.getAndIncrement();
				if(i >= 0 && i < errors.length){
					
					errors[i] = Pair.of(entry, parseError(response));
				}
				if(!error){
					error = true;
				}
			}
			
			if(oldStats != null){
				oldStats.addHit(time, appTime, error);
			}else{
				ApiStat stats = new ApiStat(time, appTime);
				statsMap.put(entry, stats);
			}
			
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public List<Pair<ApiCall, String>> getErrors() {
		List<Pair<ApiCall, String>> col = Arrays.asList(errors);
		Collections.sort(col, new ApiErrorComp());
		return col;
	}

	public List<Pair<ApiCall, ApiStat>> getReport(){
		List<Pair<ApiCall, ApiStat>> list = new ArrayList<Pair<ApiCall, ApiStat>>();
		for(Map.Entry<ApiCall, ApiStat> entry : statsMap.entrySet()){
			list.add(Pair.of(entry.getKey(), entry.getValue()));
		}
		Collections.sort(list, new ApiStatsComp());
		return list;
	}
	
	
	private static class ApiStatsComp implements Comparator<Pair<ApiCall, ApiStat>> {
		
		public int compare(Pair<ApiCall, ApiStat> arg0,
				Pair<ApiCall, ApiStat> arg1) {
			if(arg0 != null && arg1 != null){
				if(arg0.getSecond().getAvgTime() < arg1.getSecond().getAvgTime()){
					return 1;
				}else if(arg0.getSecond().getAvgTime() == arg1.getSecond().getAvgTime()){
					return 0;
				}else if(arg0.getSecond().getAvgTime() > arg1.getSecond().getAvgTime()){
					return -1;
				}
			}
			return 0;
		}
		
	}
	
	private static class ApiErrorComp implements Comparator<Pair<ApiCall, String>>{

		public int compare(Pair<ApiCall, String> o1, Pair<ApiCall, String> o2) {
			if(o1 != null && o2 != null && o1.getFirst() != null && o2.getFirst() != null){
				return o1.getFirst().getTimestamp().compareTo(o2.getFirst().getTimestamp());
			}
			return 0;
		}
		
	}
}
