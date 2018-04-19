import { Component, OnInit }		from '@angular/core'
import { ActivatedRoute, ParamMap }	from '@angular/router'

import { ProblemService }			from './problem.service'

import 'rxjs/add/operator/switchMap'

@Component({
	selector: 'problem',
	templateUrl: './problem.html'
})
export class ProblemComponent implements OnInit {

	id = 0
	name = ''
	statement = ''

	constructor(
		private problemService: ProblemService,
		private route: ActivatedRoute
	) {}

	ngOnInit(): void {
		this.route.paramMap
			.switchMap((params: ParamMap) =>
				this.problemService.getProblem(params.get('id')))
			.subscribe(
				problem => {
					this.id = problem['id']
					this.name = problem['name']
					this.statement = problem['statement']
				}
			)
	}

}
