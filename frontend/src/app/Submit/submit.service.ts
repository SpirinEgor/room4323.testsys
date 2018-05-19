import { Injectable }						from '@angular/core'
import { Http, Headers }					from '@angular/http'
import { Router }							from '@angular/router'

import { successful, error, serverError }	from '../common/response'

import 'rxjs/add/operator/toPromise';

@Injectable()
export class SubmitService {

	constructor(private $http: Http,
				private router: Router) { }

	private handleError(e: Response) {
		if (e.status === 401) {
			this.router.navigate(['/'])
		} else {
			alert(serverError)
		}
	}

	getStatus(id: string) {
		const headers = new Headers({
			'Authorization': `Bearer ${localStorage.getItem('token')}`
		})
		return this.$http.get(`http://localhost:8000/api/tasks/${id}/status`, { headers: headers })
						.toPromise()
						.then(
							response => {
								if (response.json().status === successful) {
									return response.json().result
								} else {
									alert(serverError)
								}
							}
						)
						.catch(this.handleError)
	}

	getLanguages() {
		const headers = new Headers({
			'Authorization': `Bearer ${localStorage.getItem('token')}`
		})
		return this.$http.get(`http://localhost:8000/api/languages`, { headers: headers })
						.toPromise()
						.then(
							response => {
								if (response.json().status === successful) {
									return response.json().result
								} else {
									alert(serverError)
								}
							}
						)
						.catch(this.handleError)
	}

	submit(prId: number, code: string, language: string) {
		const body = {
			'code': code,
			'language': language
		}
		const headers = new Headers({
			'Authorization': `Bearer ${localStorage.getItem('token')}`
		})
		return this.$http.post(`http://localhost:8000/api/tasks/${prId}/submit`, body, { headers: headers })
			.toPromise()
			.then(
				response => {
					if (response.json().status === error) {
						return response.json().result
					}
				}
			)
			.catch(this.handleError)
	}

}
