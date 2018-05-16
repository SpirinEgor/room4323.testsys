import { Component }	from '@angular/core'
import { Router } 		from '@angular/router'

import { LoginService } from './login.service'

@Component({
	selector: 'login',
	templateUrl: './login.html'
})
export class LoginComponent {

	username: String = ''
	password: String = ''

	constructor(
		private loginService: LoginService,
		private router: Router
	) {}

	login() {
		this.loginService.authenticate(this.username, this.password)
			.then(auth => {
				if (typeof auth !== 'undefined') {
					this.router.navigate(['task', 'all'])
				}
		})
	}

	update(new_username: String, new_password: String) {
		this.username = new_username
		this.password = new_password
	}

}
