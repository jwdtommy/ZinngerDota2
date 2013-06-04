package com.example.anaylize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.db.DBManager;
import com.example.db.GoodsColumn;
import com.example.model.ItemBean;
import com.jwd.utils.StrUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AnaylizeGoods {
	HashSet<String> allGoodsName = null;
	Context context;

	public AnaylizeGoods(Context context) {
		allGoodsName = new HashSet<String>();
		this.context = context;
	}

	public HashSet<String> getAllGoodsName() {
		for (int i = 1; i <= 14; i++) {
			HashSet<String> eachPageGoodsName = getGoodsNameByPage(i);
			allGoodsName.addAll(eachPageGoodsName);
		}
		Iterator<String> iterator = allGoodsName.iterator();
		Log.i("Zinger", "总物品数:" + allGoodsName.size());
		while (iterator.hasNext()) {
			Log.i("Zinger", "物品名:" + iterator.next());
		}
		return allGoodsName;
	}

	public HashSet<String> getGoodsNameByPage(int page) {
		HashSet<String> GoodsNames = new HashSet<String>();
		try {
			Document document = Jsoup.connect(
					"http://db.dota2.uuu9.com/goods/list/?p=" + page + "")
					.get();
			Element goodslist = document.getElementById("goodslist");
			Elements css = goodslist.getElementsByClass("heroname");

			for (Element GoodElement : css) {
				Elements qq = GoodElement.select("a[href]");
				// Log.i("Zinger","qq size:"+qq.size());
				String relHerf = qq.attr("href");
				// Log.i("Zinger","GoodElement:"+relHerf);
				String goodsName = relHerf.replace("/goods/show/", "");
				Log.i("Zinger", "物品英文名:" + goodsName);
				GoodsNames.add(goodsName);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Zinger", "e.printStackTrace():" + e.getMessage());
		}
		return GoodsNames;
	}

	public ItemBean getGoodsInfoByName(String name) {
		ItemBean itemBean = new ItemBean();
		String chineseName = "";
		Document document;
		String fromGoods = "";
		String toGoods = "";
		String descisption = "";
		String add_skill = "";
		String imageUrl = "";
		String categorys = "";
		int price = 0;
		try {
			document = Jsoup.connect(
					"http://db.dota2.uuu9.com/goods/show/" + name).get();
			chineseName = document.title().split("物品")[0];
			Elements elements = document.getElementsByClass("price");
			price = Integer
					.parseInt(elements.get(0).text().replace("总价格：", ""));

			
			elements = document.getElementsByTag("p");
			for (Element element : elements) {
				descisption=descisption+element.getElementsByClass("red").text() + "\n";
			}
			for (Element element : elements) {
				add_skill =add_skill+ element.getElementsByClass("yellow").text() + "\n";
			}
			elements = elements.get(1).getElementsByClass("orange");

			for (Element element : elements) {
				add_skill =add_skill+element.text()+"\n";
			}
			// add_skill+="\n"+elements.get(1).getElementsByClass("orange").text();

			elements = document.getElementsByClass("bord");
			imageUrl = elements.first().select("[src]").attr("abs:src");
			elements = document.getElementsByClass("anskill");

			Elements fromGoodsElements;
			Elements toGoodsElements;
			if (elements.get(0) != null) {
				fromGoodsElements = elements.get(0)
						.getElementsByClass("picbox");


				for (Element fromGoodsElement : fromGoodsElements) {
					String fromGoodsName = fromGoodsElement.select("a[href]")
							.attr("href").replace("/goods/show/", "");
		
					fromGoods = fromGoodsName + "," + fromGoods;
				}
				
			}

			if (elements.get(1) != null) {
				toGoodsElements = elements.get(1).getElementsByClass("picbox");

			
				for (Element toGoodsElement : toGoodsElements) {
					String toGoodsName = toGoodsElement.select("a[href]")
							.attr("href").replace("/goods/show/", "");
				
					toGoods = toGoodsName + "," + toGoods;
				}
				
			}
			elements = document.getElementsByClass("paddju");

			elements = elements.get(0).getElementsByTag("p");
			elements = elements.get(0).getElementsByTag("a");

			for (Element element : elements) {
				// Log.i("Zinger","fenlei:"+element.text());
				categorys = categorys + element.text() + ",";
			}
			if (fromGoods.equals(""))// 如果fromGoods为空，说明是基础物品，反之，是合成物品
			{
				categorys = categorys + "基础物品";
				itemBean.setCan_compose(false);
			} else {
				categorys = categorys + "合成物品";
			}
			Log.i("Zinger", "categorys:" + categorys);
			Log.i("Zinger", "chineseName:" + chineseName);
			Log.i("Zinger", "descisption:" + descisption);
			Log.i("Zinger", "add_skill:" + add_skill);
			Log.i("Zinger", "imageUrl:" + imageUrl);
			Log.i("Zinger", "fromGoods:" + fromGoods);
			Log.i("Zinger", "toGoods:" + toGoods);
			itemBean.setGoods_name(name.toLowerCase());
			itemBean.setGoods_chineseName(chineseName);
			itemBean.setGoods_price(price);
			itemBean.setGoods_disdription(descisption);
			itemBean.setGoods_add_skill(add_skill);
			itemBean.setGoods_image(imageUrl);
			fromGoods = StrUtil.cutLastlyComma(fromGoods);// 多值字段，去除最后一个逗号
			toGoods = StrUtil.cutLastlyComma(toGoods);
			itemBean.setGoods_compose_from(fromGoods);
			itemBean.setGoods_compose_to(toGoods);
			itemBean.setLabel_id(categorys);
			insertGoodsToDB(itemBean);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemBean;
	}

	public List<ItemBean> getAllGoodsInfoByNames(HashSet<String> names) {
		List<ItemBean> alItemBean = new ArrayList<ItemBean>();
		for (String name : names) {
			alItemBean.add(this.getGoodsInfoByName(name));
		}
		return alItemBean;
	}

	public void insertGoodsToDB(ItemBean goodsBean) {
		DBManager db = new DBManager(context, DBManager.DatabaseName);
		SQLiteDatabase sqldb = db.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(GoodsColumn.goods_name, goodsBean.getGoods_name().toLowerCase());
		cv.put(GoodsColumn.goods_chineseName, goodsBean.getGoods_chineseName());
		cv.put(GoodsColumn.goods_price, goodsBean.getGoods_price());
		cv.put(GoodsColumn.goods_discription, goodsBean.getGoods_disdription());
		cv.put(GoodsColumn.goods_add_skill, goodsBean.getGoods_add_skill());
		cv.put(GoodsColumn.goods_image, goodsBean.getGoods_image());
		cv.put(GoodsColumn.goods_compose_from,
				goodsBean.getGoods_compose_from());
		cv.put(GoodsColumn.goods_compose_to, goodsBean.getGoods_compose_to());
		cv.put(GoodsColumn.label_id, goodsBean.getLabel_id());
		cv.put(GoodsColumn.can_compose, goodsBean.isCan_compose());
		if (sqldb.update("Goods", cv, "" + GoodsColumn.goods_name + "=?",
				new String[] { goodsBean.getGoods_name() + "" }) <= 0)
			sqldb.insert("Goods", null, cv);
		try {
			sqldb.close();
			db.close();
		} catch (Exception e) {

		}

	}

}
