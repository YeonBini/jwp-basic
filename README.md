### 1번 문제 : 로컬 개발 환경에 톰캣 서버를 시작하면 서블릿 컨테이너의 초기화 과정이 진행된다. 현재 소스코드에서 초기화 되는 과정에 대해 설명하라 .

> 내 답변 

톰캣은 Servelt Container의 역할을 담당
1. 톰캣에서 웹 애플리케이션을 인식할 때 @WebServlet 어노테이션 중 loadOnStartUp 조건에 해당하는 
Servlet은 해당 시점에 미리 초기화 작업을 실행한다. 
    - 이유 : 보통의 서블릿은 인식 시점에 Thread를 생성하여 초기화 작업을 진행하지만, 
    이는 초기화에 시간이 소요되기 때문에 DispatcherServlet의 경우 톰캣의 인식 시점에 초기화를 할 수 있도록 설정한다. 
    - loadOnStartUp 조건에서 0에 가까운 양수일 수록 먼저 초기화 실행이 되며, 
    음수일 때는 실행되지 않는다. 
2. 톰캣이 실행 시 ServletContextListener 인터페이스를 구현한 ContextLoaderListener는 
contextInitailized()를 호출하여 초기화 작업을 진행한다. 
    - 이번 프로젝트에서는 데이터베이스 초기 작업을 이를 통해 수행하였다. 

> 책 답
- 서블릿 컨테이너는 웹 애플리케이션의 상태를 관리하는 ServletContext를 생성한다. 
- ServletContext가 초기화되면 컨텍스트의 초기화 이벤트가 발생한다. 
- 등록된 ServletContextListener의 콜백 메소드가 호출된다. 
이 문제에서는 ContextLoaderListener의 contextInitialized() 메소드가 호출된다. 
- jwp.sql 파일에서 SQL 문을 실행해 데이터베이스 테이블을 초기화한다. 
- 서블릿 컨테이너는 클라이언트로부터의 최초 요청 시(또는 컨테이너에 서블릿 인스턴스를 생성하도록 미리 설정을 한다면 최초 요청 전에)
 DispatcherServlet 인스턴스를 생성한다.(생성자 호출) 
 이에 대한 설정은 @WebServlet의 loadOnStartUp 속성으로 설정할 수 있다. 
 이 문제에서는 loadOnStartUp 속성이 설정되어 있기 때문에 서블릿 컨테이너가 시작하는 시점에 인스턴스를 생성한다. 
- DispatcherServlet 인스턴스의 init() 메소드를 호출해 초기화 작업을 진행한다. 
- init() 메소드 안에서 RequestMapping을 생성한다. 
- RequestMapping 인스턴스의 initMappint() 메소드를 호출한다.
 initMapping() 메소드에서는 요청 url과 Controller 인스턴스를 매핑시킨다. 
  
### 2. 메인 페이지에서 질문 목록이 보이기 까지 소스코드의 호출 순서 및 흐름을 설명하라 

> 내 답변 

순서 : 
1. DispatcherServlet의 Service에서 요청 URL에 대한 Controller를 찾기위해 RequestMapping을 호출
2. RequestMapping에서 "/"으로 mapping된 HomeController를 리턴 해줌 
3. DispatcherServlet에서는 리턴받은 HomeController의 model과 view 정보를 알아내기 위해 Controller의 execute() 메소드를 호출한다. 
4. HomeController에서는 질문 목록 조회를 위해 QuestionDao를 생성 후 findAll() 메소드를 호출한다. 
5. QuestionDao 클래스의 findAll()에서는 JdbcTemplate 클래스를 생성하고, ConnectionManager에서 생성된 DataSource에서 질문 리스트를 받아온다. 
6. HomeController에서는 리턴받은 질문리스트를 ModelAndView에 세팅을 해주고 리턴을한다. 
    6.1 HomeController의 경우 AbstractController를 상속하고 있으며, AbstractController의 jspView(), jsonView() 메소드 중 
    jspView() 메소드를 활용하여 이동해야하는 페이지를 설정해준다. 
7. DispatcherServlet은 컨트롤러로부터 리턴받은 ModelAndView 객체에서 View를 확인하고, View의 render() 메소드를 통해 
 request에 질문 리스트를 세팅하고, 페이지를 이동한다.(home.jsp)
8. home.jsp에서는 request에 담긴 질문들을 jstl 태그를 활용하여 화면에 보여준다. 

> 책 답변 

1. localhost:8080으로 접근하면 요청을 처리할 서블릿에 접근하기 위해 먼저 ResourceFilter와 CharacterEncodingFilter의 
 doFilter() 메소드가 실행된다. ResourceFilter의 경우 해당 요청이 정적 자원(CSS, 자바스크립트, 이미지) 요청이 아니기 때문에 서블릿으로 요청을 위임한다. 
2. 요청 처리는 "/"으로 매핑되어 있는 DispatcherServlet이므로 이 서블릿의 service() 메소드가 실행된다. 
3. service() 메소드는 요청받은 URL을 분석해 해당 Controller 객체를 RequestMapping에서 가져온다. 
 요청 url은 "/"이며, 이와 연결되어 있는 HomeController가 반환된다. 
4. service() 메소드는 HomeController의 execute() 메소드에게 작업을 위임한다. 
 요청에 대한 실질적인 작업은 HomeController의 execute()가 실행한다. execute() 메소드의 반환값은 ModelAndView이다. 
5. service() 메소드는 반환받은 ModelAndView의 모델 데이터를 뷰의 render() 메소드에 전달한다. 
 이 요청에서 View는 JspView이다. JspView는 render() 메소드로 전달된 모델 데이터를 home.jsp에 전달해 html을 생성하고, 응답한다. 
 
 ### 7번 문제 : ShowController는 멀티스레드 상황에서 문제가 발생할 가능성이 있는 코드이다. 멀티스레드 상황에서 문제가 발생하지 않도록 수정한다. 
 