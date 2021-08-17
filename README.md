# **ZA的介绍**

它其实和Spring的注解差不多,是我为了减少因为接口或实体类调整造成的维护成本而整出来的玩意。

ZA内只有针对接口与实体类的注解与相关工具的包,所以**需要与工具类配合使用**。

# **作用**

1.这些注解可以代替注释(与注释不同的是会被编译)

2.其他工具可以通过读取这些注解实现相关功能,比如生成调试接口的html页面(↓DEMO↓里面有生成API文档例子与源码)



# **目录**

* **1.DEMO**
* **2.特点**
* **3.注解介绍及使用方法**
* **4.工具类介绍**
* **5.集成方法**

# 1. [DEMO](https://github.com/342535324/ZA_DEMO) 

# **2.特点**

无依赖，对线上项目结构不影响，仅作为辅助工具使用

# **3.注解介绍及使用方法**

3.1.针对接口的注解
>
| 注解名称                    | 作用对象   | 注解说明                                                                                                                                                |
|  | - | - |
| @ZA_InterfaceNotes          | 接口的方法 | 接口注解,用于标注这个接口的名称,接口类型,返回值(返回值可以用 returnContent 属性作为文本说明,也可以用 returnObjectUrl 属性填写实体类 URL),时间(用于排序) |
| @ZA_InterfaceNotesParameter | 接口的方法 | 接口参数注解,每个注解对应一个接口的参数,包含这个参数的名称,类型,说明,默认值                                                                             |

在 SpringMVC 下的用法举例:
```
@Controller
@RequestMapping("/main")
public class MainController {
	@ZA_InterfaceNotes(name = "验证码登陆 请用POST请求 每天同ip 错误超过5次将被禁止当天登录 ", time = "2021-5-27 12:52:34") // 接口注解
	@ZA_InterfaceNotesParameter(name = "phone", type = ZAParameter.TYPE_String, describe = "手机号码") // 参数注解
	@ZA_InterfaceNotesParameter(name = "code", type = ZAParameter.TYPE_String, describe = "短信验证码") // 参数注解
	@RequestMapping("/login.app")
	@ResponseBody
	public String login(HttpServletRequest request, String phone, String code) {
		try {
			// 业务代码
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{code:1}";
	}
}
```

3.2针对实体类属性校验的注解
>
| 注解名称       | 作用对象   | 注解说明                                             |
| -- | - | - |
| @ZA_IsAccount  | 实体类属性 | 账号校验,包含错误提示信息,最小长度,最大长度 3 个属性 |
| @ZA_IsEMail    | 实体类属性 | 邮箱校验,包含错误提示信息                            |
| @ZA_IsIdCard   | 实体类属性 | 身份证校验,包含错误提示信息                          |
| @ZA_IsInt      | 实体类属性 | 整数校验,包含错误提示信息                            |
| @ZA_IsPassword | 实体类属性 | 密码校验,包含错误提示信息,最小长度,最大长度 3 个属性 |
| @ZA_IsPhone    | 实体类属性 | 手机号码校验,包含错误提示信息                        |
| @ZA_NotNull    | 实体类属性 | 非空校验,包含错误提示信息                            |


实体类属性校验的 DEMO:
>
```
/**
 * 实体类注解DEMO 实体类的校验注解需要搭配ZAUtil.checkModel使用
 */
public class Demo {
	@ZA_IsAccount // 账号校验注解
	private String account;
	@ZA_IsPassword(minlength = 8, maxlength = 12, msg = "密码不能为空且长度需在8-12位内") // 密码校验注解
	private String password;
	@ZA_IsPhone // 手机号码校验注解
	private String phone;

	public static void main(String[] args) {
		// 实体类校验测试
		Demo demo = new Demo("Abc", "123", "123");
		try {
			// 校验属性,校验不通过会抛出异常
			ZAUtil.checkModel(demo);
		} catch (ZACheckException e) {
			// 输出错误 密码不能为空且长度需在8-12位内
			e.printStackTrace();
		}
	}

	public Demo(String account, String password, String phone) {
		super();
		this.account = account;
		this.password = password;
		this.phone = phone;
	}
}
```

# **4.工具类介绍**

### ZAUtil
###### 实体类相关工具类：
| 方法名称   | 作用                                   |
| - | -- |
| checkModel | 根据自定义注解，校验实体类中的特定属性 |
| initObject | 读取类所有属性 将其基本属性进行初始化赋值(不会覆盖已有的值,仅针值为null的属性进行操作) |
| ObjectContrast | 读取2个obj的所有属性 将属性的值转成字符串,然后进行对比 如果所有属性一致则返回true |

### ZACreateHtml
###### 通过接口注解(@ZA_InterfaceNotes,@ZA_InterfaceNotesParameter)生成HTML的工具类：
| 方法名称   | 作用                                   |
| - | -- |
| createInterfaceHTML | 根据传入的接口对象数组生成接口文档 |

### ZACreateParameterUtil
##### 是接口文档的参数生成工具类,它会在ZAInterfaceNotesEntity对象被初始化的时候调用,根据参数类型和名称生成默认值
###### 如果需要自定义参数生成规则可以在ZACreateParameterUtil里面新增方法,如:
```
	public static String createName() {
		int r = (int) (Math.random() * 5);
		return new String[] { "张三", "李四", "王五", "赵六", "孙七", "刘八" }[r];
	}
```
###### 并在 **AutoRecognition** 自动识别参数生成值规则类中新增规则
```
public class AutoRecognition {
	/**
	 * 参数名生成参数 key为参数名,value为ZACreateParameterUtil的生成方法
	 */
	public static final Map<String, StringParameterNameAutoRecognitionMap = new HashMap<String, String>() {
		{
			put("account", "createAccount");
			put("password", "createPassword");
			put("phone", "createPhone");
			put("id", "createId");
			put("key", "createKey");
			// --
			put("name", "createName"); //这条是新增的规则,意思是名为name的参数,使用ZACreateParameterUtil的createName方法生成默认值,那么结果就是"张三", "李四", "王五", "赵六", "孙七"中的一个。
		}
	};
```

# **5.集成方法**

引入dist的jar包,或引入src的源码到本地项目
