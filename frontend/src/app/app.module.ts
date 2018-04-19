import { NgModule } 		from '@angular/core'
import { BrowserModule }	from '@angular/platform-browser'
import { FormsModule }		from '@angular/forms'
import { HttpModule }		from '@angular/http'

import { AppRoutingModule }	from './app-routing.module'

import { MainComponent}		from './Main/main.component'
import { LoginComponent }	from './Login/login.component'
import { TasksComponent }	from './Tasks/tasks.component'

import { TasksService }		from './Tasks/tasks.service'

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
		TasksComponent
	],
	bootstrap: [ MainComponent ],
	providers: [
		TasksService
	]
})
export class AppModule { }
