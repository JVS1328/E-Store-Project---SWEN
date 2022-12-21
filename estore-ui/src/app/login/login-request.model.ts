export class LoginRequest {
	constructor (
		public username: string,
		public password: string
	) {
		this.username = username;
		this.password = password;
	}
}