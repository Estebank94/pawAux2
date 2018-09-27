package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/403")
    public ModelAndView forbidden(){
        return new ModelAndView("403");
    }


    @RequestMapping("/404")
//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView error404(){
        return new ModelAndView("404");
    }
    //TODO: Filtro para manejar exepciones un internal server

////    https://www.baeldung.com/custom-error-page-spring-mvc
//    @RequestMapping(value = "errors", method = RequestMethod.GET)
//    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
//
//        ModelAndView errorPage = new ModelAndView("errors");
//        String errorMsg = "";
//        int httpErrorCode = getErrorCode(httpRequest);
//
//        switch (httpErrorCode) {
//            case 400: {
//                errorMsg = "Http Error Code: 400. Bad Request";
//                break;
//            }
//            case 401: {
//                errorMsg = "Http Error Code: 401. Unauthorized";
//                break;
//            }
//            case 404: {
//                errorMsg = "Http Error Code: 404. Resource not found";
//                break;
//            }
//            case 500: {
//                errorMsg = "Http Error Code: 500. Internal Server Error";
//                break;
//            }
//        }
//        errorPage.addObject("errorMsg", errorMsg);
//        return errorPage;
//    }
//
//    private int getErrorCode(HttpServletRequest httpRequest) {
//        return (Integer) httpRequest
//                .getAttribute("javax.servlet.error.status_code");
//    }
}
