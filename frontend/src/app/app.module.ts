import { NgModule } 			from '@angular/core'
import { BrowserModule }		from '@angular/platform-browser'
import { FormsModule }			from '@angular/forms'
import { HttpModule }			from '@angular/http'
import { HashLocationStrategy,
			LocationStrategy }	from '@angular/common'

import { AppRoutingModule }		from './app-routing.module'

import { MainComponent}			from './Main/main.component'
import { LoginComponent }		from './Login/login.component'
import { TasksComponent }		from './Tasks/tasks.component'
import { ProblemComponent }		from './Problem/problem.component'
import { SubmitComponent }		from './Submit/submit.component'

import { LoginService }			from './Login/login.service'
import { TasksService }			from './Tasks/tasks.service'
import { ProblemService }		from './Problem/problem.service'
import { SubmitService }		from './Submit/submit.service'

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
		ProblemComponent,
		SubmitComponent
	],
	bootstrap: [ MainComponent ],
	providers: [
		LoginService,
		TasksService,
		ProblemService,
		SubmitService,

		{
			provide: LocationStrategy,
			useClass: HashLocationStrategy
		}
	]
})
export class AppModule { }
