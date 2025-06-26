package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        String schoolCd = (String) session.getAttribute("school_cd");


        String cd = req.getParameter("cd");
        String name = req.getParameter("name");



        /*
         *  DBからcd変数の値で検索する
         *  　→検索結果が「ない」：科目コードは使われていない
         *  　→検索結果が「ある」：科目コードが使われている
         */

        Subject subject = new Subject();
        SubjectDao subjectDao = new SubjectDao();

        subject = subjectDao.get(cd,teacher.getSchool() );

        System.out.println(subject);

        if (subject != null) {
            req.setAttribute("error", "科目コードが重複しています。");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        }




        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "このフィールドを入力してください。");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
            return;
        }

        if (name.length() != 3) {
            req.setAttribute("error", "科目名は3文字以内で入力してください。");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }


        School school = new School();
        school.setCd(schoolCd);


        subject = new Subject();
        subject.setSchool(teacher.getSchool());
        subject.setCd(cd);
        subject.setName(name);



        SubjectDao dao = new SubjectDao();
        dao.save(subject);


        req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
    }
}



















//package scoremanager.main;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import bean.Subject;
//import bean.Teacher;
//import dao.SubjectDao;
//import tool.Action;
//
//public class SubjectCreateExecuteAction extends Action {
//
//    @SuppressWarnings("null")
//	@Override
//    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
//
//        // セッションからログイン中の教員情報を取得
//        HttpSession session = req.getSession();
//        Teacher teacher = (Teacher) session.getAttribute("user");
//
//        // リクエストパラメータ（科目コードとクラス名）を取得
//        String subject_cd = req.getParameter("cd");
//        String subject_name = req.getParameter("name");
//
//        // DAOの生成
//        SubjectDao subjectDao = new SubjectDao();
//
//        // 該当の科目情報を取得（存在すれば編集、なければ新規）
//        Subject subject = new Subject();
//        if (subject_cd != null && !subject_cd.isEmpty()) {
//        	subject.setCd(subject_cd);
//			subject.setName(subject_name);
//			subject.setSchool(teacher.getSchool());
//
//			// saveメソッドで情報を登録
//			subjectDao.save(subject);
//        }
//
//        // 科目情報を画面に渡す
//        if (subject != null) {
//            req.setAttribute("cd", subject.getCd());
//            req.setAttribute("name", subject.getName());
//            req.setAttribute("school", subject.getSchool());
//        } else {
//            req.setAttribute("cd", "");
//            req.setAttribute("name", "");
//
//        }
//
//        // クラス名も渡す（必要なら）
//        req.setAttribute("cd", subject_cd);
//        req.setAttribute("name", subject_name);
//
//        // 科目登録画面へフォワード
//        req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
//    }
//}
