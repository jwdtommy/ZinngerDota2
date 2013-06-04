package com.example.dota2;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.DBManager;
import com.example.db.GoodsColumn;
import com.example.db.HeroInfoColumn;
import com.example.db.SkillColumn;
import com.example.hero.Hero_Detial;
import com.example.model.HeroBean;
import com.example.model.ItemBean;
import com.example.model.SkillBean;
import com.jwd.utils.ImageUtil;
import com.jwd.utils.StrUtil;
import com.jwd.views.TopBar;

public class ListFrag extends Fragment
{
	View view;
	FragmentActivity context;
	ItemBean selectGoods;
	public static int Flag_HERO = 1431;
	public static int Flag_GOODS = 64;
	private int flag;
	private String where = "";
	private String category = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.goods_frag, null);
		context = this.getActivity();
		flag = getArguments().getInt("flag");
		category = getArguments().getString("Category");
		if (!StrUtil.isEmpty(category) && !category.equals("所有物品") && !category.equals("所有英雄"))
		{
			if (flag == Flag_HERO)
			{
				where = " where " + HeroInfoColumn.label_id + " like '%" + category + "%'";
			}
			if (flag == Flag_GOODS)
			{
				where = " where " + GoodsColumn.label_id + " like '%" + category + "%'";
			}
		}

		initTopBar();
		initGridView();
		return view;
	}

	private void initTopBar()
	{
		TopBar topBar = (TopBar) view.findViewById(R.id.topbar);
		topBar.setTitle(flag == Flag_GOODS ? "物品资料" : "英雄资料");
		topBar.showButton1(false);
	}

	public void switchFrag(int Rid, Fragment frag, boolean addToBackStack)
	{
		try
		{
			FragmentTransaction ft = context.getSupportFragmentManager().beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(Rid, frag);

			if (addToBackStack)
			{
				ft.addToBackStack(null);
			}

			ft.commit();// 提交
		} catch (Exception e)
		{
		}
	}

	private void initGridView()
	{
		GridView gv = (GridView) view.findViewById(R.id.gridView1);
		DBManager manager = new DBManager(context, "test");
		SQLiteDatabase db = manager.getWritableDatabase();
		String sql = "";
		MyAdapter adapter = null;
		if (flag == Flag_GOODS)
		{
			if (where.equals("")) {
				sql = "select * from Goods ";
			} else {
				sql = "select * from Goods " + where;
			}
			final Cursor cursor = db.rawQuery(sql, null);
			adapter = new MyAdapter(context, cursor, flag, true);

			gv.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
				{
					// TODO Auto-generated method stub
					cursor.moveToPosition(position);
					ItemBean goods = getItemBeanByCursor(cursor);
					GoodsDetailFrag goodsDetailFrag = new GoodsDetailFrag();
					Bundle data = new Bundle();
					data.putSerializable("itemBean", goods);
					goodsDetailFrag.setArguments(data);
					switchFrag(R.id.frag, goodsDetailFrag, true);
				}
			});
		} else
		{
			if (where.equals("")) {
				sql = "select * from Hero_Info";
			} else {
				sql = "select * from Hero_Info " + where;
			}
			final Cursor cursor = db.rawQuery(sql, null);
			Log.i("Zinger", "cursor countaaa:" + cursor.getCount());
			adapter = new MyAdapter(context, cursor, flag, true);
			gv.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
				{
					// TODO Auto-generated method stub
					cursor.moveToPosition(position);
					HeroBean heros = getHeroBeanByCursor(cursor);

					Hero_Detial hero_frag = new Hero_Detial();
					Bundle data = new Bundle();
					data.putSerializable("hero_bean", heros);
//					data.putSerializable("skill_bean", skillBeans);
					hero_frag.setArguments(data);
					switchFrag(R.id.frag, hero_frag, true);
				}
			});
		}
		gv.setAdapter(adapter);
	}

	class MyAdapter extends CursorAdapter
	{
		private class Holder
		{
			TextView tv_name;
			ImageView iv_icon;
		}

//		private ImageLoader mImageLoader;
		LayoutInflater inflater;

		Cursor c;
		private int flag;

		public MyAdapter(Context context, Cursor c, int flag, boolean autoRequery)
		{
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
			inflater = LayoutInflater.from(context);
			this.c = c;
//			mImageLoader = new ImageLoader(context);
			this.flag = flag;
		}

		@Override
		public void bindView(View arg0, Context arg1, Cursor arg2)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			Log.i("Zinger", "count():" + getCursor().getCount());
			return getCursor().getCount();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return super.getItem(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return super.getItemId(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2)
		{
			// TODO Auto-generated method stub
			Holder holder;
			if (convertView == null)
			{
				holder = new Holder();
				convertView = inflater.inflate(R.layout.item_gridview, null);
				convertView.setTag(holder);
			} else
			{
				holder = (Holder) convertView.getTag();
			}
			holder.tv_name = (TextView) convertView.findViewById(R.id.textView1);
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.imageView1);
			c.moveToPosition(position);
			if (flag == Flag_GOODS)
			{
				ItemBean goods = getItemBeanByCursor(c);
				Log.i("Zinger", "Goods_image:" + goods.getGoods_image());
				holder.tv_name.setText(goods.getGoods_chineseName());
				holder.iv_icon.setTag(goods.getGoods_image());
				Drawable bg=ImageUtil.getDrawableByPicName(goods.getGoods_name(), context, "com.example.dota2");
				holder.iv_icon.setBackgroundDrawable(bg);
			}

			else
			{
				HeroBean heroBean = getHeroBeanByCursor(c);
				Log.i("Zinger", "heroBean_name:" + heroBean.getHero_name());
				holder.tv_name.setText(heroBean.getHero_name());
				holder.iv_icon.setTag(heroBean.getHero_image());
//				mImageLoader.DisplayImage(heroBean.getHero_image(), holder.iv_icon, false, heroBean.getHero_name_en());
				Drawable bg=ImageUtil.getDrawableByPicName(heroBean.getHero_name_en(), context, "com.example.dota2");
				holder.iv_icon.setBackgroundDrawable(bg);
			}

			return convertView;
		}

	}

	public static ItemBean getItemBeanByCursor(Cursor c)
	{
		ItemBean goods = new ItemBean();
		goods.setGoods_name(c.getString(c.getColumnIndex(GoodsColumn.goods_name)));
		goods.setGoods_chineseName(c.getString(c.getColumnIndex(GoodsColumn.goods_chineseName)));
		goods.setGoods_image(c.getString(c.getColumnIndex(GoodsColumn.goods_image)));
		goods.setGoods_compose_from(c.getString(c.getColumnIndex(GoodsColumn.goods_compose_from)));
		goods.setGoods_compose_to(c.getString(c.getColumnIndex(GoodsColumn.goods_compose_to)));
		goods.setGoods_price(c.getInt(c.getColumnIndex(GoodsColumn.goods_price)));
		goods.setGoods_disdription(c.getString(c.getColumnIndex(GoodsColumn.goods_discription)));
		goods.setGoods_add_skill(c.getString(c.getColumnIndex(GoodsColumn.goods_add_skill)));
		goods.setLabel_id(c.getString(c.getColumnIndex(GoodsColumn.label_id)));
		return goods;
	}

	private HeroBean getHeroBeanByCursor(Cursor c)
	{
		HeroBean hero = new HeroBean();
		hero.setHero_image(c.getString(c.getColumnIndex(HeroInfoColumn.hero_image)));
		hero.setHero_name(c.getString(c.getColumnIndex(HeroInfoColumn.hero_name)));
		hero.setHero_name_en(c.getString(c.getColumnIndex(HeroInfoColumn.hero_name_en)));
		hero.setHero_att_range(c.getInt(c.getColumnIndex(HeroInfoColumn.hero_att_range)));
		hero.setHero_def_armor(c.getFloat(c.getColumnIndex(HeroInfoColumn.hero_def_armor)));
		hero.setHero_def_attack(c.getString(c.getColumnIndex(HeroInfoColumn.hero_def_attack)));
		hero.setHero_def_hp(c.getInt(c.getColumnIndex(HeroInfoColumn.hero_def_hp)));
		hero.setHero_def_mp(c.getInt(c.getColumnIndex(HeroInfoColumn.hero_def_mp)));
		hero.setHero_def_sharp(c.getInt(c.getColumnIndex(HeroInfoColumn.hero_def_sharp)));
		hero.setHero_def_speed(c.getInt(c.getColumnIndex(HeroInfoColumn.hero_def_speed)));
		hero.setHero_def_strong(c.getInt(c.getColumnIndex(HeroInfoColumn.hero_def_strong)));
		hero.setHero_def_intelligence(c.getInt(c.getColumnIndex(HeroInfoColumn.hero_def_intelligence)));
		hero.setHero_description(c.getString(c.getColumnIndex(HeroInfoColumn.hero_description)));
		hero.setHero_id(c.getInt(c.getColumnIndex(HeroInfoColumn.hero_id)));
		hero.setRecommend_goods(c.getString(c.getColumnIndex(HeroInfoColumn.recommend_goods)));	
		hero.setHero_up_strong(c.getFloat(c.getColumnIndex(HeroInfoColumn.hero_up_strong)));
		hero.setHero_up_sharp(c.getFloat(c.getColumnIndex(HeroInfoColumn.hero_up_sharp)));
		hero.setHero_up_intellience(c.getFloat(c.getColumnIndex(HeroInfoColumn.hero_up_intellience)));
		
		return hero;
	}


}
