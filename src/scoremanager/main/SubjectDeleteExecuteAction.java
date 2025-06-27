package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        //ローカル変数の定義
        HttpSession session = req.getSession();
    	Teacher teacher = (Teacher) session.getAttribute("user");//セッション

    	 // リクエストパラメータの取得
        String cd = req.getParameter("subject_cd");


        // Subjectオブジェクトを生成
        Subject subject = new Subject();
        SubjectDao dao = new SubjectDao();

        subject = dao.get(cd,teacher.getSchool());

        //subject deleteをよびだす
        boolean Subject = dao.delete(subject);


        //削除
        if(subject != null){
        	dao.delete(subject);
        	req.getRequestDispatcher("subject_delete_done.jsp").forward(req,res);


        } else {
            req.getRequestDispatcher("subject_delete.jsp").forward(req,res);
        }


    }
}
