package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	//ローカル変数の定義
    	HttpSession session = req.getSession();
    	Teacher teacher = (Teacher) session.getAttribute("user");//セッション
    	String subject_cd = ""; // クラス番号
        String subject_name = "";//科目名




    	// リクエストパラメーターの取得
        subject_cd = req.getParameter("cd");

     // オブジェクトを生成
        SubjectDao dao = new SubjectDao();
        Subject subject = new Subject();

        //subjectDaoに検索
        subject = dao.get(subject_cd,teacher.getSchool());

        //セットリクエストパラメータ
        subject.setCd(subject_cd);

        req.setAttribute("subject_cd",subject_cd );


        //科目管理一覧に遷移
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);








    }
}
