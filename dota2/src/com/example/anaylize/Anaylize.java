package com.example.anaylize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.db.DBManager;
import com.example.model.HeroBean;
import com.example.model.SkillBean;

public class Anaylize {
	// 0力1敏2智
	// 力量strength 敏捷Dexterity 智力intelligence
	private final static int STRENGTH = 0;
	private final static int DEXTERITY = 1;
	private final static int INTELLIGENCE = 2;
	private String heroName_en;
	private Context context;
	private ArrayList<String> skilllist = new ArrayList<String>();
	private ArrayList<String> goodsList = new ArrayList<String>();
	
	public Anaylize(Context context) {
		this.context = context;
	}

	public void anaylizeItems(String path) throws IOException {
		URL url = null;
		url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStreamReader is = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(is);
		String line = null;
		// boolean check = false;
		while (null != (line = br.readLine())) {
			line = findContent(line, 1);
			System.out.println(line.trim());
		}
	}

	public ArrayList<String> anaylizeData(String path) throws IOException {
		URL url = null;
		url = new URL(path);
//		url = new URL("http://db.dota2.uuu9.com/hero/show/invoker");
		
		ArrayList<String> list = new ArrayList<String>();
		

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		skilllist.clear();
		Document document=Jsoup.parse(conn.getInputStream(), "utf-8", "");
		Elements csss = document.getElementsByClass("name");
		Element goodList = document.getElementById("goodslist");
		Elements recomGoods =goodList.getElementsByTag("div");
		Elements goods=recomGoods.get(1).getElementsByAttribute("href");
		goodsList.clear();
		for (Element good :goods){
			if(good.attr("href").contains("/goods/show/")){
				Log.i("skill", good.attr("href"));
				goodsList.add(good.attr("href").replace("/goods/show/", "").toLowerCase());
		}
		}
		for (Element css :csss)
		{
			if(css.attr("href").contains("/skill/show/")){
				Log.i("skill", css.attr("href"));
			skilllist.add(css.attr("href"));
			}
		}
		
		
		conn.disconnect();
		conn = (HttpURLConnection) url.openConnection();
		InputStreamReader is = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(is);
		String line = null;
		boolean check = false;

		while (null != (line = br.readLine())) {
			if (line.contains("DOTA2饰品") || line.contains("技能</h4>")) {
				check = true;
			}
			if (line.contains("最近改动历史") || line.contains("点击进行装备模拟")) {
				check = false;
			}
			if (check) {
				if (line.trim().startsWith("<img title"))
					list = findImg(line, list);
				line = findContent(line, 0);
				if (!line.trim().equals(""))
					list.add(line.trim());
				System.out.println(line.trim());
			}

		}
		heroName_en=path.replace("http://db.dota2.uuu9.com/hero/show/", "").replace("/", "");
		return list;
	}

	public ArrayList<String> anaylizeAddress(String path, ArrayList<String> list)
			throws IOException {
		URL url = null;
		url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStreamReader is = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(is);
		String line = null;
		while (null != (line = br.readLine())) {

			list = findAddress(line, list);

		}
		return list;
	}

	private ArrayList<String> findImg(String html, ArrayList<String> list) {
		Pattern p = null;
		Matcher m = null;
		p = Pattern
				.compile("<img\\s?title=\"([^\"]+)\"\\s?src=\"([^\"]+)\"\\s?/>");
		m = p.matcher(html);
		while (m.find()) {
			list.add(m.group(2));
		}
		return list;
	}

	private ArrayList<String> findAddress(String html, ArrayList<String> list) {
		// 配置html标记。
		Pattern p = null;
		Matcher m = null;
		String rs = null;

		p = Pattern.compile("<a href=\"([^\"]*)\"><b>([^>])+></a><br\\s+/>");
		m = p.matcher(html);

		rs = new String(html);
		// 找出所有html标记。
		while (m.find()) {

			rs = rs.replace(m.group(), "");
			
				list.add(m.group(1));
		}
		return list;
	}

	private String deleteGold(String html) {
		Pattern p = null;
		Matcher m = null;

		p = Pattern.compile("<span\\s?class=\"gold\">([^<]+)</\\s?span>");
		m = p.matcher(html);

		String rs = new String(html);
		// 找出所有html标记。
		while (m.find()) {
			// 删除html标记。
			rs = rs.replace(m.group(), "");
		}
		return rs;
	}

	private String findContent(String html, int tag) {
		// 配置html标记。
		Pattern p = null;
		Matcher m = null;
		String rs = new String(html);

		p = Pattern.compile("<(\\S*?)[^>]*>.*?|<(.*)?");
		m = p.matcher(html);
		if (tag == 1)
			rs = deleteGold(html);

		// 找出所有html标记。
		while (m.find()) {
			// 删除html标记。
			rs = rs.replace(m.group(), "");
		}
		if (tag == 0 && (rs.contains(">") || rs.contains("vote"))) {
			rs = deleteVote(rs);
		}

		return rs;
	}

