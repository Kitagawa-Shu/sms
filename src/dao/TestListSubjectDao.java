package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	private String baseSql = "SELECT * FROM test WHERE school_cd = ?";

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {

		 List<TestListSubject> list = new ArrayList<>();

		    try {
		        while (rSet.next()) {
		            TestListSubject subject = new TestListSubject();

		            subject.setEntYear(rSet.getInt("entYear"));
		            subject.setStudentNo(rSet.getString("studentNo"));
		            subject.setStudentName(rSet.getString("studentName"));
		            subject.setClassNum(rSet.getString("classNum"));
		            subject.setPoints(new HashMap<>());

		            list.add(subject);
		        }
		    } catch (SQLException e) {
		        throw e;
		    }
		    return list;
	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

	  List<TestListSubject> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        String sql = baseSql + " AND class_num = ? AND subject_cd = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, school.getCd());
	        statement.setString(2, classNum);
	        statement.setString(3, subject.getCd());

	        ResultSet resultSet = statement.executeQuery();
	        list = postFilter(resultSet);
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return list;
	}

}
