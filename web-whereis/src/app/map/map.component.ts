import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import OSM from 'ol/source/OSM';
import {Vector as VectorSource} from 'ol/source';
import GeoJSON from 'ol/format/GeoJSON';
import {bbox as bboxStrategy} from 'ol/loadingstrategy';
import {Tile as TileLayer, Vector as VectorLayer} from 'ol/layer';
import {Stroke, Style, Circle as CircleStyle, Fill} from 'ol/style';
import Select from 'ol/interaction/Select';
import {click} from 'ol/events/condition';
import Overlay from 'ol/Overlay';
import {transform, fromLonLat, toLonLat} from 'ol/proj';
import {toStringHDMS} from 'ol/coordinate';
import Point from 'ol/geom/Point';
import Collection from 'ol/Collection';
import Feature from 'ol/Feature';

import { RxStompService } from '@stomp/ng2-stompjs';
import { Message } from '@stomp/stompjs';
import { Observable, of, Subject } from 'rxjs';
import { catchError, takeUntil } from 'rxjs/operators';

declare var $: any;

@Component({
  selector: 'map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  private map: any; 

  private messages:any;

  private destroy$ = new Subject();

  private custom:any;

  constructor(private http: HttpClient, private rxStompService: RxStompService) { }

  ngOnDestroy(): void{
    this.destroy$.next(null);
    this.destroy$.unsubscribe();
  }

  ngOnInit(): void {   

    var vectorSource = new VectorSource({
      format: new GeoJSON(),
      url: function(extent){
        return (
          'https://ahocevar.com/geoserver/wfs?service=WFS&' +
          'version=1.1.0&request=GetFeature&typename=osm:water_areas&' +
          'outputFormat=application/json&srsname=EPSG:3857&' +
          'bbox=' +  extent.join(',') + ',EPSG:3857'
        );
      },
      strategy: bboxStrategy,
    });   

    var vector = new VectorLayer({
      source: vectorSource, 
      style: new Style({
        stroke: new Stroke({
          color: 'rgba(0, 0, 255, 1.0)',
          width: 2,
        }),
      }),
    });   

    var tile = new TileLayer({
      source: new OSM()
    })   

    if(navigator.geolocation){
      navigator.geolocation.getCurrentPosition((p:any) => {       
        
        var feature = new Feature({
          geometry: new Point(transform([p.coords.longitude, p.coords.latitude], 'EPSG:4326', 'EPSG:3857')),
          name: 'My place'
        });

        this.custom = new VectorLayer({
          source: new VectorSource({
            features: new Collection([feature])
          }),
          style:  new Style({
            image: new CircleStyle({
                radius: 5,   
                fill: new Fill({
                  color: 'rgba(240, 52, 52, 1)',
                }),         
                stroke: new Stroke({color: 'red', width: 1}),
              })
          })    
        }); 

        this.map.getView().setCenter(transform([p.coords.longitude, p.coords.latitude], 'EPSG:4326', 'EPSG:3857'))
        this.map.getLayers().push(this.custom);
      })
    } 


    /*
    this.map.on('click', function(e:any) {      
      console.log('clicou -> '+ e)
    });
    */

   this.map = new Map({
      target: 'map',
      layers: [
        tile, vector
      ],
      view: new View({
        center: [0,0],
        zoom: 18
      })
    });

    // Popup showing the position the user clicked
    var popup = new Overlay({
      element: document.getElementById('popup')!
    });

    this.map.addOverlay(popup);

    var select = new Select({
      condition: click,
    });

    this.map.addInteraction(select);
    
    select.on('select', function(e:any) {
      console.log(e)      
    });   

    /*
    this.map.on('click', function (evt:any) {      
      var element = popup.getElement();
      var coordinate = evt.coordinate;
      var hdms = toStringHDMS(toLonLat(coordinate));
    
      $(element).popover('dispose');
      popup.setPosition(coordinate);
      $(element).popover({
        container: element,
        placement: 'top',
        animation: false,
        html: true,
        content: '<p>The location you clicked was:</p><code>' + hdms + '</code>',
      });
      $(element).popover('show');
    });    
    */

   this.messages = [];
   this.rxStompService.watch('/topic/messages')
     .pipe(takeUntil(this.destroy$))
     .subscribe((message: Message) => {
      
        var coords = JSON.parse(message.body).coords
        
        var info = JSON.parse(message.body).info
        
        var feature = new Feature({
          geometry: new Point(transform(coords, 'EPSG:4326', 'EPSG:3857')),
            name:  info
        });
        
        this.custom.getSource().addFeature(feature)   
       
     })
  }
}
