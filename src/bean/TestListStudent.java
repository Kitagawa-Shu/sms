package bean;

import java.io.Serializable;

public class TestListStudent implements Serializable {

	/**
	 * クラス名:subjectName
	 */
	private String subjectName;

	/**
	 * クラス名:subjectCd
	 */
	private String subjectCd;

	/**
	 * クラス名:num
	 */
	private int num;

	/**
	 * クラス名:point
	 */
	private int point;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}



}
