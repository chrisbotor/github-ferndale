
    // alert('loading service requests...');
    
	// this extracts the path in the URL and determines which page calls this javascript
    var pathArray = window.location.pathname.split("/");
    // alert('path name: ' + window.location.pathname);
    
    var callingPage = pathArray[2];
    // alert('callingPage: ' + callingPage);
    

    
//##############################################		AMENITY BEAN 		####################################### //

    var ServiceBean = Ext.data.Record.create([
	      {name: 'id', type: 'int'},
          {name: 'requestId', type: 'string'},
          {name: 'address', type: 'string'},
          {name: 'houseId', type: 'int'},
          {name: 'serviceId', type: 'int'},
          {name: 'des', type: 'string'},
          {name: 'preferredDate', type: 'string'},
          {name: 'preferredTime', type: 'string'},
          {name: 'confirmedDate', type: 'string'},
          {name: 'status', type: 'string'},
          {name: 'basic_cost', type: 'float'},
          {name: 'additional_cost', type: 'float'},
          {name: 'total_cost', type: 'float'},
          {name: 'uom', type: 'string'},
          {name: 'requestor', type: 'string'},
		  {name: 'regularPrice', type: 'float'},
		  {name: 'excessPrice', type: 'float'},
		  {name: 'quantity', type: 'int'},
		  {name: 'maxRegular', type: 'int'},
		  {name: 'remarks', type: 'string'},
		  {name: 'otherCharges', type: 'float'}
    ]);
    
    

    
 // #######################		AMENITY REQUESTS PROXY		########################## //
    
    
    // var arproxy = null;
    
    if (callingPage == 'admin-home.action')
    {
    	var serviceParam = 'home';
    }
    else if (callingPage == 'admin-search-service-requests.action')
    {
    	var serviceParam = 'search';
    }
    	
    
    // alert('SERVICE PARAM: ' + serviceParam); // IMPORTANT! The proxy cannot be changed by the assigning of value thru this. NEED TO RESEARCH MORE. Workaround is in the AdminRequestController
    
    var srproxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin/get-service-requests.action?callingPage=' + serviceParam
        }
    });

    
   
    
 // #######################		AMENITY REQUESTS READER		########################## //
    
    var srreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    }, ServiceBean);
    
    
  
    
    
 // #######################		AMENITY REQUESTS STORE		########################## //
    
    var srstore = new Ext.data.Store({
	    id: 'user',
	    proxy: srproxy,
	    //writer: writer,
	    reader: srreader,  // <-- plug a DataWriter into the store just as you would a Reader
	    autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
	});
    

    

// #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
       
    //read the data from simple array
    srstore.load();
    
