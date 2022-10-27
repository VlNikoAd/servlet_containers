package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.exception.NotFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";
    private static final String REGEX = "/api/posts/\\d+";
    private static final String validPath = "/api/posts";
    private static long id = 0;


    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("ru.netology");
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            if (path.matches(REGEX)) {
                id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
            }

            if (method.equals(GET) && path.equals(validPath)) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET) && path.matches(REGEX)) {
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST) && path.equals(validPath)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE) && path.matches(REGEX)) {
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (NotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_CONFLICT);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

