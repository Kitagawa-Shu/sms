package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql ="select student.no as student_no, student.name, student.ent_year, student.class_num, student.is_attend, student.school_cd, test.no as count, test.point, test.subject_cd from student left join (select * from test where subject_cd = ? and no = ?) as test on student.no = test.student_no";

	// 1件取得
	public Test get(Student student, Subject subject, School school, int no) throws Exception
	{
		Test test = new Test();

		Connection connection = getConnection();

		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement("select * from test where student_no = ? and subject_cd = ? and school_cd = ?  and no = ? ;");

			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);

			ResultSet resultSet = statement.executeQuery();

			TestDao testDao = new TestDao();

			if (resultSet.next()) {

				test.setStudent(testDao.get(resultSet.getString("student")));
				test.setSubject(testDao.get(resultSet.getString("subject")));
				test.setSchool(testDao.get(resultSet.getString("school")));
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));
			}
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

		return test;
	}

	// 結果セットをリストに変換
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {

		List<Test> list = new ArrayList<>();

		try {
			while (rSet.next()) {

				Test test = new Test();

				test.setStudent(rSet.getString("student"));
				test.setSubject(rSet.getString("subject"));
				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));

				list.add(test);
			}
		} catch (SQLException e) {
			throw e;
		}

		return list;
	}

	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

		List<Test> list = new ArrayList<>();

		Connection connection = getConnection();

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(baseSql +" where ent_year = ? and student.class_num = ? and student.school_cd = ? and student.is_attend = true;");


			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setInt(4, num);
			statement.setString(5, school.getCd());

			resultSet = statement.executeQuery();

			list = postFilter(resultSet, school);

		}catch (Exception e) {
			throw e;

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


	public boolean save(List<Test> list) throws Exception {

		Connection connection = getConnection();

		PreparedStatement statement = null;

		int count = 0;

		try {
			statement = connection.prepareStatement("insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");

			statement.setString(1, list.getStudent());
			statement.setString(2, list.getSubject());
			statement.setString(3, list.getSchool().getCd());
			statement.setString(4, list.getNo());
			statement.setString(5, list.getPoint());
			statement.setString(6, list.getClassNum());
			// プリペアードステートメントを実行
			count = statement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}


	private boolean save(Test test, Connection connection) throws Exception {

		Connection connection = getConnection();

		PreparedStatement statement = null;

		int count = 0;

		try {
			statement = connection.prepareStatement("insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");

			statement.setString(1, list.getStudent());
			statement.setString(2, list.getSubject());
			statement.setString(3, list.getSchool().getCd());
			statement.setString(4, list.getNo());
			statement.setString(5, list.getPoint());
			statement.setString(6, list.getClassNum());
			// プリペアードステートメントを実行
			count = statement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
}
