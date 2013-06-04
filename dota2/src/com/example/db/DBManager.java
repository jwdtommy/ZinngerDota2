package com.example.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.example.dota2.R;
import com.jwd.utils.StrUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

	private static int dbVersion = 1;
	SQLiteDatabase db;
	public static String DatabaseName = "test";

	public DBManager(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBManager(Context context, String name) {
		this(context, name, null, dbVersion);
	}
	public DBManager(Context context, String name,int version) {
		this(context, name, null, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.db = db;

		String heroInfoSql = "create table Hero_Info" + " ("
				+ HeroInfoColumn.hero_id + " integer primary key ,"
				+ HeroInfoColumn.hero_att_range + " integer ,"
				+ HeroInfoColumn.hero_attack_delay + " float ,"
				+ HeroInfoColumn.hero_bar + " integer ,"
				+ HeroInfoColumn.hero_def_armor + " integer ,"
				+ HeroInfoColumn.hero_def_attack + " integer ,"
				+ HeroInfoColumn.hero_def_hp + " integer ,"
				+ HeroInfoColumn.hero_def_intelligence + " integer ,"
				+ HeroInfoColumn.hero_def_mp + " integer ,"
				+ HeroInfoColumn.hero_def_sharp + " integer ,"
				+ HeroInfoColumn.hero_def_speed + " integer ,"
				+ HeroInfoColumn.hero_def_strong + " integer ,"
				+ HeroInfoColumn.hero_description + " varchar ,"
				+ HeroInfoColumn.hero_excute_delay + " float ,"
				+ HeroInfoColumn.hero_group + " integer ,"
				+ HeroInfoColumn.hero_image + " varchar ,"
				+ HeroInfoColumn.hero_missile + " integer ,"
				+ HeroInfoColumn.hero_name + " varchar ,"
				+ HeroInfoColumn.hero_name_en + " varchar ,"
				+ HeroInfoColumn.hero_sex + " integer ,"
				+ HeroInfoColumn.hero_type + " integer ,"
				+ HeroInfoColumn.hero_up_intellience + " float ,"
				+ HeroInfoColumn.hero_up_sharp + " float ,"
				+ HeroInfoColumn.hero_up_strong + " float ,"
				+ HeroInfoColumn.hero_vision + " integer ,"
				+ HeroInfoColumn.recommend_goods + " varchar ,"
				+ HeroInfoColumn.label_id + " varchar );";
		db.execSQL(heroInfoSql);

		String skillSql = "create table Skill" + " (" + SkillColumn.skill_id
				+ " integer primary key ," + SkillColumn.hero_id + " integer ,"
				+ SkillColumn.is_ultimate + " integer ,"
				+ SkillColumn.skill_detail + " integer ,"
				+ SkillColumn.skill_discription + " varchar ,"
				+ SkillColumn.skill_gif + " varchar ,"
				+ SkillColumn.skill_hotkey + " varchar ,"
				+ SkillColumn.skill_image + " varchar ,"
				+ SkillColumn.skill_name + " varchar ,"
				+ SkillColumn.skill_name_en + " varchar ,"
				+ SkillColumn.skill_order_id + " integer );";
		db.execSQL(skillSql);

		String goodsSql = "create table Goods" + " (" + GoodsColumn.goods_id
				+ " integer primary key ," + GoodsColumn.can_compose
				+ " integer ," + GoodsColumn.goods_add_armor + " integer ,"
				+ GoodsColumn.goods_add_attack + " integer ,"
				+ GoodsColumn.goods_add_hp + " integer ,"
				+ GoodsColumn.goods_add_intelligence + " integer ,"
				+ GoodsColumn.goods_add_mp + " integer ,"
				+ GoodsColumn.goods_add_sharp + " integer ,"
				+ GoodsColumn.goods_add_speed + " integer ,"
				+ GoodsColumn.goods_add_strong + " integer ,"
				+ GoodsColumn.goods_add_skill + " varchar ,"
				+ GoodsColumn.goods_compose_from + " varchar ,"
				+ GoodsColumn.goods_compose_to + " varchar ,"
				+ GoodsColumn.goods_compose_price + " integer ,"
				+ GoodsColumn.goods_discription + " varchar ,"
				+ GoodsColumn.goods_image + " varchar ,"
				+ GoodsColumn.goods_name + " varchar ,"
				+ GoodsColumn.goods_chineseName + " varchar ,"
				+ GoodsColumn.goods_price + " integer ,"
				+ GoodsColumn.goods_store + " integer ,"
				+ GoodsColumn.goods_type + " integer ," + GoodsColumn.label_id
				+ " varchar );";
		db.execSQL(goodsSql);

		String labelSql = "create table Label" + " (" + LabelColumn.lable_id
				+ " integer primary key ," + LabelColumn.lable_name
				+ " varchar );";
		db.execSQL(labelSql);

		String equipmentSql = "create table Equipment" + " ("
				+ EquipmentColumn.equipment_id + " integer primary key ,"
				+ EquipmentColumn.equipment_type + " integer ,"
				+ EquipmentColumn.goods_id + " integer ,"
				+ EquipmentColumn.hero_id + " integer );";
		db.execSQL(equipmentSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(oldVersion!=newVersion)
		db.execSQL("alter table Hero_Info add column recommend_goods varchar");
		

	}
	
	
	public static void copyDatabaseFile(Context context, boolean isfored, String db_name)
	{
		// 导入路径/data/data/com.Magic_Chen.WeatherNews/databases
		String DATABASES_DIR = "/data/data/com.example.dota2/databases";
		// DATABASE_NAME="*.db";
		InputStream in = null;
		File dir = new File(DATABASES_DIR);
		if (!dir.exists() || isfored)
		{
			try
			{
				dir.mkdir();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		File dest = new File(dir, db_name);
		if (dest.exists() && !isfored)
		{
			return;
		}

		try
		{
			if (dest.exists())
			{
				dest.delete();
			}
			dest.createNewFile();
			//数据库名text.db
				in = context.getResources().openRawResource(R.raw.test);
			
			int size = in.available();
			byte buf[] = new byte[size];
			in.read(buf);
			in.close();
			FileOutputStream out = new FileOutputStream(dest);
			// 关闭流
			out.write(buf);
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	

	public void createTable(String sqlCreate) {
		if (!StrUtil.isEmpty(sqlCreate)) {
			db.execSQL(sqlCreate);
		}
	}

}
