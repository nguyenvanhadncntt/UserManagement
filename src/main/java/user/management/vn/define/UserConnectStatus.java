package user.management.vn.define;

public enum UserConnectStatus {
		OFFLINE(0), ONLINE(1), BUSY(2);
		private int value;
		
		UserConnectStatus(int value){
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
}


