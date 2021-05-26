import com.rs.core.exception.ZACheckException;
import com.rs.core.za.check.annotation.ZA_IsAccount;
import com.rs.core.za.check.annotation.ZA_IsPassword;
import com.rs.core.za.check.annotation.ZA_IsPhone;
import com.rs.core.za.check.util.ZAUtil;

public class Demo {

	@ZA_IsAccount
	private String account;

	@ZA_IsPassword(minlength = 8, maxlength = 12, msg = "密码不能为空且长度需在8-12位内")
	private String password;

	@ZA_IsPhone
	private String phone;

	public Demo(String account, String password, String phone) {
		super();
		this.account = account;
		this.password = password;
		this.phone = phone;
	}

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
}
