package com.example.model;

import java.io.Serializable;

public class SkillBean implements Serializable
{

	// 列名 类型 描述 长度 是否可空 默认值 备注
	// skill_id int 技能编号 4 no 0 主键
	// hero_id int 英雄编号 4 no 0 外键
	// skill_name varchar 技能名 10 no null 技能中文名
	// skill_discription varchar 技能描述 200 no null 技能简介
	// skill_detial varchar 技能详情 500 no null 技能每级的区别
	// skill_image varchar 技能图片 500 no null url
	// skill_gif varchar 技能gif图片 500 no 0 url
	// is_ultimate boolean 是否终极技能 1 no 0 0否，1是
	// skill_hotkey varchar 技能热键 1 no null 初始快捷键，改建不算
	// skill_order_id int 技能顺序 1 no 0 0-3顺序



	/**
	 * 
	 */
	private static final long serialVersionUID = 5237701995407406681L;
	private int skill_id;// 技能编号
	private int hero_id;
	private String skill_name;
	private String skill_name_en;
	private String skill_discription;
	private String skill_detial;
	private String skill_image;
	private String skill_gif;
	private String is_ultimate;
	private String skill_hotkey;
	private int skill_order_id;
	public String getSkill_name_en()
	{
		return skill_name_en;
	}

	public void setSkill_name_en(String skill_name_en)
	{
		this.skill_name_en = skill_name_en;
	}
	public int getSkill_id()
	{
		return skill_id;
	}

	public void setSkill_id(int skill_id)
	{
		this.skill_id = skill_id;
	}

	public int getHero_id()
	{
		return hero_id;
	}

	public void setHero_id(int hero_id)
	{
		this.hero_id = hero_id;
	}

	public String getSkill_name()
	{
		return skill_name;
	}

	public void setSkill_name(String skill_name)
	{
		this.skill_name = skill_name;
	}

	public String getSkill_discription()
	{
		return skill_discription;
	}

	public void setSkill_discription(String skill_discription)
	{
		this.skill_discription = skill_discription;
	}

	public String getSkill_detial()
	{
		return skill_detial;
	}

	public void setSkill_detial(String skill_detial)
	{
		this.skill_detial = skill_detial;
	}

	public String getSkill_image()
	{
		return skill_image;
	}

	public void setSkill_image(String skill_image)
	{
		this.skill_image = skill_image;
	}

	public String getSkill_gif()
	{
		return skill_gif;
	}

	public void setSkill_gif(String skill_gif)
	{
		this.skill_gif = skill_gif;
	}

	public String getIs_ultimate()
	{
		return is_ultimate;
	}

	public void setIs_ultimate(String is_ultimate)
	{
		this.is_ultimate = is_ultimate;
	}

	public String getSkill_hotkey()
	{
		return skill_hotkey;
	}

	public void setSkill_hotkey(String skill_hotkey)
	{
		this.skill_hotkey = skill_hotkey;
	}

	public int getSkill_order_id()
	{
		return skill_order_id;
	}

	public void setSkill_order_id(int skill_order_id)
	{
		this.skill_order_id = skill_order_id;
	}

}
