import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from "rxjs";
import { Product } from './product/product.model';
import { Config } from './config';
import { LoginResponse } from './login/login-response.model';
import { LoginRequest } from './login/login-request.model';
import { UAM } from './uam';

@Injectable()
export class AppService {

	loggedInUser: boolean = false;
	loggedInAdmin: boolean = false;

	loginInfo = {
		username:  "",
		password: ""
	};

	private subject = new Subject<any>();
	
	constructor(private http: HttpClient) {
	}

	createProduct(product: Product): Observable<any> {
		return this.http.post(`${Config.api_base}/products`, product);
	}

	getProduct(id: number): Observable<any> {
		return this.http.get<Product>(`${Config.api_base}/products/${id}`);
	}

	getInventory(): Observable<any> {
		return this.http.get<Product[]>(`${Config.api_base}/products`);
	}

	search(query: String): Observable<any> {
		return this.http.get<Product[]>(`${Config.api_base}/products/?name=${query}`);
	}

  	getCartItems(): Observable<any> {
  		return this.http.get<Product[]>(`${Config.api_base}/users/${UAM.getToken()}/cart`);
  	}
  	
  	addCartItem(product: Product): Observable<any> {
		return this.http.post(`${Config.api_base}/users/save/${UAM.getToken()}`, product);
	}

	removeCartItem(product: Product): Observable<any> {
		return this.http.post(`${Config.api_base}/users/remove/${UAM.getToken()}`, product);
	}
  	
  	loginUser(username: string, password: string): Observable<LoginResponse> {
		return this.http.post<LoginResponse>(`${Config.api_base}/users/login`, new LoginRequest(username, password));
	}
	
	registerUser(username: string, password: string): Observable<LoginResponse> {
		return this.http.post<LoginResponse>(`${Config.api_base}/users/register`, new LoginRequest(username, password));
	}
	
	logoutUser(): Observable<any> {
		return this.http.get(`${Config.api_base}/users/${UAM.getToken()}/logout`);
	}
	
	checkAlive(): Observable<any> {
		return this.http.get(`${Config.api_base}/users/${UAM.getToken()}/alive`);
	}
	
	editProduct(product: Product): Observable<any> {
		return this.http.put(`${Config.api_base}/products`, product);
	}
	
	deleteProduct(product: Product): Observable<any> {
		return this.http.delete(`${Config.api_base}/products/${product.id}`);
	}

	/*newBid(id: number, end: number, user: String): Observable<any> {
		return this.http.post(`${Config.api_base}/products/bid/${id}`, end);
	}

	getBid(id: number): Observable<any> {
		return this.http.get(`${Config.api_base}/products/bid/${id}`);
	}*/
}
