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

	private String baseSql = "SELECT * FROM test WHERE school_cd = ?";

	// 1件取得
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = null;
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			String sql = "SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd()); // subject.getName() → getCd() 推奨
			statement.setString(3, school.getCd());
			statement.setInt(4, no);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				test = new Test();
				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));
				test.setClassNum(resultSet.getString("class_num"));
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
				Student student = new Student();
				student.setNo(rSet.getString("student_no"));
				test.setStudent(student);

				Subject subject = new Subject();
				subject.setCd(rSet.getString("subject_cd")); // setName → setCd 推奨
				test.setSubject(subject);

				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));

				list.add(test);
			}
		} catch (SQLException e) {
			throw e;
		}

		return list;
	}

	// 条件で絞り込み
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			String sql = baseSql + " AND class_num = ? AND subject_cd = ? AND no = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, school.getCd());
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd()); // getName() → getCd() 推奨
			statement.setInt(4, num);

			ResultSet resultSet = statement.executeQuery();
			list = postFilter(resultSet, school);
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

	// 複数保存
	public boolean save(List<Test> list) throws Exception {
		Connection connection = getConnection();

		try {
			connection.setAutoCommit(false);
			for (Test test : list) {
				if (!save(test, connection)) {
					connection.rollback();
					return false;
				}
			}
			connection.commit();
			return true;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
	}

	// 単一保存
	private boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;

		try {
			String sql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) VALUES (?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, test.getStudent().getNo());
			statement.setString(2, test.getSubject().getCd()); // getName() → getCd() 推奨
			statement.setString(3, test.getSchool().getCd());
			statement.setInt(4, test.getNo());
			statement.setInt(5, test.getPoint());
			statement.setString(6, test.getClassNum());

			int rows = statement.executeUpdate();
			return rows == 1;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// connectionは外で閉じる（呼び出し元で閉じているためここでは閉じない）
		}
	}
}
