package com.example.model;

import java.io.Serializable;

public class HeroBean implements Serializable{
	
	public String getRecommend_goods()
	{
		return recommend_goods;
	}
	public void setRecommend_goods(String recommend_goods)
	{
		this.recommend_goods = recommend_goods;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -7628720559363669057L;
	private int hero_id;
	public int getHero_id() {
		return hero_id;
	}
	public void setHero_id(int hero_id) {
		this.hero_id = hero_id;
	}
	public String getHero_name() {
		return hero_name;
	}
	public void setHero_name(String hero_name) {
		this.hero_name = hero_name;
	}
	public String getHero_name_en() {
		return hero_name_en;
	}
	public void setHero_name_en(String hero_name_en) {
		this.hero_name_en = hero_name_en;
	}
	public int getHero_sex() {
		return hero_sex;
	}
	public void setHero_sex(int hero_sex) {
		this.hero_sex = hero_sex;
	}
	public int getHero_def_hp() {
		return hero_def_hp;
	}
	public void setHero_def_hp(int hero_def_hp) {
		this.hero_def_hp = hero_def_hp;
	}
	public int getHero_def_mp() {
		return hero_def_mp;
	}
	public void setHero_def_mp(int hero_def_mp) {
		this.hero_def_mp = hero_def_mp;
	}
	public int getHero_def_strong() {
		return hero_def_strong;
	}
	public void setHero_def_strong(int hero_def_strong) {
		this.hero_def_strong = hero_def_strong;
	}
	public int getHero_def_sharp() {
		return hero_def_sharp;
	}
	public void setHero_def_sharp(int hero_def_sharp) {
		this.hero_def_sharp = hero_def_sharp;
	}
	public int getHero_def_intelligence() {
		return hero_def_intelligence;
	}
	public void setHero_def_intelligence(int hero_def_intelligence) {
		this.hero_def_intelligence = hero_def_intelligence;
	}
	public float getHero_def_armor() {
		return hero_def_armor;
	}
	public void setHero_def_armor(float hero_def_armor) {
		this.hero_def_armor = hero_def_armor;
	}
	public String getHero_def_attack() {
		return hero_def_attack;
	}
	public void setHero_def_attack(String hero_def_attack) {
		this.hero_def_attack = hero_def_attack;
	}
	public int getHero_def_speed() {
		return hero_def_speed;
	}
	public void setHero_def_speed(int hero_def_speed) {
		this.hero_def_speed = hero_def_speed;
	}
	public int getHero_att_range() {
		return hero_att_range;
	}
	public void setHero_att_range(int hero_att_range) {
		this.hero_att_range = hero_att_range;
	}
	public String getHero_vision() {
		return hero_vision;
	}
	public void setHero_vision(String hero_vision) {
		this.hero_vision = hero_vision;
	}
	public int getHero_missile() {
		return hero_missile;
	}
	public void setHero_missile(int hero_missile) {
		this.hero_missile = hero_missile;
	}
	public String getHero_attack_delay() {
		return hero_attack_delay;
	}
	public void setHero_attack_delay(String hero_attack_delay) {
		this.hero_attack_delay = hero_attack_delay;
	}
	public String getHero_excute_delay() {
		return hero_excute_delay;
	}
	public void setHero_excute_delay(String hero_excute_delay) {
		this.hero_excute_delay = hero_excute_delay;
	}
	public float getHero_up_strong() {
		return hero_up_strong;
	}
	public void setHero_up_strong(float hero_up_strong) {
		this.hero_up_strong = hero_up_strong;
	}
	public float getHero_up_sharp() {
		return hero_up_sharp;
	}
	public void setHero_up_sharp(float hero_up_sharp) {
		this.hero_up_sharp = hero_up_sharp;
	}
	public float getHero_up_intellience() {
		return hero_up_intellience;
	}
	public void setHero_up_intellience(float hero_up_intellience) {
		this.hero_up_intellience = hero_up_intellience;
	}
	public String getHero_image() {
		return hero_image;
	}
	public void setHero_image(String hero_image) {
		this.hero_image = hero_image;
	}
	public String getHero_description() {
		return hero_description;
	}
	public void setHero_description(String hero_description) {
		this.hero_description = hero_description;
	}
	public int getHero_group() {
		return hero_group;
	}
	public void setHero_group(int hero_group) {
		this.hero_group = hero_group;
	}
	public int getHero_bar() {
		return hero_bar;
	}
	public void setHero_bar(int hero_bar) {
		this.hero_bar = hero_bar;
	}
	public int getHero_type() {
		return hero_type;
	}
	public void setHero_type(int hero_type) {
		this.hero_type = hero_type;
	}
	public String getLabel_id() {
		return label_id;
	}
	public void setLabel_id(String label_id) {
		this.label_id = label_id;
	}
	private String hero_name;
	private String hero_name_en;
	private int hero_sex;
	private int hero_def_hp;
	private int hero_def_mp;
	private int hero_def_strong;
	private int hero_def_sharp;
	private int hero_def_intelligence;
	private float hero_def_armor;
	private String hero_def_attack;
	private int hero_def_speed;
	private int hero_att_range;
	private String hero_vision;
	private int hero_missile;
	private String hero_attack_delay;
	private String hero_excute_delay;
	private float hero_up_strong;
	private float hero_up_sharp;
	private float hero_up_intellience;
	private String hero_image;
	private String hero_description;
	private int hero_group;
	private int hero_bar;
	private int hero_type;
	private String label_id;
	private String recommend_goods;

}
