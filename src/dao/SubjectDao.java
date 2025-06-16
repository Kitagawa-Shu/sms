package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean Subject
import bean School;

public class Subject extends Dao{
	public Subject get(cd String, School school)throws Exception {
		// クラス番号インスタンスを初期化
		Subject subject = new Subject();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where subject = ? and school_cd = ?");
			// プリペアードステートメントに値をバインド
			statement.setString(1, subject);
			statement.setString(2, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao sDao = new SchoolDao();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// クラス番号インスタンスに検索結果をセット
				subject.setSubject(rSet.getString("subject"));
				subject.setSchool(sDao.get(rSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				// クラス番号インスタンスにnullをセット
				subject = null;
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

		return subject;
	}


	public List<Subject> filter(School school) throws Exception{
		// リストを初期化
				List<String> list = new ArrayList<>();
				// データベースへのコネクションを確立
				Connection connection = getConnection();
				// プリペアードステートメント
				PreparedStatement statement = null;

				try {
					// プリペアードステートメントにSQL文をセット
					statement = connection
							.prepareStatement("select subject from subject where school_cd=? order by subject");
					// プリペアードステートメントに学校コードをバインド
					statement.setString(1, school.getCd());
					// プリペアードステートメントを実行
					ResultSet rSet = statement.executeQuery();

					// リザルトセットを全件走査
					while (rSet.next()) {
						// リストに科目番号を追加
						list.add(rSet.getString("subject"));
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

				return list;
	}


	public boolean save(Subject subject) throws Exception{

		// コネクションを確立
				Connection connection = getConnection();
				// プリペアードステートメント
				PreparedStatement statement = null;
				// 実行件数
				int count = 0;

				try {
					// プリペアードステートメントにINSERT文をセット
					statement = connection.prepareStatement("insert into subject(school_cd, subject) values(?, ?)");
					// プリペアードステートメントに値をバインド
					statement.setString(1, subject.getSchool().getCd());
					statement.setString(2, subject.getSubject());
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


	public boolean save(Subject subject) throws Exception{
		// コネクションを確立
				Connection connection = getConnection();
				// プリペアードステートメント
				PreparedStatement statement = null;
				// 実行件数
				int count = 0;

				try {
					// プリペアードステートメントにUPDATE文をセット
					statement = connection.prepareStatement("update subject set subject = ? where school_cd = ? and subject = ?");
					statement.setString(1, newSubject);
					statement.setString(2, subject.getSchool().getCd());
					statement.setString(3, subject.getSubject());
					// プリペアードステートメントを実行
					count += statement.executeUpdate();
					// プリペアードステートメントを閉じる
					if (statement != null) {
						try {
							statement.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
					}

					// 登録されている学生のクラスも変更
					statement = connection.prepareStatement("update student set subject = ? where school_cd = ? and subject = ?");
					statement.setString(1, newSubject);
					statement.setString(2, subject.getSchool().getCd());
					statement.setString(3, subject.getSubject());
					count += statement.executeUpdate();
					// プリペアードステートメントを閉じる
					if (statement != null) {
						try {
							statement.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
					}

					// テストに登録されているクラスも変更
					statement = connection.prepareStatement("update test set subject = ? where school_cd = ? and subject = ?");
					statement.setString(1, newSubject);
					statement.setString(2, subject.getSchool().getCd());
					statement.setString(3, subject.getSubject());
					count += statement.executeUpdate();

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

				if (count > 3) {
					// 実行件数が3件以上ある場合
					return true;
				} else {
					// 実行件数が3件未満の場合
					return false;
				}
			}
	}
}