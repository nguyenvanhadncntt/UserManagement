package user.management.vn.query;

public class RoleQueryCondition {
	public static String conditionSearchGroupInRole(Long roleId) {
		StringBuilder builder = new StringBuilder(
				" id in (select distinct gr.group.id from GroupRole gr where gr.role.id=");
		builder.append(roleId);
		builder.append(")");
		return builder.toString();
	}
}
