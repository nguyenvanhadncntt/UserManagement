package user.management.vn.query;

public class GroupQueryCondition {
	public static String conditionSearchUserInGroup(Long groupId) {
		StringBuilder builder = new StringBuilder(
				" id in (select distinct ug.user.id from UserGroup ug where ug.group.id=");
		builder.append(groupId);
		builder.append(")");
		return builder.toString();
	}

}
