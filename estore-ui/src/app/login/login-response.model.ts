export class LoginResponse {
	constructor (
		public token: string,
		public success: boolean,
		public message: string,
		public admin: boolean
	) {
		this.token = token;
		this.success = success;
		this.message = message;
		this.admin = admin;
	}
}