package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.Student;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "select * from test where school_cd = ?";

	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		// 学生インスタンスを初期化
		Test test = new Test();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from test where student_no = ?");
			// プリペアードステートメントに学生番号をバインド
			statement.setInt(1, no);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			TestDao testDao = new TestDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 学生インスタンスに検索結果をセット
				test.setStudent(resultSet.getString("student"));
				// 学生フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(schoolDao.get(resultSet.getString("school_cd")));


			} else {
				// リザルトセットが存在しない場合
				// 学生インスタンスにnullをセット
				student= null;
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

		return student;
	}

	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {

	}

	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

	}

	public boolean save(List<Test> list) throws Exception {

	}

	private boolean save(Test test, Connection connection) throws Exception {

	}

}
