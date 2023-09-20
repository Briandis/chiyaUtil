package chiya.core.base.constant;

/**
 * 通用常量
 * 
 * @author brian
 */
public class ChiyaConstant {

	/** chiya */
	public static final String CHIYA = "chiya";

	/** 使用标记 */
	public class UseFlag {
		/** 使用 */
		public static final int IS_USE = 1;
		/** 不使用 */
		public static final int NOT_USE = 0;
	}

	/** 删除标记 */
	public class DeleteFlag {
		/** 已删除 */
		public static final int IS_DELETE = 1;
		/** 未删除 */
		public static final int NOT_DELETE = 0;
	}

	/** 展示标记 */
	public class ShowFlag {
		/** 展示 */
		public static final int IS_SHOW = 1;
		/** 不展示 */
		public static final int NOT_SHOW = 0;
	}

	/** 性别 */
	public class Sex {
		/** 女性 */
		public static final int WOMAN = 0;
		/** 男性 */
		public static final int MAN = 1;
		/** 未知 */
		public static final int NUKNOWN = 2;
	}

	/** 认证 */
	public class Check {
		/** 未认证 */
		public static final int NO = 0;
		/** 以认证 */
		public static final int YES = 1;
	}

	/** 用户状态 */
	public class UserState {
		/** 正常 */
		public static final int NORMAL = 0;
		/** 封禁 */
		public static final int BAN = 1;
	}

	/** 设备类型 */
	public class DeviceType {
		/** 其他 */
		public static final int OTHER = 0;
		/** PC端 */
		public static final int PC = 1;
		/** 移动端 */
		public static final int WAP = 2;
	}

	/** 审核 */
	public class Review {
		/** 正常 */
		public static final int NORMAL = 0;
		/** 审核中 */
		public static final int REVIEW = 1;
		/** 驳回 */
		public static final int FAIL = 2;
		/** 通过 */
		public static final int PASS = 3;

	}
}
