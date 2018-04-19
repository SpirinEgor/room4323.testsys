import { NgModule }             from '@angular/core'
import { RouterModule, Routes } from '@angular/router'

import { LoginComponent }		from './Login/login.component'
import { TasksComponent}		from './Tasks/tasks.component'

const routes: Routes = [
	{ path: '', redirectTo: '/login', pathMatch: 'full' },
	{ path: 'login', component: LoginComponent },
	{ path: 'tasks', component: TasksComponent },
];

@NgModule({
	imports: [ RouterModule.forRoot(routes) ],
	exports: [ RouterModule ]
})
export class AppRoutingModule {}
