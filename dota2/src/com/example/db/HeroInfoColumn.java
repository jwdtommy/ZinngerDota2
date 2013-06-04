package com.example.db;

public class HeroInfoColumn {
//	列名	类型	描述	长度	是否可空	默认值	备注	
//	hero_id	int	英雄编号	4	no	null	主键	
//	hero_name	varchar	英雄名	10	no	null	英雄中文名	
//	hero_name_en	varchar	英雄英文名	20	no	null	用于拼接网址	
//	hero_sex	int	英雄性别	1	no	0	0男，1女，-1未知	
//	hero_def_hp	int	初始血量	5	no	0	初始内容	
//	hero_def_mp	int	初始魔	5	no	0		
//	hero_def_strong	int	初始力量	3	no	0		
//	hero_def_sharp	int	初始敏捷	3	no	0		
//	hero_def_intelligence	int	初始智力	3	no	0		
//	hero_def_armor	int	初始护甲	3	no	0		
//	hero_def_attack	varchar	初始攻击	10	no	0		
//	hero_def_speed	int	初始速度	3	no	0		
//	hero_att_range	int	攻击范围	4	no	0	高级内容	
//	hero_vision	varchar	英雄视野	9	no	0		
//	hero_missile	int	弹道速度	5	no	0		
//	hero_attack_delay	varchar	攻击前后摇	9	no	0		
//	hero_excute_delay	varchar	施法前后摇	9	no	0		
//	hero_up_strong	float	每级增长力	2	no	0	每级增量	
//	hero_up_sharp	float	每级增长敏	2	no	0		
//	hero_up_intellience	float	每级增长智	2	no	0		
//	hero_image	varchar	英雄头像	500	no	null	url	
//	hero_description	varchar	英雄描述	500	no	null		
//	hero_group	int	英雄阵营	1	no	0	0天辉 1夜魇	
//	hero_bar	int	英雄酒馆	2	no	0	0-8一共9个	
//	hero_type	int	英雄类别	1	no	0	0力1敏2智	
//	label_id	varchar	标签编号	500	no	null	外键  标签表，多个标签以","隔开	
public static final String hero_id="_id";
public static final String hero_name="hero_name";
public static final String hero_name_en="hero_name_en";
public static final String hero_sex="hero_sex";
public static final String hero_def_hp="hero_def_hp";
public static final String hero_def_mp="hero_def_mp";
public static final String hero_def_strong="hero_def_strong";
public static final String hero_def_sharp="hero_def_sharp";
public static final String hero_def_intelligence="hero_def_intelligence";
public static final String hero_def_armor="hero_def_armor";
public static final String hero_def_attack="hero_def_attack";
public static final String hero_def_speed="hero_def_speed";
public static final String hero_att_range="hero_att_range";
public static final String hero_vision="hero_vision";
public static final String hero_missile="hero_missile";
public static final String hero_attack_delay="hero_attack_delay";
public static final String hero_excute_delay="hero_excute_delay";
public static final String hero_up_strong="hero_up_strong";
public static final String hero_up_sharp="hero_up_sharp";
public static final String hero_up_intellience="hero_up_intellience";
public static final String hero_image="hero_image";
public static final String hero_description="hero_description";
public static final String hero_group="hero_group";
public static final String hero_bar="hero_bar";
public static final String hero_type="hero_type";
public static final String label_id="label_id";
public static final String recommend_goods="recommend_goods";
	
}

