package com.edubrite.api.plugins.common;

import java.io.InputStream;
import java.util.Properties;

import com.edubrite.api.plugins.vo.User;

public class PluginConfigManager {
	private static ThreadLocal<User> applicationUser = new ThreadLocal<User>();
	
	private static PluginConfigManager singleton = new PluginConfigManager();
	private PluginConfig config;
	
	public static PluginConfigManager getInstance(){
		return singleton;
	}
	
	private PluginConfigManager(){
		try{
			InputStream is = PluginConfig.class.getClassLoader().getResourceAsStream("edubrite.properties");
			Properties props = new Properties();
			props.load(is);
			is.close();
			config = new PluginConfig(props);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static PluginConfig getConfig(){
		return singleton.config;
	}
	
	public User getApplicationUser(){
		return (User) applicationUser.get();
	}
	
	public void setApplicationUser (User user){
		applicationUser.set(user);
	}
	
}
