import { Injectable }               from '@angular/core'
import { Http }                     from '@angular/http'

import { successful, serverError }  from '../common/response'

import 'rxjs/add/operator/toPromise';

@Injectable()
export class ProblemService {

	constructor(private $http: Http) { }

	private handleError(error: any) {
		alert(serverError)
	}

	getProblem(id: string) {
		return this.$http.get('http://localhost:8000/api/tasks/' + id)
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

}
