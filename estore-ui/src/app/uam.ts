export var UAM = {
	setToken(token: string) {
		sessionStorage.setItem("token", token);
	},
	
	getToken(): string {
		if (sessionStorage.getItem("token") == null) {
			return "";
		} else {
			return sessionStorage.getItem("token")!;
		}
	},
	
	loggedIn(): boolean {
		return sessionStorage.getItem("token") != null;
	},
	
	logout(): void {
		sessionStorage.removeItem("token");
		sessionStorage.removeItem("admin");
		window.location.assign(window.location.href.split('/')[0]);
	},
	
	isAdmin(): boolean {
		if (sessionStorage.getItem("admin") == null) {
			return false
		} else {
			return sessionStorage.getItem("admin")!== "yes";
		}
	},
	
	setAdmin(v: boolean): void {
		sessionStorage.setItem("admin", v?"yes":"no");
	}
}