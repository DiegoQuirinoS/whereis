import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MessageStreamComponent } from './message-stream/message-stream.component';
import { MapComponent } from './map/map.component';

const routes: Routes = [
  {path: '', component: MapComponent},
  {path: 'mapa', component: MapComponent},
  {path: 'messages', component: MessageStreamComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, 
     { enableTracing: false } // <-- debugging purposes only
    )],
  exports: [RouterModule]
})
export class AppRoutingModule { }
