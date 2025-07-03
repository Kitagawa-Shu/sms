package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	private String baseSql =
			"select student.ent_year, student.class_num, student.no, student.name, a.point as point1, b.point as point2"
			+ " from (select test.student_no, test.subject_cd, test.school_cd, test.no, test.point, test.class_num"
			+ " from test join student"
			+ " on test.student_no = student.no"
			+ " where student.ent_year = ? and test.subject_cd = ? and student.class_num = ? and student.is_attend = true and student.school_cd = ? and test.no = 1) as a"
			+ " left join (select test.student_no, test.subject_cd, test.school_cd, test.no, test.point, test.class_num"
			+ " from test join student"
			+ " on test.student_no = student.no"
			+ " where student.ent_year = ? and test.subject_cd = ? and student.class_num = ? and student.is_attend = true and student.school_cd = ? and test.no = 2) as b"
			+ " on a.student_no= b.student_no and a.subject_cd = b.subject_cd and a.class_num = b.class_num"
			+ " join student on a.student_no = student.no";


	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {

		 List<TestListSubject> list = new ArrayList<>();

		    try {
		        while (rSet.next()) {
		            TestListSubject subject = new TestListSubject();

		            subject.setEntYear(rSet.getInt("ent_year"));
		            subject.setStudentNo(rSet.getString("no"));
		            subject.setStudentName(rSet.getString("name"));
		            subject.setClassNum(rSet.getString("class_num"));
		            subject.putPoint(1,rSet.getInt("point1"));
		            if(rSet.getInt("point2") != 0) {
		            	subject.putPoint(2,rSet.getInt("point2"));

		            }else{
		            	subject.putPoint(2,-1);
		            }

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

	        statement = connection.prepareStatement(baseSql);
	        statement.setInt(1, entYear);
	        statement.setString(2, subject.getCd());
	        statement.setString(3, classNum);
	        statement.setString(4,school.getCd());

	        statement.setInt(5, entYear);
	        statement.setString(6, subject.getCd());
	        statement.setString(7, classNum);
	        statement.setString(8,school.getCd());

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
