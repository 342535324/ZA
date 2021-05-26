# ZA

注解拓展包(JAVA),新增一些针对接口与实体类的注解,与工具类配合使用可以实现自动生成接口文档,自动校验实体类等功能。

---
**注解介绍**
---
---
> 1.针对接口的注解
>
> | 注解名称                    | 作用对象   | 注解说明                                                                                                                                                |
> | --------------------------- | ---------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
> | @ZA_InterfaceNotes          | 接口的方法 | 接口注解,用于标注这个接口的名称,接口类型,返回值(返回值可以用 returnContent 属性作为文本说明,也可以用 returnObjectUrl 属性填写实体类 URL),时间(用于排序) |
> | @ZA_InterfaceNotesParameter | 接口的方法 | 接口参数注解,每个注解对应一个接口的参数,包含这个参数的名称,类型,说明,默认值                                                                             |

> > 接口类型预设常量
> >
> > | 接口类型                              | 描述                 |
> > | ------------------------------------- | -------------------- |
> > | ZAParameter.InterfaceTYPE_ADD         | 新增数据的接口       |
> > | ZAParameter.InterfaceTYPE_MODIFY      | 修改数据的接口       |
> > | ZAParameter.InterfaceTYPE_SELECT      | 查询数据的接口       |
> > | ZAParameter.InterfaceTYPE_DELETE      | 删除数据的接口       |
> > | ZAParameter.InterfaceTYPE_ADDorDelete | 新增或删除数据的接口 |
> > | ZAParameter.InterfaceTYPE_LOGIN       | 登录的接口           |

> 在 SpringMVC 下的用法举例:

> ```
> @Controller
> @RequestMapping("/main")
> public class MainController{
> 	@Autowired
> 	private IBaseService baseService;
>
> 	@ZA_InterfaceNotes(name = "验证码登陆 请用POST请求 每天同ip 错误超过5次将被禁止当天登录 ", type = ZAParameter.InterfaceTYPE_LOGIN, returnContent = "返回登录凭证", time = "2021-5-26 11:34:43")
> 	@ZA_InterfaceNotesParameter(name = "phone", type = ZAParameter.TYPE_String, describe = "账号")
> 	@ZA_InterfaceNotesParameter(name = "code", type = ZAParameter.TYPE_String, describe = "短信验证码")
> 	// 下面是原版接口应有注解
> 	@RequestMapping("/login.app") // 接口级定义url
> 	@ResponseBody // 表示这是一个AJAX接口,不需要生成JSP页面
> 	public String login(String phone, String code, HttpServletRequest request) throws Exception {
> 	// 基本登录接口，login方法的参数就是接口传参
> 		try {
> 		// 接口处理逻辑 略
> 		} catch (Exception e) {
> 			e.printStackTrace();
> 		}
> 		return null;
> 	}
>
> }
> ```

> 2针对实体类属性校验的注解
>
> | 注解名称       | 作用对象   | 注解说明                                             |
> | -------------- | ---------- | ---------------------------------------------------- |
> | @ZA_IsAccount  | 实体类属性 | 账号校验,包含错误提示信息,最小长度,最大长度 3 个属性 |
> | @ZA_IsEMail    | 实体类属性 | 邮箱校验,包含错误提示信息                            |
> | @ZA_IsIdCard   | 实体类属性 | 身份证校验,包含错误提示信息                          |
> | @ZA_IsInt      | 实体类属性 | 整数校验,包含错误提示信息                            |
> | @ZA_IsPassword | 实体类属性 | 密码校验,包含错误提示信息,最小长度,最大长度 3 个属性 |
> | @ZA_IsPhone    | 实体类属性 | 手机号码校验,包含错误提示信息                        |
> | @ZA_NotNull    | 实体类属性 | 非空校验,包含错误提示信息                            |

> 实体类属性校验的 DEMO:
>
> ```
> public class Demo {
> 	@ZA_IsAccount
> 	private String account;
>
> 	@ZA_IsPassword(minlength = 8, maxlength = 12, msg = "密码不能为空且长度需在8-12位内")
> 	private String password;
>
> 	@ZA_IsPhone
> 	private String phone;
>
> 	public Demo(String account, String password, String phone) {
> 		super();
> 		this.account = account;
> 		this.password = password;
> 		this.phone = phone;
> 	}
>
> 	public static void main(String[] args) {
> 		// 实体类校验测试
> 		Demo demo = new Demo("Abc", "123", "123");
> 		try {
> 			// 校验属性,校验不通过会抛出异常
> 			ZAUtil.checkModel(demo);
> 		} catch (ZACheckException e) {
> 			// 输出错误 密码不能为空且长度需在8-12位内
>   		//ZACheckException是继承于Exception的一个类,用于区分异常是校验引发的异常
>     		e.printStackTrace();
>     	}
>     }
>}
>
> ```


