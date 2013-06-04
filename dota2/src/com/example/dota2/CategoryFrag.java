package com.example.dota2;

import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CategoryFrag extends Fragment implements OnClickListener {
	private View view;
	private FragmentActivity context;
	private Button goitem, gohero;
	private ListView lv_hero;
	private ListView lv_goods;
	private final String[] heroCategory = { "����Ӣ��","����", "����", "����","��ս","Զ��","����","��ʦ","��Ұ","Gank","���","����","����","�ٻ�","ѣ��","��Ĭ","����","����","��˸","����","����"};
	private final String[] goodsCategory = { "������Ʒ","������Ʒ", "�ϳ���Ʒ", "����","�����ָ�","�����ظ�","����","ħ������","��Ѫ","������","�����ٶ�","�ƶ��ٶ�","����","��ɽ"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.category_frag, null);
		context = this.getActivity();
		goitem = (Button) view.findViewById(R.id.goitemfrag);
		gohero = (Button) view.findViewById(R.id.goherofrag);
		lv_hero = (ListView) view.findViewById(R.id.listView1);
		lv_hero.setAdapter(new ArrayAdapter<String>(context,
				R.layout.item_leftmenu_listview,R.id.textView1,
				heroCategory));
		lv_hero.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				ListFrag listFrag = new ListFrag();
				Bundle data = new Bundle();
				data.putInt("flag", ListFrag.Flag_HERO);
				data.putString("Category", heroCategory[position]);
				listFrag.setArguments(data);
				switchFrag(R.id.frag, listFrag, false);
			}
		});
		lv_goods = (ListView) view.findViewById(R.id.listView2);
		lv_goods.setAdapter(new ArrayAdapter<String>(context,
				R.layout.item_leftmenu_listview,R.id.textView1,
				goodsCategory));
		lv_goods.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				ListFrag listFrag = new ListFrag();
				Bundle data = new Bundle();
				data.putInt("flag", ListFrag.Flag_GOODS);
				data.putString("Category", goodsCategory[position]);
				listFrag.setArguments(data);
				switchFrag(R.id.frag, listFrag, false);
			}
		});
		goitem.setOnClickListener(this);
		gohero.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		Bundle data = new Bundle();
		ListFrag listFrag = new ListFrag();
		Activity activity=this.getActivity();
        SlidingFragmentActivity slidingFragment=(SlidingFragmentActivity)activity;
        slidingFragment.showContent();
		switch (v.getId()) {
		case R.id.goitemfrag:
			data.putInt("flag", ListFrag.Flag_GOODS);
			listFrag.setArguments(data);
			switchFrag(R.id.frag, listFrag, false);
			break;
		case R.id.goherofrag:
			data.putInt("flag", ListFrag.Flag_HERO);
			listFrag.setArguments(data);
			switchFrag(R.id.frag, listFrag, false);
			break;

		}

	}

	public void switchFrag(int Rid, Fragment frag, boolean addToBackStack) {
		try {
			FragmentTransaction ft = context.getSupportFragmentManager()
					.beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(Rid, frag);

			if (addToBackStack) {
				ft.addToBackStack(null);
			}

			ft.commit();// �ύ
		} catch (Exception e) {
		}
	}

}
