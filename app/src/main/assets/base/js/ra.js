var World = {

     poiData : {
        "id":1,
        "longitude": 0 ,
        "latitude": 0 ,
        "altitude": 0 ,
        "description": "esta es una descripcion de mi poi",
        "title": "titulo"
    },

    //variables que seran inicializadas desde ARcamActivity
    poiData_id:{
        "id":1
    },
    poiData_longitude:{
        "longitude":0
    },
    poiData_latitude:{
        "latitude":0
    },
    poiData_altitude:{
        "altitude":0
    },
    podData_descripcion:{
        "descripcion":"es la descripcion"
    },
    poiData_title:{
        "title":"titulo"
    },

    //funciones que seran llamadas desde ARcamActivity
    crearPoi_id: function crearPoi_idFn(i){
        World.poiData_id={
            "id":i
        };
    },
     crearPoi_longitude: function crearPoi_longitudeFn(lon){
         World.poiData_longitude={
             "longitude":lon
         };
         //alert(World.poiData_longitude.longitude);
     },
    crearPoi_latitude: function crearPoi_latitudeFn(lat){
        World.poiData_latitude={
            "latitude":lat
        };
    },
     crearPoi_altitude: function crearPoi_altitude(alt){
         World.poiData_altitude={
             "altitude":alt
         };
     },
     crearPoi_descripcion: function crearPoi_descripcionFn(desc){
         World.poiData_descripcion={
             "descripcion":desc
         };
     },
      crearPoi_title: function crearPoi_titleFn(tit){

          World.poiData_title={
              "title":tit
          };
          //alert(World.poiData_title.title);
      },
        //inicializamos las parametros del poi



	initiallyLoadedData: false,
	markerDrawable_directionIndicator:null,
	markerDrawable: null,


    crearPoi: function crearPoiFn() {


         //inicializamos poiData
              World.poiData={

                "longitude":parseFloat(World.poiData_longitude.longitude),
                "latitude":parseFloat(World.poiData_latitude.latitude),
                "title":World.poiData_title.title,

                "id": parseFloat(World.poiData_id),

                "altitude":parseFloat(World.poiData_altitude.altitude)
              };

        World.loadPoisFromJsonData(World.poiData);
       // World.initiallyLoadedData = true;

    },

	loadPoisFromJsonData: function loadPoisFromJsonDataFn(poiData) {

		// referenciamos la imagen del POI
		World.markerDrawable_idle = new AR.ImageResource("img/marker_idle.png");
		

		var markerLocation = new AR.GeoLocation(poiData.latitude, poiData.longitude,poiData.altitude);
		var markerImageDrawable = new AR.ImageDrawable(World.markerDrawable_idle, 2.5, {
			zOrder: 0,
			opacity: 1.0
		});

        this.titleLabel = new AR.Label(World.poiData.title.trunc(10), 1, {
            zOrder: 1,
            offsetY: 0.55,
            style: {
                textColor: '#FFFFFF',
                fontStyle: AR.CONST.FONT_STYLE.BOLD
            }
        });


        /*
            Create an AR.ImageDrawable using the AR.ImageResource for the direction indicator which was created in the World. Set options regarding the offset and anchor of the image so that it will be displayed correctly on the edge of the screen.
        */
        World.markerDrawable_directionIndicator = new AR.ImageResource("img/indi.png");
        var directionIndicatorDrawable = new AR.ImageDrawable(World.markerDrawable_directionIndicator, 0.1, {
            enabled: true,
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });
        //creación del radar
                AR.radar.background = new AR.ImageResource("img/radar_bg.png");

                //posicionamos el radar en pantalla
                AR.radar.positionX = 0.05;
                AR.radar.positionY = 0.05;
                AR.radar.width = 0.4;

                //definimos el centro del radar
                AR.radar.centerX = 0.5;
                AR.radar.centerY = 0.5;
                AR.radar.radius = 0.4;

                //habilitamos el radar
                AR.radar.enabled = true;

                var radarCircle = new AR.Circle(0.05, {style: {fillColor: '#83ff7b'}});


		// creamos nuestro POI con la imagen marker.png referenciando dicho POI
		var markerObject = new AR.GeoObject(markerLocation, {
			drawables: {
				cam: [markerImageDrawable,this.titleLabel],
				indicator: directionIndicatorDrawable,
				radar: radarCircle
			}
		});



	},
	locationChanged: function locationChangedFn(lat, lon, alt, acc) {
		// cuando cambie la posicion del usuario
        var location_user = new AR.GeoLocation(lat, lon, alt);
        var location_objective = new AR.GeoLocation(World.poiData.latitude,World.poiData.longitude ,World.poiData.altitude);

        //calcular la distancia hasta el objetivo
        var dist = parseInt(location_user.distanceTo(location_objective));
       // World.loadPoisFromJsonData(poiData);

        document.getElementById("poi-detail-distance").innerHTML=dist;
      // $("#poi-detail-distance").html(dist);
	},
};
 // will truncate all strings longer than given max-length "n". e.g. "foobar".trunc(3) -> "foo..."
    String.prototype.trunc = function(n) {
        return this.substr(0, n - 1) + (this.length > n ? '...' : '');
    };

AR.context.onLocationChanged = World.locationChanged;