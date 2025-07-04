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
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = new Subject();

        subject = subjectDao.get(cd,teacher.getSchool());




        //削除
        if(subject != null){
        	subjectDao.delete(subject);
        	req.getRequestDispatcher("subject_delete_done.jsp").forward(req,res);

        }


    }
}
