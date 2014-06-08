package com.study.util;


import java.util.Properties;

public class PropertiesConfiguration
{
	
	private Properties config = new Properties();
	
	public String get(String key)
	{
		return this.getConfig(key);
	}
	
	public String get(String key, String def)
	{
		return this.getConfig(key, def);
	}
	
	public String getConfig(String key)
	{
		return this.config.getProperty(key);
	}
	
	public String getConfig(String key, String def)
	{
		String s = this.getConfig(key);
		return s == null ? def : s;
	}
	
	public int getIntConfig(String key)
	{
		String exp = this.getConfig(key, "0");
		return Integer.parseInt(exp);
	}
	
	public int getIntConfig(String key, int def)
	{
		String exp = this.getConfig(key, "" + def);
		return Integer.parseInt(exp);
	}
	
	public long getLongConfig(String key)
	{
		String exp = this.getConfig(key, "0");
		return Long.parseLong(exp);
	}
	
	public long getLongConfig(String key, long def)
	{
		String exp = this.getConfig(key, "" + def);
		return Long.parseLong(exp);
	}
	
	public double getDoubleConfig(String key)
	{
		String exp = this.getConfig(key, "0.0");
		return Double.parseDouble(exp);
	}
	
	public double getDoubleConfig(String key, double def)
	{
		String exp = this.getConfig(key, "" + def);
		return Double.parseDouble(exp);
	}

	public Properties getConfig()
	{
		return config;
	}

	public void setConfig(Properties config)
	{
		this.config = config;
	}
}
