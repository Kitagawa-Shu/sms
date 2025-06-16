package bean;

import java.io.Serializable;

public class Subject implements Serializable {

	/**
	 * クラス名:cd
	 */
	private String cd;

	/**
	 * クラス名:name
	 */
	private String name;

	/**
	 * クラス名:school
	 */
	private School school;

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}


}
