export class LoginState {
	constructor (
		public userId: number = 0,
		public success: boolean = false,
		public message: String = ""
	) {}
}