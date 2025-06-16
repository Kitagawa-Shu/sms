package bean;

import java.io.Serializable;

public class Test implements Serializable {

	/**
	 * クラス名:student
	 */
	private Student student;

	/**
	 * クラス名:classNum
	 */
	private String String;

	/**
	 * クラス名:subject
	 */
	private Subject subject;

	/**
	 * クラス名:school
	 */
	private School school;

	/**
	 * クラス名:no
	 */
	private int no;

	/**
	 * クラス名:point
	 */
	private int point;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getString() {
		return String;
	}

	public void setString(String string) {
		String = string;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}


}
