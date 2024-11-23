package com.epam.rd.servlets.exspressioncalc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/calc")
public class GetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String expression = req.getParameter("expression");
        Map<String, Integer> variables = new HashMap<>();

        for (String paramName : req.getParameterMap().keySet()) {
            if (paramName.equals("expression"))
                continue;

            String value = req.getParameter(paramName);

            try {
                variables.put(paramName, Integer.parseInt(value));
            } catch (NumberFormatException e) {
                if (variables.containsKey(value)) {
                    variables.put(paramName, variables.get(value));
                } else {
                    resp.getWriter().write("Undefined variable " + value);
                    return;
                }
            }
        }

        ExpressionParser expressionParser = new ExpressionParser();
        int result = expressionParser.evaluateExpression(expression, variables);
        resp.getWriter().write(String.valueOf(result));
    }


}
