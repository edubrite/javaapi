package com.edubrite.api.plugins.connector;

public class ApiStat {
	private int minTime;
	private int maxTime;
	private int totalTime;
	private int hitsCount;
	private int errorsCount;
	private int totalAppTime;
	
	ApiStat(int time, int appTime) {
		super();
		this.minTime = time;
		this.maxTime = time;
		this.totalTime = time;
		this.totalAppTime = appTime;
		this.hitsCount = 1;
	}
	public void addAppTime(int time){
		this.totalAppTime += time; 
	}
	public int getAvgAppTime() {
		if(hitsCount == 0)return 0;
		return totalAppTime/hitsCount;
	}
	public int getAvgTime() {
		if(hitsCount == 0)return 0;
		return totalTime/hitsCount;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public int getTotalAppTime() {
		return totalAppTime;
	}
	public int getHitsCount() {
		return hitsCount;
	}
	public int getMaxTime() {
		return maxTime;
	}
	public int getMinTime() {
		return minTime;
	}
	public int getErrorsCount(){
		return errorsCount;
	}
	public synchronized void reset(){
		minTime = 0;
		maxTime = 0;
		totalTime = 0;
		totalAppTime = 0;
		hitsCount = 0;
		errorsCount = 0;
	}
	public synchronized void addHit(int time, int appTime, boolean error){
		hitsCount++;
		this.totalTime+=time;
		this.totalAppTime += appTime;
		if(minTime > time){
			minTime = time;
		}
		if(maxTime < time){
			maxTime = time;
		}
		if(error){
			errorsCount++;
		}
	}
}
