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
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {

				test.setStudent(studentDao.get(resultSet.getString("student_no")));
				test.setSubject(subjectDao.get(resultSet.getString("subject_cd"),schoolDao.get(resultSet.getString("school_cd"))));
				test.setSchool(schoolDao.get(resultSet.getString("school_cd")));
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
				StudentDao studentDao = new StudentDao();
				SubjectDao subjectDao = new SubjectDao();
				SchoolDao schoolDao = new SchoolDao();

				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"),schoolDao.get(rSet.getString("school_cd"))));
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

				for (Test test : list) {
					statement.setString(1, test.getStudent().getNo());
					statement.setString(2, test.getSubject().getCd());
					statement.setString(3, test.getSchool().getCd());
					statement.setInt(4, test.getNo());
					statement.setInt(5, test.getPoint());
					statement.setString(6, test.getStudent().getClassNum());

					count += statement.executeUpdate();
				}

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

		PreparedStatement statement = null;

		int count = 0;

		try {
			Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getPoint());
			if (old == null) {

				statement = connection.prepareStatement("insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");

				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			}


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
