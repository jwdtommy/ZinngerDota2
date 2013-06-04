package com.example.db;

public class SkillColumn {
//	列名	类型	描述	长度	是否可空	默认值	备注	
//	skill_id	int	技能编号	4	no	0	主键	
//	hero_id	int	英雄编号	4	no	0	外键	
//	skill_name	varchar	技能名	10	no	null	技能中文名	
//	skill_discription	varchar	技能描述	200	no	null	技能简介	
//	skill_detail	varchar	技能详情	500	no	null	技能每级的区别	
//	skill_image	varchar	技能图片	500	no	null	url	
//	skill_gif	varchar	技能gif图片	500	no	0	url	
//	is_ultimate	boolean	是否终极技能	1	no	0	0否，1是	
//	skill_hotkey	varchar	技能热键	1	no	null	初始快捷键，改建不算	
//	skill_order_id	int	技能顺序	1	no	0	0-3顺序	

	public  static final String skill_id="_id";
	public  static final String hero_id="hero_id";
	public  static final String skill_name="skill_name";
	public  static final String skill_name_en="skill_name_en";
	public  static final String skill_discription="skill_discription";
	public  static final String skill_detail="skill_detail";
	public  static final String skill_image="skill_image";
	public  static final String skill_gif="skill_gif";
	public  static final String is_ultimate="is_ultimate";
	public  static final String skill_hotkey="skill_hotkey";
	public  static final String skill_order_id="skill_order_id";
	
}
