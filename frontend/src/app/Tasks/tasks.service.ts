import { Injectable }               from '@angular/core'
import { Http, Headers }			from '@angular/http'
import { Router }					from '@angular/router'

import { successful, serverError }  from '../common/response'

@Injectable()
export class TasksService {

	constructor(private $http: Http,
				private router: Router) { }

	private handleError(e: Response) {
		if (e.status === 401) {
			this.router.navigate(['/'])
		} else {
			alert(serverError)
		}
	}

	getAllTasks() {
		const headers = new Headers({
			'Authorization': `Bearer ${localStorage.getItem('token')}`
		})
		return this.$http.get('http://localhost:8000/api/tasks', { headers: headers })
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
