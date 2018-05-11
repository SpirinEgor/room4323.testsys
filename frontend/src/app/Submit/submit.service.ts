import { Injectable }               from '@angular/core'
import { Http }                     from '@angular/http'

import { successful, error, serverError }  from '../common/response'

import 'rxjs/add/operator/toPromise';

@Injectable()
export class SubmitService {

	constructor(private $http: Http) { }

	private handleError(e: any) {
		alert(serverError)
	}

	getStatus(id: string) {
		return this.$http.get('http://localhost:8000/api/tasks/' + id + '/status')
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

	submit(prId: number, code: string) {
		const body = {
			'code': code
		}
		return this.$http.post('http://localhost:8000/api/tasks/' + prId + '/submit', body)
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
