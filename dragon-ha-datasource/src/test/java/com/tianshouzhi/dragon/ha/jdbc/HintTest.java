package com.tianshouzhi.dragon.ha.jdbc;

import com.tianshouzhi.dragon.ha.hint.DragonHAHintUtil;
import com.tianshouzhi.dragon.ha.jdbc.connection.DragonHAConnection;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by TIANSHOUZHI336 on 2016/12/4.
 */
public class HintTest extends BaseTest {
	@Test
	public void sqlHintTest() throws SQLException {

		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user ");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String tag_name = resultSet.getString("name");
			System.out.println("id:" + id + ",name:" + tag_name);
		}
		preparedStatement = connection
		      .prepareStatement("/*master*/ SELECT * FROM user ");

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String tag_name = resultSet.getString("name");
			System.out.println("id:" + id + ",name:" + tag_name);
		}
		/*
		 * PreparedStatement insert = connection.prepareStatement("INSERT into user(user_id,username) VALUES (2,'wangxiaoxiao')"); int
		 * i = insert.executeUpdate(); insert.close();
		 */
	}

	@Test
	public void threadLocalHintTest() throws SQLException {
		DragonHAHintUtil.setHintMaster(true);
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user ");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String tag_name = resultSet.getString("name");
			System.out.println("id:" + id + ",name:" + tag_name);
		}
		DragonHAHintUtil.clearHintMaster();
		resultSet.close();
		preparedStatement.close();
		connection.close();
		connection = null;
		DragonHAConnection newConn = (DragonHAConnection) dragonHADatasource.getConnection();
		preparedStatement = newConn.prepareStatement("SELECT * FROM user ");
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String tag_name = resultSet.getString("name");
			System.out.println("id:" + id + ",name:" + tag_name);
		}
	}
}
