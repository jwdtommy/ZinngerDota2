package com.example.model;

import java.io.Serializable;

public class SkillBean implements Serializable
{

	// ���� ���� ���� ���� �Ƿ�ɿ� Ĭ��ֵ ��ע
	// skill_id int ���ܱ�� 4 no 0 ����
	// hero_id int Ӣ�۱�� 4 no 0 ���
	// skill_name varchar ������ 10 no null ����������
	// skill_discription varchar �������� 200 no null ���ܼ��
	// skill_detial varchar �������� 500 no null ����ÿ��������
	// skill_image varchar ����ͼƬ 500 no null url
	// skill_gif varchar ����gifͼƬ 500 no 0 url
	// is_ultimate boolean �Ƿ��ռ����� 1 no 0 0��1��
	// skill_hotkey varchar �����ȼ� 1 no null ��ʼ��ݼ����Ľ�����
	// skill_order_id int ����˳�� 1 no 0 0-3˳��



	/**
	 * 
	 */
	private static final long serialVersionUID = 5237701995407406681L;
	private int skill_id;// ���ܱ��
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
