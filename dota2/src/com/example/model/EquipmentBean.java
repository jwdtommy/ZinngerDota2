package com.example.model;

public class EquipmentBean {
//	����	����	����	����	�Ƿ�ɿ�	Ĭ��ֵ	��ע
//	equipment_id	int	��װ���	4	no	0	����
//	hero_id	int	Ӣ�۱��	4	no	0	���   Ӣ�۱�
//	equipment_type	int	��װ����	2	no	0	0����1�м�2����3���
//	goods_id	varchar	��Ʒ���	100	no	null	���   ��Ʒ��6����Ʒ������","����

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
