
    // alert('loading amenity requests...');
    
	// this extracts the path in the URL and determines which page calls this javascript
    var pathArray = window.location.pathname.split("/");
    // alert('path name: ' + window.location.pathname);
    
    var callingPage = pathArray[2];
    // alert('callingPage: ' + callingPage);
    

    
//##############################################		AMENITY BEAN 		####################################### //

    var AmenityBean = Ext.data.Record.create([
          {name: 'id', type: 'int'},
          {name: 'houseId', type: 'int'},
          {name: 'amenityId', type: 'int'},
          {name: 'requestId', type: 'string'},
          {name: 'address', type: 'string'},
          {name: 'des', type: 'string'},
          {name: 'requestedDate', type: 'string'},
          {name: 'startTime', type: 'string'}, 
          {name: 'endTime', type: 'string'},
          {name: 'status', type: 'string'},
          {name: 'basic_cost', type: 'float'},
          {name: 'additional_cost', type: 'float'},
          {name: 'total_cost', type: 'float'},
          {name: 'uom', type: 'string'},
          {name: 'requestor', type: 'string'},
          {name: 'quantity', type: 'int'},
          {name: 'regularPrice', type: 'float'},
          {name: 'excessPrice', type: 'float'},
          {name: 'maxRegular', type: 'int'},
          {name: 'remarks', type: 'string'},
          {name: 'otherCharges', type: 'float'}
     ]);
    
    

    
 // #######################		AMENITY REQUESTS PROXY		########################## //
    
    
    // var arproxy = null;
    
    if (callingPage == 'admin-home.action')
    {
    	var amenityParam = 'home';
    }
    else if (callingPage == 'admin-search-amenity-requests.action')
    {
    	var amenityParam = 'search';
    }
    	
    
    // alert('AMENITY PARAM: ' + amenityParam); // IMPORTANT! The proxy cannot be changed by the assigning of value thru this. NEED TO RESEARCH MORE. Workaround is in the AdminRequestController
    
    var arproxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin/get-amenity-requests.action?callingPage=' + amenityParam
        }
    });

    
   
    
 // #######################		AMENITY REQUESTS READER		########################## //
    
    var arreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    }, AmenityBean);
    
    
  
    
    
 // #######################		AMENITY REQUESTS STORE		########################## //
    
    var arstore = new Ext.data.Store({
	    id: 'user',
	    proxy: arproxy,
	    //writer: writer,
	    reader: arreader,  // <-- plug a DataWriter into the store just as you would a Reader
	    autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
	});
    

    

// #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
       
    //read the data from simple array
    arstore.load();
    