---
**工具类介绍**
---
---
**ZAUtil** 实体类相关工具类：

 | 方法名称   | 作用                                   |
| ---------- | -------------------------------------- |
| checkModel | 根据自定义注解，校验实体类中的特定属性 |
 | initObject | 读取类所有属性 将其基本属性进行初始化赋值(不会覆盖已有的值,仅针值为null的属性进行操作) |
| ObjectContrast | 读取2个obj的所有属性 将属性的值转成字符串,然后进行对比 如果所有属性一致则返回true |

---

**ZACreateHtml** 通过接口注解(@ZA_InterfaceNotes,@ZA_InterfaceNotesParameter)生成HTML的工具类：

 | 方法名称   | 作用                                   |
| ---------- | -------------------------------------- |
| createInterfaceHTML | 根据传入的接口对象数组生成接口文档 |

>生成接口文档的例子(基于Spring):

> >1.创建/views/interfaceText.jsp
> > ```
> > <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
> > <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
> > <%
> > 	String path = request.getContextPath();
> > 	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
> > 			+ path + "/";
> > %>
> > <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
> > <html>
> > <head>
> > <base href="<%=basePath%>">
> > <title>接口文档(不支持https)</title>
> > </head>
> > <body><div>${requestScope.str}</div></body>
> > </html>
> > ```

> > 2.创建/views/index.jsp
> > ```
> > <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
> > <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
> > 
> > <%
> > 	String path = request.getContextPath();
> > 	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
> > 			+ path + "/";
> > %>
> > 
> > <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
> > <html>
> > <head>
> > <script src="<%=basePath%>js/jquery/jquery-3.4.1.min.js"></script>
> > <base href="<%=basePath%>">
> > <title>调试工具(不支持https)</title>
> > <meta http-equiv="pragma" content="no-cache">
> > <meta http-equiv="cache-control" content="no-cache">
> > <meta http-equiv="expires" content="0">
> > <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
> > <meta http-equiv="description" content="This is my page">
> > <script type="text/javascript">
> > 	function addParameter(self) {
> > 		var id = Math.floor(Math.random() * 1000000);
> > 		$(self).parent().parent().parent().prepend("<tr id='" + id + "'><td><input type='text' value='请输入参数名' onfocus='clean(this)'onchange='setInputName(this)' /></td> <td><input type='text' value='请输入参数值'onfocus='clean(this)'   /></td><td><input type='button' value='-' onclick='$(\"#" + id + "\").remove()'   /></td></tr>");
> > 	}
> > 
> > 	function showModel(index) {
> > 		hideModel();
> > 		$(".model").eq(index).show();
> > 	}
> > 
> > 	function hideModel() {
> > 		$(".model").hide();
> > 	}
> > 
> > 	function setInputName(self) {
> > 		var td = $(self).parent();
> > 		var next_td = td.next();
> > 		var input = next_td.children().get(0);
> > 		input.name = self.value;
> > 	}
> > 
> > 	function clean(self) {
> > 		var $self = $(self);
> > 		if ($self.val() == "请输入参数名" || $self.val() == "请输入参数值") {
> > 			$(self).val("")
> > 			$(self).unbind("focus");
> > 		}
> > 	}
> > </script>
> > </head>
> > 
> > <body onload="showModel(0)">
> > 	<div>
> > 		<label>接口调试器(默认使用测试账号test) </label>
> > 	</div>
> > 
> > 	<div>
> > 		接口：<select onchange="showModel(this.value)">
> > 			<c:forEach items="${requestScope.list}" var="item" varStatus="v">
> > 				<option value="${v.index}">${item.name}</option>
> > 			</c:forEach>
> > 		</select>
> > 	</div>
> > 	<div>----------------------------------------------</div>
> > 	<div>
> > 		<c:forEach items="${requestScope.list}" var="interfaceNotesEntity"
> > 			varStatus="v">
> > 			<div class="model" id="model_${v.index}">
> > 				<div>
> > 					请求：<input id="name" disabled="disabled"
> > 						value="${interfaceNotesEntity.name}" />
> > 				</div>
> > 				<div>
> > 					URL：<input id="url" style="width: 100%;"
> > 						value="<%=basePath%>${interfaceNotesEntity.url}" />
> > 				</div>
> > 				<div style="display: none;">
> > 					类型：<input id="type" value="${interfaceNotesEntity.type}" />
> > 				</div>
> > 				<div>----------------------------------------</div>
> > 				<form id="form_${v.index}" method="post"
> > 					enctype="multipart/form-data"
> > 					action="<%=basePath%>${interfaceNotesEntity.url}">
> > 					<table>
> > 						<tr>
> > 							<td></td>
> > 							<td><input type="button" value="点击新增参数+"
> > 								onclick="addParameter(this)" /></td>
> > 						</tr>
> > 
> > 
> > 						<tr>
> > 							<td><input type="text" value="key" class="parameterName"
> > 								disabled="disabled" /></td>
> > 
> > 							<td><input type="text"
> > 								value="SbKNNbc2x6LGjTHq2ZPvV4ghWMOEuqzDKG"
> > 								name="key" class="parameterValue" /></td>
> > 
> > 						</tr>
> > 						<tr>
> > 							<td>类型:${interfaceNotesParameter.type}</td>
> > 							<td>备注:${interfaceNotesParameter.describe}</td>
> > 
> > 						</tr>
> > 
> > 						<c:forEach items="${interfaceNotesEntity.interfaceNotesParameter}"
> > 							var="interfaceNotesParameter">
> > 
> > 							<tr>
> > 								<td><input type="text"
> > 									value="${interfaceNotesParameter.name}" class="parameterName"
> > 									disabled="disabled" /></td>
> > 
> > 								<c:if
> > 									test="${interfaceNotesParameter.typeValue!=1700 && interfaceNotesParameter.typeValue!=1800}">
> > 									<td><input type="text"
> > 										value="${interfaceNotesParameter.value}"
> > 										name="${interfaceNotesParameter.name}" onfocus="clean(this)"
> > 										class="parameterValue" /></td>
> > 								</c:if>
> > 								<c:if test="${interfaceNotesParameter.typeValue==1700}">
> > 									<td><input type="file"
> > 										name="${interfaceNotesParameter.name}" class="parameterValue" /></td>
> > 								</c:if>
> > 								<c:if test="${interfaceNotesParameter.typeValue==1800}">
> > 									<td><input type="file"
> > 										name="${interfaceNotesParameter.name}" class="parameterValue" /></td>
> > 								</c:if>
> > 								
> > 								<td><input type="button" value="删除参数" onClick="var tr = this.parentNode.parentNode;tr.parentNode.removeChild(tr.nextElementSibling);tr.parentNode.removeChild(tr);"></td>
> > 							</tr>
> > 							<tr>
> > 								<td>类型:${interfaceNotesParameter.type}</td>
> > 								<td>备注:${interfaceNotesParameter.describe}</td>
> > 
> > 							</tr>
> > 						</c:forEach>
> > 						<tr>
> > 							<td><input type="submit" value="提交"></td>
> > 						</tr>
> > 					</table>
> > 				</form>
> > 			</div>
> > 		</c:forEach>
> > 	</div>
> > </body>
> > </html>
> > ```


