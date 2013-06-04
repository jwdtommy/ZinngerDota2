package com.example.model;

import java.io.Serializable;

public class ItemBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1379132664180670L;
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public int getGoods_store() {
		return goods_store;
	}
	public void setGoods_store(int goods_store) {
		this.goods_store = goods_store;
	}
	public int getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(int goods_price) {
		this.goods_price = goods_price;
	}
	public boolean isCan_compose() {
		return can_compose;
	}
	public void setCan_compose(boolean can_compose) {
		this.can_compose = can_compose;
	}
	public int getGoods_compose_price() {
		return goods_compose_price;
	}
	public void setGoods_compose_price(int goods_compose_price) {
		this.goods_compose_price = goods_compose_price;
	}
	public String getGoods_image() {
		return goods_image;
	}
	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}
	public String getGoods_disdription() {
		return goods_disdription;
	}
	public void setGoods_disdription(String goods_disdription) {
		this.goods_disdription = goods_disdription;
	}
	public String getGoods_add_hp() {
		return goods_add_hp;
	}
	public void setGoods_add_hp(String goods_add_hp) {
		this.goods_add_hp = goods_add_hp;
	}
	public String getGoods_add_mp() {
		return goods_add_mp;
	}
	public void setGoods_add_mp(String goods_add_mp) {
		this.goods_add_mp = goods_add_mp;
	}
	public String getGoods_add_strong() {
		return goods_add_strong;
	}
	public void setGoods_add_strong(String goods_add_strong) {
		this.goods_add_strong = goods_add_strong;
	}
	public String getGoods_add_sharp() {
		return goods_add_sharp;
	}
	public void setGoods_add_sharp(String goods_add_sharp) {
		this.goods_add_sharp = goods_add_sharp;
	}
	public String getGoods_add_intelligence() {
		return goods_add_intelligence;
	}
	public void setGoods_add_intelligence(String goods_add_intelligence) {
		this.goods_add_intelligence = goods_add_intelligence;
	}
	public String getGoods_add_armor() {
		return goods_add_armor;
	}
	public void setGoods_add_armor(String goods_add_armor) {
		this.goods_add_armor = goods_add_armor;
	}
	public String getGoods_add_attack() {
		return goods_add_attack;
	}
	public void setGoods_add_attack(String goods_add_attack) {
		this.goods_add_attack = goods_add_attack;
	}
	public String getGoods_add_speed() {
		return goods_add_speed;
	}
	public void setGoods_add_speed(String goods_add_speed) {
		this.goods_add_speed = goods_add_speed;
	}
	public String getGoods_compose_from() {
		return goods_compose_from;
	}
	public void setGoods_compose_from(String goods_compose_from) {
		this.goods_compose_from = goods_compose_from;
	}
	public int getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(int goods_type) {
		this.goods_type = goods_type;
	}
	public String getLabel_id() {
		return label_id;
	}
	public void setLabel_id(String label_id) {
		this.label_id = label_id;
	}
	
	public String getGoods_add_skill() {
		return goods_add_skill;
	}
	public void setGoods_add_skill(String goods_add_skill) {
		this.goods_add_skill = goods_add_skill;
	}

	
	
	public String getGoods_compose_to() {
		return goods_compose_to;
	}
	public void setGoods_compose_to(String goods_compose_to) {
		this.goods_compose_to = goods_compose_to;
	}



	public String getGoods_chineseName() {
		return goods_chineseName;
	}
	public void setGoods_chineseName(String goods_chineseName) {
		this.goods_chineseName = goods_chineseName;
	}



	private int goods_id;
	private String goods_name;
	private String goods_chineseName;
	private int goods_store;
	private int goods_price;
	private boolean can_compose;
	private int goods_compose_price;
	private String goods_image;
	private String goods_disdription;
	private String goods_add_hp;
	private String goods_add_mp;
	private String goods_add_strong;
	private String goods_add_sharp;
	private String goods_add_intelligence;
	private String goods_add_armor;
	private String goods_add_attack;
	private String goods_add_speed;
	private String goods_add_skill;
	private String goods_compose_from;
	private String goods_compose_to;
	private int goods_type;
	private String label_id;

	
}
