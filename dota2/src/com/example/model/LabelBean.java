package com.example.model;

public class LabelBean {
//	列名	类型	描述	长度	是否可空	默认值	备注
//	lable_id	int	标签编号	4	no	0	主键
//	lable_name	varcher	标签名	10	no	null	英雄标签和物品标签中文名称
private int label_id;
private String label_name;
public int getLabel_id() {
	return label_id;
}
public void setLabel_id(int label_id) {
	this.label_id = label_id;
}
public String getLabel_name() {
	return label_name;
}
public void setLabel_name(String label_name) {
	this.label_name = label_name;
}


}
