package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestListSubject implements Serializable {

	/**
	 * クラス名:entYear
	 */
	private int entYear;

	/**
	 * クラス名:studentNo
	 */
	private String studentNo;

	/**
	 * クラス名:studentName
	 */
	private String studentName;

	/**
	 * クラス名:classNum
	 */
	private String classNum;

	/**
	 * クラス名:points
	 */
	private Map<Integer,Integer> points = new HashMap<>();

	public void putPoint(int key,int value){
		this.points.put(key, value);
	}

	public int getEntYear() {
		return entYear;
	}

	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public Map<Integer, Integer> getPoints() {
		return points;
	}

	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}





}
