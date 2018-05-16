import { Injectable }               from '@angular/core'
import { Http }                     from '@angular/http'

import { successful, serverError }  from '../common/response'

import 'rxjs/add/operator/toPromise';

@Injectable()
export class LoginService {

	constructor(private $http: Http) { }

	private handleError(error: Response) {
		if (error.status === 401) {
			alert('Wrong password or username')
		} else {
			alert(serverError + '\n' + error)
		}
	}

	authenticate(username: String, password: String) {
		const body = {
			'username': username,
			'password': password
		}
		return this.$http.post('http://localhost:8000/api/login', body)
						.toPromise()
						.then(
							response => {
								localStorage.setItem('token', response.headers.get('Authorization'))
								if (response.json().status === successful) {
									return response.json().result
								} else {
									alert(serverError)
								}
							}
						)
						.catch(this.handleError)
	}

}