	private String deleteVote(String html) {
		Pattern pp = null;
		Matcher mm = null;
		String rs = html;
		pp = Pattern.compile("(\\S*?)[^>]*>.*?|<(.*)?");
		mm = pp.matcher(rs);
		while (mm.find()) {
			// 删除html标记。
			rs = rs.replace(mm.group(), "");
		}
		if (rs.contains("voteInit")) {
			rs = "";
		}
		return rs;
	}

	public void saveData(ArrayList<String> list, int heroNum) {
		HeroBean heroBean = new HeroBean();
		heroBean.setHero_id(heroNum);
		ArrayList<SkillBean> skillList = new ArrayList<SkillBean>();
		// 英雄基本信息
		heroBean.setHero_name_en(heroName_en.toLowerCase());
		heroBean.setHero_image(list.get(1));
		list.remove(1);
	//	heroBean.setHero_name(list.get(1) + "/" + list.get(2));//例：艾瑞达/暗影恶魔
		heroBean.setHero_name(list.get(2));//例：艾瑞达/暗影恶魔
		while (!list.get(3).equals("背景故事：")) {
			if (null != heroBean.getLabel_id()) {
				heroBean.setLabel_id(heroBean.getLabel_id() + "," + list.get(3));
			} else {
				heroBean.setLabel_id(list.get(3));
			}
			Log.i("Zinger","ffff:"+heroBean.getLabel_id());
			list.remove(3);
		}
		// 04-26 17:18:26.746: I/System.out(16675): 基础属性（
		heroBean.setHero_description(list.get(4));
		while (!list.get(5).contains("基础属性（")) {
			heroBean.setHero_description(heroBean.getHero_description()
					+ list.get(5));
			list.remove(5);
		}
		for (int i = 0; i < goodsList.size(); i++)
		{
			if (null != heroBean.getRecommend_goods()) {
				heroBean.setRecommend_goods(heroBean.getRecommend_goods() + "," + goodsList.get(i));
			} else {
				heroBean.setRecommend_goods(goodsList.get(i));
			}
		}
		if (list.get(6).equals("力量"))
			heroBean.setHero_type(STRENGTH);
		else if (list.get(6).equals("敏捷"))
			heroBean.setHero_type(DEXTERITY);
		else if (list.get(6).equals("智力"))
			heroBean.setHero_type(INTELLIGENCE);
		heroBean.setHero_def_hp(Integer.valueOf((list.get(8).split("魔法")[0]
				.replace("生命", ""))));
		heroBean.setHero_def_mp(Integer.valueOf((list.get(8).split("魔法")[1])));
		heroBean.setHero_def_strong(Integer.valueOf(list.get(10).split("（")[0]));
		heroBean.setHero_def_sharp(Integer.valueOf(list.get(11).split("（")[0]
				.replace("敏捷", "")));
		heroBean.setHero_def_intelligence(Integer.valueOf(list.get(12).split(
				"（")[0].replace("智力", "")));
		heroBean.setHero_up_strong(Float.valueOf(list.get(10).split("每等级+")[1]
				.replace("）", "")));
		heroBean.setHero_up_sharp(Float.valueOf(list.get(11).split("每等级+")[1]
				.replace("）", "")));
		heroBean.setHero_up_intellience(Float.valueOf(list.get(12)
				.split("每等级+")[1].replace("）", "")));
		heroBean.setHero_def_attack(list.get(13).replace("初始攻击", ""));
		heroBean.setHero_def_armor(Float.valueOf((list.get(14).replace("初始护甲",
				""))));
		heroBean.setHero_att_range(Integer.valueOf(list.get(15).split("视野")[0]
				.replace("攻击范围", "")));
		heroBean.setHero_vision(list.get(15).split("视野")[1].replace("(白天/黑夜)",
				""));
		heroBean.setHero_def_speed(Integer
				.valueOf(list.get(16).split("弹道速度")[0].replace("移动速度", "")));
		heroBean.setHero_missile(Integer.valueOf(list.get(16).split("弹道速度")[1]));
		heroBean.setHero_excute_delay(list.get(17).replace("施法前摇/后摇", ""));
		heroBean.setHero_attack_delay(list.get(18).replace("攻击前摇/后摇", ""));

		for (int i = 0; i < skilllist.size(); i++)
//		{
//			URL url;
//			try
//			{
//
//				SkillBean skillBean = new SkillBean();
//				skillBean.setSkill_id(skillcount);
//				skillBean.setSkill_name_en((heroName_en+skilllist.get(i).replace("/skill/show/", "")).toLowerCase());
//				skillBean.setHero_id(heroBean.getHero_id());
//				
//				url = new URL("http://db.dota2.uuu9.com"+skilllist.get(i));
//				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				Document document=Jsoup.parse(conn.getInputStream(), "utf-8", "");
//				Elements css = document.getElementsByClass("pageright");
//				Elements red =css.get(0).getElementsByClass("red");
//				Elements image =css.get(0).getElementsByTag("img");
//				Elements green =css.get(0).getElementsByClass("green");
//				Elements yellow =css.get(0).getElementsByClass("yellow");
//				Elements ds =css.get(0).getElementsByClass("dldd");
//				Elements reyellow =ds.get(0).getElementsByClass("yellow");
//				ds.text();
//				skillBean.setSkill_order_id(i);
//				skillBean.setSkill_name(image.get(0).attr("title"));
//				skillBean.setSkill_image(image.get(0).attr("src"));
//				skillBean.setSkill_discription(red.text()+"\n"+green.text()+"\n"+yellow.text().replace(reyellow.text(), ""));
//				skillBean.setSkill_detial(ds.text());
//				skillList.add(skillBean);
//			} catch (MalformedURLException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			skillcount++;
//			
//		}
		pushIntoBase(heroBean, skillList);
	}

