package com.example.db;

public class GoodsColumn {
//	列名	类型	描述	长度	是否可空	默认值	备注	
//	goods_id	int	物品编号	4	no	0	主键	
//	goods_name	varchar	物品名称	10	no	null	物品中文名	
//	goods_store	int	物品商店	2	no	0	0家1边2野3家边4家野5边业6全部	
//	goods_price	int	物品价格	5	no	0	如是合成物品，则是所有所需物品价格之和	
//	can_compose	boolean	能否合成	1	no	0	0否1是	
//	goods_compose_price	int	合成价格	5	no	0	合成时，除物品外附近的金钱	
//	goods_image	varchar	物品图片	500	no	null	url	
//	goods_disdription	varchar	物品描述	500	no	null	物品详细介绍	
//	goods_add_hp	varchar	增加血量	4	no	null	增量，因有按百分比增加，故以varchar类型，如纯数字，则直接加，如带百分百，则计算后加	
//	goods_add_mp	varchar	增加魔量	4	no	null		
//	goods_add_strong	varchar	增加力量	4	no	null		
//	goods_add_sharp	varchar	增加敏捷	4	no	null		
//	goods_add_intelligence	varchar	增加智力	4	no	null		
//	goods_add_armor	varchar	增加防御	4	no	null		
//	goods_add_attack	varchar	增加攻击	4	no	null		
//	goods_add_speed	varchar	增加攻速	4	no	null		
//	goods_compose_from	varchar	合成所需	500	no	null	合成所需物品编号，多个编号以","隔开	
//	goods_type	int	物品类型	1	no	0	0消耗品1装备	
//	label_id	varchar	标签编号	500	no	null	外键    标签表	
	public  static final String goods_id="_id";
	public  static final String goods_name="goods_name";
	public  static final String goods_chineseName="goods_chineseName";
	public  static final String goods_store="goods_store";
	public  static final String goods_price="goods_price";
	public  static final String can_compose="can_compose";
	public  static final String goods_compose_price="goods_compose_price";
	public  static final String goods_image="goods_image";
	public  static final String goods_discription="goods_discription";
	public  static final String goods_add_hp="goods_add_hp";
	public  static final String goods_add_mp="goods_add_mp";
	public  static final String goods_add_strong="goods_add_strong";
	public  static final String goods_add_sharp="goods_add_sharp";
	public  static final String goods_add_intelligence="goods_add_intelligence";
	public  static final String goods_add_armor="goods_add_armor";
	public  static final String goods_add_attack="goods_add_attack";
	public  static final String goods_add_speed="goods_add_speed";
	public  static final String goods_add_skill="goods_add_skill";
	public  static final String goods_compose_from="goods_compose_from";
	public  static final String goods_compose_to="goods_compose_to";
	public  static final String goods_type="goods_type";

	public  static final String label_id="label_id";

	
	
}
