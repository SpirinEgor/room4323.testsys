import { Component, OnInit }		from '@angular/core'
import { ActivatedRoute, ParamMap }	from '@angular/router'

import { SubmitService }			from './submit.service'

import 'rxjs/add/operator/switchMap'

@Component({
	selector: 'submit',
	templateUrl: './submit.html'
})
export class SubmitComponent implements OnInit {

	problemName = ''
	problemId = 0
	participantCode = ''
	submissions: Submit[] = []

	constructor(
		private submitService: SubmitService,
		private route: ActivatedRoute
	) {}

	ngOnInit(): void {
		this.route.paramMap
			.switchMap((params: ParamMap) =>
				this.submitService.getStatus(params.get('id')))
			.subscribe(
				problemStatus => {
					this.problemId = problemStatus['problem']['id']
					this.problemName = problemStatus['problem']['name']
					for (let submit of problemStatus['submissions']) {
						let currentSubmit: Submit = {
							id: submit['id'],
							status: submit['status'],
							submTime: submit['submTime'],
							verdict: submit['verdict'],
							testId: submit['testId'],
							comment: submit['comment']
						}
						this.submissions.push(currentSubmit)
					}
				}
			)
	}

	update(newCode: string) {
		this.participantCode = newCode
	}

	submitSolution() {
		this.submitService.submit(this.problemId, this.participantCode)
			.then(
				response => {}
			)
	}

}

class Submit {
	id: number
	status: string
	submTime: string
	verdict: string
	testId: number
	comment: string
}
