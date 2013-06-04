package com.example.model;

public class EquipmentBean {
//	列名	类型	描述	长度	是否可空	默认值	备注
//	equipment_id	int	出装编号	4	no	0	主键
//	hero_id	int	英雄编号	4	no	0	外键   英雄表
//	equipment_type	int	出装类型	2	no	0	0出门1中级2后期3逆风
//	goods_id	varchar	物品编号	100	no	null	外键   物品表，6个物品编码以","隔开

	private int equipment_id;
	private int hero_id;
	private int equipment_type;
	private int goods_id;
	public int getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(int equipment_id) {
		this.equipment_id = equipment_id;
	}
	public int getHero_id() {
		return hero_id;
	}
	public void setHero_id(int hero_id) {
		this.hero_id = hero_id;
	}
	public int getEquipment_type() {
		return equipment_type;
	}
	public void setEquipment_type(int equipment_type) {
		this.equipment_type = equipment_type;
	}
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	
}
