package com.fable.industry.bussiness.model.common;


import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import org.apache.ibatis.type.Alias;

@Alias("SysConfig")
public class SysConfig {

	@NumberGenerator(name = "platformSysConfigPK")
	private int id;
	private int type;
	private String paraKey;
	private String paraValue;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getParaKey() {
		return paraKey;
	}

	public void setParaKey(String paraKey) {
		this.paraKey = paraKey;
	}

	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}