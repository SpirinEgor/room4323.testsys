import { Component, OnInit } 	from '@angular/core'

import { TasksService }			from './tasks.service'

@Component({
	selector: 'tasks',
	templateUrl: './tasks.html'
})
export class TasksComponent implements OnInit {

	allTasks: Task[] = []

	constructor(private tasksService: TasksService) { }

	getAllTasks() {
		this.tasksService.getAllTasks()
			.then(allTasks => {
				for (let task of allTasks['tasks']) {
					let currentTask: Task = {
						id: task['id'],
						name: task['name']
					}
				this.allTasks.push(currentTask)
			}
		})
	}

	ngOnInit() {
		this.getAllTasks()
	}
}

class Task {
	id: number
	name: string
}
