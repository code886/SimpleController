package com.yuanhao.config;

import java.lang.reflect.Field;
import java.util.List;

import com.yuanhao.dao.BaseDao;
import com.yuanhao.model.UserBean;

/**
 * 将对象操作映射为数据表操作
 * @author YuanHao
 * @since 2016年12月27日
 */
public class Conversation {
	public static Conversation conversation;
	
	/**
	 * 单例对象 
	 */
	public static synchronized Conversation getInstance() {
		if (conversation == null) {
			conversation = new Conversation();	
		}
		return conversation;
	}

	private ORMapping orMapping;
	
	public void setOrMapping(ORMapping orMapping) {
		this.orMapping = orMapping;
	}
	
	public ORMapping getOrMapping() {
		return orMapping;
	}
		
	/**
	 * 根据配置创建表
	 */
	public void baseConfigCreateTable() {
		String sql = "CREATE TABLE IF NOT EXISTS ";
		List<ClassInfo> classInfos = orMapping.getClassInfos();
		for (ClassInfo classInfo : classInfos) {
			sql = sql + classInfo.getTable() + "(";
			sql = sql + classInfo.getId_name() + " INT PRIMARY KEY AUTO_INCREMENT" + ",";
			List<ColumnInfo> listColumns = classInfo.getColumnInfos();
			int i = 0;
			for (ColumnInfo columnInfo : listColumns) {
				// 添加列名
				sql = sql + columnInfo.getColumn()+" ";
				if (columnInfo.getType().equals("String")) {
					sql = sql + "VARCHAR(20)";
				}
				if (i != listColumns.size() - 1) {
					sql = sql + ",";
				} else {
					sql = sql + ")" + ";";
				}
				i++;
			}
		}
		System.out.println("sql:"+sql);
		new BaseDao().update(sql, null);
	}
	
	public UserBean queryUser(String value) {
		baseConfigCreateTable();
		String sql = "select * from user where username=?";
		BaseDao baseDao = new BaseDao();
		List<UserBean> list = baseDao.query(sql, new Object[]{value}, UserBean.class);
		return  (list!=null&&list.size()>0) ? list.get(0) : null;
	}

	public boolean insertUser(UserBean u){
		//插入之前检查表中是否已经存在该用户名
		UserBean userBean = this.queryUser(u.getUsername());
		boolean flag = false;
		if(userBean==null){
			Object[] paramsValue;
			String sql;
			try {
				//baseConfigCreateTable();
				paramsValue = new Object[20];
				sql = "insert into ";
				List<ClassInfo> classInfos = orMapping.getClassInfos();
				for (ClassInfo classInfo : classInfos) {
					if (classInfo.getName().equals("UserBean")) {
						sql = sql + classInfo.getTable() + "";
						Field[] fields=u.getClass().getDeclaredFields(); 
						//获得对象所有属性
						Field field=null;
						sql = sql + "(";
						// 遍历属性
						for (int i = 0; i < fields.length; i++) {
							field=fields[i];
							field.setAccessible(true);//修改访问权限
							for (int j = 0; j < classInfo.getColumnInfos().size(); j++) {
								ColumnInfo columnInfo = classInfo.getColumnInfos().get(j);
								if (columnInfo.getName().equals(field.getName())) {	
								     // 属性相等
								     System.out.println(field.getName()+":"+field.get(u));
								     //读取属性值
								     paramsValue[i] = field.get(u);
								     sql = sql  + columnInfo.getColumn();
								     if (j != classInfo.getColumnInfos().size() -1) {
										sql = sql + ",";
									} else {
										sql = sql + ") ";
									}
								}
							}
						}
						sql = sql + "values " + "(";
						// 拼接参数
						for (int i = 0; i < fields.length; i++) {
							sql = sql+"?";
							if (i != fields.length -1) {
								sql = sql + ",";
							} else {
								sql = sql + ")";
							}
						}
						sql = sql + ";";
						System.out.println("sql:"+sql);
					}
				}
				flag = new BaseDao().update(sql, paramsValue);
				System.out.println(paramsValue);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean updateUser(UserBean u) {
		baseConfigCreateTable();
		
		String sql = "update user set username=?,password=?";
		Object[] paramsValue = {u.getUsername(),u.getPassword()};
		new BaseDao().update(sql, paramsValue);
		return true;
	}

	public boolean deleteUser(UserBean u) {
		baseConfigCreateTable();

		String sql = "delete from user where username=?";
		Object[] paramsValue = {u.getUsername()};
		new BaseDao().update(sql, paramsValue);
		return true;
	}
}
