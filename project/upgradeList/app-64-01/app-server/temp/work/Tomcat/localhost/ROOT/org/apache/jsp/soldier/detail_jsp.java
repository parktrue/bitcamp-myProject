/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.79
 * Generated at: 2023-08-26 16:44:18 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.soldier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import bitcamp.myapp.vo.Soldier;

public final class detail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("bitcamp.myapp.vo.Soldier");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      bitcamp.myapp.dao.SoldierDao soldierDao = null;
      synchronized (application) {
        soldierDao = (bitcamp.myapp.dao.SoldierDao) _jspx_page_context.getAttribute("soldierDao", javax.servlet.jsp.PageContext.APPLICATION_SCOPE);
        if (soldierDao == null){
          throw new java.lang.InstantiationException("bean soldierDao not found within scope");
        }
      }

  Soldier soldier = soldierDao.findBy(Integer.parseInt(request.getParameter("no")));
  pageContext.setAttribute("soldier", soldier);

      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("  <meta charset='UTF-8'>\n");
      out.write("  <title>병사 상세 정보</title>\n");
      out.write("  <link rel=\"stylesheet\" href=\"detail.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../header.jsp", out, false);
      out.write("<link rel=\"stylesheet\" href=\"../header.css\">\n");
      out.write("<div class='container'>\n");
      out.write("  <h1>병사 상세 정보</h1>\n");
      out.write("\n");
      out.write("  ");
 if (soldier == null) {
      out.write("<p>해당 번호의 병사가 없습니다!</p>\n");
      out.write("  ");
 } else { 
      out.write("<form action='/soldier/update.jsp' method='post' enctype='multipart/form-data'>\n");
      out.write("    <table border='1'>\n");
      out.write("      <tr>\n");
      out.write("        <th style='width:120px;'>사진</th>\n");
      out.write("        <td style='width:300px;'>\n");
      out.write("          ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.photo == null ?\n                  \"<img style='height:80px' src='/images/avatar.png'>\" :\n                  \"<a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-01/soldier/\"+=soldier.photo+=\"'>\"+=\n                          \"<img src='http://xxqrmvmzbxkt19010716.cdn.ntruss.com/soldier/\"+=soldier.photo+=\n                          \"?type=f&w=60&h=80&faceopt=true&ttype=jpg'>\"+=\"</a>\"\n                  }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("<input type='file' name='photo'></td>\n");
      out.write("      </tr>\n");
      out.write("      <tr>\n");
      out.write("        <th style='width:120px;'>번호</th>\n");
      out.write("        <td style='width:300px;'><input type='text' name='no' value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.no}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(" readonly></td>\n");
      out.write("      </tr>\n");
      out.write("      <tr>\n");
      out.write("        <th>이름</th>\n");
      out.write("        <td><input type='text' name='name' value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.name}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("></td>\n");
      out.write("      </tr>\n");
      out.write("      <tr>\n");
      out.write("        <th>군번</th>\n");
      out.write("        <td><input type='text' name='militaryNumber' value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.militaryNumber}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("></td>\n");
      out.write("      </tr>\n");
      out.write("\n");
      out.write("      <tr>\n");
      out.write("        <th>나이</th>\n");
      out.write("        <td><input type='text' name='age' value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.age}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("></td>\n");
      out.write("      </tr>\n");
      out.write("      <tr>\n");
      out.write("        <th>암호</th>\n");
      out.write("        <td><input type='password' name='password'></td>\n");
      out.write("      </tr>\n");
      out.write("      <tr>\n");
      out.write("        <th>입대일</th>\n");
      out.write("        <td><input type='date' name='enlistmentDate' value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.enlistmentDate}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("></td>\n");
      out.write("      </tr>\n");
      out.write("      <tr>\n");
      out.write("        <th>전역일</th>\n");
      out.write("        <td><input type='date' name='dischargeDate' value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.dischargeDate}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("></td>\n");
      out.write("      </tr>\n");
      out.write("      <tr>\n");
      out.write("        <th>D-Day</th>\n");
      out.write("        <td>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.dDay}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("</td>\n");
      out.write("      </tr>\n");
      out.write("\n");
      out.write("      <tr>\n");
      out.write("        <th>계급</th>\n");
      out.write("        <td><select name='rank'>\n");
      out.write("          <option value='이병' ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.rank eq \"이병\"  ? \"selected\" : \"\"}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(">이병</option>\n");
      out.write("          <option value='일병' ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.rank eq \"일병\"  ? \"selected\" : \"\"}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(">일병</option>\n");
      out.write("          <option value='상병' ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.rank eq \"상병\"  ? \"selected\" : \"\"}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(">상병</option>\n");
      out.write("          <option value='병장' ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${soldier.rank eq \"병장\"  ? \"selected\" : \"\"}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(">병장</option>\n");
      out.write("        </select></td>\n");
      out.write("      </tr>\n");
      out.write("    </table>\n");
      out.write("\n");
      out.write("    <div>\n");
      out.write("      <button class='btn'>변경</button>\n");
      out.write("      <button class='btn' type='reset'>초기화</button>\n");
      out.write("      <a href='/soldier/delete.jsp?no=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${param.no}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("' class='btn'>삭제</a>\n");
      out.write("      <a href='list.jsp' class='btn'>목록</a>\n");
      out.write("    </div>\n");
      out.write("  </form>\n");
      out.write("  ");
 } 
      out.write("</div>\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../footer.jsp", out, false);
      out.write("<link rel=\"stylesheet\" href=\"../footer.css\">\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}