> >3.在要生成接口文档的接口上使用@ZA_InterfaceNotes与@ZA_InterfaceNotesParameter注解,如上面的MainController

> >4.创建一个接口用于把接口文档输出到jsp
> > ```
> > @Controller
> > @RequestMapping("/testUtil")
> > public class TestUtilController extends BaseController {
> > 	private static final String host = "http://localhost:8080/test/";//接口地址
> > 
> > 	/**
> > 	 * 输出接口文本到页面(返回HTML页面)
> > 	 */
> > 	@RequestMapping("/interfaceText.html")
> > 	public String interfaceText(HttpServletRequest request) {
> > 		try {
> > 			System.out.println(getIpAddress(request));
> > 
> > 			// 本接口主要是完成【接口反射】，【参数识别】，【页面输出】三项操作 下面开始第一步，将接口转成页面能识别的集合
> > 			List<ZAInterfaceNotesSimpleEntity> list = new ArrayList<ZAInterfaceNotesSimpleEntity>();
> > 			List<Class> controllerClasss = reflectController(request);// 通过反射加载项目中所有控制器
> > 			for (Class controller : controllerClasss) { // 遍历控制器
> > 				List<Method> interfaces = filterInterface(controller);// 该控制器内所有的接口
> > 				// 取RequestMapping注解是因为Spring框架中 接口与控制器的路径写在这个注解内
> > 				RequestMapping requestMapping = (RequestMapping) controller.getAnnotation(RequestMapping.class);
> > 				String controllerURL = requestMapping.value()[0];// 获取控制器路径
> > 				for (Method method : interfaces) {
> > 					// 取接口的RequestMapping也是为了取接口的路径
> > 					RequestMapping mapping = method.getAnnotation(RequestMapping.class);
> > 					// 封装接口对象与参数对象
> > 					ZA_InterfaceNotes interfaceNotes = method.getAnnotation(ZA_InterfaceNotes.class);
> > 					ZA_InterfaceNotesParameter[] interfaceNotesParameters = method
> > 							.getAnnotationsByType(ZA_InterfaceNotesParameter.class);
> > 					String url = controllerURL + mapping.value()[0];// 接口路径
> > 					// 把所有接口放入集合以便输出HTML
> > 					list.add(new ZAInterfaceNotesSimpleEntity(interfaceNotes, interfaceNotesParameters, url));
> > 				}
> > 			}
> > 			Collections.sort(list);// 内部实现了根据接口时间表示进行排序
> > 			request.setAttribute("str", ZACreateHtml.createInterfaceHTML(list));
> > 			return "interfaceText";
> > 		} catch (Exception e) {
> > 			return null;
> > 		}
> > 	}
> > 	/**
> > 	 * 输出单个接口的调试页面(通过接口路径) 调试页面未考虑性能优化，因此每次进入均会重新识别遍历 gotoUrl可指定路径的接口
> > 	 */
> > 	@RequestMapping("/gotoIndex.html")
> > 	public String gotoIndex(HttpServletRequest request, String interfaceUrl) {
> > 		try {
> > 			// 本接口主要是完成【接口反射】，【参数识别】这两项操作 下面开始第一步【接口反射】 将接口转成页面能识别的集合
> > 			List<ZAInterfaceNotesEntity> list = new ArrayList<ZAInterfaceNotesEntity>();// 每一个对象代表一个接口
> > 			List<Class> controllerClasss = reflectController(request); // 通过反射加载项目中所有控制器
> > 			for (Class controller : controllerClasss) { // 遍历控制器
> > 				List<Method> interfaces = filterInterface(controller);// 该控制器内所有的接口
> > 				// 取RequestMapping注解是因为Spring框架中 接口与控制器的路径写在这个注解内
> > 				RequestMapping requestMapping = (RequestMapping) controller.getAnnotation(RequestMapping.class);
> > 				String controllerURL = requestMapping.value()[0]; // 获取控制器路径
> > 				for (Method method : interfaces) {
> > 					// 取接口的RequestMapping也是为了取接口的路径
> > 					RequestMapping mapping = method.getAnnotation(RequestMapping.class);
> > 					// 封装接口对象与参数对象
> > 					ZA_InterfaceNotes interfaceNotes = method.getAnnotation(ZA_InterfaceNotes.class);
> > 					ZA_InterfaceNotesParameter[] interfaceNotesParameters = method
> > 							.getAnnotationsByType(ZA_InterfaceNotesParameter.class);
> > 					String url = controllerURL + mapping.value()[0]; // 拼接接口URL
> > 					if (StringUtils.isEmpty(interfaceUrl) || interfaceUrl.equals(url)) { // 判断是否是指定的接口
> > 						// 第二步 【参数识别】* 这里会根据参数生成默认值
> > 						list.add(new ZAInterfaceNotesEntity(interfaceNotes, interfaceNotesParameters, url));
> > 					}
> > 				}
> > 			}
> > 			request.setAttribute("list", list);
> > 			return "index";
> > 		} catch (Exception e) {
> > 			return null;
> > 		}
> > 	}
> > }
> > ```

**ZACreateParameterUtil** 是接口文档的参数生成工具类,它会在ZAInterfaceNotesEntity对象被初始化的时候调用,根据参数类型和名称生成默认值

如果需要自定义参数生成规则可以在ZACreateParameterUtil里面新增方法,如:

```
	public static String createName() {
		int r = (int) (Math.random() * 5);
		return new String[] { "张三", "李四", "王五", "赵六", "孙七", "刘八" }[r];
	}
```
并在 **AutoRecognition** 自动识别参数生成值规则类中新增规则

```
public class AutoRecognition {
	/**
	 * 参数名生成参数 key为参数名,value为ZACreateParameterUtil的生成方法
	 */
	public static final Map<String, String> ParameterNameAutoRecognitionMap = new HashMap<String, String>() {
		{
			put("account", "createAccount");
			put("password", "createPassword");
			put("phone", "createPhone");
			put("id", "createId");
			put("key", "createKey");
			// -----------------------------------------
			put("name", "createName"); //这条是新增的规则,意思是名为name的参数,使用ZACreateParameterUtil的createName方法生成默认值,那么结果就是"张三", "李四", "王五", "赵六", "孙七"中的一个。
		}
	};
```


