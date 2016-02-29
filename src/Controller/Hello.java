package Controller;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tommy on 2016/2/27.
 */

public class Hello extends HttpServlet{

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
//        doPost(request, response);
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("utf-8");
        Gson gson = new Gson();

        String userName = request.getParameter("username");
        userName= userName==null?"":URLDecoder.decode(userName, "UTF-8");

        String content = request.getParameter("content");
        content=content==null?"":URLDecoder.decode(content, "UTF-8");

        System.out.println("username:"+userName);
        System.out.println("content:"+content);
        Map<String , Object> map = new HashMap<>();
        map.put("username",userName);
        map.put("content",content);

        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(map));
        out.flush();
        out.close();
    }

    public void destroy()
    {
    }
}
