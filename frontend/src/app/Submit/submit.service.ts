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
		return this.$http.get('http://localhost:8080/temporary_data/status.json')
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

	submit(id: number, participantCode: string) {
		const body = {
			'id': id,
			'participantCode': participantCode
		}
		return this.$http.post('http://localhost:8080', body)
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
