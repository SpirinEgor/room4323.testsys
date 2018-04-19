import { NgModule } 		from '@angular/core'
import { BrowserModule }	from '@angular/platform-browser'
import { FormsModule }		from '@angular/forms'
import { HttpModule }		from '@angular/http'

import { AppRoutingModule }	from './app-routing.module'

import { MainComponent}		from './Main/main.component'
import { LoginComponent }	from './Login/login.component'
import { TasksComponent }	from './Tasks/tasks.component'
import { ProblemComponent }	from './Problem/problem.component'

import { TasksService }		from './Tasks/tasks.service'
import { ProblemService }	from './Problem/problem.service'

@NgModule({
	imports: [
		BrowserModule,
		FormsModule,
		HttpModule,
		AppRoutingModule,
	],
	declarations: [
		MainComponent,
		LoginComponent,
		TasksComponent,
		ProblemComponent
	],
	bootstrap: [ MainComponent ],
	providers: [
		TasksService,
		ProblemService
	]
})
export class AppModule { }