	private void pushIntoBase(HeroBean heroBean, ArrayList<SkillBean> list) {
		DBManager db = new DBManager(context, "test");
		SQLiteDatabase sqldb = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		try {
			values.put("_id", heroBean.getHero_id());
//			values.put("hero_name", heroBean.getHero_name());
//			values.put("hero_name_en", heroBean.getHero_name_en());
//			values.put("hero_sex", heroBean.getHero_sex());
//			values.put("hero_def_hp", heroBean.getHero_def_hp());
//			values.put("hero_def_mp", heroBean.getHero_def_mp());
//			values.put("hero_def_strong", heroBean.getHero_def_strong());
//			values.put("hero_def_sharp", heroBean.getHero_def_sharp());
//			values.put("hero_def_intelligence",
//					heroBean.getHero_def_intelligence());
//			values.put("hero_def_armor", heroBean.getHero_def_armor());
//			values.put("hero_def_attack", heroBean.getHero_def_attack());
//			values.put("hero_def_speed", heroBean.getHero_def_speed());
//			values.put("hero_att_range", heroBean.getHero_att_range());
//			values.put("hero_vision", heroBean.getHero_vision());
//			values.put("hero_missile", heroBean.getHero_missile());
//			values.put("hero_attack_delay", heroBean.getHero_attack_delay());
//			values.put("hero_excute_delay", heroBean.getHero_excute_delay());
//			values.put("hero_up_strong", heroBean.getHero_up_strong());
//			values.put("hero_up_sharp", heroBean.getHero_up_sharp());
//			values.put("hero_up_intellience", heroBean.getHero_up_intellience());
//			values.put("hero_image", heroBean.getHero_image());
//			values.put("hero_description", heroBean.getHero_description());
//			values.put("hero_group", heroBean.getHero_group());
//			values.put("hero_bar", heroBean.getHero_bar());
//			values.put("hero_type", heroBean.getHero_type());
//			values.put("label_id", heroBean.getLabel_id());
			values.put("recommend_goods", heroBean.getRecommend_goods().toLowerCase());
			if (sqldb.update("Hero_Info", values, "_id=?",
					new String[] { heroBean.getHero_id() + "" }) <= 0)
				sqldb.insert("Hero_Info", null, values);
//			for (int i = 0; i < list.size(); i++) {
//				values.clear();
//				values.put("_id", list.get(i).getSkill_id());
//				values.put("hero_id", list.get(i).getHero_id());
//				values.put("skill_name", list.get(i).getSkill_name());
//				values.put("skill_name_en", list.get(i).getSkill_name_en());
//				values.put("skill_discription", list.get(i)
//						.getSkill_discription());
//				values.put("skill_detail", list.get(i).getSkill_detial());
//				values.put("skill_image", list.get(i).getSkill_image());
//				values.put("skill_gif", list.get(i).getSkill_gif());
//				values.put("is_ultimate", list.get(i).getIs_ultimate());
//				values.put("skill_hotkey", list.get(i).getSkill_hotkey());
//				values.put("skill_order_id", list.get(i).getSkill_order_id());
//
//				if (sqldb.update("Skill", values, "_id=?",
//						new String[] { list.get(i).getSkill_id() + "" }) <= 0)
//					sqldb.insert("Skill", null, values);
//			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sqldb.close();
		}

	}
}
